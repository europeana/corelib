package eu.europeana.corelib.web.model;

import java.util.List;

public interface ApiResult {

	int getHttpStatusCode();

	String getContent();

	List<String> getRequestHeaders();

	List<String> getResponseHeaders();
}
