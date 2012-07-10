/*
 * Copyright 2007 EDL FOUNDATION
 *
 * Licensed under the EUPL, Version 1.0 or - as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "License");
 * you may not use this work except in compliance with the
 * License.
 * You may obtain a copy of the License at:
 *
 * http://ec.europa.eu/idabc/eupl
 *
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the License is
 * distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the License for the specific language governing
 * permissions and limitations under the License.
 */

package eu.europeana.corelib.web.model;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import eu.europeana.corelib.definitions.db.entity.relational.User;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public abstract class PageData {

	public static final String PARAM_MODEL = "model";

	private static final String EUROPEANA_PROVIDERS = "europeana-providers.html";

	private static final String PROVIDER_DESCRIPTION = ", Overview of collections included in Europeana";

	private Locale locale;

	private User user;

	private boolean debug = false;

	private boolean minify = true;

	private boolean indexable = false;

	private String googleAnalyticsId = "UA-XXXXXXXX-1";

	private String googleMapsId;

	private String bingTranslateId;

	private String addThisId;

	private String facebookId;

	private String portalName;

	private String cacheUrl;

	private String portalServer;

	private PageInfo pageInfo;

	private String pageTitle;

	private String metaCanonicalUrl;

	private String currentUrl;

	private boolean embedded = false;

	private String announceMsg = null;

	// public List<PortalLanguage> getPortalLanguages() {
	// return PortalLanguage.getSupported();
	// }
	//
	// public int getRandomImageNr() {
	// return RandomUtils.nextInt(6)+1;
	// }
	//
	// public String getImageLocale() {
	// PortalLanguage current = PortalLanguage.safeValueOf(getLocale());
	// if (!current.hasImageSupport()) {
	// current = PortalLanguage.EN;
	// }
	// return current.getLanguageCode();
	// }

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
		this.minify = !debug;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setMinify(boolean minify) {
		this.minify = minify;
	}

	public boolean isMinify() {
		return minify;
	}

	public void setIndexable(boolean indexable) {
		this.indexable = indexable;
	}

	public boolean isIndexable() {
		return indexable;
	}

	public final boolean isIndexingBlocked() {
		return !indexable;
	}

	public void setGoogleAnalyticsId(String googleAnalyticsId) {
		this.googleAnalyticsId = googleAnalyticsId;
	}

	public String getGoogleAnalyticsId() {
		return googleAnalyticsId;
	}

	public void setPortalName(String portalName) {
		this.portalName = portalName;
	}

	public String getPortalName() {
		return portalName;
	}

	public void setCacheUrl(String cacheUrl) {
		this.cacheUrl = cacheUrl;
	}

	public String getCacheUrl() {
		return cacheUrl;
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

	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}

	public PageInfo getPageInfo() {
		return pageInfo;
	}

	public String getPageName() {
		return getPageInfo().getPageName();
	}

	public void setEmbedded(boolean embedded) {
		this.embedded = embedded;
	}

	public boolean isEmbedded() {
		return embedded;
	}

	public String getEmbeddedString() {
		return embedded ? "true" : "false";
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	public String getPageTitle() {
		String pageTitle = "";
		if (StringUtils.isBlank(this.pageTitle)) {
			pageTitle = pageInfo.getPageTitle();
		} else {
			pageTitle = this.pageTitle;
		}

		return StringUtils.equals(getPageName(), EUROPEANA_PROVIDERS) 
			? pageTitle + PROVIDER_DESCRIPTION
			: pageTitle;
	}

	public void setCurrentUrl(String currentUrl) {
		this.currentUrl = currentUrl;
	}

	public String getCurrentUrl() {
		return StringUtils.defaultIfBlank(currentUrl, "");
	}

	public void setGoogleMapsId(String googleMapsId) {
		this.googleMapsId = googleMapsId;
	}

	public String getGoogleMapsId() {
		return googleMapsId;
	}

	public void setAddThisId(String addThisId) {
		this.addThisId = addThisId;
	}

	public String getAddThisId() {
		return addThisId;
	}

	public boolean isShowDidYouMean() {
		return false;
	}

	public String getMetaCanonicalUrl() {
		String canonical = StringUtils.defaultIfBlank(metaCanonicalUrl,
				getCurrentUrl());
		canonical = StringUtils.substringBefore(canonical, "#");
		if (StringUtils.startsWithIgnoreCase(canonical, "http")) {
			return canonical;
		}
		return portalServer
				+ (StringUtils.startsWith(canonical, "/") ? StringUtils
						.substringAfter(canonical, "/") : canonical);
	}

	public void setMetaCanonicalUrl(String metaCanonicalUrl) {
		this.metaCanonicalUrl = metaCanonicalUrl;
	}

	public String getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}

	public String getBingTranslateId() {
		return bingTranslateId;
	}

	public void setBingTranslateId(String bingTranslateId) {
		this.bingTranslateId = bingTranslateId;
	}

	public String getAnnounceMsg() {
		return announceMsg;
	}

	public void setAnnounceMsg(String announceMsg) {
		this.announceMsg = announceMsg;
	}

}
