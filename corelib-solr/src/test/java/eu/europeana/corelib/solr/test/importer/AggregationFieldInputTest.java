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

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.common.SolrInputDocument;
import org.easymock.EasyMock;
import org.junit.Test;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Key;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;

import eu.europeana.corelib.definitions.jibx.AggregatedCHO;
import eu.europeana.corelib.definitions.jibx.Aggregation;
import eu.europeana.corelib.definitions.jibx.DataProvider;
import eu.europeana.corelib.definitions.jibx.HasView;
import eu.europeana.corelib.definitions.jibx.IsShownAt;
import eu.europeana.corelib.definitions.jibx.IsShownBy;
import eu.europeana.corelib.definitions.jibx.Provider;
import eu.europeana.corelib.definitions.jibx.Rights;
import eu.europeana.corelib.definitions.jibx.Rights1;
import eu.europeana.corelib.definitions.jibx.UGCType;
import eu.europeana.corelib.definitions.jibx.Ugc;
import eu.europeana.corelib.definitions.jibx._Object;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.solr.entity.AggregationImpl;
import eu.europeana.corelib.solr.entity.WebResourceImpl;
import eu.europeana.corelib.solr.server.EdmMongoServer;
import eu.europeana.corelib.solr.server.importer.util.AggregationFieldInput;
import eu.europeana.corelib.solr.utils.MongoUtils;

/**
 * Unit test for Aggregation field creator
 * @author Yorgos.Mamakis@ kb.nl
 *
 */
public class AggregationFieldInputTest {

	private EdmMongoServer mongoServer;

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
					.createAggregationSolrFields(aggregation, solrDocument);
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
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

	}

	private void testMongo(Aggregation aggregation) {
		try {

			mongoServer = EasyMock.createMock(EdmMongoServer.class);
			Datastore ds = EasyMock.createMock(Datastore.class);
			Query query = EasyMock.createMock(Query.class);
			Key<AggregationImpl> key = EasyMock.createMock(Key.class);
			UpdateOperations<AggregationImpl> ups = EasyMock
					.createNiceMock(UpdateOperations.class);
			EasyMock.expect(mongoServer.getDatastore()).andReturn(ds);

			EasyMock.expect(ds.createUpdateOperations(AggregationImpl.class))
					.andReturn(ups);
			EasyMock.expect(
					ups.set("edmDataProvider", MongoUtils
							.createResourceOrLiteralMapFromString(aggregation
									.getDataProvider()))).andReturn(null);
			EasyMock.expect(
					ups.set("edmIsShownAt", aggregation.getIsShownAt()
							.getResource())).andReturn(null);
			EasyMock.expect(
					ups.set("edmIsShownBy", aggregation.getIsShownBy()
							.getResource())).andReturn(null);
			EasyMock.expect(
					ups.set("edmObject", aggregation.getObject().getResource()))
					.andReturn(null);
			EasyMock.expect(
					ups.set("edmProvider", MongoUtils
							.createResourceOrLiteralMapFromString(aggregation
									.getProvider()))).andReturn(null);
			EasyMock.expect(
					ups.set("edmRights", MongoUtils
							.createResourceMapFromString(aggregation
									.getRights()))).andReturn(null);
			EasyMock.expect(
					ups.set("aggregatedCHO", aggregation.getAggregatedCHO()
							.getResource())).andReturn(null);
			EasyMock.expect(
					ups.set("dcRights", MongoUtils
							.createResourceOrLiteralMapFromList(aggregation
									.getRightList()))).andReturn(null);
			
			String[] hasViewArr = new String[]{aggregation.getHasViewList().get(0).getResource()};
			EasyMock.expect(ups.set("hasView", hasViewArr)).andReturn(null);
			EasyMock.expect(mongoServer.getDatastore()).andReturn(ds);
			EasyMock.expect(ds.find(AggregationImpl.class)).andReturn(query);
			EasyMock.expect(query.filter("about", aggregation.getAbout())) .andReturn(query);
			EasyMock.expect(query.get()).andReturn(null);
			 AggregationImpl aggregationImpl = new AggregationImpl();
			 aggregationImpl.setAbout(aggregation.getAbout());
			 EasyMock.expect(mongoServer.getDatastore()).andReturn(ds);
			 EasyMock.expect(ds.save(aggregationImpl)).andReturn(key);
			EasyMock.replay(ups, query, ds, mongoServer);
			AggregationImpl aggregationMongo = new AggregationFieldInput()
					.createAggregationMongoFields(aggregation, mongoServer,
							new ArrayList<WebResourceImpl>());
			assertEquals(aggregation.getAbout(), aggregationMongo.getAbout());
			assertEquals(aggregation.getAggregatedCHO().getResource(),
					aggregationMongo.getAggregatedCHO());
			assertEquals(aggregation.getDataProvider().getString(),
					aggregationMongo.getEdmDataProvider().values().iterator()
							.next().get(0));
			assertEquals(aggregation.getHasViewList().get(0).getResource(),
					aggregationMongo.getHasView()[0]);
			assertEquals(aggregation.getIsShownAt().getResource(),
					aggregationMongo.getEdmIsShownAt());
			assertEquals(aggregation.getIsShownBy().getResource(),
					aggregationMongo.getEdmIsShownBy());
			assertEquals(aggregation.getObject().getResource(),
					aggregationMongo.getEdmObject());
			assertEquals(aggregation.getProvider().getString(),
					aggregationMongo.getEdmProvider().values().iterator()
							.next().get(0));
			assertEquals(aggregation.getUgc().getUgc().toString().toLowerCase(),
					aggregationMongo.getEdmUgc());
			assertEquals(aggregation.getRights().getResource(), aggregationMongo
					.getEdmRights().values().iterator().next().get(0));
			assertEquals(aggregation.getRightList().get(0).getString(),
					aggregationMongo.getDcRights().values().iterator().next()
							.get(0));

		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
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
		List<HasView> hasViewList = new ArrayList<HasView>();
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
		List<Rights> rightsList = new ArrayList<Rights>();
		Rights rights1 = new Rights();
		rights1.setString("test rights 1");
		rightsList.add(rights1);
		aggregation.setRightList(rightsList);
		return aggregation;
	}

	
}
