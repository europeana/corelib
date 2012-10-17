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

import org.apache.solr.common.SolrInputDocument;

import eu.europeana.corelib.definitions.jibx.AgentType;
import eu.europeana.corelib.definitions.jibx.Aggregation;
import eu.europeana.corelib.definitions.jibx.Concept;
import eu.europeana.corelib.definitions.jibx.EuropeanaAggregationType;
import eu.europeana.corelib.definitions.jibx.PlaceType;
import eu.europeana.corelib.definitions.jibx.ProvidedCHOType;
import eu.europeana.corelib.definitions.jibx.ProxyType;
import eu.europeana.corelib.definitions.jibx.RDF;
import eu.europeana.corelib.definitions.jibx.TimeSpanType;
import eu.europeana.corelib.definitions.jibx.WebResourceType;
//import eu.europeana.corelib.definitions.jibx.RDF.Choice;
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
	public SolrInputDocument constructSolrDocument(RDF rdf) throws InstantiationException, IllegalAccessException, MalformedURLException, IOException{
		SolrInputDocument solrInputDocument = new SolrInputDocument();



			for(AgentType agent :rdf.getAgentList()){
				solrInputDocument = new AgentFieldInput().createAgentSolrFields(agent, solrInputDocument);
			}
			for(Aggregation aggregation : rdf.getAggregationList()){
				solrInputDocument = new AggregationFieldInput().createAggregationSolrFields(aggregation, solrInputDocument);
				solrInputDocument = new ProxyFieldInput().addProxyForSolr(aggregation, solrInputDocument);
			}
			for(Concept concept: rdf.getConceptList()){
				solrInputDocument = new ConceptFieldInput().createConceptSolrFields(concept,solrInputDocument);
			}
			for(PlaceType place: rdf.getPlaceList()){
				solrInputDocument = new PlaceFieldInput().createPlaceSolrFields(place,solrInputDocument) ;
			}
			for(ProvidedCHOType cho : rdf.getProvidedCHOList() ){
				solrInputDocument = new ProvidedCHOFieldInput().createProvidedCHOFields(cho,solrInputDocument);
			}
			for(ProxyType proxy: rdf.getProxyList()){
				solrInputDocument = new ProxyFieldInput().createProxySolrFields(proxy, solrInputDocument);
			}
			for(TimeSpanType time: rdf.getTimeSpanList()) {
				solrInputDocument = new TimespanFieldInput().createTimespanSolrFields(time, solrInputDocument);
			}
			for(EuropeanaAggregationType euaggregation : rdf.getEuropeanaAggregationList()){
				solrInputDocument = new EuropeanaAggregationFieldInput().createAggregationSolrFields(euaggregation, solrInputDocument);
				solrInputDocument = new ProxyFieldInput().addProxyForSolr(euaggregation, solrInputDocument);
			}
			for(WebResourceType wresource:rdf.getWebResourceList()){
				solrInputDocument = new WebResourcesFieldInput().createWebResourceSolrFields(wresource, solrInputDocument);
			}


		return solrInputDocument;
	}
}
