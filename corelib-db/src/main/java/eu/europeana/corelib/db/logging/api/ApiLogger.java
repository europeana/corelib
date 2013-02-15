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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.vz.mongodb.jackson.DBCursor;
import net.vz.mongodb.jackson.JacksonDBCollection;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.GroupCommand;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

import eu.europeana.corelib.db.exception.DatabaseException;
import eu.europeana.corelib.db.logging.api.enums.RecordType;
import eu.europeana.corelib.db.util.DateInterval;
import eu.europeana.corelib.db.util.DateUtils;
import eu.europeana.corelib.definitions.exception.ProblemType;

/**
 * Logger class for the API
 * 
 * @author Yorgos.Mamakis@ kb.nl
 */
public class ApiLogger {

	volatile static ApiLogger instance;

	static JacksonDBCollection<LogTypeImpl, String> logTypeCollection;

	final static long DAY = 24 * 60 * 60 * 1000;

	static Mongo mongo;

	/**
	 * 
	 * @return the mongo instance used
	 */
	public static Mongo getMongo() {
		return mongo;
	}

	/**
	 * Sets the mongo instance
	 * 
	 * @param mongo
	 */
	public static void setMongo(Mongo mongo) {
		ApiLogger.mongo = mongo;
	}

	public ApiLogger(Mongo mongo) throws DatabaseException {
		ApiLogger.mongo = mongo;
		instance = new ApiLogger();
	}

	private ApiLogger() throws DatabaseException {
		try {
			DB db = mongo.getDB("api_log");
			logTypeCollection = JacksonDBCollection.wrap(
					db.getCollection("logs"), LogTypeImpl.class, String.class);
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
		LogTypeImpl apiEntry = new LogTypeImpl();
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
	public List<LogTypeImpl> getRequests(String apiKey) {
		return getLogTypeList(apiKey);
	}

	/**
	 * Request the numbers of requests of an API key
	 * 
	 * @param apiKey
	 * @return
	 */
	public int getRequestNumber(String apiKey) {
		Date now = new Date();
		Date yesterday = new Date(now.getTime() - DAY);
		DBCursor<LogTypeImpl> lType = logTypeCollection.find()
				.is("apiKey", apiKey).greaterThanEquals("timestamp", yesterday)
				.lessThan("timestamp", now);
		return lType.size();
	}

	/**
	 * Returns the all-time request number by an API key
	 */
	public int getTotalRequestNumber(String apiKey) {
		DBCursor<LogTypeImpl> lType = logTypeCollection.find().is("apiKey",
				apiKey);
		return lType.size();
	}

	public int getDaily(String apiKey, int dayDifference) {
		DateInterval interval = DateUtils.getDay(dayDifference);

		DBCursor<LogTypeImpl> lType = logTypeCollection.find()
				.is("apiKey", apiKey)
				.greaterThanEquals("timestamp", interval.getBegin())
				.lessThanEquals("timestamp", interval.getEnd());
		return lType.size();
	}

	// by dates
	public int getDaily(int dayDifference) {
		DateInterval interval = DateUtils.getDay(dayDifference);

		int count = logTypeCollection.find()
				.greaterThanEquals("timestamp", interval.getBegin())
				.lessThanEquals("timestamp", interval.getEnd()).count();
		return count;
	}

	// by dates
	public int getCountByInterval(DateInterval interval) {
		int count = logTypeCollection.find()
				.greaterThanEquals("timestamp", interval.getBegin())
				.lessThanEquals("timestamp", interval.getEnd()).count();
		return count;
	}

	// by users
	// db.logs.group({key: {apiKey: true}, cond: {}, initial: {count:0},
	// $reduce: function(obj, out){out.count++}});
	public DBObject getByUser() {
		DBObject result = logTypeCollection.group(new GroupCommand(
				logTypeCollection.getDbCollection(), // collection
				new BasicDBObject("apiKey", true), // keys,
				null, // cond
				new BasicDBObject("count", 0), // initial,
				"function(obj, out){out.count++}", // $reduce
				null // reduce
				));
		return result;
	}

	// by types
	// db.logs.group({key: {recordType: true, profile: true}, cond: {}, initial:
	// {count:0}, $reduce: function(obj, out){out.count++}});
	public DBObject getByType() {
		DBObject keys = new BasicDBObject("recordType", true);
		keys.put("profile", true);
		DBObject result = logTypeCollection.group(new GroupCommand(
				logTypeCollection.getDbCollection(), // collection
				keys, // keys
				null, // cond
				new BasicDBObject("count", 0), // initial
				"function(obj, out){out.count++}", // $reduce
				null // reduce
				));
		return result;
	}

	/**
	 * Retrieve the logs for a specific apiKey
	 * 
	 * @param apiKey
	 * @return
	 */

	private List<LogTypeImpl> getLogTypeList(String apiKey) {
		DBCursor<LogTypeImpl> cur = logTypeCollection.find().is("apiKey",
				apiKey);
		List<LogTypeImpl> list = new ArrayList<LogTypeImpl>();
		while (cur.hasNext()) {
			LogTypeImpl one = cur.next();
			list.add(one);
		}
		return list;
	}

	/**
	 * Deletes the logs
	 */
	public void clearLogs() {
		logTypeCollection.drop();
	}
}
