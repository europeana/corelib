/*
 * Copyright 2007 EDL FOUNDATION
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they 
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "License");
 * you may not use this work except in compliance with the
 * License.
 * You may obtain a copy of the License at:
 *
 * http://ec.europa.eu/idabc/eupl
 *
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the License is
 * distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the License for the specific language governing
 * permissions and limitations under the License.
 */

package eu.europeana.corelib.db.entity;

import java.util.Date;

import eu.europeana.corelib.db.entity.abstracts.UserConnectedImpl;
import eu.europeana.corelib.definitions.db.entity.Authentication;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public class AuthenticationImpl extends UserConnectedImpl<String> implements Authentication {
	private static final long serialVersionUID = -3859445775674333646L;

	private String apiKey;
	
	private String authKey;
	
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
