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
import eu.europeana.corelib.definitions.jibx.HasView;
import eu.europeana.corelib.definitions.jibx.IsShownAt;
import eu.europeana.corelib.definitions.jibx.IsShownBy;
import eu.europeana.corelib.definitions.jibx.Rights;
import eu.europeana.corelib.definitions.jibx._Object;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.solr.MongoServer;
import eu.europeana.corelib.solr.entity.AggregationImpl;
import eu.europeana.corelib.solr.entity.WebResourceImpl;
import eu.europeana.corelib.solr.server.EdmMongoServer;
import eu.europeana.corelib.solr.utils.MongoUtils;
import eu.europeana.corelib.solr.utils.SolrUtils;

/**
 * Constructor for an Aggregation 
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
public final class AggregationFieldInput {

	private AggregationFieldInput() {

	}

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

		solrInputDocument.addField(
				EdmLabel.PROVIDER_AGGREGATION_ORE_AGGREGATION.toString(),
				aggregation.getAbout());
		solrInputDocument.addField(
				EdmLabel.PROVIDER_AGGREGATION_EDM_AGGREGATED_CHO.toString(),
				SolrUtils.exists(AggregatedCHO.class,
						(aggregation.getAggregatedCHO())).getResource());
		solrInputDocument.addField(EdmLabel.PROVIDER_AGGREGATION_EDM_OBJECT
				.toString(),
				SolrUtils.exists(_Object.class, (aggregation.getObject()))
						.getResource());
		solrInputDocument = SolrUtils.addFieldFromResourceOrLiteral(
				solrInputDocument, aggregation.getDataProvider(),
				EdmLabel.PROVIDER_AGGREGATION_EDM_DATA_PROVIDER);
		solrInputDocument = SolrUtils.addFieldFromResourceOrLiteral(
				solrInputDocument, aggregation.getProvider(),
				EdmLabel.PROVIDER_AGGREGATION_EDM_PROVIDER);
		solrInputDocument.addField(
				EdmLabel.PROVIDER_AGGREGATION_EDM_IS_SHOWN_AT.toString(),
				SolrUtils.exists(IsShownAt.class, (aggregation.getIsShownAt()))
						.getResource());
		solrInputDocument.addField(
				EdmLabel.PROVIDER_AGGREGATION_EDM_IS_SHOWN_BY.toString(),
				SolrUtils.exists(IsShownBy.class, (aggregation.getIsShownBy()))
						.getResource());
		solrInputDocument = SolrUtils.addFieldFromResourceOrLiteral(
				solrInputDocument, aggregation.getRights(),
				EdmLabel.PROVIDER_AGGREGATION_EDM_RIGHTS);
		if (aggregation.getUgc() != null) {
			solrInputDocument.addField(EdmLabel.EDM_UGC.toString(), aggregation
					.getUgc().getUgc().toString());
		}
		if (aggregation.getRightList() != null) {
			for (Rights rights : aggregation.getRightList()) {
				solrInputDocument = SolrUtils.addFieldFromResourceOrLiteral(
						solrInputDocument, rights,
						EdmLabel.PROVIDER_AGGREGATION_DC_RIGHTS);
			}
		}
		if (aggregation.getHasViewList() != null) {
			for (HasView hasView : aggregation.getHasViewList()) {
				solrInputDocument.addField(
						EdmLabel.PROVIDER_AGGREGATION_EDM_HASVIEW.toString(),
						hasView.getResource());
			}
		}

		return solrInputDocument;
	}

	/**
	 * Append a List of Webresources to an aggregation 
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
			List<WebResourceImpl> webResources, MongoServer mongoServer)
			throws InstantiationException, IllegalAccessException {
		AggregationImpl aggregation = findAggregation(aggregations,
				webResources.get(0));

		aggregation.setWebResources(webResources);
		MongoUtils.update(AggregationImpl.class, aggregation.getAbout(),
				mongoServer, "webResources", webResources);

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
			if (aggregation.getHasView() != null) {
				for (String hasView : aggregation.getHasView()) {
					if (StringUtils.equals(hasView, webResource.getAbout())) {
						return aggregation;
					}
				}
			}
			if (aggregation.getEdmIsShownAt() != null) {
				if (StringUtils.equals(aggregation.getEdmIsShownAt(),
						webResource.getAbout())) {
					return aggregation;
				}
			}
			if (aggregation.getEdmIsShownBy() != null) {
				if (StringUtils.equals(aggregation.getEdmIsShownBy(),
						webResource.getAbout())) {
					return aggregation;
				}
			}
			if (aggregation.getEdmObject() != null) {
				if (StringUtils.equals(aggregation.getEdmObject(),
						webResource.getAbout())) {
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
			MongoServer mongoServer) throws InstantiationException,
			IllegalAccessException {
		AggregationImpl mongoAggregation = new AggregationImpl();
	//	mongoAggregation.setId(new ObjectId());
		mongoAggregation.setAbout(aggregation.getAbout());
		mongoAggregation.setEdmDataProvider(MongoUtils.createResourceOrLiteralMapFromString(aggregation.getDataProvider()));
		mongoAggregation.setEdmIsShownAt(SolrUtils.exists(IsShownAt.class,
				(aggregation.getIsShownAt())).getResource());
		mongoAggregation.setEdmIsShownBy(SolrUtils.exists(IsShownBy.class,
				(aggregation.getIsShownBy())).getResource());
		mongoAggregation.setEdmObject(SolrUtils.exists(_Object.class,
				(aggregation.getObject())).getResource());
		mongoAggregation.setEdmProvider(MongoUtils.createResourceOrLiteralMapFromString(
				aggregation.getProvider()));
		mongoAggregation.setEdmRights(MongoUtils.createResourceOrLiteralMapFromString(aggregation.getRights()));

		if (aggregation.getUgc() != null) {
			mongoAggregation
					.setEdmUgc(aggregation.getUgc().getUgc().toString());
		}
		mongoAggregation.setAggregatedCHO(SolrUtils.exists(AggregatedCHO.class,
				(aggregation.getAggregatedCHO())).getResource());
		mongoAggregation.setDcRights(MongoUtils.createResourceOrLiteralMapFromList(aggregation.getRightList()));


		if (aggregation.getHasViewList() != null) {
			List<String> hasViewList = new ArrayList<String>();
			for (HasView hasView : aggregation.getHasViewList()) {
				hasViewList.add(hasView.getResource());
			}
			mongoAggregation.setHasView(hasViewList
					.toArray(new String[hasViewList.size()]));

		}
		if (((EdmMongoServer) mongoServer).searchByAbout(AggregationImpl.class,
				mongoAggregation.getAbout()) != null) {
			// TODO:update Aggregation
		} else {
			mongoServer.getDatastore().save(mongoAggregation);
		}
		return mongoAggregation;
	}

	public static void deleteAggregationFromMongo(String about,
			EdmMongoServer mongoServer) {
		MongoUtils.delete(Aggregation.class, about, mongoServer);
	}
}
