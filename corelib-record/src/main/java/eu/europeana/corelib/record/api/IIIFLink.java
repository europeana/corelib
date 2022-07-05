package eu.europeana.corelib.record.api;

import eu.europeana.corelib.definitions.edm.beans.FullBean;
import eu.europeana.corelib.definitions.edm.entity.Aggregation;
import eu.europeana.corelib.definitions.edm.entity.Proxy;
import eu.europeana.corelib.definitions.edm.entity.WebResource;
import eu.europeana.corelib.solr.entity.WebResourceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * If a record doesn't have a referencedBy value and doesn't contain any items of type that we block, then we add a
 * reference to IIIF. Optionally, this reference can include this API's FQDN (api2BaseUrl) as a
 * parameter so IIIF Manifest will use this API to load data.
 */
public final class IIIFLink {

    private static final Logger LOG = LogManager.getLogger(IIIFLink.class);

    private static final String SOUND                              = "SOUND";
    private static final String VIDEO                              = "VIDEO";
    private static final String HTTP_WWW_EUSCREEN_EU               = "http://www.euscreen.eu";
    private static final String HTTPS_WWW_EUSCREEN_EU              = "https://www.euscreen.eu";
    private static final String HTTP_DEFAULT_IIIF_BASE_URL         = "http://iiif.europeana.eu";
    private static final String HTTPS_DEFAULT_IIIF_BASE_URL        = "https://iiif.europeana.eu";
    public static final String MANIFEST_RDF_TYPE                   = "http://iiif.io/api/presentation/3#Manifest";

    private static final String BLOCKED_MIME_TYPES_FILENAME = "IIIF_blocked_mime_types.txt";
    private static List<String> blockedMimeTypes;

