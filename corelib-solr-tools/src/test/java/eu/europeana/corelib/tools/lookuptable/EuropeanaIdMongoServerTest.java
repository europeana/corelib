package eu.europeana.corelib.tools.lookuptable;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Date;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;

import com.mongodb.Mongo;
import com.mongodb.MongoException;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.distribution.Version;

import eu.europeana.corelib.tools.lookuptable.impl.EuropeanaIdMongoServerImpl;

public class EuropeanaIdMongoServerTest {
	EuropeanaIdMongoServerImpl server;
	@Test
	public void test(){
		try {
			int port = 10000;
			MongodConfig conf = new MongodConfig(Version.V2_0_7, port,
					false);

			MongodStarter runtime = MongodStarter.getDefaultInstance();

			MongodExecutable mongodExecutable = runtime.prepare(conf);
			mongodExecutable.start();
			server = new EuropeanaIdMongoServerImpl(new Mongo("localhost",27017), "europeanaId_test","","");
			server.createDatastore();
			EuropeanaId eid = new EuropeanaId();
			eid.setNewId("newId");
			eid.setOldId("oldId");
			eid.setLastAccess(new Date().getTime());
			
			server.saveEuropeanaId(eid);
			
			Assert.assertTrue(server.newIdExists("oldId"));
			Assert.assertTrue(server.oldIdExists("newId"));
			Assert.assertEquals( server.retrieveEuropeanaIdFromNew("newId").get(0).getOldId(),"oldId");
			Assert.assertEquals(server.retrieveEuropeanaIdFromOld("oldId").getNewId(), "newId");
			mongodExecutable.stop();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MongoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
