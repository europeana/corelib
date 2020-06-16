package eu.europeana.corelib.storage.impl;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import eu.europeana.corelib.storage.MongoProvider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Helper class to create a MongoClient
 */
public class MongoProviderImpl implements MongoProvider {

    private static final Logger LOG                   = LogManager.getLogger(MongoProviderImpl.class);

    private static final int MAX_CONNECTION_IDLE_MILLIS   = 30000;

    private MongoClient mongo;
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
        MongoClientOptions.Builder clientOptionsBuilder = new MongoClientOptions.Builder();
//        clientOptionsBuilder.maxConnectionIdleTime(MAX_CONNECTION_IDLE_MILLIS);
        MongoClientURI uri = new MongoClientURI(connectionUrl, clientOptionsBuilder);
        definedDatabase = uri.getDatabase();
        LOG.info("[MongoProvider] [constructor] creating new MongoClient for {}, {}",
                uri.getHosts(),
                (StringUtils.isEmpty(definedDatabase) ? "default database" : "database: " + definedDatabase));
        mongo = new MongoClient(uri);
    }

    /**
     * Create a new MongoClient without any credentials
     * @deprecated This constructor is not used anywhere
     *
     * @param hosts comma-separated host names
     * @param ports omma-separated port numbers
     */
    @Deprecated ()
    public MongoProviderImpl(String hosts, String ports) {
        this(hosts, ports, null, null, null, null);
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
     * @param optionsBuilder optional
     */
    public MongoProviderImpl(String[] hosts, String[] ports, String dbName, String username, String password, MongoClientOptions.Builder optionsBuilder) {
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

        MongoClientOptions.Builder builder = optionsBuilder;
        if (optionsBuilder == null) {
            // use defaults
            builder = MongoClientOptions.builder();
        }

        if (StringUtils.isEmpty(dbName) || StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            LOG.info("[MongoProvider] [params constructor] creating new MongoClient: {} {}",
                    Arrays.toString(hosts),
                    " (no credentials)");
            definedDatabase = null;
            mongo           = new MongoClient(serverAddresses, builder.build());
        } else {
            List<MongoCredential> credentials = new ArrayList<>();
            credentials.add(MongoCredential.createCredential(username, dbName, password.toCharArray()));
            LOG.info("Creating new MongoClient - "+ Arrays.toString(hosts) +", database "+dbName+" (with credentials)");
            definedDatabase = dbName;
            mongo = new MongoClient(serverAddresses, credentials, builder.build());
        }
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
     * @param optionsBuilder optional
     */
    public MongoProviderImpl(String hosts, String ports, String dbName, String username, String password, MongoClientOptions.Builder optionsBuilder) {
        this(StringUtils.split(hosts, ","),
                StringUtils.split(ports, ","),
                dbName,
                username,
                password,
                optionsBuilder);
    }

    /**
     * @see MongoProvider#getMongo()
     */
    @Override
    public MongoClient getMongo() {
        return mongo;
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
        if (mongo != null) {
            LOG.info("[MongoProvider] ... closing MongoClient ... {}", mongo.getServerAddressList().get(0));
            mongo.close();
        }
    }
}
