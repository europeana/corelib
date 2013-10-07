package eu.europeana.corelib.web.model.rights;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import eu.europeana.corelib.definitions.model.RightsOption;

public class RightReusabilityCategorizer {

	private static final String FREE = "free";
	private static final String LIMITED = "limited";
	private static final String UNCATEGORIZED = "uncategorized";

	private long numberOfFree;
	private long numberOfLimited;

	private static List<String> freeUrls = Arrays.asList(new String[]{
		RightsOption.NOC.getUrl(),
		RightsOption.CC_ZERO.getUrl() + "/1.0/",
		RightsOption.CC_BY.getUrl(),
		RightsOption.CC_BY_SA.getUrl()
	});
	private static String freeRightsQuery;

	private static List<String> limitedUrls = Arrays.asList(new String[]{
		RightsOption.CC_BY_NC.getUrl(),
		RightsOption.CC_BY_NC_SA.getUrl(),
		RightsOption.CC_BY_NC_ND.getUrl(),
		RightsOption.CC_BY_ND.getUrl(),
		RightsOption.OOC_NC.getUrl()
	});
	private static String limitedRightsQuery;
	private static String allRightsQuery;

	private static Map<String, String> examinedUrlsMap = new HashMap<String, String>();

	public RightReusabilityCategorizer() {
		numberOfFree = 0;
		numberOfLimited = 0;
	}

	public String categorize(String url, long count) {
		String cleanedUrl = cleanUrl(url);
		String category = null;
		if (StringUtils.isBlank(cleanedUrl)) {
			return category;
		}

		if (examinedUrlsMap.containsKey(cleanedUrl)) {
			category = examinedUrlsMap.get(cleanedUrl);
			if (category.equals(FREE)) {
				numberOfFree += count;
			} else if (category.equals(LIMITED)) {
				numberOfLimited += count;
			}
		} else {
			for (String rightUrl : freeUrls) {
				if (cleanedUrl.startsWith(rightUrl)) {
					numberOfFree += count;
					category = FREE;
					examinedUrlsMap.put(cleanedUrl, category);
					break;
				}
			}
			if (category == null) {
				for (String rightUrl : limitedUrls) {
					// log.info(rightUrl);
					if (cleanedUrl.startsWith(rightUrl)) {
						numberOfLimited += count;
						category = LIMITED;
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

	public static String getFreeRightsQuery() {
		if (freeRightsQuery == null) {
			freeRightsQuery = join(solarizeUrls(freeUrls));
		}
		return freeRightsQuery;
	}

	public static String getLimitedRightsQuery() {
		if (limitedRightsQuery == null) {
			limitedRightsQuery = join(solarizeUrls(limitedUrls));
		}
		return limitedRightsQuery;
	}

	public static String getAllRightsQuery() {
		if (allRightsQuery == null) {
			List<String> solarizedUrls = new ArrayList<String>();
			solarizedUrls.addAll(solarizeUrls(freeUrls));
			solarizedUrls.addAll(solarizeUrls(limitedUrls));
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

	private String cleanUrl(String url) {
		return url.trim().replace("&qf=RIGHTS:", "").replace("\"", "").replace("RIGHTS:", "");
	}

	public long getNumberOfFree() {
		return numberOfFree;
	}

	public long getNumberOfLimited() {
		return numberOfLimited;
	}
}
