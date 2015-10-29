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
package eu.europeana.corelib.dereference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import com.mongodb.MongoClient;
import org.junit.Test;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.dereference.impl.ControlledVocabularyImpl;
import eu.europeana.corelib.dereference.impl.Extractor;
import eu.europeana.corelib.dereference.impl.VocabularyMongoServerImpl;

/**
 * Extract test
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
public class ExtractorTest {
	VocabularyMongoServerImpl mongoServer;

	@Test
	public void testExtractor() {
		int port = 10000;
		IMongodConfig conf;
		try {
			conf = new MongodConfigBuilder().version(Version.Main.PRODUCTION)
			        .net(new Net(port, Network.localhostIsIPv6()))
			        .build();
		
		MongodStarter runtime = MongodStarter.getDefaultInstance();

		MongodExecutable mongodExecutable = runtime.prepare(conf);


			mongodExecutable.start();
			mongoServer = new VocabularyMongoServerImpl(new MongoClient("localhost",port),"vocabulary_test");
			assertNotNull(mongoServer);
			ControlledVocabularyImpl vocabulary = new ControlledVocabularyImpl(
					"testVocabulary");
			assertNotNull(vocabulary);
			vocabulary.setLocation("testLocation");
			vocabulary.setURI("http://testuri/");
			vocabulary.setSuffix(".suffix");
			vocabulary.setRules(new String[] { "record" });
			Extractor extractor = new Extractor(vocabulary, mongoServer);
			assertNotNull(extractor);
			extractor.setMappedField("test_contributor",
					EdmLabel.PROXY_DC_CONTRIBUTOR, "xml:lang");
			extractor.saveMapping(0, vocabulary.getRules(),vocabulary.getURI());
			assertEquals(1, extractor.getControlledVocabularies().size());
			assertNotNull(mongoServer.getControlledVocabulary("URI",
					"http://testuri/record"));
			ControlledVocabulary retrieveVocabulary = mongoServer
					.getControlledVocabulary("URI", "http://testuri/record");
			assertEquals(vocabulary.getName(), retrieveVocabulary.getName());
			assertEquals(1, retrieveVocabulary.getElements().size());
			assertEquals(vocabulary.getLocation(),
					retrieveVocabulary.getLocation());
			assertEquals(vocabulary.getSuffix(), retrieveVocabulary.getSuffix());
			assertEquals(vocabulary.getURI(), retrieveVocabulary.getURI());
			assertTrue(retrieveVocabulary.getElements().containsKey(
					"test_contributor"));
			assertEquals(EdmLabel.PROXY_DC_CONTRIBUTOR.toString(), retrieveVocabulary
					.getElements().get("test_contributor").get(0).getLabel());
			assertTrue(extractor.isMapped("test_contributor"));
			assertEquals(EdmLabel.PROXY_DC_CONTRIBUTOR.toString(),
					extractor.getEdmLabel("test_contributor").get(0).getLabel());
			assertEquals("test_contributor",
					extractor.getMappedField(EdmLabel.PROXY_DC_CONTRIBUTOR));
			mongodExecutable.stop();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testReadFromFile(){
		assertNotNull(new Extractor(new ControlledVocabularyImpl(),mongoServer).readSchema("src/test/resources/rdf.xml").keySet());
	}

}
