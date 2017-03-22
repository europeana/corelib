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

import com.mongodb.*;
import eu.europeana.corelib.storage.impl.MongoProviderImpl;

import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.Datastore;
import org.apache.log4j.Logger;

/**
 * Let the search-api connect to a mongo database (to access the SugarCRMCache and apilog database)
 * This class uses a basic Morphia connection without any mappings or other specific settings
 */
public class ApiMongoConnector {

    private static final Logger LOG = Logger.getLogger(ApiMongoConnector.class);

    private MongoClient mongoClient;
    private String label;

    /**
     * Create a basic connection to do get/delete/save operations on the database
     * @param label    The label of the server to connect to
     * @param host     The host to connect to
     * @param port     The port to connect to
     * @param dbName   The database to connect to
     * @param username Username for connection
     * @param password Password for connection
     * @return datastore
     */
    public Datastore createDatastore(String label, String host, int port, String dbName, String username,
                                     String password) {
        LOG.info(String.format("Creating new MongoClient for '%s' mongo server: %s:%d/%s", label, host, port, dbName));
        Datastore datastore = null;
        Morphia connection = new Morphia();
        try {
            this.label = label;
            this.mongoClient = new MongoProviderImpl(host, String.valueOf(port), dbName, username, password).getMongo();
            datastore = connection.createDatastore(mongoClient, dbName);
            LOG.info(String.format("'%s' datastore is created", label));
        } catch (MongoException e) {
            LOG.error(e.getMessage(), e);
        }
        return datastore;
    }

    /**
     * Create a basic connection to do get/delete/save operations on the database
     * Any required login credentials should already be stored in the provided MongoClient object
     * @param label
     * @param mongoClient
     * @param dbName
     * @return datastore
     */
    public Datastore createDatastore(String label, MongoClient mongoClient, String dbName) {
        LOG.info(String.format("Creating new MongoClient for '%s' mongo server: %s", label, dbName));
        Datastore datastore = null;
        Morphia connection = new Morphia();
        try {
            this.label = label;
            this.mongoClient = mongoClient;
            datastore = connection.createDatastore(mongoClient, dbName);
            LOG.info(String.format("'%s' mongo datastore is created", label));
        } catch (MongoException e) {
            LOG.error(e.getMessage(), e);
        }
        return datastore;
    }

    /**
     * Close the connection to the database
     */
    public void close() {
        LOG.info(String.format("Closing MongoClient for '%s'", label));
        mongoClient.close();
    }
}
