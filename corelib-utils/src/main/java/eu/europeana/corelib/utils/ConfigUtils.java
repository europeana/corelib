package eu.europeana.corelib.utils;

import java.util.Properties;

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
}
