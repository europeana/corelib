package eu.europeana.corelib.definitions.solr.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class QueryFacetTest {

	@Test
	public void test() {
		QueryFacet queryFacet = new QueryFacet("query AND string", "TEST:test", "REUSABILITY");
		assertEquals("query AND string", queryFacet.getQuery());
		assertEquals("TEST:test", queryFacet.getId());
		assertEquals("REUSABILITY", queryFacet.getExclusion());
		assertEquals("{!id=TEST:test ex=REUSABILITY}query AND string", queryFacet.getQueryFacetString());
	}

}
