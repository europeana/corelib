package eu.europeana.corelib.record.api;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import eu.europeana.corelib.definitions.edm.beans.FullBean;
import eu.europeana.corelib.definitions.edm.entity.Aggregation;
import eu.europeana.corelib.definitions.edm.entity.WebResource;
import eu.europeana.corelib.edm.model.metainfo.WebResourceMetaInfoImpl;
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

        // Temp fix for missing web resources, get the DataProvider Aggregation
        // data provider aggregation is the first in list
        // if there was no data provider aggregation, the first aggregation is picked
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
        // ideally there should be web resource present only for the data provider aggregation
        for (Aggregation agg : fullBean.getAggregations()) {
            if (agg.getWebResources() != null && !agg.getWebResources().isEmpty()) {
                for (WebResourceImpl wRes : (List<WebResourceImpl>) agg.getWebResources()) {
                    wRes.initAttributionSnippet(cssLocation);
                }
            }
        }
    }

    /**
     * method will add web resource meta-info to the data provider aggregation web resource
     * @param fullBean
     * @param recordDao
     */
    private static void fillAggregations(final FullBean fullBean, final RecordDao recordDao) {
        // fill aggregation with webresource meta-info only for data provider aggregation. See: EA-2523
        Aggregation dataProviderAggregation = fullBean.getAggregations().get(0);
        Map<String, WebResource> webResourceHashCodes = prepareWebResourceHashCodes(dataProviderAggregation, fullBean.getAbout());
        Map<String, WebResourceMetaInfoImpl> metaInfos = recordDao.retrieveWebMetaInfos(new ArrayList<>(webResourceHashCodes.keySet()));
        for (Map.Entry<String, WebResourceMetaInfoImpl> metaInfo : metaInfos.entrySet()) {
            WebResource webResource = webResourceHashCodes.get(metaInfo.getKey());
            ((WebResourceImpl) webResource).setWebResourceMetaInfo(metaInfo.getValue());
        }
    }

    /**
     * EA-4463 generate hash codes for all the web resources.
     * This is to fetch technical metadata for the web resources present.
     * Hash is generated for web resources id and record id
     * @param aggregation aggregation object
     * @param recordId record id
     * @return hash codes and web resources id
     */
    private static Map<String, WebResource> prepareWebResourceHashCodes(Aggregation aggregation, String recordId) {
        Map<String, WebResource> hashCodes = new HashMap<>();
        for(WebResource wr : aggregation.getWebResources()) {
            if (StringUtils.isNotEmpty(wr.getAbout())) {
                String hashCode = generateHashCode(wr.getAbout().trim(), recordId);
                hashCodes.put(hashCode, wr);
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
