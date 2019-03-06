package eu.europeana.corelib.web.context;

import eu.europeana.corelib.web.socks.SocksProxy;
import org.apache.commons.io.FileUtils;
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
import java.util.*;

/**
 * Bridge between the VCAP (a.k.a. Cloud Foundry) metadata (specifically, the VCAP_APPLICATION and
 * VCAP_SERVICES metadata) and the europeana.properties file (part of the war).This reads the
 * europeana.properties file (found on the classpath), and will inject and CF environment variables
 * to make them accessible to the application.
 * <p>
 * The following properties will be replaced, or added if they don't exist yet, using values from
 * the system-provided environment variables, if they are listed as user-provided variables.
 * <p>
 * <pre class="code">
 * postgres.*
 * </pre>
 * <p>
 * In other words, adding 'postgres_service: europeana-object-db-blue-2' to the env section of the
 * manifest.yaml, will replace/add all postgres.* values found in the europeana properties with the
 * CF system provided ones.
 * <p>
 * The following user-provided properties, if they exist, will also be added to the
 * europeana.properties:
 * <p>
 * <pre class="code">
 * api2_baseUrl
 * portal_baseUrl
 * </pre>
 *
 * @author Yorgos, Bram, Patrick
 */
public class VcapPropertyLoader extends CloudFoundryVcapEnvironmentPostProcessor {

    private static final Logger LOG = LogManager.getLogger(VcapPropertyLoader.class.getName());

    private static final String VCAP = "vcap.services.";
    private static final String USERNAME = ".credentials.username";
    private static final String HOSTS = ".credentials.hosts";
    private static final String PASSWORD = ".credentials.password";

    private static final String POSTGRESDB = "vcap.services.";
    private static final String CREDENTIALS_DB = ".credentials.db";

    private static final String POSTGRESUSERNAME = "vcap.services.";
    private static final String CREDENTIALS_USER = ".credentials.user";
    private static final String POSTGRESPASSWORD = "vcap.services.";
    private static final String CREDENTIALS_PASSWORD = ".credentials.password";
    private static final String POSTGRESHOST = "vcap.services.";
    private static final String CREDENTIALS_HOST = ".credentials.host";

//    private static final String MONGO_SERVICE = "mongo_service";
//    private static final String MONGO_DBNAME = "mongodb.dbname";
//    private static final String MONGO_DBNAME_VALUE = "europeana";
//
//    private static final String REDISHOST = "vcap.services.redis.credentials.host";
//    private static final String REDISPORT = "vcap.services.redis.credentials.port";
//    private static final String REDISPASSWORD = "vcap.services.redis.credentials.password";

    // Note that for proper working the VCAP properties name should be the same as the key in the europeana.properties
    // file but with dots replaced by underscores
    private static final String VCAP_API2_BASEURL = "api2_baseUrl"; // matches api2.baseUrl in europeana.properties
    private static final String VCAP_PORTAL_BASEURL = "portal_baseUrl"; // matches portal.baseUrl in europeana.properties

    private static final String POSTGRES = "postgres";

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

            // PostgreSQL db, username, password, host
//            if (env.getProperty(POSTGRESHOST + env.getProperty(POSTGRES) + CREDENTIALS_HOST) != null) {
//                props.setProperty("postgres.db", env.getProperty(POSTGRESDB + env.getProperty(POSTGRES) + CREDENTIALS_DB));
//                props.setProperty("postgres.username", env.getProperty(POSTGRESUSERNAME + env.getProperty(POSTGRES) + CREDENTIALS_USER));
//                props.setProperty("postgres.password", env.getProperty(POSTGRESPASSWORD + env.getProperty(POSTGRES) + CREDENTIALS_PASSWORD));
//                props.setProperty("postgres.host", env.getProperty(POSTGRESHOST + env.getProperty(POSTGRES) + CREDENTIALS_HOST));
//            }

            // MongoDB username, password, host, port
            // The actual Mongo db depends on the user-provided "mongo_service" value
//            if (env.getSystemEnvironment().containsKey(MONGO_SERVICE)) {
//                String mongoDb = env.getSystemEnvironment().get(MONGO_SERVICE).toString();
//                String mongoUserName = VCAP + mongoDb + USERNAME;
//                String mongoPassword = VCAP + mongoDb + PASSWORD;
//                String mongoHosts = VCAP + mongoDb + HOSTS;
//
//                props.setProperty("mongodb.username", env.getProperty(mongoUserName));
//                props.setProperty("mongodb.password", env.getProperty(mongoPassword));
//                props.setProperty("metainfo.mongodb.username", env.getProperty(mongoUserName));
//                props.setProperty("metainfo.mongodb.password", env.getProperty(mongoPassword));
//
//                String[] hosts = env.getProperty(mongoHosts).replace('[', ' ').replace("]", " ").split(",");
//                String mongoHost = "";
//                String mongoPort = "";
//                for (String host : hosts) {
//                    mongoHost = mongoHost + host.split(":")[0].trim() + ",";
//                    mongoPort = mongoPort + host.split(":")[1].trim() + ",";
//                }
//                props.setProperty("mongodb.host", mongoHost.substring(0, mongoHost.length() - 1));
//                props.setProperty("mongodb.port", mongoPort.substring(0, mongoPort.length() - 1));
//                props.setProperty("metainfo.mongodb.host", mongoHost.substring(0, mongoHost.length() - 1));
//                props.setProperty("metainfo.mongodb.port", mongoPort.substring(0, mongoPort.length() - 1));
//
//                props.setProperty(MONGO_DBNAME, MONGO_DBNAME_VALUE);
//            }
//
//            // Redis host, port, password
//            if (env.getProperty(REDISHOST) != null) {
//                props.setProperty("redis.host", env.getProperty(REDISHOST));
//                props.setProperty("redis.port", env.getProperty(REDISPORT));
//                props.setProperty("redis.password", env.getProperty(REDISPASSWORD));
//            }

            // Add VCAP properties for API2 and Portal to the environment
            setVcapUrlProperty(props, VCAP_API2_BASEURL);
            setVcapUrlProperty(props, VCAP_PORTAL_BASEURL);

            // Write the Properties into the europeana.properties
            // Using the built-in store() method escapes certain characters (e.g. '=' and ':'), which is
            // not what we want to do (it breaks reading the properties elsewhere)
            // While we're writing the properties manually, might as well sort the list alphabetically...
            List<String> sortedKeys = new ArrayList<>();
            for (Object key : props.keySet()) {
                sortedKeys.add(key.toString());
            }
            Collections.sort(sortedKeys);

            StringBuilder sb = new StringBuilder();
            sb.append("#Generated by the VCAPPropertyLoaderListener\n");
            sb.append("#").append(new Date().toString()).append("\n");
            for (String key : sortedKeys) {
                sb.append(key).append("=").append(props.getProperty(key)).append("\n");
            }
            // Overwriting the original file
            FileUtils.writeStringToFile(europeanaProperties, sb + "\n", false);
            LOG.info("Properties: {}", sb.toString());

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
            LOG.info("VCAP Url property {} with is added to application properties as {}. Value = {}", vcapKey, propKey, value);
            props.setProperty(propKey, value);
        }
    }

    private void initSocksProxyConfig(String host, String port, String user, String password) {
        SocksProxy socksProxy = new SocksProxy(host, port, user, password);
        socksProxy.init();
    }

}
