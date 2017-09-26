package eu.europeana.corelib.edm.utils.construct;

import eu.europeana.corelib.edm.exceptions.MongoUpdateException;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import eu.europeana.corelib.storage.MongoServer;
import eu.europeana.corelib.definitions.edm.entity.WebResource;
import eu.europeana.corelib.edm.utils.MongoUtils;
import eu.europeana.corelib.solr.entity.AggregationImpl;

import java.util.ArrayList;
import java.util.List;

public class AggregationUpdater implements Updater<AggregationImpl> {

    @Override
    public AggregationImpl update(AggregationImpl mongoEntity, AggregationImpl newEntity,
            MongoServer mongoServer) throws MongoUpdateException {

        Query<AggregationImpl> updateQuery = mongoServer.getDatastore()
                .createQuery(AggregationImpl.class).field("about")
                .equal(mongoEntity.getAbout());
        UpdateOperations<AggregationImpl> ops = mongoServer.getDatastore()
                .createUpdateOperations(AggregationImpl.class);

        boolean update = false;

        update = MongoUtils.updateString(mongoEntity, newEntity, "aggregatedCHO", ops) || update;
        update = MongoUtils.updateString(mongoEntity, newEntity, "edmIsShownAt", ops) || update;
        update = MongoUtils.updateString(mongoEntity, newEntity, "edmIsShownBy", ops) || update;
        update = MongoUtils.updateString(mongoEntity, newEntity, "edmObject", ops) || update;
        update = MongoUtils.updateString(mongoEntity, newEntity, "edmUgc", ops) || update;
        update = MongoUtils.updateMap(mongoEntity, newEntity, "edmDataProvider", ops) || update;
        update = MongoUtils.updateMap(mongoEntity, newEntity, "edmProvider", ops) || update;
        update = MongoUtils.updateMap(mongoEntity, newEntity, "dcRights", ops) || update;
        update = MongoUtils.updateMap(mongoEntity, newEntity, "edmRights", ops) || update;
        update = MongoUtils.updateArray(mongoEntity, newEntity, "hasView", ops) || update;
        update = MongoUtils.updateArray(mongoEntity, newEntity, "aggregates", ops) || update;
        if (newEntity.getEdmPreviewNoDistribute() != null) {
            if (mongoEntity.getEdmPreviewNoDistribute() == null || mongoEntity.getEdmPreviewNoDistribute() != newEntity.
                    getEdmPreviewNoDistribute()) {
                ops.set("edmPreviewNoDistribute", newEntity.getEdmPreviewNoDistribute());
                mongoEntity.setEdmPreviewNoDistribute(newEntity.getEdmPreviewNoDistribute());
                update = true;
            }
        } else {
            if(mongoEntity.getEdmPreviewNoDistribute()!=null){
                ops.unset("edmPreviewNoDistribute");
                mongoEntity.setEdmPreviewNoDistribute(null);
                update = true;
            }
        }

        List<WebResource> webResources = new ArrayList<WebResource>();
        for (WebResource wr : mongoEntity.getWebResources()) {
            webResources.add(new WebResourceCreator().saveWebResource(wr, mongoServer));
        }
        mongoEntity.setWebResources(webResources);

        if (update) {
            mongoServer.getDatastore().update(updateQuery, ops);
        }
        return mongoEntity;
    }
}
