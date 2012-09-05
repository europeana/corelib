package eu.europeana.corelib.solr.server.importer.util;

import java.util.List;

import org.apache.solr.common.SolrInputDocument;

import eu.europeana.corelib.definitions.jibx.AggregatedCHO;
import eu.europeana.corelib.definitions.jibx.Aggregates;
import eu.europeana.corelib.definitions.jibx.Aggregation;
import eu.europeana.corelib.definitions.jibx.EuropeanaAggregationType;
import eu.europeana.corelib.definitions.jibx.HasView;
import eu.europeana.corelib.definitions.jibx.IsShownBy;
import eu.europeana.corelib.definitions.jibx.LandingPage;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.definitions.solr.entity.EuropeanaAggregation;
import eu.europeana.corelib.solr.MongoServer;
import eu.europeana.corelib.solr.entity.EuropeanaAggregationImpl;
import eu.europeana.corelib.solr.entity.WebResourceImpl;
import eu.europeana.corelib.solr.server.EdmMongoServer;
import eu.europeana.corelib.solr.utils.MongoUtils;
import eu.europeana.corelib.solr.utils.SolrUtils;

public final class EuropeanaAggregationFieldInput {

	private EuropeanaAggregationFieldInput() {

	}

	public static SolrInputDocument createAggregationSolrFields(
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
				aggregation.getLandingPage().getResource());
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

	public static EuropeanaAggregation appendWebResource(
			EuropeanaAggregation aggregation,
			List<WebResourceImpl> webResources, MongoServer mongoServer)
			throws InstantiationException, IllegalAccessException {
		

		aggregation.setWebResources(webResources);
		MongoUtils.update(EuropeanaAggregationImpl.class,
				aggregation.getAbout(), mongoServer, "webResources",
				webResources);

		return aggregation;
	}

	
	public static EuropeanaAggregationImpl createAggregationMongoFields(
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

		/*
		 * mongoAggregation.setEdmDataProvider(SolrUtils.exists(DataProvider.class
		 * , (aggregation.getDataProvider())).getString());
		 * mongoAggregation.setEdmIsShownAt(SolrUtils.exists(IsShownAt.class,
		 * (aggregation.getIsShownAt())).getResource());
		 * mongoAggregation.setEdmIsShownBy(SolrUtils.exists(IsShownBy.class,
		 * (aggregation.getIsShownBy())).getResource());
		 * mongoAggregation.setEdmObject(SolrUtils.exists(_Object.class,
		 * (aggregation.getObject())).getResource());
		 * mongoAggregation.setEdmProvider(SolrUtils.exists(Provider.class,
		 * (aggregation.getProvider())).getString());
		 * mongoAggregation.setEdmRights(SolrUtils.exists(Rights1.class,
		 * (aggregation.getRights())).getString());
		 * 
		 * if (aggregation.getUgc() != null) { mongoAggregation
		 * .setEdmUgc(aggregation.getUgc().getUgc().toString()); }
		 * mongoAggregation
		 * .setAggregatedCHO(SolrUtils.exists(AggregatedCHO.class,
		 * (aggregation.getAggregatedCHO())).getResource()); if
		 * (aggregation.getRightList() != null) { List<String> dcRightsList =
		 * new ArrayList<String>(); for (Rights rights :
		 * aggregation.getRightList()) { dcRightsList.add(rights.getString()); }
		 * 
		 * mongoAggregation.setDcRights(dcRightsList .toArray(new
		 * String[dcRightsList.size()])); }
		 * 
		 * if (aggregation.getHasViewList() != null) { List<String> hasViewList
		 * = new ArrayList<String>(); for (HasView hasView :
		 * aggregation.getHasViewList()) {
		 * hasViewList.add(hasView.getResource()); }
		 * mongoAggregation.setHasView(hasViewList .toArray(new
		 * String[hasViewList.size()]));
		 * 
		 * } if
		 * (((EdmMongoServer)mongoServer).searchByAbout(AggregationImpl.class,
		 * mongoAggregation.getAbout())!=null){
		 * MongoUtils.updateAggregation(mongoAggregation,mongoServer); } else {
		 * mongoServer.getDatastore().save(mongoAggregation); }
		 */
		return mongoAggregation;
	}

	public static void deleteAggregationFromMongo(String about,
			EdmMongoServer mongoServer) {
		MongoUtils.delete(Aggregation.class, about, mongoServer);
	}

}
