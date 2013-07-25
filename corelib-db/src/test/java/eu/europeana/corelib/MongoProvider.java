package eu.europeana.corelib;

import java.io.IOException;

import org.junit.Test;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.distribution.Version;

public class MongoProvider {

	public MongoProvider(){
		int port=10000;
		MongodConfig conf = new MongodConfig(Version.V2_0_7, port,
				false);

		MongodStarter runtime = MongodStarter.getDefaultInstance();
		
		MongodExecutable mongodExecutable = runtime.prepare(conf);
		try {
			mongodExecutable.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void doNothing(){
		return ;
	}
}
