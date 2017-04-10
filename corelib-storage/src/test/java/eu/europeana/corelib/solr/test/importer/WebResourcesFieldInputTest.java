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
import eu.europeana.corelib.definitions.jibx.Rights;
import eu.europeana.corelib.definitions.jibx.Rights1;
import eu.europeana.corelib.definitions.jibx.WebResourceType;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.edm.server.importer.util.WebResourcesFieldInput;
import eu.europeana.corelib.mongo.server.EdmMongoServer;
import eu.europeana.corelib.solr.entity.WebResourceImpl;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Web Resource Field Input
 *
 * @author Yorgos.Mamakis@ kb.nl
 */
public class WebResourcesFieldInputTest {

    @SuppressWarnings("unchecked")
    @Test
    public void testWebResource() {
        WebResourceType webResource = new WebResourceType();
        webResource.setAbout("test about");
        Rights1 rights = new Rights1();
        rights.setResource("test resource");
        webResource.setRights(rights);
        List<Rights> rightsList = new ArrayList<>();
        Rights rights1 = new Rights();
        eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource rights1Resource = new eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource();
        rights1Resource.setResource("test rights");
        rights1.setResource(rights1Resource);
        rightsList.add(rights1);
        webResource.setRightList(rightsList);
        WebResourceImpl webResourceImpl = new WebResourceImpl();
        webResourceImpl.setAbout(webResource.getAbout());

        // create mongo
        EdmMongoServer mongoServerMock = mock(EdmMongoServer.class);
        Datastore datastoreMock = mock(Datastore.class);
        Query queryMock = mock(Query.class);
        Key<WebResourceImpl> keyMock = mock(Key.class);

        when(mongoServerMock.getDatastore()).thenReturn(datastoreMock);
        when(datastoreMock.find(WebResourceImpl.class)).thenReturn(queryMock);
        when(datastoreMock.save(webResourceImpl)).thenReturn(keyMock);
        when(queryMock.filter("about", webResource.getAbout())).thenReturn(queryMock);

        WebResourceImpl webResourceMongo = new WebResourcesFieldInput()
                .createWebResourceMongoField(webResource, mongoServerMock);
        assertEquals(webResource.getAbout(), webResourceMongo.getAbout());
        assertEquals(webResource.getRights().getResource(),
                webResourceMongo.getWebResourceEdmRights().values().iterator()
                        .next().get(0));
        assertEquals(webResource.getRightList().get(0).getResource()
                .getResource(), webResourceMongo.getWebResourceDcRights()
                .values().iterator().next().get(0));

        // create solr document
        SolrInputDocument solrDocument = new SolrInputDocument();
        try {
            solrDocument = new WebResourcesFieldInput()
                    .createWebResourceSolrFields(webResource, solrDocument);
            assertEquals(webResource.getAbout(),
                    solrDocument.getFieldValue(EdmLabel.EDM_WEB_RESOURCE
                            .toString()));
            assertEquals(webResource.getRights().getResource(),
                    solrDocument.getFieldValue(EdmLabel.WR_EDM_RIGHTS
                            .toString()));
            assertEquals(
                    webResource.getRightList().get(0).getResource()
                            .getResource(),
                    solrDocument.getFieldValue(EdmLabel.WR_DC_RIGHTS.toString()));

        } catch (InstantiationException | IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
