package eu.europeana.corelib.solr.utils;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class QueryExtractorTest {

	@Test
	public void testParseQuery() {
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
		assertEquals(Arrays.asList("den haag"), new QueryExtractor("\"den, haag\"").extractTerms());
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
		assertEquals(Arrays.asList("Spinoza", "spinoza"), new QueryExtractor("Spinoza OR spinoza").extractTerms());
		assertEquals(Arrays.asList("Spinoza", "spinoza"), new QueryExtractor("Spinoza OR spinoza").extractTerms(true));
		assertEquals(Arrays.asList("apples"), new QueryExtractor("apples").extractTerms());
		assertEquals(Arrays.asList("apples"), new QueryExtractor("apples").extractTerms(true));
	}
}
