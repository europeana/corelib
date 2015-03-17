package eu.europeana.corelib.tools.lookuptable;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;

import com.mongodb.Mongo;
import com.mongodb.MongoException;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import eu.europeana.corelib.lookup.impl.EuropeanaIdMongoServerImpl;

public class EuropeanaIdMongoServerTest {
	EuropeanaIdMongoServerImpl server;
	@Test
	public void test(){
		try {
			int port = 10000;
			IMongodConfig conf = new MongodConfigBuilder().version(Version.Main.PRODUCTION)
			        .net(new Net(port, Network.localhostIsIPv6()))
			        .build();

			MongodStarter runtime = MongodStarter.getDefaultInstance();

			MongodExecutable mongodExecutable = runtime.prepare(conf);
			mongodExecutable.start();
			server = new EuropeanaIdMongoServerImpl(new Mongo("localhost",port), "europeanaId_test","","");
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
