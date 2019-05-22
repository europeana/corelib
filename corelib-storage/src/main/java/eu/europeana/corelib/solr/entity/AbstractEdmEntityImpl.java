package eu.europeana.corelib.solr.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.bson.types.ObjectId;

import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;

import eu.europeana.corelib.definitions.edm.entity.AbstractEdmEntity;

/**
 * @see AbstractEdmEntity
 * 
 * @author Yorgos.Mamakis@ kb.nl
 */
public class AbstractEdmEntityImpl implements AbstractEdmEntity {

	@Indexed(unique = true)
	protected String about;

	@Id
	protected ObjectId id = new ObjectId();

	@JsonIgnore
	@Override
	public ObjectId getId() {
		return this.id;
	}

	@Override
	public void setId(ObjectId id) {
		this.id = id;
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
