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
import org.junit.Test;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;
import eu.europeana.corelib.definitions.jibx.AltLabel;
import eu.europeana.corelib.definitions.jibx.IsPartOf;
import eu.europeana.corelib.definitions.jibx.Lat;
import eu.europeana.corelib.definitions.jibx.LiteralType.Lang;
import eu.europeana.corelib.definitions.jibx.Note;
import eu.europeana.corelib.definitions.jibx.PlaceType;
import eu.europeana.corelib.definitions.jibx.PrefLabel;
import eu.europeana.corelib.definitions.jibx._Long;
import eu.europeana.corelib.edm.server.importer.util.PlaceFieldInput;
import eu.europeana.corelib.mongo.server.EdmMongoServer;
import eu.europeana.corelib.solr.entity.PlaceImpl;

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
    }
}
