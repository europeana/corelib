package eu.europeana.corelib.utils;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Date util classes
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 *
 */
public class DateUtilsTest {
	
	@Test
	public void cloneTest() {
		assertNull("Should return null", DateUtils.clone(null));
		Date today = new Date();
		assertEquals("Cloned day not the same", today,  DateUtils.clone(today));
	}

	@Test
	public void formatTest(){
		
		Date date = new Date(10000000L);
		assertTrue(StringUtils.equals(DateUtils. format(date),"1970-01-01T02:46:40.000Z"));
	}
	
	@Test
	public void parseTest(){
		assertEquals(10000000L,DateUtils.parse("1970-01-01T02:46:40.000Z").getTime());
	}

	@Test
	public void test_isYearOrYearRange() {
		//check all possibilities for year range value
		assertTrue(DateUtils.isYearRange("1880-1990"));
		assertFalse(DateUtils.isYearRange("2000-1990"));
		assertFalse(DateUtils.isYearRange("Geschichte 1775-1798"));
		assertFalse(DateUtils.isYearRange("Geschichte 1775"));
		assertFalse(DateUtils.isYearRange("1775 -Geschichte"));
		assertFalse(DateUtils.isYearRange("1775 -"));
		assertFalse(DateUtils.isYearRange(""));
		assertFalse(DateUtils.isYearRange(null));

		//check year
		assertTrue(DateUtils.isYear("1880"));
		assertFalse(DateUtils.isYear("188F"));
		assertFalse(DateUtils.isYear("1C80"));
		assertFalse(DateUtils.isYear("abcd"));
		assertFalse(DateUtils.isYear(""));
		assertFalse(DateUtils.isYear(null));
	}

	@Test
	public void test_isIsoDate() {
		//yyyy-MM-dd format check
		assertTrue(DateUtils.isIsoDate("2000-02-13"));
		assertFalse(DateUtils.isIsoDate("13-02-2000"));
		assertFalse(DateUtils.isIsoDate("testing-90"));
		assertFalse(DateUtils.isIsoDate("8765 testing"));
		assertFalse(DateUtils.isIsoDate(""));
		assertFalse(DateUtils.isIsoDate(null));
	}

	@Test
	public void test_isIsoDateTime () {
		//yyyy-MM-dd'T'HH:mm:ss.SSS'Z'  Date time check
		assertTrue(DateUtils.isIsoDateTime("2017-07-26T01:00:00.000Z"));
		assertFalse(DateUtils.isIsoDateTime("2017-07-26T01:00:00Z"));
		assertFalse(DateUtils.isIsoDateTime("2017-07-2601:00:00.000Z"));
		assertFalse(DateUtils.isIsoDateTime("2017-07-26T01:00:00.000"));
		assertFalse(DateUtils.isIsoDateTime("2017-07-26T01::00.000Z"));
		assertFalse(DateUtils.isIsoDateTime(""));
		assertFalse(DateUtils.isIsoDateTime(null));
	}
}
