package eu.europeana.corelib.db.service;

import java.util.List;

import eu.europeana.corelib.db.entity.nosql.AccessToken;
import eu.europeana.corelib.db.entity.nosql.RefreshToken;

public interface OAuth2TokenService {
	
	AccessToken store(AccessToken token);

	RefreshToken store(RefreshToken token);
	
	RefreshToken findRefreshTokenByID(String id);

	AccessToken findAccessTokenByID(String id);
	
	void removeAccessToken(String id);

	void removeRefreshToken(String id);
	
	List<AccessToken> findByUserName(String userName);

	List<AccessToken> findByClientId(String clientId);

	void removeAccessTokenByRefreshTokenId(String refreshTokenId);

	AccessToken findAccessTokenByAuthenticationId(String authId);
	
	void cleanExpiredTokens();
	
}