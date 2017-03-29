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
package eu.europeana.corelib.edm.server.importer.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import eu.europeana.corelib.definitions.jibx.*;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.common.SolrInputDocument;

import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import eu.europeana.corelib.definitions.edm.entity.WebResource;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.edm.utils.MongoUtils;
import eu.europeana.corelib.edm.utils.SolrUtils;
import eu.europeana.corelib.mongo.server.EdmMongoServer;
import eu.europeana.corelib.solr.entity.AggregationImpl;
import eu.europeana.corelib.solr.entity.WebResourceImpl;

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
	 *
	 * @param wResources
	 * @param mongoServer
	 * @return
	 */
	public List<WebResourceImpl> createWebResources(
			List<WebResourceType> wResources, EdmMongoServer mongoServer) {
		List<WebResourceImpl> webResources = new ArrayList<WebResourceImpl>();

		for (WebResourceType wResourceType : wResources) {
			if (!containsWebResource(webResources, wResourceType.getAbout())) {
				boolean update = false;
				WebResourceImpl webResource = new WebResourceImpl();
				webResource.setAbout(wResourceType.getAbout());
				WebResourceImpl retWebResource = mongoServer.searchByAbout(
						WebResourceImpl.class, webResource.getAbout());
				Query<WebResourceImpl> updateQuery = mongoServer.getDatastore()
						.createQuery(WebResourceImpl.class).field("about")
						.equal(webResource.getAbout());
				UpdateOperations<WebResourceImpl> ups = mongoServer
						.getDatastore().createUpdateOperations(
								WebResourceImpl.class);
				Map<String, List<String>> desMap = MongoUtils
						.createResourceOrLiteralMapFromList(wResourceType
								.getDescriptionList());
				if (desMap != null) {
					if (retWebResource != null
							&& !MongoUtils.mapEquals(
									retWebResource.getDcDescription(), desMap)) {
						ups.set("dcDescription", desMap);
						retWebResource.setDcDescription(desMap);
						update = true;
					}
					webResource.setDcDescription(desMap);
				}

				Map<String, List<String>> forMap = MongoUtils
						.createResourceOrLiteralMapFromList(wResourceType
								.getFormatList());
				if (forMap != null) {
					if (retWebResource != null
							&& !MongoUtils.mapEquals(
									retWebResource.getDcFormat(), forMap)) {
						ups.set("dcFormat", forMap);
						retWebResource.setDcFormat(forMap);
						update = true;
					}
					webResource.setDcFormat(forMap);
				}
				Map<String, List<String>> sourceMap = MongoUtils
						.createResourceOrLiteralMapFromList(wResourceType
								.getSourceList());
				if (sourceMap != null) {
					if (retWebResource != null
							&& !MongoUtils.mapEquals(
									retWebResource.getDcSource(), sourceMap)) {
						ups.set("dcSource", sourceMap);
						retWebResource.setDcSource(sourceMap);
						update = true;
					}
					webResource.setDcSource(sourceMap);
				}
				Map<String, List<String>> conformsToMap = MongoUtils
						.createResourceOrLiteralMapFromList(wResourceType
								.getConformsToList());
				if (conformsToMap != null) {
					if (retWebResource != null
							&& !MongoUtils.mapEquals(
									retWebResource.getDctermsConformsTo(),
									webResource.getDctermsConformsTo())) {
						ups.set("dctermsConformsTo", conformsToMap);
						retWebResource.setDctermsConformsTo(conformsToMap);
						update = true;
					}
					webResource.setDctermsConformsTo(conformsToMap);
				}
				Map<String, List<String>> createdMap = MongoUtils
						.createResourceOrLiteralMapFromList(wResourceType
								.getCreatedList());
				if (createdMap != null) {
					if (retWebResource != null
							&& !MongoUtils.mapEquals(
									retWebResource.getDctermsCreated(),
									createdMap)) {
						ups.set("dctermsCreated", createdMap);
						retWebResource.setDctermsCreated(createdMap);
						update = true;
					}
					webResource.setDctermsCreated(createdMap);
				}
				Map<String, List<String>> extentMap = MongoUtils
						.createResourceOrLiteralMapFromList(wResourceType
								.getExtentList());
				if (extentMap != null) {
					if (retWebResource != null
							&& !MongoUtils.mapEquals(
									retWebResource.getDctermsExtent(),
									extentMap)) {
						ups.set("dctermsExtent", extentMap);
						retWebResource.setDctermsExtent(extentMap);
						update = true;
					}
					webResource.setDctermsExtent(extentMap);
				}

				Map<String, List<String>> hasPartMap = MongoUtils
						.createResourceOrLiteralMapFromList(wResourceType
								.getHasPartList());
				if (hasPartMap != null) {
					if (retWebResource != null
							&& !MongoUtils.mapEquals(
									retWebResource.getDctermsHasPart(),
									hasPartMap)) {
						ups.set("dctermsHasPart", hasPartMap);
						retWebResource.setDctermsHasPart(hasPartMap);
						update = true;
					}
					webResource.setDctermsHasPart(hasPartMap);
				}

				Map<String, List<String>> isFormatOfMap = MongoUtils
						.createResourceOrLiteralMapFromList(wResourceType
								.getIsFormatOfList());
				if (isFormatOfMap != null) {
					if (retWebResource != null
							&& !MongoUtils.mapEquals(
									retWebResource.getDctermsIsFormatOf(),
									hasPartMap)) {
						ups.set("isFormatOf", isFormatOfMap);
						retWebResource.setDctermsIsFormatOf(isFormatOfMap);
						update = true;
					}
					webResource.setDctermsIsFormatOf(isFormatOfMap);
				}

				Map<String, List<String>> issuedMap = MongoUtils
						.createResourceOrLiteralMapFromList(wResourceType
								.getIssuedList());
				if (issuedMap != null) {
					if (retWebResource != null
							&& !MongoUtils.mapEquals(
									retWebResource.getDctermsIssued(),
									issuedMap)) {
						ups.set("dctermsIssued", issuedMap);
						retWebResource.setDctermsIssued(issuedMap);
						update = true;
					}
					webResource.setDctermsIssued(issuedMap);
				}
				Map<String, List<String>> rightMap = MongoUtils
						.createResourceOrLiteralMapFromList(wResourceType
								.getRightList());
				if (rightMap != null) {
					if (retWebResource != null
							&& !MongoUtils.mapEquals(
									retWebResource.getWebResourceDcRights(),
									rightMap)) {
						ups.set("webResourceDcRights", rightMap);
						retWebResource.setWebResourceDcRights(rightMap);
						update = true;
					}
					webResource.setWebResourceDcRights(rightMap);
				}
				Map<String, List<String>> edmRightsMap = MongoUtils
						.createResourceMapFromString(wResourceType.getRights());
				if (edmRightsMap != null) {
					if (retWebResource != null
							&& !MongoUtils.mapEquals(
									retWebResource.getWebResourceEdmRights(),
									edmRightsMap)) {
						ups.set("webResourceEdmRights", edmRightsMap);
						retWebResource.setWebResourceEdmRights(edmRightsMap);
						update = true;
					}
					webResource.setWebResourceEdmRights(edmRightsMap);
				}



				if (wResourceType.getIsNextInSequence() != null) {
					webResource.setIsNextInSequence(wResourceType
							.getIsNextInSequence().getResource());
					if (retWebResource != null
							&& (retWebResource.getIsNextInSequence() == null || !retWebResource
									.getIsNextInSequence().equals(
											wResourceType.getIsNextInSequence()
													.getResource()))) {
						ups.set("isNextInSequence", wResourceType
								.getIsNextInSequence().getResource());
						retWebResource.setIsNextInSequence(wResourceType
								.getIsNextInSequence().getResource());
						update = true;
					}
				}

				if (retWebResource != null && update) {
					mongoServer.getDatastore().update(updateQuery, ups);
				} else {
					mongoServer.getDatastore().save(webResource);
				}
				webResources.add(retWebResource != null ? retWebResource
						: webResource);
			}
		}
		return webResources;
	}

	/**
	 * Create a web resource
	 *
	 * @param wResources
	 * @return
	 */
	public List<WebResourceImpl> createWebResources(
			List<WebResourceType> wResources) {
		List<WebResourceImpl> webResources = new ArrayList<WebResourceImpl>();

		for (WebResourceType wResourceType : wResources) {
			if (!containsWebResource(webResources, wResourceType.getAbout())) {
				WebResourceImpl webResource = new WebResourceImpl();
				webResource.setAbout(wResourceType.getAbout());

				Map<String, List<String>> desMap = MongoUtils
						.createResourceOrLiteralMapFromList(wResourceType
								.getDescriptionList());

				webResource.setDcDescription(desMap);

				Map<String, List<String>> forMap = MongoUtils
						.createResourceOrLiteralMapFromList(wResourceType
								.getFormatList());

				webResource.setDcFormat(forMap);

				Map<String, List<String>> sourceMap = MongoUtils
						.createResourceOrLiteralMapFromList(wResourceType
								.getSourceList());

				webResource.setDcSource(sourceMap);

				Map<String, List<String>> conformsToMap = MongoUtils
						.createResourceOrLiteralMapFromList(wResourceType
								.getConformsToList());

				webResource.setDctermsConformsTo(conformsToMap);

				Map<String, List<String>> createdMap = MongoUtils
						.createResourceOrLiteralMapFromList(wResourceType
								.getCreatedList());

				webResource.setDctermsCreated(createdMap);

				Map<String, List<String>> extentMap = MongoUtils
						.createResourceOrLiteralMapFromList(wResourceType
								.getExtentList());

				webResource.setDctermsExtent(extentMap);

				Map<String, List<String>> hasPartMap = MongoUtils
						.createResourceOrLiteralMapFromList(wResourceType
								.getHasPartList());

				webResource.setDctermsHasPart(hasPartMap);

				Map<String, List<String>> isFormatOfMap = MongoUtils
						.createResourceOrLiteralMapFromList(wResourceType
								.getIsFormatOfList());

				webResource.setDctermsIsFormatOf(isFormatOfMap);

				Map<String, List<String>> issuedMap = MongoUtils
						.createResourceOrLiteralMapFromList(wResourceType
								.getIssuedList());

				webResource.setDctermsIssued(issuedMap);
				Map<String, List<String>> rightMap = MongoUtils
						.createResourceOrLiteralMapFromList(wResourceType
								.getRightList());

				webResource.setWebResourceDcRights(rightMap);

				Map<String, List<String>> typeMap = MongoUtils
						.createResourceOrLiteralMapFromList(wResourceType.getTypeList());
				webResource.setDcType(typeMap);

				Map<String, List<String>> edmRightsMap = MongoUtils
						.createResourceMapFromString(wResourceType.getRights());

				webResource.setWebResourceEdmRights(edmRightsMap);

				if (wResourceType.getIsNextInSequence() != null) {
					webResource.setIsNextInSequence(wResourceType
							.getIsNextInSequence().getResource());
				}
				webResource.setOwlSameAs(SolrUtils
						.resourceListToArray(wResourceType.getSameAList()));

				webResource.setDcCreator(MongoUtils
						.createResourceOrLiteralMapFromList(wResourceType
								.getCreatorList()));

                if(wResourceType.getHasServiceList()!=null){
                    webResource.setSvcsHasService(SolrUtils.resourceListToArray(wResourceType.getHasServiceList()));
                }
				if(wResourceType.getIsReferencedByList()!=null){
					webResource.setDctermsIsReferencedBy(SolrUtils.resourceOrLiteralListToArray(wResourceType.getIsReferencedByList()));
				}
                if(wResourceType.getPreview()!=null){
                    webResource.setEdmPreview(wResourceType.getPreview().getResource());
                }
				webResources.add(webResource);
			}
		}
		return webResources;
	}

	private boolean containsWebResource(List<WebResourceImpl> webResources,
			String about) {
		for (WebResourceImpl wr : webResources) {
			if (StringUtils.equals(wr.getAbout(), about)) {
				return true;
			}
		}
		return false;
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
			Aggregation aggregation, SolrInputDocument solrInputDocument,
			List<License> licenses) throws InstantiationException,
			IllegalAccessException {

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
        if(aggregation.getIntermediateProviderList()!=null){
            for(IntermediateProvider prov:aggregation.getIntermediateProviderList()){
                solrInputDocument = SolrUtils.addFieldFromResourceOrLiteral(solrInputDocument,prov,
                        EdmLabel.PROVIDER_AGGREGATION_EDM_INTERMEDIATE_PROVIDER);
            }
        }
		boolean saveRights = true;
		if(licenses!=null){
			String rights = aggregation.getRights().getResource();
			for(License lic:licenses){
				if(StringUtils.equals(rights, lic.getAbout())){
					saveRights=false;
					break;
				}
			}
		}
		if(saveRights){
			solrInputDocument = SolrUtils.addFieldFromResource(
					solrInputDocument, aggregation.getRights(),
					EdmLabel.PROVIDER_AGGREGATION_EDM_RIGHTS);
		}
		
		if (aggregation.getUgc() != null) {
			solrInputDocument.addField(EdmLabel.EDM_UGC.toString(), aggregation
					.getUgc().getUgc().toString().toLowerCase());
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
			List<WebResource> webResources = (List<WebResource>) (aggregation
					.getWebResources() != null ? aggregation.getWebResources()
					: new ArrayList<WebResource>());
			if (!webResourceExists(webResources, webResource)) {
				mongoServer.getDatastore().save(webResource);
				webResources.add(webResource);
				aggregation.setWebResources(webResources);

				MongoUtils.update(AggregationImpl.class,
						aggregation.getAbout(), mongoServer, "webResources",
						webResources);
			}
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
		for (WebResource wr : cleanWebResources(webResource)) {
			mongoServer.getDatastore().save(wr);
		}
		aggregation.setWebResources(webResource);
		MongoUtils.update(AggregationImpl.class, aggregation.getAbout(),
				mongoServer, "webResources", webResource);

		return aggregation;
	}

	private List<WebResource> cleanWebResources(List<WebResource> webResource) {
		List<WebResource> webResources = new ArrayList<WebResource>();
		for (WebResource wr : webResources) {
			if (!webResourceExists(webResources, wr)) {
				webResources.add(wr);
			}
		}
		return webResources;
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
			EdmMongoServer mongoServer, List<WebResourceImpl> webResources)
			throws InstantiationException, IllegalAccessException {
		AggregationImpl mongoAggregation = new AggregationImpl();
		AggregationImpl retAggr = mongoServer.getDatastore()
				.find(AggregationImpl.class)
				.filter("about", aggregation.getAbout()).get();
		boolean update = false;
		if (retAggr != null) {
			update = true;
			mongoAggregation = retAggr;
		}

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
		mongoAggregation.setEdmIsShownAt(isShownAt);
		if (isShownAt != null) {
			mongoAggregation.setEdmIsShownAt(isShownAt.trim());
			ups.set("edmIsShownAt", isShownAt.trim());
		} else {
			ups.unset("edmIsShownAt");
		}

		String isShownBy = SolrUtils.exists(IsShownBy.class,
				(aggregation.getIsShownBy())).getResource();
		mongoAggregation.setEdmIsShownBy(isShownBy);
		if (isShownBy != null) {
			mongoAggregation.setEdmIsShownBy(isShownBy.trim());
			ups.set("edmIsShownBy", isShownBy.trim());
		} else {
			ups.unset("edmIsShownBy");
		}

		String object = SolrUtils.exists(_Object.class,
				(aggregation.getObject())).getResource();
		mongoAggregation.setEdmObject(object);
		if (object != null) {
			mongoAggregation.setEdmObject(object.trim());
			ups.set("edmObject", object.trim());
		} else {
			ups.unset("edmObject");
		}
		Map<String, List<String>> prov = MongoUtils
				.createResourceOrLiteralMapFromString(aggregation.getProvider());
		if (prov != null) {
			mongoAggregation.setEdmProvider(prov);
			ups.set("edmProvider", prov);
		} else {
			ups.unset("edmProvider");
		}
		Map<String, List<String>> rights = MongoUtils
				.createResourceMapFromString(aggregation.getRights());
		mongoAggregation.setEdmRights(rights);
		if (rights != null) {

			ups.set("edmRights", rights);
		} else {
			ups.unset("edmRights");
		}
		if (aggregation.getUgc() != null) {
			mongoAggregation.setEdmUgc(aggregation.getUgc().getUgc().toString()
					.toLowerCase());
		} else {
			mongoAggregation.setEdmUgc(null);
		}

		String agCHO = SolrUtils.exists(AggregatedCHO.class,
				(aggregation.getAggregatedCHO())).getResource();
		mongoAggregation.setAggregatedCHO(agCHO);
		ups.set("aggregatedCHO", agCHO);

		Map<String, List<String>> rights1 = MongoUtils
				.createResourceOrLiteralMapFromList(aggregation.getRightList());
		mongoAggregation.setDcRights(rights1);
		if (rights1 != null) {

			ups.set("dcRights", rights1);
		} else {
			ups.unset("dcRights");
		}

        Map<String,List<String>> providers = MongoUtils.
                createResourceOrLiteralMapFromList(aggregation.getIntermediateProviderList());
        if(providers!=null){
            ups.set("edmIntermediateProvider", providers);
        } else {
            ups.unset("edmIntermediateProvider");
        }
        mongoAggregation.setEdmIntermediateProvider(providers);
		if (aggregation.getHasViewList() != null) {
			List<String> hasViewList = new ArrayList<String>();
			for (HasView hasView : aggregation.getHasViewList()) {
				hasViewList.add(hasView.getResource().trim());
			}
			mongoAggregation.setHasView(hasViewList
					.toArray(new String[hasViewList.size()]));
			ups.set("hasView",
					hasViewList.toArray(new String[hasViewList.size()]));
		} else {
			mongoAggregation.setHasView(null);
			ups.unset("hasView");
		}
		if (webResources != null) {
			mongoAggregation.setWebResources(webResources);
		}
		if (update == true) {
			mongoServer.getDatastore().update(mongoAggregation, ups);
		} else {
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

	private boolean webResourceExists(List<WebResource> webResources,
			WebResource webResource) {
		for (WebResource wr : webResources) {
			if (StringUtils.equals(wr.getAbout(), webResource.getAbout())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Create an Aggregation MongoDBEntity. The webresources are not inserted at
	 * this method. as the aggregation may be created before the web resources
	 * are encountered in the EDM parsing or after.
	 *
	 * @param aggregation
	 *            The JiBX Aggregation entity

	 * @return The MongoDB Aggregation entity
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public AggregationImpl createAggregationMongoFields(
			eu.europeana.corelib.definitions.jibx.Aggregation aggregation,
			List<WebResourceImpl> webResources) throws InstantiationException,
			IllegalAccessException {
		AggregationImpl mongoAggregation = new AggregationImpl();

		mongoAggregation.setAbout(aggregation.getAbout());
		Map<String, List<String>> dp = MongoUtils
				.createResourceOrLiteralMapFromString(aggregation
						.getDataProvider());
		mongoAggregation.setEdmDataProvider(dp);
        if(aggregation.getIntermediateProviderList()!=null) {
            Map<String, List<String>> providers = MongoUtils.
                    createResourceOrLiteralMapFromList(aggregation.getIntermediateProviderList());
            mongoAggregation.setEdmIntermediateProvider(providers);
        }
		String isShownAt = SolrUtils.exists(IsShownAt.class,
				(aggregation.getIsShownAt())).getResource();
		mongoAggregation.setEdmIsShownAt(isShownAt != null ? isShownAt.trim()
				: null);
		boolean containsIsShownAt = false;
		for(WebResourceImpl wr:webResources){
			if(StringUtils.equals(wr.getAbout(),isShownAt)){
				containsIsShownAt = true;
			}
		}
		if(!containsIsShownAt && isShownAt!=null){
			WebResourceImpl wr = new WebResourceImpl();
			wr.setAbout(isShownAt);
			webResources.add(wr);
		}
		String isShownBy = SolrUtils.exists(IsShownBy.class,
				(aggregation.getIsShownBy())).getResource();
		mongoAggregation.setEdmIsShownBy(isShownBy != null ? isShownBy.trim()
				: null);
		boolean containsIsShownBy = false;
		for(WebResourceImpl wr:webResources){
			if(StringUtils.equals(wr.getAbout(),isShownBy)){
				containsIsShownBy = true;
			}
		}
		if(!containsIsShownBy && isShownBy!=null){
			WebResourceImpl wr = new WebResourceImpl();
			wr.setAbout(isShownBy);
			webResources.add(wr);
		}
		String object = SolrUtils.exists(_Object.class,
				(aggregation.getObject())).getResource();
		mongoAggregation.setEdmObject(object != null ? object.trim() : null);
		boolean containsObject = false;
		for(WebResourceImpl wr:webResources){
			if(StringUtils.equals(wr.getAbout(),object)){
				containsObject = true;
			}
		}
		if(!containsObject&&object!=null){
			WebResourceImpl wr = new WebResourceImpl();
			wr.setAbout(object);
			webResources.add(wr);
		}
		Map<String, List<String>> prov = MongoUtils
				.createResourceOrLiteralMapFromString(aggregation.getProvider());
		mongoAggregation.setEdmProvider(prov);
		Map<String, List<String>> rights = MongoUtils
				.createResourceMapFromString(aggregation.getRights());
		mongoAggregation.setEdmRights(rights);

		if (aggregation.getUgc() != null) {
			mongoAggregation.setEdmUgc(aggregation.getUgc().getUgc().toString()
					.toLowerCase());
		} else {
			mongoAggregation.setEdmUgc(null);
		}

		String agCHO = SolrUtils.exists(AggregatedCHO.class,
				(aggregation.getAggregatedCHO())).getResource();
		mongoAggregation.setAggregatedCHO(agCHO);

		Map<String, List<String>> rights1 = MongoUtils
				.createResourceOrLiteralMapFromList(aggregation.getRightList());
		mongoAggregation.setDcRights(rights1);

		if (aggregation.getHasViewList() != null) {
			List<String> hasViewList = new ArrayList<String>();
			for (HasView hasView : aggregation.getHasViewList()) {
				hasViewList.add(hasView.getResource().trim());
				boolean containsHasView = false;
				for(WebResourceImpl wr:webResources){
					if(StringUtils.equals(wr.getAbout(),hasView.getResource().trim())){
						containsHasView = true;
					}
				}
				if(!containsHasView&&hasView.getResource().trim()!=null){
					WebResourceImpl wr = new WebResourceImpl();
					wr.setAbout(hasView.getResource().trim());
					webResources.add(wr);
				}
			}
			mongoAggregation.setHasView(hasViewList
					.toArray(new String[hasViewList.size()]));

		} else {
			mongoAggregation.setHasView(null);

		}
		if (webResources != null) {
			mongoAggregation.setWebResources(webResources);
		}

		return mongoAggregation;
	}
}
