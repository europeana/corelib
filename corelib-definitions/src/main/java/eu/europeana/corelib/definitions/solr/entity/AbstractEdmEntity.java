package eu.europeana.corelib.definitions.solr.entity;

import org.bson.types.ObjectId;

public interface AbstractEdmEntity {
	
	ObjectId getId();

	void setId(ObjectId id);
}
