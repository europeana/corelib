package eu.europeana.corelib.storage;

import com.mongodb.client.MongoClient;

/**
 * Interface for MongoClientProvider
 */
public interface MongoProvider {

    /**
     * @return the provided mongoClient
     */
    public MongoClient getMongoClient();

    /**
     * @return the default database defined for this MongoClient (null if not set)
     */
    public String getDefaultDatabase();

    /**
     * Closes the created mongoClient
     */
    public void close();
}
