package eu.europeana.corelib.db.service.impl;

import java.util.List;

import eu.europeana.corelib.db.entity.relational.ApiKeyImpl;
import eu.europeana.corelib.db.service.ApiKeyService;
import eu.europeana.corelib.db.service.abstracts.AbstractServiceImpl;
import eu.europeana.corelib.definitions.db.entity.relational.ApiKey;

public class ApiKeyServiceImpl extends AbstractServiceImpl<ApiKey> implements ApiKeyService {

	public List<ApiKey> findAllSorted() {
		return findAll("userid desc");
	}

	public List<ApiKey> findAllSorted(int offset, int limit) {
		return findAll("userid desc", offset, limit);
	}

	public List<ApiKey> findAllSortByDate(boolean asc) {
		String namedQuery = asc ? ApiKeyImpl.QUERY_SORT_BY_DATE_ASC : ApiKeyImpl.QUERY_SORT_BY_DATE_DESC;
		return getDao().findByNamedQuery(namedQuery);
	}

	public List<ApiKey> findAllSortByDate(boolean asc, int offset, int limit) {
		String namedQuery = asc ? ApiKeyImpl.QUERY_SORT_BY_DATE_ASC : ApiKeyImpl.QUERY_SORT_BY_DATE_DESC;
		return getDao().findByNamedQueryLimited(namedQuery, offset, limit);
	}

	public long getLimit(String apiKey){
		return 0l;
	}
}
