package eu.europeana.corelib.solr.utils.queryextractor;

import static org.junit.Assert.*;

import org.junit.Test;

import eu.europeana.corelib.solr.utils.queryextractor.QueryNormalizer;

public class QueryNormalizerTest {

	@Test
	public void test() {
		String[] rawQueries = new String[]{
			"la joconde OR la gioconda OR mona lisa",
			"paris",
			"paris OR rome",
			"paris OR la joconde",
		};
		String[] normalizedQueries = new String[]{
			"(la joconde) OR (la gioconda) OR (mona lisa)",
			"paris",
			"paris OR rome",
			"paris OR (la joconde)",
		};
		for (int i = 0; i < rawQueries.length; i++) {
			assertEquals(
				normalizedQueries[i],
				QueryNormalizer.normalizeBooleans(rawQueries[i])
			);
		}
	}

}
