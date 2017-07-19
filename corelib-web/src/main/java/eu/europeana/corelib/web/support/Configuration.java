package eu.europeana.corelib.web.support;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;

public class Configuration {

	@Resource
	private Properties europeanaProperties;

	@Value("#{europeanaProperties['portal.server']}")
	private String portalServer;

	@Value("#{europeanaProperties['imageCacheUrl']}")
	private String imageCacheUrl;

	@Value("#{europeanaProperties['api2.url']}")
	private String api2url;

	@Value("#{europeanaProperties['api.rowLimit']}")
	private int apiRowLimit;

	//	TODO consider removing, is ONLY available in europeana-test.properties
	@Value("#{europeanaProperties['portal.bing.translate.key']}")
	private String bingTranslateId;

	// Google Field Trip channel attributes
	private Map<String, String> gftChannelAttributes;

	public String getPortalServer() {
		return portalServer;
	}

	public String getApi2url() {
		return api2url;
	}

	public String getImageCacheUrl() {
		return imageCacheUrl;
	}

	// Google Field Trip attribute getter
	public Map<String, String> getGftChannelAttributes(String channel) {
		gftChannelAttributes = new HashMap<String, String>();
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

	// TODO only ever called via test
	public String getBingTranslateId() {
		return bingTranslateId;
	}

}
