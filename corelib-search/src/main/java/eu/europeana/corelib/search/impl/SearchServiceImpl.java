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
package eu.europeana.corelib.search.impl;

import eu.europeana.corelib.definitions.edm.beans.BriefBean;
import eu.europeana.corelib.definitions.edm.beans.FullBean;
import eu.europeana.corelib.definitions.edm.beans.IdBean;
import eu.europeana.corelib.definitions.edm.entity.Aggregation;
import eu.europeana.corelib.definitions.solr.model.Query;
import eu.europeana.corelib.definitions.solr.model.QuerySort;
import eu.europeana.corelib.definitions.solr.model.Term;
import eu.europeana.corelib.edm.exceptions.BadDataException;
import eu.europeana.corelib.edm.exceptions.MongoDBException;
import eu.europeana.corelib.edm.exceptions.MongoRuntimeException;
import eu.europeana.corelib.edm.exceptions.SolrTypeException;
import eu.europeana.corelib.mongo.server.EdmMongoServer;
import eu.europeana.corelib.search.SearchService;
import eu.europeana.corelib.search.model.ResultSet;
import eu.europeana.corelib.search.query.MoreLikeThis;
import eu.europeana.corelib.search.utils.SearchUtils;
import eu.europeana.corelib.solr.bean.impl.ApiBeanImpl;
import eu.europeana.corelib.solr.bean.impl.BriefBeanImpl;
import eu.europeana.corelib.solr.bean.impl.FullBeanImpl;
import eu.europeana.corelib.solr.bean.impl.IdBeanImpl;
import eu.europeana.corelib.solr.bean.impl.RichBeanImpl;
import eu.europeana.corelib.solr.entity.WebResourceImpl;
import eu.europeana.corelib.tools.lookuptable.EuropeanaId;
import eu.europeana.corelib.tools.lookuptable.EuropeanaIdMongoServer;
import eu.europeana.corelib.utils.EuropeanaUriUtils;
import eu.europeana.corelib.web.exception.ProblemType;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.protocol.HttpContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrRequest;
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
import org.apache.solr.client.solrj.util.ClientUtils;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrException;
import org.apache.solr.common.params.CursorMarkParams;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.apache.solr.common.util.NamedList;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Lookup record information from Solr or Mongo
 * @author Yorgos.Mamakis@ kb.nl
 * @see eu.europeana.corelib.search.SearchService
 */
public class SearchServiceImpl implements SearchService {

    /**
     * Default number of documents retrieved by MoreLikeThis
     */
    @Deprecated
    private static final int DEFAULT_MLT_COUNT = 10;
    private static final String UNION_FACETS_FORMAT = "'{'!ex={0}'}'{0}";
    /**
     * Number of milliseconds before the query is aborted by SOLR
     */
    private static final int TIME_ALLOWED = 30_000;
    /**
     * The list of possible field input for spelling suggestions
     */
    private static final List<String> SPELL_FIELDS = Arrays.asList("who",
            "what", "where", "when", "title");
    private static final String RESOLVE_PREFIX = "http://www.europeana.eu/resolve/record";
    private static final String PORTAL_PREFIX = "http://www.europeana.eu/portal/record";

    private static final Logger LOG = LogManager.getLogger(SearchServiceImpl.class);

    @Resource(name = "corelib_solr_mongoServer")
    protected EdmMongoServer mongoServer;
    @Resource(name = "corelib_solr_mongoServer_id")
    protected EuropeanaIdMongoServer idServer;

    // provided by setSolrServer()
    private SolrServer solrServer;
    @Value("#{europeanaProperties['solr.facetLimit']}")
    private int facetLimit;
    @Value("#{europeanaProperties['solr.username']}")
    private String username;
    @Value("#{europeanaProperties['solr.password']}")
    private String password;
    @Value("#{europeanaProperties['solr.searchLimit']}")
    private int searchLimit;

    @Deprecated
    private String mltFields;

    // show solr query in output
    private boolean debug = false;


    @Override
    public FullBean findById(String collectionId, String recordId, boolean similarItems)
            throws MongoRuntimeException, MongoDBException {
        return findById(EuropeanaUriUtils.createEuropeanaId(collectionId, recordId), similarItems);
    }

