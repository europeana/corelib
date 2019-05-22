package eu.europeana.corelib.web.exception;


/**
 * Error that is thrown when there is a problem in the configuration, e.g. when a mandatory field is empty
 */
public class WebConfigurationException extends EuropeanaException {
	private static final long serialVersionUID = 4129679235632668628L;

	/**
	 * @see eu.europeana.corelib.web.exception.EuropeanaException#EuropeanaException(ProblemType, String)
	 */
	public WebConfigurationException(ProblemType problem, String additionalInfo) {
		super(problem, additionalInfo);
	}

}
