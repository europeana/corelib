package eu.europeana.corelib.web.context;

import eu.europeana.corelib.utils.ConfigUtils;
import eu.europeana.corelib.web.socks.SocksProxy;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.cloud.CloudFoundryVcapEnvironmentPostProcessor;
import org.springframework.web.context.support.StandardServletEnvironment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Bridge between the VCAP (a.k.a. Cloud Foundry) metadata and the europeana.properties file (part of the war).This reads the
 * europeana.properties file (found on the classpath), and will inject CF environment variables to make them accessible
 * to the application.
 *
 * The following user-provided properties, if they exist, will be added to the europeana.properties:
 * <p>
 * <pre class="code">
 * api2_baseUrl
 * portal_baseUrl
 * </pre>
 *
 * Note that this class doesn't handle any databases since that will be done automatically by CF and Spring
 * auto-configuration (see also https://spring.io/blog/2011/11/04/using-cloud-foundry-services-with-spring-part-2-auto-reconfiguration)
 *
 * @author Yorgos, Bram, Patrick
 */
public class VcapPropertyLoader extends CloudFoundryVcapEnvironmentPostProcessor {

    private static final Logger LOG = LogManager.getLogger(VcapPropertyLoader.class.getName());

    // Note that for proper working the VCAP properties name should be the same as the key in the europeana.properties
    // file but with dots replaced by underscores
    private static final String VCAP_API2_BASEURL = "api2_baseUrl"; // matches api2.baseUrl in europeana.properties
    private static final String VCAP_GATEWAY_BASEURL = "apiGateway_baseUrl"; // matches apiGateway.baseUrl in europeana.properties
    private static final String VCAP_PORTAL_BASEURL = "portal_baseUrl"; // matches portal.baseUrl in europeana.properties
    private static final String VCAP_MANIFEST_BASEURL="iiifManifest_baseUrl"; // matches iiifManifest.baseUrl in europeana.properties

    private static final StandardServletEnvironment env = new StandardServletEnvironment();

    // Used to find route-specific baseUrl settings in env. Keys have the format route(number)_(baseUrl)
    private static final String ROUTE_BASEURL_PROP_REGEX=String.format("^route\\d+_(%s|%s|%s)", VCAP_API2_BASEURL, VCAP_GATEWAY_BASEURL, VCAP_PORTAL_BASEURL);

    public VcapPropertyLoader() {
        super();

        this.postProcessEnvironment(env, new SpringApplication());

        ClassLoader c = getClass().getClassLoader();
        @SuppressWarnings("resource")
        URLClassLoader urlC = (URLClassLoader) c;
        URL[] urls = urlC.getURLs();
        String path = urls[0].getPath();
        Properties props = new Properties();

        File europeanaProperties = new File(path + "/europeana.properties");
        try (FileInputStream fis = new FileInputStream(europeanaProperties)){
            props.load(fis);

            // Add default VCAP properties for API2, Gateway and Portal to the loaded properties
            setVcapUrlProperty(props, VCAP_API2_BASEURL);
            setVcapUrlProperty(props, VCAP_GATEWAY_BASEURL);
            setVcapUrlProperty(props, VCAP_PORTAL_BASEURL);
            setVcapUrlProperty(props, VCAP_MANIFEST_BASEURL);

            // BaseURLs can be overridden for specific routes.eg: route1_api2_baseUrl. Load these here
            List<String> routeBaseUrls = ConfigUtils.getMatchingKeys(env.getSystemEnvironment(), ROUTE_BASEURL_PROP_REGEX);
            routeBaseUrls.forEach(key -> setVcapUrlProperty(props, key));

            // We initialize socks proxy here, because it turned out to be difficult to initialize this at the appropriate
            // time elsewhere in the (api) code
            String host = props.getProperty("socks.host");
            Boolean enabled = Boolean.valueOf(props.getProperty("socks.enabled"));
            if (StringUtils.isEmpty(host)) {
                LOG.info("No socks proxy host configured");
            } else if (!enabled) {
                LOG.info("Socks proxy disabled");
            } else {
                LOG.info("Setting up proxy at " + host);
                initSocksProxyConfig(host,
                        props.getProperty("socks.port"),
                        props.getProperty("socks.user"),
                        props.getProperty("socks.password"));
            }

            rewritePropertiesToFile(props, europeanaProperties);
        } catch (IOException e) {
            LOG.error("Error reading or writing properties", e);
        }

    }

    /**
     * Checks if a user-defined VCAP property exists that defines an url, and if so adds it to the application properties
     * file (where all _ chars are replaced with . chars).
     * If the property value doesn't start with http then we add https://
     */
    private void setVcapUrlProperty(Properties props, String vcapKey) {
        String value = (String) env.getSystemEnvironment().get(vcapKey);
        if (StringUtils.isNotBlank(value)) {
            String propKey = StringUtils.replaceChars(vcapKey, "_", ".");
            if (!StringUtils.startsWithIgnoreCase(value, "http")) {
                value = "https://" + value;
            }
            props.setProperty(propKey, value);
            LOG.info("VCAP Url property {} with is added to application properties as {}. Value = {}", vcapKey, propKey, props.getProperty(propKey));
        }
    }

    /**
     * We rewrite properties to file so any changes made here can be read later by Spring
     */
    private void rewritePropertiesToFile(Properties props, File propsFile) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("#Generated by the VcapPropertyLoader\n");
        sb.append("#").append(new Date().toString()).append("\n");
        for (Object key : props.keySet()) {
            sb.append(key).append("=").append(props.getProperty(key.toString())).append("\n");
        }
        // Overwriting the original file
        FileUtils.writeStringToFile(propsFile, sb + "\n", false);
        LOG.debug("Properties: {}", sb.toString());
    }

    private void initSocksProxyConfig(String host, String port, String user, String password) {
        SocksProxy socksProxy = new SocksProxy(host, port, user, password);
        socksProxy.init();
    }

}
