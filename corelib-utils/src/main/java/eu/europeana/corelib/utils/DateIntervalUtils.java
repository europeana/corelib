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
package eu.europeana.corelib.utils;

import java.util.Date;

import org.joda.time.DateTime;

import eu.europeana.corelib.utils.model.DateInterval;

public class DateIntervalUtils {

	public static DateInterval getToday() {
		Date now = new Date();
		Date start = new DateTime().withTimeAtStartOfDay().toDate();
		return new DateInterval(start, now);
	}

	public static DateInterval getLast24Hours() {
		DateTime now = new DateTime();
		return new DateInterval(now.minusDays(1).toDate(), now.toDate());
	}

	public static DateInterval getDay(int i) {
		Date start = new DateTime().minusDays(i).withTimeAtStartOfDay().toDate();
		Date end =  new DateTime(start).plusDays(1).toDate();
		return new DateInterval(start, end);
	}

	public static DateInterval getWeek() {
		Date start = new DateTime().minusDays(7).withTimeAtStartOfDay().toDate();
		Date end = new DateTime(start).plusWeeks(1).toDate(); 
		return new DateInterval(start, end);
	}

	public static DateInterval getThisWeek() {
		DateTime end = new DateTime();
		DateTime start = end.minusDays(end.getDayOfWeek() - 1).withTimeAtStartOfDay().toDateTime();
		return new DateInterval(start.toDate(), end.toDate());
	}

	public static DateInterval getWeek(int i) {
		DateTime now = new DateTime();
		DateTime start = now.minusDays(now.getDayOfWeek() - 1).withTimeAtStartOfDay().minusWeeks(i).toDateTime();
		DateTime end = start.plusWeeks(1).minusSeconds(1);
		return new DateInterval(start.toDate(), end.toDate());
	}

	public static DateInterval getMonth(int i) {
		DateTime now = new DateTime();
		DateTime start = now.minusDays(now.getDayOfMonth() - 1).withTimeAtStartOfDay().minusMonths(i).toDateTime();
		DateTime end = start.plusMonths(1).minusSeconds(1);
		return new DateInterval(start.toDate(), end.toDate());
	}
}
