package eu.europeana.corelib.solr.exceptions;

import eu.europeana.corelib.definitions.exception.EuropeanaException;
import eu.europeana.corelib.definitions.exception.ProblemType;

public class MongoDBException extends EuropeanaException {
	private static final long serialVersionUID = 4785113365867141067L;
	
	public MongoDBException(ProblemType problem) {
		super(problem);
	}

	public MongoDBException(Throwable causedBy,ProblemType problem) {
		super(causedBy,problem);
	}
	

}
