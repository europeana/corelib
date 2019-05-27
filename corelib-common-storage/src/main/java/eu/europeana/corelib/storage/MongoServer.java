package eu.europeana.corelib.storage;

import org.mongodb.morphia.Datastore;

/**
 * A basic interface for a connection to MongoDB storage
 * 
 * @author Yorgos.Mamakis@ kb.nl
 *
 */
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
	 * Close the connection to the MongoDB Server
	 */
	void close();
}
