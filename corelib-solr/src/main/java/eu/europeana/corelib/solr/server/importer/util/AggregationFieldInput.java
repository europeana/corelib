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

import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;

import eu.europeana.corelib.definitions.jibx.AggregatedCHO;
import eu.europeana.corelib.definitions.jibx.Aggregation;
import eu.europeana.corelib.definitions.jibx.HasView;
import eu.europeana.corelib.definitions.jibx.IsShownAt;
import eu.europeana.corelib.definitions.jibx.IsShownBy;
import eu.europeana.corelib.definitions.jibx.Rights;
import eu.europeana.corelib.definitions.jibx.WebResourceType;
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
	 * Create a web resource
	 * @param wResources
	 * @param mongoServer
	 * @return
	 */
	public List<WebResourceImpl> createWebResources(
			List<WebResourceType> wResources, EdmMongoServer mongoServer) {
		List<WebResourceImpl> webResources = new ArrayList<WebResourceImpl>();
		for (WebResourceType wResourceType : wResources) {
			WebResourceImpl webResource = new WebResourceImpl();
			webResource.setAbout(wResourceType.getAbout());
			Query<WebResourceImpl> updateQuery = mongoServer.getDatastore()
					.createQuery(WebResourceImpl.class).field("about").equal(webResource.getAbout());
			UpdateOperations<WebResourceImpl> ups = mongoServer.getDatastore()
					.createUpdateOperations(WebResourceImpl.class);
			Map<String, List<String>> desMap = MongoUtils
					.createResourceOrLiteralMapFromList(wResourceType
							.getDescriptionList());
			if (desMap != null) {
				ups.set("dcDescription", desMap);
				webResource.setDcDescription(desMap);
			}
			
			Map<String, List<String>> forMap = MongoUtils
					.createResourceOrLiteralMapFromList(wResourceType
							.getFormatList());
			if(forMap!=null){
			ups.set("dcFormat", forMap);
			webResource.setDcFormat(forMap);
			}
			Map<String, List<String>> sourceMap = MongoUtils
					.createResourceOrLiteralMapFromList(wResourceType
							.getSourceList());
			if(sourceMap!=null){
			ups.set("dcSource", sourceMap);
			webResource.setDcSource(sourceMap);
			}
			Map<String, List<String>> conformsToMap = MongoUtils
					.createResourceOrLiteralMapFromList(wResourceType
							.getConformsToList());
			if(conformsToMap!=null){
			ups.set("dctermsConformsTo", conformsToMap);
			webResource.setDctermsConformsTo(conformsToMap);
			}
			Map<String, List<String>> createdMap = MongoUtils
					.createResourceOrLiteralMapFromList(wResourceType
							.getCreatedList());
			if(createdMap!=null){
			ups.set("dctermsCreated", createdMap);
			webResource.setDctermsCreated(createdMap);
			}
			Map<String, List<String>> extentMap = MongoUtils
					.createResourceOrLiteralMapFromList(wResourceType
							.getExtentList());
			if(extentMap!=null){
			ups.set("dctermsExtent", extentMap);
			webResource.setDctermsExtent(extentMap);
			}
			
			Map<String, List<String>> hasPartMap = MongoUtils
					.createResourceOrLiteralMapFromList(wResourceType
							.getHasPartList());
			if(hasPartMap!=null){
			ups.set("dctermsHasPart", hasPartMap);
			webResource.setDctermsHasPart(hasPartMap);
			}
			Map<String, List<String>> issuedMap = MongoUtils
					.createResourceOrLiteralMapFromList(wResourceType
							.getIssuedList());
			if(issuedMap!=null){
			ups.set("dctermsIssued", issuedMap);
			webResource.setDctermsIssued(issuedMap);
			}
			Map<String, List<String>> rightMap = MongoUtils
					.createResourceOrLiteralMapFromList(wResourceType
							.getRightList());
			if(rightMap!=null){
			ups.set("webResourceDcRights", rightMap);
			webResource.setWebResourceDcRights(rightMap);
			}
			Map<String, List<String>> edmRightsMap = MongoUtils
					.createResourceOrLiteralMapFromString(wResourceType
							.getRights());
			if(edmRightsMap!=null){
			ups.set("webResourceEdmRights", edmRightsMap);
			webResource.setWebResourceEdmRights(edmRightsMap);
			}
			if(wResourceType.getIsNextInSequence()!=null){
			webResource.setIsNextInSequence(wResourceType.getIsNextInSequence().getResource());
			ups.set("isNextInSequence", wResourceType.getIsNextInSequence().getResource());
			}
			WebResourceImpl retWebResource = mongoServer.searchByAbout(WebResourceImpl.class, webResource.getAbout());
			if(retWebResource!=null){
				mongoServer.getDatastore().update(updateQuery, ups);
			} else {
				mongoServer.getDatastore().save(webResource);
			}
			webResources.add(retWebResource!=null?retWebResource:webResource);
			
		}
		return webResources;
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
			List<WebResourceImpl> webResources = (List<WebResourceImpl>) (aggregation
					.getWebResources() != null ? aggregation.getWebResources()
					: new ArrayList<WebResourceImpl>());
			mongoServer.getDatastore().save(webResource);
			webResources.add(webResource);
			aggregation.setWebResources(webResources);

			MongoUtils.update(AggregationImpl.class, aggregation.getAbout(),
					mongoServer, "webResources", webResources);
		} 
		return aggregation;
	}

	/**
	 * Method that appends a web resource to an aggregation
	 * 
	 * @param aggregations
	 *            The list of aggregations
	 * @param webResource
	 *            The web resource to append
	 * @param mongoServer
	 *            The mongo server to use
	 * @return The aggregation that holds the web resource
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public AggregationImpl appendWebResource(
			List<AggregationImpl> aggregations, List<WebResource> webResource,
			EdmMongoServer mongoServer) throws InstantiationException,
			IllegalAccessException {
		AggregationImpl aggregation = findAggregation(aggregations,
				webResource.get(0));
		for (WebResource wr : webResource) {
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
	public AggregationImpl createAggregationMongoFields(
			eu.europeana.corelib.definitions.jibx.Aggregation aggregation,
			EdmMongoServer mongoServer, List<WebResourceImpl> webResources) throws InstantiationException,
			IllegalAccessException {
		AggregationImpl mongoAggregation = new AggregationImpl();
		// mongoAggregation.setId(new ObjectId());
		mongoAggregation.setAbout(aggregation.getAbout());
		Map<String, List<String>> dp = MongoUtils
				.createResourceOrLiteralMapFromString(aggregation
						.getDataProvider());
		mongoAggregation.setEdmDataProvider(dp);
		UpdateOperations<AggregationImpl> ups = mongoServer.getDatastore()
				.createUpdateOperations(AggregationImpl.class);
		ups.set("edmDataProvider", dp);

		String isShownAt = SolrUtils.exists(IsShownAt.class,
				(aggregation.getIsShownAt())).getResource();
		if (isShownAt != null) {
			mongoAggregation.setEdmIsShownAt(isShownAt);
			ups.set("edmIsShownAt", isShownAt);
		}

		String isShownBy = SolrUtils.exists(IsShownBy.class,
				(aggregation.getIsShownBy())).getResource();
		if (isShownBy != null) {
			mongoAggregation.setEdmIsShownBy(isShownBy);

			ups.set("edmIsShownBy", isShownBy);
		}

		String object = SolrUtils.exists(_Object.class,
				(aggregation.getObject())).getResource();
		if (object != null) {
			mongoAggregation.setEdmObject(object);

			ups.set("edmObject", object);
		}
		Map<String, List<String>> prov = MongoUtils
				.createResourceOrLiteralMapFromString(aggregation.getProvider());
		if (prov != null) {
			mongoAggregation.setEdmProvider(prov);
			ups.set("edmProvider", prov);
		}
		Map<String, List<String>> rights = MongoUtils
				.createResourceOrLiteralMapFromString(aggregation.getRights());
		if (rights != null) {
			mongoAggregation.setEdmRights(rights);
			ups.set("edmRights", rights);
		}
		if (aggregation.getUgc() != null) {
			mongoAggregation
					.setEdmUgc(aggregation.getUgc().getUgc().toString());
		}

		String agCHO = SolrUtils.exists(AggregatedCHO.class,
				(aggregation.getAggregatedCHO())).getResource();
		mongoAggregation.setAggregatedCHO(agCHO);
		ups.set("aggregatedCHO", agCHO);

		Map<String, List<String>> rights1 = MongoUtils
				.createResourceOrLiteralMapFromList(aggregation.getRightList());
		if (rights1 != null) {
			mongoAggregation.setDcRights(rights1);
			ups.set("dcRights", rights);
		}
		if (aggregation.getHasViewList() != null) {
			List<String> hasViewList = new ArrayList<String>();
			for (HasView hasView : aggregation.getHasViewList()) {
				hasViewList.add(hasView.getResource());
			}
			mongoAggregation.setHasView(hasViewList
					.toArray(new String[hasViewList.size()]));
			ups.set("hasView",
					hasViewList.toArray(new String[hasViewList.size()]));
		}
		
		AggregationImpl retAggr = mongoServer.getDatastore()
				.find(AggregationImpl.class)
				.filter("about", aggregation.getAbout()).get();
		
		if (retAggr != null) {
			if(webResources!=null){
				retAggr.setWebResources(webResources);
			}
			mongoServer.getDatastore().update(retAggr, ups);
			return retAggr;
		} else {
			if(webResources!=null){
				mongoAggregation.setWebResources(webResources);
			}
			mongoServer.getDatastore().save(mongoAggregation);
		}
		return mongoAggregation;
	}

	/**
	 * Deletes an aggregation from MongoDB storage based on the indexed field
	 * about
	 * 
	 * @param about
	 *            The aggregation to delete
	 * @param mongoServer
	 *            The server to use
	 */
	public void deleteAggregationFromMongo(String about,
			EdmMongoServer mongoServer) {
		MongoUtils.delete(Aggregation.class, about, mongoServer);
	}
}
