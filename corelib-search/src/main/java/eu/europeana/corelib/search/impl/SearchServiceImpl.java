package eu.europeana.corelib.search.impl;

import eu.europeana.corelib.definitions.edm.beans.BriefBean;
import eu.europeana.corelib.definitions.edm.beans.FullBean;
import eu.europeana.corelib.definitions.edm.beans.IdBean;
import eu.europeana.corelib.definitions.edm.entity.Aggregation;
import eu.europeana.corelib.definitions.solr.model.Query;
import eu.europeana.corelib.definitions.solr.model.QuerySort;
import eu.europeana.corelib.edm.exceptions.SolrIOException;
import eu.europeana.corelib.edm.exceptions.SolrQueryException;
import eu.europeana.corelib.edm.exceptions.SolrTypeException;
import eu.europeana.corelib.mongo.server.EdmMongoServer;
import eu.europeana.corelib.search.SearchService;
import eu.europeana.corelib.search.model.ResultSet;
import eu.europeana.corelib.search.utils.SearchUtils;
import eu.europeana.corelib.solr.bean.impl.*;
import eu.europeana.corelib.solr.entity.WebResourceImpl;
import eu.europeana.corelib.utils.EuropeanaUriUtils;
import eu.europeana.corelib.web.exception.EuropeanaException;
import eu.europeana.corelib.web.exception.ProblemType;
import eu.europeana.metis.mongo.RecordRedirect;
import eu.europeana.metis.mongo.RecordRedirectDao;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.protocol.HttpContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.LukeRequest;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrException;
import org.apache.solr.common.params.CursorMarkParams;
import org.apache.solr.common.util.NamedList;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;

/**
 * Lookup record information from Solr or Mongo
 * @author Yorgos.Mamakis@ kb.nl
 * @author
 * @see eu.europeana.corelib.search.SearchService
 */
public class SearchServiceImpl implements SearchService {

    private static final String UNION_FACETS_FORMAT = "'{'!ex={0}'}'{0}";
    /**
     * Number of milliseconds before the query is aborted by SOLR
     */
    private static final int TIME_ALLOWED       = 30_000;
    private static final String EUROPEANA_ID    = "europeana_id";
    private static final Logger LOG             = LogManager.getLogger(SearchServiceImpl.class);

    @Resource(name = "corelib_solr_mongoServer")
    protected EdmMongoServer mongoServer;

    @Resource(name = "metis_redirect_mongo")
    protected RecordRedirectDao redirectDao;

    // provided by setSolrClient method via xml-beans
    private SolrClient solrClient;

    @Value("#{europeanaProperties['solr.facetLimit']}")
    private int facetLimit;
    @Value("#{europeanaProperties['solr.username']}")
    private String username;
    @Value("#{europeanaProperties['solr.password']}")
    private String password;
    @Value("#{europeanaProperties['solr.searchLimit']}")
    private int searchLimit;

    @Value("#{europeanaProperties['manifest.add.url']}")
    private Boolean manifestAddUrl;

    @Value("#{europeanaProperties['api2.baseUrl']}")
    private String api2BaseUrl;

    private boolean debug = false;

    /**
     * @see eu.europeana.corelib.search.SearchService#findById(String, String) 
     */
    @Override
    public FullBean findById(String collectionId, String recordId) throws EuropeanaException {
        return findById(EuropeanaUriUtils.createEuropeanaId(collectionId, recordId));
    }

    /**
     * @see eu.europeana.corelib.search.SearchService#findById(String)
     */
    @Override
    public FullBean findById(String europeanaId) throws EuropeanaException {
        FullBean fullBean = fetchFullBean(europeanaId);
        if (fullBean != null) {
            return processFullBean(fullBean);
        } else {
            return null;
        }
    }

