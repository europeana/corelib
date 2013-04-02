package eu.europeana.corelib.tools.lookuptable;

import java.net.UnknownHostException;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class EuropeanaIdRegistryMongoServerTest {

	EuropeanaIdRegistryMongoServer server;
	EuropeanaIdRegistry registry;
	String cid="12345";
	String oid="test_oid";
	String eid="/12345/test_oid";
	String sid="test_sid";
	String xml_checksum="tst_checksum";

	@Before
	public void prepare(){
		try{
			server = new EuropeanaIdRegistryMongoServer(new Mongo("localhost",27017), "europeana_registry_test");
			server.getEuropeanaIdMongoServer().createDatastore();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MongoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test 
	public void test(){
		
			saverecord();
			
			Assert.assertTrue(server.newIdExists(oid));
			Assert.assertEquals(server.retrieveEuropeanaIdFromOriginal(oid, cid).get(0).getEid(),registry.getEid());
			Assert.assertTrue(server.oldIdExists(eid));
			Assert.assertEquals(server.retrieveEuropeanaIdFromNew(eid).getOrid(),registry.getOrid());
			Assert.assertEquals(server.retrieveFromOriginalXML(oid, generatechecksum(xml_checksum)).getEid(), registry.getEid());
			Assert.assertEquals(server.lookupUiniqueId(oid, cid, xml_checksum, sid).getState(),LookupState.IDENTICAL);
			
			server.getDatastore().getDB().dropDatabase();
		
	}
	
	@Test
	public void testCollectionChanged(){
		saverecord();
		Assert.assertEquals(server.lookupUiniqueId(oid, "12346", xml_checksum, sid).getState(),LookupState.COLLECTION_CHANGED);
		server.getDatastore().getDB().dropDatabase();
	}
	
	@Test
	public void testDuplicateIdAcross(){
		saverecord();
		Assert.assertEquals(server.lookupUiniqueId(oid, "12345a", xml_checksum+"new", sid).getState(),LookupState.DUPLICATE_IDENTIFIER_ACROSS_COLLECTIONS);
		server.getDatastore().getDB().dropDatabase();
	}
	
	@Test
	public void testDuplicateRecAcross(){
		saverecord();
		Assert.assertEquals(server.lookupUiniqueId(oid, "12345a", xml_checksum, sid).getState(),LookupState.DUPLICATE_RECORD_ACROSS_COLLECTIONS);
		server.getDatastore().getDB().dropDatabase();
	}
	
	@Test
	public void testUpdate(){
		saverecord();
		Assert.assertEquals(server.lookupUiniqueId(oid, "12345", xml_checksum+"new", sid).getState(),LookupState.UPDATE);
		server.getDatastore().getDB().dropDatabase();
	}
	
	@Ignore
	@Test
	public void testFailedrecords(){
		saverecord();
		Assert.assertEquals(server.lookupUiniqueId(oid, cid, xml_checksum, sid).getState(),LookupState.IDENTICAL);
		Assert.assertEquals(1,  server.getFailedRecords(cid).size());
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

	
	private void saverecord(){
		
		
		
		registry = new EuropeanaIdRegistry();
		registry.setCid(cid);
		registry.setEid(eid);
		registry.setOrid(oid);
		registry.setSessionID(sid);
		registry.setXmlchecksum(generatechecksum(xml_checksum));
		server.getDatastore().save(registry);
	}
	
	@After
	public void cleanup(){
		server.getDatastore().getDB().dropDatabase();
	}
}
