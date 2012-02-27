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
import eu.europeana.corelib.definitions.jibx.WebResourceType;
import eu.europeana.corelib.definitions.jibx._Object;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.definitions.solr.entity.WebResource;
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
		solrInputDocument
				.addField(
						EdmLabel.EDM_PROVIDER.toString(),
						SolrUtil.exists(Provider.class,
								(aggregation.getProvider())).getString());
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

	public static List<AggregationImpl> appendWebResource(
			List<AggregationImpl> aggregations, WebResourceType webResource,
			MongoDBServer mongoServer) throws InstantiationException,
			IllegalAccessException {

		eu.europeana.corelib.definitions.solr.entity.Aggregation aggregation = findAggregation(
				aggregations, webResource);
		WebResource mongoWebResource = new WebResourceImpl();
		mongoWebResource.setAbout(webResource.getAbout());
		if (aggregation.getHasView() != null) {
			List<String> hasViewList = new ArrayList<String>();
			for (String hasView : aggregation.getHasView()) {
				hasViewList.add(hasView);
			}
			hasViewList.add(mongoWebResource.getAbout());
			aggregation.setHasView(hasViewList.toArray(new String[hasViewList
					.size()]));
		}
		mongoWebResource.setWebResourceEdmRights(SolrUtil.exists(Rights.class,
				webResource.getRights()).getResource());
		if (webResource.getRightList() != null) {
			List<String> dcRights = new ArrayList<String>();
			for (Rights1 rights : webResource.getRightList()) {
				dcRights.add(rights.getString());
			}
			mongoWebResource.setWebResourceDcRights(dcRights
					.toArray(new String[dcRights.size()]));
		}
		mongoServer.getDatastore().save(mongoWebResource);
		Query<AggregationImpl> query = mongoServer.getDatastore()
				.find(AggregationImpl.class).field("about")
				.equal(aggregation.getAbout());
		AggregationImpl updateAggregation = query.get();
		List<WebResource> webResources = updateAggregation.getWebResources();
		webResources.add(mongoWebResource);
		UpdateOperations<AggregationImpl> ops = mongoServer.getDatastore()
				.createUpdateOperations(AggregationImpl.class)
				.set("webResource", webResources);
		mongoServer.getDatastore().update(query, ops);
		return aggregations;
	}

	public static AggregationImpl createMongoAggregationFromWebResource(
			WebResourceType webResource, MongoDBServer mongoServer)
			throws InstantiationException, IllegalAccessException {
		AggregationImpl aggregation = new AggregationImpl();
		WebResource mongoWebResource = new WebResourceImpl();
		mongoWebResource.setAbout(webResource.getAbout());

		aggregation.setHasView(new String[] { webResource.getAbout() });

		mongoWebResource.setWebResourceEdmRights(SolrUtil.exists(Rights.class,
				webResource.getRights()).getResource());

		if (webResource.getRightList() != null) {
			List<String> dcRights = new ArrayList<String>();
			for (Rights1 rights : webResource.getRightList()) {
				dcRights.add(rights.getString());
			}
			mongoWebResource.setWebResourceDcRights(dcRights
					.toArray(new String[dcRights.size()]));
		}
		mongoServer.getDatastore().save(mongoWebResource);
		List<WebResource> webResources = new ArrayList<WebResource>();
		webResources.add(mongoWebResource);
		aggregation.setWebResources(webResources);
		mongoServer.getDatastore().save(webResources);
		mongoServer.getDatastore().save(aggregation);
		return aggregation;
	}

	private static eu.europeana.corelib.definitions.solr.entity.Aggregation findAggregation(
			List<AggregationImpl> aggregations, WebResourceType webResource) {
		for (eu.europeana.corelib.definitions.solr.entity.Aggregation aggregation : aggregations) {
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
		mongoServer.getDatastore().save(mongoAggregation);
		return mongoAggregation;
	}

}
