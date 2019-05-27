package eu.europeana.corelib.mongo.server;

import eu.europeana.corelib.edm.exceptions.MongoRuntimeException;
import eu.europeana.corelib.storage.MongoServer;
import eu.europeana.corelib.definitions.edm.beans.FullBean;
import eu.europeana.corelib.edm.exceptions.MongoDBException;
import eu.europeana.corelib.tools.lookuptable.EuropeanaIdMongoServer;
import eu.europeana.corelib.web.exception.EuropeanaException;

/**
 * Basic MongoDB server implementation
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
public interface EdmMongoServer extends MongoServer {

	/**
	 * A basic implementation of a MongoDB Server connection
	 * 
	 * @param id
	 *            The object id to retrieve from the database
	 * @return A document from MongoDB - case where the user selects to retrieve
	 *         one specific object
	 * @throws MongoDBException 
	 */
	FullBean getFullBean(String id) throws MongoDBException, MongoRuntimeException, EuropeanaException;

	/**
	 * Search using the rdf:about field of an EDM entity
	 * 
	 * @param <T>
	 *            The Class type of an EDM Entity
	 * @param clazz
	 *            The Class name of an EDM entity
	 * @param about
	 *            The unique identifier of an EDM Entity
	 * @return The EDM Mongo Entity
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	<T> T searchByAbout(Class<T> clazz, String about);

	/**
	 * Resolve a fullbean based on its id. This method should be called if the
	 * getFullBean method has failed
	 * 
	 * @param id
	 * @return
	 */
	FullBean resolve(String id);

	/**
	 * Set the EuropeanaId redirect server
	 * 
	 * @param europeanaIdMongoServer
	 */
	void setEuropeanaIdMongoServer(EuropeanaIdMongoServer europeanaIdMongoServer);
}
