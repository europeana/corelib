package eu.europeana.corelib.solr;

import com.google.code.morphia.Datastore;

public interface MongoServer {
	 /**
     * Basic information for MongoDB connection
     *
     * @return Information on MongoDB server configuration
     */
    String toString();

    /**
     * Return the datastore from a MongoDB Server
     *
     * @return The datastore from the MongoDB Server
     */
    Datastore getDatastore();

    /**
     *Close the connection to the MongoDB Server
     */
    void close();
}
