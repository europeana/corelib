package eu.europeana.corelib.storage;

import com.mongodb.MongoClient;

/**
 * Interface for MongoClientProvider
 */
public interface MongoProvider {

    /**
     * @return the provided mongoClient
     */
    public MongoClient getMongo();

    /**
     * Closes the created mongoClient
     */
    public void close();
}
