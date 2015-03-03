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
package eu.europeana.corelib.db.service;

import java.util.List;

import eu.europeana.corelib.db.exception.DatabaseException;
import eu.europeana.corelib.db.exception.LimitReachedException;
import eu.europeana.corelib.db.service.abstracts.AbstractService;
import eu.europeana.corelib.definitions.db.entity.relational.ApiKey;

public interface ApiKeyService extends AbstractService<ApiKey> {

	/**
	 * Find all API keys sorted by registration date
	 *
	 * @param asc Sort direction (true = ascending, false = descending)
	 * @return
	 */
	List<ApiKey> findAllSortByDate(boolean asc);

	
	/**
	 * Find limited number of API keys sorted by registration date
	 * 
	 * @param asc Sort direction (true = ascending, false = descending)
	 * @param offset The first item to retrieve
	 * @param limit The number of items to retrieve
	 * @return
	 */
	List<ApiKey> findAllSortByDate(boolean asc, int offset, int limit);
	
	/**
	 * Checks if user does not reached API limit yet. 
	 * 
	 * @param apiKey
	 *   The existing apikey.
	 * 
	 * @return long
	 *   Return the number of requests so far if limit is not reached
	 * 
	 * @throws DatabaseException if the API key is null
	 * @throws LimitReachedException if the limit is reached
	 */
	long checkReachedLimit(ApiKey apiKey) throws DatabaseException, LimitReachedException;

	/**
	 * Removes an ApiKey from database and User.
	 * 
	 * @param apiKeyId
	 *            The primary key of the API key to remove
	 * @throws DatabaseException
	 */
	void removeApiKey(Long userId, String apiKey) throws DatabaseException;

	/**
	 * Updates the application for the specified api key.
	 * 
	 * @param userId
	 * @param apiKeyId
	 *            The primary key of the API key to update
	 * @param applicationName
	 * 			Application name, null value is acceptable to clear the value. 
	 * @throws DatabaseException
	 */
	void updateApplicationName(Long userId, String apiKey, String applicationName) throws DatabaseException;

	/**
	 * Creates an API Key
	 */
	ApiKey createApiKey(String token, String email, String apiKey,
			String privateKey, Long limit, String appName, String username, String company,
			String country, String firstName, String lastName, String website,
			String address, String phone, String fieldOfWork) throws DatabaseException;	
}
