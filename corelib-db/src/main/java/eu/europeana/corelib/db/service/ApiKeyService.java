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
	 * checks if user does not reached API limit yet. 
	 * 
	 * @param apiKey The existing apikey.
	 * 
	 * @return true if limit is reached
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
	 * Creates an API Key
	 */
	ApiKey createApiKey(String token, String email, String apiKey,
			String privateKey, Long limit, String username, String company,
			String country, String firstName, String lastName, String website,
			String address, String phone, String fieldOfWork) throws DatabaseException;	
}
