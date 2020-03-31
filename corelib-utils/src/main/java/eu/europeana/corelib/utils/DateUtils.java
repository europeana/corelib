package eu.europeana.corelib.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * Date util classes
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 *
 */
public class DateUtils {
	private static final Logger LOG = Logger.getLogger(DateUtils.class);
	private static final SimpleDateFormat  DATE_TIME_FORMATTER     = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	private static final DateTimeFormatter ISO_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);


	static {
		DATE_TIME_FORMATTER.setTimeZone(TimeZone.getTimeZone("GTM"));
	}

	private DateUtils() {
		// do not allow instances of this class
	}

	/**
	 * Clone a date
	 * @param date
	 * @return
	 */
	public static Date clone(Date date) {
		if (date != null) {
			return new Date(date.getTime());
		}
		return null;
	}

	/**
	 * Format a date
	 * @param date
	 * @return
	 *   An ISO 8061 string presentation in GTM time zone
	 */
	public static String format(Date date) {
		return DATE_TIME_FORMATTER.format(date);
	}


	/**
	 * Read a date from a string
	 * @param date
	 * @return
	 */
	public static Date parse(String date) {
		try {
			return DATE_TIME_FORMATTER.parse(date);
		} catch (ParseException e) {
			LOG.warn(e.getMessage());
		}
		return null;
	}

	/**
	 * Returns true if the string is a year  "yyyy"
	 *
	 * @param value value to check
	 * @return true when the string is a year
	 */
	public static boolean isYear(String value) {
		//check for single year ex: 1930
		if (StringUtils.isNotEmpty(value)) {
			try {
				Year year = Year.of(Integer.parseInt(value));
				return year != null;
			} catch (Exception e) {
				return false;
			}
		}
		return false;
	}

	/**
	 * Returns true if the string is a Year Range "yyyy-yyy"
	 *
	 * @param value value to check
	 * @return true when the string is a year range
	 */
	public static boolean isYearRange(String value) {
		String[] years = value.split("-");
		//check for range ex: 1980-1990
		if (years.length == 2) {
			try {
				return Year.parse(years[0]).isBefore(Year.parse(years[1]));
			}
			catch(Exception e) {
				return false;
			}
		}
		return false;
	}
	/**
	 * Returns true if the string is a ISO8601 date (format: "yyyy-MM-dd")
	 *
	 * @param value value to check
	 * @return true when the string is a ISO8601 date
	 */
	public static boolean isIsoDate(String value) {
		try {
			TemporalAccessor tmp = ISO_DATE_FORMATTER.parse(value);
			return tmp != null;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Returns true if the string is a ISO8601 date time (format:
	 * "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	 *
	 * @param value value to check
	 * @return true when the string is a ISO8601 date time
	 */
	public static boolean isIsoDateTime(String value) {
		return DateUtils.parse(value) != null;
	}

}
