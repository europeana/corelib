package eu.europeana.corelib.utils;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.Year;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Date util classes
 *
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 *
 */
public class DateUtils {

  private static final Logger LOG = LogManager.getLogger(DateUtils.class);

  /*
   Using {@link DateTimeFormatter} instead of {@link SimpleDateFormat} A DateTimeFormater created
   from a pattern can be used as many times as necessary, it is immutable and is thread-safe.
   So a single shared static instance is safe to use from any number of threads.
   On the other hand a SimpleDateFormat date formats are not synchronized.
   Note: UTC behaves the same as GTM that was used on SimpleDateFormat
   */
  private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
      .ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
      .withZone(ZoneOffset.UTC);
  private static final DateTimeFormatter ISO_DATE_FORMATTER = DateTimeFormatter
      .ofPattern("yyyy-MM-dd", Locale.ENGLISH);

  private DateUtils() {
    // do not allow instances of this class
  }

  /**
   * Clone date.
   *
   * @param date the date
   * @return the date
   */
  public static Date clone(Date date) {
    if (date != null) {
      return new Date(date.getTime());
    }
    return null;
  }

  /**
   * Format a date
   *
   * @param date
   * @return An ISO 8061 string presentation in GMT time zone
   */
  public static String format(Date date) {
    return DATE_TIME_FORMATTER.format(date.toInstant());
  }

  /**
   * Parse and read a date from a string
   *
   * @param date the date
   * @return the date
   */
  public static Date parse(String date) {
    if (StringUtils.isNotEmpty(date)) {
      try {
        Instant instant = DATE_TIME_FORMATTER.parse(date, Instant::from);
        return Date.from(instant);
      } catch (DateTimeParseException e) {
        LOG.warn(e.getMessage());
      }
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
      } catch (NumberFormatException | DateTimeException e) {
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
    if (StringUtils.isNotEmpty(value)) {
      String[] years = value.split("-");
      //check for range ex: 1980-1990
      if (years.length == 2) {
        try {
          return Year.parse(years[0]).isBefore(Year.parse(years[1]));
        } catch (DateTimeException e) {
          return false;
        }
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
    if (StringUtils.isNotEmpty(value)) {
      try {
        TemporalAccessor tmp = ISO_DATE_FORMATTER.parse(value);
        return tmp != null;
      } catch (DateTimeException e) {
        return false;
      }
    }
    return false;
  }

  /**
   * Returns true if the string is a ISO8601 date time (format: "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
   *
   * @param value value to check
   * @return true when the string is a ISO8601 date time
   */
  public static boolean isIsoDateTime(String value) {
    return DateUtils.parse(value) != null;
  }
}