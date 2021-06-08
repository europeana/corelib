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

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * If a record doesn't have a referencedBy value and doesn't contain any items of type that we block, then we add a
 * reference to IIIF. Optionally, this reference can include this API's FQDN (api2BaseUrl) as a
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
    private static final String HTTP_DEFAULT_IIIF_BASE_URL         = "http://iiif.europeana.eu";
    private static final String HTTPS_DEFAULT_IIIF_BASE_URL        = "https://iiif.europeana.eu";

    private static final String BLOCKED_MIME_TYPES_FILENAME = "IIIF_blocked_mime_types.txt";
    private static List<String> blockedMimeTypes;

    static {
        URL file = IIIFLink.class.getClassLoader().getResource(BLOCKED_MIME_TYPES_FILENAME);
        if (file == null) {
            LOG.warn("Unable to find file {}", BLOCKED_MIME_TYPES_FILENAME);
        } else {
            try (Stream<String> lines = Files.lines(Paths.get(file.toURI()))) {
                blockedMimeTypes = lines.map(String::toLowerCase).collect(Collectors.toUnmodifiableList());
            } catch (IOException | URISyntaxException e) {
                LOG.error("Error loading file {}", BLOCKED_MIME_TYPES_FILENAME, e);
                blockedMimeTypes = new ArrayList<>();
            }
        }
        LOG.info("Loaded {} mime-types from file for which no IIIF link will be created", blockedMimeTypes.size());
    }

    private IIIFLink() {
        // hide public constructor to prevent initialization
    }

    /**
     * Add a link to a IIIF manifest in the dcReferencedBy field if
     * 1. the record doesn't have a webresource with a mime-type listed in the blocked mime-types file, if so we skip
     * generating links for the entire record
     * 2. the webresource in question doesn't already have a dcReferencedBy field, if so we just skip that webresource
     *
     * @param bean           fullbean to which referenceBy IIIF link should be added
     * @param manifestAddApiUrl if true adds extra parameter to manifest links generated as value for dctermsIsReferencedBy
     *                       field. This extra parameter tells IIIF manifest to load data from the API instance specified
     *                       by the api2BaseUrl value
     * @param api2BaseUrl    FQDN of API that should be used by IIIF manifest (works only if manifestAddUrl is true)
     * @param manifestBaseUrl IIIF manifest location
     */
    public static void addReferencedBy(FullBean bean, Boolean manifestAddApiUrl, String api2BaseUrl, String manifestBaseUrl) {
        // tmp add timing information to see impact
        long start = System.nanoTime();
        if (bean.getAggregations() != null && !hasBlockedMimeTypes(bean)) {
            for (Aggregation a : bean.getAggregations()) {
                addManifestUrl(a, bean.getAbout(), manifestAddApiUrl, api2BaseUrl, manifestBaseUrl);
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("AddReferencedByIIIF took {} ns", System.nanoTime() - start);
        }
    }

    /**
     * We iterate over all web resources to see if there is any with a mimetype for which we shouldn't generate
     * an IIIF link.
     * @param bean the fullbean the is checked
     * @return true if has web resource with blocked mime-type, false otherwise
     */
    private static boolean hasBlockedMimeTypes(FullBean bean) {
        // the first aggregation (from data provider) should be the one that contains the webresources
        List<? extends WebResource> webResources = bean.getAggregations().get(0).getWebResources();
        for (WebResource wr : webResources) {
            String mimeType = wr.getEbucoreHasMimeType();
            if (StringUtils.isNotBlank(mimeType) && blockedMimeTypes.contains(mimeType.toLowerCase(Locale.ROOT))) {
                LOG.debug("Record {} has webresource {} with blocked mime-type. No IIIF link is generated",
                        bean.getAbout(), wr.getAbout());
                return true;
            }
        }
        return false;
    }

    private static void addManifestUrl(Aggregation a, String about, Boolean manifestAddApiUrl, String api2BaseUrl, String manifestBaseUrl) {
        for (WebResource wr : a.getWebResources()) {
            LOG.debug("webresource = {}, dctermsReferencedBy = {}", wr.getAbout(), wr.getDctermsIsReferencedBy());
            String iiifBaseUrl = StringUtils.isBlank(manifestBaseUrl) ? HTTPS_DEFAULT_IIIF_BASE_URL : manifestBaseUrl;
            String manifestUrl = iiifBaseUrl + "/presentation" + about + "/manifest";
            if (Boolean.TRUE.equals(manifestAddApiUrl)) {
                if (api2BaseUrl != null && api2BaseUrl.startsWith("http")) {
                    manifestUrl = manifestUrl + "?recordApi=" + api2BaseUrl;
                } else {
                    manifestUrl = manifestUrl + "?recordApi=https://" + api2BaseUrl;
                }
            }

            // update reference link if no dcTermsIsReferencedBy is set
            if (ArrayUtils.isEmpty(wr.getDctermsIsReferencedBy())) {
                wr.setDctermsIsReferencedBy(new String[]{manifestUrl});
                LOG.debug("webresource = {}, updated dctermsReferencedBy to {}", wr.getAbout(), wr.getDctermsIsReferencedBy());
                continue;
            }

            // if dcTermsIsReferencedBy already exists, only update values starting with http(s)://iiif.europeana.eu
            List<String> dcTerms = new ArrayList<>();
            for (String referenceUrl : wr.getDctermsIsReferencedBy()) {
                if (shouldUpdateManifestUrl(manifestBaseUrl, referenceUrl)) {
                    LOG.debug("webresource = {}, replaced existing dctermsReferencedBy {} with {}",
                            wr.getAbout(), referenceUrl, manifestUrl);
                    dcTerms.add(manifestUrl);
                } else {
                    LOG.debug("webresource = {}, keeping existing dctermsReferencedBy {}", wr.getAbout(), referenceUrl);
                    dcTerms.add(referenceUrl);
                }
            }
            wr.setDctermsIsReferencedBy(dcTerms.toArray(new String[0]));
        }
    }

    /**
     * Determines if the IIIF manifest link in a WebResource should be updated with an extra parameter.
     * This should be updated when the WebResource has a dcTermsIsReferencedBy value that starts with
     * "http://iiif.europeana.eu" or "https://iiif.europeana.eu" AND the manifestBaseUrl config property is set.
     *
     * @param reference       existing IIIF link in resource
     * @param manifestBaseUrl configured baseUrl property for manifest links
     * @return true if conditions match
     */
    private static boolean shouldUpdateManifestUrl(String manifestBaseUrl, String reference) {
        return StringUtils.isNotBlank(manifestBaseUrl) &&
                StringUtils.startsWithAny(reference, HTTPS_DEFAULT_IIIF_BASE_URL, HTTP_DEFAULT_IIIF_BASE_URL);
    }

    /**
     * Check if the record is a newspaper record (there is a dcType value called 'http://schema.org/PublicationIssue')
     *
     * @param bean fullbean to check
     * @return true if it's a newspaper record, otherwise false
     * @deprecated algorithm that determines for which records to generate an IIIF link changed, not used anymore
     */
    @Deprecated(since = "v2.13.2", forRemoval = true)
    private static boolean isNewsPaperRecord(FullBean bean) {
        if (null != bean.getProxies()) {
            for (Proxy proxy : bean.getProxies()) {
                if (dcTypeIsPublicationIssue(proxy)) {
                    LOG.debug("isNewsPaperRecord = TRUE");
                    return true;
                }
            }
        }
        LOG.debug("isNewsPaperRecord = FALSE");
        return false;
    }

    /**
     *  @deprecated algorithm that determines for which records to generate an IIIF link changed, not used anymore
     */
    @Deprecated(since = "v2.13.2", forRemoval = true)
    private static boolean dcTypeIsPublicationIssue(Proxy p) {
        Map<String, List<String>> langMap = p.getDcType();
        if (null != langMap) {
            for (List<String> langValues : langMap.values()) {
                if (langValues.contains(HTTP_SCHEMA_ORG_PUBLICATION_ISSUE) ||
                        langValues.contains(HTTPS_SCHEMA_ORG_PUBLICATION_ISSUE)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Check if it's a manifest Audio/Video record or EUscreen Item
     *
     * @param bean fullbean to check
     * @return true if it's AV record  otherwise false
     * @deprecated algorithm that determines for which records to generate an IIIF link changed, not used anymore
     */
    @Deprecated(since = "v2.13.2", forRemoval = true)
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
     * @deprecated algorithm that determines for which records to generate an IIIF link changed, not used anymore
     */
    @Deprecated(since = "v2.13.2", forRemoval = true)
    private static boolean checkMimeType(FullBean bean) {
        if (null != bean.getAggregations()) {
            for (Aggregation a : bean.getAggregations()) {
                if (hasAudioOrVideoWebResource(a)) {
                    LOG.debug("is A/V Item = TRUE");
                    return true;
                }
            }
        }
        LOG.debug("is A/V Item = FALSE");
        return false;
    }

    /**
     *  @deprecated algorithm that determines for which records to generate an IIIF link changed, not used anymore
     */
    @Deprecated(since = "v2.13.2", forRemoval = true)
    private static boolean hasAudioOrVideoWebResource(Aggregation a) {
        for (WebResource wr : a.getWebResources()) {
            if (MediaType.getMediaType(wr.getEbucoreHasMimeType()) == MediaType.AUDIO ||
                    MediaType.getMediaType(wr.getEbucoreHasMimeType()) == MediaType.VIDEO) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if the record is a EU Screen Item (if edmShownAt start with  value 'http://www.euscreen.eu' )
     *
     * @param bean fullbean to check
     * @return true if it's a EUScreen item, otherwise false
     * @deprecated algorithm that determines for which records to generate an IIIF link changed, not used anymore
     */
    @Deprecated(since = "v2.13.2", forRemoval = true)
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

}
