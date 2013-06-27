/*
 * Copyright 2007-2012 The Europeana Foundation
 *
 *  Licenced under the EUPL, Version 1.1 (the "Licence") and subsequent versions as approved
 *  by the European Commission;
 *  You may not use this work except in compliance with the Licence.
 * 
 *  You may obtain a copy of the Licence at:
 *  http://joinup.ec.europa.eu/software/page/eupl
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under
 *  the Licence is distributed on an "AS IS" basis, without warranties or conditions of
 *  any kind, either express or implied.
 *  See the Licence for the specific language governing permissions and limitations under
 *  the Licence.
 */
package eu.europeana.corelib.tools.lookuptable;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

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

	protected Mongo mongoServer;
	protected String databaseName;
	protected Datastore datastore;
	protected String username;
	protected String password;

	/**
	 * Constructor of the EuropeanaIDMongoServer
	 * 
	 * @param mongoServer
	 *            The server to connect to
	 * @param databaseName
	 *            The database to connect to
	 */
	public EuropeanaIdMongoServer(Mongo mongoServer, String databaseName,
			String... loginInfo) {
		this.mongoServer = mongoServer;
		this.databaseName = databaseName;
		this.username = loginInfo[0];
		this.password = loginInfo[1];
		// createDatastore();
	}

	public void createDatastore() {
		Morphia morphia = new Morphia();
		morphia.map(EuropeanaId.class);
		datastore = morphia.createDatastore(mongoServer, databaseName);
		datastore.ensureIndexes();
		if (StringUtils.isNotBlank(this.username)
				&& StringUtils.isNotBlank(this.password)) {
			datastore.getDB().authenticate(this.username,
					this.password.toCharArray());
		}
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
	public EuropeanaId retrieveEuropeanaIdFromOld(String oldId) {
		try {
			
			EuropeanaId id = datastore.find(EuropeanaId.class)
					.field("oldId").equal(oldId).get();
			System.out.println(id == null);
			return id;
		} catch (Exception e) {
			System.out.println(e);

			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Find the EuropeanaId records based on the newId
	 * 
	 * @param newId
	 *            The id to search for
	 * @return
	 */
	public List<EuropeanaId> retrieveEuropeanaIdFromNew(String newId) {
		return datastore.find(EuropeanaId.class).field("newId").equal(newId)
				.asList();
	}

	/**
	 * Check if the record has oldIDs based on the newID
	 * 
	 * @param newId
	 *            the newID
	 * @return true if oldIDs are present false otherwise
	 */
	public boolean oldIdExists(String newId) {
		return datastore.find(EuropeanaId.class).field("newId").equal(newId)
				.get() != null ? true : false;
	}

	/**
	 * Check if the record has newID based on the oldID
	 * 
	 * @param oldId
	 *            the oldID
	 * @return true if newIDs are present false otherwise
	 */
	public boolean newIdExists(String oldId) {
		return datastore.find(EuropeanaId.class).field("oldId").equal(oldId) != null ? true
				: false;
	}

	/**
	 * Set the last accessed field on the record
	 * 
	 * @param oldId
	 *            The oldId
	 * @param newId
	 *            The newId
	 */
	public void setLastAccessed(String oldId) {
		// List<EuropeanaId> oldIdList = retrieveEuropeanaIdFromOld(oldId);
		EuropeanaId oldEuropeanaId = retrieveEuropeanaIdFromOld(oldId);
		oldEuropeanaId.setLastAccess(new Date().getTime());
		Query<EuropeanaId> updateQuery = datastore
				.createQuery(EuropeanaId.class).field("oldId").equal(oldId);
		UpdateOperations<EuropeanaId> ops = datastore.createUpdateOperations(
				EuropeanaId.class).set("lastAccess",
				oldEuropeanaId.getLastAccess());
		datastore.update(updateQuery, ops);
	}

	/**
	 * Save the europeanaId a update any references to it
	 * 
	 * @param europeanaId
	 *            The europeanaId to save
	 */
	public void saveEuropeanaId(EuropeanaId europeanaId) {
		// List<EuropeanaId> oldIdList =
		// retrieveEuropeanaIdFromOld(europeanaId.getOldId());
		// if(oldIdList.size()>0){
		EuropeanaId oldEuropeanaId = retrieveEuropeanaIdFromOld(europeanaId
				.getOldId());
		if (oldEuropeanaId != null) {

			oldEuropeanaId.setNewId(europeanaId.getNewId());
			Query<EuropeanaId> updateQuery = datastore
					.createQuery(EuropeanaId.class).field("oldId")
					.equal(europeanaId.getOldId());
			UpdateOperations<EuropeanaId> ops = datastore
					.createUpdateOperations(EuropeanaId.class).set("newId",
							oldEuropeanaId.getNewId());
			datastore.update(updateQuery, ops);
		} else {
			datastore.save(europeanaId);
		}

	}

	/**
	 * Delete a specific EuropeanaID record
	 * 
	 * @param oldId
	 *            The oldId to search for
	 * @param newId
	 *            The newId to search for
	 */
	public void deleteEuropeanaId(String oldId, String newId) {

		Query<EuropeanaId> deleteQuery = datastore
				.createQuery(EuropeanaId.class).field("oldId").equal(oldId)
				.field("newId").equal(newId);

		datastore.findAndDelete(deleteQuery);
	}

	/**
	 * Delete all the records based on the oldID
	 * 
	 * @param oldId
	 *            The id to search for
	 */
	public void deleteEuropeanaIdFromOld(String oldId) {
		Query<EuropeanaId> deleteQuery = datastore
				.createQuery(EuropeanaId.class).field("oldId").equal(oldId);

		datastore.findAndDelete(deleteQuery);
	}

	/**
	 * Delete all the records based on the newID
	 * 
	 * @param newId
	 *            The id to search for
	 */
	public void deleteEuropeanaIdFromNew(String newId) {
		Query<EuropeanaId> deleteQuery = datastore
				.createQuery(EuropeanaId.class).field("newId").equal(newId);

		datastore.findAndDelete(deleteQuery);
	}

	public void updateTime(String newId, String oldId) {
		Query<EuropeanaId> updateQuery = datastore
				.createQuery(EuropeanaId.class).field("oldId").equal(oldId)
				.field("newId").equal(newId);
		UpdateOperations<EuropeanaId> ops = datastore.createUpdateOperations(
				EuropeanaId.class).set("timestamp", new Date().getTime());
		datastore.update(updateQuery, ops);
	}

	public void setDatastore(Datastore datastore) {
		this.datastore = datastore;

	}

	public EuropeanaId find() {
		return datastore.find(EuropeanaId.class).get();
	}

	public EuropeanaId findOne(String oldId) {
		return datastore.find(EuropeanaId.class, "oldId", oldId).get();
	}
}
