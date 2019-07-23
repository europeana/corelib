package eu.europeana.corelib.search.impl;

import com.google.common.base.Charsets;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import eu.europeana.corelib.definitions.edm.beans.FullBean;
import eu.europeana.corelib.definitions.edm.entity.Aggregation;
import eu.europeana.corelib.definitions.edm.entity.Proxy;
import eu.europeana.corelib.definitions.edm.entity.WebResource;
import eu.europeana.corelib.edm.model.metainfo.WebResourceMetaInfoImpl;
import eu.europeana.corelib.mongo.server.EdmMongoServer;
import eu.europeana.corelib.solr.entity.WebResourceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Type;
import java.util.*;

/**
 * Add extra web resource information to a fullbean. Note that some if this should actually be done at ingestion time, but for
 * various reasons we generate this information on the fly here
 * @author Patrick Ehlert
 * Created on 01-03-2018
 */
public class WebMetaInfo {

    private static final Logger LOG = LogManager.getLogger(WebMetaInfo.class);

    @SuppressWarnings("squid:S2070")
    private static final HashFunction hf = Hashing.md5();

    private WebMetaInfo() {
        // empty constructor to prevent initialization
    }

    /**
     * Add webResources and fill them with metadata retrieved from Mongo
     *
     * @param fullBean
     * @param mongoServer
     */
    @SuppressWarnings("unchecked")
    public static void injectWebMetaInfoBatch(final FullBean fullBean, final EdmMongoServer mongoServer) {
        if (fullBean == null || fullBean.getAggregations() == null || fullBean.getAggregations().isEmpty()) {
            return;
        }

        // Temp fix for missing web resources
        Aggregation aggregationFix = fullBean.getAggregations().get(0);

        if (aggregationFix.getEdmIsShownBy() != null) {
            String isShownBy = aggregationFix.getEdmIsShownBy();
            generateWebResource(aggregationFix, isShownBy);
        }

        if (aggregationFix.getEdmIsShownAt() != null) {
            String isShownAt = aggregationFix.getEdmIsShownAt();
            generateWebResource(aggregationFix, isShownAt);
        }

        if (aggregationFix.getEdmObject() != null) {
            String edmObject = aggregationFix.getEdmObject();
            generateWebResource(aggregationFix, edmObject);
        }

        if (aggregationFix.getHasView() != null) {
            for (String hasView : aggregationFix.getHasView()) {
                generateWebResource(aggregationFix, hasView);
            }
        }

        ((List<Aggregation>) fullBean.getAggregations()).set(0, aggregationFix);
        fillAggregations(fullBean, mongoServer);

        addReferencedByIIIF(fullBean);
    }

    private static void fillAggregations(final FullBean fullBean, final EdmMongoServer mongoServer) {
        Map<String, WebResource> webResourceHashCodes = prepareWebResourceHashCodes(fullBean);
        Map<String, WebResourceMetaInfoImpl> metaInfos = mongoServer.retrieveWebMetaInfos(new ArrayList<>(webResourceHashCodes.keySet()));
        for (Map.Entry<String, WebResourceMetaInfoImpl> metaInfo : metaInfos.entrySet()) {
            WebResource webResource = webResourceHashCodes.get(metaInfo.getKey());
            if (webResource != null && metaInfo.getValue() != null) {
                ((WebResourceImpl) webResource).setWebResourceMetaInfo(metaInfo.getValue());
            }
        }
    }


//    private static String extractId(String json) {
//        int index = json.indexOf("\"_id\"");
//        if (index != -1) {
//            index = json.indexOf('\"', index + "\"_id\"".length());
//            if (index != -1) {
//                return json.substring(index + 1, json.indexOf('\"', index + 1));
//            }
//        }
//        return null;
//    }

