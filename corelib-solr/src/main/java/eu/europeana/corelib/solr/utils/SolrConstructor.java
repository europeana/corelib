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
	static SolrInputDocument solrInputDocument;
	
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
	public static SolrInputDocument constructSolrDocument(RDF rdf, boolean shouldDereference) throws InstantiationException, IllegalAccessException, MalformedURLException, IOException{
		solrInputDocument = new SolrInputDocument();
		
		for(Choice element: rdf.getChoiceList()){
			if(element.ifAgent()){
				solrInputDocument = AgentFieldInput.createAgentSolrFields(element.getAgent(), solrInputDocument);
				
			}
			else if(element.ifAggregation()){
				solrInputDocument = AggregationFieldInput.createAggregationSolrFields(element.getAggregation(), solrInputDocument);
				solrInputDocument = ProxyFieldInput.addProxyForSolr(element.getAggregation(), solrInputDocument);
			}
			else if(element.ifConcept()){
				solrInputDocument = ConceptFieldInput.createConceptSolrFields(element.getConcept(),solrInputDocument);
				
			}
			else if(element.ifPlace()){
				solrInputDocument = PlaceFieldInput.createPlaceSolrFields(element.getPlace(),solrInputDocument) ;
				
			}
			else if(element.ifProvidedCHO()){
				
				solrInputDocument = ProvidedCHOFieldInput.createProvidedCHOFields(element.getProvidedCHO(),solrInputDocument);
				solrInputDocument = ProxyFieldInput.createProxySolrFields(element.getProxy(), solrInputDocument, rdf, shouldDereference);
			}
		
			else if (element.ifTimeSpan()) {
				solrInputDocument = TimespanFieldInput.createTimespanSolrFields(element.getTimeSpan(), solrInputDocument);
				
			}
			else{
				solrInputDocument = WebResourcesFieldInput.createWebResourceSolrFields(element.getWebResource(), solrInputDocument);
			}
		}
		return solrInputDocument;
	}
}
