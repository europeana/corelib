package eu.europeana.corelib.search.utils;

import eu.europeana.corelib.definitions.edm.beans.ApiBean;
import eu.europeana.corelib.definitions.edm.beans.BriefBean;
import eu.europeana.corelib.definitions.edm.beans.IdBean;
import eu.europeana.corelib.definitions.edm.beans.RichBean;
import eu.europeana.corelib.definitions.solr.DocType;
import eu.europeana.corelib.edm.utils.FieldMapping;
import eu.europeana.corelib.solr.bean.impl.ApiBeanImpl;
import eu.europeana.corelib.solr.bean.impl.BriefBeanImpl;
import eu.europeana.corelib.solr.bean.impl.IdBeanImpl;
import eu.europeana.corelib.solr.bean.impl.RichBeanImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.queryparser.flexible.core.QueryNodeException;
import org.apache.lucene.queryparser.flexible.standard.StandardQueryParser;
import org.apache.lucene.search.TermQuery;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.util.ClientUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchUtils {

	private static final Pattern ID_PATTERN = Pattern.compile("^\\{!id=([^:]+):([^:]+) ex=(.*?)\\}");


//	private static final Pattern SOLR_BUG_PATTERN =
//			Pattern.compile("(.*?)(proxy_dc_creator|proxy_dc_contributor|who):(\"https?+:.*?\")(.*?)", Pattern.CASE_INSENSITIVE);
	private static final Pattern SOLR_BUG_PATTERN =
			Pattern.compile("(proxy_dc_creator|proxy_dc_contributor|who)(:\"https?+:.*?\")", Pattern.CASE_INSENSITIVE);


	/**
	 * Checks if there is no TYPE facet with an invalid type according to EDM
	 *
	 *
	 * @param refinements
	 * @return Returns true if there is no TYPE facet or each type facet has
	 *         valid value
	 */
	@Deprecated // EA-1631 usefullness of this method is disputed
	public static boolean checkTypeFacet(String[] refinements) {
		if (refinements != null) {
			for (String refinement : refinements) {
				if (StringUtils.contains(refinement, "TYPE:")
						&& !StringUtils.contains(refinement, " OR ")) {
					if (DocType.safeValueOf(StringUtils.substringAfter(
							refinement, "TYPE:")) == null) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * Get the implementation class of one of the Solr Beans
	 *
	 * @param interfaze
	 *            The interfaze to check
	 * @return The corresponding implementation class
	 */
	public static Class<? extends IdBeanImpl> getImplementationClass(
			Class<? extends IdBean> interfaze) {
		if (interfaze != null) {
			if (interfaze == RichBean.class) {
				return RichBeanImpl.class;
			}
			if (interfaze == ApiBean.class) {
				return ApiBeanImpl.class;
			}
			if (interfaze == BriefBean.class) {
				return BriefBeanImpl.class;
			}
			if (interfaze == IdBean.class) {
				return IdBeanImpl.class;
			}
		}
		return null;
	}
	
	/**
	 * Translates ESE fielded queries to EDM fielded queries
	 *
	 * @param query
	 * @return
	 */
	public static String rewriteQueryFields(String query) {
		if (!query.contains(":")) {
			return query;
		}

		// handling field + "*:*"
		if (query.equals("title:*:*")) {
			return "title:*";
		}
		if (query.equals("who:*:*")) {
			return "who:*";
		}
		if (query.equals("what:*:*")) {
			return "what:*";
		}
		if (query.equals("where:*:*")) {
			return "where:*";
		}
		if (query.equals("when:*:*")) {
			return "when:*";
		}

		// handling API1 fields
		for (FieldMapping field : FieldMapping.values()) {
			if (query.contains(field.getEseField() + ":")) {
				query = query.replaceAll("\\b" + field.getEseField() + ":",
						field.getEdmField() + ":");
			}
		}

		return query;
	}

	public static String escapeQuery(String query) {
		return ClientUtils.escapeQueryChars(query).replace("\\ ", " ")
				.replace("\\-", "-");
	}

	public static String escapeFacet(String field, String query) {
		if (StringUtils.isNotBlank(field) && StringUtils.isNotBlank(query)) {
			query = escapeQuery(StringUtils.trim(query));
			return StringUtils.trim(field) + ":\"" + query + "\"";
		}
		return null;
	}

	/**
	 * The QueryFacets are in this form:
	 * {!id=REUSABILITY:Limited}RIGHTS:(http\:\
	 * /\/creativecommons.org\/licenses\/by-nc\/* OR
	 * http\:\/\/creativecommons.org\/licenses\/by-nc-sa\/* OR
	 * http\:\/\/creativecommons.org\/licenses\/by-nc-nd\/* OR
	 * http\:\/\/creativecommons.org\/licenses\/by-nd\/* OR
	 * http\:\/\/www.europeana.eu\/rights\/out-of-copyright-non-commercial\/*)
	 *
	 * this function creates a hierarchy: REUSABILITY Limited: x Free: y ...
	 *
	 * @param queryFacets
	 * @return
	 */
	public static List<FacetField> extractQueryFacets(
			Map<String, Integer> queryFacets) {
		Map<String, FacetField> map = new HashMap<>();
		for (String query : queryFacets.keySet()) {
			Matcher matcher = ID_PATTERN.matcher(query);
			if (!matcher.find()) {
				continue;
			}
			String field = matcher.group(1);
			String value = matcher.group(2);
			if (!map.containsKey(field)) {
				map.put(field, new FacetField(field));
			}
			map.get(field).add(value, queryFacets.get(query));
		}
		return new ArrayList<>(map.values());
	}

	public static boolean isSimpleQuery(String queryTerm) {
		return isNotFieldQuery(queryTerm)
				&& (isTermQuery(queryTerm) || containsNoneSearchOperators(queryTerm));
	}

	public static boolean isTermQuery(String queryTerm) {
		StandardQueryParser queryParserHelper = new StandardQueryParser();
		org.apache.lucene.search.Query query = null;
		try {
			query = queryParserHelper.parse(queryTerm, "text");
		} catch (QueryNodeException e) {
			//e.printStackTrace();
		}
		return (query != null && query instanceof TermQuery);
	}

	private static boolean containsNoneSearchOperators(String queryTerm) {
		return !StringUtils.contains(queryTerm, " AND ")
				&& !StringUtils.contains(queryTerm, " NOT ")
				&& !StringUtils.contains(queryTerm, " OR ")
				&& !StringUtils.contains(queryTerm, "*");
	}

	private static boolean isNotFieldQuery(String queryTerm) {
		return !StringUtils.contains(queryTerm, ":");
	}

	public static String fixBuggySolrIndex(String queryString){
		return fixBuggySolrIndex("", queryString);
	}

	private static String fixBuggySolrIndex(String processed, String queryString){
		if (queryString.length() > 0){
			Matcher bugMatcher = SOLR_BUG_PATTERN.matcher(queryString);
			if (bugMatcher.find()) {
				switch (bugMatcher.group(1)) {
					case "proxy_dc_creator":
						return fixBuggySolrIndex(processed + queryString.substring(0, bugMatcher.start()) +
								"(proxy_dc_creator" + bugMatcher.group(2) + " AND CREATOR" +
								bugMatcher.group(2) + ")", queryString.substring(bugMatcher.end()));
					case "proxy_dc_contributor":
						return fixBuggySolrIndex(processed + queryString.substring(0, bugMatcher.start()) +
								"(proxy_dc_contributor" + bugMatcher.group(2) + " AND CONTRIBUTOR" +
								bugMatcher.group(2) + ")", queryString.substring(bugMatcher.end()));
					case "who":
						return fixBuggySolrIndex(processed + queryString.substring(0, bugMatcher.start()) +
								"(who" + bugMatcher.group(2) + " AND (CREATOR" + bugMatcher.group(2) +
								" OR CONTRIBUTOR" + bugMatcher.group(2) + "))", queryString.substring(bugMatcher.end()));
				}
			}
		}
		return processed + queryString;
	}
}
