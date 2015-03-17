package eu.europeana.corelib.edm.utils.construct;

import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;

import eu.europeana.corelib.MongoServer;
import eu.europeana.corelib.edm.utils.MongoUtils;
import eu.europeana.corelib.solr.entity.ProvidedCHOImpl;

import java.lang.reflect.InvocationTargetException;

public class ProvidedChoUpdater implements Updater<ProvidedCHOImpl> {

	@Override
	public ProvidedCHOImpl update(ProvidedCHOImpl mongoEntity, ProvidedCHOImpl newEntity,
			MongoServer mongoServer) throws NoSuchMethodException, IllegalAccessException,InvocationTargetException{
		Query<ProvidedCHOImpl> updateQuery = mongoServer.getDatastore()
				.createQuery(ProvidedCHOImpl.class).field("about")
				.equal(mongoEntity.getAbout());
		UpdateOperations<ProvidedCHOImpl> ops = mongoServer.getDatastore()
				.createUpdateOperations(ProvidedCHOImpl.class);
		boolean update = false;
		update = MongoUtils.updateArray(mongoEntity, newEntity, "owlSameAs", ops)||update;
		
		if(update){
			mongoServer.getDatastore().update(updateQuery, ops);
		}
		return mongoEntity;
	}

}
