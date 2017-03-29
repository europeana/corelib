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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.common.SolrInputDocument;

import org.mongodb.morphia.query.UpdateOperations;

import eu.europeana.corelib.storage.MongoServer;
import eu.europeana.corelib.definitions.edm.entity.EuropeanaAggregation;
import eu.europeana.corelib.definitions.edm.entity.WebResource;
import eu.europeana.corelib.definitions.jibx.AggregatedCHO;
import eu.europeana.corelib.definitions.jibx.Aggregates;
import eu.europeana.corelib.definitions.jibx.EuropeanaAggregationType;
import eu.europeana.corelib.definitions.jibx.HasView;
import eu.europeana.corelib.definitions.jibx.IsShownBy;
import eu.europeana.corelib.definitions.jibx.LandingPage;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.edm.utils.MongoUtils;
import eu.europeana.corelib.edm.utils.SolrUtils;
import eu.europeana.corelib.solr.entity.EuropeanaAggregationImpl;
import eu.europeana.corelib.solr.entity.WebResourceImpl;

/**
 * Constructor of a Europeana Aggregation
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
public final class EuropeanaAggregationFieldInput {

	/**
	 * The prefix of a valid europeana record in the portal
	 */
	private final static String EUROPEANA_URI = "http://www.europeana.eu/portal/record";
	private final static String EDM_PREVIEW_PREFIX = "http://europeanastatic.eu/api/image?uri=";
	private final static String EDM_PREVIEW_SUFFIX = "&size=LARGE&type=";

	public EuropeanaAggregationFieldInput() {

	}

	/**
	 * Create the corresponding EuropeanaAggregation fields in a solr document
	 * 
	 * @param aggregation
	 *            The EuropeanaAggregation to store
	 * @param solrInputDocument
	 *            The solr doocument to save the EuropeanaAggregation in
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public SolrInputDocument createAggregationSolrFields(
			EuropeanaAggregationType aggregation,
			SolrInputDocument solrInputDocument, String previewUrl)
			throws InstantiationException, IllegalAccessException {

		solrInputDocument = SolrUtils.addFieldFromResourceOrLiteral(
				solrInputDocument, aggregation.getCreator(),
				EdmLabel.EUROPEANA_AGGREGATION_DC_CREATOR);
		solrInputDocument = SolrUtils.addFieldFromEnum(solrInputDocument,
				aggregation.getCountry().getCountry().xmlValue(),
				EdmLabel.EUROPEANA_AGGREGATION_EDM_COUNTRY);

		solrInputDocument.addField(
				EdmLabel.EDM_EUROPEANA_AGGREGATION.toString(),
				aggregation.getAbout());
		if (aggregation.getHasViewList() != null) {
			for (HasView hasView : aggregation.getHasViewList()) {
				solrInputDocument.addField(
						EdmLabel.EUROPEANA_AGGREGATION_EDM_HASVIEW.toString(),
						hasView.getResource());
			}
		}
		solrInputDocument.addField(EdmLabel.EUROPEANA_AGGREGATION_EDM_ISSHOWNBY
				.toString(), aggregation.getIsShownBy() != null ? aggregation
				.getIsShownBy().getResource() : null);
		solrInputDocument.addField(
				EdmLabel.EUROPEANA_AGGREGATION_EDM_LANDINGPAGE.toString(),
				 EUROPEANA_URI
						+ aggregation.getAggregatedCHO().getResource() +".html");

		solrInputDocument = SolrUtils.addFieldFromEnum(solrInputDocument,
				aggregation.getLanguage().getLanguage().xmlValue(),

				EdmLabel.EUROPEANA_AGGREGATION_EDM_LANGUAGE);
		solrInputDocument = SolrUtils.addFieldFromResource(
				solrInputDocument, aggregation.getRights(),
				EdmLabel.EUROPEANA_AGGREGATION_EDM_RIGHTS);
		solrInputDocument.addField(
				EdmLabel.EUROPEANA_AGGREGATION_ORE_AGGREGATEDCHO.toString(),
				aggregation.getAggregatedCHO() != null ? aggregation
						.getAggregatedCHO().getResource() : null);


		if (aggregation.getAggregateList() != null) {
			for (Aggregates aggregates : aggregation.getAggregateList()) {
				solrInputDocument.addField(
						EdmLabel.EUROPEANA_AGGREGATION_ORE_AGGREGATES
								.toString(), aggregates.getResource());
			}
		}
		return solrInputDocument;
	}

	/**
	 * Append a web resource to a Europeana Aggregation
	 * 
	 * @param aggregation
	 *            The EuropeanaAggregation
	 * @param webResource
	 *            The webResource to append
	 * @param mongoServer
	 *            The mongo Server to save both the Aggregation and the
	 *            WebResource
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	public EuropeanaAggregation appendWebResource(
			EuropeanaAggregation aggregation, WebResourceImpl webResource,
			MongoServer mongoServer) throws InstantiationException,
			IllegalAccessException {

		if (belongsTo(aggregation, webResource)) {
			List<WebResource> webResources = (List<WebResource>) (aggregation
					.getWebResources() != null ? aggregation.getWebResources()
					: new ArrayList<WebResource>());

			if (!webResourceExists(webResources, webResource)) {
				aggregation.setWebResources(webResources);
				if (aggregation.getAbout() != null) {
					MongoUtils.update(EuropeanaAggregationImpl.class,
							aggregation.getAbout(), mongoServer,
							"webResources", webResources);
				} else {

					mongoServer.getDatastore().save(aggregation);
				}
			}
		}
		return aggregation;
	}

	private boolean webResourceExists(List<WebResource> webResources,
			WebResourceImpl webResource) {
		for (WebResource wr : webResources) {
			if (StringUtils.equals(wr.getAbout(), webResource.getAbout())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Append a list of web resource to a Europeana Aggregation
	 * 
	 * @param aggregation
	 *            The EuropeanaAggregation
	 * @param webResources
	 *            The list of webResources to append
	 * @param mongoServer
	 *            The mongo Server to save both the Aggregation and the
	 *            WebResource
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public EuropeanaAggregation appendWebResource(
			EuropeanaAggregation aggregation, List<WebResource> webResources,
			MongoServer mongoServer) throws InstantiationException,
			IllegalAccessException {

		aggregation.setWebResources(webResources);
		if (aggregation.getAbout() != null) {
			MongoUtils.update(EuropeanaAggregationImpl.class,
					aggregation.getAbout(), mongoServer, "webResources",
					webResources);
		} else {
			mongoServer.getDatastore().save(aggregation);
		}

		return aggregation;
	}

	/**
	 * Create a EuropeanaAggregation to save in MongoDB storage
	 * 
	 * @param aggregation
	 *            The RDF EuropeanaAggregation representation
	 * @param mongoServer
	 *            The MongoServer to use to save the EuropeanaAggregation
	 * @return the EuropeanaAggregation created
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public EuropeanaAggregationImpl createAggregationMongoFields(
			eu.europeana.corelib.definitions.jibx.EuropeanaAggregationType aggregation,
			MongoServer mongoServer, String previewUrl)
			throws InstantiationException, IllegalAccessException {
		EuropeanaAggregationImpl mongoAggregation = new EuropeanaAggregationImpl();
		UpdateOperations<EuropeanaAggregationImpl> ops = mongoServer
				.getDatastore().createUpdateOperations(
						EuropeanaAggregationImpl.class);
		mongoAggregation.setAbout(aggregation.getAbout());

		Map<String, List<String>> creator = MongoUtils
				.createResourceOrLiteralMapFromString(aggregation.getCreator());
		if (creator != null) {
			ops.set("dcCreator", creator);
			mongoAggregation.setDcCreator(creator);
		} else {
			ops.unset("dcCreator");
		}

		Map<String, List<String>> country = MongoUtils
				.createLiteralMapFromString(aggregation.getCountry()
						.getCountry().xmlValue().toLowerCase());

		if (country != null) {
			mongoAggregation.setEdmCountry(country);
			ops.set("edmCountry", country);
		} else {
			ops.unset("edmCountry");
		}

		String isShownBy = SolrUtils.exists(IsShownBy.class,
				aggregation.getIsShownBy()).getResource();
		if (isShownBy != null) {
			mongoAggregation.setEdmIsShownBy(isShownBy);
			ops.set("edmIsShownBy", isShownBy);
		} else {
			ops.unset("edmIsShownBy");
		}
		String landingPage = SolrUtils.exists(LandingPage.class,
				aggregation.getLandingPage()).getResource();
	
			mongoAggregation.setEdmLandingPage(EUROPEANA_URI
					+ aggregation.getAggregatedCHO().getResource()+".html");
			ops.set("edmLandingPage", EUROPEANA_URI
					+ aggregation.getAggregatedCHO().getResource()+".html");
		

		Map<String, List<String>> language = MongoUtils
				.createLiteralMapFromString(aggregation.getLanguage()
						.getLanguage().xmlValue().toLowerCase());

		mongoAggregation.setEdmLanguage(language);
		ops.set("edmLanguage", language);

		String agCHO = SolrUtils.exists(AggregatedCHO.class,
				aggregation.getAggregatedCHO()).getResource();
		mongoAggregation.setAggregatedCHO(agCHO);
		ops.set("aggregatedCHO", agCHO);

		Map<String, List<String>> edmRights = MongoUtils
				.createResourceMapFromString(aggregation.getRights());
		if (edmRights != null) {
			mongoAggregation.setEdmRights(edmRights);
			ops.set("edmRights", edmRights);
		} else {
			ops.unset("edmRights");
		}
		String[] aggregates = SolrUtils.resourceListToArray(aggregation
				.getAggregateList());
		if (aggregates != null) {
			mongoAggregation.setAggregates(aggregates);
			ops.set("aggregates", aggregates);
		} else {
			ops.unset("edmHasView");
		}
		String[] hasViewList = SolrUtils.resourceListToArray(aggregation
				.getHasViewList());
		mongoAggregation.setEdmHasView(hasViewList);
		ops.set("edmHasView", hasViewList);
		// TODO: This is the future scenario
		// String preview = SolrUtils.exists(Preview.class,
		// aggregation.getPreview()).getResource();
		// if (preview != null) {
		// mongoAggregation.setEdmPreview(preview);
		// ops.set("edmPreview", preview);
		// } else {
		// mongoAggregation.setEdmPreview(EUROPEANA_URI + agCHO
		// + "&size=BRIEF_DOC");
		// ops.set("edmPreview", EUROPEANA_URI + agCHO + "&size=BRIEF_DOC");
		// }
//		if (previewUrl != null) {
//			String preview = EuropeanaUrlServiceImpl
//					.getBeanInstance().getThumbnailUrl(previewUrl, type);
//			mongoAggregation.setEdmPreview(preview);
//			ops.set("edmPreview", preview);
//		}
		EuropeanaAggregationImpl retrievedAggregation = mongoServer
				.getDatastore().find(EuropeanaAggregationImpl.class)
				.filter("about", aggregation.getAbout()).get();
		if (retrievedAggregation == null) {
			mongoServer.getDatastore().save(mongoAggregation);

		} else {
			mongoServer.getDatastore().update(retrievedAggregation, ops);

		}
		// TODO: Currently the europeana aggregation does not generate any
		// WebResource, do we want it?
		return retrievedAggregation != null ? retrievedAggregation
				: mongoAggregation;
	}

	/**
	 * Check if a webResource belongs to a EuropeanaAggregation
	 * 
	 * @param aggregation
	 *            The EuropeanaAggregation to check against
	 * @param webResource
	 *            The WebResource to check
	 * @return true if it belongs to the EuropeanaAggregation, false otherwise
	 */
	private boolean belongsTo(EuropeanaAggregation aggregation,
			WebResource webResource) {
		if (aggregation.getEdmHasView() != null) {
			for (String hasView : aggregation.getEdmHasView()) {
				if (StringUtils.equals(hasView, webResource.getAbout())) {
					return true;
				}
			}
		}

		if (aggregation.getEdmIsShownBy() != null) {
			if (StringUtils.equals(aggregation.getEdmIsShownBy(),
					webResource.getAbout())) {
				return true;
			}
		}
		return false;
	}

	private String generateEdmPreview(String url) {
		try {
			return EDM_PREVIEW_PREFIX
					+ URLEncoder.encode(url, "UTF-8").replaceAll("\\%28", "(")
							.replaceAll("\\%29", ")").replaceAll("\\+", "%20")
							.replaceAll("\\%27", "'").replaceAll("\\%21", "!")
							.replaceAll("\\%7E", "~") + EDM_PREVIEW_SUFFIX;
		} catch (UnsupportedEncodingException e) {

		}
		return null;
	}
        
        /**
	 * Create a EuropeanaAggregation to save in MongoDB storage
	 * 
	 * @param aggregation
	 *            The RDF EuropeanaAggregation representation
	 * @param mongoServer
	 *            The MongoServer to use to save the EuropeanaAggregation
	 * @return the EuropeanaAggregation created
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public EuropeanaAggregationImpl createAggregationMongoFields(
			eu.europeana.corelib.definitions.jibx.EuropeanaAggregationType aggregation,
			 String previewUrl)
			throws InstantiationException, IllegalAccessException {
		EuropeanaAggregationImpl mongoAggregation = new EuropeanaAggregationImpl();
		
		mongoAggregation.setAbout(aggregation.getAbout());

		Map<String, List<String>> creator = MongoUtils
				.createResourceOrLiteralMapFromString(aggregation.getCreator());
		
			mongoAggregation.setDcCreator(creator);
	

		Map<String, List<String>> country = MongoUtils
				.createLiteralMapFromString(aggregation.getCountry()
						.getCountry().xmlValue().toLowerCase());
			mongoAggregation.setEdmCountry(country);
		String isShownBy = SolrUtils.exists(IsShownBy.class,
				aggregation.getIsShownBy()).getResource();
			mongoAggregation.setEdmIsShownBy(isShownBy);
		
			mongoAggregation.setEdmLandingPage(EUROPEANA_URI
					+ aggregation.getAggregatedCHO().getResource()+".html");

		Map<String, List<String>> language = MongoUtils
				.createLiteralMapFromString(aggregation.getLanguage()
						.getLanguage().xmlValue().toLowerCase());

		mongoAggregation.setEdmLanguage(language);

		String agCHO = SolrUtils.exists(AggregatedCHO.class,
				aggregation.getAggregatedCHO()).getResource();
		mongoAggregation.setAggregatedCHO(agCHO);

		Map<String, List<String>> edmRights = MongoUtils
				.createResourceMapFromString(aggregation.getRights());
			mongoAggregation.setEdmRights(edmRights);
		String[] aggregates = SolrUtils.resourceListToArray(aggregation
				.getAggregateList());
			mongoAggregation.setAggregates(aggregates);
		String[] hasViewList = SolrUtils.resourceListToArray(aggregation
				.getHasViewList());
		mongoAggregation.setEdmHasView(hasViewList);
		// TODO: This is the future scenario
		// String preview = SolrUtils.exists(Preview.class,
		// aggregation.getPreview()).getResource();
		// if (preview != null) {
		// mongoAggregation.setEdmPreview(preview);
		// ops.set("edmPreview", preview);
		// } else {
		// mongoAggregation.setEdmPreview(EUROPEANA_URI + agCHO
		// + "&size=BRIEF_DOC");
		// ops.set("edmPreview", EUROPEANA_URI + agCHO + "&size=BRIEF_DOC");
		// }
//		if (previewUrl != null) {
//			String preview = EuropeanaUrlServiceImpl
//					.getBeanInstance().getThumbnailUrl(previewUrl, type);
//			mongoAggregation.setEdmPreview(preview);
//			ops.set("edmPreview", preview);
//		}
		// TODO: Currently the europeana aggregation does not generate any
		// WebResource, do we want it?
		return mongoAggregation;
	}
}
