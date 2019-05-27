package eu.europeana.corelib.utils;

import org.apache.commons.lang.StringUtils;
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
}
