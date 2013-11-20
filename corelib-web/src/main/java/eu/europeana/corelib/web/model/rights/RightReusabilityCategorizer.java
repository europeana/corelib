package eu.europeana.corelib.web.model.rights;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import eu.europeana.corelib.definitions.model.RightsOption;
import eu.europeana.corelib.definitions.solr.model.QueryFacet;

public class RightReusabilityCategorizer {

	public static final String OPEN = "open";
	public static final String RESTRICTED = "restricted";
	public static final String UNCATEGORIZED = "uncategorized";

	private long numberOfOpen;
	private long numberOfRestricted;

	private static List<String> openUrls = Arrays.asList(new String[]{
		RightsOption.NOC.getUrl(),
		RightsOption.CC_ZERO.getUrl() + "/1.0/",
		RightsOption.CC_BY.getUrl(),
		RightsOption.CC_BY_SA.getUrl()
	});
	private static String openRightsQuery;

	private static List<String> restrictedUrls = Arrays.asList(new String[]{
		RightsOption.CC_BY_NC.getUrl(),
		RightsOption.CC_BY_NC_SA.getUrl(),
		RightsOption.CC_BY_NC_ND.getUrl(),
		RightsOption.CC_BY_ND.getUrl(),
		RightsOption.OOC_NC.getUrl()
	});
	private static String restrictedRightsQuery;
	private static String allRightsQuery;

	private static Map<String, String> examinedUrlsMap = new HashMap<String, String>();
	private static List<QueryFacet> queryFacets;

	public RightReusabilityCategorizer() {
		numberOfOpen = 0;
		numberOfRestricted = 0;
	}

	public String categorize(String url, long count) {
		String cleanedUrl = cleanUrl(url);
		String category = null;
		if (StringUtils.isBlank(cleanedUrl)) {
			return category;
		}

		if (examinedUrlsMap.containsKey(cleanedUrl)) {
			category = examinedUrlsMap.get(cleanedUrl);
			if (category.equals(OPEN)) {
				numberOfOpen += count;
			} else if (category.equals(RESTRICTED)) {
				numberOfRestricted += count;
			}
		} else {
			for (String rightUrl : openUrls) {
				if (cleanedUrl.startsWith(rightUrl)) {
					numberOfOpen += count;
					category = OPEN;
					examinedUrlsMap.put(cleanedUrl, category);
					break;
				}
			}
			if (category == null) {
				for (String rightUrl : restrictedUrls) {
					// log.info(rightUrl);
					if (cleanedUrl.startsWith(rightUrl)) {
						numberOfRestricted += count;
						category = RESTRICTED;
						examinedUrlsMap.put(cleanedUrl, category);
						break;
					}
				}
			}
			if (category == null) {
				examinedUrlsMap.put(cleanedUrl, UNCATEGORIZED);
			}
		}
		return category;
	}

	public static String getOpenStringRightsQuery() {
		if (openRightsQuery == null) {
			openRightsQuery = join(solarizeUrls(openUrls));
		}
		return openRightsQuery;
	}

	public static String getRestrictedRightsQuery() {
		if (restrictedRightsQuery == null) {
			restrictedRightsQuery = join(solarizeUrls(restrictedUrls));
		}
		return restrictedRightsQuery;
	}

	public static String getAllRightsQuery() {
		if (allRightsQuery == null) {
			List<String> solarizedUrls = new ArrayList<String>();
			solarizedUrls.addAll(solarizeUrls(openUrls));
			solarizedUrls.addAll(solarizeUrls(restrictedUrls));
			allRightsQuery = join(solarizedUrls);
		}
		return allRightsQuery;
	}

	private static List<String> solarizeUrls(List<String> urls) {
		List<String> solarizedUrls = new ArrayList<String>();
		for (String url : urls) {
			solarizedUrls.add(solarizeUrl(url));
		}
		return solarizedUrls;
	}

	private static String solarizeUrl(String url) {
		return url.replace(":", "\\:").replace("/", "\\/") + "*";
	}

	private static String join(List<String> urls) {
		return "RIGHTS:(" + StringUtils.join(urls, " OR ") + ")";
	}

	public static List<QueryFacet> getQueryFacets() {
		if (queryFacets == null) {
			queryFacets = new ArrayList<QueryFacet>();
			queryFacets.add(new QueryFacet(getOpenStringRightsQuery(), "REUSABILITY:" + OPEN, "REUSABILITY"));
			queryFacets.add(new QueryFacet(getRestrictedRightsQuery(), "REUSABILITY:" + RESTRICTED, "REUSABILITY"));
		}
		return queryFacets;
	}

	private String cleanUrl(String url) {
		return url.trim().replace("&qf=RIGHTS:", "").replace("\"", "").replace("RIGHTS:", "");
	}

	public long getNumberOfOpen() {
		return numberOfOpen;
	}

	public long getNumberOfRestricted() {
		return numberOfRestricted;
	}
}
