package eu.europeana.corelib.web.exception;

/**
 * List of often-used error messages and error codes
 */
public enum ProblemType {

    // Search errors
    // -------------
    // Note that these have an error code. You can change the error message, but NOT the error code because
    // Portal/Collections relies on these codes to provide custom error pages.

    // Generic error message indicating a malformed search query, but unknown what the exact problem is
    SEARCH_QUERY_INVALID("400-SA", "Invalid search query", ProblemResponseAction.LOG_WARN),
    SEARCH_INVALID_QSOURCE("400-IQS", "Invalid q.source parameter", ProblemResponseAction.IGNORE),
    SEARCH_INVALID_QTARGET("400-IQT", "Invalid q.target parameter", ProblemResponseAction.IGNORE),
    SEARCH_MISSING_QTARGET("400-MQT", "Missing q.target parameter", ProblemResponseAction.IGNORE),
    SEARCH_CURSORMARK_INVALID("400-SC","Invalid cursor value. Please make sure you encode the cursor value before sending it to the API.",
            ProblemResponseAction.LOG_WARN),
    SEARCH_START_AND_CURSOR("400-SD", "Parameters 'start' and 'cursorMark' cannot be used together", ProblemResponseAction.LOG_WARN),
    SEARCH_QUERY_EMPTY("400-SE", "Empty search query parameter", ProblemResponseAction.LOG_WARN),
    SEARCH_HITSELECTOR_INVALID("400-SH", "Invalid search hit selector value", ProblemResponseAction.LOG_WARN),
    SEARCH_PAGE_LIMIT_REACHED("400-SL",
            "It is not possible to paginate beyond the first 1000 search results. Please use cursor-based pagination instead", ProblemResponseAction.LOG_WARN),
    SEARCH_FACET_RANGE_INVALID("400-SR", "Invalid search facet range value", ProblemResponseAction.LOG_WARN),
    SEARCH_THEME_UNKNOWN("400-ST", "Theme does not exist", ProblemResponseAction.LOG_WARN),
    SEARCH_THEME_MULTIPLE("400-SU", "Theme parameter accepts one value only", ProblemResponseAction.LOG_WARN),

    CANT_CONNECT_SOLR("503-SA","Unable to connect to search engine.", ProblemResponseAction.LOG_ERR),
    // At the moment we don't use timeout, but we should start to use it with ticket EA-1391
    TIMEOUT_SOLR("504-SA", "Timeout connecting to search engine.", ProblemResponseAction.LOG_ERR),
    // No need to specify error code as this is a programming error (not run-time error) which should never occur in production
    INVALIDCLASS(null,"Search service is called with invalid bean class.", ProblemResponseAction.LOG_ERR),
    // Other errors
    // ------------
    APIKEY_MISSING("400-AM", "No API key provided", ProblemResponseAction.IGNORE),
    APIKEY_DOES_NOT_EXIST("401-AX", "API key doesn't exist", ProblemResponseAction.IGNORE),
    APIKEY_DEPRECATED("410-AD", "API key is deprecated", ProblemResponseAction.IGNORE),
    MONGO_UNREACHABLE(null,"Cannot connect to CHO database", ProblemResponseAction.LOG_ERR),
    RECORD_RETRIEVAL_ERROR(null,"Record retrieval error", ProblemResponseAction.LOG_ERR),
    INVALID_URL(null,"Url is invalid", ProblemResponseAction.LOG_ERR),
    CONFIG_ERROR(null, "Invalid application config", ProblemResponseAction.LOG_ERR);

    // following errors are not longer used and will soon be removed
    //APIKEY_ERROR("500-AE", "Error checking API key", ProblemResponseAction.LOG_ERR),
    //INCONSISTENT_DATA(null,"Inconsistent data", ProblemResponseAction.LOG_ERR),
    //INVALID_ARGUMENTS(null,"Service is called with invalid argument(s)", ProblemResponseAction.LOG_ERR),
    //NO_USERNAME(null,"User name does not exist.", ProblemResponseAction.IGNORE),
    //NO_PASSWORD(null,"Password does not exist.", ProblemResponseAction.IGNORE),

    private String errorCode;
    private String message;

    private ProblemResponseAction action;

    ProblemType(String errorCode, String message, ProblemResponseAction action) {
        this.message = message;
        this.errorCode = errorCode;
        this.action = action;
    }

    /**
     * This returns an Europeana-specific error code if available. The scheme we use is <REST-ERROR-CODE><LETTER>,
     * e.g. 400A but there is no guarantee that this scheme holds for all errors. Note that currently only search
     * errors have an error code (because these are used by Portal/Collection to provide more details information about
     * user errors)
     * @return Europeana-specific error code
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Return short error message. Note that we do not guarantee the error message to be fixed. The exact phrasing for a
     * particular error can change from version to version.
     * @return
     */
    public String getMessage() {
        return message;
    }

    public ProblemResponseAction getAction() {
        return action;
    }

}
