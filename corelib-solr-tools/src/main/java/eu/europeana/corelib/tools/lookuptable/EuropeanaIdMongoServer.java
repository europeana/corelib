package eu.europeana.corelib.tools.lookuptable;

import java.util.Date;
import java.util.List;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;
import com.mongodb.Mongo;

import eu.europeana.corelib.solr.MongoServer;

/**
 * Class for setting and accessing the EuropeanaID Lookup Table
 * 
 * @author yorgos.mamakis@ kb.nl
 * 
 */
public class EuropeanaIdMongoServer implements MongoServer {

	private Mongo mongoServer;
	private String databaseName;
	private Datastore datastore;

	/**
	 * Constructor of the EuropeanaIDMongoServer
	 * 
	 * @param mongoServer
	 *            The server to connect to
	 * @param databaseName
	 *            The database to connect to
	 */
	public EuropeanaIdMongoServer(Mongo mongoServer, String databaseName) {
		this.mongoServer = mongoServer;
		this.databaseName = databaseName;
		createDatastore();
	}

	private void createDatastore() {
		Morphia morphia = new Morphia();
		morphia.map(EuropeanaId.class);
		datastore = morphia.createDatastore(mongoServer, databaseName);
		datastore.ensureIndexes();
	}

	/**
	 * Get the datastore
	 */
	@Override
	public Datastore getDatastore() {
		return this.datastore;
	}

	/**
	 * Close the connection to the server
	 */
	@Override
	public void close() {
		mongoServer.close();
	}

	/**
	 * Find the EuropeanaId records based on the oldId
	 * 
	 * @param oldId
	 *            The id to search for
	 * @return
	 */
	public List<EuropeanaId> retrieveEuropeanaIdFromOld(String oldId) {
		return datastore.find(EuropeanaId.class).field("oldId").equal(oldId).asList();
	}
	
	/**
	 * Find the EuropeanaId records based on the newId
	 * 
	 * @param newId
	 *            The id to search for
	 * @return
	 */
	public List<EuropeanaId> retrieveEuropeanaIdFromNew(String newId) {
		return datastore.find(EuropeanaId.class).field("newId").equal(newId).asList();
	}

	/**
	 * Check if the record has oldIDs based on the newID
	 * @param newId the newID
	 * @return true if oldIDs are present false otherwise
	 */
	public boolean oldIdExists(String newId) {
		return datastore.find(EuropeanaId.class).field("newId").equal(newId)
				.get() != null ? true : false;
	}

	/**
	 * Check if the record has newID based on the oldID
	 * @param oldId the oldID
	 * @return true if newIDs are present false otherwise
	 */
	public boolean newIdExists(String oldId) {
		return datastore.find(EuropeanaId.class).field("oldId").equal(oldId)
				.get() != null ? true : false;
	}
	

	/**
	 * Set the last accessed field on the record
	 * @param oldId The oldId
	 * @param newId The newId
	 */
	public void setLastAccessed(String oldId) {
		List<EuropeanaId> oldIdList = retrieveEuropeanaIdFromOld(oldId);
		EuropeanaId oldEuropeanaId = oldIdList.get(0);
		oldEuropeanaId.setLastAccess(new Date().getTime());
		Query<EuropeanaId> updateQuery = datastore
				.createQuery(EuropeanaId.class).field("oldId").equal(oldId);
		UpdateOperations<EuropeanaId> ops =datastore.createUpdateOperations(EuropeanaId.class)
				.set("lastAccess", oldEuropeanaId.getLastAccess());
		datastore.update(updateQuery, ops);
	}

	/**
	 * Save the europeanaId a update any references to it
	 * @param europeanaId The europeanaId to save
	 */
	public void saveEuropeanaId(EuropeanaId europeanaId) {
		List<EuropeanaId> oldIdList = retrieveEuropeanaIdFromOld(europeanaId.getOldId());
		if(oldIdList.size()>0){
			EuropeanaId oldId = oldIdList.get(0);
			EuropeanaId newId = new EuropeanaId();
			newId.setOldId(oldId.getNewId());
			newId.setNewId(europeanaId.getNewId());
			newId.setTimestamp(new Date().getTime());
			newId.setLastAccess(0);
			datastore.save(newId);
			oldId.setNewId(europeanaId.getNewId());
			Query<EuropeanaId> updateQuery = datastore
					.createQuery(EuropeanaId.class).field("oldId").equal(oldId.getOldId());
			UpdateOperations<EuropeanaId> ops =datastore.createUpdateOperations(EuropeanaId.class)
					.set("newId", oldId.getNewId());
			datastore.update(updateQuery, ops);
		}
		else{
			datastore.save(europeanaId);
		}
	}

	/**
	 * Delete a specific EuropeanaID record
	 * @param oldId The oldId to search for
	 * @param newId The newId to search for
	 */
	public void deleteEuropeanaId(String oldId, String newId) {

	}

	/**
	 * Delete all the records based on the oldID
	 * @param oldId The id to search for
	 */
	public void deleteEuropeanaIdFromOld(String oldId){
		
	}
	
	/**
	 * Delete all the records based on the newID
	 * @param newId The id to search for
	 */
	public void deleteEuropeanaIdFromNew(String newId){
		
	}
}
