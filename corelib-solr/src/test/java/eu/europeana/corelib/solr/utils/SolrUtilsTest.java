package eu.europeana.corelib.solr.utils;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class SolrUtilsTest {

	@Test
	/**
	 * Tests the translateQuery function.
	 */
	public void translateQueryTest() {
		Map<String, String> identicalQueries = new HashMap<String, String>(){{
			put("simpleQuery", "simpleQuery");
			put("proxy_dc_coverage:ok", "proxy_dc_coverage:ok");
		}};
		for (String query : identicalQueries.keySet()) {
			assertEquals(identicalQueries.get(query), SolrUtils.translateQuery(query));
		}

		Map<String, String> aggregatedQueries = new HashMap<String, String>(){{
			put("title:*:*", "title:*");
			put("who:*:*", "who:*");
			put("what:*:*", "what:*");
			put("where:*:*", "where:*");
			put("when:*:*", "when:*");
		}};
		for (String query : aggregatedQueries.keySet()) {
			assertEquals(aggregatedQueries.get(query), SolrUtils.translateQuery(query));
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
}
