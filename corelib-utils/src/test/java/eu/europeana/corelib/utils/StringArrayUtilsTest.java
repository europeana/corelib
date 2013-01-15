package eu.europeana.corelib.utils;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

public class StringArrayUtilsTest {

	@Test
    public void isNotBlankTest() {
		Assert.assertTrue(StringArrayUtils.isNotBlank(new String[]{"test"}));
		Assert.assertFalse(StringArrayUtils.isNotBlank(new String[]{" "}));
		Assert.assertFalse(StringArrayUtils.isNotBlank(new String[]{""}));
		Assert.assertFalse(StringArrayUtils.isNotBlank(new String[]{}));
		Assert.assertFalse(StringArrayUtils.isNotBlank(null));
    }
	
	@Test
	public void isBlankTest() {
		Assert.assertFalse(StringArrayUtils.isBlank(new String[]{"test"}));
		Assert.assertTrue(StringArrayUtils.isBlank(new String[]{" "}));
		Assert.assertTrue(StringArrayUtils.isBlank(new String[]{""}));
		Assert.assertTrue(StringArrayUtils.isBlank(new String[]{}));
		Assert.assertTrue(StringArrayUtils.isBlank(null));
	}
	
	@Test
	public void toArrayTest() {
		Assert.assertNotNull(StringArrayUtils.toArray(null));
		Assert.assertEquals(0, StringArrayUtils.toArray(null).length);
		
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
		Assert.assertEquals("", StringArrayUtils.formatList(new String[]{""}));
		Assert.assertEquals("test", StringArrayUtils.formatList(new String[]{"test"}));
		Assert.assertEquals("1 & 2", StringArrayUtils.formatList(new String[]{"1","2"}));
		Assert.assertEquals("1, 2 & 3", StringArrayUtils.formatList(new String[]{"1","2 "," 3"}));
	}
	
	@Test
	public void addToArrayTest(){
		String[] items = new String[]{"1","2"};
		String str= "3";
		Assert.assertEquals(3,StringArrayUtils.addToArray(items, str).length);
	}
}
