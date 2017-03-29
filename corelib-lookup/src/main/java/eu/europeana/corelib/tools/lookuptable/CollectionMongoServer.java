package eu.europeana.corelib.tools.lookuptable;

import java.util.List;

import org.mongodb.morphia.Datastore;


public interface CollectionMongoServer {

	/**
	 * Retrieve the new CollectionId based on the old CollectionID. This is a
	 * fast method as the old CollectionID is indexed in the Mongo Schema
	 * 
	 * @param oldCollectionId
	 *            The old Collection ID
	 * @return
	 */
	String findNewCollectionId(String oldCollectionId);

	/**
	 * Retrieve the old CollectionId based on the new CollectionID. This is a
	 * slow method as the new CollectionId is NOT indexed in the Mongo Schema
	 * 
	 * @param newCollectionId
	 * @return
	 */
	String findOldCollectionId(String newCollectionId);

	/**
	 * Save a Collection
	 * 
	 * @param collection
	 *            The collection to save
	 */
	void saveCollection(Collection collection);

	/**
	 * Retrieve all stored collections
	 * 
	 * @return
	 */
	List<Collection> retrieveAllCollections();

	void setDatastore(Datastore datastore);

}