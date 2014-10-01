package eu.europeana.corelib.web.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;

public class Configuration {

	public static final String FACET_UCG_FILTER = "-UGC:true";
	public static final String FACET_TYPE = "TYPE:";
	public static final int DEFAULT_KEYWORD_LANGUAGES_LIMIT = 6;
	public static final String OLD_MYEUROPEANA_URL = "myeuropeana.html";
	public static final String NEW_MYEUROPEANA_URL = "myeuropeana";

	@Resource
	private Properties europeanaProperties;

	// basic portal value
	@Value("#{europeanaProperties['portal.name']}")
	private String portalName;

	@Value("#{europeanaProperties['portal.server']}")
	private String portalServer;

	@Value("#{europeanaProperties['portal.server.canonical']}")
	private String cannonicalPortalServer;

	@Value("#{europeanaProperties['portal.theme']}")
	private String defaultTheme;

	@Value("#{europeanaProperties['static.page.path']}")
	private String staticPagePath;

	@Value("#{europeanaProperties['static.page.checkFrequencyInMinute']}")
	private Integer staticPageCheckFrequencyInMinute;

	@Value("#{europeanaProperties['static.page.suffix']}")
	private String staticPageSuffix;

	@Value("#{europeanaProperties['static.page.in.versions']}")
	private String staticPageInVersionsString;

	// blog settings
	@Value("#{europeanaProperties['portal.blog.url']}")
	private String blogUrl;

	@Value("#{europeanaProperties['portal.blog.timeout']}")
	private Integer blogTimeout;

	// Pinterest settings
	@Value("#{europeanaProperties['portal.pinterest.url']}")
	private String pintUrl;

	@Value("#{europeanaProperties['portal.pinterest.feedurl']}")
	private String pintFeedUrl;

	@Value("#{europeanaProperties['portal.pinterest.timeout']}")
	private Integer pintTimeout;

	@Value("#{europeanaProperties['portal.pinterest.itemslimit']}")
	private Integer pintItemLimit;

	// Google+ settings
	@Value("#{europeanaProperties['portal.google.plus.publisher.id']}")
	private String portalGooglePlusPublisherId;

	// responsive images in the index page
	@Value("#{europeanaProperties['portal.responsive.widths']}")
	private String responsiveImageWidthString;

	@Value("#{europeanaProperties['portal.responsive.carousel.widths']}")
	private String responsiveCarouselImageWidthString;

	@Value("#{europeanaProperties['portal.responsive.labels']}")
	private String responsiveImageLabelString;

	@Value("#{europeanaProperties['portal.responsive.carousel.labels']}")
	private String responsiveCarouselImageLabelString;

	@Value("#{europeanaProperties['portal.shownAtProviderOverride']}")
	private String[] shownAtProviderOverride;

	// API settings
	@Value("#{europeanaProperties['api2.url']}")
	private String api2url;

	@Value("#{europeanaProperties['api2.canonical.url']}")
	private String api2canonicalUrl;

	@Value("#{europeanaProperties['api2.key']}")
	private String api2key;

	@Value("#{europeanaProperties['api2.secret']}")
	private String api2secret;

	// Schema.org maping
	@Value("#{europeanaProperties['schema.org.mapping']}")
	private String schemaOrgMappingFile;

	@Value("#{europeanaProperties['imageCacheUrl']}")
	private String imageCacheUrl;

	@Value("#{europeanaProperties['portal.minCompletenessToPromoteInSitemaps']}")
	private int minCompletenessToPromoteInSitemaps;

	@Value("#{europeanaProperties['portal.contentchecker']}")
	private String isContentChecker;

	@Value("#{europeanaProperties['portal.rowLimit']}")
	private String rowLimit;

	@Value("#{europeanaProperties['debug']}")
	private String debug;

	@Value("#{europeanaProperties['portal.responsive.cache']}")
	private String responsiveCache;

	@Value("#{europeanaProperties['portal.responsive.cache.checkFrequencyInMinute']}")
	private Integer responsiveCacheCheckFrequencyInMinute;

	@Value("#{europeanaProperties['api.optOutList']}")
	private String optOutList;

	@Value("#{europeanaProperties['api.rowLimit']}")
	private int apiRowLimit = 96;

	@Value("#{europeanaProperties['portal.sitemap.cache']}")
	private String sitemapCache;

	@Value("#{europeanaProperties['portal.soundCloudAwareCollections']}")
	private String soundCloudAwareCollectionsString;

	@Value("#{europeanaProperties['portal.mlt.weight.title']}")
	private double weightTitle;

	@Value("#{europeanaProperties['portal.mlt.weight.who']}")
	private double weightWho;

