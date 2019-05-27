package eu.europeana.corelib.lookup.impl;

import com.mongodb.MongoClient;
import org.apache.log4j.Logger;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import eu.europeana.corelib.storage.MongoServer;
import eu.europeana.corelib.tools.lookuptable.EuropeanaId;
import eu.europeana.corelib.tools.lookuptable.EuropeanaIdMongoServer;

import java.util.Date;
import java.util.List;

/**
 * Class for setting and accessing the EuropeanaID Lookup Table
 * 
 * @author yorgos.mamakis@ kb.nl
 * 
 */
public class EuropeanaIdMongoServerImpl implements MongoServer, EuropeanaIdMongoServer {

	private static final Logger LOG = Logger.getLogger(EuropeanaIdMongoServerImpl.class);
	private static final String OLD_ID = "oldId";
	private static final String NEW_ID = "newId";

	protected MongoClient mongoClient;
	protected String databaseName;
	protected Datastore datastore;

	/**
	 * Constructor of the EuropeanaIDMongoServer
	 * Any required login credentials should be in the provided MongoClient
	 * 
	 * @param mongoClient the server to connect to
	 * @param databaseName the database to connect to
	 */
	public EuropeanaIdMongoServerImpl(MongoClient mongoClient, String databaseName) {
		this.mongoClient = mongoClient;
		this.databaseName = databaseName;
	}

	/**
	 * @see eu.europeana.corelib.tools.lookuptable.EuropeanaIdMongoServer#createDatastore()
	 */
	@Deprecated
	@Override
	public void createDatastore() {
		createDatastore(false);
	}

	/**
	 * @see eu.europeana.corelib.tools.lookuptable.EuropeanaIdMongoServer#createDatastore(boolean)
	 */
	@Override
	public void createDatastore(boolean createIndexes) {
		Morphia morphia = new Morphia();
		morphia.map(EuropeanaId.class);
		datastore = morphia.createDatastore(mongoClient, databaseName);
		if (createIndexes) {
			datastore.ensureIndexes();
		}
		LOG.info("EuropeanaIdMongoServer datastore is created");
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
		LOG.info("Closing MongoClient for EuropeanaIdMongoServer");
		mongoClient.close();
	}

	/**
	 * @see eu.europeana.corelib.tools.lookuptable.EuropeanaIdMongoServer#retrieveEuropeanaIdFromOld(java.lang.String)
	 */
	@Override
	public EuropeanaId retrieveEuropeanaIdFromOld(String oldId) {
		try {
			return datastore.find(EuropeanaId.class).field(OLD_ID).equal(oldId).get();
		} catch (Exception e) {
			LOG.error("Error retrieving europeanaId", e);
		}
		return null;
	}

	@Override
	public EuropeanaId retrieveEuropeanaIdFromOld(List<String> oldIds) {
		return datastore.find(EuropeanaId.class).field(OLD_ID).hasAnyOf(oldIds).get();
	}

	/**
	 * @see eu.europeana.corelib.tools.lookuptable.EuropeanaIdMongoServer#retrieveEuropeanaIdFromNew(java.lang.String)
	 */
	@Override
	public List<EuropeanaId> retrieveEuropeanaIdFromNew(String newId) {
		return datastore.find(EuropeanaId.class).field(NEW_ID).equal(newId)
				.asList();
	}

	/**
	 * @see eu.europeana.corelib.tools.lookuptable.EuropeanaIdMongoServer#oldIdExists(java.lang.String)
	 */
	@Override
	public boolean oldIdExists(String newId) {
		return datastore.find(EuropeanaId.class).field(NEW_ID).equal(newId)
				.get() != null;
	}

	/**
	 * @see eu.europeana.corelib.tools.lookuptable.EuropeanaIdMongoServer#newIdExists(java.lang.String)
	 */
	@Override
	public boolean newIdExists(String oldId) {
		return datastore.find(EuropeanaId.class).field(OLD_ID).equal(oldId).get() != null;
	}

	/**
	 * @see eu.europeana.corelib.tools.lookuptable.EuropeanaIdMongoServer#setLastAccessed(java.lang.String)
	 */
	@Override
	public void setLastAccessed(String oldId) {
		// List<EuropeanaId> oldIdList = retrieveEuropeanaIdFromOld(oldId);
		EuropeanaId oldEuropeanaId = retrieveEuropeanaIdFromOld(oldId);
		oldEuropeanaId.setLastAccess(new Date().getTime());
		Query<EuropeanaId> updateQuery = datastore
				.createQuery(EuropeanaId.class).field(OLD_ID).equal(oldId);
		UpdateOperations<EuropeanaId> ops = datastore.createUpdateOperations(
				EuropeanaId.class).set("lastAccess",
				oldEuropeanaId.getLastAccess());
		datastore.update(updateQuery, ops);
	}

