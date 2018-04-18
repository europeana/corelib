package eu.europeana.corelib.definitions.solr.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.europeana.corelib.definitions.solr.SolrFacetType;
import org.junit.Test;

public class QueryTest {

	private static final String RIGHTS = "(\"http://creativecommons.org/publicdomain/mark/\""
			+ " OR \"http://creativecommons.org/publicdomain/zero\""
			+ " OR \"http://creativecommons.org/licenses/by/\""
			+ " OR \"http://creativecommons.org/licenses/by-sa/\")";
	private static final String SINGLE_RIGHT = "\"http://creativecommons.org/publicdomain/mark/1.0/\"";
	private static final String ANOTHER_RIGHT = "\"http://www.europeana.eu/rights/rr-f/\"";

	private Map<String, String> valueReplacements = new HashMap<>();

	/**
	 * Testing refinements with Query having minimal number of setters
	 */
	@Test
	public void testMinimalQuery() {

		String[] refinements = new String[] {
			"RIGHTS:" + RIGHTS
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
			"RIGHTS:" + RIGHTS
		};

		// Testing the full Query object
		Query query = new Query("*:*")
			.setRefinements(refinements)
			.setPageSize(10)
			.setStart(0)
			.setParameter("facet.mincount", "1")
			.setParameter("sort", "relevance")
			.setProduceFacetUnion(true)
			.setSpellcheckAllowed(false)
		;

		assertEquals(refinements[0], query.getRefinements()[0]);
		assertEquals("{!tag=RIGHTS}" + refinements[0], query.getRefinements(true)[0]);
	}

	/**
	 * Testing multiple rights refinements
	 */
	@Test
	public void testMultipleRights() {
		String[] refinements = new String[] {
			"RIGHTS:" + SINGLE_RIGHT,
			"RIGHTS:" + RIGHTS
		};

		Query query = new Query("*:*").setRefinements(refinements);

		String expected = "RIGHTS:(" + SINGLE_RIGHT + " OR " + RIGHTS + ")";
		assertEquals("{!tag=RIGHTS}" + expected, query.getRefinements(true)[0]);
	}

	/**
	 * Testing refinements containing single right + REUSABILITY:Free
	 */
	@Test
	public void testSingleRightWithReusability() {
		valueReplacements.put("REUSABILITY:Free", "RIGHTS:" + RIGHTS);

		String[] refinements = new String[] {
			"RIGHTS:" + SINGLE_RIGHT,
			"REUSABILITY:Free"
		};

		Query query = new Query("*:*").setRefinements(refinements).setValueReplacements(valueReplacements);
		String[] results = query.getRefinements(true);
		assertEquals("{!tag=RIGHTS}RIGHTS:" + SINGLE_RIGHT, results[0]);
		assertEquals("{!tag=REUSABILITY}RIGHTS:" + RIGHTS, results[1]);
	}

	/**
	 * Tesing refinements containing multiple rights + REUSABILITY:Free
	 */
	@Test
	public void testMultipleRightsWithReusability() {
		valueReplacements.put("REUSABILITY:Free", "RIGHTS:" + RIGHTS);

		String[] refinements = new String[] {
			"RIGHTS:" + SINGLE_RIGHT,
			"RIGHTS:" + ANOTHER_RIGHT,
			"REUSABILITY:Free"
		};

		Query query = new Query("*:*").setRefinements(refinements).setValueReplacements(valueReplacements);

		String expected = "RIGHTS:(" + SINGLE_RIGHT + " OR " + ANOTHER_RIGHT + ")";
		String[] results = query.getRefinements(true);
		assertEquals("{!tag=RIGHTS}" + expected, query.getRefinements(true)[0]);
		assertEquals("{!tag=REUSABILITY}RIGHTS:" + RIGHTS, results[1]);
	}

	/**
	 * Testing removeSolrFacet()
	 */
	@Test
	public void testRemoveFacet() {
		Query query = new Query("*:*");
		query.setDefaultSolrFacets();
		List<String> facetList = query.getSolrFacets();
		assertTrue(facetList.contains(SolrFacetType.RIGHTS.name()));
		assertEquals(SolrFacetType.values().length, facetList.size());

		query.removeSolrFacet(SolrFacetType.RIGHTS);
		facetList = query.getSolrFacets();
		assertFalse(facetList.contains(SolrFacetType.RIGHTS.toString()));
		assertEquals(SolrFacetType.values().length - 1, facetList.size());

		query = new Query("*:*");
		query.setDefaultSolrFacets();
		facetList = query.getSolrFacets();
		assertTrue(facetList.contains("RIGHTS"));
		assertEquals(SolrFacetType.values().length, facetList.size());

		query.removeSolrFacet("RIGHTS");
		facetList = query.getSolrFacets();
		assertFalse(facetList.contains("RIGHTS"));
		assertEquals(SolrFacetType.values().length - 1, facetList.size());
	}

