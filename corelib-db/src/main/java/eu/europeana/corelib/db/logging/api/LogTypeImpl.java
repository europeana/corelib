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

import net.vz.mongodb.jackson.Id;
import net.vz.mongodb.jackson.MongoCollection;
import eu.europeana.corelib.db.logging.api.enums.RecordType;

/**
 * @see LogType
 * @author Yorgos.Mamakis@ kb.nl
 *
 */
@MongoCollection(name="logEntries")
public class LogTypeImpl implements LogType {
	@Id
	String id;
	RecordType recordType;
	String requestedUri;
	Date timestamp;
	String profile;
	String apiKey;
	
	
	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public void setApiKey(String apiKey){
		this.apiKey=apiKey;
	}
	
	@Override
	public String getApiKey(){
		return this.apiKey;
	}
	@Override
	public RecordType getRecordType() {
		return this.recordType;
	}

	@Override
	public void setRecordType(RecordType recordType) {
		this.recordType=recordType;
	}

	@Override
	public String getRequestedUri() {
		return this.requestedUri;
	}

	@Override
	public void setRequestedUri(String requestedUri) {
		this.requestedUri = requestedUri;
	}

	@Override
	public Date getTimestamp() {
		return this.timestamp;
	}

	@Override
	public void setTimestamp(Date timestamp) {
		this.timestamp=timestamp;
	}

	@Override
	public String getProfile() {
		return this.profile;
	}

	@Override
	public void setProfile(String profile) {
		this.profile=profile;
	}

}