	@Value("#{europeanaProperties['portal.mlt.weight.what']}")
	private double weightWhat;

	@Value("#{europeanaProperties['portal.mlt.weight.provider']}")
	private double weightProvider;

	@Value("#{europeanaProperties['portal.mlt.weight.dataProvider']}")
	private double weightDataProvider;

	@Value("#{europeanaProperties['portal.keywordLanguagesLimit']}")
	private Integer keywordLanguagesLimit;

	@Value("#{europeanaProperties['portal.useNewMyEuropeanaUrl']}")
	private String useNewMyEuropeanaUrlString;

	@Value("#{europeanaProperties['portal.bing.translate.key']}")
	private String bingTranslateId;

	@Value("#{europeanaProperties['portal.useBackendItemTranslation']}")
	private String useBackendItemTranslationString;

	@Value("#{europeanaProperties['portal.soundcloud.clientID']}")
	private String soundcloudClientID;

	@Value("#{europeanaProperties['portal.hierarchyRoots']}")
	private String hierarchyRootsString;

	
	
	

	@Value("#{europeanaProperties['portal.bing.translate.clientId']}")
	private String bingTranslateClientId;

	public String getBingTranslateClientId() {
		return bingTranslateClientId;
	}

	public void setBingTranslateClientId(String bingTranslateClientId) {
		this.bingTranslateClientId = bingTranslateClientId;
	}

	@Value("#{europeanaProperties['portal.bing.translate.clientSecret']}")
	private String bingTranslateClientSecret;

	
	public String getBingTranslateClientSecret() {
		return bingTranslateClientSecret;
	}

	public void setBingTranslateClientSecret(String bingTranslateClientSecret) {
		this.bingTranslateClientSecret = bingTranslateClientSecret;
	}


	
	
	
	
	// ///////////////////////////// generated/derivated properties

	private Map<String, String> seeAlsoTranslations;

	private Map<String, String> seeAlsoAggregations;

	private Map<String, String> mltTranslations;

	private String portalUrl;

	private List<String> staticPageInVersions;

	/** Collection IDs which supports the SoundCloud widget */
	private List<String> soundCloudAwareCollections;

	private Boolean useNewMyEuropeanaUrl;

	private List<String> hierarchyRoots;

	// /////////////////////////////// getters and setters

	public String getPortalName() {
		return portalName;
	}

	public String getPortalServer() {
		return portalServer;
	}

	public String getCannonicalPortalServer() {
		return cannonicalPortalServer;
	}

	public String[] getShownAtProviderOverride() {
		return shownAtProviderOverride;
	}

	public String getApi2url() {
		return api2url;
	}

	public String getApi2CanonicalUrl() {
		return api2canonicalUrl;
	}

	public String getApi2key() {
		return api2key;
	}

	public String getApi2secret() {
		return api2secret;
	}

	public String getSchemaOrgMappingFile() {
		return schemaOrgMappingFile;
	}

	public String getBlogUrl() {
		return blogUrl;
	}

	public int getBlogTimeout() {
		return blogTimeout.intValue();
	}

	public String getPintUrl() {
		return pintUrl;
	}

	public String getPintFeedUrl() {
		return pintFeedUrl;
	}

	public int getPintTimeout() {
		return pintTimeout.intValue();
	}

	public int getPintItemLimit() {
		return pintItemLimit.intValue();
	}

	public String getDefaultTheme() {
		return defaultTheme;
	}

	public String getStaticPagePath() {
		return staticPagePath;
	}

	public String getStaticPageSuffix() {
		return staticPageSuffix;
	}

	public String getPortalGooglePlusPublisherId() {
		return portalGooglePlusPublisherId;
	}

	public String getImageCacheUrl() {
		return imageCacheUrl;
	}

	public int getMinCompletenessToPromoteInSitemaps() {
		return minCompletenessToPromoteInSitemaps;
	}

	public boolean isContentChecker() {
		if (StringUtils.isBlank(isContentChecker)) {
			return false;
		}
		return Boolean.parseBoolean(isContentChecker);
	}

	public int getRowLimit() {
		return Integer.parseInt(rowLimit);
	}

	public boolean getDebugMode() {
		if (StringUtils.isBlank(debug)) {
			return false;
		}
		return Boolean.parseBoolean(debug);
	}

	public String getResponsiveCache() {
		return responsiveCache;
	}

	public Integer getResponsiveCacheCheckFrequencyInMinute() {
		return responsiveCacheCheckFrequencyInMinute;
	}

