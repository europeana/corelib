package eu.europeana.corelib.web.exception;


import org.apache.commons.lang3.StringUtils;

/**
 * Abstract EuropeanaException, modules should define their own extension based on this one.
 *
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * @author Luthien <maike.dulk@europeana.eu>
 */
public abstract class EuropeanaException extends Exception {

    private static final long serialVersionUID = 4759945931809288624L;

    private final ProblemType problem; // required, contains error message, error code and action type
    private String errorDetails; // optional

    /**
     * Create a new exception of the provided problem type.
     * @param problem required field
     */
    public EuropeanaException(ProblemType problem) {
        this.problem = problem;
    }

    /**
     * Create a new exception of the provided problem type.
     * @param problem required field
     * @param errorDetails optional string describing details of the problem
     */
    public EuropeanaException(ProblemType problem, String errorDetails) {
        this.problem = problem;
        this.errorDetails = errorDetails;
    }

    /**
     * Create a new exception based on the provided problem type, plus a exception cause.
     * The message of the exception cause is used to fill the errorDetails field.
     * @param problem particular problem
     * @param causedBy initial exception
     */
    public EuropeanaException(ProblemType problem, Throwable causedBy) {
        super(causedBy);
        this.problem = problem;
        this.errorDetails = causedBy.getMessage();
    }

    /**
     * Create a new exception based on the provided problem type, plus a exception cause.
     * @param problem particular problem
     * @param errorDetails string containing more information about the error
     * @param causedBy initial exception
     */
    public EuropeanaException(ProblemType problem, String errorDetails, Throwable causedBy) {
        super(causedBy);
        this.problem = problem;
        this.errorDetails = errorDetails;
    }

    /**
     * @return Europeana-specific error code (if available)
     */
    public String getErrorCode() {
        return problem.getErrorCode();
    }

    @Override
    public String getMessage() {
        return problem.getMessage();
    }

    public String getErrorDetails() {
        return errorDetails;
    }

    public String getErrorMsgAndDetails() {
        return problem.getMessage() + (StringUtils.isEmpty(errorDetails) ? "" : (" - " +errorDetails));
    }

    public ProblemResponseAction getAction() {
        return problem.getAction();
    }

    /**
     *
     * @deprecated September 2019 There is no need anymore to check which problem you are handling. Instead we rely on
     * Exception type and the GlobalExceptionHandler to determine response httpStatus. Alternatively you can set the
     * response status in a controller before throwing an error. The GlobalExceptionHandler will respect that altered
     * response status
     */
    @Deprecated
    public ProblemType getProblem() {
        return problem;
    }

}
