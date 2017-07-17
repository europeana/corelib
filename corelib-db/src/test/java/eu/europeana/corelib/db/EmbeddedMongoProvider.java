package eu.europeana.corelib.db;

import com.mongodb.MongoClient;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import eu.europeana.corelib.edm.exceptions.MongoDBException;
import eu.europeana.corelib.mongo.server.EdmMongoServer;
import eu.europeana.corelib.mongo.server.impl.EdmMongoServerImpl;
import eu.europeana.corelib.storage.MongoProvider;
import org.apache.log4j.Logger;

import java.io.IOException;

public class EmbeddedMongoProvider implements MongoProvider {

    private static final Logger LOG = Logger.getLogger(EmbeddedMongoProvider.class);

    private static final String DB_NAME = "europeana_test";

    private MongoClient mongo;

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
            mongo = new MongoClient("localhost", port);
            EdmMongoServer mongoDBServer = new EdmMongoServerImpl(mongo, "europeana_test");
            mongoDBServer.getDatastore().getDB().dropDatabase();
        } catch (IOException | MongoDBException e) {
            e.printStackTrace();
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
    @Override
    public String getDefaultDatabase() { return DB_NAME;}

    /**
     * @see MongoProvider#close()
     */
    @Override
    public void close() {
        if (mongo != null) {
            LOG.info("Closing test MongoClient for EmbeddedMongoProvider");
            mongo.close();
        }
    }
}