	/**
	 * Testing setSolrFacet()
	 */
	@Test
	public void testSetFacet1() {
		Query query = new Query("*:*");
		List<String> facetList;

		query.setSolrFacets("RIGHTS");
		facetList = query.getSolrFacets();
		assertTrue("should contain RIGHTS", facetList.contains("RIGHTS"));
		assertFalse("should not contain YEAR", facetList.contains("YEAR"));
		assertEquals(1, facetList.size());
	}

	/**
	 * Testing setSolrFacet()
	 */
	@Test
	public void testSetFacet() {
		Query query = new Query("*:*");
		List<String> facetList;

		List<String> newFacets = new ArrayList<>();
		newFacets.add("RIGHTS");
		query.setSolrFacets(newFacets);
		facetList = query.getSolrFacets();
		assertTrue("should contain rights", facetList.contains("RIGHTS"));
		assertFalse("should not contain YEAR", facetList.contains("YEAR"));
		assertEquals(1, facetList.size());

		newFacets = new ArrayList<>();
		newFacets.add("RIGHTS");
		newFacets.add("YEAR");
		query.setSolrFacets(newFacets);
		facetList = query.getSolrFacets();
		assertTrue("should contain rights", facetList.contains("RIGHTS"));
		assertTrue("should contain YEAR", facetList.contains("YEAR"));
		assertFalse("should not contain UGC", facetList.contains("UGC"));
		assertEquals(2, facetList.size());

		// technical facets
		newFacets = new ArrayList<>();
		newFacets.add("SOUND_HQ");
		newFacets.add("IMAGE_COLOUR");
		newFacets.add("MEDIA"); // heh heh heh heh
		query.setTechnicalFacets(newFacets);
		facetList = query.getTechnicalFacets();
		assertFalse("should NOT contain mimetype", facetList.contains("MIME_TYPE"));
		assertFalse("should NOT contain is_fulltext", facetList.contains("TEXT_FULLTEXT"));
		assertFalse("should NOT contain language", facetList.contains("LANGUAGE"));
		assertFalse("should NOT contain has-media", facetList.contains("HAS_MEDIA")); // instinker!
		assertTrue("should contain image colour", facetList.contains("IMAGE_COLOUR"));
		assertTrue("should contain sound HQ", facetList.contains("SOUND_HQ"));
		assertFalse("should NOT contain video duration", facetList.contains("VIDEO_DURATION"));
		assertFalse("should NOT contain proxy_dc_contributor", facetList.contains("proxy_dc_contributor"));
		assertEquals(2, facetList.size());


		// mixed case
		newFacets = new ArrayList<>();
		newFacets.add("LANGUAGE");
		newFacets.add("TYPE");
		newFacets.add("MEDIA");
		query.setSolrFacets(newFacets);
		newFacets = new ArrayList<>();
		newFacets.add("VIDEO_DURATION");
		newFacets.add("THUMBNAIL"); // nogmaals: heh heh heh ...
		query.setTechnicalFacets(newFacets);
		facetList = query.getSolrFacets();
		facetList.addAll(query.getTechnicalFacets());
		assertTrue("should contain language", facetList.contains("LANGUAGE"));
		assertFalse("should NOT contain data provider", facetList.contains("DATA_PROVIDER"));
		assertTrue("should contain has_media", facetList.contains("has_media")); // yes, bc it's added as SOLR facet
		assertFalse("should NOT contain has_thumbnails", facetList.contains("has_thumbnails")); // instinker!
		assertFalse("should NOT contain mimetype", facetList.contains("MIME_TYPE"));
		assertFalse("should NOT contain is_fulltext", facetList.contains("is_fulltext"));
		assertTrue("should contain video duration", facetList.contains("VIDEO_DURATION"));
		assertTrue("should contain type", facetList.contains("TYPE"));
		assertEquals(4, facetList.size());

	}

