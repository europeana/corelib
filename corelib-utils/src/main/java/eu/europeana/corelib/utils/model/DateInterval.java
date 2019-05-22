package eu.europeana.corelib.utils.model;

import java.util.Date;

/**
 * Class representing a Date interval
 *
 */
public class DateInterval {

	Date begin;
	Date end;

	public DateInterval(Date begin, Date end) {
		this.begin = begin;
		this.end = end;
	}

	public Date getBegin() {
		return begin;
	}

	public void setBegin(Date begin) {
		this.begin = begin;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	@Override
	public String toString() {
		return begin.toString() + " - " + end.toString();
	}
}
