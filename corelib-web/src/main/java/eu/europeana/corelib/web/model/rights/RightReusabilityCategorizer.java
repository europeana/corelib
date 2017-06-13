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
	private long numberOfPermission;

	private static String openRightsQuery;
	private static String noOpenRightsQuery;
	private static String restrictedRightsQuery;
	private static String noRestrictedRightsQuery;
	private static String permissionRightsQuery;
	private static String allRightsQuery;
	private static String uncategorizedQuery;

	private static Map<String, String> examinedUrlsMap = new HashMap<String, String>();
	private static List<QueryFacet> queryFacets;

	private static int savedStrategy = 0;

	static final int PERMISSION_STRATEGY_NEGATIVE_ALL = 1;
	static final int PERMISSION_STRATEGY_NEGATIVE_WITH_RIGHTS = 2;
	static final int PERMISSION_STRATEGY_POSITIVE = 3;

//	private static int permissionStrategy = PERMISSION_STRATEGY_NEGATIVE_ALL;
	private static int permissionStrategy = PERMISSION_STRATEGY_POSITIVE;

	private final static int SELECTED_UNCATEGORIZED = 0;
	private final static int SELECTED_OPEN = 1;
	private final static int SELECTED_RESTRICTED = 2;
	private final static int SELECTED_PERMISSION = 4;

	private static final String OPEN          = "open";
	private static final String RESTRICTED    = "restricted";
	private static final String UNCATEGORIZED = "uncategorized";
	private static final String PERMISSION    = "permission";

	private static Map<String, String> reusabilityValueMap = new LinkedHashMap<String, String>();
	static {
		reusabilityValueMap.put(RightReusabilityCategorizer.OPEN, "reusabilityOpen_t");
		reusabilityValueMap.put(RightReusabilityCategorizer.RESTRICTED, "reusabilityRestricted_t");
		reusabilityValueMap.put(RightReusabilityCategorizer.PERMISSION, "reusabilityPermission_t");
		reusabilityValueMap = Collections.unmodifiableMap(reusabilityValueMap);
	}

	private static List<String> openUrls = new ArrayList<String>();
	static {
		openUrls.add(RightsOption.CC_NOC.getUrl());
		openUrls.add(RightsOption.CC_ZERO.getUrl() + "/1.0/");
		openUrls.add(RightsOption.CC_BY.getUrl());
		openUrls.add(RightsOption.CC_BY_SA.getUrl());
	}

	private static List<String> restrictedUrls = new ArrayList<String>();
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

	private static List<String> permissionUrls = new ArrayList<String>();
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
		numberOfPermission = 0;
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

	public static String getOpenRightsQuery() {
		if (openRightsQuery == null) {
			openRightsQuery = join(solarizeUrls(openUrls));
		}
		return openRightsQuery;
	}

	public static String getNoOpenRightsQuery() {
		if (noOpenRightsQuery == null) {
			noOpenRightsQuery = joinNegatives(solarizeUrls(openUrls));
		}
		return noOpenRightsQuery;
	}

	public static String getRestrictedRightsQuery() {
		if (restrictedRightsQuery == null) {
			restrictedRightsQuery = join(solarizeUrls(restrictedUrls));
		}
		return restrictedRightsQuery;
	}

	public static String getNoRestrictedRightsQuery() {
		if (noRestrictedRightsQuery == null) {
			noRestrictedRightsQuery = joinNegatives(solarizeUrls(restrictedUrls));
		}
		return noRestrictedRightsQuery;
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

	public static String getPermissionRightsQuery() {
		if (permissionRightsQuery == null || savedStrategy != permissionStrategy) {
			if (permissionStrategy == PERMISSION_STRATEGY_POSITIVE) {
				permissionRightsQuery = join(solarizeUrls(permissionUrls));
			} else {
				List<String> solarizedUrls = new ArrayList<String>();
				solarizedUrls.addAll(solarizeUrls(openUrls));
				solarizedUrls.addAll(solarizeUrls(restrictedUrls));
				if (permissionStrategy == PERMISSION_STRATEGY_NEGATIVE_WITH_RIGHTS) {
					permissionRightsQuery = joinNegatives(solarizedUrls);
				} else {
					permissionRightsQuery = joinAllNegatives(solarizedUrls);
				}
			}
			savedStrategy = permissionStrategy;
		}
		return permissionRightsQuery;
	}

	public static String getUncategorizedQuery() {
		if (null == uncategorizedQuery) {
			List<String> solarizedUrls = new ArrayList<String>();
			solarizedUrls.addAll(solarizeUrls(openUrls));
			solarizedUrls.addAll(solarizeUrls(restrictedUrls));
			solarizedUrls.addAll(solarizeUrls(permissionUrls));
			uncategorizedQuery = joinNegatives(solarizedUrls);
		}
		return uncategorizedQuery;
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

	private static String rights(String content) {
		return "RIGHTS:(" + content + ")";
	}

	private static String join(List<String> urls) {
		return rights(StringUtils.join(urls, " OR "));
	}

	private static String joinNegatives(List<String> urls) {
		return rights("NOT(" + StringUtils.join(urls, " OR ") + ")");
	}

	private static String joinAllNegatives(List<String> urls) {
		return joinNegatives(urls);
		// return "*:* AND -" + rights(StringUtils.join(urls, " OR "));
	}

	public static List<QueryFacet> getQueryFacets() {
		if (queryFacets == null) {
			queryFacets = new ArrayList<QueryFacet>();
			queryFacets.add(new QueryFacet(getOpenRightsQuery(), "REUSABILITY:" + OPEN, "REUSABILITY"));
			queryFacets.add(new QueryFacet(getRestrictedRightsQuery(), "REUSABILITY:" + RESTRICTED, "REUSABILITY"));
			queryFacets.add(new QueryFacet(getPermissionRightsQuery(), "REUSABILITY:" + PERMISSION, "REUSABILITY"));
			queryFacets.add(new QueryFacet(getUncategorizedQuery(), "REUSABILITY:" + UNCATEGORIZED, "REUSABILITY"));
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

	public long getNumberOfPermission() {
		return numberOfPermission;
	}

	/**
	 * Get a translation key belongs to the field value
	 * @param value
	 * @return
	 */
	public static String getTranslationKey(String value) {
		return reusabilityValueMap.containsKey(value.toLowerCase()) ? reusabilityValueMap.get(value.toLowerCase()) : null;
	}

	public static Map<String, String> getReusabilityValueMap() {
		return reusabilityValueMap;
	}

	public static int getPermissionStrategy() {
		return permissionStrategy;
	}

	public static void setPermissionStrategy(int permissionStrategy) {
		RightReusabilityCategorizer.permissionStrategy = permissionStrategy;
	}

	public static Map<String, String> mapValueReplacements(String[] qf) {
		return mapValueReplacements(qf, false);
	}

	public static Map<String, String> mapValueReplacements(String[] qf, boolean fromApi) {
		if (ArrayUtils.isEmpty(qf)) {
			return null;
		}

		Map<String, String> valueReplacements = new HashMap<String, String>();
		String open = "REUSABILITY:" + OPEN;
		String restricted = "REUSABILITY:" + RESTRICTED;
		String permission = "REUSABILITY:" + PERMISSION;
		String uncategorized = "REUSABILITY:" + UNCATEGORIZED;

		int reusabilityFilters = 0;
		for (String value : qf) {
			if (value.equalsIgnoreCase(open) || (fromApi && value.equalsIgnoreCase(OPEN))) {
				TaggedQuery query = new TaggedQuery("REUSABILITY", getOpenRightsQuery());
				valueReplacements.put(open, query.toString());
				reusabilityFilters += SELECTED_OPEN;
			} else if (value.equalsIgnoreCase(restricted) || (fromApi && value.equalsIgnoreCase(RESTRICTED))) {
				TaggedQuery query = new TaggedQuery("REUSABILITY", getRestrictedRightsQuery());
				valueReplacements.put(restricted, query.toString());
				reusabilityFilters += SELECTED_RESTRICTED;
			} else if (value.equalsIgnoreCase(permission) || (fromApi && value.equalsIgnoreCase(PERMISSION))) {
				TaggedQuery query = new TaggedQuery("REUSABILITY", getPermissionRightsQuery());
				valueReplacements.put(permission, query.toString());
				reusabilityFilters += SELECTED_PERMISSION;
			} else if (fromApi && value.equalsIgnoreCase(UNCATEGORIZED)) {
				reusabilityFilters = SELECTED_UNCATEGORIZED;

			}
		}

		if (reusabilityFilters == (SELECTED_OPEN + SELECTED_RESTRICTED)) {
			TaggedQuery query = new TaggedQuery("REUSABILITY", RightReusabilityCategorizer.getAllRightsQuery());
			valueReplacements.put(open, query.toString());
			valueReplacements.put(restricted, "");
		} else if (reusabilityFilters == (SELECTED_OPEN + SELECTED_PERMISSION)) {
			TaggedQuery query = new TaggedQuery("REUSABILITY", RightReusabilityCategorizer.getNoRestrictedRightsQuery());
			valueReplacements.put(open, query.toString());
			valueReplacements.put(permission, "");
		} else if (reusabilityFilters == (SELECTED_RESTRICTED + SELECTED_PERMISSION)) {
			TaggedQuery query = new TaggedQuery("REUSABILITY", RightReusabilityCategorizer.getNoOpenRightsQuery());
			valueReplacements.put(restricted, query.toString());
			valueReplacements.put(permission, "");
		} else if (reusabilityFilters == (SELECTED_OPEN + SELECTED_RESTRICTED + SELECTED_PERMISSION)) {
			valueReplacements.put(open, "");
			valueReplacements.put(restricted, "");
			valueReplacements.put(permission, "");
		} else if (reusabilityFilters == SELECTED_UNCATEGORIZED) {
			TaggedQuery query = new TaggedQuery("REUSABILITY", RightReusabilityCategorizer.getUncategorizedQuery());
			valueReplacements.put(uncategorized, query.toString());
		}
		return valueReplacements;
	}
}
