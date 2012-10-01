package eu.europeana.corelib.dereference.impl;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;

import eu.europeana.corelib.dereference.Entity;

@com.google.code.morphia.annotations.Entity("entity")
public class EntityImpl implements Entity {

	@Id
	private ObjectId id;
	@Indexed(unique=true)
	private String uri;
	private String content;
	private long timestamp;
	@Override
	public ObjectId getId() {
		return this.id;
	}

	/**
	 * @
	 */
	@Override
	public void setId(ObjectId id) {
		this.id = id;
	}

	@Override
	public String getUri() {
		return this.uri;
	}

	@Override
	public void setUri(String uri) {
		this.uri = uri;
	}

	@Override
	public String getContent() {
		return this.content;
	}

	@Override
	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public long getTimestamp() {
		return this.timestamp;
	}

	@Override
	public void setTimestamp(long timestamp) {
		this.timestamp=timestamp;
	}

}
