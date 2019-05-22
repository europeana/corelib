package eu.europeana.corelib.db.wrapper;

import com.mongodb.*;
import eu.europeana.corelib.storage.MongoProvider;
import eu.europeana.corelib.storage.impl.MongoProviderImpl;

import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.Datastore;
import org.apache.log4j.Logger;


/**
 * Let the search-api (and other api's?) connect to a mongo database with Morphia
 * This class uses a basic Morphia connection without any mappings or other specific settings
 */
public class ApiMongoConnector {

    private static final Logger LOG = Logger.getLogger(ApiMongoConnector.class);

    private MongoClient mongoClient;
    private String label;

    /**
     * Create a Morphia connection using a mongo connection url. Note that a database name is required in the url
     * @see <a href="http://api.mongodb.com/java/current/com/mongodb/MongoClientURI.html">
     *     MongoClientURI documentation</a>
     * @param connectionUrl, e.g. mongodb://user:password@mongo1.eanadev.org:27000/europeana_1?replicaSet=europeana
     * @return
     */
    public Datastore createDatastore(String connectionUrl) throws InterruptedException {
        MongoProvider mongo = new MongoProviderImpl(connectionUrl);
        this.label = mongo.getDefaultDatabase();
        this.mongoClient = mongo.getMongo();
        LOG.info(String.format("Creating Morphia datastore for databases '%s'", label));
        return new Morphia().createDatastore(mongoClient, label);
    }

    /**
     * Create a basic connection to do get/delete/save operations on the database
     * @param label    The label of the server to connect to (for logging purposes only)
     * @param host     The host to connect to
     * @param port     The port to connect to
     * @param dbName   The database to connect to
     * @param username Username for connection
     * @param password Password for connection
     * @return datastore
     */
    public Datastore createDatastore(String label, String host, int port, String dbName, String username,
                                     String password) {
        Datastore datastore = null;
        Morphia connection = new Morphia();
        try {
            this.label = label;
            this.mongoClient = new MongoProviderImpl(host, String.valueOf(port), dbName, username, password).getMongo();
            LOG.info(String.format("Creating Morphia datastore for database %s", dbName));
            datastore = connection.createDatastore(mongoClient, dbName);
        } catch (MongoException e) {
            LOG.error(e.getMessage(), e);
        }
        return datastore;
    }

    /**
     * Create a basic connection to do get/delete/save operations on the database
     * Any required login credentials should already be stored in the provided MongoClient object
     * @param label         The label of the server to connect to (for logging purposes only)
     * @param mongoClient
     * @param dbName
     * @return datastore
     */
    public Datastore createDatastore(String label, MongoClient mongoClient, String dbName) {
        Datastore datastore = null;
        Morphia connection = new Morphia();
        try {
            this.label = label;
            this.mongoClient = mongoClient;
            LOG.info(String.format("Creating Morphia "+label+" datastore for database %s", dbName));
            datastore = connection.createDatastore(mongoClient, dbName);
        } catch (MongoException e) {
            LOG.error(e.getMessage(), e);
        }
        return datastore;
    }

    /**
     * Close the connection to the database
     */
    public void close() {
        if (mongoClient != null) {
            LOG.info(String.format("Closing MongoClient for '%s'", label));
            mongoClient.close();
        }
    }
}
