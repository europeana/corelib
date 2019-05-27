package eu.europeana.corelib.web.exception;



/**
 * Abstract EuropeanaException, modules should define their own extension based on this one.
 * 
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * @author Luthien <maike.dulk@europeana.eu>
 */
public abstract class EuropeanaException extends Exception {
	private static final long serialVersionUID = 4759945931809288624L;

	private ProblemType problem;

	private String additionalInfo;

	public EuropeanaException(ProblemType problem) {
		this.problem = problem;
	}

	/**
	 * Create a new exception of the provided problem type. Note that when additional information is provided this is appended to the
	 * exception message.
	 * @param problem required field
	 * @param additionalInfo optional string describing details of the problem
	 */
	public EuropeanaException(ProblemType problem, String additionalInfo) {
		this.problem = problem;
		this.additionalInfo = additionalInfo;
	}

	public EuropeanaException(Throwable causedBy, ProblemType problem) {
		super(causedBy);
		this.problem = problem;
	}

	@Override
	public String getMessage() {
		return problem.getMessage() + (additionalInfo == null ? "" : " - "+additionalInfo);
	}

	public ProblemType getProblem() {
		return problem;
	}

}
