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
package eu.europeana.corelib.tools.lookuptable.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;
import com.mongodb.Mongo;

import eu.europeana.corelib.solr.MongoServer;
import eu.europeana.corelib.tools.lookuptable.EuropeanaId;
import eu.europeana.corelib.tools.lookuptable.EuropeanaIdMongoServer;

/**
 * Class for setting and accessing the EuropeanaID Lookup Table
 * 
 * @author yorgos.mamakis@ kb.nl
 * 
 */
public class EuropeanaIdMongoServerImpl implements MongoServer, EuropeanaIdMongoServer {

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
	public EuropeanaIdMongoServerImpl(Mongo mongoServer, String databaseName,
			String username,String password) {
		this.mongoServer = mongoServer;
		this.databaseName = databaseName;
			this.username = username;
			this.password = password;

		// createDatastore();
	}

	/* (non-Javadoc)
	 * @see eu.europeana.corelib.tools.lookuptable.impl.EuropeanaIdMongoServer#createDatastore()
	 */
	@Override
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

	/* (non-Javadoc)
	 * @see eu.europeana.corelib.tools.lookuptable.impl.EuropeanaIdMongoServer#retrieveEuropeanaIdFromOld(java.lang.String)
	 */
	@Override
	public EuropeanaId retrieveEuropeanaIdFromOld(String oldId) {
		try {
			
			EuropeanaId id = datastore.find(EuropeanaId.class)
					.field("oldId").equal(oldId).get();
			System.out.println(oldId);
			return id;
		} catch (Exception e) {
			System.out.println(e);

			e.printStackTrace();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see eu.europeana.corelib.tools.lookuptable.impl.EuropeanaIdMongoServer#retrieveEuropeanaIdFromNew(java.lang.String)
	 */
	@Override
	public List<EuropeanaId> retrieveEuropeanaIdFromNew(String newId) {
		return datastore.find(EuropeanaId.class).field("newId").equal(newId)
				.asList();
	}

	/* (non-Javadoc)
	 * @see eu.europeana.corelib.tools.lookuptable.impl.EuropeanaIdMongoServer#oldIdExists(java.lang.String)
	 */
	@Override
	public boolean oldIdExists(String newId) {
		return datastore.find(EuropeanaId.class).field("newId").equal(newId)
				.get() != null ? true : false;
	}

	/* (non-Javadoc)
	 * @see eu.europeana.corelib.tools.lookuptable.impl.EuropeanaIdMongoServer#newIdExists(java.lang.String)
	 */
	@Override
	public boolean newIdExists(String oldId) {
		return datastore.find(EuropeanaId.class).field("oldId").equal(oldId) != null ? true
				: false;
	}

	/* (non-Javadoc)
	 * @see eu.europeana.corelib.tools.lookuptable.impl.EuropeanaIdMongoServer#setLastAccessed(java.lang.String)
	 */
	@Override
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

	/* (non-Javadoc)
	 * @see eu.europeana.corelib.tools.lookuptable.impl.EuropeanaIdMongoServer#saveEuropeanaId(eu.europeana.corelib.tools.lookuptable.EuropeanaId)
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

	/* (non-Javadoc)
	 * @see eu.europeana.corelib.tools.lookuptable.impl.EuropeanaIdMongoServer#deleteEuropeanaId(java.lang.String, java.lang.String)
	 */
	@Override
	public void deleteEuropeanaId(String oldId, String newId) {

		Query<EuropeanaId> deleteQuery = datastore
				.createQuery(EuropeanaId.class).field("oldId").equal(oldId)
				.field("newId").equal(newId);

		datastore.findAndDelete(deleteQuery);
	}

	/* (non-Javadoc)
	 * @see eu.europeana.corelib.tools.lookuptable.impl.EuropeanaIdMongoServer#deleteEuropeanaIdFromOld(java.lang.String)
	 */
	@Override
	public void deleteEuropeanaIdFromOld(String oldId) {
		Query<EuropeanaId> deleteQuery = datastore
				.createQuery(EuropeanaId.class).field("oldId").equal(oldId);

		datastore.findAndDelete(deleteQuery);
	}

	/* (non-Javadoc)
	 * @see eu.europeana.corelib.tools.lookuptable.impl.EuropeanaIdMongoServer#deleteEuropeanaIdFromNew(java.lang.String)
	 */
	@Override
	public void deleteEuropeanaIdFromNew(String newId) {
		Query<EuropeanaId> deleteQuery = datastore
				.createQuery(EuropeanaId.class).field("newId").equal(newId);

		datastore.findAndDelete(deleteQuery);
	}

	/* (non-Javadoc)
	 * @see eu.europeana.corelib.tools.lookuptable.impl.EuropeanaIdMongoServer#updateTime(java.lang.String, java.lang.String)
	 */
	@Override
	public void updateTime(String newId, String oldId) {
		Query<EuropeanaId> updateQuery = datastore
				.createQuery(EuropeanaId.class).field("oldId").equal(oldId)
				.field("newId").equal(newId);
		UpdateOperations<EuropeanaId> ops = datastore.createUpdateOperations(
				EuropeanaId.class).set("timestamp", new Date().getTime());
		datastore.update(updateQuery, ops);
	}

	/* (non-Javadoc)
	 * @see eu.europeana.corelib.tools.lookuptable.impl.EuropeanaIdMongoServer#setDatastore(com.google.code.morphia.Datastore)
	 */
	@Override
	public void setDatastore(Datastore datastore) {
		this.datastore = datastore;

	}

	/* (non-Javadoc)
	 * @see eu.europeana.corelib.tools.lookuptable.impl.EuropeanaIdMongoServer#find()
	 */
	@Override
	public EuropeanaId find() {
		return datastore.find(EuropeanaId.class).get();
	}

	/* (non-Javadoc)
	 * @see eu.europeana.corelib.tools.lookuptable.impl.EuropeanaIdMongoServer#findOne(java.lang.String)
	 */
	@Override
	public EuropeanaId findOne(String oldId) {
		return datastore.find(EuropeanaId.class, "oldId", oldId).get();
	}
}
