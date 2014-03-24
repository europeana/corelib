package eu.europeana.corelib.solr.utils.construct;

import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;

import eu.europeana.corelib.solr.MongoServer;
import eu.europeana.corelib.solr.entity.AggregationImpl;
import eu.europeana.corelib.solr.utils.MongoUtils;

public class AggregationUpdater implements Updater<AggregationImpl> {

	@Override
	public void update(AggregationImpl mongoEntity, AggregationImpl newEntity,
			MongoServer mongoServer) {

		Query<AggregationImpl> updateQuery = mongoServer.getDatastore()
				.createQuery(AggregationImpl.class).field("about")
				.equal(mongoEntity.getAbout());
		UpdateOperations<AggregationImpl> ops = mongoServer.getDatastore()
				.createUpdateOperations(AggregationImpl.class);
		if (newEntity.getEdmPreviewNoDistribute() != null) {
			if (mongoEntity.getEdmPreviewNoDistribute() == null
					|| mongoEntity.getEdmPreviewNoDistribute() != newEntity
							.getEdmPreviewNoDistribute()) {
				ops.set("edmPreviewNoDistribute",
						newEntity.getEdmPreviewNoDistribute());
			}
		} else {
			if (mongoEntity.getEdmPreviewNoDistribute() != null) {
				ops.unset("edmPreviewNoDistribute");
			}
		}

		if (newEntity.getAggregatedCHO() != null) {
			if (mongoEntity.getAggregatedCHO() == null
					|| !mongoEntity.getAggregatedCHO().equals(
							newEntity.getAggregatedCHO())) {
				ops.set("aggregatedCHO", newEntity.getAggregatedCHO());
			}
		} else {
			if (mongoEntity.getAggregatedCHO() != null) {
				ops.unset("aggregatedCHO");
			}
		}

		if (newEntity.getEdmIsShownAt() != null) {
			if (mongoEntity.getEdmIsShownAt() == null
					|| !mongoEntity.getEdmIsShownAt().equals(
							newEntity.getEdmIsShownAt())) {
				ops.set("edmIsShownAt", newEntity.getEdmIsShownAt());
			}
		} else {
			if (mongoEntity.getEdmIsShownAt() != null) {
				ops.unset("edmIsShownAt");
			}
		}

		if (newEntity.getEdmIsShownBy() != null) {
			if (mongoEntity.getEdmIsShownBy() == null
					|| !mongoEntity.getEdmIsShownBy().equals(
							newEntity.getEdmIsShownBy())) {
				ops.set("edmIsShownBy", newEntity.getEdmIsShownBy());
			}
		} else {
			if (mongoEntity.getEdmIsShownBy() != null) {
				ops.unset("edmIsShownBy");
			}
		}

		if (newEntity.getEdmObject() != null) {
			if (mongoEntity.getEdmObject() == null
					|| !mongoEntity.getEdmObject().equals(
							newEntity.getEdmObject())) {
				ops.set("edmObject", newEntity.getEdmObject());
			}
		} else {
			if (mongoEntity.getEdmObject() != null) {
				ops.unset("edmObject");
			}
		}

		if (newEntity.getEdmUgc() != null) {
			if (mongoEntity.getEdmUgc() == null
					|| !mongoEntity.getEdmUgc().equals(newEntity.getEdmUgc())) {
				ops.set("edmUgc", newEntity.getEdmUgc());
			}
		} else {
			if (mongoEntity.getEdmUgc() != null) {
				ops.unset("edmUgc");
			}
		}

		if (newEntity.getEdmDataProvider() != null) {
			if (mongoEntity.getEdmDataProvider() == null
					|| !MongoUtils.mapEquals(newEntity.getEdmDataProvider(),
							mongoEntity.getEdmDataProvider())) {
				ops.set("edmDataProvider", newEntity.getEdmDataProvider());
			}
		} else {
			if (mongoEntity.getEdmDataProvider() != null) {
				ops.unset("edmDataProvider");
			}
		}

		if (newEntity.getEdmProvider() != null) {
			if (mongoEntity.getEdmProvider() == null
					|| !MongoUtils.mapEquals(newEntity.getEdmProvider(),
							mongoEntity.getEdmProvider())) {
				ops.set("edmProvider", newEntity.getEdmProvider());
			}
		} else {
			if (mongoEntity.getEdmProvider() != null) {
				ops.unset("edmProvider");
			}
		}

		if (newEntity.getDcRights() != null) {
			if (mongoEntity.getDcRights() == null
					|| !MongoUtils.mapEquals(newEntity.getDcRights(),
							mongoEntity.getDcRights())) {
				ops.set("dcRights", newEntity.getDcRights());
			}
		} else {
			if (mongoEntity.getDcRights() != null) {
				ops.unset("dcRights");
			}
		}

		if (newEntity.getEdmRights() != null) {
			if (mongoEntity.getEdmRights() == null
					|| !MongoUtils.mapEquals(newEntity.getEdmRights(),
							mongoEntity.getEdmRights())) {
				ops.set("edmRights", newEntity.getEdmRights());
			}
		} else {
			if (mongoEntity.getEdmRights() != null) {
				ops.unset("edmRights");
			}
		}

		if (newEntity.getHasView() != null) {
			if (mongoEntity.getHasView() == null
					|| !MongoUtils.arrayEquals(newEntity.getHasView(),
							mongoEntity.getHasView())) {
				ops.set("hasView", newEntity.getHasView());
			}
		} else {
			if (newEntity.getHasView() != null) {
				ops.unset("hasView");
			}
		}
		if (newEntity.getAggregates() != null) {
			if (mongoEntity.getAggregates() == null
					|| !MongoUtils.arrayEquals(newEntity.getAggregates(),
							mongoEntity.getAggregates())) {
				ops.set("aggregates", newEntity.getAggregates());
			}
		} else {
			if (newEntity.getAggregates() != null) {
				ops.unset("aggregates");
			}
		}
		mongoEntity.setWebResources(newEntity.getWebResources());
		mongoServer.getDatastore().save(newEntity.getWebResources());
		mongoServer.getDatastore().update(updateQuery, ops);
	}

}
