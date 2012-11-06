package eu.europeana.corelib.tools.lookuptable;

import java.net.UnknownHostException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class CollectionMongoServerTest {
	CollectionMongoServer colServer;
	@Test 
	public void test(){
		try {
			colServer = new CollectionMongoServer(new Mongo("localhost",27017), "collectionTest");
			Collection col = new Collection();
			col.setNewCollectionId("12345");
			col.setOldCollectionId("54321");
			colServer.saveCollection(col);
			
			Assert.assertEquals(1,colServer.retrieveAllCollections().size());
			Assert.assertEquals(colServer.findNewCollectionId("54321"),"12345");
			Assert.assertEquals(colServer.findOldCollectionId("12345"),"54321");
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MongoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	@After
	public void cleanUp(){
		colServer.getDatastore().getDB().dropDatabase();
	}

}
