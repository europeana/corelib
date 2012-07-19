package eu.europeana.corelib.solr.test.importer;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.solr.common.SolrInputDocument;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
import eu.europeana.corelib.solr.server.EdmMongoServer;
import eu.europeana.corelib.solr.server.importer.util.AggregationFieldInput;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/corelib-solr-context.xml", "/corelib-solr-test.xml" })
public class AggregationFieldInputTest {

	@Resource(name = "corelib_solr_mongoServer")
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
			solrDocument = AggregationFieldInput.createAggregationSolrFields(aggregation, solrDocument);
			assertEquals(aggregation.getAbout(),solrDocument.getFieldValue(EdmLabel.PROVIDER_AGGREGATION_ORE_AGGREGATION.toString()));
			assertEquals(aggregation.getAggregatedCHO().getResource(), solrDocument.getFieldValue(EdmLabel.PROVIDER_AGGREGATION_EDM_AGGREGATED_CHO.toString()));
			assertEquals(aggregation.getDataProvider().getString(), solrDocument.getFieldValue(EdmLabel.PROVIDER_AGGREGATION_EDM_DATA_PROVIDER.toString()));
			assertEquals(aggregation.getHasViewList().get(0).getResource(), solrDocument.getFieldValue(EdmLabel.PROVIDER_AGGREGATION_EDM_HASVIEW.toString()));
			assertEquals(aggregation.getIsShownAt().getResource(), solrDocument.getFieldValue(EdmLabel.PROVIDER_AGGREGATION_EDM_IS_SHOWN_AT.toString()));
			assertEquals(aggregation.getIsShownBy().getResource(), solrDocument.getFieldValue(EdmLabel.PROVIDER_AGGREGATION_EDM_IS_SHOWN_BY.toString()));
			assertEquals(aggregation.getObject().getResource(), solrDocument.getFieldValue(EdmLabel.PROVIDER_AGGREGATION_EDM_OBJECT.toString()));
			assertEquals(aggregation.getProvider().getString(), solrDocument.getFieldValue(EdmLabel.PROVIDER_AGGREGATION_EDM_PROVIDER.toString()));
			assertEquals(aggregation.getUgc().getUgc().toString(), solrDocument.getFieldValue(EdmLabel.EDM_UGC.toString()));
			assertEquals(aggregation.getRights().getString(), solrDocument.getFieldValue(EdmLabel.PROVIDER_AGGREGATION_EDM_RIGHTS.toString()));
			assertEquals(aggregation.getRightList().get(0).getString(), solrDocument.getFieldValues(EdmLabel.PROVIDER_AGGREGATION_DC_RIGHTS.toString()).toArray()[0].toString());
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void testMongo(Aggregation aggregation) {
		try {
			AggregationImpl aggregationMongo = AggregationFieldInput
					.createAggregationMongoFields(aggregation, mongoServer, null);
			assertEquals(aggregation.getAbout(), aggregationMongo.getAbout());
			assertEquals(aggregation.getAggregatedCHO().getResource(),
					aggregationMongo.getAggregatedCHO());
			assertEquals(aggregation.getDataProvider().getString(),
					aggregationMongo.getEdmDataProvider());
			assertEquals(aggregation.getHasViewList().get(0).getResource(),
					aggregationMongo.getHasView()[0]);
			assertEquals(aggregation.getIsShownAt().getResource(),
					aggregationMongo.getEdmIsShownAt());
			assertEquals(aggregation.getIsShownBy().getResource(),
					aggregationMongo.getEdmIsShownBy());
			assertEquals(aggregation.getObject().getResource(),
					aggregationMongo.getEdmObject());
			assertEquals(aggregation.getProvider().getString(),
					aggregationMongo.getEdmProvider());
			assertEquals(aggregation.getUgc().getUgc().toString(),
					aggregationMongo.getEdmUgc());
			assertEquals(aggregation.getRights().getString(),
					aggregationMongo.getEdmRights());
			assertEquals(aggregation.getRightList().get(0).getString(),
					aggregationMongo.getDcRights()[0]);

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
		rights.setString("test rights");
		aggregation.setRights(rights);
		List<Rights> rightsList = new ArrayList<Rights>();
		Rights rights1 = new Rights();
		rights1.setString("test rights 1");
		rightsList.add(rights1);
		aggregation.setRightList(rightsList);
		return aggregation;
	}

	@After
	public void cleanup() {
		mongoServer.getDatastore().getDB().dropDatabase();
	}
}
