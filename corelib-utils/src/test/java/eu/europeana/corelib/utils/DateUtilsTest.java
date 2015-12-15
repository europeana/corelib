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
