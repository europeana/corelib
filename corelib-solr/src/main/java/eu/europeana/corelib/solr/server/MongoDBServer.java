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

package eu.europeana.corelib.solr.server;

import com.google.code.morphia.Datastore;

import eu.europeana.corelib.definitions.solr.beans.FullBean;
import eu.europeana.corelib.definitions.solr.entity.AbstractEdmEntity;
/**
 * Basic MongoDB server implementation
 * @author Yorgos.Mamakis@ kb.nl
 *
 */
public interface MongoDBServer {
	
	/**
	 * A basic implementation of a MongoDB Server connection
	 * @param id The object id to retrieve from the database
	 * @return A document from MongoDB - case where the user selects to retrieve one specific object
	 */
	FullBean getFullBean(String id);
	
	/**
	 * Basic information for MongoDB connection
	 * @return Information on MongoDB server configuration
	 */
	String toString();

	Datastore getDatastore();

	AbstractEdmEntity searchByAbout(String about);
}
