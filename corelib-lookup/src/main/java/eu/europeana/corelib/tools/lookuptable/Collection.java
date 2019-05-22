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
