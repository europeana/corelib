package eu.europeana.corelib.definitions;

/**
 * Static URLs that are used in Corelib.
 *
 * There is little point in defining these as properties because Corelib is used as a dependency in other services so
 * any change to these properties requires a new built of Corelib anyway. Note that we should keep this class as small as
 * possible and only add static urls for which we have no other choice than put them in Corelib. All other urls should
 * be defined in the service that's relying on Corelib.
 *
 * @author Patrick Ehlert
 * Created on 08-10-2018
 */

public class EuropeanaStaticUrl {

    /**
     * Europeana base url used in namespaces. Note that we intentionally do not use https here!
     */
    public static final String EUROPEANA_NAMESPACE_URL = "http://www.europeana.eu";

    /**
     * Europeana main website base url. This can be used as a base url to construct urls that are resolvable.
     */
    public static final String EUROPEANA_PORTAL_URL = "https://www.europeana.eu";

    /**
     * Europeana thumbnail base url. Only parameters should be added (e.g. uri-parameter indicating which thumbnail to retrieve)
     */
    public static final String THUMBNAIL_BASE_URL = EUROPEANA_PORTAL_URL + "/api/v2/thumbnail-by-url.json?";

}
