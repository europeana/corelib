package eu.europeana.corelib.solr.server.importer.util;

import org.apache.solr.common.SolrInputDocument;

import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.definitions.jibx.Rights1;
import eu.europeana.corelib.definitions.jibx.WebResourceType;

public class WebResourcesFieldInput {
	public static SolrInputDocument createWebResourceSolrFields(WebResourceType webResource,SolrInputDocument solrInputDocument){
		solrInputDocument.addField(EdmLabel.EDM_WEB_RESOURCE.toString(), webResource.getAbout());
		solrInputDocument.addField(EdmLabel.WR_EDM_RIGHTS.toString(), webResource.getRights().getString());
		for(Rights1 dcRights : webResource.getRightList()){
			solrInputDocument.addField(EdmLabel.WR_DC_RIGHTS.toString(), dcRights.getString());
		}
		return solrInputDocument;
	}
	
}
