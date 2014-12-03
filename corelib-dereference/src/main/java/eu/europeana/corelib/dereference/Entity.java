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
package eu.europeana.corelib.dereference;

import org.bson.types.ObjectId;

/**
 * Resource fetched from the web for the dereference library. Used for caching
 * @author Yorgos.Mamakis@ kb.nl
 *
 */
public interface Entity {

	/**
	 * Morphia ObjectId
	 * @return
	 */
	ObjectId getId();
	
	/**
	 * Morphia Id setter
	 * @param id
	 */
	void setId(ObjectId id);
	
	/**
	 * The URI of the remote resource
	 * @return
	 */
	String getUri();
	
	/**
	 * 
	 * @param uri
	 */
	void setUri(String uri);
	
	/**
	 * A string representation of the content of the remote resource 
	 * @return
	 */
	String getContent();
	/**
	 * 
	 * @param content
	 */
	void setContent(String content);
	
	/**
	 * A timestamp of the creation of the stored resource in the DB. Used to update the cached entities
	 * @return
	 */
	long getTimestamp();
	/**
	 * 
	 * @param timestamp
	 */
	void setTimestamp(long timestamp);
}
