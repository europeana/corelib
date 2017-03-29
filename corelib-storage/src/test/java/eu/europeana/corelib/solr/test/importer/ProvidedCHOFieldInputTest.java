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
import eu.europeana.corelib.definitions.jibx.ProvidedCHOType;
import eu.europeana.corelib.definitions.jibx.SameAs;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.edm.server.importer.util.ProvidedCHOFieldInput;
import eu.europeana.corelib.mongo.server.EdmMongoServer;
import eu.europeana.corelib.solr.entity.ProvidedCHOImpl;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * ProvidedCHO Field Input creator
 *
 * @author Yoregos.Mamakis@ kb.nl
 */
public class ProvidedCHOFieldInputTest {

    @SuppressWarnings("unchecked")
    @Test
    public void testProvidedCHO() {
        ProvidedCHOImpl providedCHOImpl = new ProvidedCHOImpl();
        providedCHOImpl.setAbout("test about");
        ProvidedCHOType providedCHO = new ProvidedCHOType();
        providedCHO.setAbout(providedCHOImpl.getAbout());

        EdmMongoServer mongoServerMock = mock(EdmMongoServer.class);
        Datastore datastoreMock = mock(Datastore.class);
        Query queryMock = mock(Query.class);
        Key<ProvidedCHOImpl> keyMock = mock(Key.class);

        when(mongoServerMock.getDatastore()).thenReturn(datastoreMock);
        when(datastoreMock.find(ProvidedCHOImpl.class)).thenReturn(queryMock);
        when(queryMock.filter("about", "/item" + providedCHO.getAbout())).thenReturn(queryMock);

        providedCHOImpl.setAbout("/itemtest about"); // TODO What happens here...
        when(datastoreMock.save(providedCHOImpl)).thenReturn(keyMock);

        List<SameAs> sameAsList = new ArrayList<>();
        SameAs sameAs = new SameAs();
        sameAs.setResource("test same as");
        sameAsList.add(sameAs);
        providedCHO.setSameAList(sameAsList);

        // create mongo providedCHO
        try {
            ProvidedCHOImpl providedCHOMongo = new ProvidedCHOFieldInput()
                    .createProvidedCHOMongoFields(providedCHO, mongoServerMock);
            assertEquals("/item" + providedCHO.getAbout(),
                    providedCHOMongo.getAbout());
            assertEquals(providedCHO.getSameAList().get(0).getResource(),
                    providedCHOMongo.getOwlSameAs()[0]);

            SolrInputDocument solrDocument = new SolrInputDocument();
            solrDocument = new ProvidedCHOFieldInput().createProvidedCHOFields(
                    providedCHO, solrDocument);
            assertEquals(
                    providedCHO.getAbout(),
                    solrDocument.getFieldValue(EdmLabel.EUROPEANA_ID.toString()));

            assertEquals(providedCHO.getSameAList().get(0).getResource(),
                    solrDocument.getFieldValue(EdmLabel.PROXY_OWL_SAMEAS
                            .toString()));
        } catch (InstantiationException | IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
