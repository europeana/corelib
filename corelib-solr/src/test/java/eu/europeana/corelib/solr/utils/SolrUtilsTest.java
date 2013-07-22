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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import eu.europeana.corelib.definitions.jibx.Aggregation;
import eu.europeana.corelib.definitions.jibx.IsShownBy;
import eu.europeana.corelib.definitions.jibx.LiteralType;
import eu.europeana.corelib.definitions.jibx.RDF;
import eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType;
import eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Lang;
import eu.europeana.corelib.definitions.jibx.ResourceOrLiteralType.Resource;
import eu.europeana.corelib.definitions.jibx.ResourceType;
import eu.europeana.corelib.definitions.jibx._Object;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.definitions.solr.beans.ApiBean;
import eu.europeana.corelib.definitions.solr.beans.BriefBean;
import eu.europeana.corelib.definitions.solr.beans.FullBean;
import eu.europeana.corelib.definitions.solr.beans.IdBean;
import eu.europeana.corelib.solr.bean.impl.ApiBeanImpl;
import eu.europeana.corelib.solr.bean.impl.BriefBeanImpl;
import eu.europeana.corelib.solr.bean.impl.IdBeanImpl;

/**
 * Solr Utils tests
 * 
 * @author Peter.Kiraly@ kb.nl
 * @author Yorgos.Mamakis@ kb.nl
 */
public class SolrUtilsTest {

	@Test
	/**
	 * Tests the translateQuery function.
	 */
	public void translateQueryTest() {
		Map<String, String> identicalQueries = new HashMap<String, String>() {
			{
				put("simpleQuery", "simpleQuery");
				put("proxy_dc_coverage:ok", "proxy_dc_coverage:ok");
			}
		};
		for (String query : identicalQueries.keySet()) {
			assertEquals(identicalQueries.get(query),
					SolrUtils.translateQuery(query));
		}

		Map<String, String> aggregatedQueries = new HashMap<String, String>() {
			{
				put("title:*:*", "title:*");
				put("who:*:*", "who:*");
				put("what:*:*", "what:*");
				put("where:*:*", "where:*");
				put("when:*:*", "when:*");
			}
		};
		for (String query : aggregatedQueries.keySet()) {
			assertEquals(aggregatedQueries.get(query),
					SolrUtils.translateQuery(query));
		}

		for (FieldMapping field : FieldMapping.values()) {
			String eseQuery = field.getEseField() + ":test";
			String edmQuery = field.getEdmField() + ":test";
			// ESE is translated to EDM
			assertEquals(edmQuery, SolrUtils.translateQuery(eseQuery));

			// but EDM is not transformed
			assertEquals(edmQuery, SolrUtils.translateQuery(edmQuery));
		}

		// testing replace in context
		for (FieldMapping field : FieldMapping.values()) {
			String eseQuery = "test " + field.getEseField() + ":test";
			String edmQuery = "test " + field.getEdmField() + ":test";
			// ESE is translated to EDM
			assertEquals(edmQuery, SolrUtils.translateQuery(eseQuery));

			// but EDM is not transformed
			assertEquals(edmQuery, SolrUtils.translateQuery(edmQuery));
		}
	}

	@Test
	public void testTypeFacet() {
		String[] refinementsOk = new String[] { "TYPE:IMAGE" };
		String[] refinementsLowercase = new String[] { "TYPE:image" };
		String[] refinementsBad = new String[] { "TYPE:BAD" };
		assertTrue(SolrUtils.checkTypeFacet(refinementsOk));
		assertTrue(SolrUtils.checkTypeFacet(refinementsLowercase));
		assertFalse(SolrUtils.checkTypeFacet(refinementsBad));
	}

