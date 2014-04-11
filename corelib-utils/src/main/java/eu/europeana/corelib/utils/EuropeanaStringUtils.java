package eu.europeana.corelib.utils;

import org.apache.commons.lang.StringUtils;

public class EuropeanaStringUtils {

	public static String createPhraseValue(String value) {
		value = StringUtils.trim(value);
		if (StringUtils.containsNone(value, " ") &&  StringUtils.containsNone(value, "/")) {
			return value;
		} else {
			return String.format("\"%s\"", value);
		}
	}
}
