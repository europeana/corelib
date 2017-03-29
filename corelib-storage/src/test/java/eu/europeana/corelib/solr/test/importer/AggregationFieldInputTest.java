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
import org.mongodb.morphia.query.UpdateOperations;
import eu.europeana.corelib.definitions.jibx.*;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.edm.server.importer.util.AggregationFieldInput;
import eu.europeana.corelib.edm.utils.MongoUtils;
import eu.europeana.corelib.mongo.server.EdmMongoServer;
import eu.europeana.corelib.solr.entity.AggregationImpl;
import eu.europeana.corelib.solr.entity.WebResourceImpl;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit test for Aggregation field creator
 *
 * @author Yorgos.Mamakis@ kb.nl
 */
public class AggregationFieldInputTest {

    @Test
    public void testAggregation() {
        Aggregation aggregation = createAggregationJibx();
        testMongo(aggregation);
        testSolr(aggregation);
    }

    private void testSolr(Aggregation aggregation) {
        SolrInputDocument solrDocument = new SolrInputDocument();
        try {
            solrDocument = new AggregationFieldInput()
                    .createAggregationSolrFields(aggregation, solrDocument, new ArrayList<License>());
            assertEquals(
                    aggregation.getAbout(),
                    solrDocument
                            .getFieldValue(EdmLabel.PROVIDER_AGGREGATION_ORE_AGGREGATION
                                    .toString()));
            assertEquals(
                    aggregation.getAggregatedCHO().getResource(),
                    solrDocument
                            .getFieldValue(EdmLabel.PROVIDER_AGGREGATION_EDM_AGGREGATED_CHO
                                    .toString()));
            assertEquals(
                    aggregation.getDataProvider().getString(),
                    solrDocument
                            .getFieldValue(EdmLabel.PROVIDER_AGGREGATION_EDM_DATA_PROVIDER
                                    .toString()));
            assertEquals(
                    aggregation.getHasViewList().get(0).getResource(),
                    solrDocument
                            .getFieldValue(EdmLabel.PROVIDER_AGGREGATION_EDM_HASVIEW
                                    .toString()));
            assertEquals(
                    aggregation.getIsShownAt().getResource(),
                    solrDocument
                            .getFieldValue(EdmLabel.PROVIDER_AGGREGATION_EDM_IS_SHOWN_AT
                                    .toString()));
            assertEquals(
                    aggregation.getIsShownBy().getResource(),
                    solrDocument
                            .getFieldValue(EdmLabel.PROVIDER_AGGREGATION_EDM_IS_SHOWN_BY
                                    .toString()));
            assertEquals(
                    aggregation.getObject().getResource(),
                    solrDocument
                            .getFieldValue(EdmLabel.PROVIDER_AGGREGATION_EDM_OBJECT
                                    .toString()));
            assertEquals(
                    aggregation.getProvider().getString(),
                    solrDocument
                            .getFieldValue(EdmLabel.PROVIDER_AGGREGATION_EDM_PROVIDER
                                    .toString()));
            assertEquals(aggregation.getUgc().getUgc().toString().toLowerCase(),
                    solrDocument.getFieldValue(EdmLabel.EDM_UGC.toString()).toString());
            assertEquals(
                    aggregation.getRights().getResource(),
                    solrDocument
                            .getFieldValue(EdmLabel.PROVIDER_AGGREGATION_EDM_RIGHTS
                                    .toString()));
            assertEquals(
                    aggregation.getRightList().get(0).getString(),
                    solrDocument.getFieldValues(
                            EdmLabel.PROVIDER_AGGREGATION_DC_RIGHTS.toString())
                            .toArray()[0].toString());
            assertEquals(aggregation.getIntermediateProviderList().get(0).getResource().getResource(),
                    solrDocument.getFieldValues(EdmLabel.PROVIDER_AGGREGATION_EDM_INTERMEDIATE_PROVIDER.toString()).
                            toArray()[0].toString());
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    @SuppressWarnings("unchecked")
    private void testMongo(Aggregation aggregation) {
        try {
            AggregationImpl aggregationImpl = new AggregationImpl();
            aggregationImpl.setAbout(aggregation.getAbout());
            String[] hasViewArr = new String[]{aggregation.getHasViewList().get(0).getResource()};

            EdmMongoServer mongoServer = mock(EdmMongoServer.class);
            Datastore ds = mock(Datastore.class);
            Query query = mock(Query.class);
            Key<AggregationImpl> key = mock(Key.class);
            UpdateOperations<AggregationImpl> ups = mock(UpdateOperations.class);

            when(mongoServer.getDatastore()).thenReturn(ds);
            when(ds.createUpdateOperations(AggregationImpl.class)).thenReturn(ups);
            when(ds.find(AggregationImpl.class)).thenReturn(query);
            when(ds.save(aggregationImpl)).thenReturn(key);

            when(ups.set("edmDataProvider",
                            MongoUtils.createResourceOrLiteralMapFromString(aggregation.getDataProvider()))
            ).thenReturn(null);
            when(ups.set("edmIsShownAt", aggregation.getIsShownAt().getResource())).thenReturn(null);
            when(ups.set("edmIsShownBy", aggregation.getIsShownBy().getResource())).thenReturn(null);
            when(ups.set("edmObject", aggregation.getObject().getResource())).thenReturn(null);
            when(ups.set("edmProvider",
                            MongoUtils.createResourceOrLiteralMapFromString(aggregation.getProvider()))
            ).thenReturn(null);
            when(ups.set("edmRights",
                            MongoUtils.createResourceMapFromString(aggregation.getRights()))
            ).thenReturn(null);
            when(ups.set("aggregatedCHO", aggregation.getAggregatedCHO().getResource())).thenReturn(null);
            when(ups.set("dcRights",
                            MongoUtils.createResourceOrLiteralMapFromList(aggregation.getRightList()))
            ).thenReturn(null);
            when(ups.set("hasView", hasViewArr)).thenReturn(null);

            when(query.filter("about", aggregation.getAbout())).thenReturn(query);
            when(query.get()).thenReturn(null);

            AggregationImpl aggregationMongo = new AggregationFieldInput()
                    .createAggregationMongoFields(aggregation, mongoServer, new ArrayList<WebResourceImpl>());
            assertEquals(aggregation.getAbout(), aggregationMongo.getAbout());
            assertEquals(aggregation.getAggregatedCHO().getResource(), aggregationMongo.getAggregatedCHO());
            assertEquals(aggregation.getDataProvider().getString(),
                    aggregationMongo.getEdmDataProvider().values().iterator().next().get(0));
            assertEquals(aggregation.getHasViewList().get(0).getResource(), aggregationMongo.getHasView()[0]);
            assertEquals(aggregation.getIsShownAt().getResource(), aggregationMongo.getEdmIsShownAt());
            assertEquals(aggregation.getIsShownBy().getResource(), aggregationMongo.getEdmIsShownBy());
            assertEquals(aggregation.getObject().getResource(), aggregationMongo.getEdmObject());
            assertEquals(aggregation.getProvider().getString(),
                    aggregationMongo.getEdmProvider().values().iterator().next().get(0));
            assertEquals(aggregation.getUgc().getUgc().toString().toLowerCase(), aggregationMongo.getEdmUgc());
            assertEquals(aggregation.getRights().getResource(),
                    aggregationMongo.getEdmRights().values().iterator().next().get(0));
            assertEquals(aggregation.getRightList().get(0).getString(),
                    aggregationMongo.getDcRights().values().iterator().next().get(0));

        } catch (InstantiationException | IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private Aggregation createAggregationJibx() {
        Aggregation aggregation = new Aggregation();
        aggregation.setAbout("test about");
        AggregatedCHO aggregatedCHO = new AggregatedCHO();
        aggregatedCHO.setResource("test aggregatedCHO");
        aggregation.setAggregatedCHO(aggregatedCHO);
        DataProvider dataProvider = new DataProvider();
        dataProvider.setString("test data provider");
        aggregation.setDataProvider(dataProvider);
        List<HasView> hasViewList = new ArrayList<>();
        HasView hasView = new HasView();
        hasView.setResource("test hasView");
        hasViewList.add(hasView);
        aggregation.setHasViewList(hasViewList);
        IsShownAt isShownAt = new IsShownAt();
        isShownAt.setResource("test isShownAt");
        aggregation.setIsShownAt(isShownAt);
        IsShownBy isShownBy = new IsShownBy();
        isShownBy.setResource("test isShownBy");
        aggregation.setIsShownBy(isShownBy);
        _Object object = new _Object();
        object.setResource("test object");
        aggregation.setObject(object);
        Provider provider = new Provider();
        provider.setString("test resource");
        aggregation.setProvider(provider);
        Ugc ugc = new Ugc();
        ugc.setUgc(UGCType.TRUE);
        aggregation.setUgc(ugc);
        Rights1 rights = new Rights1();
        rights.setResource("test rights");
        aggregation.setRights(rights);
        List<Rights> rightsList = new ArrayList<>();
        Rights rights1 = new Rights();
        rights1.setString("test rights 1");
        rightsList.add(rights1);
        aggregation.setRightList(rightsList);
        IntermediateProvider prov = new IntermediateProvider();

        ResourceOrLiteralType.Resource res = new ResourceOrLiteralType.Resource();
        res.setResource("http://test");
        prov.setResource(res);
        List<IntermediateProvider> provs = new ArrayList<>();
        provs.add(prov);
        aggregation.setIntermediateProviderList(provs);
        return aggregation;
    }


}
