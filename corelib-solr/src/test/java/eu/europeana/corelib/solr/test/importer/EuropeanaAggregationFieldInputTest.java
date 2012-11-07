package eu.europeana.corelib.solr.test.importer;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.solr.common.SolrInputDocument;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.europeana.corelib.definitions.jibx.AggregatedCHO;
import eu.europeana.corelib.definitions.jibx.Aggregates;
import eu.europeana.corelib.definitions.jibx.Country;
import eu.europeana.corelib.definitions.jibx.Creator;
import eu.europeana.corelib.definitions.jibx.EuropeanaAggregationType;
import eu.europeana.corelib.definitions.jibx.HasView;
import eu.europeana.corelib.definitions.jibx.IsShownBy;
import eu.europeana.corelib.definitions.jibx.LandingPage;
import eu.europeana.corelib.definitions.jibx.Language1;
import eu.europeana.corelib.definitions.jibx.Preview;
import eu.europeana.corelib.definitions.jibx.Rights1;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.solr.entity.EuropeanaAggregationImpl;
import eu.europeana.corelib.solr.server.EdmMongoServer;
import eu.europeana.corelib.solr.server.importer.util.EuropeanaAggregationFieldInput;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/corelib-solr-context.xml", "/corelib-solr-test.xml" })
public class EuropeanaAggregationFieldInputTest {

	@Resource(name = "corelib_solr_mongoServer")
	private EdmMongoServer mongoServer;

	@Test
	public void testAggregation() {
		EuropeanaAggregationType aggregation = createAggregationJibx();
		testMongo(aggregation);
		testSolr(aggregation);
	}

	private void testSolr(EuropeanaAggregationType aggregation) {
		SolrInputDocument solrDocument = new SolrInputDocument();
		try {
			solrDocument = new EuropeanaAggregationFieldInput().createAggregationSolrFields(aggregation, solrDocument);
			assertEquals(aggregation.getAbout(),solrDocument.getFieldValue(EdmLabel.EDM_EUROPEANA_AGGREGATION.toString()));
			assertEquals(aggregation.getAggregatedCHO().getResource(), solrDocument.getFieldValue(EdmLabel.EUROPEANA_AGGREGATION_ORE_AGGREGATEDCHO.toString()));
			assertEquals(aggregation.getCountry().getString(), solrDocument.getFieldValue(EdmLabel.EUROPEANA_AGGREGATION_EDM_COUNTRY.toString()));
			assertEquals(aggregation.getHasViewList().get(0).getResource(), solrDocument.getFieldValue(EdmLabel.EUROPEANA_AGGREGATION_EDM_HASVIEW.toString()));
			assertEquals(aggregation.getCreator().getResource(), solrDocument.getFieldValue(EdmLabel.EUROPEANA_AGGREGATION_DC_CREATOR.toString()));
			assertEquals(aggregation.getIsShownBy().getResource(), solrDocument.getFieldValue(EdmLabel.EUROPEANA_AGGREGATION_EDM_ISSHOWNBY.toString()));
			assertEquals(aggregation.getLandingPage().getResource(), solrDocument.getFieldValue(EdmLabel.EUROPEANA_AGGREGATION_EDM_LANDINGPAGE.toString()));
			assertEquals(aggregation.getLanguage().getString(), solrDocument.getFieldValue(EdmLabel.EUROPEANA_AGGREGATION_EDM_LANGUAGE.toString()));
			assertEquals(aggregation.getPreview().getResource(), solrDocument.getFieldValue(EdmLabel.EUROPEANA_AGGREGATION_EDM_PREVIEW.toString()));
			assertEquals(aggregation.getRights().getString(), solrDocument.getFieldValue(EdmLabel.PROVIDER_AGGREGATION_EDM_RIGHTS.toString()));
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void testMongo(EuropeanaAggregationType aggregation) {
		try {
			EuropeanaAggregationImpl aggregationMongo = new EuropeanaAggregationFieldInput()
					.createAggregationMongoFields(aggregation, mongoServer);
			assertEquals(aggregation.getAbout(), aggregationMongo.getAbout());
			assertEquals(aggregation.getAggregatedCHO().getResource(),
					aggregationMongo.getAggregatedCHO());
			assertEquals(aggregation.getCountry().getString(),
					aggregationMongo.getEdmCountry().values().iterator().next().get(0));
			assertEquals(aggregation.getHasViewList().get(0).getResource(),
					aggregationMongo.getEdmHasView()[0]);
			assertEquals(aggregation.getCreator().getResource(),
					aggregationMongo.getDcCreator().values().iterator().next().get(0));
			assertEquals(aggregation.getIsShownBy().getResource(),
					aggregationMongo.getEdmIsShownBy());
			assertEquals(aggregation.getLandingPage().getResource(),
					aggregationMongo.getEdmLandingPage());
			assertEquals(aggregation.getLanguage().getString(),
					aggregationMongo.getEdmLanguage().values().iterator().next().get(0));
			assertEquals(aggregation.getPreview().getResource(),
					aggregationMongo.getEdmPreview());
			assertEquals(aggregation.getRights().getString(),
					aggregationMongo.getEdmRights().values().iterator().next().get(0));
			assertEquals(aggregation.getAggregateList().get(0).getResource(),
					aggregationMongo.getAggregates()[0]);

		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private EuropeanaAggregationType createAggregationJibx() {
		EuropeanaAggregationType aggregation = new EuropeanaAggregationType();
		aggregation.setAbout("test about");
		AggregatedCHO aggregatedCHO = new AggregatedCHO();
		aggregatedCHO.setResource("test aggregatedCHO");
		aggregation.setAggregatedCHO(aggregatedCHO);
		Country country = new Country();
		country.setString("test county");
		aggregation.setCountry(country);
		List<HasView> hasViewList = new ArrayList<HasView>();
		HasView hasView = new HasView();
		hasView.setResource("test hasView");
		hasViewList.add(hasView);
		aggregation.setHasViewList(hasViewList);
		Creator creator = new Creator();
		creator.setResource("test creator");
		aggregation.setCreator(creator);
		IsShownBy isShownBy = new IsShownBy();
		isShownBy.setResource("test isShownBy");
		aggregation.setIsShownBy(isShownBy);
		LandingPage lp = new LandingPage();
		lp.setResource("test landing page");
		aggregation.setLandingPage(lp);
		Language1 language = new Language1();
		language.setString("test language");
		aggregation.setLanguage(language);
		Preview preview = new Preview();
		preview.setResource("test preview");
		aggregation.setPreview(preview);
		Rights1 rights = new Rights1();
		rights.setString("test rights");
		aggregation.setRights(rights);
		List<Aggregates> aggregatesList = new ArrayList<Aggregates>();
		Aggregates aggregates = new Aggregates();
		aggregates.setResource("test aggregates");
		aggregatesList.add(aggregates);
		aggregation.setAggregateList(aggregatesList);
		return aggregation;
	}

	@After
	public void cleanup() {
		mongoServer.getDatastore().getDB().dropDatabase();
	}
}
