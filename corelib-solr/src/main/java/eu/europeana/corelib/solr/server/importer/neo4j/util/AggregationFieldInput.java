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

package eu.europeana.corelib.solr.server.importer.neo4j.util;

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
import eu.europeana.corelib.solr.server.Neo4jServer;
import eu.europeana.corelib.solr.entity.AggregationImpl;
import eu.europeana.corelib.solr.entity.WebResourceImpl;
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
	 * 
	 * @param wResources
	 * @param neo4jServer
	 * @return
	 */
	public List<WebResourceImpl> createWebResources(
			List<WebResourceType> wResources, Neo4jServer neo4jServer) {
		List<WebResourceImpl> webResources = new ArrayList<WebResourceImpl>();

		for (WebResourceType wResourceType : wResources) {
			if (!containsWebResource(webResources, wResourceType.getAbout())) {
				boolean update = false;
				WebResourceImpl webResource = new WebResourceImpl();
				webResource.setAbout(wResourceType.getAbout());
				WebResourceImpl retWebResource = neo4jServer.find(webResource.getAbout(),webResource);


				Map<String, List<String>> desMap = MongoUtils
						.createResourceOrLiteralMapFromList(wResourceType
								.getDescriptionList());
				if (desMap != null) {
					if (retWebResource != null
							&& !MongoUtils.mapEquals(
									retWebResource.getDcDescription(),
									desMap)) {
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
									retWebResource.getDcFormat(),
									forMap)) {
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
									retWebResource.getDcSource(),
									sourceMap)) {
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
						retWebResource.setDctermsIsFormatOf(isFormatOfMap);;
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
						retWebResource.setWebResourceDcRights(rightMap);
						update = true;
					}
					webResource.setWebResourceDcRights(rightMap);
				}
				Map<String, List<String>> edmRightsMap = MongoUtils
						.createResourceOrLiteralMapFromString(wResourceType
								.getRights());
				if (edmRightsMap != null) {
					if (retWebResource != null
							&& !MongoUtils.mapEquals(
									retWebResource.getWebResourceEdmRights(),
									edmRightsMap)) {
						retWebResource.setWebResourceEdmRights(edmRightsMap);
						update = true;
					}
					webResource.setWebResourceEdmRights(edmRightsMap);
				}
				if (wResourceType.getIsNextInSequence() != null) {
					webResource.setIsNextInSequence(wResourceType
							.getIsNextInSequence().getResource());
					if (retWebResource != null && (retWebResource.getIsNextInSequence()==null
							|| !retWebResource.getIsNextInSequence().equals(
									wResourceType
									.getIsNextInSequence().getResource()))) {
						retWebResource.setIsNextInSequence(wResourceType
								.getIsNextInSequence().getResource());
						update = true;
					}
				}


					neo4jServer.save(webResource);

				webResources.add(retWebResource != null ? retWebResource
						: webResource);
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
	 * @param neo4jServer
	 *            The neo4jServer instance that Aggregation is going to be saved
	 *            in
	 * @return The MongoDB Aggregation entity
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public AggregationImpl createAggregationMongoFields(
			eu.europeana.corelib.definitions.jibx.Aggregation aggregation,
			Neo4jServer neo4jServer, List<WebResourceImpl> webResources)
			throws InstantiationException, IllegalAccessException {
		AggregationImpl mongoAggregation = new AggregationImpl();
		AggregationImpl retAggr = (AggregationImpl) neo4jServer.find(aggregation.getAbout(),mongoAggregation);

		boolean update = false;
		if(retAggr!=null){
			update =true;
			mongoAggregation = retAggr;
		}
		
		mongoAggregation.setAbout(aggregation.getAbout());
		Map<String, List<String>> dp = MongoUtils
				.createResourceOrLiteralMapFromString(aggregation
						.getDataProvider());
		mongoAggregation.setEdmDataProvider(dp);

		
		String isShownAt = SolrUtils.exists(IsShownAt.class,
				(aggregation.getIsShownAt())).getResource();
		mongoAggregation.setEdmIsShownAt(isShownAt);

		
		String isShownBy = SolrUtils.exists(IsShownBy.class,
				(aggregation.getIsShownBy())).getResource();
		mongoAggregation.setEdmIsShownBy(isShownBy);


		String object = SolrUtils.exists(_Object.class,
				(aggregation.getObject())).getResource();
		mongoAggregation.setEdmObject(object);

		Map<String, List<String>> prov = MongoUtils
				.createResourceOrLiteralMapFromString(aggregation.getProvider());

		Map<String, List<String>> rights = MongoUtils
				.createResourceOrLiteralMapFromString(aggregation.getRights());
		mongoAggregation.setEdmRights(rights);

		if (aggregation.getUgc() != null) {
			mongoAggregation
					.setEdmUgc(aggregation.getUgc().getUgc().toString().toLowerCase());
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
				hasViewList.add(hasView.getResource());
			}
			mongoAggregation.setHasView(hasViewList
					.toArray(new String[hasViewList.size()]));

		} else {
			mongoAggregation.setHasView(null);
		}
		if (webResources != null) {
			mongoAggregation.setWebResources(webResources);
		}
		neo4jServer.save(mongoAggregation);
		return mongoAggregation;
	}

	/**
	 * Deletes an aggregation from MongoDB storage based on the indexed field
	 * about
	 * 
	 * @param about
	 *            The aggregation to delete
	 * @param neo4jServer
	 *            The server to use
	 */
	public void deleteAggregationFromMongo(String about,
			Neo4jServer neo4jServer) {
		neo4jServer.delete(about);
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
}
