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
import eu.europeana.publication.common.IDocument;
import eu.europeana.publication.common.State;
/**
 * Mongo Entity for the representation of the lookup table of the EuropeanaIDs
 * @author yorgos.mamakis@ kb.nl
 *
 */
@Entity ("EuropeanaID")
public class EuropeanaId implements IDocument{
	
	@Id
	private ObjectId id;
	@Indexed (unique=true)
	private String oldId;
	@Indexed (unique=false)
	private String newId;
	private long timestamp;
	private long lastAccess; 
	
        @Indexed
        private State state;
	/**
	 * Get the record ID
	 * @return the record ID
	 */
	public ObjectId getObjectId() {
		return id;
	}
	
        @Override
        public String getId(){
            if (id!=null){
                return id.toString();
            }
            return null;
        }
	/**
	 * Set the recordID
	 * @param id The id to set
	 */
	public void setId(ObjectId id) {
		this.id = id;
	}
	
	/**
	 * Get the oldID field
	 * @return
	 */
	public String getOldId() {
		return oldId;
	}
	
	/**
	 * Set the oldID field
	 * @param oldId
	 */
	public void setOldId(String oldId) {
		this.oldId = oldId;
	}
	
	/**
	 * Get the newID field
	 * @return the newID Field
	 */
	public String getNewId() {
		return newId;
	}
	
	/**
	 * Get a long representing the date the record was imported
	 * @return The date the record was imported
	 */
	public long getTimestamp() {
		return timestamp;
	}
	
	/**
	 * Set the date the record was imported
	 * @param timestamp The date the record was imported
	 */
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	/**
	 * The date the records was last accessed
	 * @return
	 */
	public long getLastAccess() {
		return lastAccess;
	}
	
	/**
	 * Set the date the record was last accessed
	 * @param lastAccess
	 */
	public void setLastAccess(long lastAccess) {
		this.lastAccess = lastAccess;
	}
	
	/**
	 * Set the newId field
	 * @param newId
	 */
	public void setNewId(String newId) {
		this.newId = newId;
	}

    @Override
    public void setState(State state) {
        this.state = state;
    }

    @Override
    public State getState() {
       return this.state;
    }

    @Override
    public void setId(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
	
}
