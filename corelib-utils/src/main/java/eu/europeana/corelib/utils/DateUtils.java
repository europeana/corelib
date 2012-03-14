package eu.europeana.corelib.utils;

import java.util.Date;

public class DateUtils {
	
	private DateUtils() {
		// do not allow instances of this class
	}
	
	public static Date clone(Date date) {
		if (date != null) {
			return new Date(date.getTime());
		}
		return null;
	}

}
