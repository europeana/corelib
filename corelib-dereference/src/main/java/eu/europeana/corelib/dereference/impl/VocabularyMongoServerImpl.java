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
package eu.europeana.corelib.dereference.impl;

import java.util.List;

import com.mongodb.MongoClient;
import eu.europeana.corelib.storage.impl.MongoProviderImpl;
import org.apache.commons.lang.StringUtils;

import org.apache.log4j.Logger;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import eu.europeana.corelib.dereference.VocabularyMongoServer;

/**
 * A mongo server instance for use with the controlled vocabularies.
 *
 * @author Yorgos.Mamakis@ kb.nl
 * @author Patrick Ehlert
 * 
 */
public class VocabularyMongoServerImpl implements VocabularyMongoServer {

	private static final Logger LOG = Logger.getLogger(VocabularyMongoServerImpl.class.getName());

	private MongoClient mongoClient;
	private Datastore datastore;

	/**
	 * Create a new datastore to do get/delete/save operations on the database
	 * Any required login credentials should be in the provided MongoClient
	 * @param mongoClient
	 * @param databaseName
	 */
	public VocabularyMongoServerImpl(MongoClient mongoClient, String databaseName) {
		this.mongoClient = mongoClient;
		initDatastore(mongoClient, databaseName);
	}

	/**
	 * Create a new datastore to do get/delete/save operations on the database
	 * @param host
	 * @param port
	 * @param databaseName
	 * @param username
	 * @param password
	 */
	public VocabularyMongoServerImpl(String host, int port, String databaseName, String username, String password) {
		this.mongoClient = new MongoProviderImpl(host, String.valueOf(port), databaseName, username, password).getMongo();
		initDatastore(this.mongoClient, databaseName);
	}

	/**
	 * Create a datastore and map the required morphia entities
	 */
	private void initDatastore(MongoClient mongoClient, String databaseName) {
		Morphia morphia = new Morphia();
		morphia.map(ControlledVocabularyImpl.class).map(EntityImpl.class);

		this.datastore = morphia.createDatastore(mongoClient, databaseName);
		this.datastore.ensureIndexes();
		LOG.info("VocabularyMongoServer datastore is created");
	}

	/**
	 * @see eu.europeana.corelib.dereference.VocabularyMongoServer#getDatastore()
	 */
	@Override
	public Datastore getDatastore() {
		return this.datastore;
	}

	/**
	 * @see eu.europeana.corelib.dereference.VocabularyMongoServer#close()
	 */
	@Override
	public void close() {
		LOG.info("Closing MongoClient for VocabularyMongoServer");
		mongoClient.close();
	}

	/**
	 * @see eu.europeana.corelib.dereference.VocabularyMongoServer#getControlledVocabulary(java.lang.String, java.lang.String)
	 */
	@Override
	public ControlledVocabularyImpl getControlledVocabulary(String field, String filter) {
		String[] splitName = filter.split("/");
		String vocabularyName = splitName[0] + "/" + splitName[1] + "/"	+ splitName[2] + "/";
		List<ControlledVocabularyImpl> vocabularies = getDatastore()
				.find(ControlledVocabularyImpl.class)
				.filter(field, vocabularyName).asList();
		for (ControlledVocabularyImpl vocabulary : vocabularies) {
			boolean ruleController = true;
			for (String rule : vocabulary.getRules()) {
				ruleController = ruleController	&& (filter.contains(rule) || StringUtils.equals(rule, "*"));
			}
			if (ruleController) {
				return vocabulary;
			}
		}
		return null;
	}

	/**
	 * @see eu.europeana.corelib.dereference.VocabularyMongoServer#getControlledVocabularyByUri(java.lang.String, java.lang.String)
	 */
	@Override
	public ControlledVocabularyImpl getControlledVocabularyByUri(String uri,
			String name) {
		List<ControlledVocabularyImpl> vocabularies = getDatastore()
				.find(ControlledVocabularyImpl.class)
				.asList();
		for (ControlledVocabularyImpl vocabulary : vocabularies) {
			if (StringUtils.equals(name, vocabulary.getName())) {
				return vocabulary;
			}
		}
		return null;
	}

	/**
	 * @see eu.europeana.corelib.dereference.VocabularyMongoServer#getControlledVocabularyByName(java.lang.String)
	 */
	@Override
	public ControlledVocabularyImpl getControlledVocabularyByName(String name) {
		return getDatastore().find(ControlledVocabularyImpl.class).filter("name", name).get();
	}
	
	
}
