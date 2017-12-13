/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.europeana.corelib.edm.utils.construct;

import eu.europeana.corelib.edm.exceptions.MongoUpdateException;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import eu.europeana.corelib.storage.MongoServer;
import eu.europeana.corelib.definitions.edm.entity.WebResource;
import eu.europeana.corelib.edm.utils.MongoUtils;
import eu.europeana.corelib.mongo.server.EdmMongoServer;
import eu.europeana.corelib.solr.entity.WebResourceImpl;

/**
 *
 * @author Yorgos.Mamakis@ europeana.eu
 */
public class WebResourceCreator {

	public WebResource saveWebResource(WebResource wr, MongoServer mongo) throws MongoUpdateException {

		WebResourceImpl wrMongo = ((EdmMongoServer) mongo).searchByAbout(WebResourceImpl.class, wr.getAbout());
		if (wrMongo != null) {
			return updateWebResource(wrMongo, wr, mongo);
		}

		mongo.getDatastore().save(wr);
		return wr;
	}

	private WebResource updateWebResource(WebResource wrMongo, WebResource wr, MongoServer mongoServer) throws MongoUpdateException {
		Query<WebResourceImpl> updateQuery = mongoServer.getDatastore()
				.createQuery(WebResourceImpl.class).field("about")
				.equal(wr.getAbout());
		UpdateOperations<WebResourceImpl> ops = mongoServer.getDatastore()
				.createUpdateOperations(WebResourceImpl.class);
		boolean update = false;
		update = MongoUtils.updateMap(wrMongo, wr, "dcDescription", ops)
				|| update;
		update = MongoUtils.updateMap(wrMongo, wr, "dcFormat", ops) || update;
		update = MongoUtils.updateMap(wrMongo, wr, "dcCreator", ops) || update;
		update = MongoUtils.updateMap(wrMongo, wr, "dcSource", ops) || update;
		update = MongoUtils.updateMap(wrMongo, wr, "dctermsConformsTo", ops)
				|| update;
		update = MongoUtils.updateMap(wrMongo, wr, "dctermsCreated", ops)
				|| update;
		update = MongoUtils.updateMap(wrMongo, wr, "dctermsExtent", ops)
				|| update;
		update = MongoUtils.updateMap(wrMongo, wr, "dctermsHasPart", ops)
				|| update;
		update = MongoUtils.updateMap(wrMongo, wr, "dctermsIsFormatOf", ops)
				|| update;
		update = MongoUtils.updateMap(wrMongo, wr, "dctermsIssued", ops)
				|| update;
		update = MongoUtils.updateString(wrMongo, wr, "isNextInSequence", ops)
				|| update;
		update = MongoUtils.updateMap(wrMongo, wr, "webResourceDcRights", ops)
				|| update;
		update = MongoUtils.updateMap(wrMongo, wr, "webResourceEdmRights", ops)
				|| update;
		update = MongoUtils.updateArray(wrMongo, wr, "owlSameAs", ops)
				|| update;
		update = MongoUtils.updateString(wrMongo,wr,"edmPreview",ops)||update;
		update = MongoUtils.updateArray(wrMongo,wr,"svcsHasService",ops)||update;
		update = MongoUtils.updateArray(wrMongo,wr,"dctermsIsReferencedBy",ops)||update;
		if (update) {
			mongoServer.getDatastore().update(updateQuery, ops);
		}
		return wrMongo;
	}

}
