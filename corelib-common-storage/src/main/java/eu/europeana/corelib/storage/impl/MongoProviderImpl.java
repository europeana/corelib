package eu.europeana.corelib.storage.impl;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import eu.europeana.corelib.storage.MongoProvider;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class to create a MongoClient
 */
public class MongoProviderImpl implements MongoProvider {

    private static final Logger LOG = Logger.getLogger(MongoProviderImpl.class);

    private MongoClient mongo;
    private String defaultDatabase;

    /**
     * Create a new MongoClient based on a connectionUrl, e.g.
     * mongodb://user:password@mongo1.eanadev.org:27000/europeana_1?replicaSet=europeana
     * @see <a href="http://api.mongodb.com/java/current/com/mongodb/MongoClientURI.html">
     *     MongoClientURI documentation</a>
     *
     * @param connectionUrl
     */
    public MongoProviderImpl(String connectionUrl) {
        MongoClientURI uri = new MongoClientURI(connectionUrl);
        defaultDatabase = uri.getDatabase();
        LOG.info("Creating new MongoClient - "+uri.getHosts() +
                (StringUtils.isEmpty(defaultDatabase) ? "" : ", database "+ defaultDatabase));
        mongo = new MongoClient(uri);
    }

    /**
     * Create a new MongoClient without any credentials
     * @param hosts comma-separated host names
     * @param ports omma-separated port numbers
     */
    public MongoProviderImpl(String hosts, String ports) {
        this(hosts, ports, null, null, null, null);
    }

    /**
     * Create a new MongoClient with the supplied credentials
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
     * Create a new MongoClient with the supplied credentials and optionsBuilder
     * If no optionsBuilder is provided a default one will be constructed.
     * @param hosts comma-separated host names
     * @param ports comma-separated port numbers
     * @param dbName optional
     * @param username optional
     * @param password optional
     * @param optionsBuilder optional
     */
    public MongoProviderImpl(String hosts, String ports, String dbName, String username, String password, MongoClientOptions.Builder optionsBuilder) {
        String[] hostList = StringUtils.split(hosts, ",");
        String[] portList = StringUtils.split(ports, ",");
        List<ServerAddress> serverAddresses = new ArrayList<>();
        int i = 0;
        for (String host : hostList) {
            if (host.length() > 0) {
                try {
                    ServerAddress address = new ServerAddress(host, Integer.parseInt(portList[i]));
                    serverAddresses.add(address);
                } catch (NumberFormatException e) {
                    LOG.error("Error parsing port numbers", e);
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
            LOG.info("Creating new MongoClient - "+hosts+" (no credentials)");
            defaultDatabase = null;
            mongo = new MongoClient(serverAddresses, builder.build());
        } else {
            List<MongoCredential> credentials = new ArrayList<>();
            credentials.add(MongoCredential.createCredential(username, dbName, password.toCharArray()));
            LOG.info("Creating new MongoClient - "+hosts+", database "+dbName+" (with credentials)");
            defaultDatabase = dbName;
            mongo = new MongoClient(serverAddresses, credentials, builder.build());
        }
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
        return defaultDatabase;
    }

    /**
     * @see MongoProvider#close()
     */
    @Override
    public void close() {
        if (mongo != null) {
            LOG.info("Closing MongoClient - "+mongo.getServerAddressList().get(0));
            mongo.close();
        }
    }

}
