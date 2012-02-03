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

package eu.europeana.corelib.db.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import eu.europeana.corelib.db.entity.abstracts.UserConnectedImpl;
import eu.europeana.corelib.definitions.db.DatabaseDefinition;
import eu.europeana.corelib.definitions.db.entity.Authentication;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
@Entity
@Table(name = DatabaseDefinition.TABLENAME_AUTHENTICATION)
public class AuthenticationImpl extends UserConnectedImpl<String> implements Authentication {
	private static final long serialVersionUID = -3859445775674333646L;

	@Id
	@Column
	private String apiKey;
	
	@Column
	private String authKey;
	
	@Column
	private Date expires;

	/**
	 * GETTERS & SETTTERS
	 */

	@Override
	public String getId() {
		return authKey;
	}
	
	@Override
	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}
	
	@Override
	public String getAuthKey() {
		return authKey;
	}
	
	@Override
	public void setExpires(Date expires) {
		this.expires = expires;
	}
	
	@Override
	public Date getExpires() {
		return expires;
	}

	@Override
	public String getApiKey() {
		return apiKey;
	}

	@Override
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	
}
