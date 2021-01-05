package eu.europeana.corelib.record.api;

import eu.europeana.corelib.definitions.edm.beans.FullBean;
import eu.europeana.corelib.definitions.edm.entity.Aggregation;
import eu.europeana.corelib.definitions.edm.entity.Proxy;
import eu.europeana.corelib.definitions.edm.entity.WebResource;
import eu.europeana.metis.schema.model.MediaType;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * If a record is a newspaper record and doesn't have a referencedBy value, we add a reference to IIIF
 * (see ticket EA-992) Optionally, this reference can include this API's FQDN (api2BaseUrl) as a
 * parameter so IIIF Manifest will use this API to load data.
 */
public final class IIIFLink {

    private static final Logger LOG = LogManager.getLogger(IIIFLink.class);

    private static final String AUDIO                              = "AUDIO";
    private static final String VIDEO                              = "VIDEO";
    private static final String HTTP_SCHEMA_ORG_PUBLICATION_ISSUE  = "http://schema.org/PublicationIssue";
    private static final String HTTPS_SCHEMA_ORG_PUBLICATION_ISSUE = "https://schema.org/PublicationIssue";
    private static final String HTTP_WWW_EUSCREEN_EU               = "http://www.euscreen.eu";
    private static final String HTTPS_WWW_EUSCREEN_EU              = "https://www.euscreen.eu";
    private static final String DEFAULT_IIIF_BASE_URL = "https://iiif.europeana.eu";

    private IIIFLink() {
        // empty constructor to prevent initialization
    }

