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

package eu.europeana.corelib.edm.utils;

import eu.europeana.corelib.definitions.edm.entity.*;
import eu.europeana.corelib.definitions.jibx.*;
import eu.europeana.corelib.definitions.jibx.Aggregation;
import eu.europeana.corelib.definitions.jibx.Concept;
import eu.europeana.corelib.definitions.jibx.License;
import eu.europeana.corelib.definitions.jibx.Service;
import eu.europeana.corelib.edm.server.importer.util.*;
import eu.europeana.corelib.solr.bean.impl.FullBeanImpl;
import eu.europeana.corelib.solr.entity.*;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A FullBean Constructor from an EDM XML
 * 
 * @author Yorgos.Mamakis@ kb.nl
 */
public class MongoConstructor {

	

	public FullBeanImpl constructFullBean(RDF record)
			throws InstantiationException, IllegalAccessException,
			IOException {
		FullBeanImpl fullBean = new FullBeanImpl();
		List<AgentImpl> agents = new ArrayList<AgentImpl>();
		List<AggregationImpl> aggregations = new ArrayList<AggregationImpl>();
		List<ConceptImpl> concepts = new ArrayList<ConceptImpl>();
		List<PlaceImpl> places = new ArrayList<PlaceImpl>();
		List<WebResource> webResources = new ArrayList<WebResource>();
		List<TimespanImpl> timespans = new ArrayList<TimespanImpl>();
		List<ProxyImpl> proxies = new ArrayList<ProxyImpl>();
		List<ProvidedCHOImpl> providedCHOs = new ArrayList<ProvidedCHOImpl>();
		List<LicenseImpl> licenses = new ArrayList<LicenseImpl>();
		List<ServiceImpl> services = new ArrayList<>();
		String aggregationAbout = "/aggregation/provider";
		String europeanaAggregationAbout = "/aggregation/europeana";
		String proxyAbout = "/proxy/provider";
		String europeanaProxy = "/proxy/europeana";
		String providedCHO = "/item";

		for (ProvidedCHOType pcho : record.getProvidedCHOList()) {
			fullBean.setAbout(pcho.getAbout());
			aggregationAbout = aggregationAbout + pcho.getAbout();
			europeanaAggregationAbout = europeanaAggregationAbout
					+ pcho.getAbout();
			proxyAbout = proxyAbout + pcho.getAbout();
			europeanaProxy = europeanaProxy + pcho.getAbout();

			providedCHOs.add(new ProvidedCHOFieldInput()
					.createProvidedCHOMongoFields(pcho));

		}
		for (ProxyType proxytype : record.getProxyList()) {

			boolean isEuropeanaProxy = false;
			if (proxytype.getEuropeanaProxy() != null) {
				if (!proxytype.getEuropeanaProxy().isEuropeanaProxy()) {
					proxytype.setAbout(proxyAbout);
					isEuropeanaProxy = false;
				} else {
					proxytype.setAbout(europeanaProxy);
					isEuropeanaProxy = true;

				}
			}
			if (!isEuropeanaProxy) {
				for (Aggregation aggr : record.getAggregationList()) {
					List<ProxyIn> proxyInList = new ArrayList<ProxyIn>();
					ProxyIn proxyIn = new ProxyIn();
					proxyIn.setResource(aggregationAbout);
					proxyInList.add(proxyIn);
					proxytype.setProxyInList(proxyInList);

				}
			} else {
				for (EuropeanaAggregationType aggr : record
						.getEuropeanaAggregationList()) {
					List<ProxyIn> proxyInList = new ArrayList<ProxyIn>();
					ProxyIn proxyIn = new ProxyIn();
					proxyIn.setResource(europeanaAggregationAbout);
					proxyInList.add(proxyIn);
					proxytype.setProxyInList(proxyInList);
				}
			}

			for (ProvidedCHOType pCho : record.getProvidedCHOList()) {

				ProxyFor pFor = new ProxyFor();
				pFor.setResource(providedCHO + pCho.getAbout());
				proxytype.setProxyFor(pFor);

			}

			proxies.add(new ProxyFieldInput().createProxyMongoFields(
					new ProxyImpl(), proxytype));
		}
		for (Aggregation aggregation : record.getAggregationList()) {
			AggregatedCHO ag = new AggregatedCHO();
			ag.setResource(providedCHO + fullBean.getAbout());
			aggregation.setAggregatedCHO(ag);
			aggregation.setAbout(aggregationAbout);
			List<WebResourceImpl> webResourcesMongo = new ArrayList<WebResourceImpl>();
			if (record.getWebResourceList() != null
					&& record.getWebResourceList().size() > 0) {
				webResourcesMongo = new AggregationFieldInput()
						.createWebResources(record.getWebResourceList());

			}

			aggregations.add(new AggregationFieldInput()
					.createAggregationMongoFields(aggregation,
							webResourcesMongo));

		}

		if (record.getConceptList() != null) {
			for (Concept concept : record.getConceptList()) {
				concepts.add(new ConceptFieldInput().createNewConcept(concept));
			}
		}

		if (record.getPlaceList() != null) {
			for (PlaceType place : record.getPlaceList()) {
				places.add(new PlaceFieldInput().createNewPlace(place));
			}
		}

		if (record.getTimeSpanList() != null) {
			for (TimeSpanType tspan : record.getTimeSpanList()) {
				timespans
						.add(new TimespanFieldInput().createNewTimespan(tspan));
			}
		}
		if (record.getAgentList() != null) {
			for (AgentType agent : record.getAgentList()) {
				agents.add(new AgentFieldInput().createNewAgent(agent));
			}
		}
		if (record.getLicenseList() != null) {
			for (License license : record.getLicenseList()) {
				licenses.add(new LicenseFieldInput()
						.createLicenseMongoFields(license));
			}
		}
		if (record.getEuropeanaAggregationList() != null) {
			for (EuropeanaAggregationType eaggregation : record
					.getEuropeanaAggregationList()) {
				AggregatedCHO aggregatedCho = new AggregatedCHO();
				aggregatedCho.setResource(providedCHO
						+ record.getProvidedCHOList().get(0).getAbout());
				eaggregation.setAbout(europeanaAggregationAbout);
				fullBean.setEuropeanaAggregation(new EuropeanaAggregationFieldInput()
						.createAggregationMongoFields(eaggregation,
								SolrUtils.getPreviewUrl(record)));
			}
		}
		if(record.getServiceList()!=null){
			for (Service service:record.getServiceList()){
				services.add(new ServiceFieldInput().createServiceMongoFields(service));
			}
		}
		fullBean.setProvidedCHOs(providedCHOs);

		fullBean.setAggregations(aggregations);
		fullBean.setServices(services);
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
			if (licenses.size() > 0) {
				fullBean.setLicenses(licenses);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fullBean;
	}

}
