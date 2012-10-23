/*
 * Copyright 2007-2012 The Europeana Foundation
 *
 *  Licenced under the EUPL, Version 1.1 (the "Licence") and subsequent versions as approved 
 *  by the European Commission;
 *  You may not use this work except in compliance with the Licence.
 *  
 *  You may obtain a copy of the Licence at:
 *  http://joinup.ec.europa.eu/software/page/eupl
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under 
 *  the Licence is distributed on an "AS IS" basis, without warranties or conditions of 
 *  any kind, either express or implied.
 *  See the Licence for the specific language governing permissions and limitations under 
 *  the Licence.
 */
package eu.europeana.corelib.db.logging.api;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.vz.mongodb.jackson.DBCursor;
import net.vz.mongodb.jackson.JacksonDBCollection;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

import eu.europeana.corelib.db.exception.DatabaseException;
import eu.europeana.corelib.db.logging.api.enums.RecordType;
import eu.europeana.corelib.definitions.exception.ProblemType;

public class ApiLogger {

	static ApiLogger instance;
	static JacksonDBCollection<LogType, String> logTypeCollection;
	final static long DAY = 24 * 60 * 60 * 1000;
	@Resource(name = "corelib_db_mongo")
	Mongo mongo;

	private ApiLogger() throws DatabaseException {

		try {

			DB db = mongo.getDB("api_log");
			if (db.collectionExists("logs")) {

				logTypeCollection = JacksonDBCollection.wrap(
						db.getCollection("logs"), LogType.class, String.class);
			}
		} catch (MongoException e) {
			throw new DatabaseException(ProblemType.UNKNOWN);
		}
	}

	/**
	 * Return single instance of the ApiLogger
	 * 
	 * @return
	 */
	public static ApiLogger getApiLogger() {
		if (instance == null) {
			try {
				instance = new ApiLogger();
			} catch (DatabaseException e) {
				e.printStackTrace();
			}
		}
		return instance;
	}

	/**
	 * Save an API request
	 * 
	 * @param apiKey
	 *            The API key that generated the request
	 * @param requestedUri
	 *            The URI tha was requested
	 * @param rType
	 *            The type of call made
	 * @param profile
	 *            The profile used
	 */
	public void saveApiRequest(String apiKey, String requestedUri,
			RecordType rType, String profile) {
		LogType apiEntry = new LogTypeImpl();
		apiEntry.setProfile(profile);
		apiEntry.setRecordType(rType);
		apiEntry.setRequestedUri(requestedUri);
		apiEntry.setTimestamp(new Date());
		apiEntry.setApiKey(apiKey);
		logTypeCollection.save(apiEntry);
	}

	/**
	 * Retrieve the logs for a specific entry, null otherwise
	 * 
	 * @param apiKey
	 * @return
	 */
	public List<LogType> getRequests(String apiKey) {
		return getLogTypeList(apiKey);
	}

	public int getRequestNumber(String apiKey) {
		Date now = new Date();
		Date yesterday = new Date(now.getTime() - DAY);
		DBCursor<LogType> lType = logTypeCollection.find().is("apiKey", apiKey)
				.greaterThanEquals("timestamp", yesterday)
				.lessThan("timestamp", now);
		if (lType.hasNext()) {
			return lType.toArray().size();
		}
		return 0;

	}

	/**
	 * Retrieve the logs for a specific apiKey
	 * 
	 * @param apiKey
	 * @return
	 */

	private List<LogType> getLogTypeList(String apiKey) {
		DBCursor<LogType> cur = logTypeCollection.find().is("apiKey", apiKey);
		if (cur.hasNext()) {
			return cur.toArray();
		}
		return null;
	}
}
