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

    CANT_CONNECT_SOLR("503-SA","Unable to connect to search engine.", ProblemResponseAction.MAIL),
    // At the moment we don't use timeout, but we should start to use it with ticket EA-1391
    TIMEOUT_SOLR("504-SA", "Timeout connecting to search engine.", ProblemResponseAction.LOG_ERR),
    // No need to specify error code as this is a programming error (not run-time error) which should never occur in production
    INVALIDCLASS(null,"Search service is called with invalid bean class.", ProblemResponseAction.MAIL),

    // Other errors
    // ------------
    APIKEY_INVALID(null, "Invalid API key", ProblemResponseAction.IGNORE),
    APIKEY_MISSING(null, "No API key provided", ProblemResponseAction.IGNORE),
    MAIL_ERROR(null,"Error sending email", ProblemResponseAction.LOG_ERR),
    MONGO_UNREACHABLE(null,"Cannot connect to CHO database", ProblemResponseAction.MAIL),
    RECORD_RETRIEVAL_ERROR(null,"Record retrieval error", ProblemResponseAction.LOG_ERR),
    INCONSISTENT_DATA(null,"Inconsistent data", ProblemResponseAction.MAIL),
    INVALID_ARGUMENTS(null,"Service is called with invalid argument(s)", ProblemResponseAction.MAIL),
    NO_USERNAME(null,"User name does not exist.", ProblemResponseAction.IGNORE),
    NO_PASSWORD(null,"Password does not exist.", ProblemResponseAction.IGNORE),
    NEO4J_404(null,"No hierarchical data found for record", ProblemResponseAction.LOG_ERR),
    NEO4J_500(null,"Error processing hierarchical data for record", ProblemResponseAction.LOG_ERR),
    NEO4J_502_BAD_DATA(null,"Inconsistency in hierarchical data for record", ProblemResponseAction.LOG_ERR),
    NEO4J_503_CONNECTION(null,"Could not connect to hierarchy database", ProblemResponseAction.LOG_ERR),
    INVALID_URL(null,"Url is invalid", ProblemResponseAction.LOG_ERR),

    // Deprecated (still in use, but in deprecated classes)
    // ----------
    NOT_FOUND(null,"Entity doesn't exists", ProblemResponseAction.IGNORE),
    UNKNOWN(null,"unknown", ProblemResponseAction.IGNORE),
    TOKEN_INVALID(null,"Europeana token has expired and is no longer valid.", ProblemResponseAction.LOG_ERR),
    TOKEN_MISMATCH(null,"This Europeana token is not associated with the supplied email address.", ProblemResponseAction.LOG_ERR),
    TOKEN_OUTDATED(null,"Token is outdated.", ProblemResponseAction.LOG_ERR),
    NO_USER_ID(null,"User id does not exist.", ProblemResponseAction.IGNORE),
    NO_USER(null,"User does not exist.", ProblemResponseAction.IGNORE),
    MISSING_PARAM_USERNAME(null,"Required parameter 'username' missing.", ProblemResponseAction.IGNORE),
    MISSING_PARAM_EMAIL(null,"Required parameter 'email' missing.", ProblemResponseAction.IGNORE),
    MISSING_PARAM_PASSWORD(null,"Required parameter 'password' missing.", ProblemResponseAction.IGNORE),
    DUPLICATE(null,"Record already exists.", ProblemResponseAction.IGNORE);

    // Not used anymore, to be deleted soon
// MATCH_ALL_DOCS(null,"org.apache.lucene.search.MatchAllDocsQuery", ProblemResponseAction.IGNORE),
// UNDEFINED_FIELD(null,"Undefined field", ProblemResponseAction.IGNORE),
// RECORD_NOT_INDEXED(null,"Requested Europeana record not indexed.", ProblemResponseAction.IGNORE),
// RECORD_NOT_FOUND(null,"Requested Europeana record not found.", ProblemResponseAction.IGNORE),
// PAGE_NOT_FOUND(null,"Requested Europeana page not found.", ProblemResponseAction.IGNORE),
// RECORD_REVOKED(null,"Europeana record is revoked by the content provider.", ProblemResponseAction.IGNORE),
// MALFORMED_URL(null,"Required parameters are missing from the request.", ProblemResponseAction.LOG),
// MALFORMED_QUERY(null,"Invalid query parameter.", ProblemResponseAction.LOG),
// UNABLE_TO_CHANGE_LANGUAGE(null,"We are unable to change the interface to the requested language.", ProblemResponseAction.LOG),
// TOKEN_EXPIRED(null,"Europeana token has expired and is no longer valid.", ProblemResponseAction.IGNORE),
// UNKNOWN_TOKEN(null,"Token does not exist.", ProblemResponseAction.IGNORE),
// UNABLE_TO_PARSE_JSON(null,"Unable to parse JSON response.", ProblemResponseAction.LOG),
// MALFORMED_SPRING_TYPE_CONVERSION(null,"org.springframework.beans.TypeMismatchException:", ProblemResponseAction.IGNORE),
// NONE(null,"An exception occurred", ProblemResponseAction.MAIL),
// UNKNOWN_MONGO_DB_HOST(null,"Unknown Mongo database host", ProblemResponseAction.MAIL),
// XMPMETADATACREATION(null,"Unable to crate XMP metadata for thumbnail", ProblemResponseAction.IGNORE),
// XMPMETADATARETRIEVAL(null,"Error while reading XMP metadata from thumbnail", ProblemResponseAction.IGNORE),
// NO_OLD_PASSWORD(null,"Old password does not exist.", ProblemResponseAction.IGNORE);


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
