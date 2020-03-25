package eu.europeana.corelib.web.service.impl;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;

import eu.europeana.corelib.definitions.EuropeanaStaticUrl;
import org.apache.commons.lang3.StringUtils;

import eu.europeana.corelib.definitions.ApplicationContextContainer;
import eu.europeana.corelib.definitions.model.ThumbSize;
import eu.europeana.corelib.definitions.solr.DocType;
import eu.europeana.corelib.utils.EuropeanaUriUtils;
import eu.europeana.corelib.web.service.EuropeanaUrlService;
import eu.europeana.corelib.web.support.Configuration;
import eu.europeana.corelib.web.utils.UrlBuilder;

/**
 * @deprecated Please use the EuropeanaStaticUrl or EuropeanaUrlBuilder instead
 */
@Deprecated
public class EuropeanaUrlServiceImpl implements EuropeanaUrlService {

	@Resource
	private Configuration configuration;

	/**
	 * Avoid this method as much as possible, because it will not work for other services that depend on Corelib and
	 * have not loaded this as a bean.
	 * @return
	 * @deprecated
	 */
	@Deprecated
	public static EuropeanaUrlService getBeanInstance() {
		return ApplicationContextContainer.getBean(EuropeanaUrlServiceImpl.class);
	}

    /**
     * @deprecated September 2018 not used anymore
     */
    @Deprecated
	@Override
	public UrlBuilder getApi1Home(String apikey) {
		UrlBuilder url = new UrlBuilder(configuration.getApi2url());
		url.addPath(PATH_API_V1);
		url.addParam(PARAM_API_APIKEY, apikey, true);
		return url;
	}

	@Deprecated
	@Override
	public UrlBuilder getApi2Home(String apikey) {
		UrlBuilder url = new UrlBuilder(configuration.getApi2url());
		url.addPath(PATH_API_V2);
		url.addParam(PARAM_API_APIKEY, apikey, true);
		return url;
	}

    /**
     * @deprecated September 2018 not used anymore
     */
    @Deprecated
	@Override
	public UrlBuilder getApi1SearchJson(String apikey, String query, int start) throws UnsupportedEncodingException {
		UrlBuilder url = getApi1Home(apikey);
		url.addPage("search.json");
		url.addParam(PARAM_API_V1_SEARCH_QUERY, query, true);
		url.addParam(PARAM_API_V1_SEARCH_START, start);
		return url;
	}

	@Override
	public UrlBuilder getApi2SearchJson(String apikey, String query, String rows) throws UnsupportedEncodingException {
		UrlBuilder url = getApi2Home(apikey);
		url.addPage("search.json");
		url.addParam(PARAM_SEARCH_QUERY, query);
		url.addParam(PARAM_SEARCH_ROWS, rows);
		return url;
	}

	@Deprecated
	@Override
	public UrlBuilder getApi2RecordJson(String apikey, String collectionid, String objectid) {
		UrlBuilder url = getApi2Home(apikey);
		url.addPath(PATH_RECORD, collectionid).addPage(objectid + EXT_JSON);
		return url;
	}

	@Deprecated
	@Override
	public UrlBuilder getApi2RecordJson(String apikey, String europeanaId) {
		return getApi2Record(apikey, europeanaId, EXT_JSON);
	}

	@Deprecated
	@Override
	public UrlBuilder getApi1Record(String apikey, String europeanaId, String extention) {
		UrlBuilder url = getApi1Home(apikey);
		url.addPath(PATH_RECORD).addPage(europeanaId+extention);
		return url;
	}

	@Deprecated
	@Override
	public UrlBuilder getApi2Record(String apikey, String europeanaId, String extention) {
		UrlBuilder url = getApi2Home(apikey);
		url.addPath(PATH_RECORD).addPage(europeanaId+extention);
		return url;
	}

	@Deprecated
	@Override
	public UrlBuilder getApi2Redirect(String apikey, String shownAt, String provider, String europeanaId, String profile) {
		UrlBuilder url = new UrlBuilder(configuration.getApi2url());
		url.addPath(String.valueOf(apikey), PATH_API_REDIRECT).disableTrailingSlash();
		url.addParam("shownAt", shownAt);
		url.addParam("provider", provider);
		url.addParam("id", getPortalResolve(europeanaId));
		url.addParam("profile", profile);
		return url;
	}

	/**
	 * @deprecated October 2018 not used anymore
	 */
	@Deprecated
	@Override
	public UrlBuilder getPortalHome() {
		return new UrlBuilder(configuration.getPortalServer());
	}

