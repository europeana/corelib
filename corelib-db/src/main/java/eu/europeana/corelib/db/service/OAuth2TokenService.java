package eu.europeana.corelib.db.service;

import java.util.List;

import eu.europeana.corelib.db.entity.nosql.AccessToken;
import eu.europeana.corelib.db.entity.nosql.RefreshToken;

/**
 * @deprecated January 2018 OAuth and MyEuropeana are no longer being used
 */
@Deprecated
public interface OAuth2TokenService {
	
	/**
	 * Store and return an access token
	 * @param token The token to store
	 * @return An access token
	 */
	AccessToken store(AccessToken token);

	/**
	 * Store and return an refresh token
	 * @param token The token to store
	 * @return A refresh token
	 */
	RefreshToken store(RefreshToken token);
	
	/**
	 * Find a refresh token by its identifier
	 * @param id The identifier to search on
	 * @return A refresh token
	 */
	RefreshToken findRefreshTokenByID(String id);

	/**
	 * Find an access token by its identifier
	 * @param id The identifier to search on
	 * @return An access token
	 */
	AccessToken findAccessTokenByID(String id);
	
	/**
	 * Remove an access token by its id
	 * @param id The identifier to search on
	 */
	void removeAccessToken(String id);

	/**
	 * Remove a refresh token by id
	 * @param id The id of the refresh token
	 */
	void removeRefreshToken(String id);
	
	/**
	 * Find the list of access tokens by username
	 * @param clientId The client id to search on
	 * @param userName The username to search on
	 * @return A list of access tokens
	 */
	List<AccessToken> findByClientIdAndUserName(String clientId, String userName);

	/**
	 * Find the list of access tokens associated to a client id
	 * @param clientId The client id to search on
	 * @return A list of access tokens
	 */
	List<AccessToken> findByClientId(String clientId);

	/**
	 * Remove an access token by a refresh token id
	 * @param refreshTokenId The refresh token id to search on
	 */
	void removeAccessTokenByRefreshTokenId(String refreshTokenId);

	/**
	 * Find access tokens by authentication id
	 * @param authId The authentication id to search on
	 * @return An access token
	 */
	AccessToken findAccessTokenByAuthenticationId(String authId);
	
	/**
	 * Clean all expired tokens
	 */
	void cleanExpiredTokens();

	/**
	 * Remove all tokens
	 */
	void removeAll();
	
}