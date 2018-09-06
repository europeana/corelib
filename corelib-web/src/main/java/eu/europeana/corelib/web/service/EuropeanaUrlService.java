package eu.europeana.corelib.web.service;

import java.io.UnsupportedEncodingException;

import eu.europeana.corelib.definitions.solr.DocType;
import eu.europeana.corelib.web.utils.UrlBuilder;

public interface EuropeanaUrlService {
	// HARDCODED URLS
	String URL_EUROPEANA 		= "https://www.europeana.eu";

	// ENCODING
	String ENC_UTF8 			= "UTF-8";
	
	// GENERAL PATHS
	String PATH_RECORD 		= "record";
	
	// PORTAL PATHS
	String PATH_PORTAL 		= "portal";
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
	String EXT_SRW				= ".srw";

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

	UrlBuilder getApi2Home(String apikey);

	/**
	 * @deprecated September 2018 not used anymore
	 */
	@Deprecated
	UrlBuilder getApi1SearchJson(String apikey, String query, int start) throws UnsupportedEncodingException;

	UrlBuilder getApi2SearchJson(String apikey, String query, String rows) throws UnsupportedEncodingException;

	UrlBuilder getApi2RecordJson(String apikey, String collectionid, String objectid);

	UrlBuilder getApi2RecordJson(String apikey, String europeanaId);

	/**
	 * @deprecated September 2018 not used anymore
	 */
	@Deprecated
	UrlBuilder getApi1Record(String apikey, String europeanaId, String extention);

	UrlBuilder getApi2Record(String apikey, String europeanaId, String extention);

	UrlBuilder getApi2Redirect(String apikey, String showAt, String provider, String europeanaId, String profile);

	/**
	 * @return base url of Europeana Collections homepage a.k.a. Europeana Portal
	 */
	UrlBuilder getPortalHome();

	String getPortalResolve(String europeanaId);

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
	 * @deprecated September 2018 please use getPortalRecord(collectionid, objectid) method
	 */
	@Deprecated
	UrlBuilder getPortalRecord(boolean relative, String collectionid, String objectid);

	/**
	 * @deprecated September 2018 please use getPortalRecord(europeanaId) method
	 */
	@Deprecated
	UrlBuilder getPortalRecord(boolean relative, String EuropeanaId);

	/**
	 *
	 * @param collectionid
	 * @param objectid
	 * @return UrlBuilder that points to the Europeana Collections record webpage with the provided recordId
	 */
	UrlBuilder getPortalRecord(String collectionid, String objectid);

	/**
	 *
	 * @param europeanaId
	 * @return  UrlBuilder that points to the Europeana Collections record webpage with the provided recordId
	 */
	UrlBuilder getPortalRecord(String europeanaId);

	/**
	 * @deprecated September 2018 not used anymore
	 */
	@Deprecated
	UrlBuilder getCanonicalPortalRecord(String europeanaId);

	UrlBuilder getThumbnailUrl(String thumbnail, DocType type);

	String extractEuropeanaId(String url);
}
