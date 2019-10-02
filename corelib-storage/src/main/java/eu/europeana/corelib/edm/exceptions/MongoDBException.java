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

    /**
     * @see eu.europeana.corelib.web.exception.EuropeanaException#EuropeanaException(ProblemType)
     */
    public MongoDBException(ProblemType problem) {
        super(problem);
    }

    /**
     * @see eu.europeana.corelib.web.exception.EuropeanaException#EuropeanaException(ProblemType, Throwable)
     */
    public MongoDBException(ProblemType problem, Throwable causedBy) {
        super(problem, causedBy);
    }
}
