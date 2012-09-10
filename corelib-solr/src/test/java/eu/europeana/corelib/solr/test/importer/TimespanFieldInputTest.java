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
import eu.europeana.corelib.definitions.jibx.Begin;
import eu.europeana.corelib.definitions.jibx.End;
import eu.europeana.corelib.definitions.jibx.IsPartOf;
import eu.europeana.corelib.definitions.jibx.LiteralType.Lang;
import eu.europeana.corelib.definitions.jibx.Note;
import eu.europeana.corelib.definitions.jibx.PrefLabel;
import eu.europeana.corelib.definitions.jibx.TimeSpanType;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.solr.entity.TimespanImpl;
import eu.europeana.corelib.solr.server.EdmMongoServer;
import eu.europeana.corelib.solr.server.importer.util.TimespanFieldInput;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/corelib-solr-context.xml", "/corelib-solr-test.xml" })
public class TimespanFieldInputTest {

	@Resource(name = "corelib_solr_mongoServer")
	private EdmMongoServer mongoServer;

	@Test
	public void testTimespan()  {
		TimeSpanType timespan = new TimeSpanType();
		timespan.setAbout("test about");
		List<AltLabel> altLabelList = new ArrayList<AltLabel>();
		AltLabel altLabel = new AltLabel();
		Lang lang = new Lang();
		lang.setLang("en");
		altLabel.setLang(lang);
		altLabel.setString("test alt label");
		assertNotNull(altLabel);
		altLabelList.add(altLabel);
		timespan.setAltLabelList(altLabelList);
		Begin begin = new Begin();
		begin.setString("test begin");
		timespan.setBegin(begin);
		End end = new End();
		end.setString("test end");
		timespan.setEnd(end);
		List<Note> noteList = new ArrayList<Note>();
		Note note = new Note();
		note.setString("test note");
		assertNotNull(note);
		noteList.add(note);
		timespan.setNoteList(noteList);
		List<PrefLabel> prefLabelList = new ArrayList<PrefLabel>();
		PrefLabel prefLabel = new PrefLabel();
		prefLabel.setLang(lang);
		prefLabel.setString("test pred label");
		assertNotNull(prefLabel);
		prefLabelList.add(prefLabel);
		timespan.setPrefLabelList(prefLabelList);
		List<IsPartOf> isPartOfList = new ArrayList<IsPartOf>();
		IsPartOf isPartOf = new IsPartOf();
		isPartOf.setResource("test resource");
		isPartOfList.add(isPartOf);
		timespan.setIsPartOfList(isPartOfList);

		// create mongo

		TimespanImpl timespanMongo = new TimespanFieldInput()
				.createTimespanMongoField(timespan, mongoServer);
		assertEquals(timespan.getAbout(), timespanMongo.getAbout());
		assertEquals(timespan.getBegin().getString(), timespanMongo.getBegin().values().iterator().next());
		assertEquals(timespan.getEnd().getString(), timespanMongo.getEnd().values().iterator().next());
		assertEquals(timespan.getNoteList().get(0).getString(),
				timespanMongo.getNote().values().iterator().next());
		assertTrue(timespanMongo.getAltLabel().containsKey(
				timespan.getAltLabelList().get(0).getLang().getLang()));
		assertTrue(timespanMongo.getPrefLabel().containsKey(
				timespan.getPrefLabelList().get(0).getLang().getLang()));
		assertTrue(timespanMongo.getAltLabel().containsValue(
				timespan.getAltLabelList().get(0).getString()));
		assertTrue(timespanMongo.getPrefLabel().containsValue(
				timespan.getPrefLabelList().get(0).getString()));
		assertEquals(timespan.getIsPartOfList().get(0).getResource(),
				timespanMongo.getIsPartOf().values().iterator().next());
		// create solr document
		SolrInputDocument solrDocument = new SolrInputDocument();
		solrDocument = new TimespanFieldInput().createTimespanSolrFields(timespan,
				solrDocument);
		assertEquals(timespan.getAbout(),
				solrDocument.getFieldValue(EdmLabel.EDM_TIMESPAN.toString())
						.toString());
		assertEquals(timespan.getBegin().getString(),
				solrDocument.getFieldValues(EdmLabel.TS_EDM_BEGIN.toString())
						.toArray()[0].toString());
		assertEquals(timespan.getEnd().getString(),
				solrDocument.getFieldValues(EdmLabel.TS_EDM_END.toString())
						.toArray()[0].toString());
		assertEquals(timespan.getNoteList().get(0).getString(),
				solrDocument.getFieldValues(EdmLabel.TS_SKOS_NOTE.toString())
						.toArray()[0].toString());
		assertEquals(
				timespan.getAltLabelList().get(0).getString(),
				solrDocument.getFieldValues(
						EdmLabel.TS_SKOS_ALT_LABEL.toString()
								+ "."
								+ timespan.getAltLabelList().get(0).getLang()
										.getLang()).toArray()[0].toString());

		assertEquals(
				timespan.getPrefLabelList().get(0).getString(),
				solrDocument.getFieldValues(
						EdmLabel.TS_SKOS_PREF_LABEL.toString()
								+ "."
								+ timespan.getAltLabelList().get(0).getLang()
										.getLang()).toArray()[0].toString());

		assertEquals(timespan.getIsPartOfList().get(0).getResource(), solrDocument
				.getFieldValue(EdmLabel.TS_DCTERMS_ISPART_OF.toString())
				.toString());
	}

	@After
	public void cleanup() {
		mongoServer.getDatastore().getDB().dropDatabase();
	}
}
