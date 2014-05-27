package eu.europeana.corelib.web.service.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.IOUtils;

import eu.europeana.corelib.logging.Logger;
import eu.europeana.corelib.web.model.ApiResult;
import eu.europeana.corelib.web.model.ApiResultImpl;
import eu.europeana.corelib.web.service.JsonApiService;

public class JsonApiServiceImpl implements JsonApiService {

	private Logger log = Logger.getLogger(JsonApiServiceImpl.class.getCanonicalName());

	private String lastUrl;

	public JsonApiServiceImpl() {}

	public ApiResult getJsonResponse(String url) {
		lastUrl = url;
		ApiResultImpl result = new ApiResultImpl();

		try {
			HttpClient client = new HttpClient();
			GetMethod method = new GetMethod(url);
			method.setRequestHeader("User-Agent", "Europeana API-bot/2.0 (Europeana; http://europeana.eu; api@europeana.eu)");
			result.setHttpStatusCode(client.executeMethod(method));

			StringWriter writer = new StringWriter();
			IOUtils.copy(method.getResponseBodyAsStream(), writer, "UTF-8");
			result.setContent(writer.toString());

			Header[] headers;
			List<String> requestHeaders = new ArrayList<String>();
			requestHeaders.add(String.format("%s %s %s", method.getName(), method.getPath(), method.getEffectiveVersion()));
			headers = method.getRequestHeaders();
			for (Header header : headers) {
				requestHeaders.add(String.format("%s: %s", header.getName(), header.getValue()));
			}
			result.setRequestHeaders(requestHeaders);

			List<String> responseHeaders = new ArrayList<String>();
			responseHeaders.add(method.getStatusLine().toString());
			headers = method.getResponseHeaders();
			for (Header header : headers) {
				responseHeaders.add(String.format("%s: %s", header.getName(), header.getValue()));
			}
			result.setResponseHeaders(responseHeaders);

		} catch (IOException e) {
			log.error(e.getMessage(),e);
		}

		return result;
	}

	public String getLastUrl() {
		return lastUrl;
	}
}
