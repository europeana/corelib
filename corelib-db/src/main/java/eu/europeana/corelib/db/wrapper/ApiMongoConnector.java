package eu.europeana.corelib.db.wrapper;

import java.net.UnknownHostException;

import org.apache.commons.lang3.StringUtils;

import com.google.code.morphia.Morphia;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.google.code.morphia.Datastore;

import eu.europeana.corelib.logging.Logger;

public class ApiMongoConnector {

	static Logger log = Logger.getLogger(ApiMongoConnector.class.getCanonicalName());

	public ApiMongoConnector() {
		log.info("new ApiMongoConnector");
	}

	public Datastore createDatastore(String label, String host, int port, String dbName, String username, String password) {
		Datastore datastore = null;
		Morphia connection = new Morphia();
		try {
			log.info(String.format("Connecting to '%s' mongo server: %s:%d/%s", label, host, port, dbName));
			Mongo mongo = new Mongo(host, port);
			if(StringUtils.isNotEmpty(username)&&StringUtils.isNotEmpty(password)){
			datastore = connection.createDatastore(mongo, dbName, username, password.toCharArray());
			} else {
				datastore = connection.createDatastore(mongo, dbName);
			}
			log.info(String.format("Connection to '%s' mongo server was successful", label));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		}
		return datastore;
	}
}
