package eu.europeana.corelib.solr.mongodb;

import org.bson.types.ObjectId;

import eu.europeana.corelib.solr.bean.FullBean;
/**
 * Basic MongoDB server implementation
 * @author yorgos.mamakis@kb.nl
 *
 */
public interface MongoDBServer {
	
	/**
	 * A basic implementation of a MongoDB Server connection
	 * @param id - The object id to retrieve from the database
	 * @return A document from MongoDB - case where the user selects to retrieve one specific object
	 */
	public FullBean getFullBean(ObjectId id);
	
	/**
	 * Basic information for MongoDB connection
	 * @return Information on MongoDB server configuration
	 */
	public String toString();
}
