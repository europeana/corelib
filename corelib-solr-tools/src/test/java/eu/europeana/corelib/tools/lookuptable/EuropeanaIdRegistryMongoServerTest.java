package eu.europeana.corelib.tools.lookuptable;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

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
	String sid2="test_sid2";
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
	public void testRegistered(){
			Assert.assertTrue(!server.newIdExists(oid));
			Assert.assertEquals(server.retrieveEuropeanaIdFromOriginal(oid, cid).size(),0);
			Assert.assertTrue(!server.oldIdExists(eid));
			Assert.assertEquals(server.retrieveEuropeanaIdFromNew(eid),null);
			Assert.assertEquals(server.retrieveFromOriginalXML(oid, generatechecksum(xml_checksum)), null);
			LookupResult lresult = server.lookupUiniqueId(oid, cid, xml_checksum, sid);			
			Assert.assertEquals(LookupState.ID_REGISTERED,lresult.getState());
			
			server.getDatastore().getDB().dropDatabase();
	}
	
	
	@Test 
	public void testIdentical(){
		
			saverecord(sid);		
			Assert.assertTrue(server.newIdExists(oid));
			Assert.assertEquals(server.retrieveEuropeanaIdFromOriginal(oid, cid).get(0).getEid(),registry.getEid());
			Assert.assertTrue(server.oldIdExists(eid));
			Assert.assertEquals(server.retrieveEuropeanaIdFromNew(eid).getOrid(),registry.getOrid());
			Assert.assertEquals(server.retrieveFromOriginalXML(oid, generatechecksum(xml_checksum)).getEid(), registry.getEid());
			Assert.assertEquals(LookupState.IDENTICAL,server.lookupUiniqueId(oid, cid, xml_checksum, sid2).getState());
			
			server.getDatastore().getDB().dropDatabase();
	}
	
	@Test 
	public void testDuplicateInCollection(){
		
			saverecord(sid);
			
			Assert.assertTrue(server.newIdExists(oid));
			Assert.assertEquals(server.retrieveEuropeanaIdFromOriginal(oid, cid).get(0).getEid(),registry.getEid());
			Assert.assertTrue(server.oldIdExists(eid));
			Assert.assertEquals(server.retrieveEuropeanaIdFromNew(eid).getOrid(),registry.getOrid());
			Assert.assertEquals(server.retrieveFromOriginalXML(oid, generatechecksum(xml_checksum)).getEid(), registry.getEid());
			Assert.assertEquals(LookupState.DUPLICATE_INCOLLECTION,server.lookupUiniqueId(oid, cid, xml_checksum, sid).getState());
			
			server.getDatastore().getDB().dropDatabase();
	}
	
	
	
	@Test
	public void testCollectionChanged(){
		saverecord(sid);
		Assert.assertEquals(server.lookupUiniqueId(oid, "12346", xml_checksum, sid).getState(),LookupState.COLLECTION_CHANGED);
		server.getDatastore().getDB().dropDatabase();
	}
	
	@Test
	public void testDuplicateIdAcross(){
		saverecord(sid);
		Assert.assertEquals(server.lookupUiniqueId(oid, "12345a", xml_checksum+"new", sid).getState(),LookupState.DUPLICATE_IDENTIFIER_ACROSS_COLLECTIONS);
		server.getDatastore().getDB().dropDatabase();
	}
	
	@Test
	public void testDuplicateRecAcross(){
		saverecord(sid);
		Assert.assertEquals(server.lookupUiniqueId(oid, "12345a", xml_checksum, sid).getState(),LookupState.DUPLICATE_RECORD_ACROSS_COLLECTIONS);
		server.getDatastore().getDB().dropDatabase();
	}
	
	@Test
	public void testUpdate(){
		saverecord(sid);
		Assert.assertEquals(server.lookupUiniqueId(oid, "12345", xml_checksum+"new", sid).getState(),LookupState.UPDATE);
		server.getDatastore().getDB().dropDatabase();
	}
	
	@Test
	public void testFailedrecords(){
		
		saverecord(sid);
		
		Assert.assertTrue(server.newIdExists(oid));
		Assert.assertEquals(server.retrieveEuropeanaIdFromOriginal(oid, cid).get(0).getEid(),registry.getEid());
		Assert.assertTrue(server.oldIdExists(eid));
		Assert.assertEquals(server.retrieveEuropeanaIdFromNew(eid).getOrid(),registry.getOrid());
		Assert.assertEquals(server.retrieveFromOriginalXML(oid, generatechecksum(xml_checksum)).getEid(), registry.getEid());
		Assert.assertEquals(LookupState.DUPLICATE_INCOLLECTION,server.lookupUiniqueId(oid, cid, xml_checksum, sid).getState());
		
		//edm=tst_checksum, collectionId=12345, originalId=test_oid, date=Mon Apr 29 18:50:07 CEST 2013, lookupState=DUPLICATE_INCOLLECTION, europeanaId=/12345/test_oid
		
		List<Map<String, String>> frecords = server.getFailedRecords(cid);
		
		Assert.assertEquals(1,  frecords.size());
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

	
	private void saverecord(String sessionID){
		registry = new EuropeanaIdRegistry();
		registry.setCid(cid);
		registry.setEid(eid);
		registry.setOrid(oid);
		registry.setSessionID(sessionID);
		registry.setXmlchecksum(generatechecksum(xml_checksum));
		server.getDatastore().save(registry);
	}
	
	
	
	@After
	public void cleanup(){
		server.getDatastore().getDB().dropDatabase();
	}
}