    /**
     * @see eu.europeana.corelib.search.SearchService#fetchFullBean(String) 
     * split up fetching and processing the Fullbean to facilitate improving performance for HTTP caching
     * (e.g. eTag matching)
     */
    @Override
    public FullBean fetchFullBean(String europeanaId) throws EuropeanaException {
        long   startTime = System.currentTimeMillis();
        FullBean fullBean = mongoServer.getFullBean(europeanaId);
        if (LOG.isDebugEnabled()) {
            LOG.debug("SearchService fetch FullBean with EuropeanaID {} took {} ms", europeanaId,
                      (System.currentTimeMillis() - startTime));
        }

        if (Objects.isNull(fullBean)) {
            startTime = System.currentTimeMillis();
            String newId = resolve(europeanaId);

            if (LOG.isDebugEnabled()) {
                LOG.debug("SearchService resolving EuropeanaID {} to {} took {} ms", europeanaId,
                          newId,
                          (System.currentTimeMillis() - startTime));
            }
            // (comment from ObjectController) 2017-07-06 code PE: inserted as temp workaround until we resolve #662 (see also comment below)
            if (StringUtils.isNotBlank(newId)){
                startTime = System.currentTimeMillis();
                fullBean = mongoServer.getFullBean(newId);
                if (fullBean == null) {
                    LOG.warn("{} was redirected to {} but there is no such record!", europeanaId, newId);
                }
                if (LOG.isDebugEnabled()) {
                    LOG.debug("SearchService fetching FullBean with newid took {}", (System.currentTimeMillis() - startTime));
                }
            } else {
                LOG.debug("SearchService no redirection was found for EuropeanaID {}", europeanaId);
            }
        }
        return fullBean;
    }

    /**
     * @see eu.europeana.corelib.search.SearchService#resolve(String)
     * According to the Metis team, the getRecordRedirectsByOldId can only return one object because oldId is unique.
     * The method returns a List only because Morphia doesn't know about the uniqueness.
     * Even regardless of that, every RecordRedirect for a given oldId should contain the same newId; therefore it
     * is safe to return redirects.get(0).getNewId()
     */
    @Override
    public String resolve(String europeanaId) {
        List<RecordRedirect> redirects = redirectDao.getRecordRedirectsByOldId(europeanaId);
        if (redirects.isEmpty()){
            LOG.debug("SearchService no redirection was found for EuropeanaID {}", europeanaId);
            return null;
        } else {
            return redirects.get(0).getNewId();
        }
    }

    /**
     * @see eu.europeana.corelib.search.SearchService#processFullBean(FullBean)
     */
    @Override
    public FullBean processFullBean(FullBean fullBean){
        WebMetaInfo.injectWebMetaInfoBatch(fullBean, mongoServer, manifestAddUrl, api2BaseUrl);
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
        return fullBean;
    }
    
