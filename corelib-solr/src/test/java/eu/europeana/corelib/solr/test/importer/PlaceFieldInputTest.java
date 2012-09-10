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
import eu.europeana.corelib.definitions.jibx.IsPartOf;
import eu.europeana.corelib.definitions.jibx.Lat;
import eu.europeana.corelib.definitions.jibx.LiteralType.Lang;
import eu.europeana.corelib.definitions.jibx.Note;
import eu.europeana.corelib.definitions.jibx.PlaceType;
import eu.europeana.corelib.definitions.jibx.PrefLabel;
import eu.europeana.corelib.definitions.jibx._Long;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.solr.entity.PlaceImpl;
import eu.europeana.corelib.solr.server.EdmMongoServer;
import eu.europeana.corelib.solr.server.importer.util.PlaceFieldInput;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/corelib-solr-context.xml", "/corelib-solr-test.xml" })
public class PlaceFieldInputTest {

	@Resource(name = "corelib_solr_mongoServer")
	private EdmMongoServer mongoServer;

	@Test
	public void testPlace() {
		assertNotNull(mongoServer);

		PlaceType place = new PlaceType();
		place.setAbout("test about");
		List<AltLabel> altLabelList = new ArrayList<AltLabel>();
		AltLabel altLabel = new AltLabel();
		Lang lang = new Lang();
		lang.setLang("en");
		altLabel.setLang(lang);
		altLabel.setString("test alt label");
		assertNotNull(altLabel);
		altLabelList.add(altLabel);
		place.setAltLabelList(altLabelList);
		List<Note> noteList = new ArrayList<Note>();
		Note note = new Note();
		note.setString("test note");
		assertNotNull(note);
		noteList.add(note);
		place.setNoteList(noteList);
		List<PrefLabel> prefLabelList = new ArrayList<PrefLabel>();
		PrefLabel prefLabel = new PrefLabel();
		prefLabel.setLang(lang);
		prefLabel.setString("test pred label");
		assertNotNull(prefLabel);
		prefLabelList.add(prefLabel);
		place.setPrefLabelList(prefLabelList);
		List<IsPartOf> isPartOfList = new ArrayList<IsPartOf>();
		IsPartOf isPartOf = new IsPartOf();
		isPartOf.setString("test resource");
		isPartOfList.add(isPartOf);
		place.setIsPartOfList(isPartOfList);
		Lat posLat = new Lat();
		posLat.setString("0.0");
		place.setLat(posLat);
		_Long posLong = new _Long();
		posLong.setString("0.0");
		place.setLong(posLong);
		// create mongo place
		PlaceImpl placeMongo = new PlaceFieldInput().createPlaceMongoFields(place,
				mongoServer);
		assertEquals(place.getAbout(), placeMongo.getAbout());
		assertEquals(place.getNoteList().get(0).getString(),
				placeMongo.getNote().values().iterator().next());
		assertTrue(placeMongo.getAltLabel().containsKey(
				place.getAltLabelList().get(0).getLang().getLang()));
		assertTrue(placeMongo.getPrefLabel().containsKey(
				place.getPrefLabelList().get(0).getLang().getLang()));
		assertTrue(placeMongo.getAltLabel().containsValue(
				place.getAltLabelList().get(0).getString()));
		assertTrue(placeMongo.getPrefLabel().containsValue(
				place.getPrefLabelList().get(0).getString()));
		assertEquals(place.getIsPartOfList().get(0).getString(),
				placeMongo.getIsPartOf().values().iterator().next());
		assertEquals(place.getLat().getString(),
				Float.toString(placeMongo.getLatitude()));
		assertEquals(place.getLong().getString(),
				Float.toString(placeMongo.getLongitude()));
		// create solr document
		SolrInputDocument solrDocument = new SolrInputDocument();
		solrDocument = new PlaceFieldInput().createPlaceSolrFields(place,
				solrDocument);
		assertEquals(place.getAbout(),
				solrDocument.getFieldValue(EdmLabel.EDM_PLACE.toString())
						.toString());
		assertEquals(place.getNoteList().get(0).getString(),
				solrDocument.getFieldValues(EdmLabel.PL_SKOS_NOTE.toString())
						.toArray()[0].toString());
		assertEquals(
				place.getAltLabelList().get(0).getString(),
				solrDocument.getFieldValues(
						EdmLabel.PL_SKOS_ALT_LABEL.toString()
								+ "."
								+ place.getAltLabelList().get(0).getLang()
										.getLang()).toArray()[0].toString());

		assertEquals(
				place.getPrefLabelList().get(0).getString(),
				solrDocument.getFieldValues(
						EdmLabel.PL_SKOS_PREF_LABEL.toString()
								+ "."
								+ place.getAltLabelList().get(0).getLang()
										.getLang()).toArray()[0].toString());

		assertEquals(place.getLat().getString(),
				solrDocument
						.getFieldValue(EdmLabel.PL_WGS84_POS_LAT.toString()));
		assertEquals(place.getLong().getString(),
				solrDocument
						.getFieldValue(EdmLabel.PL_WGS84_POS_LAT.toString()));

		assertEquals(place.getIsPartOfList().get(0).getString(), solrDocument
				.getFieldValue(EdmLabel.PL_DCTERMS_ISPART_OF.toString())
				.toString());
	}

	@After
	public void cleanup() {
		mongoServer.getDatastore().getDB().dropDatabase();
	}
}
