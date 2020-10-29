package eu.europeana.corelib.db;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import eu.europeana.metis.mongo.EdmMongoServer;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EmbeddedMongoProvider {

    private static final Logger LOG = LogManager.getLogger(EmbeddedMongoProvider.class);

    private static final String DB_NAME = "europeana_test";

    private MongoClient mongo;

    public EmbeddedMongoProvider() {
        int port = 10000;
        try {
            LOG.info("Starting embedded mongo...");
            IMongodConfig conf = new MongodConfigBuilder()
                    .version(Version.Main.PRODUCTION)
                    .net(new Net(port, Network.localhostIsIPv6())).build();
            MongodStarter starter = MongodStarter.getDefaultInstance();
            MongodExecutable mongodExecutable = starter.prepare(conf);
            mongodExecutable.start();
            LOG.info("Creating new test MongoClient for EmbeddedMongoProvider");
            mongo = MongoClients.create(String.format("mongodb://%s:%s", "localhost", port));

            EdmMongoServer mongoDBServer = new EdmMongoServer(mongo, "europeana_test", true);
            mongoDBServer.getDatastore().getDatabase().drop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MongoClient getMongoClient() {
        return mongo;
    }

    public String getDefaultDatabase() { return DB_NAME;}

    public void close() {
        if (mongo != null) {
            LOG.info("Closing test MongoClient for EmbeddedMongoProvider");
            mongo.close();
        }
    }
}
