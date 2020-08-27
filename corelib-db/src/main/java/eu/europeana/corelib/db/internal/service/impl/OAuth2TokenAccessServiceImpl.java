//package eu.europeana.corelib.db.internal.service.impl;
//
//import java.util.Date;
//import java.util.List;
//
//import dev.morphia.query.Query;
//
//import eu.europeana.corelib.db.entity.nosql.AccessToken;
//import eu.europeana.corelib.db.internal.service.OAuth2AccessTokenService;
//import eu.europeana.corelib.db.service.abstracts.AbstractNoSqlServiceImpl;
//
///**
// * Implementation of the OAuth2TokenAccessService
// * @deprecated January 2018 OAuth and MyEuropeana are no longer being used
// */
//@Deprecated
//public class OAuth2TokenAccessServiceImpl extends AbstractNoSqlServiceImpl<AccessToken, String> implements OAuth2AccessTokenService {
//
//	@Override
//	public List<AccessToken> findByClientId(String clientId) {
//		Query<AccessToken> query = getDao().createQuery();
//		query.field("clientId").equal(clientId);
//		return query.asList();
//	}
//
//	@Override
//	public List<AccessToken> findByClientIdAndUsername(String clientId, String userName) {
//		Query<AccessToken> query = getDao().createQuery();
//		query.field("clientId").equal(clientId);
//		query.field("userName").equal(userName);
//		return query.asList();
//	}
//
//	@Override
//	public AccessToken findByAuthenticationId(String authId) {
//		Query<AccessToken> query = getDao().createQuery();
//		query.field("authenticationId").equal(authId);
//		return query.get();
//	}
//
//	@Override
//	public void removeByRefreshTokenId(String refreshTokenId) {
//		Query<AccessToken> query = getDao().createQuery();
//		query.field("refreshToken").equal(refreshTokenId);
//		getDao().deleteByQuery(query);
//	}
//
//	@Override
//	public void cleanExpiredTokens() {
//		Query<AccessToken> query = getDao().createQuery();
//		query.field("expires").lessThan(new Date());
//		getDao().deleteByQuery(query);
//	}
//
//}
