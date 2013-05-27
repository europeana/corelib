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
		DBObject result = groupByApiKey(null);

		List<UserStatistics> statistics = createUserStatisticsList(result);

		return statistics;
	}

	// by types
	// db.logs.group({key: {recordType: true, profile: true}, cond: {}, initial:
	// {count:0}, $reduce: function(obj, out){out.count++}});
	@Override
	public List<TypeStatistics> getStatisticsByType() {
		DBObject keys = new BasicDBObject("recordType", true);
		keys.put("profile", true);

		DBObject result = groupBy(keys, null);

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

		DBObject result = groupByApiKey(condition);
		List<UserStatistics> statistics = createUserStatisticsList(result);

		return statistics;
	}

	@Override
	public List<UserStatistics> getStatisticsByUsersByRecordType(String recordType) {
		DBObject condition = new BasicDBObject("recordType", recordType);

		DBObject result = groupByApiKey(condition);
		List<UserStatistics> statistics = createUserStatisticsList(result);

		return statistics;
	}

	private List<UserStatistics> createUserStatisticsList(DBObject result) {
		List<UserStatistics> statistics = new ArrayList<UserStatistics>();
		for (String key : result.keySet()) {
			BasicDBObject item = (BasicDBObject) result.get(key);
			statistics.add(new UserStatistics(null, item.getString("apiKey"), item.getLong("count")));
		}
		return statistics;
	}

	private DBObject groupByApiKey(DBObject condition) {
		DBObject keys = new BasicDBObject("apiKey", true);

		return groupBy(keys, condition);
	}

	/**
	 * Issues a MongoDB group command, and return the result set
	 * 
	 * @param keys
	 *   The keys to group by
	 * @param condition
	 *   Search conditions
	 * @return
	 */
	private DBObject groupBy(DBObject keys, DBObject condition) {
		GroupCommand groupCommand = new GroupCommand(
			getDao().getCollection(), // collection
			keys, // keys,
			condition, // cond
			new BasicDBObject("count", 0), // initial,
			"function(obj, out){out.count++}", // $reduce
			null // reduce
		);

		DBObject result = getDao().getCollection().group(groupCommand);
		return result;
	}

}
