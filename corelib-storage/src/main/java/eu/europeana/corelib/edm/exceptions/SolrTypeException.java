package eu.europeana.corelib.edm.exceptions;

import eu.europeana.corelib.web.exception.EuropeanaException;
import eu.europeana.corelib.web.exception.ProblemType;

/**
 * Exception thrown when SearchService gets a bean other than BriefBean or ApiBean
 * 
 * @author Yorgos.Mamakis
 *
 */
public class SolrTypeException extends EuropeanaException {
	private static final long serialVersionUID = 1354282016526186556L;

	public SolrTypeException(ProblemType problemType) {
		super(problemType);
	}

	/**
	 * @see eu.europeana.corelib.definitions.exception.EuropeanaException#EuropeanaException(ProblemType, String)
	 */
	public SolrTypeException(ProblemType problemType, String additionalInfo) {
		super(problemType, additionalInfo);
	}

	public SolrTypeException(Throwable causedBy, ProblemType problemType){
		super(causedBy, problemType);
	}
}
