package eu.europeana.corelib.dereference;

import org.bson.types.ObjectId;

/**
 * Resource fetched from the web for the dereference library. Used for caching
 * @author Yorgos.Mamakis@ kb.nl
 *
 */
public interface Entity {

	/**
	 * Morphia ObjectId
	 * @return
	 */
	ObjectId getId();
	
	/**
	 * Morphia Id setter
	 * @param id
	 */
	void setId(ObjectId id);
	
	/**
	 * The URI of the remote resource
	 * @return
	 */
	String getUri();
	
	/**
	 * 
	 * @param uri
	 */
	void setUri(String uri);
	
	/**
	 * A string representation of the content of the remote resource 
	 * @return
	 */
	String getContent();
	/**
	 * 
	 * @param content
	 */
	void setContent(String content);
	
	/**
	 * A timestamp of the creation of the stored resource in the DB. Used to update the cached entities
	 * @return
	 */
	long getTimestamp();
	/**
	 * 
	 * @param timestamp
	 */
	void setTimestamp(long timestamp);
}
