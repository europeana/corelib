package eu.europeana.corelib.solr.utils.construct;

import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;
import eu.europeana.corelib.definitions.solr.entity.WebResource;

import eu.europeana.corelib.solr.MongoServer;
import eu.europeana.corelib.solr.entity.AggregationImpl;
import eu.europeana.corelib.solr.utils.MongoUtils;
import java.util.ArrayList;
import java.util.List;

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

                update = MongoUtils.updateString(mongoEntity, newEntity, "edmPreviewNoDistribute", ops)||update;
		update = MongoUtils.updateString(mongoEntity, newEntity, "aggregatedCHO", ops)||update;
                update = MongoUtils.updateString(mongoEntity, newEntity, "edmIsShownAt", ops)||update;
                update = MongoUtils.updateString(mongoEntity, newEntity, "edmIsShownBy", ops)||update;
                update = MongoUtils.updateString(mongoEntity, newEntity, "edmObject", ops)||update;
		update = MongoUtils.updateString(mongoEntity, newEntity, "edmUgc", ops)||update;
                update = MongoUtils.updateString(mongoEntity, newEntity, "edmDataProvider", ops)||update;
                update = MongoUtils.updateString(mongoEntity, newEntity, "edmProvider", ops)||update;
		update = MongoUtils.updateMap(mongoEntity, newEntity, "dcRights", ops)||update;
                update = MongoUtils.updateMap(mongoEntity, newEntity, "edmRights", ops)||update;
                update = MongoUtils.updateArray(mongoEntity, newEntity, "hasView", ops)||update;
		update = MongoUtils.updateArray(mongoEntity, newEntity, "aggregates", ops)||update;
                
		
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
