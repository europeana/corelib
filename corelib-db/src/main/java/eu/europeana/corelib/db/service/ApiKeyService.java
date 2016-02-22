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

import eu.europeana.corelib.db.exception.DatabaseException;
import eu.europeana.corelib.db.exception.LimitReachedException;
import eu.europeana.corelib.db.service.abstracts.AbstractService;
import eu.europeana.corelib.definitions.db.entity.relational.ApiKey;
import eu.europeana.corelib.web.exception.EmailServiceException;

import java.util.List;

public interface ApiKeyService extends AbstractService<ApiKey> {

    /**
     * Find all API keys by email address
     *
     * @param email email address to match
     * @return all matching api keys
     */
    List<ApiKey> findByEmail(String email);

    /**
     * Find all API keys sorted by registration date
     *
     * @param asc Sort direction (true = ascending, false = descending)
     */
    List<ApiKey> findAllSortByDate(boolean asc);


    /**
     * Find limited number of API keys sorted by registration date
     *
     * @param asc    Sort direction (true = ascending, false = descending)
     * @param offset The first item to retrieve
     * @param limit  The number of items to retrieve
     */
    List<ApiKey> findAllSortByDate(boolean asc, int offset, int limit);

    /**
     * Checks if user does not reached API limit yet.
     *
     * @param apiKey The existing apikey.
     * @return long
     * Return the number of requests so far if limit is not reached
     * @throws DatabaseException     if the API key is null
     * @throws LimitReachedException if the limit is reached
     */
    long checkReachedLimit(ApiKey apiKey) throws DatabaseException, LimitReachedException;

    /**
     * Removes an ApiKey from database and User.
     *
     * @param apiKey The primary key of the API key to remove
     * @throws DatabaseException
     */
    void removeApiKey(String apiKey) throws DatabaseException;

    /**
     * Updates the application for the specified api key.
     *
     * @param apiKey          The primary key of the API key to update
     * @param applicationName Application name, null value is acceptable to clear the value.
     * @throws DatabaseException
     */
    @Deprecated
    void updateApplicationName(String apiKey, String applicationName) throws DatabaseException;

    /**
     * Creates an API Key (Portal)
     */
    @Deprecated
    ApiKey createApiKey(String token, String email, String apiKey,
                        String privateKey, Long limit, String appName, String username, String company,
                        String country, String firstName, String lastName, String website,
                        String address, String phone, String fieldOfWork) throws DatabaseException;

    /**
     * Creates an API Key
     */
    ApiKey createApiKey(
            String email, Long limit, String appName, String company, String firstName,
            String lastName, String website, String description
    ) throws DatabaseException, EmailServiceException;

    /**
     * Updates an existing api key
     *
     * @throws DatabaseException When key doesn't exists or couldn't be updated with the new data.
     */
    ApiKey updateApiKey(
            String apiKey, String email, Long defaultUsageLimit, String application, String company,
            String firstName, String lastName, String website, String description
    ) throws DatabaseException;

    /**
     * Changes the api key usage limit
     */
    ApiKey changeLimit(String apiKey, long limit) throws DatabaseException;
}
