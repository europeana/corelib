package eu.europeana.corelib.solr.server.importer.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.common.SolrInputDocument;

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

	private final static String EUROPEANA_URI="http://www.europeana.eu/portal/record";
	public EuropeanaAggregationFieldInput() {

	}

	public SolrInputDocument createAggregationSolrFields(
			EuropeanaAggregationType aggregation,
			SolrInputDocument solrInputDocument) throws InstantiationException,
			IllegalAccessException {

		solrInputDocument = SolrUtils
				.addFieldFromResourceOrLiteral(solrInputDocument, aggregation.getCreator(),
						EdmLabel.EUROPEANA_AGGREGATION_DC_CREATOR);
		solrInputDocument = SolrUtils
				.addFieldFromLiteral(solrInputDocument, aggregation.getCountry(),
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
		solrInputDocument.addField(
				EdmLabel.EUROPEANA_AGGREGATION_EDM_ISSHOWNBY.toString(),
				aggregation.getIsShownBy().getResource());
		solrInputDocument.addField(
				EdmLabel.EUROPEANA_AGGREGATION_EDM_LANDINGPAGE.toString(),
				aggregation.getLandingPage().getResource()!=null?aggregation.getLandingPage().getResource():EUROPEANA_URI+aggregation.getAggregatedCHO().getResource());
		solrInputDocument = SolrUtils
				.addFieldFromLiteral(solrInputDocument, aggregation.getLanguage(),
						EdmLabel.EUROPEANA_AGGREGATION_EDM_LANGUAGE);
		solrInputDocument = SolrUtils
				.addFieldFromResourceOrLiteral(solrInputDocument, aggregation.getRights(),
						EdmLabel.EUROPEANA_AGGREGATION_EDM_RIGHTS);
		solrInputDocument.addField(
				EdmLabel.EUROPEANA_AGGREGATION_ORE_AGGREGATEDCHO.toString(),
				aggregation.getAggregatedCHO().getResource());
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
			EuropeanaAggregation aggregation,
			WebResourceImpl webResource, MongoServer mongoServer)
			throws InstantiationException, IllegalAccessException {
		
		if(belongsTo(aggregation,webResource)){
			List<WebResource> webResources = (List<WebResource>) (aggregation.getWebResources() != null ? aggregation
					.getWebResources() : new ArrayList<WebResource>());
			
			aggregation.setWebResources(webResources);
			if(aggregation.getAbout()!=null){
				MongoUtils.update(EuropeanaAggregationImpl.class,
				aggregation.getAbout(), mongoServer, "webResources",
				webResources);
			}
			else{
				mongoServer.getDatastore().save(aggregation);
			}

		
		}
		return aggregation;
	}

	public EuropeanaAggregation appendWebResource(
			EuropeanaAggregation aggregation,
			List<WebResource> webResources, MongoServer mongoServer)
			throws InstantiationException, IllegalAccessException {
		
			
			aggregation.setWebResources(webResources);
			if(aggregation.getAbout()!=null){
				MongoUtils.update(EuropeanaAggregationImpl.class,
				aggregation.getAbout(), mongoServer, "webResources",
				webResources);
			}
			else{
				mongoServer.getDatastore().save(aggregation);
			}

		
		return aggregation;
	}
	public EuropeanaAggregationImpl createAggregationMongoFields(
			eu.europeana.corelib.definitions.jibx.EuropeanaAggregationType aggregation,
			MongoServer mongoServer) throws InstantiationException,
			IllegalAccessException {
		EuropeanaAggregationImpl mongoAggregation = new EuropeanaAggregationImpl();
		//mongoAggregation.setId(new ObjectId());
		mongoAggregation.setAbout(aggregation.getAbout());
		mongoAggregation.setDcCreator(MongoUtils.createResourceOrLiteralMapFromString(
				aggregation.getCreator()));
		mongoAggregation.setEdmCountry(MongoUtils.createLiteralMapFromString(aggregation.getCountry()));
		mongoAggregation.setEdmIsShownBy(SolrUtils.exists(IsShownBy.class,
				aggregation.getIsShownBy()).getResource());
		mongoAggregation.setEdmLandingPage(SolrUtils.exists(LandingPage.class,
				aggregation.getLandingPage()).getResource());
		mongoAggregation.setEdmLanguage(MongoUtils.createLiteralMapFromString(aggregation.getLanguage()));
		mongoAggregation.setAggregatedCHO(SolrUtils.exists(AggregatedCHO.class,
				aggregation.getAggregatedCHO()).getResource());
		mongoAggregation.setEdmRights(MongoUtils.createResourceOrLiteralMapFromString(aggregation.getRights()));

		mongoAggregation.setAggregates(SolrUtils
				.resourceListToArray(aggregation.getAggregateList()));
		mongoAggregation.setEdmHasView(SolrUtils
				.resourceListToArray(aggregation.getHasViewList()));
		if (((EdmMongoServer) mongoServer).getDatastore().find(EuropeanaAggregationImpl.class).filter("about",
				mongoAggregation.getAbout()) != null) {
			// TODO:update Aggregation
		} else {
			mongoServer.getDatastore().save(mongoAggregation);
		}
		return mongoAggregation;
	}

	public void deleteAggregationFromMongo(String about,
			EdmMongoServer mongoServer) {
		MongoUtils.delete(EuropeanaAggregation.class, about, mongoServer);
	}
	
	private boolean belongsTo(EuropeanaAggregation aggregation, WebResource webResource){
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