    /**
     * @see eu.europeana.corelib.search.SearchService#search(Class, Query, boolean) 
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends IdBean> ResultSet<T> search(Class<T> beanInterface, Query query, boolean debug) throws EuropeanaException {
        this.debug = debug;
        return search(beanInterface, query);
    }

    /**
     * @see eu.europeana.corelib.search.SearchService#search(Class, Query)
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends IdBean> ResultSet<T> search(Class<T> beanInterface, Query query) throws EuropeanaException {

        if (query.getStart() != null && (query.getStart() + query.getPageSize() > searchLimit)) {
            throw new SolrQueryException(ProblemType.SEARCH_PAGE_LIMIT_REACHED,
                    "It is not possible to paginate beyond the first 1000 search results. Please use cursor-based pagination instead");
        }
        ResultSet<T> resultSet = new ResultSet<>();
        Class<? extends IdBeanImpl> beanClazz = SearchUtils
                .getImplementationClass(beanInterface);

        if (isValidBeanClass(beanClazz)) {
            String[] refinements = query.getRefinements(true);

            SolrQuery solrQuery = new SolrQuery().setQuery(query.getQuery(true));

            if (ArrayUtils.isNotEmpty(refinements)) {
                solrQuery.addFilterQuery(refinements);
            }

            solrQuery.setRows(query.getPageSize());
            solrQuery.setStart(query.getStart());

            setSortAndCursor(query, resultSet, solrQuery);

            // add extra parameters if any
            if (query.getParameterMap() != null) {
                Map<String, String> parameters = query.getParameterMap();
                for (Map.Entry<String, String> entry : parameters.entrySet()) {
                    solrQuery.setParam(entry.getKey(), entry.getValue());
                }
            }

            // facets are optional
            if (query.areFacetsAllowed()) {
                solrQuery.setFacet(true);
                List<String> filteredFacets = query.getFacetsUsedInRefinements();
                boolean hasFacetRefinements = (filteredFacets != null && !filteredFacets.isEmpty());

                for (String facetToAdd : query.getSolrFacets()) {
                    if (query.doProduceFacetUnion() && hasFacetRefinements && filteredFacets.contains(facetToAdd)) {
                        facetToAdd = MessageFormat.format(UNION_FACETS_FORMAT, facetToAdd);
                    }
                    solrQuery.addFacetField(facetToAdd);
                }
                solrQuery.setFacetLimit(facetLimit);
            }

            // spellcheck is optional
            if (query.isSpellcheckAllowed() && (solrQuery.getStart() == null || solrQuery.getStart() <= 1)) {
                solrQuery.setParam("spellcheck", "on");
                solrQuery.setParam("spellcheck.collate", "true");
                solrQuery.setParam("spellcheck.extendedResults", "true");
                solrQuery.setParam("spellcheck.onlyMorePopular", "true");
                solrQuery.setParam("spellcheck.q", query.getQuery());
            }
            // change this to *isblank / empty
            if (query.getQueryFacets() != null) {
                for (String facetQuery : query.getQueryFacets()) {
                    solrQuery.addFacetQuery(facetQuery);
                }
            }

            try {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Solr query is: {}", solrQuery);
                }
                query.setExecutedQuery(solrQuery.toString());

                QueryResponse queryResponse = solrClient.query(solrQuery, SolrRequest.METHOD.POST);


                resultSet.setResults((List<T>) queryResponse.getBeans(beanClazz));
                resultSet.setFacetFields(queryResponse.getFacetFields());
                if (query.areRangeFacetsRequested()) {
                    resultSet.setRangeFacets(queryResponse.getFacetRanges());
                }
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
                if (queryResponse.getHighlighting() != null) {
                    resultSet.setHighlighting(queryResponse.getHighlighting());
                }
            } catch (IOException e) {
                throw new SolrIOException(ProblemType.CANT_CONNECT_SOLR, e);
            } catch (SolrServerException e) {
                if (StringUtils.contains(e.getCause().toString(), "Collection")){
                    throw new SolrQueryException(ProblemType.SEARCH_THEME_UNKNOWN); // do not include cause error for known problems
                }
                throw new SolrQueryException(ProblemType.SEARCH_QUERY_INVALID, e);
            } catch (SolrException e) {
                String msg = e.getMessage().toLowerCase(Locale.GERMAN);
                if (msg.contains("cursormark")) {
                    throw new SolrQueryException(ProblemType.SEARCH_CURSORMARK_INVALID,
                            "Please make sure you encode the cursor value before sending it to the API."); // do not include cause error for known problems
                } else if (msg.contains("connect")) {
                    if (msg.contains("zookeeper")) {
                        throw new SolrIOException(ProblemType.CANT_CONNECT_SOLR, "Cannot connect to Zookeeper", e);
                    } else {
                        throw new SolrIOException(ProblemType.CANT_CONNECT_SOLR, "Cannot connect to Solr", e);
                    }
                }
                throw new SolrQueryException(ProblemType.SEARCH_QUERY_INVALID, e);
            }
        } else {
            // Programming error! A new profile/bean was introduced?
            throw new SolrTypeException(ProblemType.INVALIDCLASS, "Unknown bean class: " + beanClazz);
        }
        return resultSet;
    }

    private <T extends IdBean> void setSortAndCursor(Query query, ResultSet<T> resultSet, SolrQuery solrQuery) {
        boolean defaultSort = query.getSorts().isEmpty();
        if (defaultSort) {
            solrQuery.setSort("has_media", ORDER.desc);
            solrQuery.addSort("score", ORDER.desc);
            solrQuery.addSort("timestamp_update", ORDER.desc);
            // completeness is added last because many records have incorrect value 0
            solrQuery.addSort("europeana_completeness", ORDER.desc);
            solrQuery.addSort(EUROPEANA_ID, ORDER.asc);
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
                // There's a bug in Solr and we have to remove 'score' for default sorting (see also EA-1087 and EA-1364)
                solrQuery.removeSort("score");
                // Cursor-based pagination requires a unique key field for first cursor, so we add europeana_id if necessary
                if (!solrQuery.getSortField().contains(EUROPEANA_ID)) {
                    solrQuery.addSort(EUROPEANA_ID, ORDER.asc);
                }
            }
        } else {
            // TimeAllowed and cursormark are not allowed together in a query
            solrQuery.setTimeAllowed(TIME_ALLOWED);
        }

        resultSet.setSortField(solrQuery.getSortField());
        resultSet.setCurrentCursorMark(query.getCurrentCursorMark());
    }

    /**
     * Flag whether the bean class is one of the allowable ones.
     */
    private boolean isValidBeanClass(Class<? extends IdBeanImpl> beanClazz) {
        return beanClazz == BriefBeanImpl.class
               || beanClazz == ApiBeanImpl.class
               || beanClazz == RichBeanImpl.class;
    }