	@Test
	public void testDefaultFacet() {
		List<String> facetList;

		List<String> newFacets;

		// test DEFAULT
		Query query = new Query("*:*");
		query.setDefaultSolrFacets();
		facetList = query.getSolrFacets();
		assertTrue("should contain rights", facetList.contains("RIGHTS"));
		assertEquals(13, facetList.size());

		// test DEFAULT ++
		query = new Query("*:*");
		query.setDefaultSolrFacets();
		newFacets = new ArrayList<>();
		newFacets.add("proxy_dc_contributor");
		newFacets.add("pl_skos_altLabel");
		query.addSolrFacets(newFacets);
		facetList = query.getSolrFacets();
		assertTrue("should contain rights", facetList.contains("RIGHTS"));
		assertTrue("should contain proxy_dc_contributor", facetList.contains("proxy_dc_contributor"));
		assertTrue("should contain pl_skos_altLabel", facetList.contains("pl_skos_altLabel"));
		assertEquals(15, facetList.size());

		// test adding technical facets
		newFacets = new ArrayList<>();
		newFacets.add("VIDEO_DURATION");
		newFacets.add("COLOURPALETTE");
		query.setTechnicalFacets(newFacets);
		facetList = query.getTechnicalFacets();
		assertTrue("should contain video duration", facetList.contains("VIDEO_DURATION"));
		assertTrue("should contain colour palette", facetList.contains("COLOURPALETTE"));
		assertEquals(2, facetList.size());

		// test adding default technical facets
		query.setDefaultTechnicalFacets();
		facetList = query.getTechnicalFacets();
		assertTrue("should contain mimetype", facetList.contains("MIME_TYPE"));
		assertTrue("should contain image size", facetList.contains("IMAGE_SIZE"));
		assertTrue("should contain image aspectratio", facetList.contains("IMAGE_ASPECTRATIO"));
		assertTrue("should contain image colour", facetList.contains("IMAGE_COLOUR"));
		assertTrue("should contain colour palette", facetList.contains("COLOURPALETTE"));
		assertTrue("should contain video duration", facetList.contains("VIDEO_DURATION"));
		assertTrue("should contain video HD", facetList.contains("VIDEO_HD"));
		assertTrue("should contain sound HQ", facetList.contains("SOUND_HQ"));
		assertTrue("should contain sound duration", facetList.contains("SOUND_DURATION"));
		assertEquals(9, facetList.size());

		// test mixed facets
		newFacets = new ArrayList<>();
		newFacets.add("LANGUAGE");
		newFacets.add("RIGHTS");
		newFacets.add("proxy_dc_contributor");
		query.setSolrFacets(newFacets);
		newFacets = new ArrayList<>();
		newFacets.add("SOUND_HQ");
		newFacets.add("IMAGE_COLOUR");
		query.setTechnicalFacets(newFacets);
		facetList = query.getSolrFacets();
		facetList.addAll(query.getTechnicalFacets());
		assertTrue("should contain language", facetList.contains("LANGUAGE"));
		assertTrue("should contain sound HQ", facetList.contains("SOUND_HQ"));
		assertTrue("should contain rights", facetList.contains("RIGHTS"));
		assertTrue("should contain image colour", facetList.contains("IMAGE_COLOUR"));
		assertTrue("should contain proxy_dc_contributor", facetList.contains("proxy_dc_contributor"));
		assertEquals(5, facetList.size());

		// test The Works
		query.setDefaultSolrFacets();
		query.setDefaultTechnicalFacets();
		facetList = query.getSolrFacets();
		facetList.addAll(query.getTechnicalFacets());
		assertEquals(22, facetList.size());
	}

	@Test
	public void testDefaultFacetAfterRemoving() {
		Query query = new Query("*:*");
		List<String> facetList;
		query.setDefaultSolrFacets();
		facetList = query.getSolrFacets();
		assertTrue("should contain rights", facetList.contains("RIGHTS"));
		assertEquals(13, facetList.size());

		query.removeSolrFacet("RIGHTS");
		assertFalse("should NOT contain rights", facetList.contains("RIGHTS"));
		assertEquals(12, facetList.size());
	}

	@Test
	public void testSorts() {
		// single field
		Query q = new Query("*");
		q.setSort("score ascending");
		List<QuerySort> sorts = q.getSorts();
		assertEquals(1, sorts.size());
		assertTrue(q.toString().contains("sorts=score asc"));

		// multiple sort fields with different separators and extra spaces
		q.setSort(" id  ascending+timestamp_update , europeana_id asc  COMPLETENESS DESC ");
		sorts = q.getSorts();
		assertEquals(4, sorts.size());

		assertEquals("id", sorts.get(0).getSortField());
		assertEquals(QuerySort.ORDER_ASC, sorts.get(0).getSortOrder());
		assertEquals("timestamp_update", sorts.get(1).getSortField());
		assertEquals(QuerySort.ORDER_DESC, sorts.get(1).getSortOrder());
		assertEquals("europeana_id", sorts.get(2).getSortField());
		assertEquals(QuerySort.ORDER_ASC, sorts.get(2).getSortOrder());
		assertEquals("COMPLETENESS", sorts.get(3).getSortField());
		assertEquals(QuerySort.ORDER_DESC, sorts.get(3).getSortOrder());
		assertTrue(q.toString().contains("id asc,timestamp_update desc,europeana_id asc,COMPLETENESS desc"));

		// clear sorts
		q.setSort("");
		assertEquals(0, q.getSorts().size());
	}

}
