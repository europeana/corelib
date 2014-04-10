package eu.europeana.corelib.web.model;

import java.util.List;

public class ApiResultImpl implements ApiResult {

	private int httpStatusCode;
	private String content;
	private List<String> requestHeaders;
	private List<String> responseHeaders;

	public ApiResultImpl() {}

	public ApiResultImpl(int httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	@Override
	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(int httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	@Override
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public List<String> getRequestHeaders() {
		return requestHeaders;
	}

	public void setRequestHeaders(List<String> requestHeaders) {
		this.requestHeaders = requestHeaders;
	}

	@Override
	public List<String> getResponseHeaders() {
		return responseHeaders;
	}

	public void setResponseHeaders(List<String> responseHeaders) {
		this.responseHeaders = responseHeaders;
	}
}