	/**
	 * @see eu.europeana.corelib.tools.lookuptable.EuropeanaIdMongoServer#saveEuropeanaId(eu.europeana.corelib.tools.lookuptable.EuropeanaId)
	 */
	@Override
	public void saveEuropeanaId(EuropeanaId europeanaId) {
		// List<EuropeanaId> oldIdList =
		// retrieveEuropeanaIdFromOld(europeanaId.getOldId());
		// if(oldIdList.size()>0){
		EuropeanaId oldEuropeanaId = retrieveEuropeanaIdFromOld(europeanaId
				.getOldId());
		if (oldEuropeanaId != null) {

			oldEuropeanaId.setNewId(europeanaId.getNewId());
			Query<EuropeanaId> updateQuery = datastore
					.createQuery(EuropeanaId.class).field(OLD_ID)
					.equal(europeanaId.getOldId());
			UpdateOperations<EuropeanaId> ops = datastore
					.createUpdateOperations(EuropeanaId.class).set(NEW_ID,
							oldEuropeanaId.getNewId());
			datastore.update(updateQuery, ops);
		} else {
			datastore.save(europeanaId);
		}

	}

	/**
	 * @see eu.europeana.corelib.tools.lookuptable.EuropeanaIdMongoServer#deleteEuropeanaId(java.lang.String, java.lang.String)
	 */
	@Override
	public void deleteEuropeanaId(String oldId, String newId) {

		Query<EuropeanaId> deleteQuery = datastore
				.createQuery(EuropeanaId.class).field(OLD_ID).equal(oldId)
				.field(NEW_ID).equal(newId);

		datastore.findAndDelete(deleteQuery);
	}

	/**
	 * @see eu.europeana.corelib.tools.lookuptable.EuropeanaIdMongoServer#deleteEuropeanaIdFromOld(java.lang.String)
	 */
	@Override
	public void deleteEuropeanaIdFromOld(String oldId) {
		Query<EuropeanaId> deleteQuery = datastore
				.createQuery(EuropeanaId.class).field(OLD_ID).equal(oldId);

		datastore.findAndDelete(deleteQuery);
	}

	/**
	 * @see eu.europeana.corelib.tools.lookuptable.EuropeanaIdMongoServer#deleteEuropeanaIdFromNew(java.lang.String)
	 */
	@Override
	public void deleteEuropeanaIdFromNew(String newId) {
		Query<EuropeanaId> deleteQuery = datastore
				.createQuery(EuropeanaId.class).field(NEW_ID).equal(newId);

		datastore.findAndDelete(deleteQuery);
	}

	/**
	 * @see eu.europeana.corelib.tools.lookuptable.EuropeanaIdMongoServer#updateTime(java.lang.String, java.lang.String)
	 */
	@Override
	public void updateTime(String newId, String oldId) {
		Query<EuropeanaId> updateQuery = datastore
				.createQuery(EuropeanaId.class).field(OLD_ID).equal(oldId)
				.field(NEW_ID).equal(newId);
		UpdateOperations<EuropeanaId> ops = datastore.createUpdateOperations(
				EuropeanaId.class).set("timestamp", new Date().getTime());
		datastore.update(updateQuery, ops);
	}

	/**
	 * @see eu.europeana.corelib.tools.lookuptable.EuropeanaIdMongoServer#setDatastore(org.mongodb.morphia.Datastore)
	 */
	@Override
	public void setDatastore(Datastore datastore) {
		this.datastore = datastore;

	}

	/**
	 * @see eu.europeana.corelib.tools.lookuptable.EuropeanaIdMongoServer#find()
	 */
	@Override
	public EuropeanaId find() {
		return datastore.find(EuropeanaId.class).get();
	}

	/**
	 * @see eu.europeana.corelib.tools.lookuptable.EuropeanaIdMongoServer#findOne(java.lang.String)
	 */
	@Override
	public EuropeanaId findOne(String oldId) {
		return datastore.find(EuropeanaId.class, OLD_ID, oldId).get();
	}

}
