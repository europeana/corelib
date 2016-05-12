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
package eu.europeana.corelib.db.internal.service;

import java.util.List;

import eu.europeana.corelib.db.entity.nosql.AccessToken;
import eu.europeana.corelib.db.service.abstracts.AbstractNoSqlService;

/**
 * Interface for the OAuth2Access token service
 *
 */
public interface OAuth2AccessTokenService extends AbstractNoSqlService<AccessToken, String> {

	/**
	 * Find list of access tokens by client id
	 * @param clientId The client id to search on
	 * @return A list of access tokens associated with the client id
	 */
	List<AccessToken> findByClientId(String clientId);

	/**
	 * Find list of access tokens by clientid and username
	 * @param clientId The client id to search on
	 * @param userName The username to search on
	 * @return A list of access tokens associated with the username
	 */
	List<AccessToken> findByClientIdAndUsername(String clientId, String userName);

	/**
	 * Find list of access tokens by OAuth2 id
	 * @param authId The OAuth2 id to search on
	 * @return A list of access tokens associated with the OAuth2 id
	 */
	AccessToken findByAuthenticationId(String authId);

	/**
	 * Clean expired access tokens
	 */
	void cleanExpiredTokens();

	/**
	 * Clean access token by refresh token id
	 * @param refreshTokenId
	 */
	void removeByRefreshTokenId(String refreshTokenId);

}
