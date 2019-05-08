package eu.europeana.corelib.web.model.rights;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import eu.europeana.corelib.definitions.model.RightsOption;
import eu.europeana.corelib.definitions.solr.model.QueryFacet;
import eu.europeana.corelib.definitions.solr.model.TaggedQuery;

public class RightReusabilityCategorizer {

	private long numberOfOpen;
	private long numberOfRestricted;

	private static String openRightsQuery;
	private static String restrictedRightsQuery;
	private static String permissionRightsQuery;
	private static String allRightsQuery;
	private static String uncategorizedQuery;

	private static Map<String, String> examinedUrlsMap = new HashMap<>();
	private static List<QueryFacet> queryFacets;

	private static int savedStrategy = 0;

	static final int PERMISSION_STRATEGY_NEGATIVE_ALL = 1;
	static final int PERMISSION_STRATEGY_NEGATIVE_WITH_RIGHTS = 2;
	static final int PERMISSION_STRATEGY_POSITIVE = 3;

	private static int permissionStrategy = PERMISSION_STRATEGY_POSITIVE;

	private static final int SELECTED_OPEN = 1;
	private static final int SELECTED_RESTRICTED = 2;
	private static final int SELECTED_PERMISSION = 4;
	private static final int SELECTED_UNCATEGORIZED = 7;

	private static final String OPEN          = "open";
	private static final String RESTRICTED    = "restricted";
	private static final String UNCATEGORIZED = "uncategorized";
	private static final String PERMISSION    = "permission";

	private static final String REUSABILITY   = "REUSABILITY";

	private static final String REUSABILITYLIST = "REUSABILITY:list";

	private static Map<String, String> reusabilityValueMap = new LinkedHashMap<>();
	static {
		reusabilityValueMap.put(RightReusabilityCategorizer.OPEN, "reusabilityOpen_t");
		reusabilityValueMap.put(RightReusabilityCategorizer.RESTRICTED, "reusabilityRestricted_t");
		reusabilityValueMap.put(RightReusabilityCategorizer.PERMISSION, "reusabilityPermission_t");
		reusabilityValueMap = Collections.unmodifiableMap(reusabilityValueMap);
	}

	private static List<String> openUrls = new ArrayList<>();
	static {
		openUrls.add(RightsOption.CC_NOC.getUrl());
		openUrls.add(RightsOption.CC_ZERO.getUrl() + "1.0/");
		openUrls.add(RightsOption.CC_BY.getUrl());
		openUrls.add(RightsOption.CC_BY_SA.getUrl());
	}

	private static List<String> restrictedUrls = new ArrayList<>();
	static {
		restrictedUrls.add(RightsOption.CC_BY_NC.getUrl());
		restrictedUrls.add(RightsOption.CC_BY_NC_SA.getUrl());
		restrictedUrls.add(RightsOption.CC_BY_NC_ND.getUrl());
		restrictedUrls.add(RightsOption.CC_BY_ND.getUrl());
		restrictedUrls.add(RightsOption.EU_OOC_NC.getUrl());
		restrictedUrls.add(RightsOption.RS_INC_EDU.getUrl());
		restrictedUrls.add(RightsOption.RS_NOC_NC.getUrl());
		restrictedUrls.add(RightsOption.RS_NOC_OKLR.getUrl());
	}

	private static List<String> permissionUrls = new ArrayList<>();
	static {
		permissionUrls.add(RightsOption.EU_RR_F.getUrl());
		permissionUrls.add(RightsOption.EU_RR_P.getUrl());
		permissionUrls.add(RightsOption.EU_RR_R.getUrl());
		permissionUrls.add(RightsOption.EU_U.getUrl());
		permissionUrls.add(RightsOption.EU_ORPHAN.getUrl());
		permissionUrls.add(RightsOption.RS_INC.getUrl());
		permissionUrls.add(RightsOption.RS_INC_OW_EU.getUrl());
		permissionUrls.add(RightsOption.RS_CNE.getUrl());
	}

	public RightReusabilityCategorizer() {
		numberOfOpen = 0;
		numberOfRestricted = 0;
	}

	public void categorize(String url, long count) {
		String cleanedUrl = cleanUrl(url);
		String category = null;

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
	}

	static String getOpenRightsQuery() {
		if (openRightsQuery == null) {
			openRightsQuery = join(solarizeUrls(openUrls));
		}
		return openRightsQuery;
	}

	static String getRestrictedRightsQuery() {
		if (restrictedRightsQuery == null) {
			restrictedRightsQuery = join(solarizeUrls(restrictedUrls));
		}
		return restrictedRightsQuery;
	}

	static String getAllRightsQuery() {
		if (allRightsQuery == null) {
			List<String> solarizedUrls = new ArrayList<>();
			solarizedUrls.addAll(solarizeUrls(openUrls));
			solarizedUrls.addAll(solarizeUrls(restrictedUrls));
			allRightsQuery = join(solarizedUrls);
		}
		return allRightsQuery;
	}

	static String getPermissionRightsQuery() {
		if (permissionRightsQuery == null || savedStrategy != permissionStrategy) {
			if (permissionStrategy == PERMISSION_STRATEGY_POSITIVE) {
				permissionRightsQuery = join(solarizeUrls(permissionUrls));
			} else {
				List<String> solarizedUrls = new ArrayList<>();
				solarizedUrls.addAll(solarizeUrls(openUrls));
				solarizedUrls.addAll(solarizeUrls(restrictedUrls));
				permissionRightsQuery = joinNegatives(solarizedUrls);
			}
			savedStrategy = permissionStrategy;
		}
		return permissionRightsQuery;
	}

