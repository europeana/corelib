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
                .addPath("item")
                .addPage(europeanaId);
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
     * The url is process eventually by the Thumbnail API.
     * @param uri uri of original thumbnail. A null value can be provided but will result in a not-working thumbnail-url
     *            so for proper working an uri is required.
     * @param size either w200 or w400, other values are ignored (optional)
     * @param type defaults to IMAGE (optional)
     * @return UrlBuilder
     */
    // TODO for now we keep this here because it can be used by both API2 and OAI-PMH. However once OAI-PMH retrieves
    // data from Record API (instead of directly from Corelib), then we can move this to the API2 project
    public static UrlBuilder getThumbnailUrl(String uri, String size, DocType type) {
        UrlBuilder url = new UrlBuilder(EuropeanaStaticUrl.THUMBNAIL_BASE_URL)
                .addParam("uri", uri.trim())
                .addParam("size", size);
        if (type != null) {
           url.addParam("type", type.toString());
        }
        return url;
    }

}
