package eu.europeana.corelib.db.internal.service;

import java.util.List;

import eu.europeana.corelib.db.entity.nosql.AccessToken;
import eu.europeana.corelib.db.service.abstracts.AbstractNoSqlService;

public interface OAuth2AccessTokenService extends AbstractNoSqlService<AccessToken, String> {

	List<AccessToken> findByClientId(String clientId);

	List<AccessToken> findByUserName(String userName);

	AccessToken findByAuthenticationId(String authId);

	void cleanExpiredTokens();

	void removeByRefreshTokenId(String refreshTokenId);

}
