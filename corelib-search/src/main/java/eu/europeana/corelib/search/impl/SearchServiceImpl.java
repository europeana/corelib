package eu.europeana.corelib.search.impl;

import eu.europeana.corelib.definitions.edm.beans.IdBean;
import eu.europeana.corelib.definitions.solr.SolrFacetType;
import eu.europeana.corelib.definitions.solr.model.Query;
import eu.europeana.corelib.definitions.solr.model.QuerySort;
import eu.europeana.corelib.edm.exceptions.SolrIOException;
import eu.europeana.corelib.edm.exceptions.SolrQueryException;
import eu.europeana.corelib.edm.exceptions.SolrTypeException;
import eu.europeana.corelib.search.SearchService;
import eu.europeana.corelib.search.model.ResultSet;
import eu.europeana.corelib.search.utils.SearchUtils;
import eu.europeana.corelib.solr.bean.impl.ApiBeanImpl;
import eu.europeana.corelib.solr.bean.impl.BriefBeanImpl;
import eu.europeana.corelib.solr.bean.impl.IdBeanImpl;
import eu.europeana.corelib.solr.bean.impl.RichBeanImpl;
import eu.europeana.corelib.web.exception.EuropeanaException;
import eu.europeana.corelib.web.exception.ProblemType;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.LukeRequest;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrException;
import org.apache.solr.common.params.CursorMarkParams;
import org.apache.solr.common.util.NamedList;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;

/**
 * Search service that retrieves BriefBeans or APIBeans from Solr
 *
 * @author Yorgos.Mamakis@ kb.nl
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * @author Patrick Ehlert (major refactoring April 2020)
 * @see eu.europeana.corelib.search.SearchService
 */
public class SearchServiceImpl implements SearchService {

    private static final Logger LOG = LogManager.getLogger(SearchServiceImpl.class);

    private static final String UNION_FACETS_FORMAT = "'{'!ex={0}'}'{0}";
    private static final String EUROPEANA_ID = "europeana_id";

    /**
     * Number of milliseconds before the query is aborted by SOLR
     */
    private static final int TIME_ALLOWED = 30_000;

    @Value("#{europeanaProperties['solr.facetLimit']}")
    private int facetLimit;

    @Value("#{europeanaProperties['solr.searchLimit']}")
    private int searchLimit;


    /**
     * @see SearchService#search(SolrClient solrClient, Class, Query, boolean)
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends IdBean> ResultSet<T> search(SolrClient solrClient, Class<T> beanInterface, Query query,boolean divideRefinements) throws EuropeanaException {
        return search(solrClient, beanInterface, query, false,divideRefinements);
    }

    /**
     * @see SearchService#search(SolrClient solrClient, Class, Query)
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends IdBean> ResultSet<T> search(SolrClient solrClient,  Class<T> beanInterface, Query query, boolean debug,boolean divideRefinements ) throws EuropeanaException {

        if (query.getStart() != null && (query.getStart() + query.getPageSize() > searchLimit)) {
            throw new SolrQueryException(ProblemType.SEARCH_PAGE_LIMIT_REACHED,
                    "It is not possible to paginate beyond the first 1000 search results. Please use cursor-based pagination instead");
        }
        ResultSet<T> resultSet = new ResultSet<>();
        Class<? extends IdBeanImpl> beanClazz = SearchUtils
                .getImplementationClass(beanInterface);

        if (isValidBeanClass(beanClazz)) {
            String[] refinements = query.getRefinements(divideRefinements);

            SolrQuery solrQuery = new SolrQuery().setQuery(query.getQuery());

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
                if (StringUtils.containsIgnoreCase(e.getCause().toString(), "Timeout")){
                    throw new SolrQueryException(ProblemType.TIMEOUT_SOLR, e);
                }
                if (StringUtils.containsIgnoreCase(e.getCause().toString(), "Collection")){
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
        // EA-2996 workaround broken search on search_acceptance_publish collection
        // to be able to test distance search
        if (defaultSort) {
            solrQuery.addSort("score", ORDER.desc);
            solrQuery.addSort("contentTier", ORDER.desc);
            solrQuery.addSort("metadataTier", ORDER.desc);
            solrQuery.addSort("timestamp_update", ORDER.desc);
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
     * @see SearchService#getLastSolrUpdate(SolrClient solrClient)
     */
    @Override
    @SuppressWarnings("unchecked")
    public Date getLastSolrUpdate(SolrClient solrClient) throws EuropeanaException {
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

    @Override
    public Long getItemsLinkedToEntity(SolrClient solrClient, Query query) {
        SolrQuery solrQuery = new SolrQuery().setQuery(query.getQuery());
        solrQuery.setRows(query.getPageSize());
        try {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Solr query is: {}", solrQuery);
            }

            QueryResponse queryResponse = solrClient.query(solrQuery, SolrRequest.METHOD.POST);

            if (queryResponse.getResponse() != null) {
                SolrDocumentList response = (SolrDocumentList) queryResponse.getResponse().get("response");
                return response != null ? response.getNumFound() : 0;
            }
        } catch (SolrServerException | IOException e) {
            LOG.error("Error querying solr", e);
        }
        return 0L;
    }

    @Override
    public Map<String, Long> getFacet(SolrClient solrClient, Query query, SolrFacetType facetType) {
        SolrQuery solrQuery = new SolrQuery().setQuery(query.getQuery());
        solrQuery.setRows(0);
        solrQuery.setFacet(true);
        solrQuery.addFacetField(facetType.toString());

        try {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Solr query is: {}", solrQuery);
            }
            QueryResponse queryResponse = solrClient.query(solrQuery, SolrRequest.METHOD.POST);

            if (queryResponse.getResponse() != null && !queryResponse.getFacetFields().isEmpty()) {
                FacetField facetField = queryResponse.getFacetFields().get(0);
                Map<String, Long> valueCountMap = new HashMap<>();
                for (var count : facetField.getValues()) {
                    if (StringUtils.isNotEmpty(count.getName()) && count.getCount() > 0) {
                        valueCountMap.put(count.getName(), count.getCount());
                    }
                }
               return valueCountMap;
            }
        } catch (SolrServerException | IOException e) {
            LOG.error("Error querying solr", e);
        }
        return Collections.emptyMap();
    }
}