	@Test
	public void testExists() {
		Object a = null;
		Object b = new Object();
		try {
			assertNotNull(SolrUtils.exists(Object.class, a));
			assertNotNull(SolrUtils.exists(Object.class, b));
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testAddResourceOrLiteralType() {
		SolrInputDocument doc = new SolrInputDocument();
		ResourceOrLiteralType rlt = prepareRLT();
		SolrUtils.addFieldFromResourceOrLiteral(doc, rlt,
				EdmLabel.PROXY_DC_DESCRIPTION);
		assertTrue(doc.containsKey(EdmLabel.PROXY_DC_DESCRIPTION.toString()));
		assertTrue(doc.containsKey(EdmLabel.PROXY_DC_DESCRIPTION.toString()
				+ ".en"));
		assertEquals(doc
				.getFieldValue(EdmLabel.PROXY_DC_DESCRIPTION.toString())
				.toString(), "test resource");
		assertEquals(
				doc.getFieldValue(
						EdmLabel.PROXY_DC_DESCRIPTION.toString() + ".en")
						.toString(), "test string");
	}

	@Test
	public void testResourceOrLiteralToArray() {
		ResourceOrLiteralType rlt = prepareRLT();
		String[] rltArray = SolrUtils.resourceOrLiteralToArray(rlt);
		assertNotNull(rltArray);
		String[] arr = new String[] { "test resource", "test string" };
		assertArrayEquals(arr, rltArray);
	}

	@Test
	public void testGetResourceOrLiteralString() {
		ResourceOrLiteralType rlt = prepareRLT();
		assertEquals("test resource", SolrUtils.getResourceOrLiteralString(rlt));
		rlt.setResource(null);
		assertEquals("test string", SolrUtils.getResourceOrLiteralString(rlt));
		rlt.setString(null);
		assertNull(SolrUtils.getResourceOrLiteralString(rlt));
	}
	
	@Test
	public void testResourceOrLiteralListToArray(){
		List<ResourceOrLiteralType> rltList = new ArrayList<ResourceOrLiteralType>();
		rltList.add(prepareRLT());
		String[] rltArray = SolrUtils.resourceOrLiteralListToArray(rltList);
		String[] arr = new String[] { "test resource", "test string" };
		assertArrayEquals(arr, rltArray);
		assertNotNull(SolrUtils.resourceOrLiteralListToArray(null));
	}

	@Test
	public void testLiteralListToArray(){
		List<LiteralType> ltList = new ArrayList<LiteralType>();
		ltList.add(prepareLT());
		String[] ltArray = SolrUtils.literalListToArray(ltList);
		String[] arr = new String[] { "test string" };
		assertArrayEquals(arr, ltArray);
		assertNotNull(SolrUtils.literalListToArray(null));
	}
	

	@Test
	public void testResourceListToArray(){
		List<ResourceType> rtList = new ArrayList<ResourceType>();
		rtList.add(prepareRT());
		String[] rtArray = SolrUtils.resourceListToArray(rtList);
		String[] arr = new String[] { "test resource" };
		assertArrayEquals(arr, rtArray);
		assertNotNull(SolrUtils.resourceListToArray(null));
	}
	
	@Test
	public void testGetLiteralString(){
		assertEquals("test string", SolrUtils.getLiteralString(prepareLT()));
	}
	
	@Test
	public void testGetResourceString(){
		assertEquals("test resource", SolrUtils.getResourceString(prepareRT()));
	}
	
	@Test
	public void testAddFieldFromLiteral(){
		SolrInputDocument doc = new SolrInputDocument();
		LiteralType lt = prepareLT();
		SolrUtils.addFieldFromLiteral(doc, lt,
				EdmLabel.PROXY_DC_DESCRIPTION);
		assertTrue(doc.containsKey(EdmLabel.PROXY_DC_DESCRIPTION.toString()
				+ ".en"));
		assertEquals(
				doc.getFieldValue(
						EdmLabel.PROXY_DC_DESCRIPTION.toString() + ".en")
						.toString(), "test string");
	}
	
	@Test
	public void testAddFieldFromEnum(){
		SolrInputDocument doc = new SolrInputDocument();
		SolrUtils.addFieldFromEnum(doc, "EN",
				EdmLabel.PROXY_DC_DESCRIPTION);
		assertTrue(doc.containsKey(EdmLabel.PROXY_DC_DESCRIPTION.toString()));
		assertEquals(
				doc.getFieldValue(
						EdmLabel.PROXY_DC_DESCRIPTION.toString())
						.toString(), "en");
	}
	
	@Test
	public void testGetImplementationClass(){
		assertTrue(SolrUtils.getImplementationClass(IdBean.class).isAssignableFrom(IdBeanImpl.class));
		assertTrue(SolrUtils.getImplementationClass(ApiBean.class).isAssignableFrom(ApiBeanImpl.class));
		assertTrue(SolrUtils.getImplementationClass(BriefBean.class).isAssignableFrom(BriefBeanImpl.class));
		assertNull(SolrUtils.getImplementationClass(FullBean.class));
	}
	
	@Test
	public void testGetPreviewURl(){
		RDF rdf = new RDF();
		List<Aggregation> aggregations = new ArrayList<Aggregation>();
		Aggregation aggregation = new Aggregation();
		aggregations.add(aggregation);
		rdf.setAggregationList(aggregations);
		assertNull(SolrUtils.getPreviewUrl(rdf));
		aggregations.clear();
		IsShownBy isShownBy = new IsShownBy();
		isShownBy.setResource("test isShownBy");
		aggregation.setIsShownBy(isShownBy);
		aggregations.add(aggregation);
		rdf.setAggregationList(aggregations);
		assertEquals("test isShownBy",SolrUtils.getPreviewUrl(rdf));
		_Object obj = new _Object();
		obj.setResource("test obj");
		aggregation.setObject(obj);
		aggregations.clear();
		aggregations.add(aggregation);
		rdf.setAggregationList(aggregations);
		assertEquals("test obj",SolrUtils.getPreviewUrl(rdf));
		
	}
	
	@Test
	public void testEscapeQueryChars(){
		assertEquals("http\\:\\/\\/ -",SolrUtils.escapeQuery("http:// -"));
	}
	
	private ResourceOrLiteralType prepareRLT() {
		ResourceOrLiteralType rlt = new ResourceOrLiteralType();
		Lang lang = new Lang();
		lang.setLang("en");
		rlt.setLang(lang);
		Resource res = new Resource();
		res.setResource("test resource");
		rlt.setResource(res);
		rlt.setString("test string");
		return rlt;
	}
	
	private ResourceType prepareRT(){
		ResourceType rt = new ResourceType();
		rt.setResource("test resource");
		return rt;
	}
	
	private LiteralType prepareLT(){
		LiteralType lt = new LiteralType();
		LiteralType.Lang lang = new LiteralType.Lang();
		lang.setLang("en");
		lt.setLang(lang);
		lt.setString("test string");
		return lt;
	}
}
