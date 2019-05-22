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
