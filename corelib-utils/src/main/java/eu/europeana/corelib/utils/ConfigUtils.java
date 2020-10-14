package eu.europeana.corelib.utils;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Contains convenience methods for loading config properties
 */
public class ConfigUtils {

    public static final String SEPARATOR = ".";

    /**
     * Checks if a key with the given prefix is contained within a Properties object.
     *
     * @param properties Properties object
     * @param keyPrefix  key prefix to check for
     * @return true if prefix is contained within properties object, false otherwise.
     */
    public static boolean containsKeyPrefix(Properties properties, String keyPrefix) {
        return properties.keySet().stream().anyMatch(k
                -> k.toString().startsWith(keyPrefix)
        );
    }

    /**
     * Checks if a key with the given prefix is contained within a map
     *
     * @param map       Properties object
     * @param keyPrefix key prefix to check for
     * @return true if prefix is contained within map, false otherwise.
     */
    public static boolean containsKeyPrefix(Map<String, ?> map, String keyPrefix) {
        return map.keySet().stream().anyMatch(k
                -> k.startsWith(keyPrefix)
        );
    }


    /**
     * Gets all keys within the map that match a regular expression
     *
     * @param map   map to search
     * @param regex regular expression pattern to match keys against
     * @return list containing matching keys
     */
    public static List<String> getMatchingKeys(Map<String, ?> map, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return map.keySet()
                .stream()
                .filter(pattern.asMatchPredicate())
                .collect(Collectors.toUnmodifiableList());
    }

}
