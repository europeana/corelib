package eu.europeana.corelib.solr.test.importer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.solr.common.SolrInputDocument;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.europeana.corelib.definitions.jibx.AltLabel;
import eu.europeana.corelib.definitions.jibx.Concept;
import eu.europeana.corelib.definitions.jibx.Note;
import eu.europeana.corelib.definitions.jibx.PrefLabel;
import eu.europeana.corelib.definitions.jibx.LiteralType.Lang;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.solr.entity.ConceptImpl;
import eu.europeana.corelib.solr.server.EdmMongoServer;

import eu.europeana.corelib.solr.server.importer.util.ConceptFieldInput;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/corelib-solr-context.xml", "/corelib-solr-test.xml" })
public class ConceptFieldInputTest {

	@Resource(name = "corelib_solr_mongoServer")
	private EdmMongoServer mongoServer;

	@Test
	public void testConcept() {
		assertNotNull(mongoServer);
		//create concept from jibx bindings
		Concept concept = new Concept();
		concept.setAbout("test about");
		List<AltLabel> altLabelList = new ArrayList<AltLabel>();
		AltLabel altLabel = new AltLabel();
		Lang lang = new Lang();
		lang.setLang("en");
		altLabel.setLang(lang);
		altLabel.setString("test alt label");
		assertNotNull(altLabel);
		altLabelList.add(altLabel);
		concept.setAltLabelList(altLabelList);
		List<Note> noteList = new ArrayList<Note>();
		Note note = new Note();
		note.setString("test note");
		assertNotNull(note);
		noteList.add(note);
		concept.setNoteList(noteList);
		List<PrefLabel> prefLabelList = new ArrayList<PrefLabel>();
		PrefLabel prefLabel = new PrefLabel();
		prefLabel.setLang(lang);
		prefLabel.setString("test pred label");
		assertNotNull(prefLabel);
		prefLabelList.add(prefLabel);
		concept.setPrefLabelList(prefLabelList);
		
		//store in mongo
		ConceptImpl conceptMongo = ConceptFieldInput.createConceptMongoFields(concept, mongoServer, null);
		assertEquals(concept.getAbout(), conceptMongo.getAbout());
		assertEquals(concept.getNoteList().get(0).getString(),
				conceptMongo.getNote()[0]);
		assertTrue(conceptMongo.getAltLabel().containsKey(
				concept.getAltLabelList().get(0).getLang().getLang()));
		assertTrue(conceptMongo.getPrefLabel().containsKey(
				concept.getPrefLabelList().get(0).getLang().getLang()));
		assertTrue(conceptMongo.getAltLabel().containsValue(
				concept.getAltLabelList().get(0).getString()));
		assertTrue(conceptMongo.getPrefLabel().containsValue(
				concept.getPrefLabelList().get(0).getString()));
		
		//create solr document
		SolrInputDocument solrDocument = new SolrInputDocument();
		solrDocument = ConceptFieldInput.createConceptSolrFields(concept,
				solrDocument);
		assertEquals(concept.getAbout(),
				solrDocument.getFieldValue(EdmLabel.SKOS_CONCEPT.toString())
						.toString());
		assertEquals(concept.getNoteList().get(0).getString(),
				solrDocument.getFieldValues(EdmLabel.CC_SKOS_NOTE.toString())
						.toArray()[0].toString());
		assertEquals(
				concept.getAltLabelList().get(0).getString(),
				solrDocument.getFieldValues(
						EdmLabel.CC_SKOS_ALT_LABEL.toString()
								+ "."
								+ concept.getAltLabelList().get(0).getLang()
										.getLang()).toArray()[0].toString());

		assertEquals(
				concept.getPrefLabelList().get(0).getString(),
				solrDocument.getFieldValues(
						EdmLabel.CC_SKOS_PREF_LABEL.toString()
								+ "."
								+ concept.getAltLabelList().get(0).getLang()
										.getLang()).toArray()[0].toString());

	}
	
	@After
	public void cleanup(){
		mongoServer.getDatastore().getDB().dropDatabase();
	}
}
