package eu.europeana.corelib.tools.lookuptable;

import org.mongodb.morphia.Datastore;

import java.util.List;


public interface EuropeanaIdMongoServer {

	void createDatastore();

	/**
	 * Find the EuropeanaId records based on the oldId
     *
     * @param oldId The id to search for
     * @return
	 * @deprecated Use the retrieveEuropeanaIdFromOld(List<String>) instead
	 */
	@Deprecated
	EuropeanaId retrieveEuropeanaIdFromOld(String oldId);
	/**
	 * Find the EuropeanaId records based on the oldIds
     *
     * @param oldIds The ids to search for
	 * @return
	 */
	EuropeanaId retrieveEuropeanaIdFromOld(List<String> oldIds);
	/**
	 * Find the EuropeanaId records based on the newId
	 *
     * @param newId The id to search for
	 * @return
	 */
	List<EuropeanaId> retrieveEuropeanaIdFromNew(String newId);

	/**
	 * Check if the record has oldIDs based on the newID
	 * 
     * @param newId the newID
	 * @return true if oldIDs are present false otherwise
	 */
	boolean oldIdExists(String newId);

	/**
	 * Check if the record has newID based on the oldID
	 * 
     * @param oldId the oldID
	 * @return true if newIDs are present false otherwise
	 */
	boolean newIdExists(String oldId);

	/**
	 * Set the last accessed field on the record
	 * 
     * @param oldId The oldId
	 */
	void setLastAccessed(String oldId);

	/**
	 * Save the europeanaId a update any references to it
	 * 
     * @param europeanaId The europeanaId to save
	 */
	void saveEuropeanaId(EuropeanaId europeanaId);

	/**
	 * Delete a specific EuropeanaID record
	 * 
     * @param oldId The oldId to search for
     * @param newId The newId to search for
	 */
	void deleteEuropeanaId(String oldId, String newId);

	/**
	 * Delete all the records based on the oldID
	 * 
     * @param oldId The id to search for
	 */
	void deleteEuropeanaIdFromOld(String oldId);

	/**
	 * Delete all the records based on the newID
	 * 
     * @param newId The id to search for
	 */
	void deleteEuropeanaIdFromNew(String newId);

	void updateTime(String newId, String oldId);

	void setDatastore(Datastore datastore);

	EuropeanaId find();

	EuropeanaId findOne(String oldId);

}