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
package eu.europeana.corelib.dereference.impl;

import org.bson.types.ObjectId;

import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;

import eu.europeana.corelib.dereference.Entity;

/**
 * @see Entity
 * @author Yorgos.Mamakis@ kb.nl
 *
 */
@org.mongodb.morphia.annotations.Entity("entity")
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
