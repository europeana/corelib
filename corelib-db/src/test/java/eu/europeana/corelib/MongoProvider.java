package eu.europeana.corelib;

import java.io.IOException;

import org.junit.Test;

import com.mongodb.Mongo;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.distribution.Version;
import eu.europeana.corelib.edm.exceptions.MongoDBException;
import eu.europeana.corelib.mongo.server.EdmMongoServer;
import eu.europeana.corelib.mongo.server.impl.EdmMongoServerImpl;

public class MongoProvider {

	private EdmMongoServer mongoDBServer;

	public MongoProvider() {
		int port = 10000;
		MongodConfig conf = new MongodConfig(Version.V2_0_7, port, false);
		MongodStarter runtime = MongodStarter.getDefaultInstance();
		MongodExecutable mongodExecutable = runtime.prepare(conf);
		try {
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

	@Test
	public void doNothing() {
		return;
	}
}
