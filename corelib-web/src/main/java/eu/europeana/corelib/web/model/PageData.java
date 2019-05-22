package eu.europeana.corelib.web.model;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import eu.europeana.corelib.definitions.db.entity.relational.User;
import eu.europeana.corelib.web.exception.InvalidUrlException;
import eu.europeana.corelib.web.utils.UrlBuilder;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * @deprecated January 2018 only used for BingTranslateService that is was deprecated earlier
 */
@Deprecated
public abstract class PageData {

	public static final String PARAM_MODEL = "model";

	private User user;

	private String portalServer;

	private String bingTranslateId;

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setPortalServer(String portalServer) {
		this.portalServer = portalServer;
	}

	public String getPortalServer() {
		return portalServer;
	}

	public String getPortalServerSlashless() {
		return StringUtils.stripEnd(portalServer, "/");
	}

	public String getBingTranslateId() {
		return bingTranslateId;
	}

	public void setBingTranslateId(String bingTranslateId) {
		this.bingTranslateId = bingTranslateId;
	}

//	private static final String EUROPEANA_PROVIDERS = "europeana-providers.html";
//
//	private static final String PROVIDER_DESCRIPTION = ", Overview of collections included in Europeana";

//	private Locale locale;

//	private boolean debug = false;

//	private boolean minify = true;

//	private boolean indexable = false;

//	private String googleAnalyticsId = "UA-XXXXXXXX-1";
//
//	private String googleMapsId;
//
//	private String addThisId;
//
//	private String shareThisId;
//
//	private String facebookId;
//
//	private String cacheUrl;

//	private PageInfo pageInfo;
//
//	private String pageTitle;

//	private String metaCanonicalUrl;

//	private String currentUrl;
//
//	private boolean embedded = false;
//
//	private String announceMsg = null;

//	public void setLocale(Locale locale) {
//		this.locale = locale;
//	}
//
//	public Locale getLocale() {
//		return locale;
//	}

//	public void setDebug(boolean debug) {
//		this.debug = debug;
//		this.minify = !debug;
//	}
//
//	public boolean isDebug() {
//		return debug;
//	}

//	public void setMinify(boolean minify) {
//		this.minify = minify;
//	}
//
//	public boolean isMinify() {
//		return minify;
//	}

//	public void setIndexable(boolean indexable) {
//		this.indexable = indexable;
//	}
//
//	public boolean isIndexable() {
//		return indexable;
//	}

//	public final boolean isIndexingBlocked() {
//		return !indexable;
//	}

//	public void setGoogleAnalyticsId(String googleAnalyticsId) {
//		this.googleAnalyticsId = googleAnalyticsId;
//	}

//	public String getGoogleAnalyticsId() {
//		return googleAnalyticsId;
//	}

//	public void setCacheUrl(String cacheUrl) {
//		this.cacheUrl = cacheUrl;
//	}
//
//	public String getCacheUrl() {
//		return cacheUrl;
//	}

//	public void setPageInfo(PageInfo pageInfo) {
//		this.pageInfo = pageInfo;
//	}

//	public PageInfo getPageInfo() {
//		return pageInfo;
//	}
//
//	public String getPageName() {
//		return getPageInfo().getPageName();
//	}
//
//	public void setEmbedded(boolean embedded) {
//		this.embedded = embedded;
//	}
//
//	public boolean isEmbedded() {
//		return embedded;
//	}

//	public String getEmbeddedString() {
//		return embedded ? "true" : "false";
//	}
//
//	public void setPageTitle(String pageTitle) {
//		this.pageTitle = pageTitle;
//	}
//
//	public String getPageTitle() {
//		String pageTitle = "";
//		if (StringUtils.isBlank(this.pageTitle)) {
//			if (pageInfo != null && !StringUtils.isBlank(pageInfo.getPageTitle())) {
//				pageTitle = pageInfo.getPageTitle();
//			}
//		} else {
//			pageTitle = this.pageTitle;
//		}
//
//		return StringUtils.equals(getPageName(), EUROPEANA_PROVIDERS)
//			? pageTitle + PROVIDER_DESCRIPTION
//			: pageTitle;
//	}

//	public void setCurrentUrl(String currentUrl) {
//		this.currentUrl = currentUrl;
//	}
//
//	public String getCurrentUrl() {
//		return StringUtils.defaultIfBlank(currentUrl, "");
//	}
//
//	public void setGoogleMapsId(String googleMapsId) {
//		this.googleMapsId = googleMapsId;
//	}
//
//	public String getGoogleMapsId() {
//		return googleMapsId;
//	}
//
//	public void setAddThisId(String addThisId) {
//		this.addThisId = addThisId;
//	}
//
//	public String getAddThisId() {
//		return addThisId;
//	}
//
//	public void setShareThisId(String shareThisId) {
//		this.shareThisId = shareThisId;
//	}
//
//	public String getShareThisId() {
//		return shareThisId;
//	}
//
//	public boolean isShowDidYouMean() {
//		return false;
//	}

//	public String getMetaCanonicalUrl() {
//		UrlBuilder url = new UrlBuilder(getCurrentUrl());
//		url.setDomain(metaCanonicalUrl);
//		try {
//			return url.toCanonicalUrl();
//		} catch (InvalidUrlException e) {
//			// ignore. should never happen
//			return StringUtils.defaultIfBlank(metaCanonicalUrl, getCurrentUrl());
//		}
//	}
//
//	public void setMetaCanonicalUrl(String metaCanonicalUrl) {
//		this.metaCanonicalUrl = metaCanonicalUrl;
//	}

//	public String getFacebookId() {
//		return facebookId;
//	}
//
//	public void setFacebookId(String facebookId) {
//		this.facebookId = facebookId;
//	}
//

//
//	public String getAnnounceMsg() {
//		return announceMsg;
//	}
//
//	public void setAnnounceMsg(String announceMsg) {
//		this.announceMsg = announceMsg;
//	}

}
