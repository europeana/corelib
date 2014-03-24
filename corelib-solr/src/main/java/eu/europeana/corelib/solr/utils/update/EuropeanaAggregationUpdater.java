package eu.europeana.corelib.solr.utils.update;

import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;

import eu.europeana.corelib.solr.MongoServer;
import eu.europeana.corelib.solr.entity.EuropeanaAggregationImpl;
import eu.europeana.corelib.solr.utils.MongoUtils;

public class EuropeanaAggregationUpdater implements
		Updater<EuropeanaAggregationImpl> {

	@Override
	public void update(EuropeanaAggregationImpl mongoEntity,
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
				update = true;
			}
		} else {
			if (mongoEntity.getAggregatedCHO() != null) {
				ops.unset("aggregatedCHO");
				update = true;
			}
		}

		if (newEntity.getEdmIsShownBy() != null) {
			if (mongoEntity.getEdmIsShownBy() == null
					|| !mongoEntity.getEdmIsShownBy().equals(
							newEntity.getEdmIsShownBy())) {
				ops.set("edmIsShownBy", newEntity.getEdmIsShownBy());
				update = true;
			}
		} else {
			if (mongoEntity.getEdmIsShownBy() != null) {
				ops.unset("edmIsShownBy");
				update = true;
			}
		}

		if (newEntity.getEdmRights() != null) {
			if (mongoEntity.getEdmRights() == null
					|| !MongoUtils.mapEquals(newEntity.getEdmRights(),
							mongoEntity.getEdmRights())) {
				ops.set("edmRights", newEntity.getEdmRights());
				update = true;
			}
		} else {
			if (mongoEntity.getEdmRights() != null) {
				ops.unset("edmRights");
				update = true;
			}
		}

		if (newEntity.getEdmCountry() != null) {
			if (mongoEntity.getEdmCountry() == null
					|| !MongoUtils.mapEquals(newEntity.getEdmCountry(),
							mongoEntity.getEdmCountry())) {
				ops.set("edmCountry", newEntity.getEdmCountry());
				update = true;
			}
		} else {
			if (mongoEntity.getEdmCountry() != null) {
				ops.unset("edmCountry");
				update = true;
			}
		}
		if (newEntity.getEdmLandingPage() != null) {
			if (mongoEntity.getEdmLandingPage() == null
					|| !newEntity.getEdmLandingPage().equals(
							mongoEntity.getEdmRights())) {
				ops.set("edmLandingPage", newEntity.getEdmLandingPage());
				update = true;
			}
		} else {
			if (mongoEntity.getEdmLandingPage() != null) {
				ops.unset("edmLandingPage");
				update = true;
			}
		}

		if (newEntity.getEdmLanguage() != null) {
			if (mongoEntity.getEdmLanguage() == null
					|| !MongoUtils.mapEquals(mongoEntity.getEdmLanguage(),
							newEntity.getEdmLanguage())) {
				ops.set("edmLanguage", newEntity.getEdmLanguage());
				update = true;
			}
		} else {
			if (mongoEntity.getEdmLanguage() != null) {
				ops.unset("edmLanguage");
				update = true;
			}
		}

		if (newEntity.getDcCreator() != null) {
			if (mongoEntity.getDcCreator() == null
					|| !MongoUtils.mapEquals(mongoEntity.getDcCreator(),
							newEntity.getDcCreator())) {
				ops.set("dcCreator", newEntity.getDcCreator());
				update = true;
			}
		} else {
			if (mongoEntity.getDcCreator() != null) {
				ops.unset("dcCreator");
				update = true;
			}
		}
		if (newEntity.getEdmPreview() != null) {
			if (mongoEntity.getEdmPreview() == null
					|| !mongoEntity.getEdmPreview().equals(
							newEntity.getDcCreator())) {
				ops.set("edmPreview", newEntity.getEdmPreview());
				update = true;
			}
		} else {
			if (mongoEntity.getEdmPreview() != null) {
				ops.unset("edmPreview");
				update = true;
			}
		}
		if (newEntity.getAggregates() != null) {
			if (mongoEntity.getAggregates() == null
					|| !MongoUtils.arrayEquals(newEntity.getAggregates(),
							mongoEntity.getAggregates())) {
				ops.set("aggregates", newEntity.getAggregates());
				update = true;
			}
		} else {
			if (mongoEntity.getAggregates() != null) {
				ops.unset("aggregates");
				update = true;
			}
		}
		if (newEntity.getWebResources() != null) {
			mongoEntity.setWebResources(newEntity.getWebResources());
			mongoServer.getDatastore().save(newEntity.getWebResources());
			update = true;
		}
		if (update) {
			mongoServer.getDatastore().update(updateQuery, ops);
		}
	}

}
