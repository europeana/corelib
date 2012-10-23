package eu.europeana.corelib.db.logging.api;

import java.util.Date;

import net.vz.mongodb.jackson.Id;
import net.vz.mongodb.jackson.MongoCollection;

import eu.europeana.corelib.db.logging.api.enums.RecordType;
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
