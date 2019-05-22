package eu.europeana.corelib.edm.exceptions;

import eu.europeana.corelib.web.exception.EuropeanaException;
import eu.europeana.corelib.web.exception.ProblemType;

/**
 * Basic MongoDBException
 * 
 * @author Yorgos.Mamakis@ kb.nl
 *
 */
public class MongoDBException extends EuropeanaException {
	private static final long serialVersionUID = 4785113365867141067L;

	public MongoDBException(ProblemType problem) {
		super(problem);
	}

	public MongoDBException(Throwable causedBy,ProblemType problem) {
		super(causedBy,problem);
	}
}
