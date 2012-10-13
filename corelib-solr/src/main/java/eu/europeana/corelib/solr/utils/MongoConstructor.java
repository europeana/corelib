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

package eu.europeana.corelib.solr.utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import eu.europeana.corelib.definitions.jibx.RDF;
import eu.europeana.corelib.definitions.jibx.RDF.Choice;
import eu.europeana.corelib.definitions.solr.entity.EuropeanaAggregation;
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
import eu.europeana.corelib.solr.server.importer.util.AgentFieldInput;
import eu.europeana.corelib.solr.server.importer.util.AggregationFieldInput;
import eu.europeana.corelib.solr.server.importer.util.ConceptFieldInput;
import eu.europeana.corelib.solr.server.importer.util.EuropeanaAggregationFieldInput;
import eu.europeana.corelib.solr.server.importer.util.PlaceFieldInput;
import eu.europeana.corelib.solr.server.importer.util.ProvidedCHOFieldInput;
import eu.europeana.corelib.solr.server.importer.util.ProxyFieldInput;
import eu.europeana.corelib.solr.server.importer.util.TimespanFieldInput;
import eu.europeana.corelib.solr.server.importer.util.WebResourcesFieldInput;

/**
 * A FullBean Constructor from an EDM XML
 * 
 * @author Yorgos.Mamakis@ kb.nl
 */
public class MongoConstructor {

	// private EdmMongoServerImpl mongoServer;

	// public void setMongoServer(EdmMongoServerImpl mongoServer) {
	// this.mongoServer = mongoServer;
	// }

	public FullBeanImpl constructFullBean(RDF record,
			EdmMongoServer mongoServer) throws InstantiationException,
			IllegalAccessException, MalformedURLException, IOException {
		FullBeanImpl fullBean = new FullBeanImpl();
		List<AgentImpl> agents = new ArrayList<AgentImpl>();
		List<AggregationImpl> aggregations = new ArrayList<AggregationImpl>();
		List<ConceptImpl> concepts = new ArrayList<ConceptImpl>();
		List<PlaceImpl> places = new ArrayList<PlaceImpl>();
		List<WebResource> webResources = new ArrayList<WebResource>();
		List<TimespanImpl> timespans = new ArrayList<TimespanImpl>();
		List<ProxyImpl> proxies = new ArrayList<ProxyImpl>();
		List<ProvidedCHOImpl> providedCHOs = new ArrayList<ProvidedCHOImpl>();
		List<Choice> elements = record.getChoiceList();
		for (Choice element : elements) {

			if (element.ifProvidedCHO()) {
				fullBean.setAbout(element.getProvidedCHO().getAbout());
				try {
					providedCHOs.add(new ProvidedCHOFieldInput()
							.createProvidedCHOMongoFields(
									element.getProvidedCHO(), mongoServer));
					ProxyImpl proxy= getProxy(proxies, element.getProvidedCHO().getAbout());
					if(proxy!=null){
						proxy.setProxyFor(element.getProvidedCHO().getAbout());
					} 
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			if (element.ifProxy()) {
				ProxyImpl proxy = getProxy(proxies, element.getProxy().getAbout());
				if(proxy==null){
					proxies.add(new ProxyFieldInput().createProxyMongoFields(
							new ProxyImpl(), element.getProxy(), mongoServer));
				}
			}
			if (element.ifAggregation()) {
				aggregations.add(new AggregationFieldInput()
						.createAggregationMongoFields(element.getAggregation(),
								mongoServer));
				if (webResources.size() > 0) {
					aggregations.set(0, new AggregationFieldInput()
							.appendWebResource(aggregations, webResources,
									mongoServer));
				}
				if (proxies.size() > 0) {
					ProxyImpl proxy= getProxy(proxies, element.getAggregation().getAggregatedCHO().getResource());
					if(proxy!=null){
						proxy.setProxyIn(new String[]{element.getAggregation().getAbout()});
					} else {
						proxies.add(new ProxyFieldInput().addProxyForMongo(
							new ProxyImpl(), element.getAggregation(),
							mongoServer));
					}
				}

			}
			if (element.ifConcept()) {
				concepts.add(new ConceptFieldInput().createConceptMongoFields(
						element.getConcept(), mongoServer, record));
			}
			if (element.ifPlace()) {
				places.add(new PlaceFieldInput().createPlaceMongoFields(
						element.getPlace(), mongoServer));
			}

			if (element.ifWebResource()) {
				WebResourceImpl webResource = new WebResourcesFieldInput()
						.createWebResourceMongoField(element.getWebResource(),
								mongoServer);
				webResources.add(webResource);
				if (aggregations.size() > 0) {
					aggregations.set(0, new AggregationFieldInput()
							.appendWebResource(aggregations, webResource,
									mongoServer));
				}
				

			}
			if (element.ifTimeSpan()) {
				timespans.add(new TimespanFieldInput()
						.createTimespanMongoField(element.getTimeSpan(),
								mongoServer));
			}
			if (element.ifAgent()) {
				agents.add(new AgentFieldInput().createAgentMongoEntity(
						element.getAgent(), mongoServer));
			}
			if(element.ifEuropeanaAggregation()){
				fullBean.setEuropeanaAggregation(new EuropeanaAggregationFieldInput().createAggregationMongoFields(element.getEuropeanaAggregation(), mongoServer));
			}
		}

		AggregationImpl aggregation = aggregations.get(0);
		aggregation.setWebResources(webResources);
		MongoUtils.updateAggregation(aggregation, mongoServer);

		fullBean.setProvidedCHOs(providedCHOs);
		
		fullBean.setAggregations(aggregations);
		try {
			if (agents.size() > 0) {
				fullBean.setAgents(agents);
			}
			if (concepts.size() > 0) {
				fullBean.setConcepts(concepts);
			}
			if (places.size() > 0) {
				fullBean.setPlaces(places);
			}
			if (timespans.size() > 0) {
				fullBean.setTimespans(timespans);
			}
			if (proxies.size() > 0) {
				fullBean.setProxies(proxies);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fullBean;
	}

	private ProxyImpl getProxy(List<ProxyImpl> proxies,String about) {
		for (ProxyImpl proxy:proxies){
			if (StringUtils.equals(proxy.getAbout(), about)){
				return proxy;
			}
		}
		return null;
	}

}
