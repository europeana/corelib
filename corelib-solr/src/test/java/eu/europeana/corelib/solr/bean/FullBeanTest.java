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
import com.google.code.morphia.Key;

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
import eu.europeana.corelib.solr.server.EdmMongoServer;

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
	private EdmMongoServer mongoServer;
	
	private Datastore ds;

	@Test
	public void testRetrieve() {
		
		assertNotNull("Error getting server",mongoServer);
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
		fullBean.setAbout("test about");
		fullBean.setAggregations(aggregations);
		fullBean.setConcepts(concepts);
		
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
		Key<FullBeanImpl> fullBeanKey = ds.save(fullBean);
		FullBean testFullBean = ds.find(FullBeanImpl.class).filter("_id", fullBeanKey.getId()).get();
		assertEquals(fullBean.getId(), testFullBean.getId());
		assertEquals(fullBean.getAbout(),testFullBean.getAbout());
		assertEquals(fullBean.getAgents(), testFullBean.getAgents());
		assertEquals(fullBean.getAggregations(), testFullBean.getAggregations());
		assertEquals(fullBean.getAggregations().get(0).getWebResources(), testFullBean.getAggregations().get(0)
				.getWebResources());
		assertEquals(fullBean.getConcepts(), testFullBean.getConcepts());
		
		assertEquals(fullBean.getEuropeanaCompleteness(), testFullBean.getEuropeanaCompleteness());
		assertArrayEquals(fullBean.getLanguage(), testFullBean.getLanguage());
		assertEquals(fullBean.getPlaces(), testFullBean.getPlaces());
		assertArrayEquals(fullBean.getProvider(), testFullBean.getProvider());
		assertEquals(fullBean.getProxies(), testFullBean.getProxies());
		assertEquals(fullBean.getTimespans(), testFullBean.getTimespans());
		assertArrayEquals(fullBean.getTitle(), testFullBean.getTitle());
		assertEquals(fullBean.getType(), testFullBean.getType());
		assertArrayEquals(fullBean.getYear(), testFullBean.getYear());
		assertEquals(fullBean.getProvidedCHOs(),testFullBean.getProvidedCHOs());
		
		
	}

	private Aggregation createAggregation(WebResource webResource) {
		Aggregation aggregation = new AggregationImpl();
		aggregation.setAbout("test about");
		ArrayList<WebResource> webResources = new ArrayList<WebResource>();
		webResources.add(webResource);
		aggregation.setWebResources(webResources);
		Map<String,String> dcRights = new HashMap<String,String>();
		dcRights.put("en", "test dc:rights");
		aggregation.setDcRights(dcRights);
		Map<String,String> dataProvider = new HashMap<String,String>();
		dataProvider.put("en", "test edm:dataProvider");
		aggregation.setEdmDataProvider(dataProvider);
		aggregation.setEdmIsShownAt("test edm:isShownAt");
		aggregation.setEdmIsShownBy("test edm:isShownBy");
		aggregation.setEdmObject("test edm:Object");
		Map<String,String> provider = new HashMap<String,String>();
		provider.put("en", "test edm:provider");
		aggregation.setEdmProvider(provider);
		Map<String,String> edmRights = new HashMap<String,String>();
		edmRights.put("en", "test edm:rights");
		aggregation.setEdmRights(edmRights);
		Key<Aggregation> aggregationKey = ds.save(aggregation);
		Aggregation testAggregation = ds.find(AggregationImpl.class).filter("about", aggregation.getAbout()).get();
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
		webResource.setAbout("test web Resource");
		Map<String,String> dcRights = new HashMap<String,String>();
		dcRights.put("en", "test dc:rights");
		webResource.setWebResourceDcRights(dcRights);
		Map<String,String> edmRights = new HashMap<String,String>();
		edmRights.put("en", "test edm:rights");
		webResource.setWebResourceEdmRights(edmRights);
		Key<WebResource> webResourceKey = ds.save(webResource);
		WebResource testWebResource = ds.find(WebResourceImpl.class).filter("about", webResource.getAbout()).get();
		assertEquals(webResource, testWebResource);
		assertEquals(webResource.getAbout(), testWebResource.getAbout());
		assertEquals(webResource.getWebResourceEdmRights(), testWebResource.getWebResourceEdmRights());
		assertEquals(webResource.getWebResourceDcRights(), testWebResource.getWebResourceDcRights());
		return webResource;
	}

	/**
	 * Create and save Concept
	 * 
	 * @return
	 */
	private Concept createConcept() {
		Concept concept = new ConceptImpl();
		concept.setAbout("about");
		Map<String,String> note = new HashMap<String,String>();
		note.put("en", "test note");
		concept.setNote(note);
		concept.setBroader(new String[] { "test broader" });
		Map<String, String> prefLabel = new HashMap<String, String>();
		prefLabel.put("en", "test prefLabel");
		concept.setPrefLabel(prefLabel);
		Map<String, String> altLabel = new HashMap<String, String>();
		altLabel.put("en", "test altLabel");
		concept.setAltLabel(altLabel);
		Key<Concept>conceptKey = ds.save(concept);
		Concept testConcept = ds.find(ConceptImpl.class).filter("about", concept.getAbout()).get();
		assertEquals(concept, testConcept);
		assertEquals(concept.getAltLabel(), testConcept.getAltLabel());
		assertEquals(concept.getPrefLabel(), testConcept.getPrefLabel());
		assertArrayEquals(concept.getBroader(), testConcept.getBroader());
		assertEquals(concept.getNote(), testConcept.getNote());
		return concept;
	}

	/**
	 * Create and save place
	 * 
	 * @return
	 */
	private Place createPlace() {
		Place place = new PlaceImpl();
		place.setAbout("test about");
		Map<String,String> isPartOf = new HashMap<String,String>();
		isPartOf.put("en", "test isPartOf");
		place.setIsPartOf(isPartOf);
		place.setLatitude(0f);
		place.setLongitude(0f);
		Map<String,String> note = new HashMap<String,String>();
		note.put("en", "test note");
		place.setNote(note);
		Map<String, String> prefLabel = new HashMap<String, String>();
		prefLabel.put("en", "test prefLabel");
		place.setPrefLabel(prefLabel);
		Map<String, String> altLabel = new HashMap<String, String>();
		altLabel.put("en", "test altLabel");
		place.setAltLabel(altLabel);
		Key<Place>placeKey  = ds.save(place);
		Place testPlace = ds.find(PlaceImpl.class).filter("about", place.getAbout()).get();
		assertEquals(place, testPlace);
		assertEquals(place.getIsPartOf(), testPlace.getIsPartOf());
		assertEquals(place.getNote(), testPlace.getNote());
		assertEquals(place.getLatitude(), testPlace.getLatitude());
		assertEquals(place.getLongitude(), testPlace.getLongitude());
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
		timespan.setAbout("test about");
		Map<String,String> note = new HashMap<String,String>();
		note.put("en", "test note");
		timespan.setNote(note);
		Map<String,String> begin = new HashMap<String,String>();
		begin.put("en", "test begin");
		timespan.setBegin(begin);
		Map<String,String> end = new HashMap<String,String>();
		end.put("en", "test end");
		timespan.setEnd(end);
		Map<String, String> prefLabel = new HashMap<String, String>();
		prefLabel.put("en", "test prefLabel");
		timespan.setPrefLabel(prefLabel);
		Map<String, String> altLabel = new HashMap<String, String>();
		altLabel.put("en", "testAltLabel");
		timespan.setAltLabel(altLabel);
		Map<String,String> isPartOf = new HashMap<String,String>();
		isPartOf.put("en", "test isPartOf");
		timespan.setIsPartOf(isPartOf);
		Key<Timespan> timespanKey = ds.save(timespan);
		Timespan testTimespan = ds.find(TimespanImpl.class).filter("about",timespan.getAbout()).get();
		assertEquals(timespan.getAltLabel(), testTimespan.getAltLabel());
		assertEquals(timespan.getPrefLabel(), testTimespan.getPrefLabel());
		assertEquals(timespan.getBegin(), testTimespan.getBegin());
		assertEquals(timespan.getEnd(), testTimespan.getEnd());
		assertEquals(timespan.getIsPartOf(), testTimespan.getIsPartOf());
		return timespan;
	}

	/**
	 * Create and save a proxy
	 * 
	 * @return
	 */
	private Proxy createProxy() {
		Proxy proxy = new ProxyImpl();
		proxy.setAbout("about");
		Map<String,String> map = new HashMap<String, String>();
		map.put("en","test");
		proxy.setDcContributor(map);
		proxy.setDcCoverage(map);
		proxy.setDcCreator(map);
		proxy.setDcDate(map);
		proxy.setDcDescription(map);
		proxy.setDcFormat(map);
		proxy.setDcIdentifier(map);
		proxy.setDcLanguage(map);
		proxy.setDcPublisher(map);
		proxy.setDcRelation(map);
		proxy.setDcRights(map);
		proxy.setDcSource(map);
		proxy.setDcSubject(map);
		proxy.setDctermsAlternative(map);
		proxy.setDctermsConformsTo(map);
		proxy.setDctermsCreated(map);
		proxy.setDctermsExtent(map);
		proxy.setDctermsHasFormat(map);
		proxy.setDctermsHasPart(map);
		proxy.setDctermsHasVersion(map);
		proxy.setDctermsIsFormatOf(map);
		proxy.setDctermsIsPartOf(map);
		proxy.setDctermsIsReferencedBy(map);
		proxy.setDctermsIsReplacedBy(map);
		proxy.setDctermsIsRequiredBy(map);
		proxy.setDctermsIssued(map);
		proxy.setDctermsIsVersionOf(map);
		proxy.setDctermsMedium(map);
		proxy.setDctermsProvenance(map);
		proxy.setDctermsReferences(map);
		proxy.setDctermsReplaces(map);
		proxy.setDctermsRequires(map);
		proxy.setDctermsSpatial(map);
		proxy.setDctermsTemporal(map);
		proxy.setDctermsTOC(map);
		proxy.setDcTitle(map);
		proxy.setDcType(map);
		proxy.setEdmIsNextInSequence("test isnextinsequence");
		proxy.setEdmCurrentLocation("test edm:currentLocation");
		proxy.setEdmType(DocType.IMAGE);
		Key<Proxy> proxyKey = ds.save(proxy);
		Proxy testProxy = ds.find(ProxyImpl.class).filter("about", proxy.getAbout()).get();
		assertEquals(proxy, testProxy);
		
		assertEquals(proxy.getDcContributor(), testProxy.getDcContributor());
		assertEquals(proxy.getDcCoverage(), testProxy.getDcCoverage());
		assertEquals(proxy.getDcCreator(), testProxy.getDcCreator());
		assertEquals(proxy.getDcDate(), testProxy.getDcDate());
		assertEquals(proxy.getDcDescription(), testProxy.getDcDescription());
		assertEquals(proxy.getDcFormat(), testProxy.getDcFormat());
		assertEquals(proxy.getDcIdentifier(), testProxy.getDcIdentifier());
		assertEquals(proxy.getDcLanguage(), testProxy.getDcLanguage());
		assertEquals(proxy.getDcPublisher(), testProxy.getDcPublisher());
		assertEquals(proxy.getDcRelation(), testProxy.getDcRelation());
		assertEquals(proxy.getDcRights(), testProxy.getDcRights());
		assertEquals(proxy.getDcSource(), testProxy.getDcSource());
		assertEquals(proxy.getDcSubject(), testProxy.getDcSubject());
		assertEquals(proxy.getDctermsAlternative(), testProxy.getDctermsAlternative());
		assertEquals(proxy.getDctermsConformsTo(), testProxy.getDctermsConformsTo());
		assertEquals(proxy.getDctermsCreated(), testProxy.getDctermsCreated());
		assertEquals(proxy.getDctermsExtent(), testProxy.getDctermsExtent());
		assertEquals(proxy.getDctermsHasFormat(), testProxy.getDctermsHasFormat());
		assertEquals(proxy.getDctermsHasPart(), testProxy.getDctermsHasPart());
		assertEquals(proxy.getDctermsHasVersion(), testProxy.getDctermsHasVersion());
		assertEquals(proxy.getDctermsIsFormatOf(), testProxy.getDctermsIsFormatOf());
		assertEquals(proxy.getDctermsIsPartOf(), testProxy.getDctermsIsPartOf());
		assertEquals(proxy.getDctermsIsReferencedBy(), testProxy.getDctermsIsReferencedBy());
		assertEquals(proxy.getDctermsIsReplacedBy(), testProxy.getDctermsIsReplacedBy());
		assertEquals(proxy.getDctermsIsRequiredBy(), testProxy.getDctermsIsRequiredBy());
		assertEquals(proxy.getDctermsIssued(), testProxy.getDctermsIssued());
		assertEquals(proxy.getDctermsIsVersionOf(), testProxy.getDctermsIsVersionOf());
		assertEquals(proxy.getDctermsMedium(), testProxy.getDctermsMedium());
		assertEquals(proxy.getDctermsProvenance(), testProxy.getDctermsProvenance());
		assertEquals(proxy.getDctermsReferences(), testProxy.getDctermsReferences());
		assertEquals(proxy.getDctermsReplaces(), testProxy.getDctermsReplaces());
		assertEquals(proxy.getDctermsRequires(), testProxy.getDctermsRequires());
		assertEquals(proxy.getDctermsSpatial(), testProxy.getDctermsSpatial());
		assertEquals(proxy.getDctermsTemporal(), testProxy.getDctermsTemporal());
		assertEquals(proxy.getDctermsTOC(), testProxy.getDctermsTOC());
		assertEquals(proxy.getDcTitle(), testProxy.getDcTitle());
		assertEquals(proxy.getDcType(), testProxy.getDcType());
		assertEquals(proxy.getEdmIsNextInSequence(),testProxy.getEdmIsNextInSequence());
		assertEquals(proxy.getEdmCurrentLocation(), testProxy.getEdmCurrentLocation());
		assertEquals(proxy.getEdmType(), testProxy.getEdmType());
		return proxy;
	}

	private ProvidedCHO createProvidedCHO(){
		ProvidedCHO providedCHO = new ProvidedCHOImpl();
		providedCHO.setAbout("test edm:about");
		
		providedCHO.setOwlSameAs(new String[]{"test owlsameAs"});
		Key<ProvidedCHO> providedCHOKey = ds.save(providedCHO);
		
		ProvidedCHO testProvidedCHO = ds.find(ProvidedCHOImpl.class).filter("about", providedCHO.getAbout()).get();
		assertEquals(providedCHO,testProvidedCHO);
		assertEquals(providedCHO.getAbout(), testProvidedCHO.getAbout());
		
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
		agent.setAbout("test about");
		Map<String,String> note = new HashMap<String,String>();
		note.put("en", "test note");
		agent.setNote(note);
		Map<String, String> prefLabel = new HashMap<String, String>();
		prefLabel.put("en", "test prefLabel");
		agent.setPrefLabel(prefLabel);
		Map<String, String> altLabel = new HashMap<String, String>();
		altLabel.put("en", "test altLabel");
		agent.setPrefLabel(altLabel);
		Map<String,String> begin = new HashMap<String,String>();
		begin.put("en", "test begin");
		agent.setBegin(begin);
		Map<String,String> end = new HashMap<String,String>();
		end.put("en", "test begin");
		agent.setEnd(end);
		Key<Agent> agentKey = ds.save(agent);
		Agent testAgent = ds.find(AgentImpl.class).filter("about", agent.getAbout()).get();
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
