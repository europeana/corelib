package eu.europeana.corelib.web.support;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;

// TODO all loading europeana.properties functionality should be moved to API2
public class Configuration {

	@Resource
	private Properties europeanaProperties;

	@Deprecated
	@Value("#{europeanaProperties['portal.server']}")
	private String portalServer;

	@Deprecated
	@Value("#{europeanaProperties['imageCacheUrl']}")
	private String imageCacheUrl;

	@Deprecated
	@Value("#{europeanaProperties['api2.url']}")
	private String api2url;

	@Value("#{europeanaProperties['api.search.rowLimit']}")
	private int apiRowLimit;

	// Google Field Trip channel attributes
	private Map<String, String> gftChannelAttributes;

	@Deprecated // is now hard-coded in EuropeanaStaticUrl class
	public String getPortalServer() {
		return portalServer;
	}

	@Deprecated // already moved to API2
	public String getApi2url() {
		return api2url;
	}

	@Deprecated // is now hard-coded in EuropeanaStaticUrl class
	public String getImageCacheUrl() {
		return imageCacheUrl;
	}

	// Google Field Trip attribute getter
	public Map<String, String> getGftChannelAttributes(String channel) {
		gftChannelAttributes = new HashMap<>();
		int i = 1;
		while (europeanaProperties.containsKey("gft.channel." + channel + "." + i)) {
			String[] parts = europeanaProperties.getProperty("gft.channel." + channel + "." + i).split("=", 2);
			gftChannelAttributes.put(parts[0].trim(), parts[1].trim());
			i++;
		}
		return gftChannelAttributes;
	}

	public int getApiRowLimit() {
		return apiRowLimit;
	}

}
