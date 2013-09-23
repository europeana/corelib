package eu.europeana.corelib.web.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.annotation.Resource;

import eu.europeana.corelib.definitions.ApplicationContextContainer;
import eu.europeana.corelib.definitions.model.ThumbSize;
import eu.europeana.corelib.definitions.solr.DocType;
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
		url.addPath(PATH_API_V2).addParam(PARAM_API_V2_APIKEY, apikey, true);
		return url;
	}

	@Override
	public UrlBuilder getApi2SearchJson(String apikey, String query, String rows) throws UnsupportedEncodingException {
		UrlBuilder url = getApi2Home(apikey);
		url.addPage("search.json").addParam(PARAM_SEARCH_QUERY, URLEncoder.encode(query, ENC_UTF8), true);
		url.addParam(PARAM_SEARCH_ROWS, rows, true);
		return url;
	}
	
	@Override
	public UrlBuilder getApi2RecordJson(String apikey, String collectionid, String objectid) {
		UrlBuilder url = getApi2Home(apikey);
		url.addPath(PATH_RECORD, collectionid).addPage(objectid+EXT_JSON);
		return url;
	}
	
	@Override
	public UrlBuilder getApi2RecordJson(String apikey, String europeanaId) {
		UrlBuilder url = getApi2Home(apikey);
		url.addPath(PATH_RECORD).addPage(europeanaId+EXT_JSON);
		return url;
	}
	
	@Override
	public UrlBuilder getApi2Redirect(long uid, String shownAt, String provider, String europeanaId, String profile) {
		UrlBuilder url = new UrlBuilder(configuration.getApi2url());
		url.addPath(String.valueOf(uid), PATH_API_REDIRECT).disableTrailingSlash();
		url.addParam("shownAt", shownAt).addParam("provider", provider);
		url.addParam("id", getPortalResolve(europeanaId).toString()).addParam("profile", profile);
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
	public UrlBuilder getPortalResolve(String europeanaId) {
		UrlBuilder url = new UrlBuilder(URL_EUROPEANA);
		url.addPath(PATH_PORTAL_RESOLVE,PATH_RECORD, europeanaId).disableTrailingSlash();
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
		url.addPage(searchpage).addParam(PARAM_SEARCH_QUERY, URLEncoder.encode(query, ENC_UTF8), true);
		url.addParam(PARAM_SEARCH_ROWS, rows, true);
		return url;
	}
	
	@Override
	public UrlBuilder getPortalRecord(boolean relative, String collectionid, String objectid) {
		UrlBuilder url = getPortalHome(relative);
		url.addPath(PATH_RECORD, collectionid).addPage(objectid+EXT_HTML);
		return url;
	}
	
	@Override
	public UrlBuilder getPortalRecord(boolean relative, String europeanaId) {
		UrlBuilder url = getPortalHome(relative);
		url.addPath(PATH_RECORD).addPage(europeanaId+EXT_HTML);
		return url;
	}
	
	@Override
	public UrlBuilder getThumbnailUrl(String thumbnail, DocType type) {
		UrlBuilder url = new UrlBuilder(URL_IMAGE_SITE);
		try {
			url.addParam("uri", URLEncoder.encode(thumbnail, ENC_UTF8));
		} catch (UnsupportedEncodingException e) {
		}
		url.addParam("size", ThumbSize.LARGE.toString());
		url.addParam("type", type.toString());
		return url;
	}
	

}
