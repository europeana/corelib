package eu.europeana.corelib.definitions.edm.entity;

import org.bson.types.ObjectId;

/**
 * Basic interface from which every contextual entity is created
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
public interface AbstractEdmEntity {

	/**
	 * Returns the MongoDB Object ID
	 * 
	 * @return
	 */
	ObjectId getId();

	/**
	 * Set the MongoDB Object ID
	 * 
	 * @param id
	 */
	void setId(ObjectId id);

	/**
	 * Retrieves the rdf:about attribute from an EDM contextual class. This is
	 * an Indexed Unique key for every record except web resources
	 * 
	 * @return
	 */
	String getAbout();

	/**
	 * Set the rdf:about field for an EDM contextual entity. This is Indexed and
	 * Unique
	 * 
	 * @param about
	 */
	void setAbout(String about);

}
