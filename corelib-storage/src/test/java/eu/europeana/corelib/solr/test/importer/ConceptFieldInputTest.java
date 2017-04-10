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
import eu.europeana.corelib.definitions.jibx.AltLabel;
import eu.europeana.corelib.definitions.jibx.Concept;
import eu.europeana.corelib.definitions.jibx.LiteralType.Lang;
import eu.europeana.corelib.definitions.jibx.Note;
import eu.europeana.corelib.definitions.jibx.PrefLabel;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.edm.server.importer.util.ConceptFieldInput;
import eu.europeana.corelib.mongo.server.EdmMongoServer;
import eu.europeana.corelib.solr.entity.ConceptImpl;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for Concepts field input creator
 *
 * @author Yorgos.Mamakis@ kb.nl
 */
public class ConceptFieldInputTest {

    @SuppressWarnings("unchecked")
    @Test
    public void testConcept() {
        // create concept from jibx bindings
        Concept concept = new Concept();
        concept.setAbout("test about");

        ConceptImpl conceptImpl = new ConceptImpl();
        conceptImpl.setAbout(concept.getAbout());

        EdmMongoServer mongoServerMock = mock(EdmMongoServer.class);
        Datastore datastoreMock = mock(Datastore.class);
        Query queryMock = mock(Query.class);
        Key<ConceptImpl> keyMock = mock(Key.class);

        when(mongoServerMock.getDatastore()).thenReturn(datastoreMock);
        when(mongoServerMock.getDatastore()).thenReturn(datastoreMock);
        when(datastoreMock.find(ConceptImpl.class)).thenReturn(queryMock);
        when(datastoreMock.save(conceptImpl)).thenReturn(keyMock);
        when(queryMock.filter("about", concept.getAbout())).thenReturn(queryMock);

        Concept.Choice choice = new Concept.Choice();

        AltLabel altLabel = new AltLabel();
        Lang lang = new Lang();
        lang.setLang("en");
        altLabel.setLang(lang);
        altLabel.setString("test alt label");
        assertNotNull(altLabel);
        choice.setAltLabel(altLabel);
        choice.clearChoiceListSelect();
        Note note = new Note();
        note.setString("test note");
        assertNotNull(note);
        choice.setNote(note);
        choice.clearChoiceListSelect();
        PrefLabel prefLabel = new PrefLabel();
        prefLabel.setLang(lang);
        prefLabel.setString("test pred label");
        assertNotNull(prefLabel);
        choice.setPrefLabel(prefLabel);
        choice.clearChoiceListSelect();
        List<Concept.Choice> choiceList = new ArrayList<>();
        choiceList.add(choice);
        concept.setChoiceList(choiceList);
        // store in mongo
        ConceptImpl conceptMongo = new ConceptFieldInput()
                .createNewConcept(concept);
        mongoServerMock.getDatastore().save(conceptMongo);
        assertEquals(concept.getAbout(), conceptMongo.getAbout());
        for (Concept.Choice choice2 : concept.getChoiceList()) {
            if (choice2.ifNote()) {
                assertEquals(choice2.getNote().getString(), conceptMongo
                        .getNote().values().iterator().next().get(0));
            }
            if (choice2.ifAltLabel()) {
                assertTrue(conceptMongo.getAltLabel().containsKey(
                        choice2.getAltLabel().getLang().getLang()));
                assertEquals(choice2.getAltLabel().getString(), conceptMongo
                        .getAltLabel().values().iterator().next().get(0));
            }
            if (choice2.ifPrefLabel()) {
                assertTrue(conceptMongo.getPrefLabel().containsKey(
                        choice2.getPrefLabel().getLang().getLang()));
                assertEquals(choice2.getPrefLabel().getString(), conceptMongo
                        .getPrefLabel().values().iterator().next().get(0));
            }
        }
        // create solr document
        SolrInputDocument solrDocument = new SolrInputDocument();
        solrDocument = new ConceptFieldInput().createConceptSolrFields(concept,
                solrDocument);
        assertEquals(concept.getAbout(),
                solrDocument.getFieldValue(EdmLabel.SKOS_CONCEPT.toString())
                        .toString());
        for (Concept.Choice choice3 : concept.getChoiceList()) {
            if (choice3.ifNote()) {
                assertEquals(choice3.getNote().getString(), solrDocument
                        .getFieldValues(EdmLabel.CC_SKOS_NOTE.toString())
                        .toArray()[0].toString());
            }
            if (choice3.ifAltLabel()) {
                assertEquals(
                        choice3.getAltLabel().getString(),
                        solrDocument.getFieldValues(
                                EdmLabel.CC_SKOS_ALT_LABEL.toString()
                                        + "."
                                        + choice3.getAltLabel().getLang()
                                        .getLang()).toArray()[0]
                                .toString());
            }

            if (choice3.ifPrefLabel()) {
                assertEquals(
                        choice3.getPrefLabel().getString(),
                        solrDocument.getFieldValues(
                                EdmLabel.CC_SKOS_PREF_LABEL.toString()
                                        + "."
                                        + choice3.getPrefLabel().getLang()
                                        .getLang()).toArray()[0]
                                .toString());
            }
        }
    }

}
