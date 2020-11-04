package eu.europeana.corelib.db.wrapper;

import com.mongodb.client.MongoClient;
import com.mongodb.MongoException;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import eu.europeana.metis.mongo.MongoClientProvider;
import eu.europeana.metis.mongo.MongoProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Let the search-api (and other api's?) connect to a mongo database with Morphia This class uses a
 * basic Morphia connection without any mappings or other specific settings
 */
@Deprecated(since = "September 2020", forRemoval = true)
public class ApiMongoConnector {

    private static final Logger LOG = LogManager.getLogger(ApiMongoConnector.class);

    private MongoClient mongoClient;
    private String label;

    /**
     * Create a Morphia connection using a mongo connection url. Note that a database name is
     * required in the url
     *
     * @param connectionUrl, e.g. mongodb://user:password@mongo1.eanadev.org:27000/europeana_1?replicaSet=europeana
     * @return datastore
     * @see <a href="http://api.mongodb.com/java/current/com/mongodb/MongoClientURI.html">
     * MongoClientURI documentation</a>
     * @deprecated not called from anywhere
     * <p>
     * MongoClientURI documentation</a>
     */
    @Deprecated
    public Datastore createDatastore(String connectionUrl) {
        // TODO: 04/11/2020 The MaxConnection idle time can be now passed in the connection url with maxIdleTimeMS
        final MongoClientProvider<IllegalArgumentException> mongoClientProvider = MongoClientProvider
            .create(connectionUrl);
        this.label = mongoClientProvider.getAuthenticationDatabase();
        this.mongoClient = mongoClientProvider.createMongoClient();
        return Morphia.createDatastore(this.mongoClient, label);
    }

    /**
     * Create a basic connection to do get/delete/save operations on the database
     *
     * @param label    The label of the server to connect to (for logging purposes only)
     * @param host     The host to connect to
     * @param port     The port to connect to
     * @param dbName   The database to connect to
     * @param username Username for connection
     * @param password Password for connection
     * @return datastore
     * @deprecated this method is not called from anywhere
     */
    @Deprecated
    public Datastore createDatastore(String label, String host, int port, String dbName,
        String username,
        String password) {
        Datastore datastore = null;
        try {
            this.label = label;
            this.mongoClient = new MongoClientProvider<>(
                getMongoProperties(host, new int[]{port}, dbName, username,
                    password, false)).createMongoClient();
            datastore = Morphia.createDatastore(mongoClient, dbName);
        } catch (MongoException e) {
            LOG.error(e.getMessage(), e);
        }
        return datastore;
    }

    public MongoProperties<IllegalArgumentException> getMongoProperties(String mongoHosts,
        int[] mongoPorts, String mongoAuthenticationDb, String mongoUsername, String mongoPassword,
        boolean mongoEnableSSL) {
        final MongoProperties<IllegalArgumentException> mongoProperties = new MongoProperties<>(
            IllegalArgumentException::new);
        mongoProperties
            .setAllProperties(mongoHosts.trim().split(","), mongoPorts,
                mongoAuthenticationDb, mongoUsername,
                mongoPassword, mongoEnableSSL, null);
        return mongoProperties;
    }

    /**
     * Create a basic connection to do get/delete/save operations on the database Any required login
     * credentials should already be stored in the provided MongoClient object
     *
     * @param label       The label of the server to connect to (for logging purposes only)
     * @param mongoClient
     * @param dbName
     * @return datastore
     * @deprecated not used
     */
    @Deprecated
    public Datastore createDatastore(String label, MongoClient mongoClient, String dbName) {
        Datastore datastore = null;
        try {
            this.label = label;
            this.mongoClient = mongoClient;
            datastore = Morphia.createDatastore(mongoClient, dbName);
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
