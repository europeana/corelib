package eu.europeana.corelib.tools.lookuptable;

import com.mongodb.MongoClient;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import eu.europeana.corelib.lookup.impl.EuropeanaIdRegistryMongoServerImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

@Deprecated // to be replaced soon with new redirection functionality
public class EuropeanaIdRegistryMongoServerTest {

    EuropeanaIdRegistryMongoServerImpl server;
    EuropeanaIdRegistry registry;
    String cid = "12345";
    String oid = "test_oid";
    String eid = "/12345/test_oid";
    String sid = "test_sid";
    MongodExecutable mongodExecutable;

    @Before
    public void prepare() throws IOException {
        int port = 10000;
        IMongodConfig conf = new MongodConfigBuilder().version(Version.Main.PRODUCTION)
                .net(new Net(port, Network.localhostIsIPv6()))
                .build();

        MongodStarter runtime = MongodStarter.getDefaultInstance();

        mongodExecutable = runtime.prepare(conf);
        mongodExecutable.start();
        server = new EuropeanaIdRegistryMongoServerImpl(
                new MongoClient("localhost", port),
                "europeana_registry_test"
        );
        server.getEuropeanaIdMongoServer().createDatastore();
    }

    @Test
    public void testRegistered() {
        Assert.assertNotNull("Server should be initialized", server);

        Assert.assertTrue(!server.newIdExists(oid));
        Assert.assertEquals(server.retrieveEuropeanaIdFromOriginal(oid, cid).size(), 0);
        Assert.assertTrue(!server.oldIdExists(eid));
        Assert.assertEquals(server.retrieveEuropeanaIdFromNew(eid), null);

        server.getDatastore().getDB().dropDatabase();
    }

    @Test
    public void testIdentical() {
        Assert.assertNotNull("Server should be initialized", server);

        saveRecord(sid);
        Assert.assertTrue(server.newIdExists(oid));
        Assert.assertEquals(server.retrieveEuropeanaIdFromOriginal(oid, cid).get(0).getEid(), registry.getEid());
        Assert.assertTrue(server.oldIdExists(eid));
        Assert.assertEquals(server.retrieveEuropeanaIdFromNew(eid).getOrid(), registry.getOrid());

        server.getDatastore().getDB().dropDatabase();
    }

    private void saveRecord(String sessionID) {
        registry = new EuropeanaIdRegistry();
        registry.setCid(cid);
        registry.setEid(eid);
        registry.setOrid(oid);
        registry.setSessionID(sessionID);
        if (server != null) {
            server.getDatastore().save(registry);
        }
    }

    @After
    public void cleanup() {
        mongodExecutable.stop();
    }
}
