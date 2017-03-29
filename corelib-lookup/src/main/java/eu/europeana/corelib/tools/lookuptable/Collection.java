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
package eu.europeana.corelib.tools.lookuptable;

import org.bson.types.ObjectId;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;

/**
 * Morphia Collection Entity representing the Lookup table entry between old and new collections
 * @author yorgos.mamakis@ kb.nl
 *
 */
@Entity("Collection")
public class Collection {
	
	@Id
	private ObjectId id;
	
	@Indexed
	private String newCollectionId;
	@Indexed
	private String oldCollectionId;

	/**
	 * Return the ObjectID of an entry in the Collection Lookup Table
	 * @return The object id of the lookup table
	 */
	public ObjectId getId() {
		return id;
	}

	/**
	 * Set the ObjectID of the entry in the Lookup Table
	 * @param id The ObjectID
	 */
	public void setId(ObjectId id) {
		this.id = id;
	}

	/**
	 * String representation of the new collectionID
	 * @return The new collectionID
	 */
	public String getNewCollectionId() {
		return newCollectionId;
	}

	/**
	 * Set the new collectionID
	 * @param newCollectionId The new collectionID
	 */
	public void setNewCollectionId(String newCollectionId) {
		this.newCollectionId = newCollectionId;
	}

	/**
	 * String representation of the old collectionID
	 * @return The old collectionID
	 */
	public String getOldCollectionId() {
		return oldCollectionId;
	}

	/**
	 * Set the old collectionID
	 * @param oldCollectionId The old collectionID
	 */
	public void setOldCollectionId(String oldCollectionId) {
		this.oldCollectionId = oldCollectionId;
	}
	
	
}
