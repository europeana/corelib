package eu.europeana.corelib.solr.server.importer.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.common.SolrInputDocument;

import eu.europeana.corelib.definitions.jibx.Rights;
import eu.europeana.corelib.definitions.jibx.Rights1;
import eu.europeana.corelib.definitions.jibx.WebResourceType;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.solr.entity.WebResourceImpl;
import eu.europeana.corelib.solr.server.MongoDBServer;
import eu.europeana.corelib.solr.utils.SolrUtil;

public class WebResourcesFieldInput {
	public static SolrInputDocument createWebResourceSolrFields(WebResourceType webResource,SolrInputDocument solrInputDocument) throws InstantiationException, IllegalAccessException{
		solrInputDocument.addField(EdmLabel.EDM_WEB_RESOURCE.toString(), webResource.getAbout());
		solrInputDocument.addField(EdmLabel.WR_EDM_RIGHTS.toString(), SolrUtil.exists(Rights.class, (webResource.getRights())).getResource());
		if(webResource.getRightList()!=null){
		for(Rights1 dcRights : webResource.getRightList()){
			solrInputDocument.addField(EdmLabel.WR_DC_RIGHTS.toString(), dcRights.getString());
		}
		}
		return solrInputDocument;
	}

	public static WebResourceImpl createWebResourceMongoField(
			WebResourceType webResource, MongoDBServer mongoServer) {
		WebResourceImpl mongoWebResource= new WebResourceImpl();
		mongoWebResource.setAbout(webResource.getAbout());
		if (webResource.getRights()!=null){
			mongoWebResource.setWebResourceEdmRights(webResource.getRights().getResource());
		}
		
		List<String> dcRightsList = new ArrayList<String>();
		if(webResource.getRightList()!=null){
			for (Rights1 dcRights:webResource.getRightList()){
				dcRightsList.add(dcRights.getResource());
			}
		}
		mongoWebResource.setWebResourceDcRights(dcRightsList.toArray(new String[dcRightsList.size()]));
		return mongoWebResource;
	}
	
}
