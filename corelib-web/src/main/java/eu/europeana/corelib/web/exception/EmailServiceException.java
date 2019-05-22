package eu.europeana.corelib.web.exception;


/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public class EmailServiceException extends EuropeanaException {
	private static final long serialVersionUID = -3889168743374306317L;

	public EmailServiceException(ProblemType problem) {
		super(problem);
	}

	public EmailServiceException(Throwable causedBy, ProblemType problem) {
		super(causedBy, problem);
	}
}