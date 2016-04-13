package eu.europeana.corelib.definitions.solr.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.europeana.corelib.definitions.solr.SolrFacetType;
import org.junit.Test;

public class QueryTest {

	private String rights = "(\"http://creativecommons.org/publicdomain/mark/\""
			+ " OR \"http://creativecommons.org/publicdomain/zero\""
			+ " OR \"http://creativecommons.org/licenses/by/\""
			+ " OR \"http://creativecommons.org/licenses/by-sa/\")";
	private String singleRight = "\"http://creativecommons.org/publicdomain/mark/1.0/\"";
	private String anotherRight = "\"http://www.europeana.eu/rights/rr-f/\"";

	private Map<String, String> valueReplacements = new HashMap<String, String>();

	/**
	 * Testing refinements with Query having minimal number of setters
	 */
	@Test
	public void testMinimalQuery() {

		String[] refinements = new String[] {
			"RIGHTS:" + rights
		};

		Query query = new Query("*:*").setRefinements(refinements);

		assertEquals(refinements[0], query.getRefinements()[0]);
		assertEquals("{!tag=RIGHTS}" + refinements[0], query.getRefinements(true)[0]);
	}

	/**
	 * Testing refinements with Query having lots of setters
	 */
	@Test
	public void testQueryWithSetters() {

		String[] refinements = new String[] {
			"RIGHTS:" + rights
		};

		// Testing the full Query object
		Query query = new Query("*:*")
			.setRefinements(refinements)
			.setPageSize(10)
			.setStart(0)
			.setParameter("facet.mincount", "1")
			.setParameter("sort", "relevance")
			.setProduceFacetUnion(true)
			.setAllowSpellcheck(false)
		;
		
		assertEquals(refinements[0], query.getRefinements()[0]);
		assertEquals("{!tag=RIGHTS}" + refinements[0], query.getRefinements(true)[0]);
	}

	/**
	 * Tesing multiple rights refinements
	 */
	@Test
	public void testMultipleRights() {
		String[] refinements = new String[] {
			"RIGHTS:" + singleRight,
			"RIGHTS:" + rights
		};

		Query query = new Query("*:*").setRefinements(refinements);

		String expected = "RIGHTS:(" + singleRight + " OR " + rights + ")";
		assertEquals("{!tag=RIGHTS}" + expected, query.getRefinements(true)[0]);
	}

	/**
	 * Tesing refinements containing single right + REUSABILITY:Free
	 */
	@Test
	public void testSingleRightWithReusability() {
		valueReplacements.put("REUSABILITY:Free", "RIGHTS:" + rights);

		String[] refinements = new String[] {
			"RIGHTS:" + singleRight,
			"REUSABILITY:Free"
		};

		Query query = new Query("*:*").setRefinements(refinements).setValueReplacements(valueReplacements);
		String[] results = query.getRefinements(true);
		assertEquals("{!tag=RIGHTS}RIGHTS:" + singleRight, results[0]);
		assertEquals("{!tag=REUSABILITY}RIGHTS:" + rights, results[1]);
	}

	/**
	 * Tesing refinements containing multiple rights + REUSABILITY:Free
	 */
	@Test
	public void testMultipleRightsWithReusability() {
		valueReplacements.put("REUSABILITY:Free", "RIGHTS:" + rights);

		String[] refinements = new String[] {
			"RIGHTS:" + singleRight,
			"RIGHTS:" + anotherRight,
			"REUSABILITY:Free"
		};

		Query query = new Query("*:*").setRefinements(refinements).setValueReplacements(valueReplacements);

		String expected = "RIGHTS:(" + singleRight + " OR " + anotherRight + ")";
		String[] results = query.getRefinements(true);
		assertEquals("{!tag=RIGHTS}" + expected, query.getRefinements(true)[0]);
		assertEquals("{!tag=REUSABILITY}RIGHTS:" + rights, results[1]);
	}

