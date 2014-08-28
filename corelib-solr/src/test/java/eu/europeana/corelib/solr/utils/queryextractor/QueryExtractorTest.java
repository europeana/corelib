package eu.europeana.corelib.solr.utils.queryextractor;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import eu.europeana.corelib.solr.utils.queryextractor.QueryExtractor;
import eu.europeana.corelib.solr.utils.queryextractor.QueryToken;

public class QueryExtractorTest {

	@Test
	public void testExtractTerms() {
		assertEquals(Arrays.asList("spinoza"), new QueryExtractor("spinoza").extractTerms());
		assertEquals(Arrays.asList("den", "haag"), new QueryExtractor("den haag").extractTerms());
		assertEquals(Arrays.asList("den haag"), new QueryExtractor("den haag").extractTerms(true));
		assertEquals(Arrays.asList("den", "haag"), new QueryExtractor("den OR haag").extractTerms());
		assertEquals(Arrays.asList("den", "haag"), new QueryExtractor("den AND haag").extractTerms());
		assertEquals(Arrays.asList("den", "haag", "new", "york"), new QueryExtractor("(den haag) AND (new york)").extractTerms());
		assertEquals(Arrays.asList("den haag", "new york"), new QueryExtractor("(den haag) AND (new york)").extractTerms(true));
		assertEquals(Arrays.asList("Den Haag", "New York"), new QueryExtractor("(Den Haag) AND (New York)").extractTerms(true));
		assertEquals(Arrays.asList("den", "haag", "new", "york"), new QueryExtractor("(den haag) OR (new york)").extractTerms());
		assertEquals(Arrays.asList("den haag", "new york"), new QueryExtractor("(den haag) OR (new york)").extractTerms(true));
		assertEquals(Arrays.asList("den", "haag", "new", "york"), new QueryExtractor("den haag OR new york").extractTerms());
		assertEquals(Arrays.asList("den haag", "new york"), 
				new QueryExtractor(
					QueryNormalizer.normalizeBooleans("den haag OR new york")
				).extractTerms(true));
		assertEquals(Arrays.asList("den", "haag", "new", "york"), new QueryExtractor("(den haag) (new york)").extractTerms());
		assertEquals(Arrays.asList("den haag", "new york"), new QueryExtractor("(den haag) (new york)").extractTerms(true));
		assertEquals(Arrays.asList("den", "haag", "new", "york"), new QueryExtractor("den haag new york").extractTerms());
		assertEquals(Arrays.asList("den haag new york"),
				new QueryExtractor("den haag new york").extractTerms(true));
		assertEquals(Arrays.asList("den", "haag"), new QueryExtractor("den AND haag").extractTerms());
		assertEquals(Arrays.asList("den", "haag"),
				new QueryExtractor("den AND haag").extractTerms(true));
		assertEquals(Arrays.asList("den", "haag"), new QueryExtractor("den NOT haag").extractTerms());
		assertEquals(Arrays.asList("den", "haag"), new QueryExtractor("den NOT haag").extractTerms(true));
		assertEquals(Arrays.asList("den haag"), new QueryExtractor("\"den haag\"").extractTerms());
		assertEquals(Arrays.asList("den haag"), new QueryExtractor("\"den haag\"").extractTerms(true));
		assertEquals(Arrays.asList("den, haag"), new QueryExtractor("\"den, haag\"").extractTerms());
		assertEquals(Arrays.asList("den, haag"), new QueryExtractor("\"den, haag\"").extractTerms(true));
		assertEquals(Arrays.asList("den", "haag", "den haag"), 
				new QueryExtractor("den OR haag OR \"den haag\"").extractTerms());
		assertEquals(Arrays.asList("den", "haag", "den haag"), 
				new QueryExtractor("den OR haag OR \"den haag\"").extractTerms(true));
		assertEquals(Arrays.asList("den", "haag"), new QueryExtractor("name:(den OR haag)").extractTerms());
		assertEquals(Arrays.asList("den", "haag"), new QueryExtractor("name:(den OR haag)").extractTerms(true));
		assertEquals(Arrays.asList("den haag", "new york"),
				new QueryExtractor("name:(\"den haag\" OR \"new york\")").extractTerms());
		assertEquals(Arrays.asList("den haag", "new york"),
				new QueryExtractor("name:(\"den haag\" OR \"new york\")").extractTerms(true));
		assertEquals(Arrays.asList("spinoza"), new QueryExtractor("spinoza*").extractTerms());
		assertEquals(Arrays.asList("spinoza"), new QueryExtractor("spinoza*").extractTerms(true));
		assertEquals(Arrays.asList("den haag"), new QueryExtractor("\"den haag*\"").extractTerms());
		assertEquals(Arrays.asList("den haag"), new QueryExtractor("\"den haag*\"").extractTerms(true));
		assertEquals(Arrays.asList("spinoza"), new QueryExtractor("spinoza~").extractTerms());
		assertEquals(Arrays.asList("spinoza"), new QueryExtractor("spinoza~").extractTerms(true));
		assertEquals(Arrays.asList("Spinoza"), new QueryExtractor("Spinoza~").extractTerms());
		assertEquals(Arrays.asList("Spinoza"), new QueryExtractor("Spinoza~").extractTerms(true));
		assertEquals(Arrays.asList("spinoza"), new QueryExtractor("spinoza~0.3").extractTerms());
		assertEquals(Arrays.asList("Spinoza"), new QueryExtractor("Spinoza~0.3").extractTerms(true));
		assertEquals(Arrays.asList("Spinoza", "spinoza"), new QueryExtractor("Spinoza OR spinoza").extractTerms());
		assertEquals(Arrays.asList("Spinoza", "spinoza"), new QueryExtractor("Spinoza OR spinoza").extractTerms(true));
		assertEquals(Arrays.asList("apples"), new QueryExtractor("apples").extractTerms());
		assertEquals(Arrays.asList("apples"), new QueryExtractor("apples").extractTerms(true));
	}

