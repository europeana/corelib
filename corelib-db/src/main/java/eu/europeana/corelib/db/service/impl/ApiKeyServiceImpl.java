package eu.europeana.corelib.db.service.impl;

import java.util.List;

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

	public long getLimit(String apiKey){
		return 0l;
	}
}
