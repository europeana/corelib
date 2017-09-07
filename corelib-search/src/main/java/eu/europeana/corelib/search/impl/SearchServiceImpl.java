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

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import eu.europeana.corelib.definitions.edm.beans.BriefBean;
import eu.europeana.corelib.definitions.edm.beans.FullBean;
import eu.europeana.corelib.definitions.edm.beans.IdBean;
import eu.europeana.corelib.definitions.edm.entity.Aggregation;
import eu.europeana.corelib.definitions.edm.entity.Proxy;
import eu.europeana.corelib.definitions.edm.entity.WebResource;
import eu.europeana.corelib.neo4j.exception.Neo4JException;
import eu.europeana.corelib.web.exception.ProblemType;
import eu.europeana.corelib.definitions.solr.model.Query;
import eu.europeana.corelib.definitions.solr.model.Term;
import eu.europeana.corelib.edm.exceptions.BadDataException;
import eu.europeana.corelib.edm.exceptions.MongoDBException;
import eu.europeana.corelib.edm.exceptions.MongoRuntimeException;
import eu.europeana.corelib.edm.exceptions.SolrTypeException;
import eu.europeana.corelib.edm.model.metainfo.WebResourceMetaInfoImpl;
import eu.europeana.corelib.mongo.server.EdmMongoServer;
import eu.europeana.corelib.neo4j.entity.CustomNode;
import eu.europeana.corelib.neo4j.entity.Neo4jBean;
import eu.europeana.corelib.neo4j.entity.Neo4jStructBean;
import eu.europeana.corelib.neo4j.entity.Node2Neo4jBeanConverter;
import eu.europeana.corelib.neo4j.server.Neo4jServer;
import eu.europeana.corelib.search.SearchService;
import eu.europeana.corelib.search.model.ResultSet;
import eu.europeana.corelib.search.query.MoreLikeThis;
import eu.europeana.corelib.search.utils.SearchUtils;
import eu.europeana.corelib.solr.bean.impl.*;
import eu.europeana.corelib.solr.entity.WebResourceImpl;
import eu.europeana.corelib.tools.lookuptable.EuropeanaId;
import eu.europeana.corelib.tools.lookuptable.EuropeanaIdMongoServer;
import eu.europeana.corelib.utils.EuropeanaUriUtils;
import eu.europeana.corelib.web.support.Configuration;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.protocol.HttpContext;
import org.apache.log4j.Logger;
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
import org.neo4j.graphdb.Node;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * @author Yorgos.Mamakis@ kb.nl
 * @see eu.europeana.corelib.search.SearchService
 */
public class SearchServiceImpl implements SearchService {

    private static final int DEFAULT_HIERARCHY_TIMEOUT = 8000;
    private static final int MAX_HIERARCHY_TIMEOUT = 20000;
    private static final int MIN_HIERARCHY_TIMEOUT = 400;

    /**
     * Default number of documents retrieved by MoreLikeThis
     */
    private static final int DEFAULT_MLT_COUNT = 10;
    private static final String UNION_FACETS_FORMAT = "'{'!ex={0}'}'{0}";
    /**
     * Number of milliseconds before the query is aborted by SOLR
     */
    private static final int TIME_ALLOWED = 30000;
    /**
     * The list of possible field input for spelling suggestions
     */
    private static final List<String> SPELL_FIELDS = Arrays.asList("who",
            "what", "where", "when", "title");
    private final static String RESOLVE_PREFIX = "http://www.europeana.eu/resolve/record";
    private final static String PORTAL_PREFIX = "http://www.europeana.eu/portal/record";
    private static final HashFunction hf = Hashing.md5();
    protected static Logger log = Logger.getLogger(SearchServiceImpl.class);

    @Resource(name = "corelib_solr_mongoServer")
    protected EdmMongoServer mongoServer;
    @Resource(name = "corelib_solr_mongoServer_id")
    protected EuropeanaIdMongoServer idServer;
    @Resource(name = "corelib_solr_neo4jServer")
    protected Neo4jServer neo4jServer;
    // provided by setter
    private SolrServer solrServer;
    @Value("#{europeanaProperties['solr.facetLimit']}")
    private int facetLimit;
    @Value("#{europeanaProperties['solr.username']}")
    private String username;
    @Value("#{europeanaProperties['solr.password']}")
    private String password;
    @Value("#{europeanaProperties['solr.searchLimit']}")
    private int searchLimit;
    private String mltFields;
    private boolean debug = false;

