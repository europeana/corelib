package eu.europeana.corelib.web.exception;

import eu.europeana.corelib.definitions.exception.EuropeanaException;
import eu.europeana.corelib.definitions.exception.ProblemType;

@SuppressWarnings("serial")
public class InvalidUrlException extends EuropeanaException {

	public InvalidUrlException() {
		super(ProblemType.INVALID_URL);
	}

}