    @Override
    public FullBean findById(String europeanaObjectId, boolean similarItems) throws MongoRuntimeException, MongoDBException {

        FullBean fullBean = mongoServer.getFullBean(europeanaObjectId);
        if (fullBean != null) {
            WebMetaInfo.injectWebMetaInfo(fullBean, mongoServer);

            if (similarItems) {
                try {
                    fullBean.setSimilarItems(findMoreLikeThis(europeanaObjectId));
                } catch (SolrServerException e) {
                    LOG.error("SolrServerException: {}", e.getMessage());
                }
            }

            if ((fullBean.getAggregations() != null && !fullBean.getAggregations().isEmpty())) {
                ((FullBeanImpl) fullBean).setAsParent();
                for (Aggregation agg : fullBean.getAggregations()) {
                    if (agg.getWebResources() != null && !agg.getWebResources().isEmpty()) {
                        for (WebResourceImpl wRes : (List<WebResourceImpl>) agg.getWebResources()) {
                            wRes.initAttributionSnippet();
                        }
                    }
                }
            }
        }
        return fullBean;
    }

    /**
     * @see SearchService#resolve(String, String, boolean)
     */
    @Override
    public FullBean resolve(String collectionId, String recordId, boolean similarItems) throws SolrTypeException {
        return resolve(EuropeanaUriUtils.createEuropeanaId(collectionId, recordId), similarItems);
    }

    /**
     * @see SearchService#resolve(String, boolean)
     */
    @Override
    public FullBean resolve(String europeanaObjectId, boolean similarItems)
            throws SolrTypeException {

        FullBean fullBean = resolveInternal(europeanaObjectId, similarItems);
        FullBean fullBeanNew = fullBean;
        if (fullBean != null) {
            while (fullBeanNew != null) {
                fullBeanNew = resolveInternal(fullBeanNew.getAbout(), similarItems);
                if (fullBeanNew != null) {
                    fullBean = fullBeanNew;
                }
            }
        }

        return fullBean;
    }

    /**
     * Retrieve bean via a single redirect, i.e. checks if a record has a newer Id and if so retrieves the bean via that newId.
     *
     * @param europeanaObjectId
     * @return
     * @throws SolrTypeException
     */
    private FullBean resolveInternal(String europeanaObjectId, boolean similarItems) throws SolrTypeException {
        mongoServer.setEuropeanaIdMongoServer(idServer);
        FullBean fullBean = mongoServer.resolve(europeanaObjectId);
        if (fullBean != null) {
            WebMetaInfo.injectWebMetaInfo(fullBean, mongoServer);
            if (similarItems) {
                try {
                    fullBean.setSimilarItems(findMoreLikeThis(fullBean.getAbout()));
                } catch (SolrServerException e) {
                    LOG.error("SolrServerException", e);
                }
            }
        }
        return fullBean;
    }

    /**
     * @see eu.europeana.corelib.search.SearchService#resolveId(String)
     */
    @Override
    public String resolveId(String europeanaObjectId) throws BadDataException {
        List<String> idsSeen = new ArrayList<>(); // used to detect circular references

        String lastId = resolveIdInternal(europeanaObjectId, idsSeen);
        String newId = lastId;
        while (newId != null) {
            newId = resolveIdInternal(newId, idsSeen);
            if (newId != null) {
                lastId = newId;
            }
        }
        return lastId;
    }

    /**
     * @see eu.europeana.corelib.search.SearchService#resolveId(String, String)
     */
    @Override
    public String resolveId(String collectionId, String recordId) throws BadDataException {
        return resolveId(EuropeanaUriUtils.createEuropeanaId(
                collectionId, recordId));
    }

