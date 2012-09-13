/*
 * Copyright 2007-2012 The Europeana Foundation
 *
 *  Licenced under the EUPL, Version 1.1 (the "Licence") and subsequent versions as approved
 *  by the European Commission;
 *  You may not use this work except in compliance with the Licence.
 * 
 *  You may obtain a copy of the Licence at:
 *  http://joinup.ec.europa.eu/software/page/eupl
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under
 *  the Licence is distributed on an "AS IS" basis, without warranties or conditions of
 *  any kind, either express or implied.
 *  See the Licence for the specific language governing permissions and limitations under
 *  the Licence.
 */
package eu.europeana.corelib.solr.service.impl;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.Resource;


import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.util.NamedList;
import org.springframework.beans.factory.annotation.Value;

import eu.europeana.corelib.definitions.exception.ProblemType;
import eu.europeana.corelib.definitions.solr.Facet;
import eu.europeana.corelib.definitions.solr.QueryType;
import eu.europeana.corelib.definitions.solr.beans.BriefBean;
import eu.europeana.corelib.definitions.solr.beans.FullBean;
import eu.europeana.corelib.definitions.solr.beans.IdBean;
import eu.europeana.corelib.definitions.solr.model.Query;
import eu.europeana.corelib.definitions.solr.model.Term;
import eu.europeana.corelib.solr.bean.impl.ApiBeanImpl;
import eu.europeana.corelib.solr.bean.impl.BriefBeanImpl;
import eu.europeana.corelib.solr.bean.impl.IdBeanImpl;
import eu.europeana.corelib.solr.exceptions.SolrTypeException;
import eu.europeana.corelib.solr.model.ResultSet;
import eu.europeana.corelib.solr.server.EdmMongoServer;
import eu.europeana.corelib.solr.server.impl.EuropeanaHttpClient;

import eu.europeana.corelib.solr.service.SearchService;
import eu.europeana.corelib.solr.service.query.MoreLikeThis;
import eu.europeana.corelib.solr.utils.SolrUtils;
import eu.europeana.corelib.tools.utils.EuropeanaUriUtils;

