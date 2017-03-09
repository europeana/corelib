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

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;
import eu.europeana.corelib.definitions.jibx.*;
import eu.europeana.corelib.definitions.jibx.LiteralType.Lang;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.edm.server.importer.util.PlaceFieldInput;
import eu.europeana.corelib.mongo.server.EdmMongoServer;
import eu.europeana.corelib.solr.entity.PlaceImpl;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Place Field Input Field creator
 *
 * @author Yorgos.Mamakis@ kb.nl
 */
public class PlaceFieldInputTest {

    @SuppressWarnings("unchecked")
    @Test
    public void testPlace() {
        PlaceImpl placeImpl = new PlaceImpl();
        placeImpl.setAbout("test about");

        EdmMongoServer mongoServerMock = mock(EdmMongoServer.class);
        Datastore datastoreMock = mock(Datastore.class);
        Query queryMock = mock(Query.class);
        Key<PlaceImpl> keyMock = mock(Key.class);

        when(mongoServerMock.getDatastore()).thenReturn(datastoreMock);
        when(datastoreMock.find(PlaceImpl.class)).thenReturn(queryMock);
        when(datastoreMock.save(placeImpl)).thenReturn(keyMock);
        when(queryMock.filter("about", placeImpl.getAbout())).thenReturn(queryMock);

        PlaceType place = new PlaceType();
        place.setAbout("test about");
        List<AltLabel> altLabelList = new ArrayList<>();
        AltLabel altLabel = new AltLabel();
        Lang lang = new Lang();
        lang.setLang("en");
        altLabel.setLang(lang);
        altLabel.setString("test alt label");
        assertNotNull(altLabel);
        altLabelList.add(altLabel);
        place.setAltLabelList(altLabelList);
        List<Note> noteList = new ArrayList<>();
        Note note = new Note();
        note.setString("test note");
        assertNotNull(note);
        noteList.add(note);
        place.setNoteList(noteList);
        List<PrefLabel> prefLabelList = new ArrayList<>();
        PrefLabel prefLabel = new PrefLabel();
        prefLabel.setLang(lang);
        prefLabel.setString("test pred label");
        assertNotNull(prefLabel);
        prefLabelList.add(prefLabel);
        place.setPrefLabelList(prefLabelList);
        List<IsPartOf> isPartOfList = new ArrayList<>();
        IsPartOf isPartOf = new IsPartOf();
        isPartOf.setString("test resource");
        isPartOfList.add(isPartOf);
        place.setIsPartOfList(isPartOfList);
        Lat posLat = new Lat();
        posLat.setLat(new Float("1.0"));
        place.setLat(posLat);
        _Long posLong = new _Long();
        posLong.setLong(new Float("1.0"));
        place.setLong(posLong);
        // create mongo place
        PlaceImpl placeMongo = new PlaceFieldInput().createNewPlace(
                place);
        mongoServerMock.getDatastore().save(placeMongo);
        assertEquals(place.getAbout(), placeMongo.getAbout());
        assertEquals(place.getNoteList().get(0).getString(), placeMongo
                .getNote().values().iterator().next().get(0));
        assertTrue(placeMongo.getAltLabel().containsKey(
                place.getAltLabelList().get(0).getLang().getLang()));
        assertEquals(place.getAltLabelList().get(0).getString(), placeMongo
                .getAltLabel().values().iterator().next().get(0));

        assertEquals(place.getPrefLabelList().get(0).getString(), placeMongo
                .getPrefLabel().values().iterator().next().get(0));
        assertEquals(place.getIsPartOfList().get(0).getString(), placeMongo
                .getIsPartOf().values().iterator().next().get(0));
        assertEquals(Float.toString(place.getLat().getLat()),
                Float.toString(placeMongo.getLatitude()));
        assertEquals(Float.toString(place.getLong().getLong()),
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

        assertEquals(
                place.getLat().getLat(),
                solrDocument.getFieldValue(EdmLabel.PL_WGS84_POS_LAT.toString()));
        assertEquals(
                place.getLong().getLong(),
                solrDocument.getFieldValue(EdmLabel.PL_WGS84_POS_LAT.toString()));

        assertEquals(place.getIsPartOfList().get(0).getString(), solrDocument
                .getFieldValue(EdmLabel.PL_DCTERMS_ISPART_OF.toString())
                .toString());
    }

}
