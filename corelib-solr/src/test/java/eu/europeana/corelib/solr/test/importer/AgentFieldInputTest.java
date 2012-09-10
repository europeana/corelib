package eu.europeana.corelib.solr.test.importer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.MalformedURLException;
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
import eu.europeana.corelib.definitions.jibx.Begin;
import eu.europeana.corelib.definitions.jibx.End;
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
	public void testAgent() throws MalformedURLException, IOException{
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
		Begin begin = new Begin();
		begin.setString("test begin");
		agentType.setBegin(begin);
		End end = new End();
		end.setString("test end");
		agentType.setEnd(end);
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
		AgentImpl agent = new AgentFieldInput().createAgentMongoEntity(agentType,
				mongoServer);
		assertEquals(agentType.getAbout(), agent.getAbout());
		assertEquals(agentType.getBegin().getString(), agent.getBegin().values().iterator().next());
		assertEquals(agentType.getEnd().getString(), agent.getEnd().values().iterator().next());
		assertEquals(agentType.getNoteList().get(0).getString(),
				agent.getNote().values().iterator().next());
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
		solrDocument = new AgentFieldInput().createAgentSolrFields(agentType,
				solrDocument);
		assertEquals(agentType.getAbout(),
				solrDocument.getFieldValue(EdmLabel.EDM_AGENT.toString())
						.toString());
		assertEquals(agentType.getBegin().getString(),
				solrDocument.getFieldValues(EdmLabel.AG_EDM_BEGIN.toString())
						.toArray()[0].toString());
		assertEquals(agentType.getEnd().getString(),
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