/**
 * @see eu.europeana.corelib.solr.service.SearchService
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
public class SearchServiceImpl implements SearchService {

	/**
	 * Default number of documents retrieved by MoreLikeThis
	 */
	private static final int DEFAULT_MLT_COUNT = 10;

	// provided by setter
	private SolrServer solrServer;

	@Resource(name = "corelib_solr_mongoServer")
	private EdmMongoServer mongoServer;

	@Resource (name = "corelib_solr_httpClient")
	private EuropeanaHttpClient httpClient;
	@Value("#{europeanaProperties['solr.facetLimit']}")
	private int facetLimit;

	private static final Logger log = Logger.getLogger(SearchServiceImpl.class.getName());

	// private static final String TERMS_QUERY_TYPE = "/terms";

	// private static final String TERMS_REGEX_FLAG = "case_insensitive";

	@Override
	public FullBean findById(String collectionId, String recordId)
			throws SolrTypeException {
		return findById(EuropeanaUriUtils.createEuropeanaId(collectionId,
				recordId));
	}

	@Override
	public FullBean findById(String europeanaObjectId) throws SolrTypeException {

		FullBean fullBean = mongoServer.getFullBean(europeanaObjectId);
		if (fullBean != null) {
			try {
				fullBean.setSimilarItems(findMoreLikeThis(europeanaObjectId));
			} catch (SolrServerException e) {
				log.severe("SolrServerException: " + e.getMessage());
			}
		}

		return fullBean;
	}

	@Override
	public FullBean resolve(String collectionId, String recordId)
			throws SolrTypeException {
		return resolve(EuropeanaUriUtils.createEuropeanaId(collectionId,
				recordId));
	}

	@Override
	public FullBean resolve(String europeanaObjectId) throws SolrTypeException {

		FullBean fullBean = mongoServer.resolve(europeanaObjectId);
		if (fullBean != null) {
			try {
				fullBean.setSimilarItems(findMoreLikeThis(europeanaObjectId));
			} catch (SolrServerException e) {
				log.severe("SolrServerException: " + e.getMessage());
			}
		}

		return fullBean;
	}

	@Override
	public List<BriefBean> findMoreLikeThis(String europeanaObjectId) throws SolrServerException {
		return findMoreLikeThis(europeanaObjectId, DEFAULT_MLT_COUNT);
	}

	public List<BriefBean> findMoreLikeThis(String europeanaObjectId, int count)
			throws SolrServerException {
		String query = "europeana_id:\"" + europeanaObjectId + "\"";

		SolrQuery solrQuery = new SolrQuery().setQuery(query);
		solrQuery.setQueryType(QueryType.ADVANCED.toString());
		solrQuery.set("mlt", true);
		List<String> fields = new ArrayList<String>();
		for (MoreLikeThis mltField : MoreLikeThis.values()) {
			fields.add(mltField.toString());
		}
		solrQuery.set("mlt.fl", StringUtils.join(fields, ","));
		solrQuery.set("mlt.mintf", 1);
		solrQuery.set("mlt.match.include", "false");
		solrQuery.set("mlt.count", count);
		
		System.out.println(solrQuery.toString());

		QueryResponse response = solrServer.query(solrQuery);

		@SuppressWarnings("unchecked")
		NamedList<Object> moreLikeThisList = (NamedList<Object>) response.getResponse().get("moreLikeThis");
		@SuppressWarnings("unchecked")
		List<SolrDocument> docs = (List<SolrDocument>)moreLikeThisList.getVal(0);
		List<BriefBean> beans = new ArrayList<BriefBean>();
		for (SolrDocument doc : docs) {
			beans.add(solrServer.getBinder().getBean(BriefBeanImpl.class, doc));
		}
		return beans;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IdBean> ResultSet<T> search(Class<T> beanInterface,
			Query query) throws SolrTypeException {
		ResultSet<T> resultSet = new ResultSet<T>();
		Class<? extends IdBeanImpl> beanClazz = SolrUtils.getImplementationClass(beanInterface);

		if (beanClazz == BriefBeanImpl.class || beanClazz == ApiBeanImpl.class) {
			String[] refinements = query.getRefinements();
			if (SolrUtils.checkTypeFacet(refinements)) {
				SolrQuery solrQuery = new SolrQuery()
						.setQuery(query.getQuery());
				solrQuery.setFacet(true);
				for (Facet facet : query.getFacets()) {
					solrQuery.addFacetField(facet.toString());
				}
				if (refinements != null) {
					solrQuery.addFilterQuery(refinements);
				}
				solrQuery.setFacetLimit(facetLimit);
				solrQuery.setRows(query.getPageSize());
				solrQuery.setStart(query.getStart());

				// These are going to change when we import ASSETS as well
				solrQuery.setQueryType(QueryType.ADVANCED.toString());
				solrQuery.setSortField("COMPLETENESS", ORDER.desc);
				solrQuery.setSortField("score", ORDER.desc);

				// enable spellcheck
				if (solrQuery.getStart() == null
						|| solrQuery.getStart().intValue() <= 1) {
					solrQuery.setParam("spellcheck", "on");
					solrQuery.setParam("spellcheck.collate", "true");
					solrQuery.setParam("spellcheck.extendedResults", "true");
					solrQuery.setParam("spellcheck.onlyMorePopular", "true");
					solrQuery.setParam("spellcheck.q", query.getQuery());
				}

				// add extra parameters if any
				if (query.getParameters() != null) {
					Map<String, String> parameters = query.getParameters();
					for (String key : parameters.keySet()) {
						solrQuery.setParam(key, parameters.get(key));
					}
				}

				try {
					log.info("Solr query is: " + solrQuery);
					QueryResponse queryResponse = solrServer.query(solrQuery);

					resultSet.setResults((List<T>) queryResponse
							.getBeans(beanClazz));
					resultSet.setFacetFields(queryResponse.getFacetFields());
					resultSet.setResultSize(queryResponse.getResults()
							.getNumFound());
					resultSet.setSearchTime(queryResponse.getElapsedTime());
					resultSet.setSpellcheck(queryResponse
							.getSpellCheckResponse());
				} catch (SolrServerException e) {
					log.severe("SolrServerException: " + e.getMessage());
					resultSet = null;
					throw new SolrTypeException(e, ProblemType.MALFORMED_QUERY);
				}

			} else {
				resultSet = null;
				throw new SolrTypeException(ProblemType.INVALIDARGUMENTS);
			}

		} else {
			resultSet = null;
			throw new SolrTypeException(ProblemType.INVALIDARGUMENTS);
		}
		return resultSet;
	}

	/*
	 * @Override public List<eu.europeana.corelib.solr.model.Term>
	 * suggestions(String query, int pageSize) throws SolrTypeException {
	 * SolrQuery solrQuery = new SolrQuery();
	 * solrQuery.setQueryType(TERMS_QUERY_TYPE); solrQuery.setTerms(true);
	 * solrQuery.setTermsLimit(pageSize); solrQuery.setTermsPrefix(query);
	 * solrQuery.setTermsRegexFlag(TERMS_REGEX_FLAG);
	 * solrQuery.addTermsField("titleSpell");
	 * solrQuery.addTermsField("whoSpell");
	 * solrQuery.addTermsField("whatSpell");
	 * solrQuery.addTermsField("whenSpell");
	 * solrQuery.addTermsField("whereSpell"); try { QueryResponse queryResponse
	 * = solrServer.query(solrQuery); TermsResponse response =
	 * queryResponse.getTermsResponse();
	 * List<eu.europeana.corelib.solr.model.Term> results = new
	 * ArrayList<eu.europeana.corelib.solr.model.Term>(); for (Term term :
	 * response.getTerms("titleSpell")) { results.add(new
	 * eu.europeana.corelib.solr.model.Term(term .getTerm(),
	 * term.getFrequency(), "Title")); } for(Term term :
	 * response.getTerms("whoSpell")){ results.add(new
	 * eu.europeana.corelib.solr.model.Term(term .getTerm(),
	 * term.getFrequency(), "Person")); } for (Term term :
	 * response.getTerms("whenSpell")){ results.add(new
	 * eu.europeana.corelib.solr.model.Term(term .getTerm(),
	 * term.getFrequency(), "Date")); } for (Term term :
	 * response.getTerms("whatSpell")){ results.add(new
	 * eu.europeana.corelib.solr.model.Term(term .getTerm(),
	 * term.getFrequency(), "Subject")); } for (Term term :
	 * response.getTerms("whereSpell")){ results.add(new
	 * eu.europeana.corelib.solr.model.Term(term .getTerm(),
	 * term.getFrequency(), "Place")); } return results; } catch
	 * (SolrServerException e) {
	 * 
	 * throw new SolrTypeException(e, ProblemType.MALFORMED_QUERY);
	 * 
	 * }
	 * 
	 * }
	 */

	/**
	 * Adds suggestions regarding a given query string
	 */
	public List<Term> suggestions(String query, int pageSize)
			throws SolrTypeException {
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setFacet(true);
		solrQuery.setFacetMinCount(1);
		solrQuery.setFacetPrefix(query);
		solrQuery.setFacetLimit(pageSize);
		solrQuery.setQuery("*:*");
		solrQuery.setRows(0);
		solrQuery.addFacetField("whoSpell", "whatSpell", "whereSpell", "whenSpell", "titleSpell");
		List<Term> results = new ArrayList<Term>();

		QueryResponse response;
		try {
			log.info("SolrQuery:" +solrQuery);
			response = solrServer.query(solrQuery);

			FacetField who = response.getFacetField("whoSpell");
			List<Count> whoSuggestions = who.getValues();
			if (whoSuggestions != null) {
				for (Count whoSuggestion : whoSuggestions) {
					results.add(new Term(whoSuggestion.getName(), whoSuggestion.getCount(), "Person"));
				}
			}

			FacetField what = response.getFacetField("whatSpell");
			List<Count> whatSuggestions = what.getValues();
			if (whatSuggestions != null) {
				for (Count whatSuggestion : whatSuggestions) {
					results.add(new Term(whatSuggestion.getName(), whatSuggestion.getCount(), "Subject"));
				}
			}

			FacetField when = response.getFacetField("whenSpell");
			List<Count> whenSuggestions = when.getValues();
			if (whenSuggestions != null) {
				for (Count whenSuggestion : whenSuggestions) {
					results.add(new Term(whenSuggestion.getName(), whenSuggestion.getCount(), "Period"));
				}
			}

			FacetField where = response.getFacetField("whereSpell");
			List<Count> whereSuggestions = where.getValues();
			if (whereSuggestions != null) {
				for (Count whereSuggestion : whereSuggestions) {
					results.add(new Term(whereSuggestion.getName(), whereSuggestion.getCount(), "Place"));
				}
			}

			FacetField title = response.getFacetField("titleSpell");
			List<Count> titleSuggestions = title.getValues();
			if (titleSuggestions != null) {
				for (Count titleSuggestion : titleSuggestions) {
					results.add(new Term(titleSuggestion.getName(), titleSuggestion.getCount(), "Title"));
				}
			}
		} catch (SolrServerException e) {

			log.severe("SolrServerException: " + e.getMessage());
		}
		Collections.sort(results);
		return results.size() > pageSize 
				? results.subList(0, pageSize)
				: results;
	}

	public void setSolrServer(SolrServer solrServer) {
		//If it is instance of CommonsHTTPSolrServer
		if (solrServer instanceof CommonsHttpSolrServer) {
			try {
				//Create a new solrServer. HttpClient is final and provided upon construction
				this.solrServer = new CommonsHttpSolrServer(((CommonsHttpSolrServer) solrServer).getBaseURL(), httpClient);
			} catch (MalformedURLException e) {
				log.severe("MalformedURLException: " + e.getMessage());
			}
		}
		else{
			this.solrServer = solrServer;
		}
	}
}
