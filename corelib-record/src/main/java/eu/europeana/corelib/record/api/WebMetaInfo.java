package eu.europeana.corelib.record.api;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import eu.europeana.corelib.definitions.edm.beans.FullBean;
import eu.europeana.corelib.definitions.edm.entity.Aggregation;
import eu.europeana.corelib.definitions.edm.entity.WebResource;
import eu.europeana.corelib.edm.model.metainfo.WebResourceMetaInfoImpl;
import eu.europeana.corelib.edm.utils.ProxyAggregationUtils;
import eu.europeana.corelib.solr.bean.impl.FullBeanImpl;
import eu.europeana.corelib.solr.entity.WebResourceImpl;
import eu.europeana.metis.mongo.dao.RecordDao;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Add extra web resource information to a fullbean. Note that some if this should actually be done at ingestion time, but for
 * various reasons we generate this information on the fly here
 * @author Patrick Ehlert
 * Created on 01-03-2018
 */
public final class WebMetaInfo {

    @SuppressWarnings("squid:S2070")
    private static final HashFunction hf = Hashing.md5();

    private WebMetaInfo() {
        // empty constructor to prevent initialization
    }

    /**
     * Add webResources and fill them with metadata retrieved from Mongo
     *
     * @param fullBean the fullbean object to which metadata should be added
     * @param recordDao the record dao from which the metadata should be retrieved
     * @param attributionCss location where the attribution css file is available
     */
    @SuppressWarnings("unchecked")
    public static void injectWebMetaInfoBatch(final FullBean fullBean, final RecordDao recordDao, String attributionCss) {
        if (fullBean == null || fullBean.getAggregations() == null || fullBean.getAggregations().isEmpty()) {
            return;
        }

        // Temp fix for missing web resources, get the main Aggregation
        Aggregation aggregationFix = ProxyAggregationUtils.getDataProviderAggregation(fullBean);

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
        fillAggregations(fullBean, recordDao);

        addAttributionSnippets(fullBean, attributionCss);
    }

    /**
     * For each webresource in the fullbean, generate an attribution snippet
     * @param fullBean
     * @param cssLocation
     */
    private static void addAttributionSnippets(FullBean fullBean, String cssLocation) {
        if ((fullBean.getAggregations() == null ||  fullBean.getAggregations().isEmpty())) {
            return;
        }

        ((FullBeanImpl) fullBean).setAsParent();
        for (Aggregation agg : fullBean.getAggregations()) {
            if (agg.getWebResources() != null && !agg.getWebResources().isEmpty()) {
                for (WebResourceImpl wRes : (List<WebResourceImpl>) agg.getWebResources()) {
                    wRes.initAttributionSnippet(cssLocation);
                }
            }
        }
    }

    private static void fillAggregations(final FullBean fullBean, final RecordDao recordDao) {
        Map<String, WebResource> webResourceHashCodes = prepareWebResourceHashCodes(fullBean);
        Map<String, WebResourceMetaInfoImpl> metaInfos = recordDao.retrieveWebMetaInfos(new ArrayList<>(webResourceHashCodes.keySet()));
        for (Map.Entry<String, WebResourceMetaInfoImpl> metaInfo : metaInfos.entrySet()) {
            WebResource webResource = webResourceHashCodes.get(metaInfo.getKey());
            if (webResource != null && metaInfo.getValue() != null) {
                ((WebResourceImpl) webResource).setWebResourceMetaInfo(metaInfo.getValue());
            }
        }
    }

    private static Map<String, WebResource> prepareWebResourceHashCodes(FullBean fullBean) {
        Map<String, WebResource> hashCodes = new HashMap<>();

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

    private static String generateHashCode(String wrId, String recordId) {
        return hf.newHasher()
                .putString(wrId, StandardCharsets.UTF_8)
                .putString("-", StandardCharsets.UTF_8)
                .putString(recordId, StandardCharsets.UTF_8)
                .hash()
                .toString();
    }

}
