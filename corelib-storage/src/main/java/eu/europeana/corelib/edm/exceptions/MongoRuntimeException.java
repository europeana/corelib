package eu.europeana.corelib.edm.exceptions;

import eu.europeana.corelib.web.exception.EuropeanaException;
import eu.europeana.corelib.web.exception.ProblemType;

/**
 * im, luthien, hain echant na 25/07/2016.
 */

public class MongoRuntimeException extends EuropeanaException {

    private static final long serialVersionUID = 1L;

    public MongoRuntimeException(ProblemType problem) {
        super(problem);
    }

    public MongoRuntimeException(Throwable causedBy,ProblemType problem) {
        super(causedBy,problem);
    }

}