	public Map<String, String> getSeeAlsoTranslations() {
		if (seeAlsoTranslations == null) {
			seeAlsoTranslations = new HashMap<String, String>();
			int i = 1;
			while (europeanaProperties.containsKey("portal.seeAlso.field." + i)) {
				String[] parts = europeanaProperties.getProperty("portal.seeAlso.field." + i).split("=", 2);
				seeAlsoTranslations.put(parts[0].trim(), parts[1].trim());
				i++;
			}
		}
		return seeAlsoTranslations;
	}

	public Map<String, String> getMltTranslations() {
		if (mltTranslations == null) {
			mltTranslations = new HashMap<String, String>();
			int i = 1;
			while (europeanaProperties.containsKey("portal.mlt.field." + i)) {
				String[] parts = europeanaProperties.getProperty("portal.mlt.field." + i).split("=", 2);
				mltTranslations.put(parts[0].trim(), parts[1].trim());
				i++;
			}
		}
		return mltTranslations;
	}

	public Map<String, String> getSeeAlsoAggregations() {
		if (seeAlsoAggregations == null) {
			seeAlsoAggregations = new HashMap<String, String>();
			int i = 1;
			String key = "portal.seeAlso.aggregation.";
			while (europeanaProperties.containsKey(key + i)) {
				String[] parts = europeanaProperties.getProperty(key + i).split(",");
				for (int j = 1, l = parts.length; j < l; j++) {
					seeAlsoAggregations.put(parts[j].trim(), parts[0].trim());
				}
				i++;
			}
		}
		return seeAlsoAggregations;
	}

	public String getPortalUrl() {
		if (portalUrl == null) {
			StringBuilder sb = new StringBuilder(portalServer);
			if (!portalServer.endsWith("/") && !portalName.startsWith("/")) {
				sb.append("/");
			}
			sb.append(portalName);
			portalUrl = sb.toString();
		}
		return portalUrl;
	}

	public List<String> getStaticPageInVersions() {
		if (staticPageInVersions == null) {
			staticPageInVersions = new ArrayList<String>();
			if (StringUtils.isNotBlank(staticPageInVersionsString)) {
				String[] items = staticPageInVersionsString.split(",");
				for (String item : items) {
					staticPageInVersions.add(item.trim());
				}
			}
		}
		return staticPageInVersions;
	}

	public List<String> getSoundCloudAwareCollections() {
		if (soundCloudAwareCollections == null) {
			soundCloudAwareCollections = new ArrayList<String>();
			if (StringUtils.isNotBlank(soundCloudAwareCollectionsString)) {
				String[] items = soundCloudAwareCollectionsString.split(",");
				for (String item : items) {
					soundCloudAwareCollections.add(item.trim());
				}
			}
		}
		return soundCloudAwareCollections;
	}

	public List<String> getHierarchyRoots() {
		if (hierarchyRoots == null) {
			hierarchyRoots = new ArrayList<String>();
			if (StringUtils.isNotBlank(hierarchyRootsString)) {
				String[] items = hierarchyRootsString.split(",");
				for (String item : items) {
					hierarchyRoots.add(item.trim());
				}
			}
		}
		return hierarchyRoots;
	}

	public String getOptOutList() {
		return optOutList;
	}

	public String getSitemapCache() {
		return sitemapCache;
	}
	
	public int getApiRowLimit() {
		return apiRowLimit;
	}

	public double getWeightTitle() {
		return weightTitle;
	}

	public double getWeightWho() {
		return weightWho;
	}

	public double getWeightWhat() {
		return weightWhat;
	}

	public double getWeightProvider() {
		return weightProvider;
	}

	public double getWeightDataProvider() {
		return weightDataProvider;
	}

	public int getKeywordLanguagesLimit() {
		return (keywordLanguagesLimit == null) ? DEFAULT_KEYWORD_LANGUAGES_LIMIT : keywordLanguagesLimit;
	}

	public String getMyEuropeanaUrl() {
		if (useNewMyEuropeanaUrl == null) {
			if (StringUtils.isBlank(useNewMyEuropeanaUrlString)) {
				useNewMyEuropeanaUrl = false;
			} else {
				useNewMyEuropeanaUrl = Boolean.parseBoolean(useNewMyEuropeanaUrlString.trim());
			}
		}
		return (useNewMyEuropeanaUrl) ? NEW_MYEUROPEANA_URL : OLD_MYEUROPEANA_URL;
	}

	public boolean useBackendTranslation() {
		if (StringUtils.isBlank(useBackendItemTranslationString)) {
			return false;
		}
		return Boolean.parseBoolean(useBackendItemTranslationString);
	}

	public String getBingTranslateId() {
		return bingTranslateId;
	}

	public String getSoundcloudClientID() {
		return soundcloudClientID;
	}

	public void setSoundcloudClientID(String soundcloudClientID) {
		this.soundcloudClientID = soundcloudClientID;
	}
}
