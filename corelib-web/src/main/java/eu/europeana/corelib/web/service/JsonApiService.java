package eu.europeana.corelib.web.service;

import eu.europeana.corelib.web.model.ApiResult;

/**
 * @deprecated will be replaced by new translation services
 */
@Deprecated(since = "July 2021")
public interface JsonApiService {

	ApiResult getJsonResponse(String url);

}
