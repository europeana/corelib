package eu.europeana.corelib.solr.server.importer.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.common.SolrInputDocument;

import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;

import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.definitions.solr.entity.WebResource;
import eu.europeana.corelib.solr.entity.AggregationImpl;
import eu.europeana.corelib.solr.entity.WebResourceImpl;
import eu.europeana.corelib.solr.server.MongoDBServer;
import eu.europeana.corelib.definitions.jibx.Aggregation;
import eu.europeana.corelib.definitions.jibx.HasView;
import eu.europeana.corelib.definitions.jibx.Rights1;
import eu.europeana.corelib.definitions.jibx.WebResourceType;

public class AggregationFieldInput {

	public static SolrInputDocument createAggregationSolrFields(Aggregation aggregation,SolrInputDocument solrInputDocument){
		
		solrInputDocument.addField(EdmLabel.ORE_AGGREGATION.toString(), aggregation.getAbout());
		solrInputDocument.addField(EdmLabel.EDM_AGGREGATED_CHO.toString(), aggregation.getAggregatedCHO().toString());
		solrInputDocument.addField(EdmLabel.EDM_OBJECT.toString(), aggregation.getObject().toString());
		solrInputDocument.addField(EdmLabel.EDM_DATA_PROVIDER.toString(), aggregation.getDataProvider().toString());
		solrInputDocument.addField(EdmLabel.EDM_PROVIDER.toString(), aggregation.getProvider().toString());
		solrInputDocument.addField(EdmLabel.EDM_IS_SHOWN_AT.toString(), aggregation.getIsShownAt().toString());
		solrInputDocument.addField(EdmLabel.EDM_IS_SHOWN_BY.toString(), aggregation.getIsShownBy().toString());
		solrInputDocument.addField(EdmLabel.AGGR_EDM_RIGHTS.toString(), aggregation.getRights().getString());
		solrInputDocument.addField(EdmLabel.EDM_UGC.toString(), aggregation.getUgc().toString());
		for(Rights1 rights : aggregation.getRightList()){
			solrInputDocument.addField(EdmLabel.AGGR_DC_RIGHTS.toString(), rights.toString());
		}
		for(HasView hasView : aggregation.getHasViewList()){
			solrInputDocument.addField(EdmLabel.EDM_HASVIEW.toString(), hasView.toString());
		}
		return solrInputDocument;
	}

	public static List<eu.europeana.corelib.definitions.solr.entity.Aggregation> appendWebResource(
			List<eu.europeana.corelib.definitions.solr.entity.Aggregation> aggregations,
			WebResourceType webResource, MongoDBServer mongoServer) {
		
		eu.europeana.corelib.definitions.solr.entity.Aggregation aggregation =findAggregation(aggregations,webResource);
		WebResource mongoWebResource = new WebResourceImpl();
		mongoWebResource.setAbout(webResource.getAbout());
		List<String> hasViewList = new ArrayList<String>();
		for(String hasView:aggregation.getHasView()){
			hasViewList.add(hasView);
		}
		hasViewList.add(mongoWebResource.getAbout());
		aggregation.setHasView(hasViewList.toArray(new String[hasViewList.size()]));
		mongoWebResource.setWebResourceEdmRights(webResource.getRights().getString());
		List<String> dcRights = new ArrayList<String>();
		for(Rights1 rights : webResource.getRightList()){
			dcRights.add(rights.getString());
		}
		mongoWebResource.setWebResourceDcRights(dcRights.toArray(new String[dcRights.size()]));
		mongoServer.getDatastore().save(mongoWebResource);
		Query<AggregationImpl> query = mongoServer.getDatastore().find(AggregationImpl.class).field("about").equal(aggregation.getAbout());
		AggregationImpl updateAggregation = query.get();
		List<WebResource> webResources = updateAggregation.getWebResources();
		webResources.add(mongoWebResource);
		UpdateOperations<AggregationImpl> ops = mongoServer.getDatastore().createUpdateOperations(AggregationImpl.class).set("webResource", webResources);
		mongoServer.getDatastore().update(query, ops);
		return aggregations;
	}

	public static eu.europeana.corelib.definitions.solr.entity.Aggregation createMongoAggregationFromWebResource(
			WebResourceType webResource, MongoDBServer mongoServer) {
		eu.europeana.corelib.definitions.solr.entity.Aggregation aggregation = new AggregationImpl();
		WebResource mongoWebResource = new WebResourceImpl();
		mongoWebResource.setAbout(webResource.getAbout());
		
		
		aggregation.setHasView(new String[]{webResource.getAbout()});
		
		mongoWebResource.setWebResourceEdmRights(webResource.getRights().getString());
		List<String> dcRights = new ArrayList<String>();
		for(Rights1 rights : webResource.getRightList()){
			dcRights.add(rights.getString());
		}
		mongoWebResource.setWebResourceDcRights(dcRights.toArray(new String[dcRights.size()]));
		mongoServer.getDatastore().save(mongoWebResource);
		List<WebResource> webResources  = new ArrayList<WebResource>();
		webResources.add(mongoWebResource);
		aggregation.setWebResources(webResources);
		mongoServer.getDatastore().save(webResources);
		mongoServer.getDatastore().save(aggregation);
		return aggregation;
	}
	
	private static eu.europeana.corelib.definitions.solr.entity.Aggregation findAggregation(List<eu.europeana.corelib.definitions.solr.entity.Aggregation> aggregations, WebResourceType webResource){
		for(eu.europeana.corelib.definitions.solr.entity.Aggregation aggregation :aggregations){
			for(String hasView:aggregation.getHasView()){
				if(StringUtils.equals(hasView, webResource.getAbout())){
					return aggregation;
				}
			}
		}
		return null;
	}

	
	public static eu.europeana.corelib.definitions.solr.entity.Aggregation createAggregationMongoFields(
			eu.europeana.corelib.definitions.jibx.Aggregation aggregation,
			MongoDBServer mongoServer) {
		eu.europeana.corelib.definitions.solr.entity.Aggregation mongoAggregation = new AggregationImpl();
		mongoAggregation.setAbout(aggregation.getAbout());
		mongoAggregation.setEdmDataProvider(aggregation.getDataProvider().getString());
		mongoAggregation.setEdmIsShownAt(aggregation.getIsShownAt().getResource());
		mongoAggregation.setEdmIsShownBy(aggregation.getIsShownBy().getResource());
		mongoAggregation.setEdmObject(aggregation.getObject().toString());
		mongoAggregation.setEdmProvider(aggregation.getProvider().getString());
		mongoAggregation.setEdmRights(aggregation.getRights().getString());
		mongoAggregation.setEdmUgc(aggregation.getUgc().getUgc().toString());
		List<String> dcRightsList = new ArrayList<String>();
		for(Rights1 rights : aggregation.getRightList()){
			dcRightsList.add(rights.getString());
		}
		mongoAggregation.setHasView(dcRightsList.toArray(new String[dcRightsList.size()]));
		List<String> hasViewList = new ArrayList<String>();
		for(HasView hasView:aggregation.getHasViewList()){
			hasViewList.add(hasView.getResource());
		}
		mongoAggregation.setHasView(hasViewList.toArray(new String[hasViewList.size()]));
		mongoServer.getDatastore().save(mongoAggregation);
		return mongoAggregation;
	}
	
	
}
