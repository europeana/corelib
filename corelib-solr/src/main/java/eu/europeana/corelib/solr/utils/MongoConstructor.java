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

import java.util.ArrayList;
import java.util.List;

import eu.europeana.corelib.definitions.jibx.RDF;
import eu.europeana.corelib.definitions.jibx.RDF.Choice;
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
import eu.europeana.corelib.solr.server.importer.util.AgentFieldInput;
import eu.europeana.corelib.solr.server.importer.util.AggregationFieldInput;
import eu.europeana.corelib.solr.server.importer.util.ConceptFieldInput;
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

//	private RDF record;

//	private FullBeanImpl fullBean;

	private MongoDBServer mongoServer;
	
	public void setMongoServer(MongoDBServer mongoServer) {
		this.mongoServer = mongoServer;
	}

/*	*//**
	 * Retrieve the JiBX RDF Entity
	 * 
	 * @return JiBX RDF Entity
	 *//*
	public RDF getRecord() {
		return record;
	}
*/
	/**
	 * Construct a FullBean out of A JiBX RDF record
	 * 
	 * @param record
	 *            The JiBX RDF record
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public FullBeanImpl constructFullBean(RDF record) throws InstantiationException, IllegalAccessException {
		FullBeanImpl fullBean = new FullBeanImpl();
		List<AgentImpl> agents = new ArrayList<AgentImpl>();
		List<AggregationImpl> aggregations = new ArrayList<AggregationImpl>();
		List<ConceptImpl> concepts = new ArrayList<ConceptImpl>();
		List<PlaceImpl> places = new ArrayList<PlaceImpl>();
		List<WebResourceImpl> webResources = new ArrayList<WebResourceImpl>();
		List<TimespanImpl> timespans = new ArrayList<TimespanImpl>();
		List<ProxyImpl> proxies = new ArrayList<ProxyImpl>();
		List<ProvidedCHOImpl> providedCHOs = new ArrayList<ProvidedCHOImpl>();
		List<Choice> elements = record.getChoiceList();
		for (Choice element : elements) {

			if (element.ifProvidedCHO()) {
				fullBean.setAbout(element.getProvidedCHO().getAbout());
				try {
					providedCHOs.add(ProvidedCHOFieldInput.createProvidedCHOMongoFields(element.getProvidedCHO(),
							mongoServer));
					if (proxies.size() > 0) {
						proxies.set(0, ProxyFieldInput.createProxyMongoFields(new ProxyImpl(),
								element.getProvidedCHO(), mongoServer));
					} else {
						proxies.add(ProxyFieldInput.createProxyMongoFields(new ProxyImpl(), element.getProvidedCHO(),
								mongoServer));
					}
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (element.ifAggregation()) {
				aggregations.add(AggregationFieldInput.createAggregationMongoFields(element.getAggregation(),
						mongoServer));
				if (webResources.size() > 0) {
					aggregations.set(0,
							AggregationFieldInput.appendWebResource(aggregations, webResources, mongoServer));
				}
				if (proxies.size() > 0) {
					proxies.set(0,
							ProxyFieldInput.addProxyForMongo(proxies.get(0), element.getAggregation(), mongoServer));
				} else {
					proxies.add(ProxyFieldInput.addProxyForMongo(new ProxyImpl(), element.getAggregation(), mongoServer));
				}

			}
			if (element.ifConcept()) {
				concepts.add(ConceptFieldInput.createConceptMongoFields(element.getConcept(), mongoServer));
			}
			if (element.ifPlace()) {
				places.add(PlaceFieldInput.createPlaceMongoFields(element.getPlace(), mongoServer));
			}

			if (element.ifWebResource()) {
				webResources.add(WebResourcesFieldInput.createWebResourceMongoField(element.getWebResource(),
						mongoServer));
				if (aggregations.size() > 0) {
					aggregations.set(0,
							AggregationFieldInput.appendWebResource(aggregations, webResources, mongoServer));
				}

			}
			if (element.ifTimeSpan()) {
				timespans.add(TimespanFieldInput.createTimespanMongoField(element.getTimeSpan(), mongoServer));
			}
			if (element.ifAgent()) {
				agents.add(AgentFieldInput.createAgentMongoEntity(element.getAgent(), mongoServer));
			}
		}

		AggregationImpl aggregation = aggregations.get(0);
		aggregation.setWebResources(webResources);
		fullBean.setProvidedCHOs(providedCHOs);
		fullBean.setAgents(agents);

		fullBean.setAggregations(aggregations);
		try {
			fullBean.setConcepts(concepts);
			fullBean.setPlaces(places);
			fullBean.setTimespans(timespans);
			fullBean.setProxies(proxies);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fullBean;
	}

}
