package eu.europeana.corelib.utils;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;

public class DateUtilsTest {
	
	@Test
	public void cloneTest() {
		Assert.assertNull("Should return null", DateUtils.clone(null));
		Date today = new Date();
		Assert.assertEquals("Cloned day not the same", today,  DateUtils.clone(today));
	}

}
