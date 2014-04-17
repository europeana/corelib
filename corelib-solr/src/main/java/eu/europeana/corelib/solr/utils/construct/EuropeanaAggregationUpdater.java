package eu.europeana.corelib.solr.utils.construct;

import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;
import eu.europeana.corelib.definitions.solr.entity.WebResource;

import eu.europeana.corelib.solr.MongoServer;
import eu.europeana.corelib.solr.entity.EuropeanaAggregationImpl;
import eu.europeana.corelib.solr.utils.MongoUtils;
import java.util.ArrayList;
import java.util.List;

public class EuropeanaAggregationUpdater implements
		Updater<EuropeanaAggregationImpl> {

	@Override
	public EuropeanaAggregationImpl update(EuropeanaAggregationImpl mongoEntity,
			EuropeanaAggregationImpl newEntity, MongoServer mongoServer) {
		Query<EuropeanaAggregationImpl> updateQuery = mongoServer
				.getDatastore().createQuery(EuropeanaAggregationImpl.class)
				.field("about").equal(mongoEntity.getAbout());
		UpdateOperations<EuropeanaAggregationImpl> ops = mongoServer
				.getDatastore().createUpdateOperations(
						EuropeanaAggregationImpl.class);
		boolean update = false;
		if (newEntity.getAggregatedCHO() != null) {
			if (mongoEntity.getAggregatedCHO() == null
					|| !mongoEntity.getAggregatedCHO().equals(
							newEntity.getAggregatedCHO())) {
				ops.set("aggregatedCHO", newEntity.getAggregatedCHO());
				mongoEntity.setAggregatedCHO(newEntity.getAggregatedCHO());
				update = true;
			}
		} else {
			if (mongoEntity.getAggregatedCHO() != null) {
				ops.unset("aggregatedCHO");
				mongoEntity.setAggregatedCHO(null);
				update = true;
			}
		}

		if (newEntity.getEdmIsShownBy() != null) {
			if (mongoEntity.getEdmIsShownBy() == null
					|| !mongoEntity.getEdmIsShownBy().equals(
							newEntity.getEdmIsShownBy())) {
				ops.set("edmIsShownBy", newEntity.getEdmIsShownBy());
				mongoEntity.setEdmIsShownBy(newEntity.getEdmIsShownBy());
				update = true;
			}
		} else {
			if (mongoEntity.getEdmIsShownBy() != null) {
				ops.unset("edmIsShownBy");
				mongoEntity.setEdmIsShownBy(null);
				update = true;
			}
		}

		if (newEntity.getEdmRights() != null) {
			if (mongoEntity.getEdmRights() == null
					|| !MongoUtils.mapEquals(newEntity.getEdmRights(),
							mongoEntity.getEdmRights())) {
				ops.set("edmRights", newEntity.getEdmRights());
				mongoEntity.setEdmRights(newEntity.getEdmRights());
				update = true;
			}
		} else {
			if (mongoEntity.getEdmRights() != null) {
				ops.unset("edmRights");
				mongoEntity.setEdmRights(null);
				update = true;
			}
		}

		if (newEntity.getEdmCountry() != null) {
			if (mongoEntity.getEdmCountry() == null
					|| !MongoUtils.mapEquals(newEntity.getEdmCountry(),
							mongoEntity.getEdmCountry())) {
				ops.set("edmCountry", newEntity.getEdmCountry());
				mongoEntity.setEdmCountry(newEntity.getEdmCountry());
				update = true;
			}
		} else {
			if (mongoEntity.getEdmCountry() != null) {
				ops.unset("edmCountry");
				mongoEntity.setEdmCountry(null);
				update = true;
			}
		}
		if (newEntity.getEdmLandingPage() != null) {
			if (mongoEntity.getEdmLandingPage() == null
					|| !newEntity.getEdmLandingPage().equals(
							mongoEntity.getEdmRights())) {
				ops.set("edmLandingPage", newEntity.getEdmLandingPage());
				mongoEntity.setEdmLandingPage(newEntity.getEdmLandingPage());
				update = true;
			}
		} else {
			if (mongoEntity.getEdmLandingPage() != null) {
				ops.unset("edmLandingPage");
				mongoEntity.setEdmLandingPage(null);
				update = true;
			}
		}

		if (newEntity.getEdmLanguage() != null) {
			if (mongoEntity.getEdmLanguage() == null
					|| !MongoUtils.mapEquals(mongoEntity.getEdmLanguage(),
							newEntity.getEdmLanguage())) {
				ops.set("edmLanguage", newEntity.getEdmLanguage());
				mongoEntity.setEdmLanguage(newEntity.getEdmLanguage());
				update = true;
			}
		} else {
			if (mongoEntity.getEdmLanguage() != null) {
				ops.unset("edmLanguage");
				mongoEntity.setEdmLanguage(null);
				update = true;
			}
		}

		if (newEntity.getDcCreator() != null) {
			if (mongoEntity.getDcCreator() == null
					|| !MongoUtils.mapEquals(mongoEntity.getDcCreator(),
							newEntity.getDcCreator())) {
				ops.set("dcCreator", newEntity.getDcCreator());
				mongoEntity.setDcCreator(newEntity.getDcCreator());
				update = true;
			}
		} else {
			if (mongoEntity.getDcCreator() != null) {
				ops.unset("dcCreator");
				mongoEntity.setDcCreator(null);
				update = true;
			}
		}
		if (newEntity.getEdmPreview() != null) {
			if (mongoEntity.getEdmPreview() == null
					|| !mongoEntity.getEdmPreview().equals(
							newEntity.getDcCreator())) {
				ops.set("edmPreview", newEntity.getEdmPreview());
				mongoEntity.setEdmPreview(newEntity.getEdmPreview());
				update = true;
			}
		} else {
			if (mongoEntity.getEdmPreview() != null) {
				ops.unset("edmPreview");
				mongoEntity.setEdmPreview(null);
				update = true;
			}
		}
		if (newEntity.getAggregates() != null) {
			if (mongoEntity.getAggregates() == null
					|| !MongoUtils.arrayEquals(newEntity.getAggregates(),
							mongoEntity.getAggregates())) {
				ops.set("aggregates", newEntity.getAggregates());
				mongoEntity.setAggregates(newEntity.getAggregates());
				update = true;
			}
		} else {
			if (mongoEntity.getAggregates() != null) {
				ops.unset("aggregates");
				mongoEntity.setAggregates(null);
				update = true;
			}
		}
		List<WebResource> webResources = new ArrayList<WebResource>();
                for(WebResource wr : mongoEntity.getWebResources()){
                    webResources.add(new WebResourceCreator().saveWebResource(wr, mongoServer));
                }
                mongoEntity.setWebResources(webResources);
		if (update) {
			mongoServer.getDatastore().update(updateQuery, ops);
		}
		return mongoEntity;
	}

}
