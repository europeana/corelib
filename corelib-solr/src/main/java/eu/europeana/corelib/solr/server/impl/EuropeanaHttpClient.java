package eu.europeana.corelib.solr.server.impl;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.lang.StringUtils;

public class EuropeanaHttpClient extends HttpClient {

	private String username;
	private String password;
	private String server;
	private int port;

	public EuropeanaHttpClient(String username, String password, String url) {
		this.username = username;
		this.password = password;
		this.server = getServer(url);
		this.port = getPort(url);
		this.getParams().setAuthenticationPreemptive(true);
		Credentials credentials = new UsernamePasswordCredentials(this.username, this.password);
		this.getState().setCredentials(new AuthScope(this.server, this.port, AuthScope.ANY_REALM), credentials);
	}

	private int getPort(String url) {
		String temp = StringUtils.remove(url, "http://");
		return StringUtils.isNotBlank(StringUtils.substringBetween(temp, ":",
				"/")) ? Integer.parseInt(StringUtils.substringBetween(temp,
				":", "/")) : 80;
	}

	private String getServer(String url) {
		return StringUtils.substringBetween(url, "htpp://", ":");
	}
}
