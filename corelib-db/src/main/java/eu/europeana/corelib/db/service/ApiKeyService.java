package eu.europeana.corelib.db.service;

import java.util.List;

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
	boolean checkReachedLimit(ApiKey apiKey);
	
}
