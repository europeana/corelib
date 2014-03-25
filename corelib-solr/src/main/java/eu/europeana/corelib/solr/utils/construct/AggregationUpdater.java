package eu.europeana.corelib.solr.utils.construct;

import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;

import eu.europeana.corelib.solr.MongoServer;
import eu.europeana.corelib.solr.entity.AggregationImpl;
import eu.europeana.corelib.solr.utils.MongoUtils;

public class AggregationUpdater implements Updater<AggregationImpl> {

	@Override
	public AggregationImpl update(AggregationImpl mongoEntity, AggregationImpl newEntity,
			MongoServer mongoServer) {

		Query<AggregationImpl> updateQuery = mongoServer.getDatastore()
				.createQuery(AggregationImpl.class).field("about")
				.equal(mongoEntity.getAbout());
		UpdateOperations<AggregationImpl> ops = mongoServer.getDatastore()
				.createUpdateOperations(AggregationImpl.class);
		boolean update = false;
		if (newEntity.getEdmPreviewNoDistribute() != null) {
			if (mongoEntity.getEdmPreviewNoDistribute() == null
					|| mongoEntity.getEdmPreviewNoDistribute() != newEntity
							.getEdmPreviewNoDistribute()) {
				ops.set("edmPreviewNoDistribute",
						newEntity.getEdmPreviewNoDistribute());
				mongoEntity.setEdmPreviewNoDistribute(newEntity.getEdmPreviewNoDistribute());
				update = true;
			}
		} else {
			if (mongoEntity.getEdmPreviewNoDistribute() != null) {
				ops.unset("edmPreviewNoDistribute");
				mongoEntity.setEdmPreviewNoDistribute(false);
				update = true;
			}
		}

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

		if (newEntity.getEdmIsShownAt() != null) {
			if (mongoEntity.getEdmIsShownAt() == null
					|| !mongoEntity.getEdmIsShownAt().equals(
							newEntity.getEdmIsShownAt())) {
				ops.set("edmIsShownAt", newEntity.getEdmIsShownAt());
				mongoEntity.setEdmIsShownAt(newEntity.getEdmIsShownAt());
				update = true;
			}
		} else {
			if (mongoEntity.getEdmIsShownAt() != null) {
				ops.unset("edmIsShownAt");
				mongoEntity.setEdmIsShownAt(null);
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

		if (newEntity.getEdmObject() != null) {
			if (mongoEntity.getEdmObject() == null
					|| !mongoEntity.getEdmObject().equals(
							newEntity.getEdmObject())) {
				ops.set("edmObject", newEntity.getEdmObject());
				mongoEntity.setEdmObject(newEntity.getEdmObject());
				update = true;
			}
		} else {
			if (mongoEntity.getEdmObject() != null) {
				ops.unset("edmObject");
				mongoEntity.setEdmObject(null);
				update = true;
			}
		}

		if (newEntity.getEdmUgc() != null) {
			if (mongoEntity.getEdmUgc() == null
					|| !mongoEntity.getEdmUgc().equals(newEntity.getEdmUgc())) {
				ops.set("edmUgc", newEntity.getEdmUgc());
				mongoEntity.setEdmUgc(newEntity.getEdmUgc());
				update = true;
			}
		} else {
			if (mongoEntity.getEdmUgc() != null) {
				ops.unset("edmUgc");
				mongoEntity.setEdmUgc(null);
				update = true;
			}
		}

		if (newEntity.getEdmDataProvider() != null) {
			if (mongoEntity.getEdmDataProvider() == null
					|| !MongoUtils.mapEquals(newEntity.getEdmDataProvider(),
							mongoEntity.getEdmDataProvider())) {
				ops.set("edmDataProvider", newEntity.getEdmDataProvider());
				mongoEntity.setEdmDataProvider(newEntity.getEdmDataProvider());
				update = true;
			}
		} else {
			if (mongoEntity.getEdmDataProvider() != null) {
				ops.unset("edmDataProvider");
				mongoEntity.setEdmDataProvider(null);
				update = true;
			}
		}

		if (newEntity.getEdmProvider() != null) {
			if (mongoEntity.getEdmProvider() == null
					|| !MongoUtils.mapEquals(newEntity.getEdmProvider(),
							mongoEntity.getEdmProvider())) {
				ops.set("edmProvider", newEntity.getEdmProvider());
				mongoEntity.setEdmProvider(newEntity.getEdmProvider());
				update = true;
			}
		} else {
			if (mongoEntity.getEdmProvider() != null) {
				ops.unset("edmProvider");
				mongoEntity.setEdmProvider(null);
				update = true;
			}
		}

		if (newEntity.getDcRights() != null) {
			if (mongoEntity.getDcRights() == null
					|| !MongoUtils.mapEquals(newEntity.getDcRights(),
							mongoEntity.getDcRights())) {
				ops.set("dcRights", newEntity.getDcRights());
				mongoEntity.setDcRights(newEntity.getDcRights());
				update = true;
			}
		} else {
			if (mongoEntity.getDcRights() != null) {
				ops.unset("dcRights");
				mongoEntity.setDcRights(null);
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

		if (newEntity.getHasView() != null) {
			if (mongoEntity.getHasView() == null
					|| !MongoUtils.arrayEquals(newEntity.getHasView(),
							mongoEntity.getHasView())) {
				ops.set("hasView", newEntity.getHasView());
				mongoEntity.setHasView(newEntity.getHasView());
				update = true;
			}
		} else {
			if (newEntity.getHasView() != null) {
				ops.unset("hasView");
				mongoEntity.setHasView(null);
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
			if (newEntity.getAggregates() != null) {
				ops.unset("aggregates");
				mongoEntity.setAggregates(null);
				update = true;
			}
		}
		MongoUtils.update(AggregationImpl.class, mongoEntity.getAbout(),
				mongoServer, "webResources", newEntity.getWebResources());
		if (update) {
			mongoServer.getDatastore().update(updateQuery, ops);
		}
		return mongoEntity;
	}

}

