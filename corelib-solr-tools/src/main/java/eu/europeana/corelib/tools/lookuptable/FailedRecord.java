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

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
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

	@Id
	private ObjectId id;
	
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

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

}
