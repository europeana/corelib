package eu.europeana.corelib.lookup.impl;

import java.util.List;
import java.util.logging.Logger;

import com.mongodb.MongoClient;
import eu.europeana.corelib.storage.impl.MongoProviderImpl;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import com.mongodb.Mongo;

import eu.europeana.corelib.storage.MongoServer;
import eu.europeana.corelib.tools.lookuptable.Collection;
import eu.europeana.corelib.tools.lookuptable.CollectionMongoServer;

/**
 * Class for creating and accessing the Collection Lookup table
 * 
 * @author yorgos.mamakis@ kb.nl
 * @author Patrick Ehlert
 * 
 */
public class CollectionMongoServerImpl implements MongoServer, CollectionMongoServer {

	private static final Logger log = Logger.getLogger(CollectionMongoServerImpl.class.getName());

	private MongoClient mongoClient;
	private String databaseName;
	private Datastore datastore;

	/**
	 * Constructor for the CollectionMongoServer to ensure that everything has been set upon initialization
	 * Any required login credentials should be present in the provided mongoClient
	 * 
	 * @param mongoClient the Mongo Server to connect to
	 * @param databaseName the database to connect to
	 */
	public CollectionMongoServerImpl(MongoClient mongoClient, String databaseName) {
		this.mongoClient = mongoClient;
		this.databaseName = databaseName;
		createDatastore();
	}

	/**
	 * Setup a new datastore to do get/delete/save operations on the database
	 * @param datastore
	 */
	public CollectionMongoServerImpl(Datastore datastore){
		this.mongoClient = datastore.getMongo();
		this.databaseName = datastore.getCollection(Collection.class).getName();
		this.datastore = datastore;
	}

	/**
	 * Create a new datastore to do get/delete/save operations on the database
	 * @param host
	 * @param port
	 * @param databaseName
	 * @param username
	 * @param password
	 */
	public CollectionMongoServerImpl(String host, int port, String databaseName, String username, String password) {
		this.mongoClient = new MongoProviderImpl(host, String.valueOf(port), databaseName, username, password).getMongo();
		this.databaseName = databaseName;
		createDatastore();
	}

	private void createDatastore() {
		Morphia morphia = new Morphia();
		morphia.map(Collection.class);
		datastore = morphia.createDatastore(mongoClient, databaseName);
		datastore.ensureIndexes();
		log.info("CollectionMongoServer datastore is created");
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
		if (mongoClient != null) {
			log.info("Closing MongoClient for CollectionMongoServer");
			mongoClient.close();
		}
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
	 * @see eu.europeana.corelib.tools.lookuptable.impl.CollectionMongoServer#setDatastore(org.mongodb.morphia.Datastore)
	 */
	@Override
	public void setDatastore(Datastore datastore) {
		this.datastore = datastore;
	}
}
