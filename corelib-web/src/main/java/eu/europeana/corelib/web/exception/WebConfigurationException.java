package eu.europeana.corelib.web.exception;

import eu.europeana.corelib.definitions.exception.EuropeanaException;
import eu.europeana.corelib.definitions.exception.ProblemType;

public class WebConfigurationException extends EuropeanaException {
	private static final long serialVersionUID = 4129679235632668628L;

	public WebConfigurationException(ProblemType problem) {
		super(problem);
	}

}
