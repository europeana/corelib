package eu.europeana.corelib.solr.server.importer.util;

import org.apache.solr.common.SolrInputDocument;

import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.definitions.jibx.Rights;
import eu.europeana.corelib.definitions.jibx.Rights1;
import eu.europeana.corelib.definitions.jibx.WebResourceType;
import eu.europeana.corelib.solr.utils.SolrUtil;

public class WebResourcesFieldInput {
	public static SolrInputDocument createWebResourceSolrFields(WebResourceType webResource,SolrInputDocument solrInputDocument) throws InstantiationException, IllegalAccessException{
		solrInputDocument.addField(EdmLabel.EDM_WEB_RESOURCE.toString(), webResource.getAbout());
		solrInputDocument.addField(EdmLabel.WR_EDM_RIGHTS.toString(), SolrUtil.exists(Rights.class, (webResource.getRights())).getString());
		if(webResource.getRightList()!=null){
		for(Rights1 dcRights : webResource.getRightList()){
			solrInputDocument.addField(EdmLabel.WR_DC_RIGHTS.toString(), dcRights.getString());
		}
		}
		return solrInputDocument;
	}
	
}
