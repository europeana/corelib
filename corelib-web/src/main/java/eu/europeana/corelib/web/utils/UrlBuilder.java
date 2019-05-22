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
import org.apache.commons.lang.math.NumberUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import eu.europeana.corelib.utils.StringArrayUtils;
import eu.europeana.corelib.web.exception.InvalidUrlException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public class UrlBuilder {

	private static final Logger LOG = LogManager.getLogger(UrlBuilder.class);

	public static final String PATH_SEPERATOR = "/";

	private String protocol = "https";
	private String domain = null;
	private int port = 80;
	private StringBuilder path;
	private String anchor = null;

	private boolean disableProtocol = false;
	private boolean disableDomain = false;
	private boolean trailingSlash = false;

	private Map<String, String> params = new LinkedHashMap<>();
	private Map<String, List<String>> multiParams = new LinkedHashMap<>();

	public UrlBuilder(String url) {
		init(StringUtils.replace(url, "&amp;", "&"));
	}

	private void init(String url) {
		url = StringUtils.stripEnd(url, "/?&");
		if (StringUtils.isBlank(url)
				|| StringUtils.startsWith(url, PATH_SEPERATOR)) {
			disableDomain = true;
		} else {
			if (StringUtils.contains(url, "://")) {
				protocol = StringUtils.substringBefore(url, "://");
				url = StringUtils.substringAfter(url, "://");
			}
			domain = StringUtils.substringBefore(url, PATH_SEPERATOR);
			if (!StringUtils.contains(domain, ":")) {
				url = StringUtils.substringAfter(url, domain);
			} else {
				port = NumberUtils.toInt(StringUtils
						.substringAfter(domain, ":"));
				domain = StringUtils.substringBefore(domain, ":");
				url = StringUtils.substringAfter(url, ":" + port);
			}
		}
		if (StringUtils.contains(url, "#")) {
			anchor = StringUtils.substringAfterLast(url, "#");
			url = StringUtils.substringBeforeLast(url, "#");
		}
		if (StringUtils.contains(url, '?')) {
			url = stripBaseUrl(url);
		}
		path = new StringBuilder(StringUtils.replace(url, "//", PATH_SEPERATOR));
	}

	private String stripBaseUrl(String url) {
		String[] result = StringUtils.split(url, '?');
		String stripped;
		String toProcess = null;
		if (result.length == 2) {
			stripped = result[0];
			toProcess = result[1];
		} else {
			if (StringUtils.endsWith(url, "?")) {
				stripped = result[0];
			} else {
				stripped = "";
				toProcess = result[0];
			}
		}
		addParamsFromURL(toProcess);
		return stripped;
	}

	public UrlBuilder disableProtocol() {
		disableProtocol = true;
		return this;
	}

	public UrlBuilder disableDomain() {
		disableDomain = true;
		return this;
	}

	public UrlBuilder disableTrailingSlash() {
		trailingSlash = false;
		return this;
	}

	public UrlBuilder setAnchor(String a) {
		anchor = a;
		return this;
	}

	public UrlBuilder addPath(String... paths) {
		if (StringArrayUtils.isNotBlank(paths)) {
			for (String p : paths) {
				p = StringUtils.strip(p, "/?&");
				path.append(PATH_SEPERATOR).append(p);
			}
		}
		trailingSlash = true;
		return this;
	}

	public UrlBuilder addPage(String page) {
		if (StringUtils.isNotBlank(page)) {
			page = StringUtils.strip(page, "/?&");
			if (StringUtils.isBlank(path.toString())) {
				path.append(page);
			} else {
				path.append(PATH_SEPERATOR).append(page);

			}
		}
		trailingSlash = false;
		return this;
	}

	public UrlBuilder addParamsFromURL(String url, String... ignoreKeys) {
		if (StringUtils.isNotBlank(url)) {
			List<NameValuePair> queryParams = URLEncodedUtils.parse(url,
					Charset.forName("UTF-8"));
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

	/**
	 * Adds a parameter, but only if the key and value are not blank. Subsequent calls for the same parameter key will
	 * overwrite the previous value
	 * @param key
	 * @param value
	 * @return
	 */
	public UrlBuilder addParam(String key, String value) {
		return addParam(key, value, true);
	}

	public UrlBuilder addParam(String key, int value) {
		return addParam(key, String.valueOf(value), true);
	}

	public UrlBuilder addParam(String key, int value, boolean override) {
		return addParam(key, String.valueOf(value), override);
	}

	/**
	 * Adds a parameter, but only if the key and value are not blank
	 * @param key
	 * @param value
	 * @param override if false then any existing parameter won't be changed
	 * @return
	 */
	public UrlBuilder addParam(String key, String value, boolean override) {
		if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
			if (!params.containsKey(key) || (params.containsKey(key) && override)) {
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
			if (multiParams.containsKey(key) && multiParams.get(key).contains(value)) {
				multiParams.get(key).remove(value);
			}
			if (params.containsKey(key) && StringUtils.equals(value, params.get(key))) {
				params.remove(key);
			}
		}
		return this;
	}

	public UrlBuilder removeStartWith(String key, String value) {
		if (params.containsKey(key) && StringUtils.startsWith(params.get(key), value)) {
			params.remove(key);
		}
		if (multiParams.containsKey(key)) {
			List<String> toRemove = new ArrayList<>();
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

	/**
	 * Sets a new protocol (e.g. http or ftp).
	 * @param newProtocol, string containing desired protocol (can be with or without trailing "://". Anything after the
	 *                     :// is ignored)
	 */
	public void setProtocol(String newProtocol) {
		if (StringUtils.isNotBlank(newProtocol)) {
			if (StringUtils.contains(newProtocol, "://")) {
				protocol = StringUtils.substringBefore(newProtocol, "://");
			} else {
				protocol = newProtocol;
			}
		}
	}

	/**
	 * Note that this only alters the urls FQDN and not the protocol!
	 * @param newDomain
	 */
	public void setDomain(String newDomain) {
		if (StringUtils.isNotBlank(newDomain)) {
			if (StringUtils.contains(newDomain, "://")) {
				newDomain = StringUtils.substringAfter(newDomain, "://");
			}
			domain = StringUtils.substringBefore(newDomain, PATH_SEPERATOR);
			disableDomain = false;
			disableProtocol = false;
		}
	}

	public UrlBuilder addMultiParam(String key, String value) {
		if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
			List<String> list;
			if (multiParams.containsKey(key)) {
				list = multiParams.get(key);
			} else {
				list = new ArrayList<>();
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

	private StringBuilder createCanonical() {
		StringBuilder sb = new StringBuilder();
		if (!disableDomain && StringUtils.isNotBlank(domain)) {
			if (!disableProtocol) {
				sb.append(protocol).append("://");
			}
			sb.append(domain);
			if (port != 80) {
				sb.append(":").append(port);
			}
		}
		if (path != null) {
			sb.append(path.toString());
		}
		if (trailingSlash) {
			sb.append(PATH_SEPERATOR);
		}
		return sb;
	}

	public String toCanonicalUrl() throws InvalidUrlException {
		if (disableDomain || disableProtocol || StringUtils.isBlank(domain)) {
			throw new InvalidUrlException();
		}
		return createCanonical().toString();
	}

	@Override
	public String toString() {
		StringBuilder sb = createCanonical();
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
		if (StringUtils.isNotBlank(anchor)) {
			sb.append("#").append(anchor);
		}
		return sb.toString();
	}

	private String encode(String value) {
		try {
			value = URLEncoder.encode(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			LOG.error("UnsupportedEncodingException: {}", e.getLocalizedMessage(), e);
		}
		return value;
	}
}