    /**
     * Checks if there is a new id for the provided europeanaObjectId, using different variations of this id
     * Additionally this method checks if the new found id is an id that was resolved earlier in the chain (check for circular reference)
     * @param europeanaObjectId
     * @param europeanaObjectIdsSeen list with all europeanaObjectIds (without any prefixes) we've resolved so far.
     * @return the found newId of the record, or null if no newId was found,
     * @throws BadDataException if a circular id reference is found
     */
    private String resolveIdInternal(String europeanaObjectId, List<String> europeanaObjectIdsSeen) throws BadDataException {
        europeanaObjectIdsSeen.add(europeanaObjectId);

        List<String> idsToCheck = new ArrayList<>();
        idsToCheck.add(europeanaObjectId);
        idsToCheck.add(RESOLVE_PREFIX + europeanaObjectId);
        idsToCheck.add(PORTAL_PREFIX + europeanaObjectId);
        if (LOG.isDebugEnabled()) { LOG.debug("Trying to resolve ids {}", idsToCheck); }
        EuropeanaId newId = idServer.retrieveEuropeanaIdFromOld(idsToCheck);
        if (newId != null) {
            String result = newId.getNewId();

            //TODO For now update time is disabled because it's rather expensive operation and we need to think of a better approach
            // idServer.updateTime(newId.getNewId(), PORTAL_PREFIX
            //         + europeanaObjectId);

            // check for circular references
            if (result != null && europeanaObjectIdsSeen.contains(result)) {
                europeanaObjectIdsSeen.add(result);
                String message = "Cannot resolve record-id because a circular id reference was found! "+europeanaObjectIdsSeen;
                LOG.error(message);
                throw new BadDataException(ProblemType.INCONSISTENT_DATA, message +" The data team will be notified of the problem.");
            }
            return result;
        }
        return null;
    }

    @Override
    @Deprecated
    public List<BriefBean> findMoreLikeThis(String europeanaObjectId)
            throws SolrServerException {
        return findMoreLikeThis(europeanaObjectId, DEFAULT_MLT_COUNT);
    }

