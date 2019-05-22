package eu.europeana.corelib.utils;

import java.util.*;


import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * String array util classes
 * 
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * 
 */
public class StringArrayUtilsTest {

	@Test
	public void isNotBlankTest() {
		assertTrue(StringArrayUtils.isNotBlank(new String[]{"test"}));
		assertFalse(StringArrayUtils.isNotBlank(new String[]{" "}));
		assertFalse(StringArrayUtils.isNotBlank(new String[]{""}));
		assertFalse(StringArrayUtils.isNotBlank(new String[]{}));
		assertFalse(StringArrayUtils.isNotBlank(null));
	}

	@Test
	public void isNotBlankLinkTest() {
		assertTrue(StringArrayUtils.isNotBlankList(Collections.singletonList("test")));
		assertFalse(StringArrayUtils.isNotBlankList(Collections.singletonList(" ")));
		assertFalse(StringArrayUtils.isNotBlankList(Collections.singletonList("")));
		assertFalse(StringArrayUtils.isNotBlankList(Arrays.asList(new String[]{})));
		assertFalse(StringArrayUtils.isNotBlankList(null));
	}

	@Test
	public void isBlankTest() {
		assertFalse(StringArrayUtils.isBlank(new String[]{"test"}));
		assertTrue(StringArrayUtils.isBlank(new String[]{" "}));
		assertTrue(StringArrayUtils.isBlank(new String[]{""}));
		assertTrue(StringArrayUtils.isBlank(new String[]{}));
		assertTrue(StringArrayUtils.isBlank(null));
	}

	@Test
	public void toArrayTest() {
		Assert.assertNotNull(StringArrayUtils.toArray((List<String>) null));
		Assert.assertEquals(0,
				StringArrayUtils.toArray((List<String>) null).length);

		List<String> list = new ArrayList<>();
		Assert.assertEquals(0, StringArrayUtils.toArray(list).length);

		list.add("1");
		Assert.assertEquals(1, StringArrayUtils.toArray(list).length);

		list.add("2");
		Assert.assertEquals(2, StringArrayUtils.toArray(list).length);
		
		Assert.assertEquals(2, StringArrayUtils.toArray("1","2").length);
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
		Assert.assertEquals(1, StringArrayUtils.addToArray(null, str).length);
	}

	
	/**
	 * Testing StringArrayUtils.splitWebParameter()
	 */
	@Test
	public void testSplitWebParamater() {
		assertArrayEquals(new String[]{"open"}, StringArrayUtils.splitWebParameter(new String[]{"open"}));
		assertArrayEquals(new String[]{"open", "permission"}, StringArrayUtils.splitWebParameter(new String[]{"open", "permission"}));
		assertArrayEquals(new String[]{"open", "permission"}, StringArrayUtils.splitWebParameter(new String[]{"open permission"}));
		assertArrayEquals(new String[]{"open", "permission"}, StringArrayUtils.splitWebParameter(new String[]{"open+permission"}));
		assertArrayEquals(new String[]{"open", "permission"}, StringArrayUtils.splitWebParameter(new String[]{"open,permission"}));

		assertArrayEquals(new String[]{"restricted", "open", "permission"}, StringArrayUtils.splitWebParameter(new String[]{"restricted", "open permission"}));
		assertArrayEquals(new String[]{"restricted", "open", "permission"}, StringArrayUtils.splitWebParameter(new String[]{"restricted", "open+permission"}));
		assertArrayEquals(new String[]{"restricted", "open", "permission"}, StringArrayUtils.splitWebParameter(new String[]{"restricted", "open,permission"}));

		// this is not cleared
		assertArrayEquals(new String[]{"open/permission"}, StringArrayUtils.splitWebParameter(new String[]{"open/permission"}));
		assertArrayEquals(StringArrayUtils.splitWebParameter(new String[1]),StringArrayUtils.EMPTY_ARRAY);
		assertArrayEquals(StringArrayUtils.splitWebParameter(new String[]{""}),StringArrayUtils.EMPTY_ARRAY);
	}
	
	@Test
	public void toListTest(){
		List<String> strList = new ArrayList<>();
		strList.add("1");
		strList.add("2");
		assertEquals(strList,StringArrayUtils.toList("1","2"));
	}
	@Test
	public void toSetTest(){
		Set<String> strList = new HashSet<>();
		strList.add("1");
		
		assertEquals(strList,StringArrayUtils.toSet("1","1"));
	}
}
