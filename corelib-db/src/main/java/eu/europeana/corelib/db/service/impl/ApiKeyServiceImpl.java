package eu.europeana.corelib.db.service.impl;

import java.util.List;

import javax.annotation.Resource;

import eu.europeana.corelib.db.entity.relational.ApiKeyImpl;
import eu.europeana.corelib.db.service.ApiKeyService;
import eu.europeana.corelib.db.service.ApiLogService;
import eu.europeana.corelib.db.service.abstracts.AbstractServiceImpl;
import eu.europeana.corelib.definitions.db.entity.relational.ApiKey;

public class ApiKeyServiceImpl extends AbstractServiceImpl<ApiKey> implements ApiKeyService {
	
	@Resource
	private ApiLogService apiLogService;

	@Override
	public List<ApiKey> findAllSortByDate(boolean asc) {
		String namedQuery = asc ? ApiKeyImpl.QUERY_SORT_BY_DATE_ASC : ApiKeyImpl.QUERY_SORT_BY_DATE_DESC;
		return getDao().findByNamedQuery(namedQuery);
	}

	@Override
	public List<ApiKey> findAllSortByDate(boolean asc, int offset, int limit) {
		String namedQuery = asc ? ApiKeyImpl.QUERY_SORT_BY_DATE_ASC : ApiKeyImpl.QUERY_SORT_BY_DATE_DESC;
		return getDao().findByNamedQueryLimited(namedQuery, offset, limit);
	}
	
	@Override
	public boolean checkReachedLimit(ApiKey apiKey) {
		if (apiKey != null) {
			return apiKey.getUsageLimit() <= apiLogService.countByApiKey(apiKey.getId());
		}
		return false;
	}

}