	/**
	 * @deprecated October 2018 not used anymore
	 */
	@Deprecated
	@Override
	public String getPortalResolve(String europeanaId) {
		UrlBuilder url = new UrlBuilder(EuropeanaStaticUrl.EUROPEANA_PORTAL_URL);
		url.addPath(PATH_PORTAL_RESOLVE, PATH_RECORD, europeanaId).disableTrailingSlash();
		return url.toString();
	}

	/**
	 * @deprecated October 2018 not used anymore
	 */
	@Deprecated
	@Override
	public String getPortalResolve(String collectionid, String objectid) {
		UrlBuilder url = new UrlBuilder(EuropeanaStaticUrl.EUROPEANA_PORTAL_URL);
		url.addPath(PATH_PORTAL_RESOLVE, PATH_RECORD, collectionid, objectid).disableTrailingSlash();
		return url.toString();
	}

	/**
	 * @deprecated September 2018 not used anymore
	 */
	@Deprecated
	@Override
	public UrlBuilder getPortalSearch() throws UnsupportedEncodingException {
		return getPortalSearch(true, "search.html", null, null);
	}

	/**
	 * @deprecated September 2018 not used anymore
	 */
	@Deprecated
	@Override
	public UrlBuilder getPortalSearch(boolean relative, String query, String rows) throws UnsupportedEncodingException {
		return getPortalSearch(relative, "search.html", query, rows);
	}

    /**
     * @deprecated September 2018 not used anymore
     */
	@Deprecated
	@Override
	public UrlBuilder getPortalSearch(boolean relative, String searchpage, String query, String rows)
			throws UnsupportedEncodingException {
		UrlBuilder url = getPortalHome();
		url.addPage(searchpage);
		url.addParam(PARAM_SEARCH_QUERY, query, true);
		url.addParam(PARAM_SEARCH_ROWS, rows, true);
		return url;
	}

	/**
	 * @deprecated September 2018 please use #EuropeanaUrlBuilder.getPortalRecord(collectionid, objectid) method
	 */
    @Deprecated
    public UrlBuilder getPortalRecord(boolean relative, String collectionid, String objectid) {
        return getPortalRecord(collectionid, objectid);
    }

	/**
	 * @deprecated September 2018 please use #EuropeanaUrlBuilder.getPortalRecord(europeanaId) method
	 */
    @Deprecated
    public UrlBuilder getPortalRecord(boolean relative, String europeanaId) {
        return getPortalRecord(europeanaId);
    }

	/**
	 * @deprecated October 2018 please use #EuropeanaUrlBuilder.getPortalRecord(collectionid, objectid) method
	 */
	@Deprecated
	@Override
	public UrlBuilder getPortalRecord(String collectionid, String objectid) {
		UrlBuilder url = getPortalHome();
		url.addPath(PATH_RECORD, collectionid).addPage(objectid);
		return url;
	}

	/**
	 * @deprecated October 2018
	 * @see EuropeanaUrlBuilder#getRecordPortalUrl(String)
	 */
	@Deprecated
	@Override
	public UrlBuilder getPortalRecord(String europeanaId) {
		UrlBuilder url = getPortalHome();
		url.addPath(PATH_RECORD).addPage(europeanaId);
		return url;
	}

    /**
     * @deprecated September 2018 not used anymore
     */
	@Deprecated
	@Override
	public UrlBuilder getCanonicalPortalRecord(String europeanaId) {
		UrlBuilder url = new UrlBuilder(EuropeanaStaticUrl.EUROPEANA_PORTAL_URL);
		url.addPath(PATH_RECORD).addPage(europeanaId);
		return url;
	}

	/**
	 * @deprecated October 2018
	 * @see EuropeanaUrlBuilder#getThumbnailUrl(String, String, DocType)
	 */
	@Deprecated
	@Override
	public UrlBuilder getThumbnailUrl(String thumbnail, DocType type) {
        if (null == configuration.getImageCacheUrl()) {
            return new UrlBuilder("");
        }
		UrlBuilder url = new UrlBuilder(configuration.getImageCacheUrl());
        if(thumbnail != null) {
            url.addParam("uri", thumbnail.trim());
        }
		url.addParam("size", ThumbSize.LARGE.toString());
		if(type != null) {
            url.addParam("type", type.toString());
        }
		return url;
	}

	@Deprecated
	@Override
	public String extractEuropeanaId(String url) {
		if (StringUtils.contains(url, PATH_RECORD)) {
			url = StringUtils.removeEnd(url, EXT_HTML);
			String recordId = StringUtils.substringAfterLast(url, UrlBuilder.PATH_SEPERATOR);
			url = StringUtils.substringBeforeLast(url, UrlBuilder.PATH_SEPERATOR);
			String collectionId = StringUtils.substringAfterLast(url, UrlBuilder.PATH_SEPERATOR);
			return EuropeanaUriUtils.createEuropeanaId(collectionId, recordId);
		}
		return null;
	}
}
