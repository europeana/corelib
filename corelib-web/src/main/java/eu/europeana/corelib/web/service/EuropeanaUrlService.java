package eu.europeana.corelib.web.service;

import java.io.UnsupportedEncodingException;

import eu.europeana.corelib.definitions.solr.DocType;
import eu.europeana.corelib.web.utils.UrlBuilder;

public interface EuropeanaUrlService {
	
	static final String PARAM_SEARCH_QUERY 	= "query";
	static final String PARAM_SEARCH_ROWS 	= "rows";
	static final String PARAM_SEARCH_START 	= "start";
	static final String PARAM_SEARCH_FACET 	= "qf";

	static final String PARAM_API_V2_APIKEY	= "wskey";
	
	static final String URL_IMAGE_SITE = "http://europeanastatic.eu/api/image";
	
	UrlBuilder getApi2Home(String apikey);
	
	UrlBuilder getApi2SearchJson(String apikey, String query, String rows) throws UnsupportedEncodingException;

	UrlBuilder getApi2RecordJson(String apikey, String collectionid, String objectid);

	UrlBuilder getApi2RecordJson(String apikey, String europeanaId);
	
	UrlBuilder getApi2Redirect(int uid, String showAt, String provider, String europeanaId, String profile);
	
	UrlBuilder getPortalHome(boolean relative);

	UrlBuilder getPortalResolve(String europeanaId);
	
	UrlBuilder getPortalSearch(boolean relative, String query, String rows) throws UnsupportedEncodingException;

	UrlBuilder getPortalSearch(boolean relative, String searchpage, String query, String rows) throws UnsupportedEncodingException;
	
	UrlBuilder getPortalRecord(boolean relative, String collectionid, String objectid);
	
	UrlBuilder getPortalRecord(boolean relative, String europeanaId);

	UrlBuilder getThumbnailUrl(String thumbnail, DocType type);
	
}
