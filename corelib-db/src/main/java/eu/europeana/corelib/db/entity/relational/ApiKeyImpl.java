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

package eu.europeana.corelib.db.entity.relational;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import eu.europeana.corelib.db.entity.relational.abstracts.UserConnectedImpl;
import eu.europeana.corelib.definitions.db.entity.RelationalDatabase;
import eu.europeana.corelib.definitions.db.entity.relational.ApiKey;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
@Entity
@Table(name = RelationalDatabase.TABLENAME_APIKEY)
public class ApiKeyImpl extends UserConnectedImpl<String> implements RelationalDatabase, ApiKey {
	private static final long serialVersionUID = -1717717883751281497L;

	@Id
	@Column(length = FIELDSIZE_APIKEY, nullable=false)
	private String apiKey;

	@Column(length = FIELDSIZE_APIKEY, nullable=false)
	private String privateKey;

	@Column
	private long usageLimit;

	/**
	 * GETTERS & SETTTERS
	 */

	@Override
	public String getId() {
		return apiKey;
	}

	@Override
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	@Override
	public String getPrivateKey() {
		return privateKey;
	}

	@Override
	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	@Override
	public long getUsageLimit() {
		return usageLimit;
	}

	@Override
	public void setUsageLimit(long usageLimit) {
		this.usageLimit = usageLimit;
	}
}
