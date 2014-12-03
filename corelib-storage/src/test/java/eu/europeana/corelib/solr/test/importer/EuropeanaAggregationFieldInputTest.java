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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
import eu.europeana.corelib.definitions.jibx.Aggregates;
import eu.europeana.corelib.definitions.jibx.Aggregation;
import eu.europeana.corelib.definitions.jibx.Country;
import eu.europeana.corelib.definitions.jibx.CountryCodes;
import eu.europeana.corelib.definitions.jibx.Creator;
import eu.europeana.corelib.definitions.jibx.EdmType;
import eu.europeana.corelib.definitions.jibx.EuropeanaAggregationType;
import eu.europeana.corelib.definitions.jibx.HasView;
import eu.europeana.corelib.definitions.jibx.IsShownBy;
import eu.europeana.corelib.definitions.jibx.LandingPage;
import eu.europeana.corelib.definitions.jibx.Language1;
import eu.europeana.corelib.definitions.jibx.LanguageCodes;
import eu.europeana.corelib.definitions.jibx.Preview;
import eu.europeana.corelib.definitions.jibx.ProxyType;
import eu.europeana.corelib.definitions.jibx.RDF;
import eu.europeana.corelib.definitions.jibx.Rights1;
import eu.europeana.corelib.definitions.jibx.Type1;
import eu.europeana.corelib.definitions.jibx._Object;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.edm.server.importer.util.EuropeanaAggregationFieldInput;
import eu.europeana.corelib.edm.utils.SolrUtils;
import eu.europeana.corelib.mongo.server.EdmMongoServer;
import eu.europeana.corelib.solr.entity.EuropeanaAggregationImpl;

