package eu.europeana.corelib.web.service;

import java.io.UnsupportedEncodingException;

import eu.europeana.corelib.web.utils.UrlBuilder;

public interface EuropeanaUrlService {
	
	static final String PARAM_SEARCH_QUERY 	= "query";
	static final String PARAM_SEARCH_ROWS 	= "rows";
	static final String PARAM_SEARCH_START 	= "start";
	static final String PARAM_SEARCH_FACET 	= "qf";

	static final String PARAM_API_V2_APIKEY	= "wskey";
	
	UrlBuilder getApi2Home(String apikey);
	
	UrlBuilder getApi2SearchJson(String apikey, String query, String rows) throws UnsupportedEncodingException;

	UrlBuilder getApi2RecordJson(String apikey, String collectionid, String objectid);
	
	UrlBuilder getPortalHome(boolean relative);
	
	UrlBuilder getPortalSearch(boolean relative, String query, String rows) throws UnsupportedEncodingException;

	UrlBuilder getPortalSearch(boolean relative, String searchpage, String query, String rows) throws UnsupportedEncodingException;
	
	UrlBuilder getPortalRecord(boolean relative, String collectionid, String objectid);
	
}