    /**
     * If the record is a newspaper record and doesn't have a referencedBy value, add a link to IIIF Manifest
     *
     * @param bean           fullbean to which referenceBy IIIF link should be added
     * @param manifestAddUrl if true adds extra parameter to manifest links generated as value for dctermsIsReferencedBy
     *                       field. This extra parameter tells IIIF manifest to load data from the API instance specified
     *                       by the api2BaseUrl value
     * @param api2BaseUrl    FQDN of API that should be used by IIIF manifest (works only if manifestAddUrl is true)
     *
     * @param manifestBaseUrl IIIF manifest location
     */
    public static void addReferencedBy(FullBean bean, Boolean manifestAddUrl, String api2BaseUrl, String manifestBaseUrl) {
        // tmp add timing information to see impact
        long start = System.nanoTime();
        if ((isNewsPaperRecord(bean) || isManifestAVRecord(bean)) &&
            bean.getAggregations() != null) {
            // add to all webresources in all aggregations
            for (Aggregation a : bean.getAggregations()) {
                for (WebResource wr : a.getWebResources()) {
                    String iifBaseUrl = StringUtils.isBlank(manifestBaseUrl) ? DEFAULT_IIIF_BASE_URL : manifestBaseUrl;
                    String iiifId = iifBaseUrl + "/presentation" + bean.getAbout() + "/manifest";
                    if (Boolean.TRUE.equals(manifestAddUrl)) {
                        iiifId = iiifId + "?recordApi=" + api2BaseUrl;
                    }

                    // update reference link if no dcTermsIsReferencedBy is set
                    if (ArrayUtils.isEmpty(wr.getDctermsIsReferencedBy())) {
                        wr.setDctermsIsReferencedBy(new String[]{iiifId});
                        continue;
                    }

                    // if dcTermsIsReferencedBy already exists, only update values starting with http(s)://iiif.europeana.eu
                    List<String> dcTerms = new ArrayList<>();
                    for (String referenceUrl : wr.getDctermsIsReferencedBy()) {
                        dcTerms.add(shouldUpdateManifestUrl(manifestBaseUrl, referenceUrl) ? iiifId : referenceUrl);
                    }
                    wr.setDctermsIsReferencedBy(dcTerms.toArray(new String[0]));
                }
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("AddReferencedByIIIF took {} ns", System.nanoTime() - start);
        }
    }

    /**
     * Determines if the IIIF manifest link in a WebResource should be updated.
     * This should be updated when the WebResource has a dcTermsIsReferencedBy value that starts with "http://iiif.europeana.eu" or "https://iiif.europeana.eu" AND
     * the manifestBaseUrl config property is set.
     *
     * @param reference       existing IIIF link in resource
     * @param manifestBaseUrl configured baseUrl property for manifest links
     * @return true if conditions match
     */
    private static boolean shouldUpdateManifestUrl(String manifestBaseUrl, String reference) {
        return StringUtils.isNotBlank(manifestBaseUrl) &&
                StringUtils.startsWithAny(reference, new String[]{"http://iiif.europeana.eu", "https://iiif.europeana.eu"});
    }

    /**
     * Check if the record is a newspaper record (there is a dcType value called 'http://schema.org/PublicationIssue')
     *
     * @param bean fullbean to check
     * @return true if it's a newspaper record, otherwise false
     */
    private static boolean isNewsPaperRecord(FullBean bean) {
        if (null != bean.getProxies()) {
            for (Proxy proxy : bean.getProxies()) {
                Map<String, List<String>> langMap = proxy.getDcType();
                if (null != langMap) {
                    for (List<String> langValues : langMap.values()) {
                        if (langValues.contains(HTTP_SCHEMA_ORG_PUBLICATION_ISSUE) ||
                                 langValues.contains(HTTPS_SCHEMA_ORG_PUBLICATION_ISSUE)){
                            LOG.debug("isNewsPaperRecord = TRUE");
                            return true;
                        }
                    }
                }
            }
        }
        LOG.debug("isNewsPaperRecord = FALSE");
        return false;
    }

    /**
     * Check if it's a manifest Audio/Video record or EUscreen Item
     *
     * @param bean fullbean to check
     * @return true if it's AV record  otherwise false
     */
    private static boolean isManifestAVRecord(FullBean bean) {
        String  edmType = null;
        boolean isEdmTypeAV;
        boolean isMimeTypeOrEUScreen = false;
        //get edmType
        if (null != bean.getProxies()) {
            for (Proxy proxy : bean.getProxies()) {
                if (null != proxy.getEdmType()) {
                    edmType = proxy.getEdmType();
                }
            }
        }
        isEdmTypeAV = StringUtils.containsAny(edmType, AUDIO, VIDEO);
        LOG.debug("edmType A/V = {}", Boolean.valueOf(isEdmTypeAV));

        // if edmType is A/V, return TRUE if mimeType is playable OR a EUScreen Item, FALSE if neither
        // if edmType is NOT A/V return FALSE
        if (isEdmTypeAV) {
            isMimeTypeOrEUScreen = (checkMimeType(bean) || isEUScreenItem(bean));
            LOG.debug("Mimetype playable: {}", Boolean.valueOf(isMimeTypeOrEUScreen));
        }
        return isEdmTypeAV && isMimeTypeOrEUScreen;
    }

    /**
     * Check if webResources has a playable MimeType
     *
     * @param bean fullbean to check
     * @return true if mimeType is playable, otherwise false
     */
    private static boolean checkMimeType(FullBean bean) {
        if (null != bean.getAggregations()) {
            for (Aggregation a : bean.getAggregations()) {
                for (WebResource wr : a.getWebResources()) {
                    if (MediaType.getMediaType(wr.getEbucoreHasMimeType()) == MediaType.AUDIO ||
                        MediaType.getMediaType(wr.getEbucoreHasMimeType()) == MediaType.VIDEO) {
                        LOG.debug("isA/V Item = TRUE");
                        return true;
                    }
                }
            }
        }
        LOG.debug("isA/V Item = FALSE");
        return false;
    }

    /**
     * Check if the record is a EU Screen Item (if edmShownAt start with  value 'http://www.euscreen.eu' )
     *
     * @param bean fullbean to check
     * @return true if it's a EUScreen item, otherwise false
     */
    private static boolean isEUScreenItem(FullBean bean) {
        // check edmIsShownAt
        if (null != bean.getAggregations()) {
            for (Aggregation a : bean.getAggregations()) {
                if (null != a.getEdmIsShownAt() && (a.getEdmIsShownAt().startsWith(HTTP_WWW_EUSCREEN_EU) ||
                                                    a.getEdmIsShownAt().startsWith(HTTPS_WWW_EUSCREEN_EU))) {
                    LOG.debug("isEUScreen Item = TRUE");
                    return true;
                }
            }
        }
        LOG.debug("isEUScreen Item = FALSE");
        return false;
    }

    /**
     * Checks if there is any webresource that has a dcTermsIsReferenced value (if true we rely on the values that were
     * ingested and do not construct any manifest urls ourselves
     *
     * @param bean
     * @return true if bean has a webresource for any aggregation that has a dcTermsIsReferenced value, otherwise false
     */
    private static boolean hasReferencedBy(FullBean bean) {
        if (null != bean.getAggregations()) {
            // check all aggregations
            for (Aggregation a : bean.getAggregations()) {
                // check all webresources
                for (WebResource wr : a.getWebResources()) {
                    if (null != wr.getDctermsIsReferencedBy() && 0 < wr.getDctermsIsReferencedBy().length) {
                        LOG.debug("hasReferencedBy = TRUE");
                        return true;
                    }
                }
            }
        }
        LOG.debug("hasReferencedBy = FALSE");
        return false;
    }
}