/**
 * EuropeanaAggregation Field Input Creator
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
public class EuropeanaAggregationFieldInputTest {

	private EdmMongoServer mongoServer;
	private final static String EUROPEANA_URI = "http://www.europeana.eu/portal/record";
	private final static String EDM_PREVIEW_PREFIX = "http://europeanastatic.eu/api/image?uri=";
	private final static String EDM_PREVIEW_SUFFIX = "&size=LARGE&type=TEXT";

	@Test
	public void testAggregation() {
		EuropeanaAggregationType eAggregation = createAggregationJibx();
		ProxyType proxy = new ProxyType();
		Type1 type = new Type1();
		type.setType(EdmType.TEXT);
		proxy.setType(type);
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
			EuropeanaAggregationType eAggregation = rdf
					.getEuropeanaAggregationList().get(0);
			solrDocument = new EuropeanaAggregationFieldInput()
					.createAggregationSolrFields(eAggregation, solrDocument,
							SolrUtils.getPreviewUrl(rdf));
			
			assertEquals(eAggregation.getAbout(),
					solrDocument
							.getFieldValue(EdmLabel.EDM_EUROPEANA_AGGREGATION
									.toString()));
			assertEquals(
					eAggregation.getAggregatedCHO().getResource(),
					solrDocument
							.getFieldValue(EdmLabel.EUROPEANA_AGGREGATION_ORE_AGGREGATEDCHO
									.toString()));
			assertEquals(
					eAggregation.getCountry().getCountry().xmlValue().toLowerCase(),
					solrDocument
							.getFieldValue(EdmLabel.EUROPEANA_AGGREGATION_EDM_COUNTRY
									.toString()));
			assertEquals(
					eAggregation.getHasViewList().get(0).getResource(),
					solrDocument
							.getFieldValue(EdmLabel.EUROPEANA_AGGREGATION_EDM_HASVIEW
									.toString()));
			assertEquals(
					eAggregation.getCreator().getResource().getResource(),
					solrDocument
							.getFieldValue(EdmLabel.EUROPEANA_AGGREGATION_DC_CREATOR
									.toString()));
			assertEquals(
					eAggregation.getIsShownBy().getResource(),
					solrDocument
							.getFieldValue(EdmLabel.EUROPEANA_AGGREGATION_EDM_ISSHOWNBY
									.toString()));
			assertEquals(
					"http://www.europeana.eu/portal/recordtest aggregatedCHO.html",
					solrDocument
							.getFieldValue(EdmLabel.EUROPEANA_AGGREGATION_EDM_LANDINGPAGE
									.toString()));
			assertEquals(
					eAggregation.getLanguage().getLanguage().xmlValue(),
					solrDocument
							.getFieldValue(EdmLabel.EUROPEANA_AGGREGATION_EDM_LANGUAGE
									.toString()));
			assertEquals(
					eAggregation.getRights().getResource(),
					solrDocument
							.getFieldValue(EdmLabel.EUROPEANA_AGGREGATION_EDM_RIGHTS
									.toString()));
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
			EuropeanaAggregationImpl aggregationMongo = new EuropeanaAggregationImpl();
			EuropeanaAggregationType aggregation = rdf
					.getEuropeanaAggregationList().get(0);
			aggregationMongo.setAbout(aggregation.getAbout());
			aggregationMongo.setEdmPreview(SolrUtils.getPreviewUrl(rdf));
			mongoServer = EasyMock.createMock(EdmMongoServer.class);
			Datastore ds = EasyMock.createMock(Datastore.class);
			Query query = EasyMock.createMock(Query.class);
			Key<EuropeanaAggregationImpl> key = EasyMock.createMock(Key.class);
			UpdateOperations<EuropeanaAggregationImpl> ups = EasyMock
					.createNiceMock(UpdateOperations.class);
			EasyMock.expect(mongoServer.getDatastore()).andReturn(ds);
			EasyMock.expect(
					ds.createUpdateOperations(EuropeanaAggregationImpl.class))
					.andReturn(ups);
			EasyMock.expect(mongoServer.getDatastore()).andReturn(ds);
			EasyMock.expect(ds.find(EuropeanaAggregationImpl.class)).andReturn(
					query);
			EasyMock.expect(query.filter("about", aggregation.getAbout()))
					.andReturn(query);
			EasyMock.expect(query.get()).andReturn(null);
			
			EasyMock.expect(mongoServer.getDatastore()).andReturn(ds);
			
			
			EasyMock.expect(ds.save(EasyMock.anyObject(EuropeanaAggregationImpl.class))).andReturn(key);			
			EasyMock.replay(ups,query, ds, mongoServer);
			aggregationMongo = new EuropeanaAggregationFieldInput()
					.createAggregationMongoFields(aggregation, mongoServer,
							SolrUtils.getPreviewUrl(rdf));
			assertEquals(aggregation.getAbout(), aggregationMongo.getAbout());
			assertEquals(aggregation.getAggregatedCHO().getResource(),
					aggregationMongo.getAggregatedCHO());
			assertEquals(aggregation.getCountry().getCountry().xmlValue().toLowerCase(),
					aggregationMongo.getEdmCountry().values().iterator().next()
							.get(0));
			assertEquals(aggregation.getHasViewList().get(0).getResource(),
					aggregationMongo.getEdmHasView()[0]);
			assertEquals(aggregation.getCreator().getResource().getResource(),
					aggregationMongo.getDcCreator().values().iterator().next()
							.get(0));
			assertEquals(aggregation.getIsShownBy().getResource(),
					aggregationMongo.getEdmIsShownBy());
			assertEquals("http://europeana.eu/portal/record/.html",
					aggregationMongo.getEdmLandingPage());
			assertEquals(aggregation.getLanguage().getLanguage().xmlValue(),
					aggregationMongo.getEdmLanguage().values().iterator()
							.next().get(0));
			assertEquals(aggregation.getRights().getResource(), aggregationMongo
					.getEdmRights().values().iterator().next().get(0));
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
		country.setCountry(CountryCodes.EUROPE);
		aggregation.setCountry(country);
		List<HasView> hasViewList = new ArrayList<HasView>();
		HasView hasView = new HasView();
		hasView.setResource("test hasView");
		hasViewList.add(hasView);
		aggregation.setHasViewList(hasViewList);
		Creator creator = new Creator();

		eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource crResource = new eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource();
		crResource.setResource("test creator");
		creator.setResource(crResource);

		aggregation.setCreator(creator);
		IsShownBy isShownBy = new IsShownBy();
		isShownBy.setResource("test isShownBy");
		aggregation.setIsShownBy(isShownBy);
		LandingPage lp = new LandingPage();
		lp.setResource("test landing page");
		aggregation.setLandingPage(lp);
		Language1 language = new Language1();
		language.setLanguage(LanguageCodes.EN);
		aggregation.setLanguage(language);
		Rights1 rights = new Rights1();
		rights.setResource("test rights");
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
	private String generateEdmPreview(String url) {
		try {
			return EDM_PREVIEW_PREFIX+URLEncoder.encode(url, "UTF-8").replaceAll("\\%28", "(")
					.replaceAll("\\%29", ")").replaceAll("\\+", "%20")
					.replaceAll("\\%27", "'").replaceAll("\\%21", "!")
					.replaceAll("\\%7E", "~")+EDM_PREVIEW_SUFFIX;
		} catch (UnsupportedEncodingException e) {
			

		}
		return null;
	}
}
