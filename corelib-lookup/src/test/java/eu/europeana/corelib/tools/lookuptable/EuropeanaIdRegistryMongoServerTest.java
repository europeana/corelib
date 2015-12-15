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
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class EuropeanaIdRegistryMongoServerTest {

    EuropeanaIdRegistryMongoServerImpl server;
    EuropeanaIdRegistry registry;
    String cid = "12345";
    String oid = "test_oid";
    String eid = "/12345/test_oid";
    String sid = "test_sid";
    String sid2 = "test_sid2";
    String xml_checksum = "tst_checksum";
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
        Assert.assertEquals(server.retrieveFromOriginalXML(oid, generatechecksum(xml_checksum)), null);
        LookupResult lresult = server.lookupUiniqueId(oid, cid, xml_checksum, sid);
        Assert.assertEquals(LookupState.ID_REGISTERED, lresult.getState());
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
        Assert.assertEquals(server.retrieveFromOriginalXML(oid, generatechecksum(xml_checksum)).getEid(), registry.getEid());
        Assert.assertEquals(LookupState.IDENTICAL, server.lookupUiniqueId(oid, cid, xml_checksum, sid2).getState());

        server.getDatastore().getDB().dropDatabase();
    }

    @Test
    public void testDuplicateInCollection() {
        Assert.assertNotNull("Server should be initialized", server);

        saveRecord(sid);

        Assert.assertTrue(server.newIdExists(oid));
        Assert.assertEquals(server.retrieveEuropeanaIdFromOriginal(oid, cid).get(0).getEid(), registry.getEid());
        Assert.assertTrue(server.oldIdExists(eid));
        Assert.assertEquals(server.retrieveEuropeanaIdFromNew(eid).getOrid(), registry.getOrid());
        Assert.assertEquals(
                server.retrieveFromOriginalXML(oid, generatechecksum(xml_checksum)).getEid(),
                registry.getEid());
        Assert.assertEquals(
                LookupState.DUPLICATE_INCOLLECTION,
                server.lookupUiniqueId(oid, cid, xml_checksum, sid).getState());

        server.getDatastore().getDB().dropDatabase();
    }

    @Test
    public void testUpdate() {
        Assert.assertNotNull("Server should be initialized", server);

        saveRecord(sid);
        Assert.assertEquals(server.lookupUiniqueId(oid, "12345", xml_checksum + "new", sid2).getState(), LookupState.UPDATE);
        server.getDatastore().getDB().dropDatabase();
    }

    @Test
    public void testFailedrecords() {
        Assert.assertNotNull("Server should be initialized", server);

        saveRecord(sid);
        Assert.assertTrue(server.newIdExists(oid));
        Assert.assertEquals(server.retrieveEuropeanaIdFromOriginal(oid, cid).get(0).getEid(), registry.getEid());
        Assert.assertTrue(server.oldIdExists(eid));
        Assert.assertEquals(server.retrieveEuropeanaIdFromNew(eid).getOrid(), registry.getOrid());
        Assert.assertEquals(server.retrieveFromOriginalXML(oid, generatechecksum(xml_checksum)).getEid(), registry.getEid());
        Assert.assertEquals(LookupState.DUPLICATE_INCOLLECTION, server.lookupUiniqueId(oid, cid, xml_checksum, sid).getState());
        List<Map<String, String>> frecords = server.getFailedRecords(cid);
        Assert.assertEquals(1, frecords.size());
        Map<String, String> valuemap = frecords.get(0);
        Assert.assertEquals(valuemap.get("edm"), "tst_checksum");
        Assert.assertEquals(valuemap.get("collectionId"), "12345");
        Assert.assertEquals(valuemap.get("originalId"), "test_oid");
        Assert.assertEquals(valuemap.get("lookupState"), "DUPLICATE_INCOLLECTION");
        Assert.assertEquals(valuemap.get("europeanaId"), "/12345/test_oid");

        server.getDatastore().getDB().dropDatabase();
    }

    /**
     * Generates the checksum for the given string
     *
     * @param xml
     * @return
     */
    @SuppressWarnings("deprecation")
    private String generatechecksum(String xml) {
        return DigestUtils.shaHex(xml);
    }

    private void saveRecord(String sessionID) {
        registry = new EuropeanaIdRegistry();
        registry.setCid(cid);
        registry.setEid(eid);
        registry.setOrid(oid);
        registry.setSessionID(sessionID);
        registry.setXmlchecksum(generatechecksum(xml_checksum));
        if (server != null) {
            server.getDatastore().save(registry);
        }
    }

    @After
    public void cleanup() {
        mongodExecutable.stop();
    }
}
