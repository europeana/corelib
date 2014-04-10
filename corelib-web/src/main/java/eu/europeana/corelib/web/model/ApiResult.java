package eu.europeana.corelib.web.model;

import java.util.List;

public interface ApiResult {

	public int getHttpStatusCode();

	public String getContent();

	public List<String> getRequestHeaders();

	public List<String> getResponseHeaders();
}