    // load blocked mime-types from file
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
     * 1. the record is an EU screen audio or video time, OR if
     * 2a. the record doesn't have a webresource with a mime-type listed in the blocked mime-types file
     * 2b. the record doesn't have a webresource that already has a non-europeana dcReferencedBy field
     * Note that only edmIsShownBy or hasView webresources are considered in these checks (or edmIsShownAt for EU-screen
     * A/V items)
     *
     * Also Adds the manifest Web resources for the IIIF manifest links added in the dcReferencedBy field
     *
     * @param bean           fullbean to which referenceBy IIIF link should be added
     * @param manifestAddApiUrl if true adds extra parameter to manifest links generated as value for dctermsIsReferencedBy
     *                       field. This extra parameter tells IIIF manifest to load data from the API instance specified
     *                       by the api2BaseUrl value
     * @param api2BaseUrl    FQDN of API that should be used by IIIF manifest (works only if manifestAddUrl is true)
     * @param manifestBaseUrl IIIF manifest location
     */
    public static void addReferencedByAndManifestResources(FullBean bean, Boolean manifestAddApiUrl, String api2BaseUrl, String manifestBaseUrl) {
        // add timing information to see impact
        long start = System.nanoTime();

        List<String> webResourceIds = getRelevantWebResourceIds(bean);

        // find the corresponding webresource objects
        List<WebResource> webResourcesToUpdate = new ArrayList<>();
        // the first aggregation (from data provider) should be the one that contains the hasViews and webresources
        for (WebResource wr : bean.getAggregations().get(0).getWebResources()) {
            LOG.debug("Checking webresources with id {}", wr.getAbout());
            if (webResourceIds.contains(wr.getAbout())) {
                // abort if there is a blocked item or dcTermsIsReferencedBy value not from europeana
                if (hasNonEuropeanaDcTermsIsRefValue(wr, bean.getAbout()) || hasBlockedMimeTypes(wr, bean.getAbout())) {
                    LOG.debug("Abort adding dcTermsIsReferenceBy values for record {}", bean.getAbout());
                    webResourceIds.clear();
                    webResourcesToUpdate.clear();
                } else {
                    LOG.debug("  Webresource {} should be updated", wr.getAbout());
                    webResourceIds.remove(wr.getAbout());
                    webResourcesToUpdate.add(wr);
                }
            }
            if (webResourceIds.isEmpty()) {
                break;
            }
        }

        // add dcTermsIsReferencedBy values
        for (WebResource wr : webResourcesToUpdate) {
            addManifestUrl(wr, bean.getAbout(), manifestAddApiUrl, api2BaseUrl, manifestBaseUrl);
        }

        // Now, add manifest web resources for the IIIF manifest links added in the dcReferencedBy field
        addManifestWebResource(bean, webResourcesToUpdate);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Adding dcTermsIsReferencedBy to {} web resources took {} ms", webResourcesToUpdate.size(),
                    (double) (System.nanoTime() - start) / 1_000_0000);
        }
    }

    private static List<String> getRelevantWebResourceIds(FullBean bean) {
        List<String> result = new ArrayList<>();
        // if it's an EU-screen sound or video item use isShownAt, otherwise use isShownBy
        String isShownAt = isEUScreenItem(bean);
        if (isShownAt != null && isVideoOrSound(bean)) {
            LOG.debug("Found edmIsShownAt = {}", isShownAt);
            result.add(isShownAt);
        } else {
            String isShownBy = getIsShownBy(bean);
            LOG.debug("Found edmIsShownBy = {}", isShownBy);
            result.add(isShownBy);
        }
        // also add all hasViews
        // the first aggregation (from data provider) should be the one that contains the hasViews and webresources
        Aggregation aggregation = bean.getAggregations().get(0);
        if (aggregation.getHasView() != null) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Found hasViews = {}", Arrays.toString(aggregation.getHasView()));
            }
            Collections.addAll(result, aggregation.getHasView());
        }
        return result;
    }

    /**
     * Check if the record is a EU Screen Item (if edmShownAt start with  value 'http(s)://www.euscreen.eu')
     * If so we return the value of the edmIsShownAtField, otherwise we return null
     */
    private static String isEUScreenItem(FullBean bean) {
        for (Aggregation a : bean.getAggregations()) {
            if (StringUtils.startsWithAny (a.getEdmIsShownAt(), HTTP_WWW_EUSCREEN_EU, HTTPS_WWW_EUSCREEN_EU)) {
                LOG.debug("isEUScreen A/V item = TRUE");
                return a.getEdmIsShownAt();
            }
        }
        LOG.debug("isEUScreen A/V item = FALSE");
        return null;
    }

    /**
     * Check proxies if edmType is SOUND or VIDEO
     */
    private static boolean isVideoOrSound(FullBean bean) {
        if (bean.getProxies() != null) {
            for (Proxy p : bean.getProxies()) {
                if (SOUND.equals(p.getEdmType()) || VIDEO.equals(p.getEdmType())) {
                    LOG.debug("isVideoOrSound item = TRUE");
                    return true;
                }
            }
        }
        LOG.debug("isVideoOrSound item = FALSE");
        return false;
    }

    /**
     * Check if the record is a EU Screen Item (if edmShownAt start with  value 'http(s)://www.euscreen.eu')
     * If so we return the value of the edmIsShownAtField, otherwise we return null
     */
    private static String getIsShownBy(FullBean bean) {
        for (Aggregation a : bean.getAggregations()) {
            if (a.getEdmIsShownBy() != null) {
                return a.getEdmIsShownBy();
            }
        }
        return null;
    }

    /**
     * Checks if the provided webresource as a dcTermsIsReferencedBy value that does not start with
     * http(s)://iiif.europeana.eu
     */
    private static boolean hasNonEuropeanaDcTermsIsRefValue(WebResource wr, String about) {
        String[] dcRefValue = wr.getDctermsIsReferencedBy();
        if (dcRefValue != null && dcRefValue.length > 0 &&
                !StringUtils.startsWithAny(dcRefValue[0], HTTPS_DEFAULT_IIIF_BASE_URL, HTTP_DEFAULT_IIIF_BASE_URL)) {
            LOG.debug("Record {} has webresource {} with provided (non-europeana) dcTermsIsReferencedBy", about, wr.getAbout());
            return true;
        }
        return false;
    }

    /**
     * Checks if the provided webresource has a mime-type that is in our blocked list
     */
    private static boolean hasBlockedMimeTypes(WebResource wr, String about) {
        String mimeType = wr.getEbucoreHasMimeType();
        if (StringUtils.isNotBlank(mimeType) && blockedMimeTypes.contains(mimeType.toLowerCase(Locale.ROOT))) {
            LOG.debug("Record {} has webresource {} with blocked mime-type", about, wr.getAbout());
            return true;
        }
        return false;
    }

    private static void addManifestUrl(WebResource wr, String about, Boolean manifestAddApiUrl, String api2BaseUrl, String manifestBaseUrl) {
        String iiifBaseUrl = StringUtils.isBlank(manifestBaseUrl) ? HTTPS_DEFAULT_IIIF_BASE_URL : manifestBaseUrl;
        String manifestUrl = iiifBaseUrl + "/presentation" + about + "/manifest";
        if (Boolean.TRUE.equals(manifestAddApiUrl)) {
            if (api2BaseUrl != null && api2BaseUrl.startsWith("http")) {
                manifestUrl = manifestUrl + "?recordApi=" + api2BaseUrl;
            } else {
                manifestUrl = manifestUrl + "?recordApi=https://" + api2BaseUrl;
            }
        }
        wr.setDctermsIsReferencedBy(new String[]{manifestUrl});
    }

    /**
     * Adds the manifest resources from the IIIF Links web resources provided
     * @param bean
     * @param webResourcesWithIIIFLinks
     */
    private static void addManifestWebResource(FullBean bean, List<WebResource> webResourcesWithIIIFLinks) {
        // add timing information to see impact
        long start = System.nanoTime();
        ((List<WebResource>)bean.getAggregations().get(0).getWebResources()).addAll(getManifestResources(webResourcesWithIIIFLinks));
        if (LOG.isDebugEnabled()) {
            LOG.debug("Adding {} new manifest web resources with dcTermsIsReferencedBy values took {} ms", webResourcesWithIIIFLinks.size(),
                    (double) (System.nanoTime() - start) / 1_000_0000);
        }
    }

    private static List<WebResource> getManifestResources(List<WebResource> webResourcesWithIIIFLinks) {
        List<WebResource> manifestResources = new ArrayList<>();
        webResourcesWithIIIFLinks.stream().forEach(webResource -> {
            WebResource manifestResource = new WebResourceImpl();
            manifestResource.setAbout(webResource.getDctermsIsReferencedBy()[0]); // will never be null or empty
            manifestResource.setRdfType(MANIFEST_RDF_TYPE);
            manifestResources.add(manifestResource);
        });
        return manifestResources;
    }
}
