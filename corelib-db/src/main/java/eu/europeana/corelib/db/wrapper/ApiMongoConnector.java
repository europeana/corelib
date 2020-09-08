package eu.europeana.corelib.db.wrapper;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import eu.europeana.corelib.storage.MongoProvider;
import eu.europeana.corelib.storage.impl.MongoProviderImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Let the search-api (and other api's?) connect to a mongo database with Morphia
 * This class uses a basic Morphia connection without any mappings or other specific settings
 */
public class ApiMongoConnector {

    private static final Logger LOG = LogManager.getLogger(ApiMongoConnector.class);

    private MongoClient mongoClient;
    private String      label;

    /**
     * Create a Morphia connection using a mongo connection url. Note that a database name is required in the url
     * @deprecated  not called from anywhere
     *
     *     MongoClientURI documentation</a>
     * @param connectionUrl, e.g. mongodb://user:password@mongo1.eanadev.org:27000/europeana_1?replicaSet=europeana
     * @return datastore
     *
     *@see <a href="http://api.mongodb.com/java/current/com/mongodb/MongoClientURI.html">
     *     MongoClientURI documentation</a>
     */
    @Deprecated
    public Datastore createDatastore(String connectionUrl) throws InterruptedException {
        MongoProvider mongo = new MongoProviderImpl(connectionUrl, "0");
        this.label          = mongo.getDefaultDatabase();
        this.mongoClient    = mongo.getMongo();
        return new Morphia().createDatastore(mongoClient, label);
    }

    /**
     * Create a basic connection to do get/delete/save operations on the database
     *@deprecated      this method is not called from anywhere
     *
     * @param label    The label of the server to connect to (for logging purposes only)
     * @param host     The host to connect to
     * @param port     The port to connect to
     * @param dbName   The database to connect to
     * @param username Username for connection
     * @param password Password for connection
     * @return datastore
     */
    @Deprecated
    public Datastore createDatastore(String label, String host, int port, String dbName, String username,
                                     String password) {
        Datastore datastore = null;
        Morphia connection = new Morphia();
        try {
            this.label       = label;
            this.mongoClient = new MongoProviderImpl(host, String.valueOf(port), dbName, username, password).getMongo();
            datastore        = connection.createDatastore(mongoClient, dbName);
        } catch (MongoException e) {
            LOG.error(e.getMessage(), e);
        }
        return datastore;
    }

    /**
     * Create a basic connection to do get/delete/save operations on the database
     * Any required login credentials should already be stored in the provided MongoClient object
     *@deprecated          not used
     *
     * @param label         The label of the server to connect to (for logging purposes only)
     * @param mongoClient
     * @param dbName
     * @return datastore
     */
    @Deprecated
    public Datastore createDatastore(String label, MongoClient mongoClient, String dbName) {
        Datastore datastore  = null;
        Morphia  connection  = new Morphia();
        try {
            this.label       = label;
            this.mongoClient = mongoClient;
            datastore        = connection.createDatastore(mongoClient, dbName);
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
            mongoClient.close();
        }
    }
}
