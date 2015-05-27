package eu.europeana.corelib.web.context;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.cloudfoundry.VcapApplicationListener;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.web.context.support.StandardServletEnvironment;

public class VcapPropertyLoaderListener extends VcapApplicationListener {

	private final static String POSTGRESDB = "vcap.services.postgresql.credentials.db";
	private final static String POSTGRESUSERNAME = "vcap.services.postgresql.credentials.user";
	private final static String POSTGRESPASSWORD = "vcap.services.postgresql.credentials.password";
	private final static String POSTGRESHOST = "vcap.services.postgresql.credentials.host";
//	private final static String MONGOUSERNAME = "vcap.services.europeana-object-db.credentials.username";
//	private final static String MONGOPASSWORD = "vcap.services.europeana-object-db.credentials.password";
//	private final static String MONGOHOSTS = "vcap.services.europeana-object-db.credentials.hosts";
        private final static String VCAP = "vcap.services.";
        private final static String USERNAME=".username";
        private final static String HOSTS = ".hosts";
        private final static String PASSWORD = ".password";
        
	private final static String MONGOPORTS = "vcap.services.europeana-object-db.credentials.ports";
	private final static String REDISHOST = "vcap.services.redis.credentials.host";
    private final static String REDISPORT = "vcap.services.redis.credentials.port";
	private final static String REDISPASSWORD = "vcap.services.redis.credentials.password";
	private final static String HTTP="http://";
	/*portal.server = http://hostname.domain/portal
		portal.server.canonical=http://hostname.domain

		# no trailing slash
		api2.url=http://hostname.domain/api
		api2.canonical.url=http://hostname.domain/api
	*/
	private final static String PORTALSERVER = "portal_server";
	private final static String PORTALCANONICALURL = "portal_server_canonical";
	private final static String API2URL = "api2_url";
	private final static String API2CANONICALURL="api2_canonical_url";
        private final static String MONGO_SERVICE = "mongo_service"; 
	
	private static StandardServletEnvironment env = new StandardServletEnvironment();

	public VcapPropertyLoaderListener() {
		super();

		this.onApplicationEvent(new ApplicationEnvironmentPreparedEvent(
				new SpringApplication(), new String[0], env));
		ClassLoader c = getClass().getClassLoader();
		@SuppressWarnings("resource")
		URLClassLoader urlC = (URLClassLoader) c;
		URL[] urls = urlC.getURLs();
		String path = urls[0].getPath();
		Properties props = new Properties();
		
		File europeanaProperties = new File(path + "/europeana.properties");
		try {
			props.load(new FileInputStream(europeanaProperties));
			if (!props.containsKey("postgres.db")) {
				FileUtils.writeStringToFile(europeanaProperties, "\n" + "postgres.db"
						+ "=" + env.getProperty(POSTGRESDB) + "\n", true);
				FileUtils.writeStringToFile(
						europeanaProperties,
						"postgres.username" + "="
								+ env.getProperty(POSTGRESUSERNAME) + "\n",
						true);
				FileUtils.writeStringToFile(
						europeanaProperties,
						"postgres.password" + "="
								+ env.getProperty(POSTGRESPASSWORD) + "\n",
						true);
				FileUtils.writeStringToFile(europeanaProperties,
						"postgres.host" + "=" + env.getProperty(POSTGRESHOST)
								+ "\n", true);
                                String mongoDb = env.getSystemEnvironment().get(MONGO_SERVICE).toString();
                                String mongoUserName = VCAP+mongoDb+USERNAME;
                                String mongoPassword = VCAP+mongoDb+PASSWORD;
                                String mongoHosts = VCAP+mongoDb+HOSTS;
				FileUtils.writeStringToFile(
						europeanaProperties,
						"mongodb.username" + "="
								+ env.getProperty(mongoUserName) + "\n", true);
				FileUtils.writeStringToFile(
						europeanaProperties,
						"mongodb.password" + "="
								+ env.getProperty(mongoPassword) + "\n", true);
				//org.apache.log4j.Logger.getLogger(this.getClass()).error(env.getProperty(MONGOHOSTS));
				String[] hosts = env.getProperty(mongoHosts).replace('[', ' ')
						.replace("]", " ").split(",");
				String mongoHost = "mongodb.host=";
				String mongoPort = "mongodb.port=";
				for (String host : hosts) {
					mongoHost = mongoHost + host.split(":")[0].trim() + ",";
					mongoPort = mongoPort + host.split(":")[1].trim() + ",";
				}
				FileUtils.writeStringToFile(europeanaProperties,
						mongoHost.substring(0, mongoHost.length() - 1) + "\n",
						true);
				FileUtils.writeStringToFile(europeanaProperties,
						mongoPort.substring(0, mongoPort.length() - 1) + "\n",
						true);
				FileUtils.writeStringToFile(europeanaProperties, "redis.host"
						+ "=" + env.getProperty(REDISHOST) +"\n", true);
                FileUtils.writeStringToFile(europeanaProperties, "redis.port"
                    + "=" + env.getProperty(REDISPORT) +"\n", true);
				FileUtils.writeStringToFile(europeanaProperties, "redis.password"
						+ "=" + env.getProperty(REDISPASSWORD)+"\n", true);
				
				if(env.containsProperty(API2CANONICALURL)){
					FileUtils.writeStringToFile(europeanaProperties, StringUtils.replaceChars(API2CANONICALURL,"_",".")
							+ "=" + HTTP+env.getSystemEnvironment().get(API2CANONICALURL)+"\n", true);
				}
				if(env.containsProperty(API2URL)){
					FileUtils.writeStringToFile(europeanaProperties, StringUtils.replaceChars(API2URL,"_",".")
							+ "=" + HTTP+env.getSystemEnvironment().get(API2URL)+"\n", true);
				}
				if(env.containsProperty(PORTALCANONICALURL)){
					FileUtils.writeStringToFile(europeanaProperties, StringUtils.replaceChars(PORTALCANONICALURL,"_",".")
							+ "=" + HTTP+env.getSystemEnvironment().get(PORTALCANONICALURL)+"\n", true);
				}
				if(env.containsProperty(PORTALSERVER)){
					FileUtils.writeStringToFile(europeanaProperties, StringUtils.replaceChars(PORTALSERVER,"_",".")
							+ "=" + HTTP+env.getSystemEnvironment().get(PORTALSERVER)+"\n", true);
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
					e1.getMessage(), e1.getCause());
		}

	}
}
