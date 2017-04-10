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
package eu.europeana.corelib.db.internal.service.impl;

import java.util.Date;
import java.util.List;

import org.mongodb.morphia.query.Query;

import eu.europeana.corelib.db.entity.nosql.AccessToken;
import eu.europeana.corelib.db.internal.service.OAuth2AccessTokenService;
import eu.europeana.corelib.db.service.abstracts.AbstractNoSqlServiceImpl;

/**
 * Implementation of the OAuth2TokenAccessService
 *
 */
public class OAuth2TokenAccessServiceImpl extends AbstractNoSqlServiceImpl<AccessToken, String> implements OAuth2AccessTokenService {
	
	@Override
	public List<AccessToken> findByClientId(String clientId) {
		Query<AccessToken> query = getDao().createQuery();
		query.field("clientId").equal(clientId);
		return query.asList();
	}

	@Override
	public List<AccessToken> findByClientIdAndUsername(String clientId, String userName) {
		Query<AccessToken> query = getDao().createQuery();
		query.field("clientId").equal(clientId);
		query.field("userName").equal(userName);
		return query.asList();
	}
	
	@Override
	public AccessToken findByAuthenticationId(String authId) {
		Query<AccessToken> query = getDao().createQuery();
		query.field("authenticationId").equal(authId);
		return query.get();
	}
	
	@Override
	public void removeByRefreshTokenId(String refreshTokenId) {
		Query<AccessToken> query = getDao().createQuery();
		query.field("refreshToken").equal(refreshTokenId);
		getDao().deleteByQuery(query);
	}
	
	@Override
	public void cleanExpiredTokens() {
		Query<AccessToken> query = getDao().createQuery();
		query.field("expires").lessThan(new Date());
		getDao().deleteByQuery(query);
	}
	
}
