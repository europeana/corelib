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

package eu.europeana.corelib.db.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.code.morphia.query.Query;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.GroupCommand;

import eu.europeana.corelib.db.entity.enums.RecordType;
import eu.europeana.corelib.db.entity.nosql.ApiLog;
import eu.europeana.corelib.db.service.ApiLogService;
import eu.europeana.corelib.db.service.abstracts.AbstractNoSqlServiceImpl;
import eu.europeana.corelib.definitions.model.statistics.TypeStatistics;
import eu.europeana.corelib.definitions.model.statistics.UserStatistics;
import eu.europeana.corelib.utils.model.DateInterval;

/**
 * 
 * 
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * @author Yorgos.Mamakis@ kb.nl
 */
public class ApiLogServiceImpl extends AbstractNoSqlServiceImpl<ApiLog, String> implements ApiLogService {

	@Override
	public void logApiRequest(String apiKey, String requestedUri, RecordType rType, String profile) {
		ApiLog apiLog = new ApiLog();
		apiLog.setProfile(profile);
		apiLog.setRecordType(rType);
		apiLog.setRequestedUri(requestedUri);
		apiLog.setApiKey(apiKey);
		store(apiLog);
	}

	@Override
	public List<ApiLog> findByApiKey(String apiKey) {
		Query<ApiLog> query = getDao().createQuery();
		query.field("apiKey").equal(apiKey);
		return query.asList();
	}

	@Override
	public long countByApiKey(String apiKey) {
		return getDao().count("apiKey", apiKey);
	}

	@Override
	public long countByApiKeyByInterval(String apiKey, DateInterval interval) {
		Query<ApiLog> query = getDao().createQuery();
		query.field("apiKey").equal(apiKey);
		query.field("timestamp").greaterThanOrEq(interval.getBegin());
		query.field("timestamp").lessThan(interval.getEnd());
		return query.countAll();
	}

	@Override
	public long countByInterval(DateInterval interval) {
		Query<ApiLog> query = getDao().createQuery();
		query.field("timestamp").greaterThanOrEq(interval.getBegin());
		query.field("timestamp").lessThan(interval.getEnd());
		return query.countAll();
	}

	// by users
	// db.logs.group({key: {apiKey: true}, cond: {}, initial: {count:0},
	// $reduce: function(obj, out){out.count++}});
	@Override
	public List<UserStatistics> getStatisticsByUser() {
		DBObject result = getDao().getCollection().group(
			new GroupCommand(
				getDao().getCollection(), // collection
				new BasicDBObject("apiKey", true), // keys,
				null, // cond
				new BasicDBObject("count", 0), // initial,
				"function(obj, out){out.count++}", // $reduce
				null // reduce
			)
		);

		List<UserStatistics> statistics = new ArrayList<UserStatistics>();
		for (String key : result.keySet()) {
			BasicDBObject item = (BasicDBObject) result.get(key);
			statistics.add(new UserStatistics(null, item.getString("apiKey"), item.getLong("count")));
		}

		return statistics;
	}

	// by types
	// db.logs.group({key: {recordType: true, profile: true}, cond: {}, initial:
	// {count:0}, $reduce: function(obj, out){out.count++}});
	@Override
	public List<TypeStatistics> getStatisticsByType() {
		DBObject keys = new BasicDBObject("recordType", true);
		keys.put("profile", true);
		DBObject result = getDao().getCollection().group(
			new GroupCommand(
				getDao().getCollection(), // collection
				keys, // keys
				null, // cond
				new BasicDBObject("count", 0), // initial
				"function(obj, out){out.count++}", // $reduce
				null // reduce
			)
		);
		
		List<TypeStatistics> statistics = new ArrayList<TypeStatistics>();
		for (String key : result.keySet()) {
			BasicDBObject item = (BasicDBObject) result.get(key);
			statistics.add(new TypeStatistics(
					item.getString("recordType"), 
					item.getString("profile"), 
					item.getLong("count"))
			);
		}

		return statistics;
	}

	/**
	 * Deletes the logs
	 */
	public void clearLogs() {
		getDao().deleteAll();
	}

	// db.logs.group({
	//   key: {apiKey: true}, 
	//   cond: {"timestamp": {$gte: ISODate("2013-04-30T22:00:00.000Z"), $lt: ISODate("2013-05-31T21:59:59.000Z")}}, 
	//   initial: {count:0}, 
	//   $reduce: function(obj, out){out.count++}
	// });
	@Override
	public List<UserStatistics> getStatisticsByUsersByInterval(DateInterval interval) {
		List<BasicDBObject> timestampConditions = new ArrayList<BasicDBObject>(
			Arrays.asList(
				new BasicDBObject("timestamp", new BasicDBObject("$gte", interval.getBegin())),
				new BasicDBObject("timestamp", new BasicDBObject("$lt", interval.getEnd()))
			)
		);
		DBObject condition = new BasicDBObject("$and", timestampConditions);

		GroupCommand groupCommand = new GroupCommand(
			getDao().getCollection(), // collection
			new BasicDBObject("apiKey", true), // keys,
			condition, // cond
			new BasicDBObject("count", 0), // initial,
			"function(obj, out){out.count++}", // $reduce
			null // reduce
		);

		DBObject result = getDao().getCollection().group(groupCommand);
		List<UserStatistics> statistics = new ArrayList<UserStatistics>();
		for (String key : result.keySet()) {
			BasicDBObject item = (BasicDBObject) result.get(key);
			statistics.add(new UserStatistics(null, item.getString("apiKey"), item.getLong("count")));
		}

		return statistics;
	}

}
