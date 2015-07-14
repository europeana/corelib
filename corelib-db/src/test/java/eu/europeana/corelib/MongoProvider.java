package eu.europeana.corelib;

import java.io.IOException;

import org.junit.Test;

import com.mongodb.Mongo;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import eu.europeana.corelib.edm.exceptions.MongoDBException;
import eu.europeana.corelib.mongo.server.EdmMongoServer;
import eu.europeana.corelib.mongo.server.impl.EdmMongoServerImpl;

public class MongoProvider {

	private EdmMongoServer mongoDBServer;

	public MongoProvider() {
		int port = 10000;
		try {
			IMongodConfig conf = new MongodConfigBuilder()
					.version(Version.Main.PRODUCTION)
					.net(new Net(port, Network.localhostIsIPv6())).build();
		MongodStarter runtime = MongodStarter.getDefaultInstance();
		MongodExecutable mongodExecutable = runtime.prepare(conf);
			mongodExecutable.start();
			Mongo mongo = new Mongo("localhost", port);
			mongoDBServer = new EdmMongoServerImpl(mongo, "europeana_test", "",
					"");
			mongoDBServer.getDatastore().getDB().dropDatabase();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MongoDBException e) {
			e.printStackTrace();
		}
	}

				public MongoProvider(final String host, final String port) {
								try {
												IMongodConfig conf = new MongodConfigBuilder()
																																									.version(Version.Main.PRODUCTION)
																																									.net(new Net(Integer.getInteger(port), Network.localhostIsIPv6())).build();
												MongodStarter runtime = MongodStarter.getDefaultInstance();
												MongodExecutable mongodExecutable = runtime.prepare(conf);
												mongodExecutable.start();
												Mongo mongo = new Mongo("localhost", Integer.getInteger(port));
												mongoDBServer = new EdmMongoServerImpl(mongo, "europeana_test", "",
																																																			"");
												mongoDBServer.getDatastore().getDB().dropDatabase();
								} catch (IOException e) {
												e.printStackTrace();
								} catch (MongoDBException e) {
												e.printStackTrace();
								}
				}

	@Test
	public void doNothing() {
		return;
	}
}
