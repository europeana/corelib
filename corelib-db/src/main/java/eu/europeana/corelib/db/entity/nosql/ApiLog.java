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

package eu.europeana.corelib.db.entity.nosql;

import java.util.Date;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;

import eu.europeana.corelib.db.entity.enums.RecordType;
import eu.europeana.corelib.db.entity.nosql.abstracts.NoSqlEntity;

/**
 * Class representing API log entry
 *
 */
@Entity("logEntries")
public class ApiLog implements NoSqlEntity {

	@Id
	private String id;

	private RecordType recordType;

	private String requestedUri;

	private Date timestamp = new Date();

	private String profile;

	@Indexed(unique=false)
	private String apiKey;

	/**
	 * GETTERS & SETTTERS
	 */

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public RecordType getRecordType() {
		return recordType;
	}

	public void setRecordType(RecordType recordType) {
		this.recordType = recordType;
	}

	public String getRequestedUri() {
		return requestedUri;
	}

	public void setRequestedUri(String requestedUri) {
		this.requestedUri = requestedUri;
	}

	public Date getTimestamp() {
		return new Date(timestamp.getTime());
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = new Date(timestamp.getTime());
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
}
