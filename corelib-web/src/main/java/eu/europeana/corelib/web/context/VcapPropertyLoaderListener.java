package eu.europeana.corelib.web.context;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.cloudfoundry.VcapApplicationListener;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.web.context.support.StandardServletEnvironment;

/**
 * Bridge between the VCAP (a.k.a. Cloud Foundry) metadata (specifically, the VCAP_APPLICATION and
 * VCAP_SERVICES metadata) and the europeana.properties file (part of the war).This reads the
 * europeana.properties file (found on the classpath), and will inject and CF environment variables
 * to make them accessible to the application.
 * 
 * The following properties will be replaced, or added if they don't exist yet, using values from
 * the system-provided environment variables, if they are listed as user-provided variables.
 * 
 * <pre class="code">
 * postgres.*
 * mongodb.*
 * </pre>
 * 
 * In other words, adding 'mongo_service: europeana-object-db-blue-2' to the env section of the
 * manifest.yaml, will replace/add all mongodb.* values found in the europeana properties with the
 * CF system provided ones. To use the Postgres values, use 'postgres_service: default'
 * 
 * The following system-provided properties will _always_ be injected, if it exists in the system
 * environment:
 * 
 * <pre class="code">
 * redis.*
 * </pre>
 * 
 * And the following user-provided properties, if they exist, will also be added to the
 * europeana.properties:
 * 
 * <pre class="code">
 * api2_url
 * api2_canonical_url
 * portal_server
 * portal_server_canonical
 * </pre>
 * 
 * @author Yorgos, Bram
 * 
 */
public class VcapPropertyLoaderListener extends VcapApplicationListener {

  private final static String VCAP = "vcap.services.";
  private final static String USERNAME = ".credentials.username";
  private final static String HOSTS = ".credentials.hosts";
  private final static String PASSWORD = ".credentials.password";

  private final static String POSTGRESDB = "vcap.services.";
  private final static String CREDENTIALS_DB=".credentials.db";

  private final static String POSTGRESUSERNAME = "vcap.services." ;
  private final static String CREDENTIALS_USER =".credentials.user";
  private final static String POSTGRESPASSWORD = "vcap.services.";
  private final static String CREDENTIALS_PASSWORD =".credentials.password";
  private final static String POSTGRESHOST = "vcap.services.";
  private final static String CREDENTIALS_HOST=".credentials.host";
  private final static String MONGO_SERVICE = "mongo_service";
  private final static String MONGO_DBNAME = "mongodb.dbname";
  private final static String MONGO_DBNAME_VALUE = "europeana";

  private final static String REDISHOST = "vcap.services.redis.credentials.host";
  private final static String REDISPORT = "vcap.services.redis.credentials.port";
  private final static String REDISPASSWORD = "vcap.services.redis.credentials.password";

  /*
   * # no trailing slash api2.url=http://hostname.domain/api
   * api2.canonical.url=http://hostname.domain/api
   * 
   * portal.server = http://hostname.domain/portal portal.server.canonical=http://hostname.domain
   */
  private final static String API2URL = "api2_url";
  private final static String API2CANONICALURL = "api2_canonical_url";
  private final static String PORTALSERVER = "portal_server";
  private final static String PORTALCANONICALURL = "portal_server_canonical";
  private final static String POSTGRES = "postgres";
  private final static String SWIFT_AUTHENTICATION_URL="vcap.services.swift-sitemap.credentials.authentication_uri";
  private final static String SWIFT_AUTHENTICATION_AV_ZONE="vcap.services.swift-sitemap.credentials.availability_zone";
  private final static String SWIFT_AUTHENTICATION_TENANT_NAME="vcap.services.swift-sitemap.credentials.tenant_name";
  private final static String SWIFT_AUTHENTICATION_USER_NAME="vcap.services.swift-sitemap.credentials.user_name";
  private final static String SWIFT_AUTHENTICATION_PASSWORD="vcap.services.swift-sitemap.credentials.password";

  private static StandardServletEnvironment env = new StandardServletEnvironment();

