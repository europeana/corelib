package eu.europeana.corelib.web.exception;


@SuppressWarnings("serial")
public class InvalidUrlException extends EuropeanaException {

	public InvalidUrlException() {
		super(ProblemType.INVALID_URL);
	}

}
