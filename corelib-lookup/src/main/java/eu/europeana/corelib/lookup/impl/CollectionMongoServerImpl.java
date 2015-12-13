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

package eu.europeana.corelib.lookup.impl;

import java.util.List;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.Mongo;

import eu.europeana.corelib.storage.MongoServer;
import eu.europeana.corelib.tools.lookuptable.Collection;
import eu.europeana.corelib.tools.lookuptable.CollectionMongoServer;

/**
 * Class for creating and accessing the Collection Lookup table
 * 
 * @author yorgos.mamakis@ kb.nl
 * 
 */
public class CollectionMongoServerImpl implements MongoServer, CollectionMongoServer {

	private Mongo mongoServer;
	private String databaseName;
	private Datastore datastore;
	private String username;
	private String password;

	/**
	 * Constructor for the CollectionMongoServer to ensure that everything has
	 * been set upon initialization
	 * 
	 * @param mongoServer
	 *            The Mongo Server to connect to
	 * @param databaseName
	 *            The database to connect to
	 */
	public CollectionMongoServerImpl(Mongo mongoServer, String databaseName) {
		this.mongoServer = mongoServer;
		this.databaseName = databaseName;
		createDatastore();
	}

	public CollectionMongoServerImpl(Datastore datastore){
		this.datastore = datastore;
		this.mongoServer = datastore.getMongo();
		this.databaseName = datastore.getCollection(Collection.class).getName();

	}
	public CollectionMongoServerImpl(Mongo mongoServer, String databaseName, String username, String password) {
		this.mongoServer = mongoServer;
		this.databaseName = databaseName;
		this.username = username;
		this.password = password;
		createDatastoreWithCredentials();
	}

	public CollectionMongoServerImpl() {

	}

	private void createDatastore() {
		Morphia morphia = new Morphia();
		morphia.map(Collection.class);
		datastore = morphia.createDatastore(mongoServer, databaseName);
		datastore.ensureIndexes();
	}
	private void createDatastoreWithCredentials() {
		Morphia morphia = new Morphia();
		morphia.map(Collection.class);
		datastore = morphia.createDatastore(mongoServer, databaseName,username,password.toCharArray());
		datastore.ensureIndexes();
	}
	/**
	 * Return the datastore. Useful for exposing surplus functionality
	 */
	@Override
	public Datastore getDatastore() {
		return this.datastore;
	}

	/**
	 * Close the connection to the Mongo server
	 */
	@Override
	public void close() {
		mongoServer.close();
	}

	/* (non-Javadoc)
	 * @see eu.europeana.corelib.tools.lookuptable.impl.CollectionMongoServer#findNewCollectionId(java.lang.String)
	 */
	@Override
	public String findNewCollectionId(String oldCollectionId) {
		return datastore.find(Collection.class).field("oldCollectionId")
				.equal(oldCollectionId).get() != null ? datastore
				.find(Collection.class).field("oldCollectionId")
				.equal(oldCollectionId).get().getNewCollectionId() : null;
	}

	/* (non-Javadoc)
	 * @see eu.europeana.corelib.tools.lookuptable.impl.CollectionMongoServer#findOldCollectionId(java.lang.String)
	 */
	@Override
	public String findOldCollectionId(String newCollectionId) {
		return datastore.find(Collection.class).field("newCollectionId")
				.equal(newCollectionId).get() != null ? datastore
				.find(Collection.class).field("newCollectionId")
				.equal(newCollectionId).get().getOldCollectionId() : null;
	}

	/* (non-Javadoc)
	 * @see eu.europeana.corelib.tools.lookuptable.impl.CollectionMongoServer#saveCollection(eu.europeana.corelib.tools.lookuptable.Collection)
	 */
	@Override
	public void saveCollection(Collection collection) {
		datastore.save(collection);
	}

	/* (non-Javadoc)
	 * @see eu.europeana.corelib.tools.lookuptable.impl.CollectionMongoServer#retrieveAllCollections()
	 */
	@Override
	public List<Collection> retrieveAllCollections() {
		return datastore.find(Collection.class).asList();
	}

	/* (non-Javadoc)
	 * @see eu.europeana.corelib.tools.lookuptable.impl.CollectionMongoServer#setDatastore(com.google.code.morphia.Datastore)
	 */
	@Override
	public void setDatastore(Datastore datastore) {
		this.datastore = datastore;
	}
}
