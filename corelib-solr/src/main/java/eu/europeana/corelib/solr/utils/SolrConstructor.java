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

import eu.europeana.corelib.definitions.jibx.RDF;
import eu.europeana.corelib.definitions.jibx.RDF.Choice;
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

		for(Choice element: rdf.getChoiceList()){
			if(element.ifAgent()){
				solrInputDocument = new AgentFieldInput().createAgentSolrFields(element.getAgent(), solrInputDocument);
			}
			else if(element.ifAggregation()){
				solrInputDocument = new AggregationFieldInput().createAggregationSolrFields(element.getAggregation(), solrInputDocument);
				solrInputDocument = new ProxyFieldInput().addProxyForSolr(element.getAggregation(), solrInputDocument);
			}
			else if(element.ifConcept()){
				solrInputDocument = new ConceptFieldInput().createConceptSolrFields(element.getConcept(),solrInputDocument);
			}
			else if(element.ifPlace()){
				solrInputDocument = new PlaceFieldInput().createPlaceSolrFields(element.getPlace(),solrInputDocument) ;
			}
			else if(element.ifProvidedCHO()){
				solrInputDocument = new ProvidedCHOFieldInput().createProvidedCHOFields(element.getProvidedCHO(),solrInputDocument);
			}
			else if(element.ifProxy()){
				solrInputDocument = new ProxyFieldInput().createProxySolrFields(element.getProxy(), solrInputDocument);
			}
			else if (element.ifTimeSpan()) {
				solrInputDocument = new TimespanFieldInput().createTimespanSolrFields(element.getTimeSpan(), solrInputDocument);
			}
			else if(element.ifEuropeanaAggregation()){
				solrInputDocument = new EuropeanaAggregationFieldInput().createAggregationSolrFields(element.getEuropeanaAggregation(), solrInputDocument);
				solrInputDocument = new ProxyFieldInput().addProxyForSolr(element.getEuropeanaAggregation(), solrInputDocument);
			}
			else{
				solrInputDocument = new WebResourcesFieldInput().createWebResourceSolrFields(element.getWebResource(), solrInputDocument);
			}
		}
		return solrInputDocument;
	}
}
