package eu.europeana.corelib.mocks;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import eu.europeana.corelib.storage.MongoProvider;
import eu.europeana.metis.mongo.EdmMongoServer;
import java.io.IOException;
import org.apache.log4j.Logger;

public class EmbeddedMongoProvider implements MongoProvider {


    private static final Logger LOG = Logger.getLogger(EmbeddedMongoProvider.class);

    private static final String DB_NAME = "europeana_test";

    private MongoClient mongoClient;

    public EmbeddedMongoProvider() {
        int port = 10000;
        try {
            IMongodConfig conf = new MongodConfigBuilder()
                    .version(Version.Main.PRODUCTION)
                    .net(new Net(port, Network.localhostIsIPv6())).build();
            MongodStarter starter = MongodStarter.getDefaultInstance();
            MongodExecutable mongodExecutable = starter.prepare(conf);
            mongodExecutable.start();
            LOG.info("Creating new test MongoClient for EmbeddedMongoProvider");
            mongoClient = MongoClients.create(String.format("mongodb://%s:%s", "localhost", port));
            EdmMongoServer mongoDBServer = new EdmMongoServer(mongoClient, DB_NAME, false);
            mongoDBServer.getDatastore().getDatabase().drop();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    @Override
    public String getDefaultDatabase() { return DB_NAME;}

    /**
     * @see MongoProvider#close()
     */
    @Override
    public void close() {
        if (mongoClient != null) {
            LOG.info("Closing test MongoClient for EmbeddedMongoProvider");
            mongoClient.close();
        }
    }
}
