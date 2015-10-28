/*
 * Copyright 2007-2012 The Europeana Foundation
 *
 *  Licenced under the EUPL, Version 1.1 (the "Licence") and subsequent versions as approved
 *  by the European Commission;
 *  You may not use this work except in compliance with the Licence.
 * 
 *  You may obtain a copy of the Licence at:
 *  http://joinup.ec.europa.eu/software/page/eupl
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under
 *  the Licence is distributed on an "AS IS" basis, without warranties or conditions of
 *  any kind, either express or implied.
 *  See the Licence for the specific language governing permissions and limitations under
 *  the Licence.
 */
package eu.europeana.corelib.dereference.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mongodb.MongoClient;
import org.junit.Assert;
import org.junit.Test;

import com.mongodb.MongoException;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.dereference.VocabularyMongoServer;

/**
 * Vocabulary Mongo Test
 * 
 * @author Yorgos.Mamakis@ kb.nl
 *
 */
public class VocabularyMongoServerTest {

	VocabularyMongoServer server;

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
			server = new VocabularyMongoServerImpl(
					new MongoClient("localhost", port), "vocTest");
			ControlledVocabularyImpl voc = new ControlledVocabularyImpl();
			voc.setName("name");
			voc.setLocation("location");
			voc.setSuffix("rdf");
			voc.setURI("http://test_uri/");
			Map<String, List<EdmMappedField>> elements = new HashMap<>();
			List<EdmMappedField> lst = new ArrayList<>();
			EdmMappedField dateField = new EdmMappedField();
			dateField.setLabel(EdmLabel.AG_DC_DATE.toString());
			lst.add(dateField);
			elements.put("test", lst);
			voc.setElements(elements);
			voc.setRules(new String[] { "*" });
			server.getDatastore().save(voc);
			Assert.assertNotNull(server.getControlledVocabularyByUri(
					"http://test_uri/", "name"));
			Assert.assertNotNull(server.getControlledVocabulary("URI",
					"http://test_uri/"));

			if (server != null) {
				server.getDatastore().getDB().dropDatabase();
			}
			mongodExecutable.stop();
		} catch (MongoException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
