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
import org.springframework.boot.SpringApplication;
import org.springframework.boot.cloudfoundry.VcapApplicationListener;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.web.context.support.StandardServletEnvironment;

public class VcapPropertyLoaderListener extends VcapApplicationListener {

	private final static String POSTGRESDB = "vcap.services.postgresql.credentials.db";
	private final static String POSTGRESUSERNAME = "vcap.services.postgresql.credentials.user";
	private final static String POSTGRESPASSWORD = "vcap.services.postgresql.credentials.password";
	private final static String POSTGRESHOST = "vcap.services.postgresql.credentials.host";
	private final static String MONGOUSERNAME = "vcap.services.europeana-object-db.credentials.username";
	private final static String MONGOPASSWORD = "vcap.services.europeana-object-db.credentials.password";
	private final static String MONGOHOSTS = "vcap.services.europeana-object-db.credentials.hosts";
	private final static String MONGOPORTS = "vcap.services.europeana-object-db.credentials.ports";
	private final static String REDISHOST = "vcap.services.redis.credentials.host";
	private final static String REDISPASSWORD = "vcap.services.redis.credentials.password";

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
				FileUtils.writeStringToFile(europeanaProperties, "postgres.db"
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
				FileUtils.writeStringToFile(europeanaProperties, "postgres.host"
						+ "=" + env.getProperty(POSTGRESHOST) + "\n", true);
				FileUtils.writeStringToFile(europeanaProperties, "mongodb.username"
						+ "=" + env.getProperty(MONGOUSERNAME) + "\n", true);
				FileUtils.writeStringToFile(europeanaProperties, "mongodb.password"
						+ "=" + env.getProperty(MONGOPASSWORD) + "\n", true);
				String host = env.getProperty(MONGOHOSTS).replace('[', ' ')
						.split(",")[0].trim();
				FileUtils.writeStringToFile(europeanaProperties, "mongodb.host"
						+ "=" + host.split(":")[0] + "\n", true);
				FileUtils.writeStringToFile(europeanaProperties, "mongodb.port"
						+ "=" + host.split(":")[1] + "\n", true);
				FileUtils.writeStringToFile(europeanaProperties, "redis.host"
						+ "=" + env.getProperty(REDISHOST) +"\n", true);
				
				FileUtils.writeStringToFile(europeanaProperties, "redis.password"
						+ "=" + env.getProperty(REDISPASSWORD), true);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
					e1.getMessage(), e1.getCause());
		}

	}
}
