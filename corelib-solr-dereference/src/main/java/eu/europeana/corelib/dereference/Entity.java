package eu.europeana.corelib.dereference;

import org.bson.types.ObjectId;

public interface Entity {

	ObjectId getId();
	
	void setId(ObjectId id);
	
	String getUri();
	
	void setUri(String uri);
	
	String getContent();
	
	void setContent(String content);
	
	long getTimestamp();
	
	void setTimestamp(long timestamp);
}