	/**
	 * Testing removeFacet()
	 */
	@Test
	public void testRemoveFacet() {
		Query query = new Query("*:*");
		List<String> facetList = query.getFacets();
		assertTrue(facetList.contains(SolrFacetType.RIGHTS.name()));
		assertEquals(SolrFacetType.values().length, facetList.size());

		query.removeFacet(SolrFacetType.RIGHTS);
		facetList = query.getFacets();
		assertFalse(facetList.contains(SolrFacetType.RIGHTS.toString()));
		assertEquals(SolrFacetType.values().length - 1, facetList.size());

		query = new Query("*:*");
		facetList = query.getFacets();
		assertTrue(facetList.contains("RIGHTS"));
		assertEquals(SolrFacetType.values().length, facetList.size());

		query.removeFacet("RIGHTS");
		facetList = query.getFacets();
		assertFalse(facetList.contains("RIGHTS"));
		assertEquals(SolrFacetType.values().length - 1, facetList.size());
	}

	/**
	 * Testing setFacet()
	 */
	@Test
	public void testSetFacet1() {
		Query query = new Query("*:*");
		List<String> facetList;

		query.setFacets("RIGHTS");
		facetList = query.getFacets();
		assertTrue("should contain RIGHTS", facetList.contains("RIGHTS"));
		assertFalse("should not contain YEAR", facetList.contains("YEAR"));
		assertEquals(1, facetList.size());
	}

	/**
	 * Testing setFacet()
	 */
	@Test
	public void testSetFacet() {
		Query query = new Query("*:*");
		List<String> facetList;

		facetList = query.getFacets();
		assertTrue(facetList.contains("RIGHTS"));
		assertEquals(SolrFacetType.values().length, facetList.size());

		query.setFacets("RIGHTS", "YEAR");
		facetList = query.getFacets();
		assertTrue("should contain RIGHTS", facetList.contains("RIGHTS"));
		assertTrue("should not contain YEAR", facetList.contains("YEAR"));
		assertFalse("should not contain UGC", facetList.contains("UGC"));
		assertEquals(2, facetList.size());

		List<String> newFacets = new ArrayList<String>();
		newFacets.add("RIGHTS");
		query.setFacets(newFacets);
		facetList = query.getFacets();
		assertTrue("should contain rights", facetList.contains("RIGHTS"));
		assertFalse("should not contain YEAR", facetList.contains("YEAR"));
		assertEquals(1, facetList.size());

		newFacets = new ArrayList<String>();
		newFacets.add("RIGHTS");
		newFacets.add("YEAR");
		query.setFacets(newFacets);
		facetList = query.getFacets();
		assertTrue("should contain rights", facetList.contains("RIGHTS"));
		assertTrue("should not contain YEAR", facetList.contains("YEAR"));
		assertFalse("should not contain UGC", facetList.contains("UGC"));
		assertEquals(2, facetList.size());
	}

	@Test
	public void testDefaultFacet() {
		Query query = new Query("*:*");
		List<String> facetList;

		List<String> newFacets;

		// test DEFAULT
		newFacets = new ArrayList<String>();
		newFacets.add("DEFAULT");
		query.setFacets(newFacets);
		facetList = query.getFacets();
		assertTrue("should contain rights", facetList.contains("RIGHTS"));
		assertEquals(11, facetList.size());

		// test DEFAULT + something else
		newFacets = new ArrayList<String>();
		newFacets.add("DEFAULT");
		newFacets.add("proxy_dc_contributor");
		query.setFacets(newFacets);
		facetList = query.getFacets();
		assertTrue("should contain rights", facetList.contains("RIGHTS"));
		assertTrue("should contain rights", facetList.contains("proxy_dc_contributor"));
		assertEquals(12, facetList.size());
	}

	@Test
	public void testDefaultFacetAfterRemoving() {
		Query query = new Query("*:*");
		List<String> facetList;

		List<String> newFacets = new ArrayList<String>();
		newFacets.add("DEFAULT");
		query.setFacets(newFacets);
		facetList = query.getFacets();
		assertTrue("should contain rights", facetList.contains("RIGHTS"));
		assertEquals(11, facetList.size());

		query.removeFacet("RIGHTS");

		query = new Query("*:*");

		newFacets = new ArrayList<String>();
		newFacets.add("DEFAULT");
		query.setFacets(newFacets);
		facetList = query.getFacets();
		assertTrue("should contain rights", facetList.contains("RIGHTS"));
		assertEquals(11, facetList.size());
	}
}
