package eu.europeana.corelib.tools.lookuptable;

import java.util.Date;

import org.bson.types.ObjectId;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;

/**
 * 
 * Failed Ingested Record
 * Currently supporting duplicate and identical records
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
@Entity("FailedRecord")
public class FailedRecord {

	@Id
	private ObjectId id;
	
	@Indexed(unique = false)
	private String originalId;
	
	private String europeanaId;
	
	@Indexed(unique = false)
	private String collectionId;

	private String xml;

	private LookupState lookupState;

	private Date date;
	
	private String message;
	
	
	/**
	 * The record Identifier
	 * 
	 * @return
	 */
	public String getOriginalId() {
		return originalId;
	}

	public void setOriginalId(String originalId) {
		this.originalId = originalId;
	}

	/**
	 * The reason it failed
	 * 
	 * @return
	 */
	public LookupState getLookupState() {
		return lookupState;
	}

	public void setLookupState(LookupState lookupState) {
		this.lookupState = lookupState;
	}

	/**
	 * The XML representation of the record
	 * 
	 * @return
	 */
	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	/**
	 * The generated europeanaId of the record
	 * @return
	 */
	public String getEuropeanaId() {
		return europeanaId;
	}

	public void setEuropeanaId(String europeanaId) {
		this.europeanaId = europeanaId;
	}

	/**
	 * The collection Id
	 * @return
	 */
	public String getCollectionId() {
		return collectionId;
	}

	/**
	 * @param collectionId
	 */
	public void setCollectionId(String collectionId) {
		this.collectionId = collectionId;
	}

	/**
	 * @return
	 */
	public ObjectId getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(ObjectId id) {
		this.id = id;
	}

	/**
	 * Get the ingestion failure date
	 * 
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Set the ingestion failure date
	 * @param date
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
