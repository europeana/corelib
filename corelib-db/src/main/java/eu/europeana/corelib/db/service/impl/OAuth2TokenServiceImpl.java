package eu.europeana.corelib.db.service.impl;

import java.util.List;

import javax.annotation.Resource;

import eu.europeana.corelib.db.entity.nosql.AccessToken;
import eu.europeana.corelib.db.entity.nosql.RefreshToken;
import eu.europeana.corelib.db.internal.service.OAuth2AccessTokenService;
import eu.europeana.corelib.db.internal.service.OAuth2RefreshTokenService;
import eu.europeana.corelib.db.service.OAuth2TokenService;

/**
 * Implementation of the {@link OAuth2TokenService}
 * @deprecated January 2018 OAuth and MyEuropeana are no longer being used
 */
@Deprecated
public class OAuth2TokenServiceImpl implements OAuth2TokenService {
	
	@Resource
	private OAuth2AccessTokenService accessTokenService;
	
	@Resource
	private OAuth2RefreshTokenService refreshTokenService;

	@Override
	public synchronized AccessToken store(AccessToken token) {
		return accessTokenService.store(token);
	}

	@Override
	public synchronized RefreshToken store(RefreshToken token) {
		return refreshTokenService.store(token);
	}

	@Override
	public RefreshToken findRefreshTokenByID(String id) {
		return refreshTokenService.findByID(id);
	}

	@Override
	public AccessToken findAccessTokenByID(String id) {
		return accessTokenService.findByID(id);
	}

	@Override
	public List<AccessToken> findByClientIdAndUserName(String clientId, String userName) {
		return accessTokenService.findByClientIdAndUsername(clientId, userName);
	}

	@Override
	public List<AccessToken> findByClientId(String clientId) {
		return accessTokenService.findByClientId(clientId);
	}
	
	@Override
	public synchronized void removeAccessToken(String id) {
		accessTokenService.remove(id);
	}
	
	@Override
	public synchronized void removeRefreshToken(String id) {
		refreshTokenService.remove(id);
	}
	
	@Override
	public synchronized void removeAccessTokenByRefreshTokenId(String refreshTokenId) {
		accessTokenService.removeByRefreshTokenId(refreshTokenId);
	}
	
	@Override
	public AccessToken findAccessTokenByAuthenticationId(String authId) {
		return accessTokenService.findByAuthenticationId(authId);
	}
	
	@Override
	public synchronized void cleanExpiredTokens() {
		accessTokenService.cleanExpiredTokens();
	}
	
	@Override
	public synchronized void removeAll() {
		for (AccessToken token: accessTokenService.findAll()) {
			accessTokenService.remove(token.getId());
		}
		for (RefreshToken token: refreshTokenService.findAll()) {
			refreshTokenService.remove(token.getId());
		}
	}

}