  public VcapPropertyLoaderListener() {
    super();

    this.onApplicationEvent(new ApplicationEnvironmentPreparedEvent(new SpringApplication(),
        new String[0], env));
    ClassLoader c = getClass().getClassLoader();
    @SuppressWarnings("resource")
    URLClassLoader urlC = (URLClassLoader) c;
    URL[] urls = urlC.getURLs();
    String path = urls[0].getPath();
    Properties props = new Properties();

    File europeanaProperties = new File(path + "/europeana.properties");
    try {
      props.load(new FileInputStream(europeanaProperties));

      final String[] swiftProp = new String[] { "authUrl", "userName", "password", "containerName", "regionName", "tenantName"};



      // PostgreSQL db, username, password, host
      if (env.getProperty(POSTGRESHOST+env.getProperty(POSTGRES)+CREDENTIALS_HOST) != null) {
        props.setProperty("postgres.db", env.getProperty(POSTGRESDB+env.getProperty(POSTGRES)+CREDENTIALS_DB));
        props.setProperty("postgres.username", env.getProperty(POSTGRESUSERNAME+env.getProperty(POSTGRES)+CREDENTIALS_USER));
        props.setProperty("postgres.password", env.getProperty(POSTGRESPASSWORD+env.getProperty(POSTGRES)+CREDENTIALS_PASSWORD));
        props.setProperty("postgres.host", env.getProperty(POSTGRESHOST+env.getProperty(POSTGRES)+CREDENTIALS_HOST));
      }

      // MongoDB username, password, host, port
      // The actual Mongo db depends on the user-provided "mongo_service" value
      if (env.getSystemEnvironment().containsKey(MONGO_SERVICE)) {
        String mongoDb = env.getSystemEnvironment().get(MONGO_SERVICE).toString();
        String mongoUserName = VCAP + mongoDb + USERNAME;
        String mongoPassword = VCAP + mongoDb + PASSWORD;
        String mongoHosts = VCAP + mongoDb + HOSTS;

        props.setProperty("mongodb.username", env.getProperty(mongoUserName));
        props.setProperty("mongodb.password", env.getProperty(mongoPassword));
        props.setProperty("metainfo.mongodb.username", env.getProperty(mongoUserName));
        props.setProperty("metainfo.mongodb.password", env.getProperty(mongoPassword));

        org.apache.log4j.Logger.getLogger(this.getClass()).error(env.getProperty(mongoHosts));
        String[] hosts = env.getProperty(mongoHosts).replace('[', ' ').replace("]", " ").split(",");
        String mongoHost = "";
        String mongoPort = "";
        for (String host : hosts) {
          mongoHost = mongoHost + host.split(":")[0].trim() + ",";
          mongoPort = mongoPort + host.split(":")[1].trim() + ",";
        }
        props.setProperty("mongodb.host", mongoHost.substring(0, mongoHost.length() - 1));
        props.setProperty("mongodb.port", mongoPort.substring(0, mongoPort.length() - 1));
        props.setProperty("metainfo.mongodb.host", mongoHost.substring(0, mongoHost.length() - 1));
        props.setProperty("metainfo.mongodb.port", mongoPort.substring(0, mongoPort.length() - 1));

        props.setProperty(MONGO_DBNAME, MONGO_DBNAME_VALUE);
      }

      // Redis host, port, password
      if (env.getProperty(REDISHOST) != null) {
        props.setProperty("redis.host", env.getProperty(REDISHOST));
        props.setProperty("redis.port", env.getProperty(REDISPORT));
        props.setProperty("redis.password", env.getProperty(REDISPASSWORD));
      }

      if (env.getProperty(SWIFT_AUTHENTICATION_URL) != null) {
        props.setProperty("swift.authUrl", env.getProperty(SWIFT_AUTHENTICATION_URL));
        props.setProperty("swift.password", env.getProperty(SWIFT_AUTHENTICATION_PASSWORD));
        props.setProperty("swift.username", env.getProperty(SWIFT_AUTHENTICATION_USER_NAME));
        props.setProperty("swift.regionName", env.getProperty(SWIFT_AUTHENTICATION_AV_ZONE));
        props.setProperty("swift.tenantName", env.getProperty(SWIFT_AUTHENTICATION_TENANT_NAME));
        props.setProperty("swift.containerName", "sitemap");
      }
      // API and Portal canonical URLs, server
      setHTTPProperty(props, API2URL);
      setHTTPProperty(props, API2CANONICALURL);
      setHTTPProperty(props, PORTALCANONICALURL);
      setHTTPProperty(props, PORTALSERVER);

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

    } catch (IOException e1) {
      Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e1.getMessage(), e1.getCause());
    }

  }

  /**
   * Checks if a user-defined variable exists, and adds it to the properties file if it does
   * 
   * @throws IOException
   */
  private void setHTTPProperty(Properties props, String key) throws IOException {
    String HTTP = "http://";

    if (env.containsProperty(key)) {
      props.setProperty(StringUtils.replaceChars(key, "_", "."), HTTP
          + env.getSystemEnvironment().get(key));
    }
  }
}
