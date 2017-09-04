package eu.europeana.corelib.edm.exceptions;

import eu.europeana.corelib.web.exception.EuropeanaException;
import eu.europeana.corelib.web.exception.ProblemType;

/**
 * Exception that can be thrown when received data is invalid and/or inconsistent
 * @author Patrick Ehlert
 */
public class BadDataException extends EuropeanaException{

    private static final long serialVersionUID = -343856562570766816L;

    /**
     * @see eu.europeana.corelib.web.exception.EuropeanaException#EuropeanaException(ProblemType)
     */
    public BadDataException(ProblemType problem) {
        super(problem);
    }

    /**
     * @see eu.europeana.corelib.web.exception.EuropeanaException#EuropeanaException(ProblemType, String)
     */
    public BadDataException(ProblemType problem, String additionalInfo) {
        super(problem, additionalInfo);
    }

    /**
     * @see eu.europeana.corelib.web.exception.EuropeanaException#EuropeanaException(Throwable, ProblemType)
     */
    public BadDataException(Throwable causedBy, ProblemType problem) {
        super(causedBy,problem);
    }
}
