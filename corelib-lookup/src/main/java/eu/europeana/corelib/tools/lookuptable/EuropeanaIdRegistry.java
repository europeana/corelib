package eu.europeana.corelib.tools.lookuptable;

import java.util.Date;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;

/**
 * Mongo Entity for the representation of the lookup table of the EuropeanaIDs
 * @author yorgos.mamakis@ kb.nl
 * @author Georgios Markakis <gwarkx@hotmail.com>
 * 
 * @since 3 Oct 2012
 */

@Entity ("EuropeanaIdRegistry")
public class EuropeanaIdRegistry {
	
	@Id
	private ObjectId id;
	
	/**
	 * EuropeanaID: The EuropeanaID of the record
	 */
	@Indexed (unique=true)
	private String eid;

	/**
	 * Old CollectionID: The initial collection ID of the record
	 */
	@Indexed (unique=false)
	private String cid;

	/**
	 * Non stripped record ID: The unique record ID as given by the provider
	 */
	@Indexed (unique=false)
	private String orid;

	/**
	 * XML: The checksum of the record's content
	 */
	@Indexed (unique=false)
	private String xmlchecksum;
	
	/**
	 * Last Checked: when the record was last updated
	 */
	private Date last_checked;
	
	/**
	 * The UIM sessionID
	 */
	@Indexed (unique=false)
	private String sessionID; 
	
	/**
	 * Whether the id corresponds to a record that has been deleted
	 */
	private boolean deleted;
	
	


	/**
	 * Get the record ID
	 * @return the record ID
	 */
	public ObjectId getId() {
		return id;
	}
	
	/**
	 * Set the recordID
	 * @param id The id to set
	 */
	public void setId(ObjectId id) {
		this.id = id;
	}
	
	
	/**
	 * @return the eid
	 */
	public String getEid() {
		return eid;
	}

	/**
	 * @param eid the eid to set
	 */
	public void setEid(String eid) {
		this.eid = eid;
	}

	/**
	 * @return the cid
	 */
	public String getCid() {
		return cid;
	}

	/**
	 * @return the orid
	 */
	public String getOrid() {
		return orid;
	}

	/**
	 * @return the xmlchecksum
	 */
	public String getXmlchecksum() {
		return xmlchecksum;
	}

	/**
	 * @return the last_checked
	 */
	public Date getLast_checked() {
		return last_checked;
	}

	/**
	 * @return the sessionID
	 */
	public String getSessionID() {
		return sessionID;
	}

	/**
	 * @param cid the cid to set
	 */
	public void setCid(String cid) {
		this.cid = cid;
	}

	/**
	 * @param orid the orid to set
	 */
	public void setOrid(String orid) {
		this.orid = orid;
	}

	/**
	 * @param xmlchecksum the xmlchecksum to set
	 */
	public void setXmlchecksum(String xmlchecksum) {
		this.xmlchecksum = xmlchecksum;
	}

	/**
	 * @param last_checked the last_checked to set
	 */
	public void setLast_checked(Date last_checked) {
		this.last_checked = last_checked;
	}

	/**
	 * @param sessionID the sessionID to set
	 */
	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}
	
	/**
	 * Is the id's record deleted
	 * @return
	 */
	public boolean isDeleted() {
		return deleted;
	}

	/**
	 * Set the record of the id as deleted
	 * 
	 * @param deleted
	 */
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
}
