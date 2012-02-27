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

package eu.europeana.corelib.solr.bean;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.code.morphia.Datastore;

import eu.europeana.corelib.definitions.solr.DocType;
import eu.europeana.corelib.definitions.solr.beans.FullBean;
import eu.europeana.corelib.definitions.solr.entity.Agent;
import eu.europeana.corelib.definitions.solr.entity.Aggregation;
import eu.europeana.corelib.definitions.solr.entity.Concept;
import eu.europeana.corelib.definitions.solr.entity.Place;
import eu.europeana.corelib.definitions.solr.entity.ProvidedCHO;
import eu.europeana.corelib.definitions.solr.entity.Proxy;
import eu.europeana.corelib.definitions.solr.entity.Timespan;
import eu.europeana.corelib.definitions.solr.entity.WebResource;
import eu.europeana.corelib.solr.bean.impl.FullBeanImpl;
import eu.europeana.corelib.solr.entity.AgentImpl;
import eu.europeana.corelib.solr.entity.AggregationImpl;
import eu.europeana.corelib.solr.entity.ConceptImpl;
import eu.europeana.corelib.solr.entity.PlaceImpl;
import eu.europeana.corelib.solr.entity.ProvidedCHOImpl;
import eu.europeana.corelib.solr.entity.ProxyImpl;
import eu.europeana.corelib.solr.entity.TimespanImpl;
import eu.europeana.corelib.solr.entity.WebResourceImpl;
import eu.europeana.corelib.solr.server.MongoDBServer;
import eu.europeana.corelib.solr.service.SearchService;

