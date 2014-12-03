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

package eu.europeana.corelib.definitions.edm.entity;

import org.bson.types.ObjectId;

/**
 * Basic interface from which every contextual entity is created
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
public interface AbstractEdmEntity {

	/**
	 * Returns the MongoDB Object ID
	 * 
	 * @return
	 */
	ObjectId getId();

	/**
	 * Set the MongoDB Object ID
	 * 
	 * @param id
	 */
	void setId(ObjectId id);

	/**
	 * Retrieves the rdf:about attribute from an EDM contextual class. This is
	 * an Indexed Unique key for every record except web resources
	 * 
	 * @return
	 */
	String getAbout();

	/**
	 * Set the rdf:about field for an EDM contextual entity. This is Indexed and
	 * Unique
	 * 
	 * @param about
	 */
	void setAbout(String about);

}
