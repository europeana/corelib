package eu.europeana.corelib.solr.server.importer.util;

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
import eu.europeana.corelib.solr.server.EdmMongoServer;
import eu.europeana.corelib.solr.utils.MongoUtils;
import eu.europeana.corelib.solr.utils.SolrUtils;

public final class EuropeanaAggregationFieldInput {

	private final static String EUROPEANA_URI = "http://www.europeana.eu/portal/record";

	public EuropeanaAggregationFieldInput() {

	}

	public SolrInputDocument createAggregationSolrFields(
			EuropeanaAggregationType aggregation,
			SolrInputDocument solrInputDocument) throws InstantiationException,
			IllegalAccessException {

		solrInputDocument = SolrUtils.addFieldFromResourceOrLiteral(
				solrInputDocument, aggregation.getCreator(),
				EdmLabel.EUROPEANA_AGGREGATION_DC_CREATOR);
		solrInputDocument = SolrUtils.addFieldFromLiteral(solrInputDocument,
				aggregation.getCountry(),
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
		solrInputDocument
				.addField(
						EdmLabel.EUROPEANA_AGGREGATION_EDM_LANDINGPAGE
								.toString(),
						aggregation.getLandingPage().getResource() != null ? aggregation
								.getLandingPage().getResource() : EUROPEANA_URI
								+ aggregation.getAggregatedCHO().getResource());
		solrInputDocument = SolrUtils.addFieldFromLiteral(solrInputDocument,
				aggregation.getLanguage(),
				EdmLabel.EUROPEANA_AGGREGATION_EDM_LANGUAGE);
		solrInputDocument = SolrUtils.addFieldFromResourceOrLiteral(
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

	@SuppressWarnings("unchecked")
	public EuropeanaAggregation appendWebResource(
			EuropeanaAggregation aggregation, WebResourceImpl webResource,
			MongoServer mongoServer) throws InstantiationException,
			IllegalAccessException {

		if (belongsTo(aggregation, webResource)) {
			List<WebResource> webResources = (List<WebResource>) (aggregation
					.getWebResources() != null ? aggregation.getWebResources()
					: new ArrayList<WebResource>());

			aggregation.setWebResources(webResources);
			if (aggregation.getAbout() != null) {
				MongoUtils.update(EuropeanaAggregationImpl.class,
						aggregation.getAbout(), mongoServer, "webResources",
						webResources);
			} else {
				mongoServer.getDatastore().save(aggregation);
			}

		}
		return aggregation;
	}

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

	public EuropeanaAggregationImpl createAggregationMongoFields(
			eu.europeana.corelib.definitions.jibx.EuropeanaAggregationType aggregation,
			MongoServer mongoServer) throws InstantiationException,
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
				.createLiteralMapFromString(aggregation.getCountry());
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
		mongoAggregation.setEdmLandingPage(landingPage);
		ops.set("edmLandingPage", landingPage);

		Map<String, List<String>> language = MongoUtils
				.createLiteralMapFromString(aggregation.getLanguage());
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
		EuropeanaAggregationImpl retrievedAggregation = ((EdmMongoServer) mongoServer)
				.getDatastore().find(EuropeanaAggregationImpl.class)
				.filter("about", mongoAggregation.getAbout()).get();
		if (retrievedAggregation != null) {
			mongoServer.getDatastore().update(retrievedAggregation, ops);
		} else {
			mongoServer.getDatastore().save(mongoAggregation);
		}
		return mongoAggregation;
	}

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

}
