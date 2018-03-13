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
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
     * @param fullBean
     * @param mongoServer
     */
    @SuppressWarnings("unchecked")
    public static void injectWebMetaInfo(final FullBean fullBean, final EdmMongoServer mongoServer) {
        if (fullBean == null) {
            //   LOG.error("FullBean is null when injecting web meta info");
            return;
        }

        if (fullBean.getAggregations() == null || fullBean.getAggregations().isEmpty()) {
            //     LOG.error("FullBean Aggregation is null or empty when trying to inject web meta info");
            return;
        }

        // Temp fix for missing web resources
        Aggregation aggregationFix = fullBean.getAggregations().get(0);
        if (aggregationFix.getEdmIsShownBy() != null) {
            String isShownBy = aggregationFix.getEdmIsShownBy();
            generateWebResource(aggregationFix, isShownBy);
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
        for (final WebResource webResource : fullBean.getEuropeanaAggregation().getWebResources()) {
            WebResourceMetaInfoImpl webMetaInfo = null;

            // Locate the technical meta data from the web resource about
            if (webResource.getAbout() != null) {
                String hashCodeAbout = generateHashCode(webResource.getAbout(), fullBean.getAbout());
                webMetaInfo = getMetaInfo(mongoServer, hashCodeAbout);
            }


            // Locate the technical meta data from the aggregation is shown by
            if (webMetaInfo == null && fullBean.getEuropeanaAggregation().getEdmIsShownBy() != null) {
                String hashCodeIsShownBy = generateHashCode(fullBean.getEuropeanaAggregation().getEdmIsShownBy(), fullBean.getAbout());
                webMetaInfo = getMetaInfo(mongoServer, hashCodeIsShownBy);
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

                // Locate the technical meta data from the web resource about
                if (webResource.getAbout() != null) {
                    String hashCodeAbout = generateHashCode(webResource.getAbout(), fullBean.getAbout());
                    webMetaInfo = getMetaInfo(mongoServer, hashCodeAbout);
                }
                // Locate the technical meta data from the aggregation is shown by
                if (webMetaInfo == null && aggregation.getEdmIsShownBy() != null) {
                    String hashCodeIsShownBy = generateHashCode(aggregation.getEdmIsShownBy(), aggregation.getAbout());
                    webMetaInfo = getMetaInfo(mongoServer, hashCodeIsShownBy);
                }

                if (webMetaInfo != null) {
                    ((WebResourceImpl) webResource).setWebResourceMetaInfo(webMetaInfo);
                }
            }
        }

        addReferencedByIIIF(fullBean);
    }

    /**
     * Check if the aggregation already has webResources with the provided 'about' id. If not we generate a new
     * webResource
     * @param aggregation
     * @param about
     */
    private static void generateWebResource(Aggregation aggregation, String about) {
        boolean containsWr = false;
        if (aggregation.getWebResources() != null) {
            for (WebResource wr : aggregation.getWebResources()) {
                if (StringUtils.equals(about, wr.getAbout())) {
                    containsWr = true;
                }
            }
        }
        if (!containsWr) {
            List<WebResource> wResources = (List<WebResource>) aggregation.getWebResources();
            if (wResources == null) {
                wResources = new ArrayList<>();
            }
            WebResourceImpl wr = new WebResourceImpl();
            wr.setAbout(about);
            wResources.add(wr);
            aggregation.setWebResources(wResources);
        }
    }

    private static String generateHashCode(String wrId, String recordId){
        return hf.newHasher()
                .putString(wrId, Charsets.UTF_8)
                .putString("-", Charsets.UTF_8)
                .putString(recordId, Charsets.UTF_8)
                .hash()
                .toString();
    }

    private static WebResourceMetaInfoImpl getMetaInfo(final EdmMongoServer mongoServer, final String webResourceMetaInfoId) {
        final DB db = mongoServer.getDatastore().getDB();
        final DBCollection webResourceMetaInfoColl = db.getCollection("WebResourceMetaInfo");

        final BasicDBObject query = new BasicDBObject("_id", webResourceMetaInfoId);
        final DBCursor cursor = webResourceMetaInfoColl.find(query);

        final Type type = new TypeToken<WebResourceMetaInfoImpl>() {}.getType();

        if (cursor.hasNext()) {
            return new Gson().fromJson(cursor.next().toString(), type);
        }
        return null;
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
        if (bean.getProxies() != null) {
            for (Proxy proxy : bean.getProxies()){
                Map<String, List<String>> langMap = proxy.getDcType();
                if (langMap != null) {
                    boolean result = langMap.values().contains("http://schema.org/PublicationIssue") ||
                                     langMap.values().contains("https://schema.org/PublicationIssue");
                    LOG.debug("isNewsPaperRecord = {}", result);
                    return result;
                }
            }
        }
        LOG.debug("isNewsPaperRecord = false");
        return false;
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
