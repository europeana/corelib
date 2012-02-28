package eu.europeana.corelib.solr.util;

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

public class SolrConstructor {
	static SolrInputDocument solrInputDocument;
	
	public static SolrInputDocument constructSolrDocument(RDF rdf) throws InstantiationException, IllegalAccessException{
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
				solrInputDocument = ProxyFieldInput.createProxySolrFields(element.getProvidedCHO(), solrInputDocument);
			}
		
			else if (element.ifTimeSpan()) {
				solrInputDocument = TimespanFieldInput.createTimespanSolrFields(element.getTimeSpan(), solrInputDocument);
				
			}
			else{
				solrInputDocument = WebResourcesFieldInput.createWebResourceSolrFields(element.getWebResource(), solrInputDocument);
			}
		}
		rdf=null;
		return solrInputDocument;
	}
}
