package eu.europeana.corelib.db.exception;

import eu.europeana.corelib.definitions.exception.EuropeanaException;
import eu.europeana.corelib.definitions.exception.ProblemType;

public class DatabaseException extends EuropeanaException {
	private static final long serialVersionUID = 5060479670279882199L;


	public DatabaseException(ProblemType problem) {
		super(problem);
	}
	
	public DatabaseException(Throwable causedBy, ProblemType problem) {
		super(causedBy, problem);
	}

}
