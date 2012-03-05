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

package eu.europeana.corelib.solr.server.importer.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.common.SolrInputDocument;

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
import eu.europeana.corelib.solr.utils.MongoUtil;
import eu.europeana.corelib.solr.utils.SolrUtil;

/**
 * Constructor for an Aggregation TODO:update/delete
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
public class AggregationFieldInput {
	/**
	 * Fill in a SolrInputDocument with Aggregation specific fields
	 * 
	 * @param aggregation
	 *            A JiBX entity that represents an Aggregation
	 * @param solrInputDocument
	 *            The SolrInputDocument to alter
	 * @return The SolrInputDocument with Aggregation specific values
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
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

	/**
	 * Append a List of Webresources to an aggregation TODO: what happens when
	 * there are more than one aggregations
	 * 
	 * @param aggregations
	 *            The List of aggregations in a record
	 * @param webResources
	 *            The List of webresources to be appended to an aggregation
	 * @param mongoServer
	 *            The instance of the MongoDBServer the aggregation is going to
	 *            be saved
	 * @return The aggregation Object that was altered (???to be converted to
	 *         ArrayList of aggregations???)
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static AggregationImpl appendWebResource(
			List<AggregationImpl> aggregations,
			List<WebResourceImpl> webResources, MongoDBServer mongoServer)
			throws InstantiationException, IllegalAccessException {
		AggregationImpl aggregation = findAggregation(aggregations,
				webResources.get(0));

		aggregation.setWebResources(webResources);
		MongoUtil.update(AggregationImpl.class, aggregation.getAbout(), mongoServer,
				"webResources", webResources);

	
		return aggregation;
	}

	/**
	 * Retrieve the aggregation in which the webresources will be saved in
	 * 
	 * @param aggregations
	 *            The list of aggregations to search on
	 * @param webResource
	 *            The webresource from which the aggregation is extracted
	 * @return The aggregation that is going to be altered
	 */
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

	/**
	 * Create an Aggregation MongoDBEntity. The webresources are not inserted at
	 * this method. as the aggregation may be created before the web resources
	 * are encountered in the EDM parsing or after.
	 * 
	 * @param aggregation
	 *            The JiBX Aggregation entity
	 * @param mongoServer
	 *            The mongoServer instance that Aggregation is going to be saved
	 *            in
	 * @return The MongoDB Aggregation entity
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
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
				(aggregation.getObject())).getResource());
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
		if (mongoServer.searchByAbout(AggregationImpl.class, mongoAggregation.getAbout())!=null){
			mongoServer.getDatastore().delete(AggregationImpl.class,mongoAggregation.getAbout());
		} 
		mongoServer.getDatastore().save(mongoAggregation);
		return mongoAggregation;
	}

	public static void deleteAggregationFromMongo(String about, MongoDBServer mongoServer){
		MongoUtil.delete(Aggregation.class,about,mongoServer);
	}
}
