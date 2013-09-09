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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import eu.europeana.corelib.utils.StringArrayUtils;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public class UrlBuilder {

	private String protocol = "http";
	private StringBuilder baseUrl;

	private Map<String, String> params = new HashMap<String, String>();
	private Map<String, List<String>> multiParams = new HashMap<String, List<String>>();

	public UrlBuilder(String url) {
		setBaseUrl(url);
		if (StringUtils.contains(baseUrl.toString(), '?')) {
			stripBaseUrl();
		}
	}
	
	private void setBaseUrl(String url) {
		url = StringUtils.stripEnd(url, "/?&");
		if (StringUtils.contains(url,"://")) {
			protocol = StringUtils.substringBefore(url, "://");
			url = StringUtils.substringAfter(url, "://");
		}
		if (StringUtils.containsNone(url, "?")) {
			url = StringUtils.replace(url, "//", "/");
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
	
	public void addPath(String... paths) {
		if (StringArrayUtils.isNotBlank(paths)) {
			for (String path: paths) {
				path = StringUtils.strip(path, "/?&");
				baseUrl.append("/").append(path);
			}
		}
	}
	
	public void addPage(String page) {
		if (StringUtils.isNotBlank(page)) {
			page = StringUtils.strip(page, "/?&");
			baseUrl.append("/").append(page);
		}
	}

	public void addParamsFromURL(String url, String... ignoreKeys) {
		if (StringUtils.isNotBlank(url)) {
			String[] parameters = StringUtils.split(url, "&");
			for (String string : parameters) {
				String[] param = StringUtils.split(string, "=");
				if (param.length == 2) {
					if (StringArrayUtils.isBlank(ignoreKeys)
							|| !ArrayUtils.contains(ignoreKeys, param[0])) {
						if (multiParams.containsKey(param[0])
								|| params.containsKey(param[0])) {
							addMultiParam(param[0], param[1]);
						} else {
							addParam(param[0], param[1], true);
						}
					}
				}
			}
		}
	}

	public void addParam(String key, String value, boolean override) {
		if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
			if (!params.containsKey(key)
					|| (params.containsKey(key) && override)) {
				params.put(key, value);
			}
		}
	}

	public void addParam(String key, String[] values, boolean override) {
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
	}

	public boolean hasParam(String key) {
		return multiParams.containsKey(key) || params.containsKey(key);
	}

	public void removeParam(String key) {
		if (StringUtils.isNotBlank(key)) {
			if (multiParams.containsKey(key)) {
				multiParams.remove(key);
			}
			if (params.containsKey(key)) {
				params.remove(key);
			}
		}
	}

	public void removeDefault(String key, String value) {
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
	}

	public void removeStartWith(String key, String value) {
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
	}

	public void addMultiParam(String key, String value) {
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
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(protocol);
		sb.append("://").append(baseUrl.toString());
		if (params.size() + multiParams.size() > 0) {
			boolean first = true;
			sb.append("?");
			for (String key : params.keySet()) {
				if (!first) {
					sb.append("&");
				}
				sb.append(key).append("=").append(params.get(key));
				first = false;
			}
			for (String key : multiParams.keySet()) {
				for (String s : multiParams.get(key)) {
					if (!first) {
						sb.append("&");
					}
					sb.append(key).append("=").append(s);
					first = false;
				}
			}
		}
		return sb.toString();
	}
}