    /**
     * @see eu.europeana.corelib.search.SearchService#seeAlso(List) 
     */
    @Override
    public Map<String, Integer> seeAlso(List<String> queries) {
        return queryFacetSearch("*:*", null, queries);
    }

    /**
     * @see eu.europeana.corelib.search.SearchService#queryFacetSearch(String, String[], List) 
     */
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
                LOG.debug("Solr query is: {}", solrQuery);
            }
            response = solrClient.query(solrQuery, SolrRequest.METHOD.POST);
            queryFacets = response.getFacetQuery();
        } catch (SolrServerException e) {
            LOG.error("SolrServerException - query = {} ", solrQuery, e);
        } catch (IOException | RuntimeException e) {
            LOG.error("Exception - class = {}, query = {}", e.getClass().getCanonicalName(), solrQuery, e);
        }
        return queryFacets;
    }

    public void setSolrClient(SolrClient solrClient) {
        this.solrClient = setClient(solrClient);
    }

    /**
     * If it's not a cluster but a single solr server, we add authentication
     */
    private SolrClient setClient(SolrClient solrClient) {

        if (solrClient instanceof HttpSolrClient) {
            HttpSolrClient server = new HttpSolrClient(((HttpSolrClient) solrClient).getBaseURL());
            AbstractHttpClient client = (AbstractHttpClient) server.getHttpClient();
            client.addRequestInterceptor(new PreEmptiveBasicAuthenticator(username, password));
            return server;
        } else {
            return solrClient;
        }
    }

    /**
     * @see SearchService#getLastSolrUpdate() 
     */
    @Override
    @SuppressWarnings("unchecked")
    public Date getLastSolrUpdate() {
        long t0 = new Date().getTime();
        try {
            NamedList<Object> namedList = solrClient.request(new LukeRequest());
            NamedList<Object> index = (NamedList<Object>) namedList.get("index");
            if (LOG.isInfoEnabled()) {
                LOG.info("spent: {}", (new Date().getTime() - t0));
            }
            return (Date) index.get("lastModified");
        } catch (SolrServerException | IOException e) {
            LOG.error("Error querying solr", e);
        }
        return null;
    }

    private static class PreEmptiveBasicAuthenticator implements HttpRequestInterceptor {
        private final UsernamePasswordCredentials credentials;

        public PreEmptiveBasicAuthenticator(String user, String pass) {
            credentials = new UsernamePasswordCredentials(user, pass);
        }

        /**
         * @see HttpRequestInterceptor#process(HttpRequest, HttpContext) 
         */
        @Override
        public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
            request.addHeader(BasicScheme.authenticate(credentials, "US-ASCII", false));
        }
    }


    // ----------------------------------------------------------------- //
    // --> CODE BELOW THIS LINE IS DEPRECATED OR NOT USED ANY LONGER <-- //
    // ----------------------------------------------------------------- //

    @Deprecated
    @Override
    public List<Count> createCollections(String facetFieldName, String queryString, String... refinements) throws EuropeanaException {

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

}
