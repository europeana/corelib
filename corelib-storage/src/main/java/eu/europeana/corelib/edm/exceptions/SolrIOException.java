package eu.europeana.corelib.edm.exceptions;

import eu.europeana.corelib.web.exception.EuropeanaException;
import eu.europeana.corelib.web.exception.ProblemType;

/**
 * Exception thrown when there are connections problems with Solr
 * @author Patrick Ehlert
 * Created on 03-07-2018
 */
public class SolrIOException extends EuropeanaException {

    private static final long serialVersionUID = -7926477896852883299L;

    public SolrIOException(ProblemType problemType) {
        super(problemType);
    }

    public SolrIOException(Throwable causedBy, ProblemType problemType){
        super(causedBy, problemType);
    }
}
