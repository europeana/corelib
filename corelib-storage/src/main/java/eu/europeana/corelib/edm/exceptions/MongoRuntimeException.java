package eu.europeana.corelib.edm.exceptions;

import eu.europeana.corelib.web.exception.EuropeanaException;
import eu.europeana.corelib.web.exception.ProblemType;

/**
 * im, luthien, hain echant na 25/07/2016.
 */

public class MongoRuntimeException extends EuropeanaException {

    private static final long serialVersionUID = -5078231212709296605L;

    /**
     * @see eu.europeana.corelib.web.exception.EuropeanaException#EuropeanaException(ProblemType)
     */
    public MongoRuntimeException(ProblemType problem) {
        super(problem);
    }

    /**
     * @see eu.europeana.corelib.web.exception.EuropeanaException#EuropeanaException(ProblemType, Throwable)
     */
    public MongoRuntimeException(ProblemType problem, Throwable causedBy) {
        super(problem, causedBy);
    }

}

