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

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.protocol.HttpContext;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.request.LukeRequest;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse.Collation;
import org.apache.solr.client.solrj.response.SpellCheckResponse.Correction;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrException;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.apache.solr.common.util.NamedList;
import org.springframework.beans.factory.annotation.Value;

import com.google.code.morphia.mapping.MappingException;

import eu.europeana.corelib.definitions.exception.ProblemType;
import eu.europeana.corelib.definitions.solr.Facet;
import eu.europeana.corelib.definitions.solr.beans.BriefBean;
import eu.europeana.corelib.definitions.solr.beans.FullBean;
import eu.europeana.corelib.definitions.solr.beans.IdBean;
import eu.europeana.corelib.definitions.solr.model.Query;
import eu.europeana.corelib.definitions.solr.model.Term;
import eu.europeana.corelib.solr.bean.impl.ApiBeanImpl;
import eu.europeana.corelib.solr.bean.impl.BriefBeanImpl;
import eu.europeana.corelib.solr.bean.impl.IdBeanImpl;
import eu.europeana.corelib.solr.exceptions.MongoDBException;
import eu.europeana.corelib.solr.exceptions.SolrTypeException;
import eu.europeana.corelib.solr.model.ResultSet;
import eu.europeana.corelib.solr.server.EdmMongoServer;
import eu.europeana.corelib.solr.service.SearchService;
import eu.europeana.corelib.solr.service.query.MoreLikeThis;
import eu.europeana.corelib.solr.utils.SolrUtils;
import eu.europeana.corelib.tools.lookuptable.EuropeanaId;
import eu.europeana.corelib.tools.lookuptable.EuropeanaIdMongoServer;
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

	private static final String UNION_FACETS_FORMAT = "'{'!ex={0}'}'{0}";

	/**
	 * Number of milliseconds before the query is aborted by SOLR
	 */
	private static final int TIME_ALLOWED = 30000;

	private Map<String, Long> total = new HashMap<String, Long>();

	/**
	 * The list of possible field input for spelling suggestions
	 */
	private static final List<String> SPELL_FIELDS = Arrays.asList("who",
			"what", "where", "when", "title");

	// provided by setter
	private SolrServer solrServer;

	@Resource(name = "corelib_solr_mongoServer")
	protected EdmMongoServer mongoServer;

	@Resource(name = "corelib_solr_idServer")
	protected EuropeanaIdMongoServer idServer;
	
	@Value("#{europeanaProperties['solr.facetLimit']}")
	private int facetLimit;

	@Value("#{europeanaProperties['solr.username']}")
	private String username;
	@Value("#{europeanaProperties['solr.password']}")
	private String password;
	
	private final static String RESOLVE_PREFIX = "http://www.europeana.eu/resolve/record";

	private String mltFields;

	private static final Logger log = Logger.getLogger(SearchServiceImpl.class.getCanonicalName());

	// private static final String TERMS_QUERY_TYPE = "/terms";

	// private static final String TERMS_REGEX_FLAG = "case_insensitive";

	@Override
	public FullBean findById(String collectionId, String recordId, boolean similarItems)
			throws MongoDBException {
		return findById(EuropeanaUriUtils.createEuropeanaId(collectionId, recordId),similarItems);
	}


	@Override
	public FullBean findById(String europeanaObjectId, boolean similarItems) throws MongoDBException {
		long t0 = new Date().getTime();
		try{
		FullBean fullBean = mongoServer.getFullBean(europeanaObjectId);
		logTime("mongo findById", (new Date().getTime() - t0));
		if (fullBean != null && similarItems) {
			try {
				fullBean.setSimilarItems(findMoreLikeThis(europeanaObjectId));
			} catch (SolrServerException e) {
				log.severe("SolrServerException: " + e.getMessage());
			}
		}

		return fullBean;
		}
		catch (MappingException e){
			throw new MongoDBException(ProblemType.RECORD_RETRIEVAL_ERROR);
		}
	}

	@Override
	public FullBean resolve(String collectionId, String recordId, boolean similarItems)
			throws SolrTypeException {
		return resolve(EuropeanaUriUtils.createResolveEuropeanaId(collectionId,
				recordId),similarItems);
	}

	@Override
	public FullBean resolve(String europeanaObjectId,boolean similarItems) throws SolrTypeException {
		long t0 = new Date().getTime();
		mongoServer.setEuropeanaIdMongoServer(idServer);
		FullBean fullBean = mongoServer.resolve(europeanaObjectId);
		logTime("mongo resolve", (new Date().getTime() - t0));
		if (fullBean != null) {
			try {
				fullBean.setSimilarItems(findMoreLikeThis(fullBean.getAbout()));
			} catch (SolrServerException e) {
				log.severe("SolrServerException: " + e.getMessage());
			}
		}

		return fullBean;
	}

	@Override
	public String resolveId(String europeanaObjectId) {
		EuropeanaId newId = idServer.retrieveEuropeanaIdFromOld(europeanaObjectId);
		if(newId!=null){
			idServer.updateTime(newId.getNewId(), europeanaObjectId);
			return newId.getNewId();
		}
		
		newId = idServer
				.retrieveEuropeanaIdFromOld(RESOLVE_PREFIX + europeanaObjectId);
		if(newId!=null){
			idServer.updateTime(newId.getNewId(), RESOLVE_PREFIX +europeanaObjectId);
			return newId.getNewId();
		}
		return null;
	}


	@Override
	public String resolveId(String collectionId, String recordId) {
		return resolveId(EuropeanaUriUtils.createResolveEuropeanaId(collectionId,
				recordId));
	}
	
	
	@Override
	public List<BriefBean> findMoreLikeThis(String europeanaObjectId)
			throws SolrServerException {
		return findMoreLikeThis(europeanaObjectId, DEFAULT_MLT_COUNT);
	}

	@Override
	public List<BriefBean> findMoreLikeThis(String europeanaObjectId, int count)
			throws SolrServerException {
		String query = "europeana_id:\"" + europeanaObjectId + "\"";

		SolrQuery solrQuery = new SolrQuery().setQuery(query);
		// solrQuery.setQueryType(QueryType.ADVANCED.toString());
		solrQuery.set("mlt", true);

		if (mltFields == null) {
			List<String> fields = new ArrayList<String>();
			for (MoreLikeThis mltField : MoreLikeThis.values()) {
				fields.add(mltField.toString());
			}
			mltFields = StringUtils.join(fields, ",");
		}
		solrQuery.set("mlt.fl", mltFields);
		solrQuery.set("mlt.mintf", 1);
		solrQuery.set("mlt.match.include", "false");
		solrQuery.set("mlt.count", count);
		solrQuery.set("rows", 1);
		solrQuery.setTimeAllowed(TIME_ALLOWED);
		log.fine(solrQuery.toString());

		QueryResponse response = solrServer.query(solrQuery);
		logTime("MoreLikeThis", response.getElapsedTime());

		@SuppressWarnings("unchecked")
		NamedList<Object> moreLikeThisList = (NamedList<Object>) response.getResponse().get("moreLikeThis");
		@SuppressWarnings("unchecked")
		List<SolrDocument> docs = (List<SolrDocument>) moreLikeThisList.getVal(0);
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
			String[] refinements = query.getRefinements(true);
			if (SolrUtils.checkTypeFacet(refinements)) {
				SolrQuery solrQuery = new SolrQuery().setQuery(query.getQuery());

				if (refinements != null) {
					solrQuery.addFilterQuery(refinements);
				}

				solrQuery.setRows(query.getPageSize());
				solrQuery.setStart(query.getStart());

				// These are going to change when we import ASSETS as well
				// solrQuery.setQueryType(QueryType.ADVANCED.toString());
				// query.setQueryType(solrQuery.getQueryType());

				// solrQuery.setSortField("COMPLETENESS", ORDER.desc);
				solrQuery.setSortField("score", ORDER.desc);
				solrQuery.setTimeAllowed(TIME_ALLOWED);
				// add extra parameters if any
				if (query.getParameters() != null) {
					Map<String, String> parameters = query.getParameters();
					for (String key : parameters.keySet()) {
						solrQuery.setParam(key, parameters.get(key));
					}
				}

				// facets are optional
				if (query.isAllowFacets()) {
					solrQuery.setFacet(true);
					List<String> filteredFacets = query.getFilteredFacets();
					boolean hasFacetRefinements = (filteredFacets != null && filteredFacets.size() > 0);
					for (Facet facet : query.getFacets()) {
						String facetToAdd = facet.toString();
						if (query.isProduceFacetUnion()) {
							if (hasFacetRefinements && filteredFacets.contains(facetToAdd)) {
								facetToAdd = MessageFormat.format(UNION_FACETS_FORMAT, facetToAdd);
							}
						}
						solrQuery.addFacetField(facetToAdd);
					}
					solrQuery.setFacetLimit(facetLimit);
				}

				// spellcheck is optional
				if (query.isAllowSpellcheck()) {
					if (solrQuery.getStart() == null
							|| solrQuery.getStart().intValue() <= 1) {
						solrQuery.setParam("spellcheck", "on");
						solrQuery.setParam("spellcheck.collate", "true");
						solrQuery.setParam("spellcheck.extendedResults", "true");
						solrQuery.setParam("spellcheck.onlyMorePopular", "true");
						solrQuery.setParam("spellcheck.q", query.getQuery());
					}
				}

				try {
					log.fine("Solr query is: " + solrQuery);
					QueryResponse queryResponse = solrServer.query(solrQuery);
					logTime("search", queryResponse.getElapsedTime());

					resultSet.setResults((List<T>) queryResponse.getBeans(beanClazz));
					resultSet.setFacetFields(queryResponse.getFacetFields());
					resultSet.setResultSize(queryResponse.getResults().getNumFound());
					resultSet.setSearchTime(queryResponse.getElapsedTime());
					resultSet.setSpellcheck(queryResponse.getSpellCheckResponse());
				} catch (SolrServerException e) {
					log.severe("SolrServerException: " + e.getMessage());
					resultSet = null;
					throw new SolrTypeException(e, ProblemType.MALFORMED_QUERY);
				} catch (SolrException e) {
					log.severe("SolrException: " + e.getMessage());
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

	@Override
	public List<Term> suggestions(String query, int pageSize)
			throws SolrTypeException {
		return suggestions(query, pageSize, null);
	}
	
	@Override
	public List<Count> createCollections(String facetFieldName, String queryString, String... refinements)
			throws SolrTypeException {
		Query query = new Query(queryString).setParameter("rows", "0").setParameter("facet", "true")
				.setRefinements(refinements).setParameter("facet.mincount", "1").setParameter("facet.limit", "750")
				.setAllowSpellcheck(false);
		final ResultSet<BriefBean> response = search(BriefBean.class, query);
		for (FacetField facetField : response.getFacetFields()) {
			if (facetField.getName().equalsIgnoreCase(facetFieldName)) {
				return facetField.getValues();
			}
		}
		return new ArrayList<FacetField.Count>();
	}

	@Override
	public Map<String, Integer> seeAlso(List<String> queries) {
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery("*:*");
		solrQuery.setRows(0);
		solrQuery.setFacet(true);
		solrQuery.setTimeAllowed(TIME_ALLOWED);
		for (String query : queries) {
			solrQuery.addFacetQuery(query);
		}
		QueryResponse response;
		Map<String, Integer> seeAlso = null;
		try {
			response = solrServer.query(solrQuery);
			log.fine(solrQuery.toString());
			logTime("seeAlso", response.getElapsedTime());
			seeAlso = response.getFacetQuery();
		} catch (SolrServerException e) {
			log.severe("SolrServerException: " + e.getMessage() + " for query " + solrQuery.toString());
			e.printStackTrace();
		} catch (Exception e) {
			log.severe("Exception: " + e.getClass().getCanonicalName() + " " + e.getMessage() + " for query " 
					+ solrQuery.toString());
			e.printStackTrace();
		}

		return seeAlso;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IdBean> ResultSet<T> sitemap(Class<T> beanInterface, Query query) throws SolrTypeException {
		
		ResultSet<T> resultSet = new ResultSet<T>();
		Class<? extends IdBeanImpl> beanClazz = SolrUtils.getImplementationClass(beanInterface);

		String[] refinements = query.getRefinements(true);
		if (SolrUtils.checkTypeFacet(refinements)) {
			SolrQuery solrQuery = new SolrQuery().setQuery(query.getQuery());

			if (refinements != null) {
				solrQuery.addFilterQuery(refinements);
			}

			solrQuery.setFacet(false);
			solrQuery.setRows(query.getPageSize());
			solrQuery.setStart(query.getStart());

			solrQuery.setSortField("COMPLETENESS", ORDER.desc);
			solrQuery.setSortField("score", ORDER.desc);
			solrQuery.setTimeAllowed(TIME_ALLOWED);
			// add extra parameters if any
			if (query.getParameters() != null) {
				Map<String, String> parameters = query.getParameters();
				for (String key : parameters.keySet()) {
					solrQuery.setParam(key, parameters.get(key));
				}
			}

			try {
				log.fine("Solr query is: " + solrQuery);
				QueryResponse queryResponse = solrServer.query(solrQuery);
				logTime("search", queryResponse.getElapsedTime());

				resultSet.setResults((List<T>) queryResponse.getBeans(beanClazz));
				resultSet.setResultSize(queryResponse.getResults().getNumFound());
				resultSet.setSearchTime(queryResponse.getElapsedTime());
			} catch (SolrServerException e) {
				log.severe("SolrServerException: " + e.getMessage());
				throw new SolrTypeException(e, ProblemType.MALFORMED_QUERY);
			} catch (SolrException e) {
				log.severe("SolrException: " + e.getMessage());
				throw new SolrTypeException(e, ProblemType.MALFORMED_QUERY);
			}
		}



		return resultSet;
	}

	/**
	 * Get Suggestions from Solr Suggester
	 * 
	 * @param query
	 *            The query term
	 * @param field
	 *            The field to query on
	 * @param rHandler
	 *            The ReqestHandler to use
	 * @return A list of Terms for the specific term from the SolrSuggester
	 */
	private List<Term> getSuggestions(String query, String field, String rHandler) {
		List<Term> results = new ArrayList<Term>();
		try {
			ModifiableSolrParams params = new ModifiableSolrParams();
			params.set("qt", "/" + rHandler);
			params.set("q", field + ":" + query);
			params.set("rows", 0);
			params.set("timeAllowed", TIME_ALLOWED);

			// get the query response
			QueryResponse qResp = solrServer.query(params);
			SpellCheckResponse spResponse = qResp.getSpellCheckResponse();
			//if the suggestions are not empty and there are collated results
			if (!spResponse.getSuggestions().isEmpty()
					&& spResponse.getCollatedResults() != null) {
				for (Collation collation : spResponse.getCollatedResults()) {
					StringBuilder termResult = new StringBuilder();
					for (Correction cor : collation.getMisspellingsAndCorrections()) {
						//pickup the corrections, remove duplicates
						String[] terms = cor.getCorrection().trim().replaceAll("  ", " ").split(" ");
						for (String term : terms) {
							if (StringUtils.isBlank(term)) {
								continue;
							}
							// termResult.
							if (!StringUtils.contains(termResult.toString(), term)) {
								termResult.append(term + " ");
							}
						}
					}
					//return the term, the number of hits for each collation and the field that it should be mapped to
					Term term = new Term(termResult.toString().trim(),
							collation.getNumberOfHits(),
							SuggestionTitle.getMappedTitle(field),SolrUtils.escapeFacet(field, termResult.toString()));
					results.add(term);
				}
			}
		} catch (SolrServerException e) {
			log.severe("Exception :" + e.getMessage());
		}

		return results;
	}

	/**
	 * Get the suggestions 
	 */
	@Override
	public List<Term> suggestions(String query, int pageSize, String field) {
		log.fine(String.format("%s, %d, %s", query, pageSize, field));
		List<Term> results = new ArrayList<Term>();
		long start = new Date().getTime();
		total.put(query, 0l);
		// if the fiels is null check on all fields else on the requested field
		if (StringUtils.isBlank(field) || !SPELL_FIELDS.contains(field)) {
			results.addAll(getSuggestions(query, "title", "suggestTitle"));
			results.addAll(getSuggestions(query, "who", "suggestWho"));
			results.addAll(getSuggestions(query, "what", "suggestWhat"));
			results.addAll(getSuggestions(query, "where", "suggestWhere"));
			results.addAll(getSuggestions(query, "when", "suggestWhen"));
		} else if (StringUtils.equals(field, SuggestionTitle.TITLE.title)) {
			results.addAll(getSuggestions(query, field, "suggestTitle"));
		} else if (StringUtils.equals(field, SuggestionTitle.PERSON.title)) {
			results.addAll(getSuggestions(query, field, "suggestWho"));
		} else if (StringUtils.equals(field, SuggestionTitle.SUBJECT.title)) {
			results.addAll(getSuggestions(query, field, "suggestWhat"));
		} else if (StringUtils.equals(field, SuggestionTitle.PLACE.title)) {
			results.addAll(getSuggestions(query, field, "suggestWhere"));
		} else if (StringUtils.equals(field, SuggestionTitle.DATE.title)) {
			results.addAll(getSuggestions(query, field, "suggestWhen"));
		}

		// Sort the results by number of hits
		Collections.sort(results);
		logTime("suggestions", (new Date().getTime() - start));
		total.remove(query);
		
		log.fine(String.format("Returned %d results in %d ms",
				results.size() > pageSize ? pageSize : results.size(),
				new Date().getTime() - start));
		return results.size() > pageSize ? results.subList(0, pageSize)
				: results;
	}

	public void setSolrServer(SolrServer solrServer) {
		this.solrServer = setServer(solrServer);
	}

	private SolrServer setServer(SolrServer solrServer) {
		
		if (solrServer instanceof HttpSolrServer) {
			HttpSolrServer server = new HttpSolrServer(
					((HttpSolrServer) solrServer).getBaseURL());
			AbstractHttpClient client = (AbstractHttpClient) server
			        .getHttpClient();
			client.addRequestInterceptor(new PreEmptiveBasicAuthenticator(
			        username, password));
		return server;
		} else {
			return solrServer;
		}
	}

	private enum SuggestionTitle {
		TITLE("title", "Title"),
		DATE("when", "Time/Period"),
		PLACE("where", "Place"),
		PERSON("who", "Creator"),
		SUBJECT("what", "Subject");

		String title;
		String mappedTitle;

		private SuggestionTitle(String title, String mappedTitle) {
			this.title = title;
			this.mappedTitle = mappedTitle;
		}

		public static String getMappedTitle(String title) {
			for (SuggestionTitle st : SuggestionTitle.values()) {
				if (StringUtils.equals(title, st.title)) {
					return st.mappedTitle;
				}
			}
			return null;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public Date getLastSolrUpdate() throws SolrServerException, IOException {
		long t0 = new Date().getTime();
		NamedList<Object> namedList = solrServer.request(new LukeRequest());
		NamedList<Object> index = (NamedList<Object>) namedList.get("index");
		log.info("spent: " + (new Date().getTime() - t0));
		return (Date) index.get("lastModified");
	}

	public void logTime(String type, long time) {
		log.fine(String.format("elapsed time (%s): %d", type, time));
	}



}

class PreEmptiveBasicAuthenticator implements HttpRequestInterceptor {
	  private final UsernamePasswordCredentials credentials;

	  public PreEmptiveBasicAuthenticator(String user, String pass) {
	    credentials = new UsernamePasswordCredentials(user, pass);
	  }

	  @Override
	  public void process(HttpRequest request, HttpContext context)
	      throws HttpException, IOException {
	    request.addHeader(BasicScheme.authenticate(credentials,"US-ASCII",false));
	  }
	}
