package eu.europeana.corelib.denormalization.impl;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.solr.denormalization.impl.ControlledVocabularyImpl;
import eu.europeana.corelib.solr.denormalization.impl.Extractor;
import eu.europeana.corelib.solr.denormalization.impl.VocabularyMongoServer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/corelib-solr-context.xml", "/corelib-solr-test.xml" })
public class ExtractorTest {
	@Resource (name = "corelib_solr_vocabularyMongoServer")
	VocabularyMongoServer mongoServer;
	
	@Test
	public void testExtractor(){
		assertNotNull(mongoServer);
		ControlledVocabularyImpl vocabulary = new ControlledVocabularyImpl("testVocabulary");
		assertNotNull(vocabulary);
		vocabulary.setLocation("testLocation");
		vocabulary.setURI("http://testuri");
		vocabulary.setSuffix(".suffix");
		
		Extractor extractor = new Extractor(vocabulary, mongoServer);
		assertNotNull(extractor);
		extractor.setMappedField("test_contributor", EdmLabel.DC_CONTRIBUTOR);
		extractor.saveMapping();
		assertEquals(1, extractor.getControlledVocabularies().size());
		assertNotNull(extractor.getControlledVocabulary("name", "testVocabulary"));
		ControlledVocabularyImpl retrieveVocabulary = (ControlledVocabularyImpl) extractor.getControlledVocabulary("name","testVocabulary");
		ControlledVocabularyImpl retrieveVocabularyUri = (ControlledVocabularyImpl) extractor.getControlledVocabulary("URI","http://testuri");
		assertEquals(retrieveVocabulary.getId(),retrieveVocabularyUri.getId());
		assertEquals(vocabulary.getName(),retrieveVocabulary.getName());
		assertEquals(1, retrieveVocabulary.getElements().size());
		assertEquals(vocabulary.getLocation(), retrieveVocabulary.getLocation());
		assertEquals(vocabulary.getSuffix(),retrieveVocabulary.getSuffix());
		assertEquals(vocabulary.getURI(),retrieveVocabulary.getURI());
		assertTrue(retrieveVocabulary.getElements().containsKey("test_contributor"));
		assertEquals(EdmLabel.DC_CONTRIBUTOR, retrieveVocabulary.getElements().get("test_contributor"));
		assertEquals(EdmLabel.DC_CONTRIBUTOR,extractor.getEdmLabel("test_contributor"));
		assertEquals("test_contributor", extractor.getMappedField(EdmLabel.DC_CONTRIBUTOR));
	}
	
	@After
	public void cleanup(){
		mongoServer.getDatastore().getDB().dropDatabase();
	}
}
