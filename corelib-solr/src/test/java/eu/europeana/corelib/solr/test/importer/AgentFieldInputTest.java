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

import eu.europeana.corelib.definitions.jibx.AgentType;
import eu.europeana.corelib.definitions.jibx.AltLabel;
import eu.europeana.corelib.definitions.jibx.LiteralType.Lang;
import eu.europeana.corelib.definitions.jibx.Note;
import eu.europeana.corelib.definitions.jibx.PrefLabel;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.solr.entity.AgentImpl;
import eu.europeana.corelib.solr.server.EdmMongoServer;
import eu.europeana.corelib.solr.server.importer.util.AgentFieldInput;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/corelib-solr-context.xml", "/corelib-solr-test.xml" })
public class AgentFieldInputTest {

	@Resource(name = "corelib_solr_mongoServer")
	private EdmMongoServer mongoServer;

	@Test
	public void testAgent(){
		assertNotNull(mongoServer);
		AgentType agentType = new AgentType();
		agentType.setAbout("test about");
		List<AltLabel> altLabelList = new ArrayList<AltLabel>();
		AltLabel altLabel = new AltLabel();
		Lang lang = new Lang();
		lang.setLang("en");
		altLabel.setLang(lang);
		altLabel.setString("test alt label");
		assertNotNull(altLabel);
		altLabelList.add(altLabel);
		agentType.setAltLabelList(altLabelList);
		List<String> beginList = new ArrayList<String>();
		beginList.add("test begin");
		agentType.setBegins(beginList);
		List<String> endList = new ArrayList<String>();
		endList.add("test end");
		agentType.setEnds(endList);
		List<Note> noteList = new ArrayList<Note>();
		Note note = new Note();
		note.setString("test note");
		assertNotNull(note);
		noteList.add(note);
		agentType.setNoteList(noteList);
		List<PrefLabel> prefLabelList = new ArrayList<PrefLabel>();
		PrefLabel prefLabel = new PrefLabel();
		prefLabel.setLang(lang);
		prefLabel.setString("test pred label");
		assertNotNull(prefLabel);
		prefLabelList.add(prefLabel);
		agentType.setPrefLabelList(prefLabelList);

		//store in mongo
		AgentImpl agent = AgentFieldInput.createAgentMongoEntity(agentType,
				mongoServer);
		assertEquals(agentType.getAbout(), agent.getAbout());
		assertEquals(agentType.getBegins().get(0), agent.getBegin());
		assertEquals(agentType.getEnds().get(0), agent.getEnd());
		assertEquals(agentType.getNoteList().get(0).getString(),
				agent.getNote()[0]);
		assertTrue(agent.getAltLabel().containsKey(
				agentType.getAltLabelList().get(0).getLang().getLang()));
		assertTrue(agent.getPrefLabel().containsKey(
				agentType.getPrefLabelList().get(0).getLang().getLang()));
		assertTrue(agent.getAltLabel().containsValue(
				agentType.getAltLabelList().get(0).getString()));
		assertTrue(agent.getPrefLabel().containsValue(
				agentType.getPrefLabelList().get(0).getString()));

		//create solr document
		SolrInputDocument solrDocument = new SolrInputDocument();
		solrDocument = AgentFieldInput.createAgentSolrFields(agentType,
				solrDocument);
		assertEquals(agentType.getAbout(),
				solrDocument.getFieldValue(EdmLabel.EDM_AGENT.toString())
						.toString());
		assertEquals(agentType.getBegins().get(0),
				solrDocument.getFieldValues(EdmLabel.AG_EDM_BEGIN.toString())
						.toArray()[0].toString());
		assertEquals(agentType.getEnds().get(0),
				solrDocument.getFieldValues(EdmLabel.AG_EDM_END.toString())
						.toArray()[0].toString());
		assertEquals(agentType.getNoteList().get(0).getString(),
				solrDocument.getFieldValues(EdmLabel.AG_SKOS_NOTE.toString())
						.toArray()[0].toString());
		assertEquals(
				agentType.getAltLabelList().get(0).getString(),
				solrDocument.getFieldValues(
						EdmLabel.AG_SKOS_ALT_LABEL.toString()
								+ "."
								+ agentType.getAltLabelList().get(0).getLang()
										.getLang()).toArray()[0].toString());

		assertEquals(
				agentType.getPrefLabelList().get(0).getString(),
				solrDocument.getFieldValues(
						EdmLabel.AG_SKOS_PREF_LABEL.toString()
								+ "."
								+ agentType.getAltLabelList().get(0).getLang()
										.getLang()).toArray()[0].toString());

	}

	@After
	public void cleanup() {
		mongoServer.getDatastore().getDB().dropDatabase();
	}
}
