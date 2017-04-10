/*
 * Copyright 2007-2012 The Europeana Foundation
 *
 *  Licenced under the EUPL, Version 1.1 (the "Licence") and subsequent versions as approved
 *  by the European Commission;
 *  You may not use this work except in compliance with the Licence.
 * 
 *  You may obtain a copy of the Licence at:
 *  http://joinup.ec.europa.eu/software/page/eupl
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under
 *  the Licence is distributed on an "AS IS" basis, without warranties or conditions of
 *  any kind, either express or implied.
 *  See the Licence for the specific language governing permissions and limitations under
 *  the Licence.
 */
package eu.europeana.corelib.edm.server.importer.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.common.SolrInputDocument;

import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import eu.europeana.corelib.definitions.jibx.ProvidedCHOType;
import eu.europeana.corelib.definitions.jibx.SameAs;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.edm.utils.MongoUtils;
import eu.europeana.corelib.edm.utils.SolrUtils;
import eu.europeana.corelib.mongo.server.EdmMongoServer;
import eu.europeana.corelib.solr.entity.ProvidedCHOImpl;

/**
 * Class constructing a SOLR document and MongoDB representation of a
 * ProvidedCHO
 * 
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
public final class ProvidedCHOFieldInput {

	public ProvidedCHOFieldInput() {

	}

	/**
	 * Method filling in a SolrInputDocument with the fields of a providedCHO
	 * 
	 * @param providedCHO
	 *            The ProvidedCHO representation from the JiBX bindings
	 * @param solrInputDocument
	 *            The SolrInputDocument whose fields the class fills in
	 * @return A SolrInputDocument with filled in ProvidedCHO fields
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public SolrInputDocument createProvidedCHOFields(
			ProvidedCHOType providedCHO, SolrInputDocument solrInputDocument)
			throws InstantiationException, IllegalAccessException {
		solrInputDocument.addField(EdmLabel.EUROPEANA_ID.toString(),
				providedCHO.getAbout());
		if (providedCHO.getSameAList() != null) {
			for (SameAs sameAs : providedCHO.getSameAList()) {
				solrInputDocument.addField(
						EdmLabel.PROXY_OWL_SAMEAS.toString(),
						sameAs.getResource());
			}
		}

		solrInputDocument.addField(
				EdmLabel.EUROPEANA_COLLECTIONNAME.toString(),
				StringUtils.substringBetween(providedCHO.getAbout(), "/", "/"));
		return solrInputDocument;
	}

	/**
	 * 
	 * Method Creating a MongoDB Entity of a ProvidedCHO
	 * 
	 * @param providedCHO
	 *            The ProvidedCHO representation from the JiBX bindings
	 * @param mongoServer
	 *            The MongoDB Server object to save the ProvidedCHO
	 * @return The MongoDB ProvidedCHO Entity
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public ProvidedCHOImpl createProvidedCHOMongoFields(
			ProvidedCHOType providedCHO, EdmMongoServer mongoServer)
			throws InstantiationException, IllegalAccessException {
		ProvidedCHOImpl mongoProvidedCHO = mongoServer.getDatastore()
				.find(ProvidedCHOImpl.class)
				.filter("about", "/item" + providedCHO.getAbout()).get();
		// If the ProvidedCHO does not exist create it
		if (mongoProvidedCHO == null) {
			mongoProvidedCHO = new ProvidedCHOImpl();
			// mongoProvidedCHO.setId(new ObjectId());
			mongoProvidedCHO.setAbout("/item" + providedCHO.getAbout());

			mongoProvidedCHO.setOwlSameAs(SolrUtils
					.resourceListToArray(providedCHO.getSameAList()));

			mongoServer.getDatastore().save(mongoProvidedCHO);
		} else {
			// update the ProvidedCHO
			List<String> owlSameAsList = null;
			if (providedCHO.getSameAList() != null) {
				owlSameAsList = new ArrayList<String>();
				for (SameAs sameAs : providedCHO.getSameAList()) {
					owlSameAsList.add(sameAs.getResource());
				}
				Query<ProvidedCHOImpl> query = mongoServer.getDatastore()
						.createQuery(ProvidedCHOImpl.class)
						.filter("about", providedCHO.getAbout());
				UpdateOperations<ProvidedCHOImpl> ops = mongoServer.getDatastore().createUpdateOperations(ProvidedCHOImpl.class);
				ops.set("owlSameAs",
						owlSameAsList);
				mongoServer.getDatastore().update(query, ops);
			}

		}
		return mongoProvidedCHO;
	}

	/**
	 * Delete a providedCHO from mongoDB storage based on its about field
	 * 
	 * @param about
	 *            the about field to search
	 * @param mongoServer
	 *            the mongoserver to use
	 */
	public void deleteProvideCHOFromMongo(String about,
			EdmMongoServer mongoServer) {
		MongoUtils.delete(ProvidedCHOImpl.class, about, mongoServer);
	}

        
        
        /**
	 * 
	 * Method Creating a MongoDB Entity of a ProvidedCHO
	 * 
	 * @param providedCHO
	 *            The ProvidedCHO representation from the JiBX bindings
	 * @return The MongoDB ProvidedCHO Entity
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public ProvidedCHOImpl createProvidedCHOMongoFields(
			ProvidedCHOType providedCHO)
			throws InstantiationException, IllegalAccessException {
		ProvidedCHOImpl mongoProvidedCHO = new ProvidedCHOImpl();
			mongoProvidedCHO.setAbout("/item" + providedCHO.getAbout());

			mongoProvidedCHO.setOwlSameAs(SolrUtils
					.resourceListToArray(providedCHO.getSameAList()));
		return mongoProvidedCHO;
	}
}
