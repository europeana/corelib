package eu.europeana.corelib.solr.utils.construct;

import java.util.ArrayList;
import java.util.List;

import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;

import eu.europeana.corelib.solr.MongoServer;
import eu.europeana.corelib.solr.entity.TimespanImpl;
import eu.europeana.corelib.solr.utils.MongoUtils;
import java.lang.reflect.InvocationTargetException;

public class TimespanUpdater implements Updater<TimespanImpl> {
	public TimespanImpl update(TimespanImpl mongoTimespan, TimespanImpl timeSpan,
			MongoServer mongoServer) throws NoSuchMethodException, IllegalAccessException,InvocationTargetException {
		Query<TimespanImpl> updateQuery = mongoServer.getDatastore()
				.createQuery(TimespanImpl.class).field("about")
				.equal(mongoTimespan.getAbout());
		UpdateOperations<TimespanImpl> ops = mongoServer.getDatastore()
				.createUpdateOperations(TimespanImpl.class);
		boolean update = false;
                update = MongoUtils.updateMap(mongoTimespan, timeSpan, "begin", ops)||update;
		update = MongoUtils.updateMap(mongoTimespan, timeSpan, "end", ops)||update;
		update = MongoUtils.updateMap(mongoTimespan, timeSpan, "note", ops)||update;
                update = MongoUtils.updateMap(mongoTimespan, timeSpan, "altLabel", ops)||update;
		update = MongoUtils.updateMap(mongoTimespan, timeSpan, "prefLabel", ops)||update;
		update = MongoUtils.updateMap(mongoTimespan, timeSpan, "dctermsHasPart", ops)||update;
                update = MongoUtils.updateArray(mongoTimespan, timeSpan, "owlSameAs", ops)||update;
		
		if (update) {
			mongoServer.getDatastore().update(updateQuery, ops);
		}
		return mongoTimespan;
	}
}
