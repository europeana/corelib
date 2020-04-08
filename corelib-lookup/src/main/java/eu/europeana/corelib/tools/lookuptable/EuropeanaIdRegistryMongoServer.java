package eu.europeana.corelib.tools.lookuptable;

import org.mongodb.morphia.Datastore;

import java.util.List;


/**
 * @author Georgios Markakis (gwarkx@hotmail.com)
 * @since Feb 27, 2014
 */
@Deprecated // to be replaced soon with new redirection functionality
public interface EuropeanaIdRegistryMongoServer {

	EuropeanaIdMongoServer getEuropeanaIdMongoServer();

	/**
	 * Find the EuropeanaId records based on the oldId
	 * 
	 * @param originalId the id to search for
	 * @param collectionid
	 * @return
	 */
	List<EuropeanaIdRegistry> retrieveEuropeanaIdFromOriginal(
			String originalId, String collectionid);

	/**
	 * Find the EuropeanaId records based on the newId (Europeana id)
	 * 
	 * @param newId
	 *            The id to search for
	 * @return
	 */
	EuropeanaIdRegistry retrieveEuropeanaIdFromNew(String newId);

	/**
	 * Check if the record has oldIDs based on the newID
	 * 
	 * @param newId
	 *            the newID
	 * @return true if oldIDs are present false otherwise
	 */
	boolean oldIdExists(String newId);

	/**
	 * Check if the record has newID based on the oldID
	 * 
	 * @param oldId
	 *            the oldID
	 * @return true if newIDs are present false otherwise
	 */
	boolean newIdExists(String oldId);

	/**
	 * Get the Morphia Datastore instance
	 * @return a Datastore
	 */
	Datastore getDatastore();
}