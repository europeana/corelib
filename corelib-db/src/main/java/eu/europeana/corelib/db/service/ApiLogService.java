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

package eu.europeana.corelib.db.service;

import java.util.List;

import eu.europeana.corelib.db.entity.enums.RecordType;
import eu.europeana.corelib.db.entity.nosql.ApiLog;
import eu.europeana.corelib.db.service.abstracts.AbstractNoSqlService;
import eu.europeana.corelib.definitions.model.statistics.TypeStatistics;
import eu.europeana.corelib.definitions.model.statistics.UserStatistics;
import eu.europeana.corelib.utils.model.DateInterval;

/**
 * 
 * 
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * @author Yorgos.Mamakis@ kb.nl
 */
public interface ApiLogService extends AbstractNoSqlService<ApiLog, String> {

	/**
	 * Log a API request
	 * @param apiKey
	 *   The API key (wskey)
	 * @param requestedUri
	 *   The requested URL
	 * @param rType
	 *   The record type to request
	 * @param profile
	 *   The profile
	 */
	void logApiRequest(String apiKey, String requestedUri, RecordType rType, String profile);

	/*
	 *  FINDERS / COUNTERS
	 */

	List<ApiLog> findByApiKey(String apiKey);

	long countByApiKey(String apiKey);

	long countByInterval(DateInterval interval);

	long countByIntervalAndApiKey(DateInterval interval, String apiKey);

	long countByIntervalAndRecordType(DateInterval interval, String recordType);


	/*
	 *  STATISTICS
	 */

	/**
	 * Get statistics by type
	 * @return
	 */
	List<TypeStatistics> getStatisticsForType();

	/**
	 * Get statistics by user
	 * @return
	 */
	List<UserStatistics> getStatisticsForUser();

	/**
	 * Get statistics by user in a given date interval
	 * @param interval
	 *   A date interval, in which the statistics should be calculated
	 * @return
	 */
	List<UserStatistics> getStatisticsForUsersByInterval(DateInterval interval);

	/**
	 * Get user statistics by API request type
	 * @param recordType
	 *   The API request type (LIMIT, SEARCH, OBJECT)
	 * @return
	 */
	List<UserStatistics> getStatisticsForUsersByRecordType(String recordType);

	/**
	 * Get user statistics by API request type
	 * @param recordType
	 *   The API request type (LIMIT, SEARCH, OBJECT)
	 * @return
	 */
	List<TypeStatistics> getStatisticsForRecordTypesByUser(String apiKey);

	/**
	 * Get user statistics by interval
	 * @param interval
	 *   A date interval, in which the statistics should be calculated
	 * @return
	 */
	List<TypeStatistics> getStatisticsForRecordTypesByInterval(DateInterval interval);

}
