package eu.europeana.corelib.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.log4j.Logger;

/**
 * Date util classes
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 *
 */
public class DateUtils {
	private static final Logger LOG = Logger.getLogger(DateUtils.class);
	private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	static {
		formatter.setTimeZone(TimeZone.getTimeZone("GTM"));
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
		return formatter.format(date);
	}


	/**
	 * Read a date from a string
	 * @param date
	 * @return
	 */
	public static Date parse(String date) {
		try {
			return formatter.parse(date);
		} catch (ParseException e) {
			LOG.error(e.getMessage());
		}
		return null;
	}
}
