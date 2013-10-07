package eu.europeana.corelib.definitions.solr.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class QueryTest {

	@Test
	public void testRefinements() {
		String rights = "(\"http://creativecommons.org/publicdomain/mark/\""
				+ " OR \"http://creativecommons.org/publicdomain/zero\""
				+ " OR \"http://creativecommons.org/licenses/by/\""
				+ " OR \"http://creativecommons.org/licenses/by-sa/\")";

		String[] refinements1 = new String[] {"RIGHTS:" + rights};

		// Testing the simple case
		Query query = new Query("*:*").setRefinements(refinements1);
		assertEquals(refinements1[0], query.getRefinements()[0]);
		assertEquals("{!tag=RIGHTS}" + refinements1[0], query.getRefinements(true)[0]);

		// Testing the full Query object
		query = new Query("*:*")
			.setRefinements(refinements1)
			.setPageSize(10)
			.setStart(0)
			.setParameter("facet.mincount", "1")
			.setParameter("sort", "relevance")
			.setProduceFacetUnion(true)
			.setAllowSpellcheck(false)
		;
		assertEquals(refinements1[0], query.getRefinements()[0]);
		assertEquals("{!tag=RIGHTS}" + refinements1[0], query.getRefinements(true)[0]);

		// Testing the tricky Reusability-related refinements
		String singleRight = "\"http://creativecommons.org/publicdomain/mark/1.0/\"";
		String[] refinements3 = new String[] {"RIGHTS:" + singleRight, "RIGHTS:" + rights};

		query = new Query("*:*").setRefinements(refinements3);
		String expected = "RIGHTS:(" + singleRight + " AND " + rights + ")";
		// assertEquals(expected, query.getRefinements()[0]);
		assertEquals("{!tag=RIGHTS}" + expected, query.getRefinements(true)[0]);

	}

}
