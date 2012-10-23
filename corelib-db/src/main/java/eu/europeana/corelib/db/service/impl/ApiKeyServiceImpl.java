package eu.europeana.corelib.db.service.impl;

import eu.europeana.corelib.db.service.ApiKeyService;
import eu.europeana.corelib.db.service.abstracts.AbstractServiceImpl;
import eu.europeana.corelib.definitions.db.entity.relational.ApiKey;

public class ApiKeyServiceImpl extends AbstractServiceImpl<ApiKey>  implements ApiKeyService {

	public long getLimit(String apiKey){
		return 0l;
	}
}