    @Override
    @Deprecated
    public List<BriefBean> findMoreLikeThis(String europeanaObjectId, int count)
            throws SolrServerException {
        String query = "europeana_id:\"" + europeanaObjectId + "\"";

        SolrQuery solrQuery = new SolrQuery().setQuery(query);
        // solrQuery.setQueryType(QueryType.ADVANCED.toString());
        solrQuery.set("mlt", true);

        if (mltFields == null) {
            List<String> fields = new ArrayList<>();
            for (MoreLikeThis mltField : MoreLikeThis.values()) {
                fields.add(mltField.toString());
            }
            mltFields = ClientUtils.escapeQueryChars(StringUtils.join(fields, ","));
        }
        solrQuery.set("mlt.fl", mltFields);
        solrQuery.set("mlt.mintf", 1);
        solrQuery.set("mlt.match.include", "false");
        solrQuery.set("mlt.count", count);
        solrQuery.set("rows", 1);
        solrQuery.setTimeAllowed(TIME_ALLOWED);
        if (LOG.isDebugEnabled()) {
            LOG.debug(solrQuery.toString());
        }

        QueryResponse response = solrServer.query(solrQuery, SolrRequest.METHOD.POST);

        @SuppressWarnings("unchecked")
        NamedList<Object> moreLikeThisList = (NamedList<Object>) response
                .getResponse().get("moreLikeThis");
        List<BriefBean> beans = new ArrayList<>();
        if (moreLikeThisList.size() > 0) {
            @SuppressWarnings("unchecked")
            List<SolrDocument> docs = (List<SolrDocument>) moreLikeThisList
                    .getVal(0);
            for (SolrDocument doc : docs) {
                beans.add(solrServer.getBinder().getBean(BriefBeanImpl.class,
                        doc));
            }
        }
        return beans;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends IdBean> ResultSet<T> search(Class<T> beanInterface, Query query, boolean debug) throws SolrTypeException {
        this.debug = debug;
        return search(beanInterface, query);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends IdBean> ResultSet<T> search(Class<T> beanInterface, Query query) throws SolrTypeException {
        if (query.getStart() != null && (query.getStart() + query.getPageSize() > searchLimit)) {
            throw new SolrTypeException(ProblemType.PAGINATION_LIMIT_REACHED);
        }
        ResultSet<T> resultSet = new ResultSet<>();
        Class<? extends IdBeanImpl> beanClazz = SearchUtils
                .getImplementationClass(beanInterface);

        if (isValidBeanClass(beanClazz)) {
            String[] refinements = query.getRefinements(true);
            if (SearchUtils.checkTypeFacet(refinements)) {
                SolrQuery solrQuery = new SolrQuery().setQuery(query.getQuery(true));

                if (refinements != null) { // TODO add length 0 check!!
                    solrQuery.addFilterQuery(refinements);
                }

                solrQuery.setRows(query.getPageSize());
                solrQuery.setStart(query.getStart());

                setSortAndCursor(query, resultSet, solrQuery);

                // add extra parameters if any
                if (query.getParameterMap() != null) {
                    Map<String, String> parameters = query.getParameterMap();
                    for (String key : parameters.keySet()) {
                        solrQuery.setParam(key, parameters.get(key));
                    }
                }

                // facets are optional
                if (query.areFacetsAllowed()) {
                    solrQuery.setFacet(true);
                    List<String> filteredFacets = query.getFacetsUsedInRefinements();
                    boolean hasFacetRefinements = (filteredFacets != null && !filteredFacets.isEmpty());

                    for (String facetToAdd : query.getSolrFacets()) {
                        if (query.doProduceFacetUnion()) {
                            if (hasFacetRefinements
                                    && filteredFacets.contains(facetToAdd)) {
                                facetToAdd = MessageFormat.format(
                                        UNION_FACETS_FORMAT, facetToAdd);
                            }
                        }
                        solrQuery.addFacetField(facetToAdd);
                    }
                    solrQuery.setFacetLimit(facetLimit);
                }

                // spellcheck is optional
                if (query.isSpellcheckAllowed()) {
                    if (solrQuery.getStart() == null || solrQuery.getStart() <= 1) {
                        solrQuery.setParam("spellcheck", "on");
                        solrQuery.setParam("spellcheck.collate", "true");
                        solrQuery.setParam("spellcheck.extendedResults", "true");
                        solrQuery.setParam("spellcheck.onlyMorePopular", "true");
                        solrQuery.setParam("spellcheck.q", query.getQuery());
                    }
                }
                // change this to *isblank / empty
                if (query.getQueryFacets() != null) {
                    for (String facetQuery : query.getQueryFacets()) {
                        solrQuery.addFacetQuery(facetQuery);
                    }
                }

                try {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Solr query is: " + solrQuery);
                    }
                    query.setExecutedQuery(solrQuery.toString());

                    QueryResponse queryResponse = solrServer.query(solrQuery, SolrRequest.METHOD.POST);


                    resultSet.setResults((List<T>) queryResponse.getBeans(beanClazz));
                    resultSet.setFacetFields(queryResponse.getFacetFields());
                    resultSet.setResultSize(queryResponse.getResults().getNumFound());
                    resultSet.setSearchTime(queryResponse.getElapsedTime());
                    resultSet.setSpellcheck(queryResponse.getSpellCheckResponse());
                    resultSet.setNextCursorMark(queryResponse.getNextCursorMark());
                    if (debug) {
                        resultSet.setSolrQueryString(query.getExecutedQuery());
                    }
                    if (queryResponse.getFacetQuery() != null) {
                        resultSet.setQueryFacets(queryResponse.getFacetQuery());
                    }
                } catch (SolrServerException e) {
                    LOG.error("SolrServerException: " + e.getMessage() + " The query was: " + solrQuery);
                    if (StringUtils.contains(e.getCause().toString(), "Collection")){
                        throw new SolrTypeException(e, ProblemType.INVALID_THEME);
                    } else {
                        throw new SolrTypeException(e, ProblemType.MALFORMED_QUERY);
                    }
                } catch (SolrException e) {
                    LOG.error("SolrException: " + e.getMessage() + " The query was: " + solrQuery);
                    if (e.getMessage().toLowerCase().contains("cursorMark".toLowerCase())){
                        throw new SolrTypeException(e, ProblemType.UNABLE_TO_PARSE_CURSORMARK);
                    } else{
                        throw new SolrTypeException(e, ProblemType.MALFORMED_QUERY);
                    }
                }

            } else {
                throw new SolrTypeException(ProblemType.INVALIDARGUMENTS);
            }

        } else {
            throw new SolrTypeException(ProblemType.INVALIDCLASS, "Bean class: " + beanClazz);
        }
        return resultSet;
    }

    private <T extends IdBean> void setSortAndCursor(Query query, ResultSet<T> resultSet, SolrQuery solrQuery) {
        boolean defaultSort = query.getSorts().size() == 0;
        if (defaultSort) {
            solrQuery.setSort("has_media", ORDER.desc);
            solrQuery.addSort("score", ORDER.desc);
            solrQuery.addSort("timestamp_update", ORDER.desc);
            // completeness is added last because many records have incorrect value 0
            solrQuery.addSort("europeana_completeness", ORDER.desc);
        } else {
            // User set sort
            solrQuery.clearSorts();
            for (QuerySort userSort : query.getSorts()) {
                solrQuery.addSort(userSort.getSortField(), (userSort.getSortOrder() == QuerySort.ORDER_ASC ? ORDER.asc : ORDER.desc));
            }
        }

        // Check if user defined a cursor (because we need to adjust sort based on it's requirements)
        if (query.getCurrentCursorMark() != null) {
            solrQuery.set(CursorMarkParams.CURSOR_MARK_PARAM, query.getCurrentCursorMark());

            if (defaultSort) {
                // There's a bug in Solr and we have to remove 'score' for default sorting (see also EA-1087)
                solrQuery.removeSort("score");
                // Cursor-based pagination requires a unique key field for first cursor, so we add europeana_id if necessary
                if (!solrQuery.getSortField().contains("europeana_id")) {
                    solrQuery.addSort("europeana_id", ORDER.asc);
                }
            }

        } else {
            // TimeAllowed and cursormark are not allowed together in a query
            solrQuery.setTimeAllowed(TIME_ALLOWED);
        }

        resultSet.setSortField(solrQuery.getSortField());
        resultSet.setCurrentCursorMark(query.getCurrentCursorMark());
    }

    @Deprecated // since april 2018, we don't change sorting beased on type of query anymore
    private boolean isFieldQuery(String query) {
        //TODO fix
        String subquery = StringUtils.substringBefore(query, "filter_tags");
        String queryWithoutTags = StringUtils.substringBefore(subquery, "facet_tags");
        return !(StringUtils.contains(queryWithoutTags, "who:") || StringUtils.contains(queryWithoutTags, "what:")
                || StringUtils.contains(queryWithoutTags, "where:") || StringUtils.contains(queryWithoutTags, "when:")
                || StringUtils.contains(queryWithoutTags, "title:")) && StringUtils.contains(queryWithoutTags, ":") && !(StringUtils.contains(queryWithoutTags.trim(), " ") && StringUtils.contains(queryWithoutTags.trim(), "\""));
    }

    /**
     * Flag whether the bean class is one of the allowable ones.
     */
    private boolean isValidBeanClass(Class<? extends IdBeanImpl> beanClazz) {
        return beanClazz == BriefBeanImpl.class
                || beanClazz == ApiBeanImpl.class
                || beanClazz == RichBeanImpl.class;
    }

    @Override
    public List<Term> suggestions(String query, int pageSize) throws SolrTypeException {
        return suggestions(query, pageSize, null);
    }

    @Override
    public List<Count> createCollections(String facetFieldName, String queryString, String... refinements) throws SolrTypeException {

        Query query = new Query(queryString).setParameter("rows", "0")
                .setParameter("facet", "true").setRefinements(refinements)
                .setParameter("facet.mincount", "1")
                .setParameter("facet.limit", "750").setSpellcheckAllowed(false);
        query.setSolrFacet(facetFieldName);

        final ResultSet<BriefBean> response = search(BriefBean.class, query);
        for (FacetField facetField : response.getFacetFields()) {
            if (facetField.getName().equalsIgnoreCase(facetFieldName)) {
                return facetField.getValues();
            }
        }
        return new ArrayList<>();
    }

    @Override
    public Map<String, Integer> seeAlso(List<String> queries) {
        return queryFacetSearch("*:*", null, queries);
    }

    @Override
    public Map<String, Integer> queryFacetSearch(String query, String[] qf,
                                                 List<String> queries) {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery(query);
        if (qf != null) {
            solrQuery.addFilterQuery(qf);
        }
        solrQuery.setRows(0);
        solrQuery.setFacet(true);
        solrQuery.setTimeAllowed(TIME_ALLOWED);
        for (String queryFacet : queries) {
            solrQuery.addFacetQuery(queryFacet);
        }
        QueryResponse response;
        Map<String, Integer> queryFacets = null;
        try {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Solr query is: " + solrQuery.toString());
            }
            response = solrServer.query(solrQuery, SolrRequest.METHOD.POST);
            queryFacets = response.getFacetQuery();
        } catch (SolrServerException e) {
            LOG.error("SolrServerException: " + e.getMessage() + " for query "
                    + solrQuery.toString(), e);
        } catch (Exception e) {
            LOG.error("Exception: " + e.getClass().getCanonicalName() + " "
                    + e.getMessage() + " for query " + solrQuery.toString(), e);
        }

        return queryFacets;
    }

