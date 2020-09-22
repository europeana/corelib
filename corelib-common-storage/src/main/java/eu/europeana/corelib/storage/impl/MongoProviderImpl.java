package eu.europeana.corelib.storage.impl;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import eu.europeana.corelib.storage.MongoProvider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Helper class to create a MongoClient
 */
public class MongoProviderImpl implements MongoProvider {

    private static final Logger LOG                   = LogManager.getLogger(MongoProviderImpl.class);

    private MongoClient mongoClient;

    private String definedDatabase;

    /**
     * Create a new MongoClient based on a connectionUrl, e.g.
     * mongodb://user:password@mongo1.eanadev.org:27000/europeana_1?replicaSet=europeana
     * @see <a href="http://api.mongodb.com/java/current/com/mongodb/MongoClientURI.html">
     *     MongoClientURI documentation</a>
     *
     * @param connectionUrl
     */
    public MongoProviderImpl(String connectionUrl) {
        final MongoClientSettings.Builder mongoClientSettingsBuilder = MongoClientSettings.builder();
        final ConnectionString connectionString = new ConnectionString(connectionUrl);
        definedDatabase = connectionString.getDatabase();
        LOG.info("[MongoProvider] [constructor] creating new MongoClient for {}, {}",
            connectionString.getHosts(),
                (StringUtils.isEmpty(definedDatabase) ? "default database" : "database: " + definedDatabase));

        mongoClient = MongoClients
            .create(mongoClientSettingsBuilder.applyConnectionString(connectionString).build());
    }

    /**
     * Create a new MongoClient based on a connectionUrl with MongoClientOptions e.g.
     * mongodb://user:password@mongo1.eanadev.org:27000/europeana_1?replicaSet=europeana
     * @see <a href="http://api.mongodb.com/java/current/com/mongodb/MongoClientURI.html">
     *     MongoClientURI documentation</a>
     *
     * @param connectionUrl
     * @param maxConnectionIdleTime sets the maxConnectionIdleTime if not empty
     */
    public MongoProviderImpl(String connectionUrl, String maxConnectionIdleTime) {
        final MongoClientSettings.Builder mongoClientSettingsBuilder = MongoClientSettings.builder();

        if(StringUtils.isNotEmpty(maxConnectionIdleTime) && Integer.parseInt(maxConnectionIdleTime) > 0) {
            mongoClientSettingsBuilder.applyToConnectionPoolSettings(
            builder -> builder.maxConnectionIdleTime(Integer.parseInt(maxConnectionIdleTime), TimeUnit.MILLISECONDS));
        }

        final ConnectionString connectionString = new ConnectionString(connectionUrl);
        definedDatabase = connectionString.getDatabase();
        LOG.info("[MongoProvider] [constructor] creating new MongoClient for {}, {}, maxConnectionIdleTime {} ms",
                connectionString.getHosts(),
                (StringUtils.isEmpty(definedDatabase) ? "default database" : "database: " + definedDatabase),
                connectionString.getMaxConnectionIdleTime());

        mongoClient = MongoClients
                .create(mongoClientSettingsBuilder.applyConnectionString(connectionString).build());
    }

    /**
     * Create a new MongoClient with the supplied credentials
     * Used only in corelib.lookup EuropeanaIdRegistryMongoServerImpl; two other usages (in corelib-db ApiMongoConnector
     * and in corelib.lookup CollectionMongoServerImpl) are unused themselves
     *
     * @param hosts comma-separated host names
     * @param ports comma-separated port numbers
     * @param dbName optional
     * @param username optional
     * @param password optional
     */
    public MongoProviderImpl(String hosts, String ports, String dbName, String username, String password) {
        this(hosts, ports, dbName, username, password, null);
    }

    /**
     * Create a new MongoClient with the supplied credentials and optionsBuilder.
     * If no optionsBuilder is provided a default one will be constructed.
     * Hosts array should contain one or more host names. If number of the hosts is greater
     * than 1 then the ports array should contain an entry for each host or just one port
     * which will be used for all the hosts.
     *
     * @param hosts array of host names
     * @param ports array of ports
     * @param dbName optional
     * @param username optional
     * @param password optional
     * @param mongoClientSettingsBuilder optional
     */
    public MongoProviderImpl(String[] hosts, String[] ports, String dbName, String username, String password, MongoClientSettings.Builder mongoClientSettingsBuilder) {
        List<ServerAddress> serverAddresses = new ArrayList<>();
        int i = 0;
        for (String host : hosts) {
            if (host.length() > 0) {
                try {
                    ServerAddress address = new ServerAddress(host, getPort(ports, i));
                    serverAddresses.add(address);
                } catch (NumberFormatException e) {
                    LOG.error("[MongoProvider] [params constructor] error parsing port numbers", e);
                }
            }
            i++;
        }

        MongoClientSettings.Builder clientSettingsBuilder = mongoClientSettingsBuilder;
        if (mongoClientSettingsBuilder == null) {
            // use defaults
            clientSettingsBuilder = MongoClientSettings.builder();
        }
        clientSettingsBuilder.applyToClusterSettings(builder -> builder.hosts(serverAddresses));

        if (StringUtils.isEmpty(dbName) || StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            LOG.info("[MongoProvider] [params constructor] creating new MongoClient: {} {}",
                    Arrays.toString(hosts),
                    " (no credentials)");
            definedDatabase = null;
        } else {
            final MongoCredential credential = MongoCredential
                .createCredential(username, dbName, password.toCharArray());
            LOG.info("Creating new MongoClient - "+ Arrays.toString(hosts) +", database "+dbName+" (with credentials)");
            definedDatabase = dbName;
            mongoClientSettingsBuilder.credential(credential);
        }
        mongoClient = MongoClients.create(clientSettingsBuilder.build());
    }

    private int getPort(String[] ports, int index) {
        if (ports == null || index < 0) {
            throw new NumberFormatException("Empty port");
        }
        if (ports.length > 1 && index < ports.length) {
            return Integer.parseInt(ports[index]);
        }
        return Integer.parseInt(ports[0]);
    }

    /**
     * Create a new MongoClient with the supplied credentials and optionsBuilder
     * If no optionsBuilder is provided a default one will be constructed.
     *
     * Usages:  corelib-storage:    EdmMongoServerImpl, but that method is not used
     * internally:         #49 (deprecated, nog used)
     * #65 => corelib.lookup EuropeanaIdRegistryMongoServerImpl
     *
     * @param hosts comma-separated host names
     * @param ports comma-separated port numbers
     * @param dbName optional
     * @param username optional
     * @param password optional
     * @param mongoClientsSettingsBuilder optional
     */
    public MongoProviderImpl(String hosts, String ports, String dbName, String username, String password, MongoClientSettings.Builder mongoClientsSettingsBuilder) {
        this(StringUtils.split(hosts, ","),
                StringUtils.split(ports, ","),
                dbName,
                username,
                password, mongoClientsSettingsBuilder);
    }

    /**
     * @see MongoProvider#getMongoClient()
     */
    @Override
    public MongoClient getMongoClient() {
        return mongoClient;
    }

    /**
     * @see MongoProvider#getDefaultDatabase()
     */
    public String getDefaultDatabase() {
        return definedDatabase;
    }

    /**
     * @see MongoProvider#close()
     */
    @Override
    public void close() {
        if (mongoClient != null) {
            LOG.info("[MongoProvider] ... closing MongoClient ... {}", mongoClient.getClusterDescription().getClusterSettings().getHosts().get(0));
            mongoClient.close();
        }
    }
}
