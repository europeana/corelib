package eu.europeana.corelib.web.service;

import java.io.UnsupportedEncodingException;

import eu.europeana.corelib.definitions.solr.DocType;
import eu.europeana.corelib.web.service.impl.EuropeanaUrlBuilder;
import eu.europeana.corelib.web.utils.UrlBuilder;

/**
 * @deprecated October 2018 All functionality has been moved to EuropeanaUrlBuilder and Api2UrlBuilder
 * @see EuropeanaUrlBuilder
 */
@Deprecated
public interface EuropeanaUrlService {

    // ENCODING
    String ENC_UTF8 			= "UTF-8";

    // GENERAL PATHS
    String PATH_RECORD 		= "record";
    String PATH_ITEM		= "item";


    // PORTAL PATHS
    String PATH_PORTAL_RESOLVE = "resolve";

    // API PATHS
    String PATH_API 			= "api";
    String PATH_API_V1 		= "v1";
    String PATH_API_V2 		= "v2";
    String PATH_API_REDIRECT 	= "redirect";

    // EXTENTIONS
    String EXT_JSON			= ".json";
    String EXT_JSON_LD			= ".jsonld";
    String EXT_HTML			= ".html";

    // GENERAL PARAMS
    String PARAM_SEARCH_QUERY 	= "query";
    String PARAM_SEARCH_ROWS 	= "rows";
    String PARAM_SEARCH_START 	= "start";
    String PARAM_SEARCH_FACET 	= "qf";

    // PORTAL PARAMS
    String PARAM_REDIRECT_SHOWNAT 		= "shownAt";
    String PARAM_REDIRECT_SHOWNBY 		= "shownBy";
    String PARAM_REDIRECT_PROVIDER 	= "provider";
    String PARAM_REDIRECT_EUROPEANAID 	= "id";

    // API PARAMS
    String PARAM_API_APIKEY	= "wskey";
    String PARAM_API_V1_SEARCH_QUERY	= "searchTerms";
    String PARAM_API_V1_SEARCH_START	= "startPage";

    /**
     * @deprecated September 2018 not used anymore
     */
    @Deprecated
    UrlBuilder getApi1Home(String apikey);

    @Deprecated
    UrlBuilder getApi2Home(String apikey);

    /**
     * @deprecated October 2018 only used in old MyEuropeana functionality
     */
    @Deprecated
    UrlBuilder getApi1SearchJson(String apikey, String query, int start) throws UnsupportedEncodingException;

    @Deprecated
    UrlBuilder getApi2SearchJson(String apikey, String query, String rows) throws UnsupportedEncodingException;

    @Deprecated
    UrlBuilder getApi2RecordJson(String apikey, String collectionid, String objectid);

    @Deprecated
    UrlBuilder getApi2RecordJson(String apikey, String europeanaId);

    /**
     * @deprecated September 2018 not used anymore
     */
    @Deprecated
    UrlBuilder getApi1Record(String apikey, String europeanaId, String extention);

    @Deprecated
    UrlBuilder getApi2Record(String apikey, String europeanaId, String extention);

    @Deprecated
    UrlBuilder getApi2Redirect(String apikey, String showAt, String provider, String europeanaId, String profile);

    /**
     * @return base url of Europeana Collections homepage a.k.a. Europeana Portal
     */
    @Deprecated
    UrlBuilder getPortalHome();

    @Deprecated
    String getPortalResolve(String europeanaId);

    @Deprecated
    String getPortalResolve(String collectionid, String objectid);

    /**
     * @deprecated September 2018 not used anymore
     */
    @Deprecated
    UrlBuilder getPortalSearch() throws UnsupportedEncodingException;

    /**
     * @deprecated September 2018 not used anymore
     */
    @Deprecated
    UrlBuilder getPortalSearch(boolean relative, String query, String rows) throws UnsupportedEncodingException;

    /**
     * @deprecated September 2018 not used anymore
     */
    @Deprecated
    UrlBuilder getPortalSearch(boolean relative, String searchpage, String query, String rows) throws UnsupportedEncodingException;


    /**
     * @deprecated September 2018
     * @see EuropeanaUrlBuilder#getRecordPortalUrl(String, String)
     */
    @Deprecated
    UrlBuilder getPortalRecord(boolean relative, String collectionid, String objectid);

    /**
     * @deprecated September 2018
     * @see EuropeanaUrlBuilder#getRecordPortalUrl(String)
     */
    @Deprecated
    UrlBuilder getPortalRecord(boolean relative, String EuropeanaId);

    /**
     *
     * @param collectionid
     * @param objectid
     * @return UrlBuilder that points to the Europeana Collections record webpage with the provided recordId
     * @deprecated October 2018
     * @see EuropeanaUrlBuilder#getRecordPortalUrl(String, String)
     */
    @Deprecated
    UrlBuilder getPortalRecord(String collectionid, String objectid);

    /**
     *
     * @param europeanaId
     * @return  UrlBuilder that points to the Europeana Collections record webpage with the provided recordId
     * @deprecated October 2018
     * @see EuropeanaUrlBuilder#getRecordPortalUrl(String)
     */
    @Deprecated
    UrlBuilder getPortalRecord(String europeanaId);

    /**
     * @deprecated September 2018 not used anymore
     */
    @Deprecated
    UrlBuilder getCanonicalPortalRecord(String europeanaId);

    /**
     * @deprecated October 2018
     * @see EuropeanaUrlBuilder#getThumbnailUrl(String, String, DocType)
     */
    @Deprecated
    UrlBuilder getThumbnailUrl(String thumbnail, DocType type);

    @Deprecated
    String extractEuropeanaId(String url);
}
