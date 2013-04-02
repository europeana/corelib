package eu.europeana.corelib.tools.lookuptable;

import java.net.UnknownHostException;
import java.util.Date;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;

import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class EuropeanaIdMongoServerTest {
	EuropeanaIdMongoServer server;
	@Test
	public void test(){
		try {
			server = new EuropeanaIdMongoServer(new Mongo("localhost",27017), "europeanaId_test");
			server.createDatastore();
			EuropeanaId eid = new EuropeanaId();
			eid.setNewId("newId");
			eid.setOldId("oldId");
			eid.setLastAccess(new Date().getTime());
			
			server.saveEuropeanaId(eid);
			
			Assert.assertTrue(server.newIdExists("oldId"));
			Assert.assertTrue(server.oldIdExists("newId"));
			Assert.assertEquals( server.retrieveEuropeanaIdFromNew("newId").get(0).getOldId(),"oldId");
			Assert.assertEquals(server.retrieveEuropeanaIdFromOld("oldId").get(0).getNewId(), "newId");
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MongoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@After
	public void cleanup(){
		server.getDatastore().getDB().dropDatabase();
	}
}
