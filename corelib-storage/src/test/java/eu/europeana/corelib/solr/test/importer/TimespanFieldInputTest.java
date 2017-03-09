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
package eu.europeana.corelib.solr.test.importer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;

import eu.europeana.corelib.definitions.jibx.AltLabel;
import eu.europeana.corelib.definitions.jibx.Begin;
import eu.europeana.corelib.definitions.jibx.End;
import eu.europeana.corelib.definitions.jibx.IsPartOf;
import eu.europeana.corelib.definitions.jibx.LiteralType.Lang;
import eu.europeana.corelib.definitions.jibx.Note;
import eu.europeana.corelib.definitions.jibx.PrefLabel;
import eu.europeana.corelib.definitions.jibx.TimeSpanType;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.edm.server.importer.util.TimespanFieldInput;
import eu.europeana.corelib.mongo.server.EdmMongoServer;
import eu.europeana.corelib.solr.entity.TimespanImpl;

/**
 * Timespan Field Input Creator
 * @author Yorgos.Mamakis@ kb.nl
 *
 */
public class TimespanFieldInputTest {

	@SuppressWarnings("unchecked")
	@Test
	public void testTimespan() {
		TimeSpanType timespan = new TimeSpanType();
		timespan.setAbout("test about");
		List<AltLabel> altLabelList = new ArrayList<>();
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
		List<Note> noteList = new ArrayList<>();
		Note note = new Note();
		note.setString("test note");
		assertNotNull(note);
		noteList.add(note);
		timespan.setNoteList(noteList);
		List<PrefLabel> prefLabelList = new ArrayList<>();
		PrefLabel prefLabel = new PrefLabel();
		prefLabel.setLang(lang);
		prefLabel.setString("test pred label");
		assertNotNull(prefLabel);
		prefLabelList.add(prefLabel);
		timespan.setPrefLabelList(prefLabelList);
		List<IsPartOf> isPartOfList = new ArrayList<>();
		IsPartOf isPartOf = new IsPartOf();
		eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource isPartOfResource = 
				new eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource();
		isPartOfResource.setResource("test resource");
		isPartOf.setResource(isPartOfResource);
		isPartOfList.add(isPartOf);
		timespan.setIsPartOfList(isPartOfList);

		TimespanImpl timespanImpl = new TimespanImpl();
		timespanImpl.setAbout(timespan.getAbout());
	
		// create mongo
		EdmMongoServer mongoServerMock = mock(EdmMongoServer.class);
		Datastore datastoreMock = mock(Datastore.class);
		Query queryMock = mock(Query.class);
		Key<TimespanImpl> keyMock = mock(Key.class);
		
		when(mongoServerMock.getDatastore()).thenReturn(datastoreMock);
		when(datastoreMock.find(TimespanImpl.class)).thenReturn(queryMock);
		when(datastoreMock.save(timespanImpl)).thenReturn(keyMock);
		when(queryMock.filter("about", timespan.getAbout())).thenReturn(queryMock);

		TimespanImpl timespanMongo = new TimespanFieldInput()
				.createNewTimespan(timespan);
		mongoServerMock.getDatastore().save(timespanMongo);
		assertEquals(timespan.getAbout(), timespanMongo.getAbout());
		assertEquals(timespan.getBegin().getString(), timespanMongo.getBegin().values().iterator().next().get(0));
		assertEquals(timespan.getEnd().getString(), timespanMongo.getEnd().values().iterator().next().get(0));
		assertEquals(timespan.getNoteList().get(0).getString(),
				timespanMongo.getNote().values().iterator().next().get(0));
		assertTrue(timespanMongo.getAltLabel().containsKey(
				timespan.getAltLabelList().get(0).getLang().getLang()));
		assertTrue(timespanMongo.getPrefLabel().containsKey(
				timespan.getPrefLabelList().get(0).getLang().getLang()));
		assertEquals(timespan.getAltLabelList().get(0).getString(),
				timespanMongo.getAltLabel().values().iterator().next().get(0));
		assertEquals(timespan.getPrefLabelList().get(0).getString(),
				timespanMongo.getPrefLabel().values().iterator().next().get(0));
		assertEquals(timespan.getIsPartOfList().get(0).getResource().getResource(),
				timespanMongo.getIsPartOf().values().iterator().next().get(0));
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

		assertEquals(timespan.getIsPartOfList().get(0).getResource().getResource(), solrDocument
				.getFieldValue(EdmLabel.TS_DCTERMS_ISPART_OF.toString())
				.toString());
	}


}
