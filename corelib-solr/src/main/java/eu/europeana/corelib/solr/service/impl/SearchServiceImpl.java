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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.Resource;


import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Value;

import eu.europeana.corelib.definitions.exception.ProblemType;
import eu.europeana.corelib.definitions.solr.Facet;
import eu.europeana.corelib.definitions.solr.QueryType;
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

	// provided by setter
	private SolrServer solrServer;

	@Resource(name = "corelib_solr_mongoServer")
	private EdmMongoServer mongoServer;

	@Resource (name = "corelib_solr_httpClient")
	private EuropeanaHttpClient httpClient;
	@Value("#{europeanaProperties['solr.facetLimit']}")
	private int facetLimit;

	private static final Logger log = Logger.getLogger(SearchServiceImpl.class
			.getName());

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
				fullBean.setRelatedItems(findMoreLikeThis(europeanaObjectId)
						.getBeans(BriefBeanImpl.class));
			} catch (SolrServerException e) {
				// LOG HERE
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
				fullBean.setRelatedItems(findMoreLikeThis(europeanaObjectId)
						.getBeans(BriefBeanImpl.class));
			} catch (SolrServerException e) {
				// LOG
			}
		}

		return fullBean;
	}

	private QueryResponse findMoreLikeThis(String europeanaObjectId)
			throws SolrServerException {
		SolrQuery solrQuery = new SolrQuery().setQuery("europeana_id:\""
				+ europeanaObjectId + "\"");
		solrQuery.set("mlt", true);
		String[] mlt = new String[MoreLikeThis.values().length];
		int i = 0;
		for (MoreLikeThis mltField : MoreLikeThis.values()) {
			mlt[i] = mltField.toString();
			i++;
		}
		solrQuery.set("mlt.fl", mlt);
		solrQuery.setQueryType(QueryType.ADVANCED.toString());
		return solrServer.query(solrQuery);

	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IdBean> ResultSet<T> search(Class<T> beanInterface,
			Query query) throws SolrTypeException {
		ResultSet<T> resultSet = new ResultSet<T>();
		Class<? extends IdBeanImpl> beanClazz = SolrUtils
				.getImplementationClass(beanInterface);

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

	public List<Term> suggestions(String query, int pageSize)
			throws SolrTypeException {
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setFacet(true);
		solrQuery.setFacetMinCount(1);
		solrQuery.setFacetPrefix(query);
		solrQuery.setFacetLimit(pageSize);
		solrQuery.setQuery("*:*");
		solrQuery.setRows(0);
		solrQuery.addFacetField("whoSpell", "whatSpell", "whereSpell",
				"whenSpell", "titleSpell");
		List<Term> results = new ArrayList<Term>();

		QueryResponse response;
		try {
			response = solrServer.query(solrQuery);

			FacetField who = response.getFacetField("whoSpell");

			List<Count> whoSuggestions = who.getValues();
			if (whoSuggestions != null) {
				for (Count whoSuggestion : whoSuggestions) {
					results.add(new Term(whoSuggestion.getName(), whoSuggestion
							.getCount(), "Person"));
				}
			}

			FacetField what = response.getFacetField("whatSpell");

			List<Count> whatSuggestions = what.getValues();
			if (whatSuggestions != null) {
				for (Count whatSuggestion : whatSuggestions) {
					results.add(new Term(whatSuggestion.getName(),
							whatSuggestion.getCount(), "Subject"));
				}

			}
			FacetField when = response.getFacetField("whenSpell");

			List<Count> whenSuggestions = when.getValues();
			if (whenSuggestions != null) {
				for (Count whenSuggestion : whenSuggestions) {
					results.add(new Term(whenSuggestion.getName(),
							whenSuggestion.getCount(), "Period"));
				}
			}
			FacetField where = response.getFacetField("whereSpell");

			List<Count> whereSuggestions = where.getValues();
			if (whereSuggestions != null) {
				for (Count whereSuggestion : whereSuggestions) {
					results.add(new Term(whereSuggestion.getName(),
							whereSuggestion.getCount(), "Place"));
				}
			}
			FacetField title = response.getFacetField("titleSpell");

			List<Count> titleSuggestions = title.getValues();
			if (titleSuggestions != null) {
				for (Count titleSuggestion : titleSuggestions) {
					results.add(new Term(titleSuggestion.getName(),
							titleSuggestion.getCount(), "Title"));
				}
			}
		} catch (SolrServerException e) {
			// LOG
		}
		Collections.sort(results);
		return results.size() > pageSize ? results.subList(0, pageSize)
				: results;
	}

	public void setSolrServer(SolrServer solrServer) {

		if (solrServer instanceof CommonsHttpSolrServer) {
			try {
				this.solrServer = new CommonsHttpSolrServer(((CommonsHttpSolrServer) solrServer).getBaseURL(), httpClient);
			} catch (MalformedURLException e) {
				//LOG HERE
			}
		}
		else{
			this.solrServer = solrServer;
		}
	}
}
