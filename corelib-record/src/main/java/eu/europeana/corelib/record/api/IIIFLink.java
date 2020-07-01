package eu.europeana.corelib.record.api;

import eu.europeana.corelib.definitions.edm.beans.FullBean;
import eu.europeana.corelib.definitions.edm.entity.Aggregation;
import eu.europeana.corelib.definitions.edm.entity.Proxy;
import eu.europeana.corelib.definitions.edm.entity.WebResource;
import eu.europeana.metis.utils.MediaType;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * If a record is a newspaper record and doesn't have a referencedBy value, we add a reference to IIIF
 * (see ticket EA-992) Optionally, this reference can include this API's FQDN (api2BaseUrl) as a
 * parameter so IIIF Manifest will use this API to load data.
 */
public final class IIIFLink {

    private static final Logger LOG = LogManager.getLogger(IIIFLink.class);

    private IIIFLink() {
        // empty constructor to prevent initialization
    }

    /**
     * If the record is a newspaper record and doesn't have a referencedBy value, add a link to IIIF Manifest
     * @param bean fullbean to which referenceBy IIIF link should be added
     * @param manifestAddUrl if true adds extra parameter to manifest links generated as value for dctermsIsReferencedBy
     *                       field. This extra parameter tells IIIF manifest to load data from the API instance specified
     *                       by the api2BaseUrl value
     * @param api2BaseUrl FQDN of API that should be used by IIIF manifest (works only if manifestAddUrl is true)
     *
     *
     */
    public static void addReferencedBy(FullBean bean, Boolean manifestAddUrl, String api2BaseUrl) {
        // tmp add timing information to see impact
        long start = System.nanoTime();
        if ((isNewsPaperRecord(bean) || isManifestAVRecord(bean)) && !hasReferencedBy(bean) && bean.getAggregations() != null) {
            // add to all webresources in all aggregations
            for (Aggregation a : bean.getAggregations()) {
                for (WebResource wr : a.getWebResources()) {
                    String iiifId = "https://iiif.europeana.eu/presentation" + bean.getAbout() + "/manifest";
                    if (manifestAddUrl != null && manifestAddUrl.equals(Boolean.TRUE)) {
                        iiifId = iiifId + "?recordApi=" + api2BaseUrl;
                    }
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
     * @param bean fullbean to check
     * @return true if it's a newspaper record, otherwise false
     */
    public static boolean isNewsPaperRecord(FullBean bean) {
        boolean result = false;
        if (bean.getProxies() != null) {
            for (Proxy proxy : bean.getProxies()) {
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
     * Check if it's a manifest Audio/Video record or EUscreen Item
     * @param bean fullbean to check
     * @return true if it's AV record  otherwise false
     */
    public static boolean isManifestAVRecord (FullBean bean) {
        String edmType = null;
        boolean result    = false;
        boolean edmTypeAV;
        //get edmType
        if (bean.getProxies() != null) {
            for (Proxy proxy : bean.getProxies()) {
                if(proxy.getEdmType() != null) {
                    edmType = proxy.getEdmType().getEnumNameValue();
                }
            }
        }
        if (StringUtils.isNotEmpty(edmType)) {
            // check if edmType is Audio or Video
            edmTypeAV = StringUtils.equals(edmType, "VIDEO") || StringUtils.equals(edmType, "AUDIO");
            LOG.debug("edmType A/V = {}", edmTypeAV);
            // if edmType is A/V, do the mimeType check
            if (edmTypeAV) {
                 result = checkMimeType(bean);
                 // if mimeType is not playable, check for EUScreen item
                if (! result) {
                    result = isEUScreenItem(bean);
                }
            }
        }
       return  result;
    }

    /**
     * Check if webResources has a playable MimeType
     * @param bean fullbean to check
     * @return true if mimeType is playable  otherwise false
     */
    private static boolean checkMimeType(FullBean bean) {
        boolean result = false ;
        if (bean.getAggregations() != null) {
            for (Aggregation a : bean.getAggregations()) {
                for (WebResource wr : a.getWebResources()) {
                    result = isPlayableMimeType(wr.getEbucoreHasMimeType());
                    if (result) {
                        break;
                    }
                }
                if (result) {
                    break;
                }
            }
        }
        LOG.debug("isA/V Item = {}", result);
        return result;
    }

    /**
     * Check if the mimeType is playable mimeType
     * @param mimeType mimeType to check
     * @return true if mimeType is playable
     **/
    private static boolean isPlayableMimeType(String mimeType) {
        MediaType mediaType = MediaType.getMediaType(mimeType);
        return (mediaType == MediaType.AUDIO || mediaType == MediaType.VIDEO) ;
    }

    /**
     * Check if the record is a EU Screen Item (if edmShownAt start with  value 'http://www.euscreen.eu' )
     * @param bean fullbean to check
     * @return true if it's a EUScreen item, otherwise false
     */
    private static  boolean isEUScreenItem(FullBean bean) {
        boolean result = false;
        // check edmIsShownAt
        if (bean.getAggregations() != null) {
            for (Aggregation a : bean.getAggregations()) {
                if (a.getEdmIsShownAt() != null  &&
                        (a.getEdmIsShownAt().startsWith("http://www.euscreen.eu") ||
                                a.getEdmIsShownAt().startsWith("https://www.euscreen.eu")) ) {
                    result = true;
                }
                if (result) {
                    break;
                }
            }
        }
        LOG.debug("isEUScreen Item = {}", result);
        return result;
    }

    /**
     * Checks if there is any webresource that has a dcTermsIsReferenced value (if true we rely on the values that were
     * ingested and do not construct any manifest urls ourselves
     * @param bean
     * @return
     */
    private static boolean hasReferencedBy(FullBean bean) {
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
