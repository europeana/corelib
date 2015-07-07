package eu.europeana.corelib.search.queryextractor;

import org.apache.commons.lang.StringUtils;

public class QueryNormalizer {

	public static String normalizeBooleans(String query) {
		if (isSimpleBoolean(query)) {
			query = normalizeSimpleBooleans(query);
		}
		return query;
	}

	private static String normalizeSimpleBooleans(String query) {
		String[] parts = StringUtils.splitByWholeSeparator(query, " OR ");
		for (int i = 0; i < parts.length; i++) {
			if (StringUtils.contains(parts[i], " ")) {
				parts[i] = '(' + parts[i] + ')';
			}
		}
		return StringUtils.join(parts, " OR ");
	}

	private static boolean isSimpleBoolean(String query) {
		return StringUtils.contains(query, " OR ")
				&& !StringUtils.contains(query, "(")
				&& !StringUtils.contains(query, ")")
				&& !StringUtils.contains(query, " AND ");
	}
}
