package eu.europeana.corelib.web.service.impl;

import eu.europeana.corelib.definitions.EuropeanaStaticUrl;
import eu.europeana.corelib.definitions.solr.DocType;
import eu.europeana.corelib.web.utils.UrlBuilder;

/**
 * Class for constructing often-used urls.
 * This class replaces the corelib-specific functionality of the outdated EuropeanaUrlServiceImpl
 * @author Patrick Ehlert
 * Created on 08-10-2018
 */
public class EuropeanaUrlBuilder {

    /**
     * Generates an url to retrieve a record from the Europeana website
     * @param collectionId
     * @param itemId
     * @return UrlBuilder
     */
    public static UrlBuilder getRecordPortalUrl(String collectionId, String itemId) {
        return getRecordPortalUrl("/" + collectionId + "/" +itemId);
    }

    /**
     * Generates an url to retrieve a record from the Europeana website
     * @param europeanaId
     * @return url as String
     */
    public static UrlBuilder getRecordPortalUrl(String europeanaId) {
        UrlBuilder url = new UrlBuilder(EuropeanaStaticUrl.EUROPEANA_PORTAL_URL)
                .addPath("portal", "record")
                .addPage(europeanaId + ".html");
        return url;
    }

    /**
     * Generates an url to retrieve a thumbnail with default size from the Europeana Thumbnail Storage
     * The url is processed eventually by the ThumbnailController in the API2 project.
     * @param uri uri of original thumbnail. A null value can be provided but will result in a not-working thumbnail-url
     *            so for proper working an uri is required.
     * @param type defaults to IMAGE (optional)
     * @return UrlBuilder
     */
    public static UrlBuilder getThumbnailUrl(String uri, DocType type) {
        return getThumbnailUrl(uri, null, type);
    }

    /**
     * Generates an url to retrieve a thumbnail from the Europeana Thumbnail Storage
     * The url is process eventually by the ThumbnailController in the API2 project.
     * @param uri uri of original thumbnail. A null value can be provided but will result in a not-working thumbnail-url
     *            so for proper working an uri is required.
     * @param size either w200 or w400, other values are ignored (optional)
     * @param type defaults to IMAGE (optional)
     * @return UrlBuilder
     */
    // TODO once we move the only usage of this method in BriefBeanImpl, we can move this to Api2UrlBuilder
    public static UrlBuilder getThumbnailUrl(String uri, String size, DocType type) {
        UrlBuilder url = new UrlBuilder(EuropeanaStaticUrl.THUMBNAIL_BASE_URL)
                .addParam("uri", uri.trim())
                .addParam("size", size)
                .addParam("type", type.toString());
        return url;
    }

}
