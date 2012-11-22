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

import eu.europeana.corelib.definitions.jibx.AgentType;
import eu.europeana.corelib.definitions.jibx.AggregatedCHO;
import eu.europeana.corelib.definitions.jibx.Aggregation;
import eu.europeana.corelib.definitions.jibx.Concept;
import eu.europeana.corelib.definitions.jibx.EuropeanaAggregationType;
import eu.europeana.corelib.definitions.jibx.PlaceType;
import eu.europeana.corelib.definitions.jibx.ProvidedCHOType;
import eu.europeana.corelib.definitions.jibx.ProxyType;
import eu.europeana.corelib.definitions.jibx.RDF;
import eu.europeana.corelib.definitions.jibx.TimeSpanType;
import eu.europeana.corelib.definitions.jibx.WebResourceType;
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
import eu.europeana.corelib.tools.utils.EuropeanaUriUtils;
//import eu.europeana.corelib.definitions.jibx.RDF.Choice;

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
	/**
	 * Constructs a FullBean from an RDF
	 * 
	 * @param record
	 *            The RDF record to use for the Fullbean contruction
	 * @param mongoServer
	 *            The mongo server used to save the FullBean
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public FullBeanImpl constructFullBean(RDF record, EdmMongoServer mongoServer)
			throws InstantiationException, IllegalAccessException,
			MalformedURLException, IOException {
		FullBeanImpl fullBean = new FullBeanImpl();
		List<AgentImpl> agents = new ArrayList<AgentImpl>();
		List<AggregationImpl> aggregations = new ArrayList<AggregationImpl>();
		List<ConceptImpl> concepts = new ArrayList<ConceptImpl>();
		List<PlaceImpl> places = new ArrayList<PlaceImpl>();
		List<WebResource> webResources = new ArrayList<WebResource>();
		List<TimespanImpl> timespans = new ArrayList<TimespanImpl>();
		List<ProxyImpl> proxies = new ArrayList<ProxyImpl>();
		List<ProvidedCHOImpl> providedCHOs = new ArrayList<ProvidedCHOImpl>();
		String aggregatedCHO = "";
		if (record.getProvidedCHOList() != null) {
			for (ProvidedCHOType pcho : record.getProvidedCHOList()) {
				fullBean.setAbout(pcho.getAbout());
				aggregatedCHO = pcho.getAbout();
				try {
					providedCHOs.add(new ProvidedCHOFieldInput()
							.createProvidedCHOMongoFields(pcho, mongoServer));
					ProxyImpl proxy = getProxy(proxies, pcho.getAbout());
					if (proxy != null) {
						proxy.setProxyFor(pcho.getAbout());
					}
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		if (record.getProxyList() != null) {
			for (ProxyType proxytype : record.getProxyList()) {
				ProxyImpl proxy = getProxy(proxies, proxytype.getAbout());
				if (proxy == null) {
					proxies.add(new ProxyFieldInput().createProxyMongoFields(
							new ProxyImpl(), proxytype, mongoServer));
				}
			}
		}
		if (record.getAggregationList() != null) {
			for (Aggregation aggregation : record.getAggregationList()) {
				AggregatedCHO ag = new AggregatedCHO();
				ag.setResource(aggregatedCHO);
				aggregation.setAggregatedCHO(ag);
				aggregations
						.add(new AggregationFieldInput()
								.createAggregationMongoFields(aggregation,
										mongoServer));
				if (webResources.size() > 0) {
					aggregations.set(0, new AggregationFieldInput()
							.appendWebResource(aggregations, webResources,
									mongoServer));
				}
				if (proxies.size() > 0) {
					ProxyImpl proxy = getProxy(proxies, aggregation
							.getAggregatedCHO().getResource());
					if (proxy != null) {
						proxy.setProxyIn(new String[] { aggregation.getAbout() });
					} else {
						proxies.add(new ProxyFieldInput().addProxyForMongo(
								new ProxyImpl(), aggregation, mongoServer));
					}
				}

			}
		}
		if (record.getConceptList() != null) {
			for (Concept concept : record.getConceptList()) {
				concepts.add(new ConceptFieldInput().createConceptMongoFields(
						concept, mongoServer, record));
			}
		}

		if (record.getPlaceList() != null) {
			for (PlaceType place : record.getPlaceList()) {
				places.add(new PlaceFieldInput().createPlaceMongoFields(place,
						mongoServer));
			}
		}
		if (record.getWebResourceList() != null) {
			for (WebResourceType wresource : record.getWebResourceList()) {
				WebResourceImpl webResource = new WebResourcesFieldInput()
						.createWebResourceMongoField(wresource, mongoServer);
				webResources.add(webResource);
				if (aggregations.size() > 0) {
					AggregationImpl aggr = new AggregationFieldInput()
							.appendWebResource(aggregations, webResource,
									mongoServer);
					if (aggr != null) {
						aggregations.remove(aggr);
						aggregations.add(aggr);
					}
				}

			}
		}
		if (record.getTimeSpanList() != null) {
			for (TimeSpanType tspan : record.getTimeSpanList()) {
				timespans.add(new TimespanFieldInput()
						.createTimespanMongoField(tspan, mongoServer));
			}
		}
		if (record.getAgentList() != null) {
			for (AgentType agent : record.getAgentList()) {
				agents.add(new AgentFieldInput().createAgentMongoEntity(agent,
						mongoServer));
			}
		}
		if (record.getEuropeanaAggregationList() != null) {
			for (EuropeanaAggregationType eaggregation : record
					.getEuropeanaAggregationList()) {
				fullBean.setEuropeanaAggregation(new EuropeanaAggregationFieldInput()
						.createAggregationMongoFields(eaggregation, mongoServer));
			}
		}

		// AggregationImpl aggregation = aggregations.get(0);
		// aggregation.setWebResources(webResources);
		// MongoUtils.updateAggregation(aggregation, mongoServer);

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

	private ProxyImpl getProxy(List<ProxyImpl> proxies, String about) {
		for (ProxyImpl proxy : proxies) {
			if (StringUtils.equals(proxy.getAbout(), about)) {
				return proxy;
			}
		}
		return null;
	}

}
