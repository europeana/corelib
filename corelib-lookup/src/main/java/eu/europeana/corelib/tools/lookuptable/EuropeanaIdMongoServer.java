package eu.europeana.corelib.tools.lookuptable;

import java.util.List;

import com.google.code.morphia.Datastore;
import eu.europeana.publication.common.ICollection;


public interface EuropeanaIdMongoServer extends ICollection{

	public abstract void createDatastore();

	/**
	 * Find the EuropeanaId records based on the oldId
	 * 
	 * @param oldId
	 *            The id to search for
	 * @return
	 */
	public abstract EuropeanaId retrieveEuropeanaIdFromOld(String oldId);

	/**
	 * Find the EuropeanaId records based on the newId
	 * 
	 * @param newId
	 *            The id to search for
	 * @return
	 */
	public abstract List<EuropeanaId> retrieveEuropeanaIdFromNew(String newId);

	/**
	 * Check if the record has oldIDs based on the newID
	 * 
	 * @param newId
	 *            the newID
	 * @return true if oldIDs are present false otherwise
	 */
	public abstract boolean oldIdExists(String newId);

	/**
	 * Check if the record has newID based on the oldID
	 * 
	 * @param oldId
	 *            the oldID
	 * @return true if newIDs are present false otherwise
	 */
	public abstract boolean newIdExists(String oldId);

	/**
	 * Set the last accessed field on the record
	 * 
	 * @param oldId
	 *            The oldId
	 * @param newId
	 *            The newId
	 */
	public abstract void setLastAccessed(String oldId);

	/**
	 * Save the europeanaId a update any references to it
	 * 
	 * @param europeanaId
	 *            The europeanaId to save
	 */
	public abstract void saveEuropeanaId(EuropeanaId europeanaId);

	/**
	 * Delete a specific EuropeanaID record
	 * 
	 * @param oldId
	 *            The oldId to search for
	 * @param newId
	 *            The newId to search for
	 */
	public abstract void deleteEuropeanaId(String oldId, String newId);

	/**
	 * Delete all the records based on the oldID
	 * 
	 * @param oldId
	 *            The id to search for
	 */
	public abstract void deleteEuropeanaIdFromOld(String oldId);

	/**
	 * Delete all the records based on the newID
	 * 
	 * @param newId
	 *            The id to search for
	 */
	public abstract void deleteEuropeanaIdFromNew(String newId);

	public abstract void updateTime(String newId, String oldId);

	public abstract void setDatastore(Datastore datastore);

	public abstract EuropeanaId find();

	public abstract EuropeanaId findOne(String oldId);

}