package eu.europeana.corelib.tools.lookuptable;

import java.util.List;
import java.util.Map;


public interface EuropeanaIdRegistryMongoServer {

	public abstract EuropeanaIdMongoServer getEuropeanaIdMongoServer();

	/**
	 * @param origID
	 * @param collectionID
	 * @param xml
	 * @return
	 */
	public abstract LookupResult lookupUiniqueId(String origID,
			String collectionID, String xml, String sessionID);

	/**
	 * Sets a value for a failed record
	 * @param state
	 * @param oldID
	 */
	public abstract void createFailedRecord(LookupState state,
			String collectionID, String oldID, String newId, String xml);

	/**
	 * Deletes a failed record
	 * @param state
	 * @param oldID
	 */
	public abstract void deleteFailedRecord(String europeanaId,
			String collectionID);

	/**
	 * Find the EuropeanaId records based on the oldId
	 * 
	 * @param oldId
	 *            The id to search for
	 * @return
	 */
	public abstract List<EuropeanaIdRegistry> retrieveEuropeanaIdFromOriginal(
			String originalId, String collectionid);

	/**
	 * Find the EuropeanaId records based on the newId (Europeana id)
	 * 
	 * @param newId
	 *            The id to search for
	 * @return
	 */
	public abstract EuropeanaIdRegistry retrieveEuropeanaIdFromNew(String newId);

	/**
	 * Check if the record has oldIDs based on the newID
	 * 
	 * @param newId
	 *            the newID
	 * @return true if oldIDs are present false otherwise
	 */
	public abstract boolean oldIdExists(String newId);

	/**
	 * Retrieve a record from the original XML
	 * @param orId The originalID
	 * @param xml The original XML
	 * @return
	 */
	public abstract EuropeanaIdRegistry retrieveFromOriginalXML(String orId,
			String xml);

	/**
	 * Check if the record has newID based on the oldID
	 * 
	 * @param oldId
	 *            the oldID
	 * @return true if newIDs are present false otherwise
	 */
	public abstract boolean newIdExists(String oldId);

	/**
	 * Delete all the records based on the oldID
	 * @param oldId The id to search for
	 */
	public abstract void deleteEuropeanaIdFromOld(String oldId);

	/**
	 * Delete all the records based on the newID
	 * @param newId The id to search for
	 */
	public abstract void deleteEuropeanaIdFromNew(String newId);

	/**
	 * @param newId
	 * @param oldId
	 */
	public abstract void updateTime(String newId, String oldId);

	/**
	 * Retrieve the failed records for a collection ID
	 * @param collectionId The collection ID to use
	 * @return
	 */
	public abstract List<Map<String, String>> getFailedRecords(
			String collectionId);

}