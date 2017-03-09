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
package eu.europeana.corelib.storage;

import org.mongodb.morphia.Datastore;

/**
 * A basic interface for a connection to MongoDB storage
 * 
 * @author Yorgos.Mamakis@ kb.nl
 *
 */
public interface MongoServer {
	/**
	 * Basic information for MongoDB connection
	 *
	 * @return Information on MongoDB server configuration
	 */
	String toString();

	/**
	 * Return the datastore from a MongoDB Server
	 *
	 * @return The datastore from the MongoDB Server
	 */
	Datastore getDatastore();

	/**
	 * Close the connection to the MongoDB Server
	 */
	void close();
}