    /**
     * Get Suggestions from Solr Suggester
     *
     * @param query    The query term
     * @param field    The field to query on
     * @param rHandler The ReqestHandler to use
     * @return A list of Terms for the specific term from the SolrSuggester
     */
    private List<Term> getSuggestions(String query, String field, String rHandler) {
        List<Term> results = new ArrayList<>();
        try {
            ModifiableSolrParams params = new ModifiableSolrParams();
            params.set("qt", "/" + rHandler);
            params.set("q", field + ":" + query);
            params.set("rows", 0);
            params.set("timeAllowed", TIME_ALLOWED);

            // get the query response
            QueryResponse queryResponse = solrServer.query(params, SolrRequest.METHOD.POST);
            SpellCheckResponse spellcheckResponse = queryResponse
                    .getSpellCheckResponse();
            // if the suggestions are not empty and there are collated results
            if (spellcheckResponse != null
                    && !spellcheckResponse.getSuggestions().isEmpty()
                    && spellcheckResponse.getCollatedResults() != null) {
                for (Collation collation : spellcheckResponse
                        .getCollatedResults()) {
                    StringBuilder termResult = new StringBuilder();
                    for (Correction cor : collation
                            .getMisspellingsAndCorrections()) {
                        // pickup the corrections, remove duplicates
                        String[] terms = cor.getCorrection().trim()
                                .replaceAll("  ", " ").split(" ");
                        for (String term : terms) {
                            if (StringUtils.isBlank(term)) {
                                continue;
                            }
                            // termResult.
                            if (!StringUtils.contains(termResult.toString(),
                                    term)) {
                                termResult.append(term).append(" ");
                            }
                        }
                    }
                    // return the term, the number of hits for each collation
                    // and the field that it should be mapped to
                    Term term = new Term(termResult.toString().trim(),
                            collation.getNumberOfHits(),
                            SuggestionTitle.getMappedTitle(field),
                            SearchUtils.escapeFacet(field,
                                    termResult.toString()));
                    results.add(term);
                }
            }
        } catch (SolrServerException e) {
            LOG.error("Exception :" + e.getMessage());
        }

        return results;
    }

