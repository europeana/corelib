package eu.europeana.corelib.search.utils;

import eu.europeana.corelib.utils.ComparatorUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * For now the scope of caching qf parameters is limited to default technical Facets.
 * While processing these parameters there is a filter tag generated always.
 * See - SearchController.processQfParameters()
 * hence for now caching will be applied to filter tags if two or more present with boolean operator
 *
 * @author srishti singh
 * @since 17 April 2025
 */
public class CachingRefinementUtils {

    private static final Set<String> cachingFields   = Set.of("filter_tags");
    /**
     * Pattern to check if the field has all the values appended together
     * Example - field:(value1 OR value2 OR value3 AND value4)
     */
    private static String fieldValuesRegex           = "(^\\w*:\\(.*\\))";
    private  static final Pattern fieldValuesPattern = Pattern.compile(fieldValuesRegex);

    private static String tag                        = "(^\\{!tag=.*\\})";
    private  static final Pattern tagPattern         = Pattern.compile(tag);

    /**
     * True if the string contains boolean operator value
     * @param value value to check
     * @return
     */
    public static boolean ifBooleanFiltersPresent(String value) {
        return StringUtils.containsAny(value, "OR", "AND");
    }

    /**
     * True if the value is a boolean parameter
     * @param value value to check
     * @return
     */
    public static boolean isBooleanParameter(String value) {
        return StringUtils.equalsAny(value.trim(), "OR", "AND");
    }

    public static boolean isFieldValuesPattern(String value) {
        return fieldValuesPattern.matcher(value).find();
    }

    /**
     * Will return the new refinements array with the filter clause values if needed.
     *
     * See - EA-4172
     * Filter clause is added for caching in solr, and also it is applicable only to values which have
     *      two or more clauses in fq combined with the boolean operator.
     *      example : fq={!tag=filter_tags}filter_tags:123 OR filter_tags:456
     *      the new value will be : {!tag=filter_tags}filter(filter_tags:123) OR filter(filter_tags:456)
     *
     * @param refinements refinements values (with or without '!tag') , in both v2 and v3 we have dividedRefinements
     *                           so it is with !tag. But the presence or absence of '!tag' will not affect the code
     * @return old unaffected values and new cached refinements values (if any)
     */
    public static String[] getCachedRefinements(String [] refinements) {
        List<String> cachedRefinements = new ArrayList<>(refinements.length);
        Map<String, String> cachingFieldsMap = getFilterClauseRefinement(getValuesForCaching(refinements));
        if (!cachingFieldsMap.isEmpty()) {
            for (String refinement : refinements) {
                boolean added = false;
                for (Map.Entry<String, String> entry : cachingFieldsMap.entrySet()) {
                    if (StringUtils.contains(refinement, entry.getKey())) {
                        cachedRefinements.add(StringUtils.replace(refinement, entry.getKey(), entry.getValue()));
                        added = true;
                    }
                }
                if (!added) {
                    cachedRefinements.add(refinement);
                }
            }
            return cachedRefinements.toArray(new String[0]);
        }
        return refinements;
    }


    /**
     * Will add the qualified values for caching as key in the map
     *
     * the refinements array may or may NOT contain '!tag', this
     * method will also remove the tag values if present
     *
     * @param refinements refinement array
     * @return
     */
    private static Map<String, String> getValuesForCaching(String[] refinements) {
        Map<String, String> cachingFieldsMap = new LinkedHashMap<>();
        for (String refinement : refinements) {
            String keyToAdd = getValueWithoutTag(refinement);
            for (String cachingField : cachingFields) {
                if (StringUtils.contains(keyToAdd, cachingField) && CachingRefinementUtils.ifBooleanFiltersPresent(keyToAdd)) {
                    cachingFieldsMap.put(keyToAdd, null);
                }
            }
        }
        return cachingFieldsMap;
    }

    /**
     * returns value without tag if present or returns the original value
     * example - {!tag=has_media}has_media:true  OR has_media:true
     * @param valueWithTag value with tag
     * @return
     */
    private static String getValueWithoutTag(String valueWithTag) {
        Matcher m = tagPattern.matcher(valueWithTag);
        while (m.find()) {
            return StringUtils.substringAfter(valueWithTag, m.group());
        }
        return valueWithTag;
    }

    /**
     * Adds the filter values in the map
     * the refinement values with boolean operators are of two types -
     *                      1.  filter_tags:123 OR filter_tags:456  -> filter(filter_tags:123) OR filter(filter_tags:456)
     *                      2.  filter_tags:(123 OR 456)  -> filter(filter_tags:(123 OR 456))
     * @param cachingFieldsMap caching values map to be processed
     * @return Map<String, String>
     */
    private static Map<String, String> getFilterClauseRefinement(Map<String, String> cachingFieldsMap) {
        for (Map.Entry<String, String> entry : cachingFieldsMap.entrySet()) {
            String key = entry.getKey();
            if (CachingRefinementUtils.isFieldValuesPattern(key)) {
                entry.setValue("filter(" + key + ")");
            } else {
                key = ComparatorUtils.stripParenthesis(key);
                List<String> values = Arrays.asList(StringUtils.splitByWholeSeparator(key, " "));
                StringBuilder mapValue = new StringBuilder();
                for (String value : values) {
                    if (CachingRefinementUtils.isBooleanParameter(value)) {
                        mapValue.append(" " + value + " ");
                    } else {
                        mapValue.append("filter(").append(value).append(")");
                    }
                }
                entry.setValue(mapValue.toString());
            }
        }
        cachingFieldsMap.values().removeIf(Objects::isNull);
        return cachingFieldsMap;
    }
}
