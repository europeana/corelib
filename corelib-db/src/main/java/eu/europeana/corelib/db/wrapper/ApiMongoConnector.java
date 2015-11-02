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
package eu.europeana.corelib.db.wrapper;

import java.net.UnknownHostException;

import com.mongodb.MongoClient;
import org.apache.commons.lang3.StringUtils;

import com.google.code.morphia.Morphia;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.google.code.morphia.Datastore;

import eu.europeana.corelib.logging.Logger;

/**
 * Api Mongo connector
 *
 */
public class ApiMongoConnector {

	private static Logger log = Logger.getLogger(ApiMongoConnector.class
			.getCanonicalName());

	/**
	 * Default constructor
	 */
	public ApiMongoConnector() {
	}

	/**
	 * Create a datastore for connection
	 * @param label The label of the server to connect to
	 * @param host The host to connect to
	 * @param port The port to connect to
	 * @param dbName The database to connect to
	 * @param username Username for connection
	 * @param password Password for connection
	 * @return
	 */
	public Datastore createDatastore(String label, String host, int port,
			String dbName, String username, String password) {
		Datastore datastore = null;
		Morphia connection = new Morphia();
		try {
			log.info(String.format("Connecting to '%s' mongo server: %s:%d/%s",
					label, host, port, dbName));
			Mongo mongo = new MongoClient(host, port);
			if (StringUtils.isNotEmpty(username)
					&& StringUtils.isNotEmpty(password)) {
				datastore = connection.createDatastore(mongo, dbName, username,
						password.toCharArray());
			} else {
				datastore = connection.createDatastore(mongo, dbName);
			}
			log.info(String.format(
					"Connection to '%s' mongo server was successful", label));
		} catch (UnknownHostException | MongoException e) {
			log.error(e.getMessage());
		}
		return datastore;
	}
	
	public Datastore createDatastore(String label, Mongo mongo,
			String dbName, String username, String password) {
		Datastore datastore = null;
		Morphia connection = new Morphia();
		try {
			log.info(String.format("Connecting to '%s' mongo server: %s",
					label, dbName));
			
			if (StringUtils.isNotEmpty(username)
					&& StringUtils.isNotEmpty(password)) {
				datastore = connection.createDatastore(mongo, dbName, username,
						password.toCharArray());
			} else {
				datastore = connection.createDatastore(mongo, dbName);
			}
			log.info(String.format(
					"Connection to '%s' mongo server was successful", label));
		} catch (MongoException e) {
			log.error(e.getMessage());
		}
		return datastore;
	}
}
