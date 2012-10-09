package eu.europeana.corelib.tools.lookuptable;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Indexed;

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

	@Indexed(unique = false)
	private String originalId;
	
	private String europeanaId;
	
	@Indexed(unique = false)
	private String collectionId;

	private String xml;

	private LookupState lookupState;

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

	public void setCollectionId(String collectionId) {
		this.collectionId = collectionId;
	}

}
