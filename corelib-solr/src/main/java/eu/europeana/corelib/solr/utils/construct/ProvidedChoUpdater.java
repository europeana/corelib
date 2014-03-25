package eu.europeana.corelib.solr.utils.construct;

import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;

import eu.europeana.corelib.solr.MongoServer;
import eu.europeana.corelib.solr.entity.ProvidedCHOImpl;
import eu.europeana.corelib.solr.utils.MongoUtils;

public class ProvidedChoUpdater implements Updater<ProvidedCHOImpl> {

	@Override
	public ProvidedCHOImpl update(ProvidedCHOImpl mongoEntity, ProvidedCHOImpl newEntity,
			MongoServer mongoServer) {
		Query<ProvidedCHOImpl> updateQuery = mongoServer.getDatastore()
				.createQuery(ProvidedCHOImpl.class).field("about")
				.equal(mongoEntity.getAbout());
		UpdateOperations<ProvidedCHOImpl> ops = mongoServer.getDatastore()
				.createUpdateOperations(ProvidedCHOImpl.class);
		boolean update = false;
		if(newEntity.getOwlSameAs()!=null){
			if(mongoEntity.getOwlSameAs()==null|| !MongoUtils.arrayEquals(newEntity.getOwlSameAs(), mongoEntity.getOwlSameAs())){
				ops.set("owlSameAs",newEntity.getOwlSameAs());
				mongoEntity.setOwlSameAs(newEntity.getOwlSameAs());
				update = true;
			}
		} else {
			if(mongoEntity.getOwlSameAs()!=null){
				ops.unset("owlSameAs");
				mongoEntity.setOwlSameAs(null);
				update = true;
			}
		}
		
		if(update){
			mongoServer.getDatastore().update(updateQuery, ops);
		}
		return mongoEntity;
	}

}
