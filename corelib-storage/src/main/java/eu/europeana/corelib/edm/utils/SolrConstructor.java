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
import java.net.MalformedURLException;
import java.util.List;

import eu.europeana.corelib.definitions.jibx.*;
import eu.europeana.corelib.edm.server.importer.util.*;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.common.SolrInputDocument;
import eu.europeana.corelib.definitions.jibx.HasView;

/**
 * Construct a SolrInputDocument from a JiBX RDF Entity
 * 
 * @author Yorgos.Mamakis@ kb.nl
 */

public class SolrConstructor {

	/**
	 * Construct a SolrInputDocument from a JiBX RDF Entity
	 * 
	 * @param rdf
	 *            The JiBX RDF Entity
	 * @return SolrInputDocument with an full EDM XML record
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	public SolrInputDocument constructSolrDocument(RDF rdf)
			throws InstantiationException, IllegalAccessException,
			IOException {
		SolrInputDocument solrInputDocument = new SolrInputDocument();

		if (rdf.getAgentList() != null) {
			for (AgentType agent : rdf.getAgentList()) {
				solrInputDocument = new AgentFieldInput()
						.createAgentSolrFields(agent, solrInputDocument);
			}
		}
		if (rdf.getAggregationList() != null) {
			for (Aggregation aggregation : rdf.getAggregationList()) {
				solrInputDocument = new AggregationFieldInput()
						.createAggregationSolrFields(aggregation,
								solrInputDocument, rdf.getLicenseList());
				solrInputDocument = new ProxyFieldInput().addProxyForSolr(
						aggregation, solrInputDocument);
			}
		}
		if (rdf.getConceptList() != null) {
			for (Concept concept : rdf.getConceptList()) {
				solrInputDocument = new ConceptFieldInput()
						.createConceptSolrFields(concept, solrInputDocument);
			}
		}
		if (rdf.getPlaceList() != null) {
			for (PlaceType place : rdf.getPlaceList()) {
				solrInputDocument = new PlaceFieldInput()
						.createPlaceSolrFields(place, solrInputDocument);
			}
		}
		if (rdf.getProvidedCHOList() != null) {
			for (ProvidedCHOType cho : rdf.getProvidedCHOList()) {
				solrInputDocument = new ProvidedCHOFieldInput()
						.createProvidedCHOFields(cho, solrInputDocument);
			}
		}
		if (rdf.getProxyList() != null) {
			for (ProxyType proxy : rdf.getProxyList()) {
				solrInputDocument = new ProxyFieldInput()
						.createProxySolrFields(proxy, solrInputDocument);
			}
		}
		if (rdf.getTimeSpanList() != null) {
			for (TimeSpanType time : rdf.getTimeSpanList()) {
				solrInputDocument = new TimespanFieldInput()
						.createTimespanSolrFields(time, solrInputDocument);
			}
		}
		if (rdf.getEuropeanaAggregationList() != null) {
			for (EuropeanaAggregationType euaggregation : rdf
					.getEuropeanaAggregationList()) {
				solrInputDocument = new EuropeanaAggregationFieldInput()
						.createAggregationSolrFields(euaggregation,
								solrInputDocument, SolrUtils.getPreviewUrl(rdf));
				solrInputDocument = new ProxyFieldInput().addProxyForSolr(
						euaggregation, solrInputDocument);
			}
		}
		if (rdf.getWebResourceList() != null) {
			for (WebResourceType wresource : rdf.getWebResourceList()) {
				solrInputDocument = new WebResourcesFieldInput()
						.createWebResourceSolrFields(wresource,
								solrInputDocument);
			}
		}
		if (rdf.getLicenseList() != null) {
			for (License license : rdf.getLicenseList()) {
				boolean isAggregation = false;
				for (Aggregation aggr : rdf.getAggregationList()) {
					if (aggr.getRights() != null) {
						if (license.getAbout().equalsIgnoreCase(
								aggr.getRights().getResource())) {
							isAggregation = true;
							break;
						}
					}
				}
				solrInputDocument = new LicenseFieldInput()
						.createLicenseSolrFields(license, solrInputDocument,
								isAggregation);
			}
		}
		if(rdf.getServiceList()!=null){
			for (Service service:rdf.getServiceList()){
				solrInputDocument = new ServiceFieldInput().createServiceSolrFields(service,solrInputDocument);
			}
		}
		solrInputDocument = generateWRFromAggregation(solrInputDocument,
				rdf.getAggregationList());
		return solrInputDocument;
	}

	private SolrInputDocument generateWRFromAggregation(
			SolrInputDocument solrInputDocument,
			List<Aggregation> aggregationList) {

		if (aggregationList != null) {
			for (Aggregation aggr : aggregationList) {
				if (solrInputDocument.containsKey("edm_webResource")) {
					boolean containsWr = false;
					if (aggr.getIsShownBy() != null) {
						String isShownBy = aggr.getIsShownBy().getResource();
						for (Object str : solrInputDocument
								.getFieldValues("edm_webResource")) {
							if (StringUtils.equals(str.toString(), isShownBy)) {
								containsWr = true;
							}
						}
						if (!containsWr) {
							solrInputDocument.addField("edm_webResource",
									isShownBy);
						}
					}
					containsWr = false;
					if (aggr.getObject() != null) {
						String object = aggr.getObject().getResource();
						for (Object str : solrInputDocument
								.getFieldValues("edm_webResource")) {
							if (StringUtils.equals(str.toString(), object)) {
								containsWr = true;
							}
						}
						if (!containsWr) {
							solrInputDocument.addField("edm_webResource",
									object);
						}
					}

					if (aggr.getHasViewList() != null) {
						for (HasView hasView : aggr.getHasViewList()) {
							containsWr = false;
							String res = hasView.getResource().trim();
							for (Object str : solrInputDocument
									.getFieldValues("edm_webResource")) {
								if (StringUtils.equals(str.toString(), res)) {
									containsWr = true;
								}
							}
							if (!containsWr) {
								solrInputDocument.addField("edm_webResource",
										res);
							}
						}
					}
				}
			}
		}

		return solrInputDocument;
	}
}
