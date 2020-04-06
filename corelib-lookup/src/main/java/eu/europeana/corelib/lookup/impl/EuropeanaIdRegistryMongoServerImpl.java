package eu.europeana.corelib.lookup.impl;

import com.mongodb.MongoClient;
import eu.europeana.corelib.storage.MongoServer;
import eu.europeana.corelib.storage.impl.MongoProviderImpl;
import eu.europeana.corelib.tools.lookuptable.EuropeanaIdMongoServer;
import eu.europeana.corelib.tools.lookuptable.EuropeanaIdRegistry;
import eu.europeana.corelib.tools.lookuptable.EuropeanaIdRegistryMongoServer;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Class for setting and accessing the EuropeanaIdRegistry Lookup Table
 *
 * @author yorgos.mamakis@ kb.nl
 *
 */
@Deprecated // to be replaced soon with new redirection functionality
public class EuropeanaIdRegistryMongoServerImpl implements MongoServer, EuropeanaIdRegistryMongoServer {

	private static final Logger LOG = Logger.getLogger(EuropeanaIdRegistryMongoServerImpl.class.getName());

	private MongoClient mongoClient;
	private String databaseName;
	private Datastore datastore;

	private static final String EID = "eid";
	private static final String ORID = "orid";
	private static final String DATE = "last_checked";
	private static final String SESSION = "sessionID";
	private static final String CID = "cid";
	private static final String XMLCHECKSUM = "xmlchecksum";
	private static final String ISDELETED = "deleted";
	private EuropeanaIdMongoServer europeanaIdMongoServer;

	/**
	 * Constructor of the EuropeanaIDRegistryMongoServer
	 * Any required login credentials should be present in the provided mongoClient
	 *
	 * @param mongoClient the server to connect to
	 * @param databaseName the database to connect to
	 */
	@Deprecated
	public EuropeanaIdRegistryMongoServerImpl(MongoClient mongoClient, String databaseName) {
        this(mongoClient, databaseName, false);
	}

    /**
     * Constructor of the EuropeanaIDRegistryMongoServer
     * Any required login credentials should be present in the provided mongoClient
     *
     * @param mongoClient the server to connect to
     * @param databaseName the database to connect to
     * @param createIndexes if true it will try to create all indexes if necessary when creating the datastore
     */
    public EuropeanaIdRegistryMongoServerImpl(MongoClient mongoClient, String databaseName, boolean createIndexes) {
        this.mongoClient = mongoClient;
        this.databaseName = databaseName;
        europeanaIdMongoServer = new EuropeanaIdMongoServerImpl(mongoClient, databaseName);
        createDatastore(createIndexes);
    }

	/**
	 * Create a new datastore to do get/delete/save operations on the database
	 * @param host
	 * @param port
	 * @param databaseName
	 * @param username
	 * @param password
	 */
	@Deprecated
	public EuropeanaIdRegistryMongoServerImpl(String host, int port, String databaseName, String username, String password) {
        this(host, port, databaseName, username, password, false);
	}

    /**
     * Create a new datastore to do get/delete/save operations on the database
     * @param host
     * @param port
     * @param databaseName
     * @param username
     * @param password
     * @param createIndexes if true it will try to create all indexes if necessary when creating the datastore
     */
    public EuropeanaIdRegistryMongoServerImpl(String host, int port, String databaseName, String username, String password,
                                              boolean createIndexes) {
        this.mongoClient = new MongoProviderImpl(host, String.valueOf(port), databaseName, username, password).getMongo();
        this.databaseName = databaseName;
        europeanaIdMongoServer = new EuropeanaIdMongoServerImpl(mongoClient, databaseName);
        createDatastore(createIndexes);
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.europeana.corelib.tools.lookuptable.impl.
	 * EuropeanaIdRegistryMongoServer#getEuropeanaIdMongoServer()
	 */

	@Override
	public EuropeanaIdMongoServer getEuropeanaIdMongoServer() {
		return this.europeanaIdMongoServer;
	}

	private void createDatastore(boolean createIndexes) {
		Morphia morphia = new Morphia();

		morphia.map(EuropeanaIdRegistry.class);
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
		LOG.info("Closing MongoClient for EuropeanaIdRegistry");
		mongoClient.close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.europeana.corelib.tools.lookuptable.impl.
	 * EuropeanaIdRegistryMongoServer#retrieveEuropeanaIdFromOriginal(java.lang.
	 * String, java.lang.String)
	 */
	@Override
	public List<EuropeanaIdRegistry> retrieveEuropeanaIdFromOriginal(String originalId, String collectionid) {
		List<EuropeanaIdRegistry> retlist = new ArrayList<>();

		List<EuropeanaIdRegistry> list = datastore.find(EuropeanaIdRegistry.class).field(ORID).equal(originalId)
				.asList();

		for (EuropeanaIdRegistry entry : list) {
			if (!entry.isDeleted()) {
				retlist.add(entry);
			}
		}

		return retlist;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.europeana.corelib.tools.lookuptable.impl.
	 * EuropeanaIdRegistryMongoServer#retrieveEuropeanaIdFromNew(java.lang.
	 * String)
	 */
	@Override
	public EuropeanaIdRegistry retrieveEuropeanaIdFromNew(String newId) {

		List<EuropeanaIdRegistry> retrList = datastore.find(EuropeanaIdRegistry.class).field(EID).equal(newId).asList();

		if (retrList.isEmpty()) {
			return null;
		} else {
			for (EuropeanaIdRegistry entry : retrList) {
				// if (!entry.isDeleted()) {
				return entry;
				// }
			}
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.europeana.corelib.tools.lookuptable.impl.
	 * EuropeanaIdRegistryMongoServer#oldIdExists(java.lang.String)
	 */
	@Override
	public boolean oldIdExists(String newId) {
		return datastore.find(EuropeanaIdRegistry.class).field(EID).equal(newId).get() != null ? true : false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.europeana.corelib.tools.lookuptable.impl.
	 * EuropeanaIdRegistryMongoServer#newIdExists(java.lang.String)
	 */
	@Override
	public boolean newIdExists(String oldId) {
		return datastore.find(EuropeanaIdRegistry.class).field(ORID).equal(oldId).get() != null ? true : false;
	}

	void setDatastore(Datastore datastore) {
		this.datastore = datastore;
	}
}
