package eu.europeana.corelib.solr.server.importer.util;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.common.SolrInputDocument;
import org.bson.types.ObjectId;

import eu.europeana.corelib.definitions.jibx.AggregatedCHO;
import eu.europeana.corelib.definitions.jibx.Aggregates;
import eu.europeana.corelib.definitions.jibx.Aggregation;
import eu.europeana.corelib.definitions.jibx.Country;
import eu.europeana.corelib.definitions.jibx.Creator;
import eu.europeana.corelib.definitions.jibx.EuropeanaAggregationType;
import eu.europeana.corelib.definitions.jibx.HasView;
import eu.europeana.corelib.definitions.jibx.IsShownBy;
import eu.europeana.corelib.definitions.jibx.LandingPage;
import eu.europeana.corelib.definitions.jibx.Language1;
import eu.europeana.corelib.definitions.jibx.Rights1;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.definitions.solr.entity.WebResource;
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

		solrInputDocument.addField(
				EdmLabel.EUROPEANA_AGGREGATION_DC_CREATOR.toString(),
				aggregation.getCreator());
		solrInputDocument.addField(
				EdmLabel.EUROPEANA_AGGREGATION_EDM_COUNTRY.toString(),
				aggregation.getCountry().getString());
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
		solrInputDocument.addField(
				EdmLabel.EUROPEANA_AGGREGATION_EDM_LANGUAGE.toString(),
				aggregation.getLanguage().getString());
		solrInputDocument.addField(
				EdmLabel.EUROPEANA_AGGREGATION_EDM_RIGHTS.toString(),
				aggregation.getRights().getResource());
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

	public static EuropeanaAggregationImpl appendWebResource(
			List<EuropeanaAggregationImpl> aggregations,
			List<WebResourceImpl> webResources, MongoServer mongoServer)
			throws InstantiationException, IllegalAccessException {
		EuropeanaAggregationImpl aggregation = findEuropeanaAggregation(
				aggregations, webResources.get(0));

		aggregation.setWebResources(webResources);
		MongoUtils.update(EuropeanaAggregationImpl.class,
				aggregation.getAbout(), mongoServer, "webResources",
				webResources);

		return aggregation;
	}

	private static EuropeanaAggregationImpl findEuropeanaAggregation(
			List<EuropeanaAggregationImpl> aggregations,
			WebResourceImpl webResource) {
		for (EuropeanaAggregationImpl aggregation : aggregations) {
			for (WebResource hasView : aggregation.getWebResources()) {
				if (StringUtils.equals(hasView.getAbout(),
						webResource.getAbout())) {
					return aggregation;
				}
			}
		}
		return null;
	}

	public static EuropeanaAggregationImpl createAggregationMongoFields(
			eu.europeana.corelib.definitions.jibx.EuropeanaAggregationType aggregation,
			MongoServer mongoServer) throws InstantiationException,
			IllegalAccessException {
		EuropeanaAggregationImpl mongoAggregation = new EuropeanaAggregationImpl();
		mongoAggregation.setId(new ObjectId());
		mongoAggregation.setAbout(aggregation.getAbout());
		mongoAggregation.setDcCreator(SolrUtils.exists(Creator.class,
				aggregation.getCreator()).getString());
		mongoAggregation.setEdmCountry(SolrUtils.exists(Country.class,
				aggregation.getCountry()).getString());
		mongoAggregation.setEdmIsShownBy(SolrUtils.exists(IsShownBy.class,
				aggregation.getIsShownBy()).getResource());
		mongoAggregation.setEdmLandingPage(SolrUtils.exists(LandingPage.class,
				aggregation.getLandingPage()).getResource());
		mongoAggregation.setEdmLanguage(SolrUtils.exists(Language1.class,
				aggregation.getLanguage()).getString());
		mongoAggregation.setAggregatedCHO(SolrUtils.exists(AggregatedCHO.class,
				aggregation.getAggregatedCHO()).getResource());
		mongoAggregation.setEdmRights(SolrUtils.exists(Rights1.class,
				aggregation.getRights()).getResource());

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
