/*
 * Copyright 2007 EDL FOUNDATION
 *
 * Licensed under the EUPL, Version 1.0 or? as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 * you may not use this work except in compliance with the
 * Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://ec.europa.eu/idabc/eupl
 *
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
 */
package eu.europeana.corelib.solr.mongodb;

import org.bson.types.ObjectId;

import com.google.code.morphia.Datastore;

import eu.europeana.corelib.definitions.solr.beans.FullBean;
/**
 * Basic MongoDB server implementation
 * @author yorgos.mamakis@kb.nl
 *
 */
public interface MongoDBServer {
	
	/**
	 * A basic implementation of a MongoDB Server connection
	 * @param id - The object id to retrieve from the database
	 * @return A document from MongoDB - case where the user selects to retrieve one specific object
	 */
	public FullBean getFullBean(ObjectId id);
	
	/**
	 * Basic information for MongoDB connection
	 * @return Information on MongoDB server configuration
	 */
	public String toString();

	public Datastore getDatastore();
}