    private static Map<String, WebResource> prepareWebResourceHashCodes(FullBean fullBean) {
        Map<String, WebResource> hashCodes = new HashMap<>();

        for (final WebResource webResource : fullBean.getEuropeanaAggregation().getWebResources()) {
            // Locate the technical meta data from the web resource about
            if (webResource.getAbout() != null) {
                String hashCodeAbout = generateHashCode(webResource.getAbout(), fullBean.getAbout());
                hashCodes.put(hashCodeAbout, webResource);
            }

            // Locate the technical meta data from the aggregation is shown by
            if (!hashCodes.containsValue(webResource) && fullBean.getEuropeanaAggregation().getEdmIsShownBy() != null) {
                String hashCodeIsShownBy = generateHashCode(fullBean.getEuropeanaAggregation().getEdmIsShownBy(), fullBean.getAbout());
                hashCodes.put(hashCodeIsShownBy, webResource);
            }
        }

        for (final Aggregation aggregation : fullBean.getAggregations()) {
            final Set<String> urls = new HashSet<>();

            if (StringUtils.isNotEmpty(aggregation.getEdmIsShownBy())) {
                urls.add(aggregation.getEdmIsShownBy());
            }

            if (StringUtils.isNotEmpty(aggregation.getEdmIsShownAt())) {
                urls.add(aggregation.getEdmIsShownAt());
            }

            if (null != aggregation.getHasView()) {
                urls.addAll(Arrays.asList(aggregation.getHasView()));
            }

            // if the fix adds a web resource for edmObject it also has to be added here in order to be processed
            if (null != aggregation.getEdmObject()) {
                urls.add(aggregation.getEdmObject());
            }

            for (final WebResource webResource : aggregation.getWebResources()) {
                if (!urls.contains(webResource.getAbout().trim())) {
                    continue;
                }

                // Locate the technical meta data from the web resource about
                if (webResource.getAbout() != null) {
                    String hashCodeAbout = generateHashCode(webResource.getAbout(), fullBean.getAbout());
                    hashCodes.put(hashCodeAbout, webResource);
                }

                // Locate the technical meta data from the aggregation is shown by
                if (!hashCodes.containsValue(webResource) && aggregation.getEdmIsShownBy() != null) {
                    String hashCodeIsShownBy = generateHashCode(aggregation.getEdmIsShownBy(), aggregation.getAbout());
                    hashCodes.put(hashCodeIsShownBy, webResource);
                }

                // Locate the technical meta data from the aggregation is shown at
                if (!hashCodes.containsValue(webResource) && aggregation.getEdmIsShownAt() != null) {
                    String hashCodeIsShownAt = generateHashCode(aggregation.getEdmIsShownAt(), aggregation.getAbout());
                    hashCodes.put(hashCodeIsShownAt, webResource);
                }
            }
        }
        return hashCodes;
    }

    /**
     * Check if the aggregation already has webResources with the provided 'about' id. If not we generate a new
     * webResource
     * @param aggregation
     * @param about
     */
    private static void generateWebResource(Aggregation aggregation, String about) {
        if (aggregation.getWebResources() != null) {
            for (WebResource wr : aggregation.getWebResources()) {
                if (StringUtils.equals(about, wr.getAbout())) {
                    return;
                }
            }
        }
        List<WebResource> wResources = (List<WebResource>) aggregation.getWebResources();
        if (wResources == null) {
            wResources = new ArrayList<>();
        }
        WebResourceImpl wr = new WebResourceImpl();
        wr.setAbout(about);
        wResources.add(wr);
        aggregation.setWebResources(wResources);
    }

    private static String generateHashCode(String wrId, String recordId){
        return hf.newHasher()
                .putString(wrId, Charsets.UTF_8)
                .putString("-", Charsets.UTF_8)
                .putString(recordId, Charsets.UTF_8)
                .hash()
                .toString();
    }

       /**
     * This is temporary code: if a record is a newspaper record and doesn't have a referencedBy value, we add
     * a reference to IIIF (see ticket EA-992)
     * @param bean
     */
    private static void addReferencedByIIIF(FullBean bean) {
        // tmp add timing information to see impact
        long start = System.nanoTime();
        if (isNewsPaperRecord(bean) && !hasReferencedBy(bean) && bean.getAggregations() != null) {
            // add to all webresources in all aggregations
            for (Aggregation a : bean.getAggregations()) {
                for (WebResource wr : a.getWebResources()) {
                    String iiifId = "https://iiif.europeana.eu/presentation"+bean.getAbout()+"/manifest";
                    wr.setDctermsIsReferencedBy(new String[]{iiifId});
                }
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("AddReferencedByIIIF took {} ns", System.nanoTime() - start);
        }
    }

    /**
     * Check if the record is a newspaper record (there is a dcType value called 'http://schema.org/PublicationIssue')
     * @param bean
     * @return true if it's a newspaper record, otherwise false
     */
    public static boolean isNewsPaperRecord(FullBean bean) {
        boolean result = false;
        if (bean.getProxies() != null) {
            for (Proxy proxy : bean.getProxies()){
                Map<String, List<String>> langMap = proxy.getDcType();
                if (langMap != null) {
                    for (List<String> langValues : langMap.values()) {
                        result = langValues.contains("http://schema.org/PublicationIssue") ||
                                 langValues.contains("https://schema.org/PublicationIssue");
                        if (result) {
                            break;
                        }
                    }
                }
                if (result) {
                    break;
                }
            }
        }
        LOG.debug("isNewsPaperRecord = {}", result);
        return result;
    }

    /**
     * Check if the record is a newspaper record (there is a dcTermsIsPartOf that starts with http://data.theeuropeanalibrary.org)
     * @param bean
     * @return
     */
    public static boolean hasReferencedBy(FullBean bean) {
        if (bean.getAggregations() != null) {
            // check all aggregations
            for (Aggregation a : bean.getAggregations()) {
                // check all webresources
                for (WebResource wr : a.getWebResources()) {
                    if (wr.getDctermsIsReferencedBy() != null && wr.getDctermsIsReferencedBy().length > 0) {
                        LOG.debug("hasReferencedBy = true");
                        return true;
                    }
                }
            }
        }
        LOG.debug("hasReferencedBy = false");
        return false;
    }
}
