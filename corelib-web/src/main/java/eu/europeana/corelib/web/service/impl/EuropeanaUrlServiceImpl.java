package eu.europeana.corelib.web.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.annotation.Resource;

import eu.europeana.corelib.definitions.ApplicationContextContainer;
import eu.europeana.corelib.web.service.EuropeanaUrlService;
import eu.europeana.corelib.web.support.Configuration;
import eu.europeana.corelib.web.utils.UrlBuilder;

public class EuropeanaUrlServiceImpl implements EuropeanaUrlService {

	@Resource
	private Configuration configuration;
	
	public static EuropeanaUrlService getBeanInstance() {
		return ApplicationContextContainer.getBean(EuropeanaUrlServiceImpl.class);
	}
	
	@Override
	public UrlBuilder getApi2Home(String apikey) {
		UrlBuilder url = new UrlBuilder(configuration.getApi2url());
		url.addPath("v2").addParam(PARAM_API_V2_APIKEY, apikey, true);
		return url;
	}

	@Override
	public UrlBuilder getApi2SearchJson(String apikey, String query, String rows) throws UnsupportedEncodingException {
		UrlBuilder url = getApi2Home(apikey);
		url.addPage("search.json").addParam(PARAM_SEARCH_QUERY, URLEncoder.encode(query, "UTF-8"), true);
		url.addParam(PARAM_SEARCH_ROWS, rows, true);
		return url;
	}
	
	@Override
	public UrlBuilder getApi2RecordJson(String apikey, String collectionid, String objectid) {
		UrlBuilder url = getApi2Home(apikey);
		url.addPath("record", collectionid).addPage(objectid+".json");
		return url;
	}
	
	@Override
	public UrlBuilder getPortalHome(boolean relative) {
		UrlBuilder url;
		if (relative) {
			url = new UrlBuilder("/");
		} else {
			url = new UrlBuilder(configuration.getPortalServer());
		}
		url.addPath(configuration.getPortalName());
		return url;
	}

	@Override
	public UrlBuilder getPortalSearch(boolean relative, String query, String rows) throws UnsupportedEncodingException {
		return getPortalSearch(relative, "search.html", query, rows);
	}
	
	@Override
	public UrlBuilder getPortalSearch(boolean relative, String searchpage, String query, String rows)
			throws UnsupportedEncodingException {
		UrlBuilder url = getPortalHome(relative);
		url.addPage(searchpage).addParam(PARAM_SEARCH_QUERY, URLEncoder.encode(query, "UTF-8"), true);
		url.addParam(PARAM_SEARCH_ROWS, rows, true);
		return url;
	}
	
	@Override
	public UrlBuilder getPortalRecord(boolean relative, String collectionid, String objectid) {
		UrlBuilder url = getPortalHome(relative);
		url.addPath("record", collectionid).addPage(objectid+".html");
		return url;
	}

}
