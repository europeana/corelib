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
import eu.europeana.corelib.definitions.jibx.Aggregation;
import eu.europeana.corelib.definitions.jibx.Country;
import eu.europeana.corelib.definitions.jibx.Creator;
import eu.europeana.corelib.definitions.jibx.EuropeanaAggregationType;
import eu.europeana.corelib.definitions.jibx.HasView;
import eu.europeana.corelib.definitions.jibx.IsShownBy;
import eu.europeana.corelib.definitions.jibx.LandingPage;
import eu.europeana.corelib.definitions.jibx.Language1;
import eu.europeana.corelib.definitions.jibx.Preview;
import eu.europeana.corelib.definitions.jibx.RDF;
import eu.europeana.corelib.definitions.jibx.Rights1;
import eu.europeana.corelib.definitions.jibx.WebResourceType;
import eu.europeana.corelib.definitions.jibx._Object;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.solr.entity.EuropeanaAggregationImpl;
import eu.europeana.corelib.solr.server.EdmMongoServer;
import eu.europeana.corelib.solr.server.importer.util.EuropeanaAggregationFieldInput;
import eu.europeana.corelib.solr.utils.SolrUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/corelib-solr-context.xml", "/corelib-solr-test.xml" })
public class EuropeanaAggregationFieldInputTest {

	@Resource(name = "corelib_solr_mongoServer")
	private EdmMongoServer mongoServer;

	@Test
	public void testAggregation() {
		EuropeanaAggregationType eAggregation = createAggregationJibx();
		RDF rdf = new RDF();
		List<Aggregation> aggregations = new ArrayList<Aggregation>();
		Aggregation aggr = new Aggregation();
		_Object obj = new _Object();
		obj.setResource("test_preview");
		aggr.setObject(obj);
		aggregations.add(aggr);
		rdf.setAggregationList(aggregations);
		List<EuropeanaAggregationType> eAggregations = new ArrayList<EuropeanaAggregationType>();
		eAggregations.add(eAggregation);
		rdf.setEuropeanaAggregationList(eAggregations);
		
		testMongo(rdf);
		testSolr(rdf);
	}

	private void testSolr(RDF rdf) {
		SolrInputDocument solrDocument = new SolrInputDocument();
		try {
			EuropeanaAggregationType eAggregation = rdf.getEuropeanaAggregationList().get(0);
			solrDocument = new EuropeanaAggregationFieldInput().createAggregationSolrFields(eAggregation, solrDocument, SolrUtils.getPreviewUrl(rdf));
			assertEquals(eAggregation.getAbout(),solrDocument.getFieldValue(EdmLabel.EDM_EUROPEANA_AGGREGATION.toString()));
			assertEquals(eAggregation.getAggregatedCHO().getResource(), solrDocument.getFieldValue(EdmLabel.EUROPEANA_AGGREGATION_ORE_AGGREGATEDCHO.toString()));
			assertEquals(eAggregation.getCountry().getString(), solrDocument.getFieldValue(EdmLabel.EUROPEANA_AGGREGATION_EDM_COUNTRY.toString()));
			assertEquals(eAggregation.getHasViewList().get(0).getResource(), solrDocument.getFieldValue(EdmLabel.EUROPEANA_AGGREGATION_EDM_HASVIEW.toString()));
			assertEquals(eAggregation.getCreator().getResource(), solrDocument.getFieldValue(EdmLabel.EUROPEANA_AGGREGATION_DC_CREATOR.toString()));
			assertEquals(eAggregation.getIsShownBy().getResource(), solrDocument.getFieldValue(EdmLabel.EUROPEANA_AGGREGATION_EDM_ISSHOWNBY.toString()));
			assertEquals(eAggregation.getLandingPage().getResource(), solrDocument.getFieldValue(EdmLabel.EUROPEANA_AGGREGATION_EDM_LANDINGPAGE.toString()));
			assertEquals(eAggregation.getLanguage().getString(), solrDocument.getFieldValue(EdmLabel.EUROPEANA_AGGREGATION_EDM_LANGUAGE.toString()));
			assertEquals(eAggregation.getPreview().getResource(), solrDocument.getFieldValue(EdmLabel.EUROPEANA_AGGREGATION_EDM_PREVIEW.toString()));
			assertEquals(eAggregation.getRights().getString(), solrDocument.getFieldValue(EdmLabel.EUROPEANA_AGGREGATION_EDM_RIGHTS.toString()));
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void testMongo(RDF rdf) {
		try {
			EuropeanaAggregationType aggregation = rdf.getEuropeanaAggregationList().get(0);
			EuropeanaAggregationImpl aggregationMongo = new EuropeanaAggregationFieldInput()
					.createAggregationMongoFields(aggregation, mongoServer, SolrUtils.getPreviewUrl(rdf));
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
			assertEquals(rdf.getAggregationList().get(0).getObject().getResource(),
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
		Rights1 rights = new Rights1();
		rights.setString("test rights");
		aggregation.setRights(rights);
		List<Aggregates> aggregatesList = new ArrayList<Aggregates>();
		Aggregates aggregates = new Aggregates();
		aggregates.setResource("test aggregates");
		aggregatesList.add(aggregates);
		aggregation.setAggregateList(aggregatesList);
		Preview preview = new Preview();
		preview.setResource("test_preview");
		aggregation.setPreview(preview);
		
		return aggregation;
	}

	@After
	public void cleanup() {
		mongoServer.getDatastore().getDB().dropDatabase();
	}
}
