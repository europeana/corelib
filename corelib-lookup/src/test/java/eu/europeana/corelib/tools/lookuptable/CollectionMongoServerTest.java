package eu.europeana.corelib.tools.lookuptable;

import java.io.IOException;
import java.net.UnknownHostException;

import org.junit.Assert;
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
import eu.europeana.corelib.lookup.impl.CollectionMongoServerImpl;

public class CollectionMongoServerTest {
	CollectionMongoServerImpl colServer;

	@Test
	public void test() {
		int port = 10000;
		try {
			IMongodConfig conf = new MongodConfigBuilder()
					.version(Version.Main.PRODUCTION)
					.net(new Net(port, Network.localhostIsIPv6())).build();

			MongodStarter runtime = MongodStarter.getDefaultInstance();

			MongodExecutable mongodExecutable = runtime.prepare(conf);

			mongodExecutable.start();
			colServer = new CollectionMongoServerImpl(new Mongo("localhost",
					port), "collectionTest");
			Collection col = new Collection();
			col.setNewCollectionId("12345");
			col.setOldCollectionId("54321");
			colServer.saveCollection(col);

			Assert.assertEquals(1, colServer.retrieveAllCollections().size());
			Assert.assertEquals(colServer.findNewCollectionId("54321"), "12345");
			Assert.assertEquals(colServer.findOldCollectionId("12345"), "54321");
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
