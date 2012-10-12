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
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.common.SolrInputDocument;

import com.google.code.morphia.query.UpdateOperations;

import eu.europeana.corelib.definitions.jibx.AggregatedCHO;
import eu.europeana.corelib.definitions.jibx.Aggregation;
import eu.europeana.corelib.definitions.jibx.HasView;
import eu.europeana.corelib.definitions.jibx.IsShownAt;
import eu.europeana.corelib.definitions.jibx.IsShownBy;
import eu.europeana.corelib.definitions.jibx.Rights;
import eu.europeana.corelib.definitions.jibx._Object;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.definitions.solr.entity.WebResource;
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

	public AggregationFieldInput() {

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
	public SolrInputDocument createAggregationSolrFields(
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
	@SuppressWarnings("unchecked")
	public AggregationImpl appendWebResource(
			List<AggregationImpl> aggregations, WebResourceImpl webResource,
			EdmMongoServer mongoServer) throws InstantiationException,
			IllegalAccessException {
		AggregationImpl aggregation = findAggregation(aggregations, webResource);
		if (aggregation != null) {
			List<WebResourceImpl> webResources = (List<WebResourceImpl>) (aggregation.getWebResources() != null ? aggregation
					.getWebResources() : new ArrayList<WebResourceImpl>());
			mongoServer.getDatastore().save(webResource);
			webResources.add(webResource);
			aggregation.setWebResources(webResources);
			
			MongoUtils.update(AggregationImpl.class, aggregation.getAbout(),
					mongoServer, "webResources", webResources);
		}
		return aggregation;
	}

	public AggregationImpl appendWebResource(
			List<AggregationImpl> aggregations,
			List<WebResource> webResource, EdmMongoServer mongoServer)
			throws InstantiationException, IllegalAccessException {
		AggregationImpl aggregation = findAggregation(aggregations,
				webResource.get(0));
		for(WebResource wr : webResource){
			mongoServer.getDatastore().save(wr);
		}
		aggregation.setWebResources(webResource);
		MongoUtils.update(AggregationImpl.class, aggregation.getAbout(),
				mongoServer, "webResources", webResource);
		
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
	private AggregationImpl findAggregation(List<AggregationImpl> aggregations,
			WebResource webResource) {
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
		return new AggregationImpl();
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
	public AggregationImpl createAggregationMongoFields(
			eu.europeana.corelib.definitions.jibx.Aggregation aggregation,
			EdmMongoServer mongoServer) throws InstantiationException,
			IllegalAccessException {
		AggregationImpl mongoAggregation = new AggregationImpl();
		// mongoAggregation.setId(new ObjectId());
		mongoAggregation.setAbout(aggregation.getAbout());
		Map<String,List<String>> dp = MongoUtils
				.createResourceOrLiteralMapFromString(
						aggregation.getDataProvider());
		mongoAggregation.setEdmDataProvider(dp);
		UpdateOperations<AggregationImpl> ups =  mongoServer.getDatastore().createUpdateOperations(AggregationImpl.class);
		ups.set("edmDataProvider", dp);
		String isShownAt = SolrUtils.exists(IsShownAt.class,
				(aggregation.getIsShownAt())).getResource();
		mongoAggregation.setEdmIsShownAt(isShownAt);
		ups.set("edmIsShownAt",isShownAt);
		String isShownBy = SolrUtils.exists(IsShownBy.class,
				(aggregation.getIsShownBy())).getResource();
		mongoAggregation.setEdmIsShownBy(isShownBy);
		ups.set("edmIsShownBy",isShownBy);
		String object = SolrUtils.exists(_Object.class,
				(aggregation.getObject())).getResource();
		mongoAggregation.setEdmObject(object);
		ups.set("edmObject",object);
		
		Map<String,List<String>> prov = MongoUtils
				.createResourceOrLiteralMapFromString(
						aggregation.getProvider());
		mongoAggregation.setEdmProvider(prov);
		ups =  mongoServer.getDatastore().createUpdateOperations(AggregationImpl.class);
		ups.set("edmProvider", prov);
		Map<String,List<String>> rights = MongoUtils
				.createResourceOrLiteralMapFromString(
						aggregation.getRights());
		mongoAggregation.setEdmRights(rights);
		ups =  mongoServer.getDatastore().createUpdateOperations(AggregationImpl.class);
		ups.set("edmRights", rights);
		if (aggregation.getUgc() != null) {
			mongoAggregation
					.setEdmUgc(aggregation.getUgc().getUgc().toString());
		}
		
		String agCHO = SolrUtils.exists(AggregatedCHO.class,
				(aggregation.getAggregatedCHO())).getResource();
		mongoAggregation.setAggregatedCHO(agCHO);
		ups.set("aggregatedCHO",agCHO);
		
		
		Map<String,List<String>> rights1 = MongoUtils
				.createResourceOrLiteralMapFromList(
						aggregation.getRightList());
		mongoAggregation.setDcRights(rights1);
		ups =  mongoServer.getDatastore().createUpdateOperations(AggregationImpl.class);
		ups.set("dcRights", rights);
		if (aggregation.getHasViewList() != null) {
			List<String> hasViewList = new ArrayList<String>();
			for (HasView hasView : aggregation.getHasViewList()) {
				hasViewList.add(hasView.getResource());
			}
			mongoAggregation.setHasView(hasViewList
					.toArray(new String[hasViewList.size()]));
			ups.set("hasView", hasViewList.toArray(new String[hasViewList.size()]));
		}
		AggregationImpl retAggr = mongoServer.getDatastore()
				.find(AggregationImpl.class)
				.filter("about", aggregation.getAbout()).get();
		if (retAggr != null) {
			mongoServer.getDatastore().update(retAggr, ups);
		} else {
			mongoServer.getDatastore().save(mongoAggregation);
		}
		return mongoAggregation;
	}

	public void deleteAggregationFromMongo(String about,
			EdmMongoServer mongoServer) {
		MongoUtils.delete(Aggregation.class, about, mongoServer);
	}
}
