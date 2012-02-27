package eu.europeana.corelib.solr.entity;

import org.bson.types.ObjectId;

import eu.europeana.corelib.definitions.solr.entity.AbstractEdmEntity;

public class AbstractEdmEntityImpl implements AbstractEdmEntity {

	String about;
	ObjectId id;
	@Override
	public ObjectId getId() {
		
		return this.id;
	}

	@Override
	public void setId(ObjectId id) {
		this.id=id;

	}

	@Override
	public String getAbout() {
		return this.about;
	}

	@Override
	public void setAbout(String about) {
		this.about = about;
	}

}
