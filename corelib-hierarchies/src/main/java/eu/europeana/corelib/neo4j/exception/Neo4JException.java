package eu.europeana.corelib.neo4j.exception;

import eu.europeana.corelib.web.exception.EuropeanaException;
import eu.europeana.corelib.web.exception.ProblemType;

/**
 * Basic Neo4JException
 *
 * @author luthien (maike.dulk@europeana.eu)

 */
public class Neo4JException extends EuropeanaException {

	private static final long serialVersionUID = 662625433933164897L;

	public Neo4JException(ProblemType problem) {
		super(problem);
	}

	public Neo4JException(ProblemType problem, String additionalInfo) {
		super(problem, additionalInfo);
	}

	public Neo4JException(Throwable causedBy, ProblemType problem) {
		super(causedBy,problem);
	}
}
