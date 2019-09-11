package eu.europeana.corelib.edm.exceptions;

import eu.europeana.corelib.web.exception.EuropeanaException;
import eu.europeana.corelib.web.exception.ProblemType;

/**
 * Exception thrown when there is a problem with the search query (user error)
 *
 * @author Patrick Ehlert
 */
public class SolrQueryException extends EuropeanaException {

    private static final long serialVersionUID = -2969728542278535536L;

    /**
     * @see eu.europeana.corelib.web.exception.EuropeanaException#EuropeanaException(ProblemType)
     */

    public SolrQueryException(ProblemType problemType) {
        super(problemType);
    }

    /**
     * @see eu.europeana.corelib.web.exception.EuropeanaException#EuropeanaException(ProblemType, String)
     */
    public SolrQueryException(ProblemType problemType, String errorDetails) {
        super(problemType, errorDetails);
    }

    /**
     * @see eu.europeana.corelib.web.exception.EuropeanaException#EuropeanaException(ProblemType, Throwable)
     */
    public SolrQueryException(ProblemType problemType, Throwable causedBy){
        super(problemType, causedBy);
    }

}
