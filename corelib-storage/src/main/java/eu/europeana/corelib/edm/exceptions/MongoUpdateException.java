package eu.europeana.corelib.edm.exceptions;

/**
 * Exception that is thrown when there is a problem updating a record field in Mongo (specifically via the MongoUtils class)
 * @author Patrick Ehlert on 26-9-17.
 */
public class MongoUpdateException extends Exception {

    private static final long serialVersionUID = -7815567791723210299L;

    public MongoUpdateException(Throwable t) {
        super(t);
    }

    public MongoUpdateException(String message, Throwable t) {
        super(message, t);
    }
}
