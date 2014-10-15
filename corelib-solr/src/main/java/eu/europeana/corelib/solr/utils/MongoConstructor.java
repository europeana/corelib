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

import eu.europeana.corelib.definitions.jibx.AgentType;
import eu.europeana.corelib.definitions.jibx.AggregatedCHO;
import eu.europeana.corelib.definitions.jibx.Aggregation;
import eu.europeana.corelib.definitions.jibx.Concept;
import eu.europeana.corelib.definitions.jibx.EuropeanaAggregationType;
import eu.europeana.corelib.definitions.jibx.License;
import eu.europeana.corelib.definitions.jibx.PlaceType;
import eu.europeana.corelib.definitions.jibx.ProvidedCHOType;
import eu.europeana.corelib.definitions.jibx.ProxyFor;
import eu.europeana.corelib.definitions.jibx.ProxyIn;
import eu.europeana.corelib.definitions.jibx.ProxyType;
import eu.europeana.corelib.definitions.jibx.RDF;
import eu.europeana.corelib.definitions.jibx.TimeSpanType;
import eu.europeana.corelib.definitions.solr.entity.WebResource;
import eu.europeana.corelib.solr.bean.impl.FullBeanImpl;
import eu.europeana.corelib.solr.entity.AgentImpl;
import eu.europeana.corelib.solr.entity.AggregationImpl;
import eu.europeana.corelib.solr.entity.ConceptImpl;
import eu.europeana.corelib.solr.entity.LicenseImpl;
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
import eu.europeana.corelib.solr.server.importer.util.LicenseFieldInput;
import eu.europeana.corelib.solr.server.importer.util.PlaceFieldInput;
import eu.europeana.corelib.solr.server.importer.util.ProvidedCHOFieldInput;
import eu.europeana.corelib.solr.server.importer.util.ProxyFieldInput;
import eu.europeana.corelib.solr.server.importer.util.TimespanFieldInput;

/**
 * A FullBean Constructor from an EDM XML
 * 
 * @author Yorgos.Mamakis@ kb.nl
 */
public class MongoConstructor {

	/**
	 * Constructs a FullBean from an RDF TODO: check for sanity
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
         @Deprecated
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
		List<LicenseImpl> licenses = new ArrayList<LicenseImpl>();
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

			try {
				providedCHOs.add(new ProvidedCHOFieldInput()
						.createProvidedCHOMongoFields(pcho, mongoServer));
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		if(record.getLicenseList()!=null){
		for(License license:record.getLicenseList()){
			licenses.add(new LicenseFieldInput().createLicenseMongoFields(license, mongoServer));
		}
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
				for (EuropeanaAggregationType aggr: record.getEuropeanaAggregationList()){
					List<ProxyIn> proxyInList =  new ArrayList<ProxyIn>();
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
					new ProxyImpl(), proxytype, mongoServer));
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
						.createWebResources(record.getWebResourceList(),
								mongoServer);

			}

			aggregations.add(new AggregationFieldInput()
					.createAggregationMongoFields(aggregation, mongoServer,
							webResourcesMongo));

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
				AggregatedCHO aggregatedCho = new AggregatedCHO();
				aggregatedCho.setResource(providedCHO+record.getProvidedCHOList().get(0).getAbout());
				eaggregation.setAbout(europeanaAggregationAbout);
				fullBean.setEuropeanaAggregation(new EuropeanaAggregationFieldInput()
						.createAggregationMongoFields(eaggregation,
								mongoServer, SolrUtils.getPreviewUrl(record)));
			}
		}

		fullBean.setProvidedCHOs(providedCHOs);

		fullBean.setAggregations(aggregations);
		if(licenses.size()>0){
			fullBean.setLicenses(licenses);
		}
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

        public FullBeanImpl constructFullBean(RDF record)
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
		List<LicenseImpl> licenses = new ArrayList<LicenseImpl>();
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
				for (EuropeanaAggregationType aggr: record.getEuropeanaAggregationList()){
					List<ProxyIn> proxyInList =  new ArrayList<ProxyIn>();
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
				concepts.add(new ConceptFieldInput().createNewConcept(
						concept));
			}
		}

		if (record.getPlaceList() != null) {
			for (PlaceType place : record.getPlaceList()) {
				places.add(new PlaceFieldInput().createNewPlace(place));
			}
		}

		if (record.getTimeSpanList() != null) {
			for (TimeSpanType tspan : record.getTimeSpanList()) {
				timespans.add(new TimespanFieldInput()
						.createNewTimespan(tspan));
			}
		}
		if (record.getAgentList() != null) {
			for (AgentType agent : record.getAgentList()) {
				agents.add(new AgentFieldInput().createNewAgent(agent));
			}
		}
		if(record.getLicenseList()!=null){
			for(License license: record.getLicenseList()){
				licenses.add(new LicenseFieldInput().createLicenseMongoFields(license));
			}
		}
		if (record.getEuropeanaAggregationList() != null) {
			for (EuropeanaAggregationType eaggregation : record
					.getEuropeanaAggregationList()) {
				AggregatedCHO aggregatedCho = new AggregatedCHO();
				aggregatedCho.setResource(providedCHO+record.getProvidedCHOList().get(0).getAbout());
				eaggregation.setAbout(europeanaAggregationAbout);
				fullBean.setEuropeanaAggregation(new EuropeanaAggregationFieldInput()
						.createAggregationMongoFields(eaggregation,
								SolrUtils.getPreviewUrl(record)));
			}
		}

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
			if(licenses.size()>0){
				fullBean.setLicenses(licenses);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fullBean;
	}
        
}
