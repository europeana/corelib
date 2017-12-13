package eu.europeana.corelib.web.service.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.IOUtils;

import eu.europeana.corelib.web.model.ApiResult;
import eu.europeana.corelib.web.model.ApiResultImpl;
import eu.europeana.corelib.web.service.JsonApiService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JsonApiServiceImpl implements JsonApiService {

	private static final String USER_AGENT = "Europeana API-bot/2.0 (Europeana; http://europeana.eu; api@europeana.eu)";

	private static final Logger LOG = LogManager.getLogger(JsonApiServiceImpl.class);

	private String lastUrl;

	@Override
	public ApiResult getJsonResponse(String url) {
		lastUrl = url;
		ApiResultImpl result = new ApiResultImpl();

		try {
			HttpClient client = new HttpClient();
			GetMethod method = new GetMethod(url);
			method.setRequestHeader("User-Agent", USER_AGENT);
			result.setHttpStatusCode(client.executeMethod(method));

			StringWriter writer = new StringWriter();
			IOUtils.copy(method.getResponseBodyAsStream(), writer, "UTF-8");
			result.setContent(writer.toString());

			Header[] headers;
			List<String> requestHeaders = new ArrayList<>();
			requestHeaders.add(String.format("%s %s %s", method.getName(), method.getPath(), method.getEffectiveVersion()));
			headers = method.getRequestHeaders();
			for (Header header : headers) {
				requestHeaders.add(String.format("%s: %s", header.getName(), header.getValue()));
			}
			result.setRequestHeaders(requestHeaders);

			List<String> responseHeaders = new ArrayList<>();
			responseHeaders.add(method.getStatusLine().toString());
			headers = method.getResponseHeaders();
			for (Header header : headers) {
				responseHeaders.add(String.format("%s: %s", header.getName(), header.getValue()));
			}
			result.setResponseHeaders(responseHeaders);

		} catch (IOException e) {
			LOG.error(e.getMessage(),e);
		}

		return result;
	}

	public String getLastUrl() {
		return lastUrl;
	}
}
