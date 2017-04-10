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
package eu.europeana.corelib.db.entity.nosql;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;

import eu.europeana.corelib.db.entity.nosql.abstracts.NoSqlEntity;

/**
 * A refresh token for OAuth2
 *
 */
@Entity("OAuth2RefreshToken")
public class RefreshToken implements NoSqlEntity {

	@Id
	private String tokenId;

	@Indexed(unique=false)
	private String apiKey;

	private byte[] token;

	private byte[] authentication;
	
	public RefreshToken() {
	}
	
	public RefreshToken(String id) {
		this.tokenId = id;
	}
	
	/**
	 * GETTERS & SETTTERS
	 */

	public String getId() {
		return tokenId;
	}

	public byte[] getToken() {
		return token.clone();
	}

	public void setToken(byte[] token) {
		this.token = token.clone();
	}

	public byte[] getAuthentication() {
		return authentication.clone();
	}

	public void setAuthentication(byte[] authentication) {
		this.authentication = authentication.clone();
	}

	public String getApiKey() {
		return apiKey;
	}
	
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
}
