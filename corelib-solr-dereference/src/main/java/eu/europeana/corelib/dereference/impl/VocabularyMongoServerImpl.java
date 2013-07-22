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
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.Mongo;

import eu.europeana.corelib.dereference.VocabularyMongoServer;
import eu.europeana.corelib.solr.MongoServer;

/**
 * A mongo server instance for use with the controlled vocabularies
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
public class VocabularyMongoServerImpl implements VocabularyMongoServer {

	private final Logger log = Logger.getLogger(getClass().getName());

	private Mongo mongoServer;
	private String databaseName;
	private Datastore datastore;

	/* (non-Javadoc)
	 * @see eu.europeana.corelib.dereference.impl.VocabularyMongoServer#getDatastore()
	 */
	@Override
	/**
	 * Retrieve the created datastore
	 */
	public Datastore getDatastore() {
		return this.datastore;
	}

	/* (non-Javadoc)
	 * @see eu.europeana.corelib.dereference.impl.VocabularyMongoServer#close()
	 */
	@Override
	public void close() {
		mongoServer.close();
	}

	public VocabularyMongoServerImpl(Mongo mongoServer, String databaseName) {
		log.info("VocabularyMongoServer is instantiated");
		this.mongoServer = mongoServer;
		this.databaseName = databaseName;
		createDatastore();
	}

	public VocabularyMongoServerImpl() {
		
	}

	/**
	 * Create a datastore and map the required morphia entities
	 */
	private void createDatastore() {
		Morphia morphia = new Morphia();
		morphia.map(ControlledVocabularyImpl.class).map(EntityImpl.class);

		datastore = morphia.createDatastore(mongoServer, databaseName);

		datastore.ensureIndexes();
		log.info("VocabularyMongoServer datastore is created");
	}

	/* (non-Javadoc)
	 * @see eu.europeana.corelib.dereference.impl.VocabularyMongoServer#getControlledVocabulary(java.lang.String, java.lang.String)
	 */
	@Override
	public ControlledVocabularyImpl getControlledVocabulary(String field,
			String filter) {
		String[] splitName = filter.split("/");
		String vocabularyName = splitName[0] + "/" + splitName[1] + "/"
				+ splitName[2] + "/";
		List<ControlledVocabularyImpl> vocabularies = getDatastore()
				.find(ControlledVocabularyImpl.class)
				.filter(field, vocabularyName).asList();
		for (ControlledVocabularyImpl vocabulary : vocabularies) {
			boolean ruleController = true;
			for (String rule : vocabulary.getRules()) {
				ruleController = ruleController
						&& (filter.contains(rule) || StringUtils.equals(rule,
								"*"));
			}
			if (ruleController) {
				return vocabulary;
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see eu.europeana.corelib.dereference.impl.VocabularyMongoServer#getControlledVocabularyByUri(java.lang.String, java.lang.String)
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
}
