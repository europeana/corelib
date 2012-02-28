package eu.europeana.corelib.solr.server.importer.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.common.SolrInputDocument;

import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;

import eu.europeana.corelib.definitions.jibx.AggregatedCHO;
import eu.europeana.corelib.definitions.jibx.Aggregation;
import eu.europeana.corelib.definitions.jibx.DataProvider;
import eu.europeana.corelib.definitions.jibx.HasView;
import eu.europeana.corelib.definitions.jibx.IsShownAt;
import eu.europeana.corelib.definitions.jibx.IsShownBy;
import eu.europeana.corelib.definitions.jibx.Provider;
import eu.europeana.corelib.definitions.jibx.Rights;
import eu.europeana.corelib.definitions.jibx.Rights1;
import eu.europeana.corelib.definitions.jibx._Object;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.solr.entity.AggregationImpl;
import eu.europeana.corelib.solr.entity.WebResourceImpl;
import eu.europeana.corelib.solr.server.MongoDBServer;
import eu.europeana.corelib.solr.utils.SolrUtil;

public class AggregationFieldInput {

	public static SolrInputDocument createAggregationSolrFields(
			Aggregation aggregation, SolrInputDocument solrInputDocument)
			throws InstantiationException, IllegalAccessException {

		solrInputDocument.addField(EdmLabel.ORE_AGGREGATION.toString(),
				aggregation.getAbout());
		solrInputDocument.addField(
				EdmLabel.EDM_AGGREGATED_CHO.toString(),
				SolrUtil.exists(AggregatedCHO.class,
						(aggregation.getAggregatedCHO())).getResource());
		solrInputDocument
				.addField(
						EdmLabel.EDM_OBJECT.toString(),
						SolrUtil.exists(_Object.class,
								(aggregation.getObject())).getResource());
		solrInputDocument.addField(
				EdmLabel.EDM_DATA_PROVIDER.toString(),
				SolrUtil.exists(DataProvider.class,
						((aggregation.getDataProvider()))).getString());
		solrInputDocument.addField(EdmLabel.EDM_PROVIDER.toString(), SolrUtil
				.exists(Provider.class, (aggregation.getProvider()))
				.getString());
		solrInputDocument.addField(EdmLabel.EDM_IS_SHOWN_AT.toString(),
				SolrUtil.exists(IsShownAt.class, (aggregation.getIsShownAt()))
						.getResource());
		solrInputDocument.addField(EdmLabel.EDM_IS_SHOWN_BY.toString(),
				SolrUtil.exists(IsShownBy.class, (aggregation.getIsShownBy()))
						.getResource());
		solrInputDocument.addField(EdmLabel.AGGR_EDM_RIGHTS.toString(),
				SolrUtil.exists(Rights.class, (aggregation.getRights()))
						.getString());
		if (aggregation.getUgc() != null) {
			solrInputDocument.addField(EdmLabel.EDM_UGC.toString(), aggregation
					.getUgc().getUgc().toString());
		}
		if (aggregation.getRightList() != null) {
			for (Rights1 rights : aggregation.getRightList()) {
				solrInputDocument.addField(EdmLabel.AGGR_DC_RIGHTS.toString(),
						rights.getString());
			}
		}
		if (aggregation.getHasViewList() != null) {
			for (HasView hasView : aggregation.getHasViewList()) {
				solrInputDocument.addField(EdmLabel.EDM_HASVIEW.toString(),
						hasView.getResource());
			}
		}
		return solrInputDocument;
	}

	public static AggregationImpl appendWebResource(
			List<AggregationImpl> aggregations, List<WebResourceImpl> webResources,
			MongoDBServer mongoServer) throws InstantiationException,
			IllegalAccessException {
		AggregationImpl aggregation=findAggregation(aggregations, webResources.get(0));
			
		aggregation.setWebResources(webResources);

		UpdateOperations<AggregationImpl> ops = mongoServer.getDatastore()
				.createUpdateOperations(AggregationImpl.class)
				.set("webResources", webResources);
		Query<AggregationImpl> query = mongoServer.getDatastore().find(AggregationImpl.class).filter("about", aggregation.getAbout());
		mongoServer.getDatastore().update(query, ops);
		return aggregation;
	}



	private static AggregationImpl findAggregation(
			List<AggregationImpl> aggregations, WebResourceImpl webResource) {
		for (AggregationImpl aggregation : aggregations) {
			for (String hasView : aggregation.getHasView()) {
				if (StringUtils.equals(hasView, webResource.getAbout())) {
					return aggregation;
				}
			}
		}
		return null;
	}

	public static AggregationImpl createAggregationMongoFields(
			eu.europeana.corelib.definitions.jibx.Aggregation aggregation,
			MongoDBServer mongoServer) throws InstantiationException,
			IllegalAccessException {
		AggregationImpl mongoAggregation = new AggregationImpl();
		mongoAggregation.setAbout(aggregation.getAbout());

		mongoAggregation.setEdmDataProvider(SolrUtil.exists(DataProvider.class,
				(aggregation.getDataProvider())).getString());
		mongoAggregation.setEdmIsShownAt(SolrUtil.exists(IsShownAt.class,
				(aggregation.getIsShownAt())).getResource());
		mongoAggregation.setEdmIsShownBy(SolrUtil.exists(IsShownBy.class,
				(aggregation.getIsShownBy())).getResource());
		mongoAggregation.setEdmObject(SolrUtil.exists(_Object.class,
				(aggregation.getObject())).toString());
		mongoAggregation.setEdmProvider(SolrUtil.exists(Provider.class,
				(aggregation.getProvider())).getString());
		mongoAggregation.setEdmRights(SolrUtil.exists(Rights.class,
				(aggregation.getRights())).getString());
		if (aggregation.getUgc() != null) {
			mongoAggregation
					.setEdmUgc(aggregation.getUgc().getUgc().toString());
		}
		mongoAggregation.setAggregatedCHO(SolrUtil.exists(AggregatedCHO.class,
				(aggregation.getAggregatedCHO())).getResource());
		if (aggregation.getRightList() != null) {
			List<String> dcRightsList = new ArrayList<String>();
			for (Rights1 rights : aggregation.getRightList()) {
				dcRightsList.add(rights.getString());
			}

			mongoAggregation.setHasView(dcRightsList
					.toArray(new String[dcRightsList.size()]));
		}

		if (aggregation.getHasViewList() != null) {
			List<String> hasViewList = new ArrayList<String>();
			for (HasView hasView : aggregation.getHasViewList()) {
				hasViewList.add(hasView.getResource());
			}
			mongoAggregation.setHasView(hasViewList
					.toArray(new String[hasViewList.size()]));

		}
		try{
		mongoServer.getDatastore().save(mongoAggregation);
		}
		catch(Exception e){
			//DUP UNIQUE IDENTIFIER
		}
		return mongoAggregation;
	}

}
