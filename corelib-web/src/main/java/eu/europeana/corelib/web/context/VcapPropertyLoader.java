package eu.europeana.corelib.web.context;

import eu.europeana.corelib.web.socks.SocksProxy;
import org.apache.commons.lang.StringUtils;
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
    private static final String VCAP_PORTAL_BASEURL = "portal_baseUrl"; // matches portal.baseUrl in europeana.properties

    private static StandardServletEnvironment env = new StandardServletEnvironment();

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

            // Add VCAP properties for API2 and Portal to the loaded properties
            setVcapUrlProperty(props, VCAP_API2_BASEURL);
            setVcapUrlProperty(props, VCAP_PORTAL_BASEURL);

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

        } catch (IOException e) {
            LOG.error("Error reading properties", e);
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

    private void initSocksProxyConfig(String host, String port, String user, String password) {
        SocksProxy socksProxy = new SocksProxy(host, port, user, password);
        socksProxy.init();
    }

}
