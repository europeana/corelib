package eu.europeana.corelib.edm.utils;

import java.util.regex.Pattern;

/**
 * For validating common input parameters
 * @author Patrick Ehlert
 * Created on 05-09-2018
 */
public final class ValidateUtils {

    private static final Pattern RECORD_ID = Pattern.compile("^/[a-zA-Z0-9_]*/[a-zA-Z0-9_]*$");

    private ValidateUtils() {
        // empty constructor to prevent initialization
    }

    /**
     * Checks if the provided recordId has the correct format (no illegal characters that may mess up the query)
     * @param europeanaId string that should consist of "/<collectionId>/<itemId>"
     * @return true if it has a valid format, otherwise false
     */
    public static boolean validateRecordIdFormat(String europeanaId) {
        return europeanaId != null && RECORD_ID.matcher(europeanaId).matches();
    }


}
