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

import static org.junit.Assert.assertArrayEquals;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

/**
 * String array util classes
 * 
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * 
 */
public class StringArrayUtilsTest {

	@Test
	public void isNotBlankTest() {
		Assert.assertTrue(StringArrayUtils.isNotBlank(new String[] { "test" }));
		Assert.assertFalse(StringArrayUtils.isNotBlank(new String[] { " " }));
		Assert.assertFalse(StringArrayUtils.isNotBlank(new String[] { "" }));
		Assert.assertFalse(StringArrayUtils.isNotBlank(new String[] {}));
		Assert.assertFalse(StringArrayUtils.isNotBlank(null));
	}

	@Test
	public void isBlankTest() {
		Assert.assertFalse(StringArrayUtils.isBlank(new String[] { "test" }));
		Assert.assertTrue(StringArrayUtils.isBlank(new String[] { " " }));
		Assert.assertTrue(StringArrayUtils.isBlank(new String[] { "" }));
		Assert.assertTrue(StringArrayUtils.isBlank(new String[] {}));
		Assert.assertTrue(StringArrayUtils.isBlank(null));
	}

	@Test
	public void toArrayTest() {
		Assert.assertNotNull(StringArrayUtils.toArray((List<String>) null));
		Assert.assertEquals(0,
				StringArrayUtils.toArray((List<String>) null).length);

		List<String> list = new ArrayList<String>();
		Assert.assertEquals(0, StringArrayUtils.toArray(list).length);

		list.add("1");
		Assert.assertEquals(1, StringArrayUtils.toArray(list).length);

		list.add("2");
		Assert.assertEquals(2, StringArrayUtils.toArray(list).length);

	}

	@Test
	public void formatListTest() {
		Assert.assertEquals("", StringArrayUtils.formatList(null));
		Assert.assertEquals("",
				StringArrayUtils.formatList(new String[] { "" }));
		Assert.assertEquals("test",
				StringArrayUtils.formatList(new String[] { "test" }));
		Assert.assertEquals("1 & 2",
				StringArrayUtils.formatList(new String[] { "1", "2" }));
		Assert.assertEquals("1, 2 & 3",
				StringArrayUtils.formatList(new String[] { "1", "2 ", " 3" }));
	}

	@Test
	public void addToArrayTest() {
		String[] items = new String[] { "1", "2" };
		String str = "3";
		Assert.assertEquals(3, StringArrayUtils.addToArray(items, str).length);
	}
	
	@Test
	public void testSplitWebParamater() {
		assertArrayEquals(new String[]{"open"}, StringArrayUtils.splitWebParameter(new String[]{"open"}));
		assertArrayEquals(new String[]{"open", "permission"}, StringArrayUtils.splitWebParameter(new String[]{"open", "permission"}));
		assertArrayEquals(new String[]{"open", "permission"}, StringArrayUtils.splitWebParameter(new String[]{"open permission"}));
		assertArrayEquals(new String[]{"open", "permission"}, StringArrayUtils.splitWebParameter(new String[]{"open+permission"}));
		assertArrayEquals(new String[]{"open", "permission"}, StringArrayUtils.splitWebParameter(new String[]{"open,permission"}));

		// this is not cleared
		assertArrayEquals(new String[]{"open/permission"}, StringArrayUtils.splitWebParameter(new String[]{"open/permission"}));
	}
	
}
