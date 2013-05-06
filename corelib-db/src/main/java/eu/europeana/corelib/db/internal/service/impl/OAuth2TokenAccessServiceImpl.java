package eu.europeana.corelib.db.internal.service.impl;

import java.util.Date;
import java.util.List;

import com.google.code.morphia.query.Query;

import eu.europeana.corelib.db.entity.nosql.AccessToken;
import eu.europeana.corelib.db.internal.service.OAuth2AccessTokenService;
import eu.europeana.corelib.db.service.abstracts.AbstractNoSqlServiceImpl;

public class OAuth2TokenAccessServiceImpl extends AbstractNoSqlServiceImpl<AccessToken, String> implements OAuth2AccessTokenService {
	
	@Override
	public List<AccessToken> findByClientId(String clientId) {
		Query<AccessToken> query = getDao().createQuery();
		query.field("clientId").equals(clientId);
		return query.asList();
	}

	@Override
	public List<AccessToken> findByUserName(String userName) {
		Query<AccessToken> query = getDao().createQuery();
		query.field("userName").equals(userName);
		return query.asList();
	}
	
	@Override
	public AccessToken findByAuthenticationId(String authId) {
		Query<AccessToken> query = getDao().createQuery();
		query.field("authenticationId").equals(authId);
		return query.get();
	}
	
	@Override
	public void removeByRefreshTokenId(String refreshTokenId) {
		Query<AccessToken> query = getDao().createQuery();
		query.field("refreshToken").equals(refreshTokenId);
		getDao().deleteByQuery(query);
	}
	
	@Override
	public void cleanExpiredTokens() {
		Query<AccessToken> query = getDao().createQuery();
		query.field("expires").lessThan(new Date());
		getDao().deleteByQuery(query);
	}
	
}