    @SuppressWarnings("unchecked")
    private void injectWebMetaInfo(final FullBean fullBean) {
        if (fullBean == null) {
         //   log.error("FullBean is null when injecting web meta info");
            return;
        }

        if (fullBean.getAggregations() == null || fullBean.getAggregations().isEmpty()) {
       //     log.error("FullBean Aggregation is null or empty when trying to inject web meta info");
            return;
        }

        // Temp fix for missing web resources
        Aggregation aggregationFix = fullBean.getAggregations().get(0);
        if (aggregationFix.getEdmIsShownBy() != null) {
            String isShownBy = fullBean.getAggregations().get(0).getEdmIsShownBy();
            boolean containsWr = false;

            if (aggregationFix.getWebResources() != null) {
                for (WebResource wr : aggregationFix.getWebResources()) {
                    if (StringUtils.equals(isShownBy, wr.getAbout())) {
                        containsWr = true;
                    }
                }
            }
            if (!containsWr) {
                List<WebResource> wResources = (List<WebResource>) aggregationFix.getWebResources();
                if (wResources == null) {
                    wResources = new ArrayList<>();
                }
                WebResourceImpl wr = new WebResourceImpl();
                wr.setAbout(isShownBy);
                wResources.add(wr);
                aggregationFix.setWebResources(wResources);
            }
        }

        if (aggregationFix.getEdmObject() != null) {
            String isShownBy = fullBean.getAggregations().get(0).getEdmObject();
            boolean containsWr = false;

            if (aggregationFix.getWebResources() != null) {
                for (WebResource wr : aggregationFix.getWebResources()) {
                    if (StringUtils.equals(isShownBy, wr.getAbout())) {
                        containsWr = true;
                    }
                }
            }
            if (!containsWr) {
                List<WebResource> wResources = (List<WebResource>) aggregationFix.getWebResources();
                if (wResources == null) {
                    wResources = new ArrayList<>();
                }
                WebResourceImpl wr = new WebResourceImpl();
                wr.setAbout(isShownBy);
                wResources.add(wr);
                aggregationFix.setWebResources(wResources);
            }
        }

        if (aggregationFix.getHasView() != null) {
            for (String hasView : aggregationFix.getHasView()) {

                boolean containsWr = false;

                if (aggregationFix.getWebResources() != null) {
                    for (WebResource wr : aggregationFix.getWebResources()) {
                        if (StringUtils.equals(hasView, wr.getAbout())) {
                            containsWr = true;
                        }
                    }
                }
                if (!containsWr) {
                    List<WebResource> wResources = (List<WebResource>) aggregationFix.getWebResources();
                    if (wResources == null) {
                        wResources = new ArrayList<>();
                    }
                    WebResourceImpl wr = new WebResourceImpl();
                    wr.setAbout(hasView);
                    wResources.add(wr);
                    aggregationFix.setWebResources(wResources);
                }
            }
        }

        ((List<Aggregation>) fullBean.getAggregations()).set(0, aggregationFix);
        for (final WebResource webResource : fullBean.getEuropeanaAggregation().getWebResources()) {
            WebResourceMetaInfoImpl webMetaInfo = null;

            // Locate the technical meta data from the web resource about
            if (webResource.getAbout() != null) {
                final HashCode hashCodeAbout = hf.newHasher()
                        .putString(webResource.getAbout(), Charsets.UTF_8)
                        .putString("-", Charsets.UTF_8)
                        .putString(fullBean.getAbout(), Charsets.UTF_8)
                        .hash();


                final String webMetaInfoId = hashCodeAbout.toString();
                webMetaInfo = getMetaInfo(webMetaInfoId);
            }


            // Locate the technical meta data from the aggregation is shown by
            if (webMetaInfo == null && fullBean.getEuropeanaAggregation().getEdmIsShownBy() != null) {
                final HashCode hashCodeIsShownBy = hf.newHasher()
                        .putString(fullBean.getEuropeanaAggregation().getEdmIsShownBy(), Charsets.UTF_8)
                        .putString("-", Charsets.UTF_8)
                        .putString(fullBean.getAbout(), Charsets.UTF_8)
                        .hash();

                final String webMetaInfoId = hashCodeIsShownBy.toString();
                webMetaInfo = getMetaInfo(webMetaInfoId);
            }

            if (webMetaInfo != null) {
                ((WebResourceImpl) webResource).setWebResourceMetaInfo(webMetaInfo);
            }
        }

        // Step 2 : Fill in the aggregation
        for (final Aggregation aggregation : fullBean.getAggregations()) {
            final Set<String> urls = new HashSet<>();

            if (StringUtils.isNotEmpty(aggregation.getEdmIsShownBy())) {
                urls.add(aggregation.getEdmIsShownBy());
            }

            if (null != aggregation.getHasView()) {
                urls.addAll(Arrays.asList(aggregation.getHasView()));
            }

            for (final WebResource webResource : aggregation.getWebResources()) {
                if (!urls.contains(webResource.getAbout().trim())) {
                    continue;
                }

                WebResourceMetaInfoImpl webMetaInfo = null;

                if (webResource.getAbout() != null) {
                    final HashCode hashCodeAbout = hf.newHasher()
                            .putString(webResource.getAbout(), Charsets.UTF_8)
                            .putString("-", Charsets.UTF_8)
                            .putString(fullBean.getAbout(), Charsets.UTF_8)
                            .hash();

                    // Locate the technical meta data from the web resource about
                    final String webMetaInfoId = hashCodeAbout.toString();
                    webMetaInfo = getMetaInfo(webMetaInfoId);
                }

                // Locate the technical meta data from the aggregation is shown
                // by
                if (webMetaInfo == null && aggregation.getEdmIsShownBy() != null) {
                    final HashCode hashCodeIsShownBy = hf.newHasher()
                            .putString(aggregation.getEdmIsShownBy(), Charsets.UTF_8)
                            .putString("-", Charsets.UTF_8)
                            .putString(aggregation.getAbout(), Charsets.UTF_8)
                            .hash();

                    final String webMetaInfoId = hashCodeIsShownBy.toString();
                    webMetaInfo = getMetaInfo(webMetaInfoId);
                }

                if (webMetaInfo != null) {
                    ((WebResourceImpl) webResource).setWebResourceMetaInfo(webMetaInfo);
                }
            }
        }
    }