    /**
     * Get the suggestions
     */
    @Override
    public List<Term> suggestions(String query, int pageSize, String field) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(String.format("%s, %d, %s", query, pageSize, field));
        }
        List<Term> results = new ArrayList<>();
        long start = new Date().getTime();
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

        if (LOG.isDebugEnabled()) {
            LOG.debug(String.format("Returned %d results in %d ms",
                    results.size() > pageSize ? pageSize : results.size(),
                    new Date().getTime() - start));
        }
        return results.size() > pageSize ? results.subList(0, pageSize)
                : results;
    }

    public void setSolrServer(SolrServer solrServer) {
        this.solrServer = setServer(solrServer);
    }

    private SolrServer setServer(SolrServer solrServer) {
        if (solrServer instanceof HttpSolrServer) {
            HttpSolrServer server = new HttpSolrServer(((HttpSolrServer) solrServer).getBaseURL());
            AbstractHttpClient client = (AbstractHttpClient) server.getHttpClient();
            client.addRequestInterceptor(new PreEmptiveBasicAuthenticator(username, password));
            return server;
        } else {
            return solrServer;
        }
    }

    private enum SuggestionTitle {
        TITLE("title", "Title"), DATE("when", "Time/Period"), PLACE("where",
                "Place"), PERSON("who", "Creator"), SUBJECT("what", "Subject");

        private String title;
        private String mappedTitle;

        SuggestionTitle(String title, String mappedTitle) {
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
        if (LOG.isInfoEnabled()) {
            LOG.info("spent: " + (new Date().getTime() - t0));
        }
        return (Date) index.get("lastModified");
    }

    public void logTime(String type, long time) {
        if (LOG.isDebugEnabled()) {
            LOG.debug(String.format("elapsed time (%s): %d", type, time));
        }
    }

    private static class PreEmptiveBasicAuthenticator implements HttpRequestInterceptor {
        private final UsernamePasswordCredentials credentials;

        public PreEmptiveBasicAuthenticator(String user, String pass) {
            credentials = new UsernamePasswordCredentials(user, pass);
        }

        @Override
        public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
            request.addHeader(BasicScheme.authenticate(credentials, "US-ASCII", false));
        }
    }
}

