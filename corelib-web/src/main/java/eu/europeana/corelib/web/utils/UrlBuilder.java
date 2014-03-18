/*
 * Copyright 2007-2012 The Europeana Foundation
 *
 *  Licenced under the EUPL, Version 1.1 (the "Licence") and subsequent versions as approved 
 *  by the European Commission;
 *  You may not use this work except in compliance with the Licence.
 *  
 *  You may obtain a copy of the Licence at:
 *  http://joinup.ec.europa.eu/software/page/eupl
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under 
 *  the Licence is distributed on an "AS IS" basis, without warranties or conditions of 
 *  any kind, either express or implied.
 *  See the Licence for the specific language governing permissions and limitations under 
 *  the Licence.
 */

package eu.europeana.corelib.web.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import eu.europeana.corelib.logging.Logger;
import eu.europeana.corelib.utils.StringArrayUtils;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public class UrlBuilder {

	private static Logger log = Logger.getLogger(UrlBuilder.class.getCanonicalName());

	public static final String PATH_SEPERATOR = "/";

	private String protocol = "http";
	private StringBuilder baseUrl;

	private boolean relativeUrl = false;
	private boolean trailingSlash = false;

	private Map<String, String> params = new LinkedHashMap<String, String>();
	private Map<String, List<String>> multiParams = new LinkedHashMap<String, List<String>>();

	public UrlBuilder(String url) {
		setBaseUrl(url);
		if (StringUtils.contains(baseUrl.toString(), '?')) {
			stripBaseUrl();
		}
	}

	private void setBaseUrl(String url) {
		url = StringUtils.stripEnd(url, "/?&");
		if (StringUtils.isBlank(url) || StringUtils.startsWith(url, PATH_SEPERATOR)) {
			relativeUrl = true;
		} else {
			if (StringUtils.contains(url,"://")) {
				protocol = StringUtils.substringBefore(url, "://");
				url = StringUtils.substringAfter(url, "://");
			}
		}
		if (StringUtils.containsNone(url, "?")) {
			url = StringUtils.replace(url, "//", PATH_SEPERATOR);
		}
		baseUrl = new StringBuilder(url); 
	}

	private void stripBaseUrl() {
		setBaseUrl(StringUtils.replace(baseUrl.toString(), "&amp;", "&"));
		String[] result = StringUtils.split(baseUrl.toString(), '?');
		String toProcess = null;
		if (result.length == 2) {
			setBaseUrl(result[0]);
			toProcess = result[1];
		} else {
			if (StringUtils.endsWith(baseUrl.toString(), "?")) {
				setBaseUrl(result[0]);
			} else {
				setBaseUrl("");
				toProcess = result[0];
			}
		}
		addParamsFromURL(toProcess);
	}

	public UrlBuilder disableProtocol() {
		relativeUrl = true;
		return this;
	}

	public UrlBuilder disableTrailingSlash() {
		trailingSlash = false;
		return this;
	}

	public UrlBuilder addPath(String... paths) {
		if (StringArrayUtils.isNotBlank(paths)) {
			for (String path: paths) {
				path = StringUtils.strip(path, "/?&");
				baseUrl.append(PATH_SEPERATOR).append(path);
			}
		}
		trailingSlash = true;
		return this;
	}

	public UrlBuilder addPage(String page) {
		if (StringUtils.isNotBlank(page)) {
			page = StringUtils.strip(page, "/?&");
			baseUrl.append(PATH_SEPERATOR).append(page);
		}
		trailingSlash = false;
		return this;
	}

	public UrlBuilder addParamsFromURL(String url, String... ignoreKeys) {
		if (StringUtils.isNotBlank(url)) {
			List<NameValuePair> queryParams = URLEncodedUtils.parse(url, Charset.forName("UTF-8"));
			if (queryParams == null) {
				return this;
			}
			for (NameValuePair param : queryParams) {
				if (StringArrayUtils.isBlank(ignoreKeys)
						|| !ArrayUtils.contains(ignoreKeys, param.getName())) {
					if (multiParams.containsKey(param.getName())
							|| params.containsKey(param.getName())) {
						addMultiParam(param.getName(), param.getValue());
					} else {
						addParam(param.getName(), param.getValue(), true);
					}
				}
			}
		}
		return this;
	}

	public UrlBuilder addParam(String key, String value) {
		return addParam(key, value, true);
	}

	public UrlBuilder addParam(String key, int value) {
		return addParam(key, String.valueOf(value), true);
	}

	public UrlBuilder addParam(String key, int value, boolean override) {
		return addParam(key, String.valueOf(value), override);
	}

	public UrlBuilder addParam(String key, String value, boolean override) {
		if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
			if (!params.containsKey(key)
					|| (params.containsKey(key) && override)) {
				params.put(key, value);
			}
		}
		return this;
	}

	public UrlBuilder addParam(String key, String[] values) {
		return addParam(key, values, true);
	}

	public UrlBuilder addParam(String key, String[] values, boolean override) {
		if (StringUtils.isNotBlank(key)) {
			if (override) {
				removeParam(key);
			}
			if ((values != null) && (values.length > 0)) {
				for (String value : values) {
					addMultiParam(key, value);
				}

			}
		}
		return this;
	}

	public boolean hasParam(String key) {
		return multiParams.containsKey(key) || params.containsKey(key);
	}

	public UrlBuilder removeParam(String key) {
		if (StringUtils.isNotBlank(key)) {
			if (multiParams.containsKey(key)) {
				multiParams.remove(key);
			}
			if (params.containsKey(key)) {
				params.remove(key);
			}
		}
		return this;
	}

	public UrlBuilder removeDefault(String key, String value) {
		if (StringUtils.isNotBlank(key)) {
			if (multiParams.containsKey(key)) {
				if (multiParams.get(key).contains(value)) {
					multiParams.get(key).remove(value);
				}
			}
			if (params.containsKey(key)) {
				if (StringUtils.equals(value, params.get(key))) {
					params.remove(key);
				}
			}
		}
		return this;
	}

	public UrlBuilder removeStartWith(String key, String value) {
		if (params.containsKey(key)) {
			if (StringUtils.startsWith(params.get(key), value)) {
				params.remove(key);
			}
		}
		if (multiParams.containsKey(key)) {
			List<String> toRemove = new ArrayList<String>();
			for (String string : multiParams.get(key)) {
				if (StringUtils.startsWith(string, value)) {
					toRemove.add(string);
				}
			}
			if (toRemove.size() > 0) {
				multiParams.get(key).removeAll(toRemove);
			}
			if (multiParams.get(key).size() == 0) {
				removeParam(key);
			}
		}
		return this;
	}

	public UrlBuilder addMultiParam(String key, String value) {
		if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
			List<String> list = null;
			if (multiParams.containsKey(key)) {
				list = multiParams.get(key);
			} else {
				list = new ArrayList<String>();
				multiParams.put(key, list);
				if (params.containsKey(key)) {
					// convert to Array...
					list.add(params.get(key));
					params.remove(key);
				}
			}
			if (!list.contains(value)) {
				list.add(value);
			}
		}
		return this;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (!relativeUrl) {
			sb.append(protocol).append("://");
		}
		sb.append(baseUrl.toString());
		if (trailingSlash) {
			sb.append(PATH_SEPERATOR);
		}
		if (params.size() + multiParams.size() > 0) {
			boolean first = true;
			sb.append("?");
			for (String key : params.keySet()) {
				if (!first) {
					sb.append("&");
				}
				sb.append(key).append("=").append(encode(params.get(key)));
				first = false;
			}
			for (String key : multiParams.keySet()) {
				for (String s : multiParams.get(key)) {
					if (!first) {
						sb.append("&");
					}
					sb.append(key).append("=").append(encode(s));
					first = false;
				}
			}
		}
		return sb.toString();
	}

	public static String encode(String value) {
		try {
			value = URLEncoder.encode(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.error("UnsupportedEncodingException: " + e.getLocalizedMessage());
		}
		return value;
	}
}