    @Override
    public FullBean findById(String collectionId, String recordId, boolean similarItems)
            throws MongoRuntimeException, MongoDBException, Neo4JException {
        return findById(EuropeanaUriUtils.createEuropeanaId(collectionId, recordId), similarItems);
    }

    @Override
    public FullBean findById(String europeanaObjectId, boolean similarItems) throws MongoRuntimeException, MongoDBException {
        return findById(europeanaObjectId, similarItems, DEFAULT_HIERARCHY_TIMEOUT);
    }

    @Override
    public FullBean findById(String europeanaObjectId, boolean similarItems, int hierarchyTimeout) throws MongoRuntimeException, MongoDBException {
        hierarchyTimeout = (hierarchyTimeout == 0 ? DEFAULT_HIERARCHY_TIMEOUT :
                            (hierarchyTimeout < MIN_HIERARCHY_TIMEOUT ? MIN_HIERARCHY_TIMEOUT :
                             (hierarchyTimeout > MAX_HIERARCHY_TIMEOUT ? MAX_HIERARCHY_TIMEOUT : hierarchyTimeout)));
        FullBean fullBean = mongoServer.getFullBean(europeanaObjectId);
        if (fullBean != null) {
            injectWebMetaInfo(fullBean);

            boolean isHierarchy = false;
            try {
                isHierarchy = isHierarchy(fullBean.getAbout(), hierarchyTimeout);
            } catch (Neo4JException e) {
                log.error("Neo4JException: Could not establish Hierarchical status for object with Europeana ID: " + europeanaObjectId + ", reason: " + e.getMessage());
            }

            // TODO: this is a hack to prevent hundreds of dcTermsHasParts present in some hierarchies
            if (isHierarchy) {
                for (Proxy prx : fullBean.getProxies()) {
                    prx.setDctermsHasPart(null);
                }
            }

            if (similarItems) {
                try {
                    fullBean.setSimilarItems(findMoreLikeThis(europeanaObjectId));
                } catch (SolrServerException e) {
                    log.error("SolrServerException: " + e.getMessage());
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
    public FullBean resolve(String collectionId, String recordId,
                            boolean similarItems) throws SolrTypeException {
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
            injectWebMetaInfo(fullBean);
            if (similarItems) {
                try {
                    fullBean.setSimilarItems(findMoreLikeThis(fullBean.getAbout()));
                } catch (SolrServerException e) {
                    log.error("SolrServerException", e);
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
        if (log.isDebugEnabled()) { log.debug("Trying to resolve ids " + idsToCheck); }
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
                log.error(message);
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
        if (log.isDebugEnabled()) {
            log.debug(solrQuery.toString());
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
    public <T extends IdBean> ResultSet<T> search(Class<T> beanInterface,
                                                  Query query, boolean debug) throws SolrTypeException {
        this.debug = debug;
        return search(beanInterface, query);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends IdBean> ResultSet<T> search(Class<T> beanInterface,
                                                  Query query) throws SolrTypeException {
        if (query.getStart() != null && (query.getStart() + query.getPageSize() > searchLimit)) {
            throw new SolrTypeException(ProblemType.PAGINATION_LIMIT_REACHED);
        }
        ResultSet<T> resultSet = new ResultSet<>();
        Class<? extends IdBeanImpl> beanClazz = SearchUtils
                .getImplementationClass(beanInterface);

        if (isValidBeanClass(beanClazz)) {
            String[] refinements = query.getRefinements(true);
            if (SearchUtils.checkTypeFacet(refinements)) {
                SolrQuery solrQuery = new SolrQuery().setQuery(query
                        .getQuery(true));

                if (refinements != null) { // TODO add length 0 check!!
                    solrQuery.addFilterQuery(refinements);
                }

                solrQuery.setRows(query.getPageSize());
                solrQuery.setStart(query.getStart());

                // In case of a paginated query or a numbered field query:
                // => SORT = [OPTIONAL_EXPLICIT_SORT asc|desc, ] EUR_ID desc
                // and in case of a numbered non-field query:
                // => SORT = SCORE desc, EUR_ID desc
                // Note: timeallowed and cursormark are not allowed together in a query

                if (query.getCurrentCursorMark() != null) {
                    solrQuery.set(CursorMarkParams.CURSOR_MARK_PARAM, query.getCurrentCursorMark());
                } else {
                    if (!isFieldQuery(solrQuery.getQuery())) {
                        solrQuery.setSort("score", ORDER.desc);
                    }
                    solrQuery.setTimeAllowed(TIME_ALLOWED);
                }
                // will replace sort on score if available
                if (!StringUtils.isBlank(query.getSort())) {
                    solrQuery.setSort(query.getSort(),
                            (query.getSortOrder() == Query.ORDER_ASC ? ORDER.asc : ORDER.desc));
                }
                solrQuery.addSort("europeana_id", ORDER.desc);
                resultSet.setSortField(solrQuery.getSortField());

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
                    boolean hasFacetRefinements = (filteredFacets != null && filteredFacets
                            .size() > 0);

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
                    if (log.isDebugEnabled()) {
                        log.debug("Solr query is: " + solrQuery);
                    }
                    query.setExecutedQuery(solrQuery.toString());

                    QueryResponse queryResponse = solrServer.query(solrQuery, SolrRequest.METHOD.POST);


                    resultSet.setResults((List<T>) queryResponse.getBeans(beanClazz));
                    resultSet.setFacetFields(queryResponse.getFacetFields());
                    resultSet.setResultSize(queryResponse.getResults().getNumFound());
                    resultSet.setSearchTime(queryResponse.getElapsedTime());
                    resultSet.setSpellcheck(queryResponse.getSpellCheckResponse());
                    resultSet.setCurrentCursorMark(query.getCurrentCursorMark());
                    resultSet.setNextCursorMark(queryResponse.getNextCursorMark());
                    if (debug) {
                        resultSet.setSolrQueryString(query.getExecutedQuery());
                    }
                    if (queryResponse.getFacetQuery() != null) {
                        resultSet.setQueryFacets(queryResponse.getFacetQuery());
                    }
                } catch (SolrServerException e) {
                    log.error("SolrServerException: " + e.getMessage()
                            + " The query was: " + solrQuery);
                    throw new SolrTypeException(e, ProblemType.MALFORMED_QUERY);
                } catch (SolrException e) {
                    log.error("SolrException: " + e.getMessage()
                            + " The query was: " + solrQuery);
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
    public List<Term> suggestions(String query, int pageSize)
            throws SolrTypeException {
        return suggestions(query, pageSize, null);
    }

    @Override
    public List<Count> createCollections(String facetFieldName,
                                         String queryString, String... refinements) throws SolrTypeException {

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
            if (log.isDebugEnabled()) {
                log.debug("Solr query is: " + solrQuery.toString());
            }
            response = solrServer.query(solrQuery, SolrRequest.METHOD.POST);
            queryFacets = response.getFacetQuery();
        } catch (SolrServerException e) {
            log.error("SolrServerException: " + e.getMessage() + " for query "
                    + solrQuery.toString(), e);
        } catch (Exception e) {
            log.error("Exception: " + e.getClass().getCanonicalName() + " "
                    + e.getMessage() + " for query " + solrQuery.toString(), e);
        }

        return queryFacets;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends IdBean> ResultSet<T> sitemap(Class<T> beanInterface,
                                                   Query query) throws SolrTypeException {

        ResultSet<T> resultSet = new ResultSet<>();
        Class<? extends IdBeanImpl> beanClazz = SearchUtils
                .getImplementationClass(beanInterface);

        String[] refinements = query.getRefinements(true);
        if (SearchUtils.checkTypeFacet(refinements)) {
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
            if (query.getParameterMap() != null) {
                Map<String, String> parameters = query.getParameterMap();
                for (String key : parameters.keySet()) {
                    solrQuery.setParam(key, parameters.get(key));
                }
            }

            try {
                if (log.isDebugEnabled()) {
                    log.debug("Solr query is: " + solrQuery);
                }
                QueryResponse queryResponse = solrServer.query(solrQuery, SolrRequest.METHOD.POST);

                resultSet.setResults((List<T>) queryResponse
                        .getBeans(beanClazz));
                resultSet.setResultSize(queryResponse.getResults()
                        .getNumFound());
                resultSet.setSearchTime(queryResponse.getElapsedTime());
                if (solrQuery.getBool("facet", false)) {
                    resultSet.setFacetFields(queryResponse.getFacetFields());
                }
            } catch (SolrServerException e) {
                log.error("SolrServerException: " + e.getMessage());
                throw new SolrTypeException(e, ProblemType.MALFORMED_QUERY);
            } catch (SolrException e) {
                log.error("SolrException: " + e.getMessage());
                throw new SolrTypeException(e, ProblemType.MALFORMED_QUERY);
            }
        }

        return resultSet;
    }

    /**
     * Get Suggestions from Solr Suggester
     *
     * @param query    The query term
     * @param field    The field to query on
     * @param rHandler The ReqestHandler to use
     * @return A list of Terms for the specific term from the SolrSuggester
     */
    private List<Term> getSuggestions(String query, String field,
                                      String rHandler) {
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
            log.error("Exception :" + e.getMessage());
        }

        return results;
    }

    /**
     * Get the suggestions
     */
    @Override
    public List<Term> suggestions(String query, int pageSize, String field) {
        if (log.isDebugEnabled()) {
            log.debug(String.format("%s, %d, %s", query, pageSize, field));
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

        if (log.isDebugEnabled()) {
            log.debug(String.format("Returned %d results in %d ms",
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

    @Override
    public List<Neo4jBean> getChildren(String rdfAbout, int offset, int limit) {
        List<Neo4jBean> beans = new ArrayList<>();
        long startIndex = offset;
        List<CustomNode> children = neo4jServer.getChildren(rdfAbout, offset, limit);
        for (CustomNode child : children) {
            startIndex += 1L;
            beans.add(Node2Neo4jBeanConverter.toNeo4jBean(child, startIndex));
        }
        return beans;
    }

    @Override
    public boolean isHierarchy(String rdfAbout, int hierarchyTimeout) throws Neo4JException {
//        return neo4jServer.isHierarchy(rdfAbout);
        boolean result = false;
        long startTime = System.nanoTime();
        try {
            result = neo4jServer.isHierarchyTimeLimited(rdfAbout, hierarchyTimeout);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            log.warn(e.getClass().getSimpleName() +" retrieving isHierarchy information");
        }
        if (log.isInfoEnabled()) {
            log.info("Requesting hierarchy information took " + (System.nanoTime() - startTime) / 1000000 + "ms, max time = " + hierarchyTimeout);
        }
        return result;
    }

    @Override
    public List<Neo4jBean> getChildren(String rdfAbout, int offset) {
        return getChildren(rdfAbout, offset, 10);
    }

    @Override
    public List<Neo4jBean> getChildren(String rdfAbout) {
        return getChildren(rdfAbout, 0, 10);
    }

    private Node getNode(String rdfAbout) throws Neo4JException {
        return neo4jServer.getNode(rdfAbout);
    }

    @Override
    public Neo4jBean getHierarchicalBean(String rdfAbout) throws Neo4JException {
        Node node = getNode(rdfAbout);
        if (node != null) {
            return Node2Neo4jBeanConverter.toNeo4jBean(node, neo4jServer.getNodeIndex(node));
        }
        return null;
    }

    private enum SuggestionTitle {
        TITLE("title", "Title"), DATE("when", "Time/Period"), PLACE("where",
                "Place"), PERSON("who", "Creator"), SUBJECT("what", "Subject");

        String title;
        String mappedTitle;

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
        if (log.isInfoEnabled()) {
            log.info("spent: " + (new Date().getTime() - t0));
        }
        return (Date) index.get("lastModified");
    }

    public void logTime(String type, long time) {
        if (log.isDebugEnabled()) {
            log.debug(String.format("elapsed time (%s): %d", type, time));
        }
    }

    @Override
    public List<Neo4jBean> getPrecedingSiblings(String rdfAbout, int limit) throws Neo4JException {
        List<Neo4jBean> beans = new ArrayList<>();
        List<CustomNode> precedingSiblings = neo4jServer.getPrecedingSiblings(rdfAbout, limit);
        long startIndex = neo4jServer.getNodeIndexByRdfAbout(rdfAbout);
        for (CustomNode precedingSibling : precedingSiblings) {
            startIndex -= 1L;
            beans.add(Node2Neo4jBeanConverter.toNeo4jBean(precedingSibling, startIndex));
        }
        return beans;
    }

    @Override
    public List<Neo4jBean> getPrecedingSiblings(String rdfAbout) throws Neo4JException {
        return getPrecedingSiblings(rdfAbout, 10);
    }

    @Override
    public List<Neo4jBean> getFollowingSiblings(String rdfAbout, int limit) throws Neo4JException {
        List<Neo4jBean> beans = new ArrayList<>();
        List<CustomNode> followingSiblings = neo4jServer.getFollowingSiblings(rdfAbout, limit);
        long startIndex = neo4jServer.getNodeIndexByRdfAbout(rdfAbout);
        for (CustomNode followingSibling : followingSiblings) {
            startIndex += 1L;
            beans.add(Node2Neo4jBeanConverter.toNeo4jBean(followingSibling, startIndex));
        }
        return beans;
    }

    @Override
    public List<Neo4jBean> getFollowingSiblings(String rdfAbout) throws Neo4JException {
        return getFollowingSiblings(rdfAbout, 10);
    }

    @Override
    public long getChildrenCount(String rdfAbout) throws Neo4JException {
        return neo4jServer.getChildrenCount(getNode(rdfAbout));
    }

    // note that parents don't have their node indexes set in getInitialStruct
    // because they have to be fetched separately; therefore this is done afterwards
    @Override
    public Neo4jStructBean getInitialStruct(String nodeId) throws Neo4JException {
        return addParentNodeIndex(Node2Neo4jBeanConverter.toNeo4jStruct(neo4jServer.getInitialStruct(nodeId), neo4jServer.getNodeIndex(getNode(nodeId))));
    }

    private Neo4jStructBean addParentNodeIndex(Neo4jStructBean struct) throws Neo4JException {
        if (!struct.getParents().isEmpty()) {
            for (Neo4jBean parent : struct.getParents()) {
                parent.setIndex(neo4jServer.getNodeIndex(getNode(parent.getId())));
            }
        }
        return struct;
    }


    private WebResourceMetaInfoImpl getMetaInfo(final String webResourceMetaInfoId) {
        final DB db = mongoServer.getDatastore().getDB();
        final DBCollection webResourceMetaInfoColl = db.getCollection("WebResourceMetaInfo");

        final BasicDBObject query = new BasicDBObject("_id", webResourceMetaInfoId);
        final DBCursor cursor = webResourceMetaInfoColl.find(query);

        final Type type = new TypeToken<WebResourceMetaInfoImpl>() {
        }.getType();

        if (cursor.hasNext()) {
            return new Gson().fromJson(cursor.next().toString(), type);
        }
        return null;
    }

    // Filter tag generation

}

class PreEmptiveBasicAuthenticator implements HttpRequestInterceptor {
    private final UsernamePasswordCredentials credentials;

    public PreEmptiveBasicAuthenticator(String user, String pass) {
        credentials = new UsernamePasswordCredentials(user, pass);
    }

    @Override
    public void process(HttpRequest request, HttpContext context)
            throws HttpException, IOException {
        request.addHeader(BasicScheme.authenticate(credentials, "US-ASCII",
                false));
    }
}