	@Test
	public void testTermRangeQueries() {
		assertEquals(Arrays.asList("1989", "1990"), new QueryExtractor("year:[1989 TO 1990]").extractTerms());
		assertEquals(Arrays.asList("1989", "1990"), new QueryExtractor("year:[1989 TO 1990]").extractTerms(true));
		assertEquals(Arrays.asList("1989", "1990"), new QueryExtractor("year:[1989 TO 1990}").extractTerms());
		assertEquals(Arrays.asList("1989", "1990"), new QueryExtractor("year:[1989 TO 1990}").extractTerms(true));
		assertEquals(Arrays.asList("1989", "1990"), new QueryExtractor("year:{1989 TO 1990]").extractTerms());
		assertEquals(Arrays.asList("1989", "1990"), new QueryExtractor("year:{1989 TO 1990]").extractTerms(true));
		assertEquals(Arrays.asList("1989", "1990"), new QueryExtractor("year:{1989 TO 1990}").extractTerms());
		assertEquals(Arrays.asList("1989", "1990"), new QueryExtractor("year:{1989 TO 1990}").extractTerms(true));
		assertEquals(Arrays.asList("apple", "zoo"), new QueryExtractor("name:[apple TO zoo]").extractTerms());
		assertEquals(Arrays.asList("apple", "zoo"), new QueryExtractor("name:[apple TO zoo]").extractTerms(true));
		assertEquals(Arrays.asList("2013-03-15t19:58:36.43z", "2013-04-15t19:58:36.43z"),
			new QueryExtractor(
				"timestamp_created:[\"2013-03-15T19:58:36.43Z\" TO \"2013-04-15T19:58:36.43Z\"]").extractTerms());
		assertEquals(Arrays.asList("2013-03-15t19:58:36.43z", "2013-04-15t19:58:36.43z"),
			new QueryExtractor(
				"timestamp_created:[2013-03-15T19:58:36.43Z TO 2013-04-15T19:58:36.43Z]").extractTerms());
	}

	@Test
	public void testPhraseTerms() {
		assertEquals(Arrays.asList("den, haag"), new QueryExtractor("\"den, haag\"").extractTerms());
		assertEquals(Arrays.asList("den haag"), new QueryExtractor("\"den haag\"").extractTerms());
	}

	@Test
	public void testExtractInfo() {
		List<QueryToken> tokens = new QueryExtractor("\"den, haag\"").extractInfo(true);
		assertEquals(1, tokens.size());
		assertEquals("den, haag", tokens.get(0).getPosition().getOriginal());
		assertEquals(1, tokens.get(0).getPosition().getStart());
		assertEquals(10, tokens.get(0).getPosition().getEnd());
		assertEquals(QueryType.PHRASE, tokens.get(0).getType());

		tokens = new QueryExtractor("\"den haag\"").extractInfo(true);
		assertEquals(1, tokens.size());
		assertEquals("den haag", tokens.get(0).getPosition().getOriginal());
		assertEquals(1, tokens.get(0).getPosition().getStart());
		assertEquals(9, tokens.get(0).getPosition().getEnd());
		assertEquals(QueryType.PHRASE, tokens.get(0).getType());
	}

	@Test
	public void testInjectAlternatives() {
		String query = "la joconde";
		String modifiedQuery = query;
		List<String> alternatives = Arrays.asList("la gioconda", "mona lisa");
		QueryExtractor queryExtractor = new QueryExtractor(query);
		List<QueryToken> tokens = queryExtractor.extractInfo(true);
		List<QueryModification> queryModifications = new ArrayList<QueryModification>();
		for (QueryToken token : tokens) {
			QueryModification queryModification = token.createModification(query, alternatives);
			if (queryModification != null) {
				queryModifications.add(queryModification);
			}
		}
		modifiedQuery = queryExtractor.rewrite(queryModifications);
		assertEquals("(la joconde) OR \"la gioconda\" OR \"mona lisa\"", modifiedQuery);

		queryExtractor = new QueryExtractor("(mona lisa) OR (den haag)");
		queryModifications = new ArrayList<QueryModification>();
		queryModifications.add(new QueryModification(1, 10, "((mona lisa) OR \"La Joconde\" OR \"Gioconda\" OR \"Mona Lisa\")"));
		queryModifications.add(new QueryModification(16, 24, "((den haag) OR \"The Hague\" OR \"Den Haag\" OR \"Haag\" OR \"La Haye\" OR \"L'Aia\" OR \"Hága\")"));
		modifiedQuery = queryExtractor.rewrite(queryModifications);
		assertEquals("(((mona lisa) OR \"La Joconde\" OR \"Gioconda\" OR \"Mona Lisa\")) OR (((den haag) OR \"The Hague\" "
				+ "OR \"Den Haag\" OR \"Haag\" OR \"La Haye\" OR \"L'Aia\" OR \"Hága\"))", modifiedQuery);

	}
}
