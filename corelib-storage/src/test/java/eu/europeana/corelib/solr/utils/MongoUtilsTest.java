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
package eu.europeana.corelib.solr.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import eu.europeana.corelib.definitions.jibx.LiteralType;
import eu.europeana.corelib.definitions.jibx.LiteralType.Lang;
import eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType;
import eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource;
import eu.europeana.corelib.edm.utils.MongoUtils;

/**
 * MongoUtils unit tests
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
public class MongoUtilsTest {
	
	@Test
	public void testContainsStringArray() {
		String[] arr = new String[] { "1", "2", "3" };
		Assert.assertTrue(MongoUtils.contains(arr, "2"));
		Assert.assertFalse(MongoUtils.contains(arr, "$"));
	}

	@Test
	public void testContainsMap() {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> listA = new ArrayList<String>();
		List<String> listB = new ArrayList<String>();
		listA.add("1");
		listA.add("2");
		listB.add("3");
		map.put("keyA", listA);
		map.put("keyB", listB);
		Assert.assertTrue(MongoUtils.contains(map, "keyA", "2"));
		Assert.assertFalse(MongoUtils.contains(map, "keyC", "2"));
		Assert.assertFalse(MongoUtils.contains(map, "keyA", "3"));
		Assert.assertFalse(MongoUtils.contains(map, "keyA", "4"));
	}

	@Test
	public void testCreateLiteralMapFromObject() {
		LiteralType obj = new LiteralType();
		Lang lang = new Lang();
		lang.setLang("en");
		obj.setLang(lang);
		obj.setString("str");
		Map<String,List<String>> testMap= MongoUtils.createLiteralMapFromString(obj);
		Assert.assertNotNull(testMap);
		Assert.assertEquals(1, testMap.size());
		Assert.assertEquals(testMap.keySet().iterator().next(), "en");
		Assert.assertEquals(testMap.get("en").get(0), "str");
	}

	@Test
	public void testCreateLiteralMapFromString() {
		Map<String,List<String>> testMap= MongoUtils.createLiteralMapFromString("str");
		Assert.assertNotNull(testMap);
		Assert.assertEquals(1, testMap.size());
		Assert.assertEquals(testMap.keySet().iterator().next(), "def");
		Assert.assertEquals(testMap.get("def").get(0), "str");
	}

	@Test
	public void testMapEquals() {
		Map<String,List<String>> mapA = new HashMap<String, List<String>>();
		Map<String,List<String>> mapB = new HashMap<String, List<String>>();
		Map<String,List<String>> mapC = new HashMap<String, List<String>>();
		Map<String,List<String>> mapD = new HashMap<String, List<String>>();
		Map<String,List<String>> mapE = new HashMap<String, List<String>>();
		List<String> listA = new ArrayList<String>();
		List<String> listB = new ArrayList<String>();
		List<String> listC = new ArrayList<String>();
		List<String> listD = new ArrayList<String>();
		listA.add("1");
		listA.add("2");
		listB.add("3");
		listB.add("4");
		listC.add("1");
		listC.add("2");
		listD.add("4");
		listD.add("3");
		mapA.put("1",listA);
		mapA.put("2", listB);
		mapB.put("1", listC);
		mapB.put("2", listD);
		mapC.put("1",listA);
		mapC.put("2", listA);
		mapD.put("1",listA);
		mapE.put("1", listA);
		mapE.put("3", listB);
		Assert.assertTrue(MongoUtils.mapEquals(mapA, mapB));
		Assert.assertFalse(MongoUtils.mapEquals(mapA, mapC));
		Assert.assertFalse(MongoUtils.mapEquals(mapA, mapD));
		Assert.assertFalse(MongoUtils.mapEquals(mapA, mapE));
	}

	@Test
	public void testArrayEquals() {
		String[] arrA = new String[]{"1","2","3"};
		String[] arrB = new String[]{"1","3","2"};
		String[] arrC = new String[]{"1","2"};
		String[] arrD = new String[]{"1","2","4"};
		Assert.assertTrue(MongoUtils.arrayEquals(arrA, arrB));
		Assert.assertFalse(MongoUtils.arrayEquals(arrA, arrC));
		Assert.assertFalse(MongoUtils.arrayEquals(arrA, arrD));
	}

	@Test
	public void testCreateResourceOrLiteralMapFromString() {
		
		ResourceOrLiteralType obj = new ResourceOrLiteralType();
		ResourceOrLiteralType.Lang lang = new ResourceOrLiteralType.Lang();
		lang.setLang("en");
		obj.setLang(lang);
		obj.setString("str");
		Map<String,List<String>>testMap = MongoUtils.createResourceOrLiteralMapFromString(obj);
		Assert.assertNotNull(testMap);
		Assert.assertEquals(1, testMap.size());
		Assert.assertEquals(testMap.keySet().iterator().next(), "en");
		Assert.assertEquals(testMap.get("en").get(0), "str");
		
		ResourceOrLiteralType obj2 = new ResourceOrLiteralType();
		Resource res = new Resource();
		res.setResource("str");
		obj2.setResource(res);
		Map<String,List<String>>testMap2 = MongoUtils.createResourceOrLiteralMapFromString(obj2);
		Assert.assertNotNull(testMap2);
		Assert.assertEquals(1, testMap2.size());
		Assert.assertEquals(testMap2.keySet().iterator().next(), "def");
		Assert.assertEquals(testMap2.get("def").get(0), "str");
		
		ResourceOrLiteralType obj3 = new ResourceOrLiteralType();
		ResourceOrLiteralType.Lang lang3 = new ResourceOrLiteralType.Lang();
		lang3.setLang("en");
		obj3.setLang(lang3);
		obj3.setString("str");
		Resource res2 = new Resource();
		res2.setResource("str2");
		obj3.setResource(res2);
		Map<String,List<String>>testMap3 = MongoUtils.createResourceOrLiteralMapFromString(obj3);
		Assert.assertNotNull(testMap3);
		Assert.assertEquals(1, testMap3.size());
		Assert.assertEquals(testMap3.keySet().iterator().next(), "en");
		Assert.assertEquals(testMap3.get("en").get(0), "str");
		Assert.assertEquals(testMap3.get("en").get(1), "str2");
	}

	@Test
	public void testCreateLiteralMapFromList() {
		List<LiteralType> listA = new ArrayList<LiteralType>();
		List<LiteralType> listB = new ArrayList<LiteralType>();
		List<LiteralType> listC = new ArrayList<LiteralType>();
		LiteralType ltA = new LiteralType();
		
		LiteralType ltB = new LiteralType();
		LiteralType ltC = new LiteralType();
		LiteralType ltD = new LiteralType();
		LiteralType ltE = new LiteralType();
		LiteralType ltF = new LiteralType();
		Lang lang = new Lang();
		lang.setLang("en");
		ltA.setString("strA");
		ltA.setLang(lang);
		ltB.setString("strB");
		ltB.setLang(lang);
		listA.add(ltA);
		listA.add(ltB);
		Map<String,List<String>> mapA = MongoUtils.createLiteralMapFromList(listA);
		Assert.assertNotNull(mapA);
		Assert.assertEquals(1, mapA.size());
		Assert.assertEquals(mapA.keySet().iterator().next(), "en");
		Assert.assertEquals(mapA.get("en").get(0), "strA");
		Assert.assertEquals(mapA.get("en").get(1), "strB");
		
		ltC.setString("strC");
		ltC.setLang(lang);
		ltD.setString("strD");
		listB.add(ltC);
		listB.add(ltD);
		Map<String,List<String>> mapB = MongoUtils.createLiteralMapFromList(listB);
		Assert.assertNotNull(mapB);
		Assert.assertEquals(2, mapB.size());
		Assert.assertTrue(mapB.containsKey("def"));
		Assert.assertTrue(mapB.containsKey("en"));
		Assert.assertEquals(mapB.get("en").get(0), "strC");
		Assert.assertEquals(mapB.get("def").get(0), "strD");
		
		ltE.setString("strE");
		ltF.setString("strF");
		listC.add(ltE);
		listC.add(ltF);
		Map<String,List<String>> mapC = MongoUtils.createLiteralMapFromList(listC);
		Assert.assertNotNull(mapC);
		Assert.assertEquals(1, mapC.size());
		Assert.assertTrue(mapC.containsKey("def"));
		Assert.assertEquals(mapC.get("def").get(0), "strE");
		Assert.assertEquals(mapC.get("def").get(1), "strF");
	}

	@Test
	public void testCreateResourceOrLiteralMapFromList() {

		List<ResourceOrLiteralType> listA = new ArrayList<ResourceOrLiteralType>();
		List<ResourceOrLiteralType> listB = new ArrayList<ResourceOrLiteralType>();
		List<ResourceOrLiteralType> listC = new ArrayList<ResourceOrLiteralType>();
		ResourceOrLiteralType ltA = new ResourceOrLiteralType();
		
		ResourceOrLiteralType ltB = new ResourceOrLiteralType();
		ResourceOrLiteralType ltC = new ResourceOrLiteralType();
		ResourceOrLiteralType ltD = new ResourceOrLiteralType();
		ResourceOrLiteralType ltE = new ResourceOrLiteralType();
		ResourceOrLiteralType ltF = new ResourceOrLiteralType();
		ResourceOrLiteralType.Lang lang = new ResourceOrLiteralType.Lang();
		lang.setLang("en");
		ltA.setString("strA");
		ltA.setLang(lang);
		ltB.setString("strB");
		ltB.setLang(lang);
		listA.add(ltA);
		listA.add(ltB);
		Map<String,List<String>> mapA = MongoUtils.createResourceOrLiteralMapFromList(listA);
		Assert.assertNotNull(mapA);
		Assert.assertEquals(1, mapA.size());
		Assert.assertEquals(mapA.keySet().iterator().next(), "en");
		Assert.assertEquals(mapA.get("en").get(0), "strA");
		Assert.assertEquals(mapA.get("en").get(1), "strB");
		
		ltC.setString("strC");
		ltC.setLang(lang);
		ltD.setString("strD");
		listB.add(ltC);
		listB.add(ltD);
		Map<String,List<String>> mapB = MongoUtils.createResourceOrLiteralMapFromList(listB);
		Assert.assertNotNull(mapB);
		Assert.assertEquals(2, mapB.size());
		Assert.assertTrue(mapB.containsKey("def"));
		Assert.assertTrue(mapB.containsKey("en"));
		Assert.assertEquals(mapB.get("en").get(0), "strC");
		Assert.assertEquals(mapB.get("def").get(0), "strD");
		
		ltE.setString("strE");
		ltF.setString("strF");
		listC.add(ltE);
		listC.add(ltF);
		Map<String,List<String>> mapC = MongoUtils.createResourceOrLiteralMapFromList(listC);
		Assert.assertNotNull(mapC);
		Assert.assertEquals(1, mapC.size());
		Assert.assertTrue(mapC.containsKey("def"));
		Assert.assertEquals(mapC.get("def").get(0), "strE");
		Assert.assertEquals(mapC.get("def").get(1), "strF");
		
	}
}
