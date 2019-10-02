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

    /**
     * @see eu.europeana.corelib.web.exception.EuropeanaException#EuropeanaException(ProblemType)
     */
    public SolrTypeException(ProblemType problemType) {
        super(problemType);
    }

    /**
     * @see eu.europeana.corelib.web.exception.EuropeanaException#EuropeanaException(ProblemType, String)
     */
    public SolrTypeException(ProblemType problemType, String errorDetails) {
        super(problemType, errorDetails);
    }

}
