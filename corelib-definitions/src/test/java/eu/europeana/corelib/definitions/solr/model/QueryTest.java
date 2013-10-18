package eu.europeana.corelib.definitions.solr.model;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import eu.europeana.corelib.definitions.solr.Facet;

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

		String expected = "RIGHTS:(" + singleRight + " AND " + rights + ")";
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
		List<Facet> facetList = Arrays.asList(query.getFacets());
		assertTrue(facetList.contains(Facet.RIGHTS));
		assertEquals(Facet.values().length, facetList.size());

		query.removeFacet(Facet.RIGHTS);
		facetList = Arrays.asList(query.getFacets());
		assertFalse(facetList.contains(Facet.RIGHTS));
		assertEquals(Facet.values().length - 1, facetList.size());
	}

	/**
	 * Testing setFacet()
	 */
	@Test
	public void testSetFacet() {
		Query query = new Query("*:*");
		List<Facet> facetList = Arrays.asList(query.getFacets());
		assertTrue(facetList.contains(Facet.RIGHTS));
		assertEquals(Facet.values().length, facetList.size());

		query.setFacet(Facet.RIGHTS);
		facetList = Arrays.asList(query.getFacets());
		assertTrue(facetList.contains(Facet.RIGHTS));
		assertEquals(1, facetList.size());
	}
}