/**
 * 
 * Unit test for FullBean
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/corelib-solr-context.xml", "/corelib-solr-test.xml" })
public class FullBeanTest {
	
	@Resource(name = "corelib_solr_mongoServer")
	MongoDBServer mongoServer;
	
	Datastore ds;
	
	@Resource(name = "corelib_solr_searchService")
	SearchService searchService;

	@Test
	public void testRetrieve() {
		ds = mongoServer.getDatastore();

		assertNotNull("Error creating datastore", ds);
		Agent agent = createAgent();
		assertNotNull("Error creating agent", agent);
		Timespan timespan = createTimespan();
		assertNotNull("Error creating timespan", timespan);
		Place place = createPlace();
		assertNotNull("Error creating place", place);
		Concept concept = createConcept();
		assertNotNull("Error creating concept", concept);
		Proxy proxy = createProxy();
		assertNotNull("Error creating proxy", proxy);
		WebResource webResource = createWebResource();
		assertNotNull("Error creating web resource", webResource);
		Aggregation aggregation = createAggregation(webResource);
		assertNotNull("Error creating aggregation", aggregation);
		ProvidedCHO providedCHO = createProvidedCHO();
		assertNotNull("Error create providedCHO",providedCHO);
		FullBeanImpl fullBean = new FullBeanImpl();
		ArrayList<Agent> agents = new ArrayList<Agent>();
		agents.add(agent);
		ArrayList<Aggregation> aggregations = new ArrayList<Aggregation>();
		aggregations.add(aggregation);
		ArrayList<Proxy> proxies = new ArrayList<Proxy>();
		proxies.add(proxy);
		ArrayList<Timespan> timespans = new ArrayList<Timespan>();
		timespans.add(timespan);
		ArrayList<Concept> concepts = new ArrayList<Concept>();
		concepts.add(concept);
		ArrayList<Place> places = new ArrayList<Place>();
		places.add(place);
		ArrayList<ProvidedCHO> providedCHOs = new ArrayList<ProvidedCHO>();
		providedCHOs.add(providedCHO);
		fullBean.setAgents(agents);
		fullBean.setAggregations(aggregations);
		fullBean.setConcepts(concepts);
		fullBean.setCreator(new String[] { "test creator" });
		fullBean.setEuropeanaCompleteness(9);
		fullBean.setLanguage(new String[] { "en" });
		fullBean.setPlaces(places);
		fullBean.setProvider(new String[] { "europeana" });
		fullBean.setProxies(proxies);
		fullBean.setTimespans(timespans);
		fullBean.setTitle(new String[] { "test" });
		fullBean.setType(DocType.IMAGE);
		fullBean.setYear(new String[] { "2012" });
		fullBean.setProvidedCHOs(providedCHOs);
		ds.save(fullBean);
		FullBean testFullBean = ds.find(FullBeanImpl.class).get();
		assertEquals(fullBean.getId(), testFullBean.getId());
		assertEquals(fullBean.getAgents(), testFullBean.getAgents());
		assertEquals(fullBean.getAggregations(), testFullBean.getAggregations());
		assertEquals(fullBean.getAggregations().get(0).getWebResources(), testFullBean.getAggregations().get(0)
				.getWebResources());
		assertEquals(fullBean.getConcepts(), testFullBean.getConcepts());
		assertArrayEquals(fullBean.getCreator(), testFullBean.getCreator());
		assertEquals(fullBean.getEuropeanaCompleteness(), testFullBean.getEuropeanaCompleteness());
		assertArrayEquals(fullBean.getLanguage(), testFullBean.getLanguage());
		assertEquals(fullBean.getPlaces(), testFullBean.getPlaces());
		assertArrayEquals(fullBean.getProvider(), testFullBean.getProvider());
		assertEquals(fullBean.getProxies(), testFullBean.getProxies());
		assertEquals(fullBean.getTimespans(), testFullBean.getTimespans());
		assertArrayEquals(fullBean.getTitle(), testFullBean.getTitle());
		assertEquals(fullBean.getType(), testFullBean.getType());
		assertArrayEquals(fullBean.getYear(), testFullBean.getYear());
		assertArrayEquals(fullBean.getEdmWebResource(), testFullBean.getEdmWebResource());
		assertEquals(fullBean.getProvidedCHOs(),testFullBean.getProvidedCHOs());
		FullBean fullBeanSearch = null;
		try {
			fullBeanSearch = searchService.findById(testFullBean.getAbout());
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(fullBean, fullBeanSearch);
	}

	private Aggregation createAggregation(WebResource webResource) {
		Aggregation aggregation = new AggregationImpl();
		ArrayList<WebResource> webResources = new ArrayList<WebResource>();
		webResources.add(webResource);
		aggregation.setWebResources(webResources);
		aggregation.setDcRights(new String[] { "test dc:rights" });
		aggregation.setEdmDataProvider("test edm:dataProvider");
		aggregation.setEdmIsShownAt("test edm:isShownAt");
		aggregation.setEdmIsShownBy("test edm:isShownBy");
		aggregation.setEdmObject("test edm:Object");
		aggregation.setEdmProvider("test edm:provider");
		aggregation.setEdmRights("test edm:rights");
		ds.save(aggregation);
		Aggregation testAggregation = ds.find(AggregationImpl.class).get();
		assertEquals(aggregation, testAggregation);
		assertEquals(aggregation.getWebResources(), testAggregation.getWebResources());
		assertEquals(aggregation.getEdmDataProvider(), testAggregation.getEdmDataProvider());
		assertEquals(aggregation.getEdmIsShownAt(), testAggregation.getEdmIsShownAt());
		assertEquals(aggregation.getEdmIsShownBy(), testAggregation.getEdmIsShownBy());
		assertEquals(aggregation.getEdmObject(), testAggregation.getEdmObject());
		assertEquals(aggregation.getEdmProvider(), testAggregation.getEdmProvider());
		assertEquals(aggregation.getEdmRights(), testAggregation.getEdmRights());
		return aggregation;
	}

	/**
	 * Create and save a Web resource
	 * 
	 * @return
	 */
	private WebResource createWebResource() {
		WebResource webResource = new WebResourceImpl();
		webResource.setWebResource("test web Resource");
		webResource.setWebResourceDcRights(new String[] { "test dc:rights" });
		webResource.setWebResourceEdmRights("test edm:rights");
		ds.save(webResource);
		WebResource testWebResource = ds.find(WebResourceImpl.class).get();
		assertEquals(webResource, testWebResource);
		assertEquals(webResource.getWebResource(), testWebResource.getWebResource());
		assertEquals(webResource.getWebResourceEdmRights(), testWebResource.getWebResourceEdmRights());
		assertArrayEquals(webResource.getWebResourceDcRights(), testWebResource.getWebResourceDcRights());
		return webResource;
	}

	/**
	 * Create and save Concept
	 * 
	 * @return
	 */
	private Concept createConcept() {
		Concept concept = new ConceptImpl();
		concept.setNote(new String[] { "test note" });
		concept.setBroader(new String[] { "test broader" });
		Map<String, String> prefLabel = new HashMap<String, String>();
		prefLabel.put("en", "test prefLabel");
		concept.setPrefLabel(prefLabel);
		Map<String, String> altLabel = new HashMap<String, String>();
		altLabel.put("en", "test altLabel");
		concept.setAltLabel(altLabel);
		ds.save(concept);
		Concept testConcept = ds.find(ConceptImpl.class).get();
		assertEquals(concept, testConcept);
		assertEquals(concept.getAltLabel(), testConcept.getAltLabel());
		assertEquals(concept.getPrefLabel(), testConcept.getPrefLabel());
		assertArrayEquals(concept.getBroader(), testConcept.getBroader());
		assertArrayEquals(concept.getNote(), testConcept.getNote());
		return concept;
	}

	/**
	 * Create and save place
	 * 
	 * @return
	 */
	private Place createPlace() {
		Place place = new PlaceImpl();
		place.setIsPartOf(new String[] { "test isPartOf" });
		place.setLatitude(0f);
		place.setLongitude(0f);
		place.setNote(new String[] { "test note" });
		Map<String, String> prefLabel = new HashMap<String, String>();
		prefLabel.put("en", "test prefLabel");
		place.setPrefLabel(prefLabel);
		Map<String, String> altLabel = new HashMap<String, String>();
		altLabel.put("en", "test altLabel");
		place.setAltLabel(altLabel);
		ds.save(place);
		Place testPlace = ds.find(PlaceImpl.class).get();
		assertEquals(place, testPlace);
		assertArrayEquals(place.getIsPartOf(), testPlace.getIsPartOf());
		assertArrayEquals(place.getNote(), testPlace.getNote());
		assertEquals(place.getLatitude(), testPlace.getLatitude(), 1e-15);
		assertEquals(place.getLongitude(), testPlace.getLongitude(), 1e-15);
		assertEquals(place.getAltLabel(), testPlace.getAltLabel());
		assertEquals(place.getPrefLabel(), testPlace.getPrefLabel());
		return place;
	}

	/**
	 * Create and save timespan
	 * 
	 * @return
	 */
	private Timespan createTimespan() {
		Timespan timespan = new TimespanImpl();
		timespan.setNote(new String[] { "test note" });
		timespan.setBegin("test begin");
		timespan.setEnd("test end");
		Map<String, String> prefLabel = new HashMap<String, String>();
		prefLabel.put("en", "test prefLabel");
		timespan.setPrefLabel(prefLabel);
		Map<String, String> altLabel = new HashMap<String, String>();
		altLabel.put("en", "testAltLabel");
		timespan.setAltLabel(altLabel);
		timespan.setIsPartOf(new String[] { "test isPartOf" });
		ds.save(timespan);
		Timespan testTimespan = ds.find(TimespanImpl.class).get();
		assertEquals(timespan.getAltLabel(), testTimespan.getAltLabel());
		assertEquals(timespan.getPrefLabel(), testTimespan.getPrefLabel());
		assertEquals(timespan.getBegin(), testTimespan.getBegin());
		assertEquals(timespan.getEnd(), testTimespan.getEnd());
		assertArrayEquals(timespan.getIsPartOf(), testTimespan.getIsPartOf());
		return timespan;
	}

	/**
	 * Create and save a proxy
	 * 
	 * @return
	 */
	private Proxy createProxy() {
		Proxy proxy = new ProxyImpl();
		proxy.setDcContributor(new String[] { "test dc:contributor" });
		proxy.setDcCoverage(new String[] { "test dc:coverage" });
		proxy.setDcCreator(new String[] { "test dc:creator" });
		proxy.setDcDate(new String[] { "test date" });
		proxy.setDcDescription(new String[] { "test dc:description" });
		proxy.setDcFormat(new String[] { "test dc:format" });
		proxy.setDcIdentifier(new String[] { "test dc:identifier" });
		proxy.setDcLanguage(new String[] { "test dc:language" });
		proxy.setDcPublisher(new String[] { "test dc:publisher" });
		proxy.setDcRelation(new String[] { "test dc:relation" });
		proxy.setDcRights(new String[] { "test dc:rights" });
		proxy.setDcSource(new String[] { "test dc:source" });
		proxy.setDcSubject(new String[] { "test dc:subject" });
		proxy.setDctermsAlternative(new String[] { "test dcterms:alternative" });
		proxy.setDctermsConformsTo(new String[] { "test dcterms:conformsTo" });
		proxy.setDctermsCreated(new String[] { "test created" });
		proxy.setDctermsExtent(new String[] { "test dcterms:extent" });
		proxy.setDctermsHasFormat(new String[] { "test dcterms:hasFormat" });
		proxy.setDctermsHasPart(new String[] { "test dcterms:hasPart" });
		proxy.setDctermsHasVersion(new String[] { "test dcterms:hasVersion" });
		proxy.setDctermsIsFormatOf(new String[] { "test dcterms:isFormatOf" });
		proxy.setDctermsIsPartOf(new String[] { "test dcterms:isPartOf" });
		proxy.setDctermsIsReferencedBy(new String[] { "test dcterms:isReferencedBy" });
		proxy.setDctermsIsReplacedBy(new String[] { "test dcterms:isReplacedBy" });
		proxy.setDctermsIsRequiredBy(new String[] { "test dcterms:isRequiredBy" });
		proxy.setDctermsIssued(new String[] { "test dcterms:issued" });
		proxy.setDctermsIsVersionOf(new String[] { "test dc:termsIsVersionOf" });
		proxy.setDctermsIsVersionOf(new String[] { "test dcterms:isVersionOf" });
		proxy.setDctermsMedium(new String[] { "test dcterms:medium" });
		proxy.setDctermsProvenance(new String[] { "test dcterms:provenance" });
		proxy.setDctermsReferences(new String[] { "test dcterms:references" });
		proxy.setDctermsReplaces(new String[] { "test dcterms:replaces" });
		proxy.setDctermsRequires(new String[] { "test dcterms:requires" });
		proxy.setDctermsSpatial(new String[] { "test dcterms:spatial" });
		proxy.setDctermsTemporal(new String[] { "test dcterms:temporal" });
		proxy.setDctermsTOC(new String[] { "test dcterms:spatial" });
		proxy.setDcTitle(new String[] { "test dc:title" });
		proxy.setDcType(new String[] { "test dc:type" });
		proxy.setEdmCurrentLocation("test edm:currentLocation");
		proxy.setEdmType(DocType.IMAGE);
		ds.save(proxy);
		Proxy testProxy = ds.find(ProxyImpl.class).get();
		assertEquals(proxy, testProxy);
		assertArrayEquals(proxy.getDcContributor(), testProxy.getDcContributor());
		assertArrayEquals(proxy.getDcCoverage(), testProxy.getDcCoverage());
		assertArrayEquals(proxy.getDcCreator(), testProxy.getDcCreator());
		assertArrayEquals(proxy.getDcDate(), testProxy.getDcDate());
		assertArrayEquals(proxy.getDcDescription(), testProxy.getDcDescription());
		assertArrayEquals(proxy.getDcFormat(), testProxy.getDcFormat());
		assertArrayEquals(proxy.getDcIdentifier(), testProxy.getDcIdentifier());
		assertArrayEquals(proxy.getDcLanguage(), testProxy.getDcLanguage());
		assertArrayEquals(proxy.getDcPublisher(), testProxy.getDcPublisher());
		assertArrayEquals(proxy.getDcRelation(), testProxy.getDcRelation());
		assertArrayEquals(proxy.getDcRights(), testProxy.getDcRights());
		assertArrayEquals(proxy.getDcSource(), testProxy.getDcSource());
		assertArrayEquals(proxy.getDcSubject(), testProxy.getDcSubject());
		assertArrayEquals(proxy.getDctermsAlternative(), testProxy.getDctermsAlternative());
		assertArrayEquals(proxy.getDctermsConformsTo(), testProxy.getDctermsConformsTo());
		assertArrayEquals(proxy.getDctermsCreated(), testProxy.getDctermsCreated());
		assertArrayEquals(proxy.getDctermsExtent(), testProxy.getDctermsExtent());
		assertArrayEquals(proxy.getDctermsHasFormat(), testProxy.getDctermsHasFormat());
		assertArrayEquals(proxy.getDctermsHasPart(), testProxy.getDctermsHasPart());
		assertArrayEquals(proxy.getDctermsHasVersion(), testProxy.getDctermsHasVersion());
		assertArrayEquals(proxy.getDctermsIsFormatOf(), testProxy.getDctermsIsFormatOf());
		assertArrayEquals(proxy.getDctermsIsPartOf(), testProxy.getDctermsIsPartOf());
		assertArrayEquals(proxy.getDctermsIsReferencedBy(), testProxy.getDctermsIsReferencedBy());
		assertArrayEquals(proxy.getDctermsIsReplacedBy(), testProxy.getDctermsIsReplacedBy());
		assertArrayEquals(proxy.getDctermsIsRequiredBy(), testProxy.getDctermsIsRequiredBy());
		assertArrayEquals(proxy.getDctermsIssued(), testProxy.getDctermsIssued());
		assertArrayEquals(proxy.getDctermsIsVersionOf(), testProxy.getDctermsIsVersionOf());
		assertArrayEquals(proxy.getDctermsMedium(), testProxy.getDctermsMedium());
		assertArrayEquals(proxy.getDctermsProvenance(), testProxy.getDctermsProvenance());
		assertArrayEquals(proxy.getDctermsReferences(), testProxy.getDctermsReferences());
		assertArrayEquals(proxy.getDctermsReplaces(), testProxy.getDctermsReplaces());
		assertArrayEquals(proxy.getDctermsRequires(), testProxy.getDctermsRequires());
		assertArrayEquals(proxy.getDctermsSpatial(), testProxy.getDctermsSpatial());
		assertArrayEquals(proxy.getDctermsTemporal(), testProxy.getDctermsTemporal());
		assertArrayEquals(proxy.getDctermsTOC(), testProxy.getDctermsTOC());
		assertArrayEquals(proxy.getDcTitle(), testProxy.getDcTitle());
		assertArrayEquals(proxy.getDcType(), testProxy.getDcType());
		assertEquals(proxy.getEdmCurrentLocation(), testProxy.getEdmCurrentLocation());
		assertEquals(proxy.getEdmType(), testProxy.getEdmType());
		return proxy;
	}

	private ProvidedCHO createProvidedCHO(){
		ProvidedCHO providedCHO = new ProvidedCHOImpl();
		providedCHO.setAbout("test edm:about");
		providedCHO.setEdmIsNextInSequence("test isnextinsequence");
		providedCHO.setOwlSameAs(new String[]{"test owlsameAs"});
		ds.save(providedCHO);
		ProvidedCHO testProvidedCHO = ds.find(ProvidedCHOImpl.class).get();
		assertEquals(providedCHO,testProvidedCHO);
		assertEquals(providedCHO.getAbout(), testProvidedCHO.getAbout());
		assertEquals(providedCHO.getEdmIsNextInSequence(),testProvidedCHO.getEdmIsNextInSequence());
		assertArrayEquals(providedCHO.getOwlSameAs(), testProvidedCHO.getOwlSameAs());
		return providedCHO;
	}
	/**
	 * Create and save an Agent
	 * 
	 * @return the created agent
	 */
	private Agent createAgent() {
		Agent agent = new AgentImpl();
		agent.setNote(new String[] { "test note" });
		Map<String, String> prefLabel = new HashMap<String, String>();
		prefLabel.put("en", "test prefLabel");
		agent.setPrefLabel(prefLabel);
		Map<String, String> altLabel = new HashMap<String, String>();
		altLabel.put("en", "test altLabel");
		agent.setPrefLabel(altLabel);
		
		agent.setBegin("test begin");
		
		agent.setEnd("test end");
		ds.save(agent);
		Agent testAgent = ds.find(AgentImpl.class).get();
		assertEquals(agent, testAgent);
		assertEquals(agent.getAltLabel(), testAgent.getAltLabel());
		assertEquals(agent.getPrefLabel(), testAgent.getPrefLabel());
		assertEquals(agent.getBegin(), testAgent.getBegin());
		assertEquals(agent.getEnd(), testAgent.getEnd());
		return agent;
	}

	@After
	public void cleanup() {
		ds.getDB().dropDatabase();
	}
}