	private static String getUncategorizedQuery() {
		if (null == uncategorizedQuery) {
			List<String> solarizedUrls = new ArrayList<>();
			solarizedUrls.addAll(solarizeUrls(openUrls));
			solarizedUrls.addAll(solarizeUrls(restrictedUrls));
			solarizedUrls.addAll(solarizeUrls(permissionUrls));
			uncategorizedQuery = joinNegatives(solarizedUrls);
		}
		return uncategorizedQuery;
	}

	private static List<String> solarizeUrls(List<String> urls) {
		List<String> solarizedUrls = new ArrayList<>();
		for (String url : urls) {
			solarizedUrls.add(solarizeUrl(url));
		}
		return solarizedUrls;
	}

	private static String solarizeUrl(String url) {
		return url.replace(":", "\\:").replace("/", "\\/") + "*";
	}

	private static String rights(String content) {
		return "RIGHTS:(" + content + ")";
	}

	private static String join(List<String> urls) {
		return rights(StringUtils.join(urls, " OR "));
	}

	private static String joinNegatives(List<String> urls) {
		return "-" + rights(StringUtils.join(urls, " OR "));
	}

	public static List<QueryFacet> getQueryFacets() {
		if (queryFacets == null) {
			queryFacets = new ArrayList<>();
			queryFacets.add(new QueryFacet(getOpenRightsQuery(), REUSABILITY + ":" + OPEN, REUSABILITY));
			queryFacets.add(new QueryFacet(getRestrictedRightsQuery(), REUSABILITY + ":" + RESTRICTED, REUSABILITY));
			queryFacets.add(new QueryFacet(getPermissionRightsQuery(), REUSABILITY + ":" + PERMISSION, REUSABILITY));
			queryFacets.add(new QueryFacet(getUncategorizedQuery(), REUSABILITY + ":" + UNCATEGORIZED, REUSABILITY));
		}
		return queryFacets;
	}

	private String cleanUrl(String url) {
		return url.trim().replace("&qf=RIGHTS:", "")
				.replace("\"", "")
				.replace("RIGHTS:", "");
	}

	public long getNumberOfOpen() {
		return numberOfOpen;
	}

	public long getNumberOfRestricted() {
		return numberOfRestricted;
	}

	static int getPermissionStrategy() {
		return permissionStrategy;
	}

	static void setPermissionStrategy(int permissionStrategy) {
		RightReusabilityCategorizer.permissionStrategy = permissionStrategy;
	}

	static Map<String, String> mapValueReplacements(String[] qf) {
		return mapValueReplacements(qf, false);
	}

	public static Map<String, String> mapValueReplacements(String[] qf, boolean fromApi) {
		if (ArrayUtils.isEmpty(qf)) {
			return null;
		}

		Map<String, String> valueReplacements = new HashMap<>();
		List<String> urlList = new ArrayList<>();
		boolean negateQf = false;
		String urlString;
		String open = REUSABILITY + ":" + OPEN;
		String restricted = REUSABILITY + ":" + RESTRICTED;
		String permission = REUSABILITY + ":" + PERMISSION;
		String uncategorized = REUSABILITY + ":" + UNCATEGORIZED;

		int reusabilityFilters = 0;
		for (String value : qf) {
			if (value.equalsIgnoreCase(open) || (fromApi && value.equalsIgnoreCase(OPEN))) {
				reusabilityFilters += SELECTED_OPEN;
			} else if (value.equalsIgnoreCase(restricted) || (fromApi && value.equalsIgnoreCase(RESTRICTED))) {
				reusabilityFilters += SELECTED_RESTRICTED;
			} else if (value.equalsIgnoreCase(permission) || (fromApi && value.equalsIgnoreCase(PERMISSION))) {
				reusabilityFilters += SELECTED_PERMISSION;
			} else if (value.equalsIgnoreCase(uncategorized) || (fromApi && value.equalsIgnoreCase(UNCATEGORIZED))) {
				reusabilityFilters -= SELECTED_UNCATEGORIZED;
				negateQf = true;
			}
		}
		// reusabilityFilters == 0 means: all reusabilities + uncategorised -> means 'everything', ergo: refinement
		if (reusabilityFilters == 0){
			return null;
		}

		if (Math.abs(reusabilityFilters) > 3){
			urlList.addAll(permissionUrls);
			if (negateQf) reusabilityFilters += 4;
			else reusabilityFilters -= 4;
		}
		if (Math.abs(reusabilityFilters) > 1){
			urlList.addAll(restrictedUrls);
			if (negateQf) reusabilityFilters += 2;
			else reusabilityFilters -= 2;
		}
		if (Math.abs(reusabilityFilters) > 0){
			urlList.addAll(openUrls);
		}

		urlString = join(solarizeUrls(urlList));

		if (negateQf) {
			urlString = "-" + urlString;
		}

		TaggedQuery query = new TaggedQuery(REUSABILITY, urlString);
		valueReplacements.put(REUSABILITYLIST, query.toString());
		return valueReplacements;
	}
}
