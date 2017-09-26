package eu.europeana.corelib.edm.utils.construct;

import eu.europeana.corelib.edm.exceptions.MongoUpdateException;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import eu.europeana.corelib.storage.MongoServer;
import eu.europeana.corelib.edm.utils.MongoUtils;
import eu.europeana.corelib.solr.entity.ConceptImpl;

public class ConceptUpdater implements Updater<ConceptImpl> {

        @Override
	public ConceptImpl update(ConceptImpl conceptMongo, ConceptImpl concept,
			MongoServer mongoServer) throws MongoUpdateException {
		Query<ConceptImpl> updateQuery = mongoServer.getDatastore()
				.createQuery(ConceptImpl.class).field("about")
				.equal(conceptMongo.getAbout());
		UpdateOperations<ConceptImpl> ops = mongoServer.getDatastore()
				.createUpdateOperations(ConceptImpl.class);
		boolean update = false;
                update = MongoUtils.updateMap(conceptMongo, concept, "altLabel", ops)||update;
		update = MongoUtils.updateMap(conceptMongo, concept, "prefLabel", ops)||update;
		update = MongoUtils.updateMap(conceptMongo, concept, "hiddenLabel", ops)||update;
		update = MongoUtils.updateMap(conceptMongo, concept, "notation", ops)||update;
		update = MongoUtils.updateMap(conceptMongo, concept, "note", ops)||update;
                update = MongoUtils.updateArray(conceptMongo, concept, "broader", ops)||update;
                update = MongoUtils.updateArray(conceptMongo, concept, "broadMatch", ops)||update;
		update = MongoUtils.updateArray(conceptMongo, concept, "closeMatch", ops)||update;
		update = MongoUtils.updateArray(conceptMongo, concept, "exactMatch", ops)||update;
		update = MongoUtils.updateArray(conceptMongo, concept, "inScheme", ops)||update;
		update = MongoUtils.updateArray(conceptMongo, concept, "narrower", ops)||update;
		update = MongoUtils.updateArray(conceptMongo, concept, "narrowMatch", ops)||update;
		update = MongoUtils.updateArray(conceptMongo, concept, "relatedMatch", ops)||update;
		update = MongoUtils.updateArray(conceptMongo, concept, "related", ops)||update;
		
		if (update) {
			mongoServer.getDatastore().update(updateQuery, ops);
		}
		return conceptMongo;
	}

}
