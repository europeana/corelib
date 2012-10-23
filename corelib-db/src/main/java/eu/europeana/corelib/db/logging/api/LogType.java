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
package eu.europeana.corelib.db.logging.api;

import java.util.Date;

import eu.europeana.corelib.db.logging.api.enums.RecordType;

/**
 * Log entry information for a specific API call. It contains the record type
 * (enumerated) SEARCH or OBJECT call, the requested URI, when it was created
 * and the profile used to make the search
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
public interface LogType {

	/**
	 * The record id
	 * 
	 * @return
	 */
	String getId();

	void setId(String id);

	String getApiKey();
	
	void setApiKey(String apiKey);
	
	/**
	 * The type of API call (search or object retrieval)
	 * 
	 * @return
	 */
	RecordType getRecordType();

	void setRecordType(RecordType recordType);

	/**
	 * The requested uri of the call
	 * 
	 * @return
	 */
	String getRequestedUri();

	void setRequestedUri(String requestedUri);

	/**
	 * When the call was made
	 * 
	 * @return
	 */
	Date getTimestamp();

	void setTimestamp(Date date);

	/**
	 * What profile was used in retrieving the record (minimal, standard, rich,
	 * portal, facets, breadcrumb spelling)
	 * 
	 * @return
	 */
	String getProfile();

	void setProfile(String profile);
}
