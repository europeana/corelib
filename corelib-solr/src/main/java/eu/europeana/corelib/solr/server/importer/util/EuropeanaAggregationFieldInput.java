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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.common.SolrInputDocument;

import com.google.code.morphia.query.UpdateOperations;

import eu.europeana.corelib.definitions.jibx.AggregatedCHO;
import eu.europeana.corelib.definitions.jibx.Aggregates;
import eu.europeana.corelib.definitions.jibx.EuropeanaAggregationType;
import eu.europeana.corelib.definitions.jibx.HasView;
import eu.europeana.corelib.definitions.jibx.IsShownBy;
import eu.europeana.corelib.definitions.jibx.LandingPage;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.definitions.solr.entity.EuropeanaAggregation;
import eu.europeana.corelib.definitions.solr.entity.WebResource;
import eu.europeana.corelib.solr.MongoServer;
import eu.europeana.corelib.solr.entity.EuropeanaAggregationImpl;
import eu.europeana.corelib.solr.entity.WebResourceImpl;
import eu.europeana.corelib.solr.utils.MongoUtils;
import eu.europeana.corelib.solr.utils.SolrUtils;

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
	private final static String EDM_PREVIEW_SUFFIX = "&size=LARGE&type=TEXT";

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
			SolrInputDocument solrInputDocument, String previewUrl) throws InstantiationException,
			IllegalAccessException {

		solrInputDocument = SolrUtils.addFieldFromResourceOrLiteral(
				solrInputDocument, aggregation.getCreator(),
				EdmLabel.EUROPEANA_AGGREGATION_DC_CREATOR);
		solrInputDocument = SolrUtils.addFieldFromEnum(solrInputDocument,
				aggregation.getCountry().getCountry(),
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
				aggregation.getLandingPage() != null ? aggregation
						.getLandingPage().getResource() : EUROPEANA_URI
						+ aggregation.getAggregatedCHO().getResource());
		
		solrInputDocument = SolrUtils.addFieldFromEnum(solrInputDocument,
				aggregation.getLanguage().getLanguage(),
				
				
				
				EdmLabel.EUROPEANA_AGGREGATION_EDM_LANGUAGE);
		solrInputDocument = SolrUtils.addFieldFromResourceOrLiteral(
				solrInputDocument, aggregation.getRights(),
				EdmLabel.EUROPEANA_AGGREGATION_EDM_RIGHTS);
		solrInputDocument.addField(
				EdmLabel.EUROPEANA_AGGREGATION_ORE_AGGREGATEDCHO.toString(),
				aggregation.getAggregatedCHO() != null ? aggregation
						.getAggregatedCHO().getResource() : null);
		
		solrInputDocument.addField(EdmLabel.EUROPEANA_AGGREGATION_EDM_PREVIEW
				.toString(), previewUrl != null ? generateEdmPreview(previewUrl) : null);
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
			MongoServer mongoServer, String previewUrl) throws InstantiationException,
			IllegalAccessException {
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
		}
		
		Map<String, List<String>> country = MongoUtils
				.createLiteralMapFromString(aggregation.getCountry().getCountry());
		
		if (country != null) {
			mongoAggregation.setEdmCountry(country);
			ops.set("edmCountry", country);
		}

		String isShownBy = SolrUtils.exists(IsShownBy.class,
				aggregation.getIsShownBy()).getResource();
		if (isShownBy != null) {
			mongoAggregation.setEdmIsShownBy(isShownBy);
			ops.set("edmIsShownBy", isShownBy);
		}
		String landingPage = SolrUtils.exists(LandingPage.class,
				aggregation.getLandingPage()).getResource();
		if (landingPage != null) {
			mongoAggregation.setEdmLandingPage(landingPage);
			ops.set("edmLandingPage", landingPage);
		} else {
			mongoAggregation.setEdmLandingPage(EUROPEANA_URI
					+ aggregation.getAggregatedCHO().getResource());
			ops.set("edmLandingPage", EUROPEANA_URI
					+ aggregation.getAggregatedCHO().getResource());
		}

		Map<String, List<String>> language = MongoUtils
				.createLiteralMapFromString(aggregation.getLanguage().getLanguage());
		
		mongoAggregation.setEdmLanguage(language);
		ops.set("edmLanguage", language);

		String agCHO = SolrUtils.exists(AggregatedCHO.class,
				aggregation.getAggregatedCHO()).getResource();
		mongoAggregation.setAggregatedCHO(agCHO);
		ops.set("aggregatedCHO", agCHO);

		Map<String, List<String>> edmRights = MongoUtils
				.createResourceOrLiteralMapFromString(aggregation.getRights());
		if (edmRights != null) {
			mongoAggregation.setEdmRights(edmRights);
			ops.set("edmRights", edmRights);
		}
		String[] aggregates = SolrUtils.resourceListToArray(aggregation
				.getAggregateList());
		if (aggregates != null) {
			mongoAggregation.setAggregates(aggregates);
			ops.set("aggregates", aggregates);
		}
		String[] hasViewList = SolrUtils.resourceListToArray(aggregation
				.getHasViewList());
		mongoAggregation.setEdmHasView(hasViewList);
		ops.set("edmHasView", hasViewList);
//TODO: This is the future scenario
//		String preview = SolrUtils.exists(Preview.class,
//				aggregation.getPreview()).getResource();
//		if (preview != null) {
//			mongoAggregation.setEdmPreview(preview);
//			ops.set("edmPreview", preview);
//		} else {
//			mongoAggregation.setEdmPreview(EUROPEANA_URI + agCHO
//					+ "&size=BRIEF_DOC");
//			ops.set("edmPreview", EUROPEANA_URI + agCHO + "&size=BRIEF_DOC");
//		}
		if (previewUrl!=null){
			String preview = generateEdmPreview(previewUrl);
			mongoAggregation.setEdmPreview(preview);
			ops.set("edmPreview",preview);
		}
		EuropeanaAggregationImpl retrievedAggregation = mongoServer
				.getDatastore().find(EuropeanaAggregationImpl.class)
				.filter("about", mongoAggregation.getAbout()).get();
		if (retrievedAggregation != null) {
			mongoServer.getDatastore().update(retrievedAggregation, ops);

		} else {
			mongoServer.getDatastore().save(mongoAggregation);
		}
		//TODO: Currently the europeana aggregation does not generate any WebResource, do we want it?
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
			return EDM_PREVIEW_PREFIX+URLEncoder.encode(url, "UTF-8").replaceAll("\\%28", "(")
					.replaceAll("\\%29", ")").replaceAll("\\+", "%20")
					.replaceAll("\\%27", "'").replaceAll("\\%21", "!")
					.replaceAll("\\%7E", "~")+EDM_PREVIEW_SUFFIX;
		} catch (UnsupportedEncodingException e) {
			

		}
		return null;
	}
}
