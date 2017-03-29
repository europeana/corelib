package eu.europeana.corelib.tools.lookuptable;

import com.mongodb.MongoClient;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import eu.europeana.corelib.lookup.impl.EuropeanaIdMongoServerImpl;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EuropeanaIdMongoServerTest {
    EuropeanaIdMongoServerImpl server;

    @Test
    public void test() throws IOException {
        int port = 10000;
        IMongodConfig conf = new MongodConfigBuilder().version(Version.Main.PRODUCTION)
                .net(new Net(port, Network.localhostIsIPv6()))
                .build();

        MongodStarter runtime = MongodStarter.getDefaultInstance();

        MongodExecutable mongodExecutable = runtime.prepare(conf);
        mongodExecutable.start();
        server = new EuropeanaIdMongoServerImpl(new MongoClient("localhost", port), "europeanaId_test");
        server.createDatastore();
        EuropeanaId eid = new EuropeanaId();
        eid.setNewId("newId");
        eid.setOldId("oldId");
        eid.setLastAccess(new Date().getTime());

        server.saveEuropeanaId(eid);

        assertTrue(server.newIdExists("oldId"));
        assertTrue(server.oldIdExists("newId"));
        assertEquals(server.retrieveEuropeanaIdFromNew("newId").get(0).getOldId(), "oldId");
        assertEquals(server.retrieveEuropeanaIdFromOld("oldId").getNewId(), "newId");
        mongodExecutable.stop();

    }


}
