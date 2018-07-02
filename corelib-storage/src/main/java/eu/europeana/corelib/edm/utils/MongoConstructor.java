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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import eu.europeana.corelib.definitions.jibx.AgentType;
import eu.europeana.corelib.definitions.jibx.Aggregation;
import eu.europeana.corelib.definitions.jibx.CollectionName;
import eu.europeana.corelib.definitions.jibx.Concept;
import eu.europeana.corelib.definitions.jibx.DatasetName;
import eu.europeana.corelib.definitions.jibx.EuropeanaAggregationType;
import eu.europeana.corelib.definitions.jibx.License;
import eu.europeana.corelib.definitions.jibx.PlaceType;
import eu.europeana.corelib.definitions.jibx.ProvidedCHOType;
import eu.europeana.corelib.definitions.jibx.ProxyType;
import eu.europeana.corelib.definitions.jibx.RDF;
import eu.europeana.corelib.definitions.jibx.Service;
import eu.europeana.corelib.definitions.jibx.TimeSpanType;
import eu.europeana.corelib.edm.server.importer.util.AgentFieldInput;
import eu.europeana.corelib.edm.server.importer.util.AggregationFieldInput;
import eu.europeana.corelib.edm.server.importer.util.ConceptFieldInput;
import eu.europeana.corelib.edm.server.importer.util.EuropeanaAggregationFieldInput;
import eu.europeana.corelib.edm.server.importer.util.LicenseFieldInput;
import eu.europeana.corelib.edm.server.importer.util.PlaceFieldInput;
import eu.europeana.corelib.edm.server.importer.util.ProvidedCHOFieldInput;
import eu.europeana.corelib.edm.server.importer.util.ProxyFieldInput;
import eu.europeana.corelib.edm.server.importer.util.ServiceFieldInput;
import eu.europeana.corelib.edm.server.importer.util.TimespanFieldInput;
import eu.europeana.corelib.solr.bean.impl.FullBeanImpl;
import eu.europeana.corelib.solr.entity.AgentImpl;
import eu.europeana.corelib.solr.entity.AggregationImpl;
import eu.europeana.corelib.solr.entity.ConceptImpl;
import eu.europeana.corelib.solr.entity.LicenseImpl;
import eu.europeana.corelib.solr.entity.PlaceImpl;
import eu.europeana.corelib.solr.entity.ProvidedCHOImpl;
import eu.europeana.corelib.solr.entity.ProxyImpl;
import eu.europeana.corelib.solr.entity.ServiceImpl;
import eu.europeana.corelib.solr.entity.TimespanImpl;
import eu.europeana.corelib.solr.entity.WebResourceImpl;

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
		List<AgentImpl> agents = new ArrayList<>();
		List<AggregationImpl> aggregations = new ArrayList<>();
		List<ConceptImpl> concepts = new ArrayList<>();
		List<PlaceImpl> places = new ArrayList<>();
		List<TimespanImpl> timespans = new ArrayList<>();
		List<ProxyImpl> proxies = new ArrayList<>();
		List<ProvidedCHOImpl> providedCHOs = new ArrayList<>();
		List<LicenseImpl> licenses = new ArrayList<>();
		List<ServiceImpl> services = new ArrayList<>();

		for (ProvidedCHOType pcho : record.getProvidedCHOList()) {
			fullBean.setAbout(pcho.getAbout());
			providedCHOs.add(new ProvidedCHOFieldInput().createProvidedCHOMongoFields(pcho));
		}
		for (ProxyType proxytype : record.getProxyList()) {
			proxies.add(new ProxyFieldInput().createProxyMongoFields(new ProxyImpl(), proxytype));
		}
		for (Aggregation aggregation : record.getAggregationList()) {
			List<WebResourceImpl> webResourcesMongo = new ArrayList<>();
			if (record.getWebResourceList() != null && !record.getWebResourceList().isEmpty()) {
				webResourcesMongo = new AggregationFieldInput()
						.createWebResources(record.getWebResourceList());
			}
			aggregations.add(new AggregationFieldInput()
					.createAggregationMongoFields(aggregation, webResourcesMongo));
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
				timespans.add(new TimespanFieldInput().createNewTimespan(tspan));
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
			for (EuropeanaAggregationType eaggregation : record.getEuropeanaAggregationList()) {
				fullBean.setEuropeanaAggregation(new EuropeanaAggregationFieldInput()
						.createAggregationMongoFields(eaggregation, SolrUtils.getPreviewUrl(record)));
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

		fullBean.setEuropeanaCollectionName(new String[] {getDatasetNameFromRdf(record)});
	    
		return fullBean;
	}

	private static String getDatasetNameFromRdf(RDF rdf) {

	    // Try to find the europeana aggregation
	    final EuropeanaAggregationType aggregation;
	    if (rdf.getEuropeanaAggregationList() == null || rdf.getEuropeanaAggregationList().isEmpty()) {
	      aggregation = null;
	    } else {
	      aggregation = rdf.getEuropeanaAggregationList().get(0);
	    }
	    if (aggregation == null) {
	      return "";
	    }

	    // Try the dataset name property from the aggregation.
	    final DatasetName datasetNameObject = aggregation.getDatasetName();
	    final String datasetName = datasetNameObject == null ? null : datasetNameObject.getString();
	    if (StringUtils.isNotBlank(datasetName)) {
	      return datasetName;
	    }

	    // If that fails, try the collection name property from the aggregation.
	    final CollectionName collectionNameObject = aggregation.getCollectionName();
	    final String collectionName =
	        collectionNameObject == null ? null : collectionNameObject.getString();
	    return StringUtils.isNotBlank(collectionName) ? collectionName : "";
	}
}
