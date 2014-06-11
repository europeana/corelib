package eu.europeana.corelib.solr.utils;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class QueryExtractorTest {

	@Test
	public void testParseQuery() {
		// assertEquals(Arrays.asList("spinoza"), QueryExtractor.extractTerms("spinoza"));
		assertEquals(Arrays.asList("den", "haag"), QueryExtractor.extractTerms("den haag"));
		assertEquals(Arrays.asList("den", "haag"), QueryExtractor.extractTerms("den OR haag"));
		assertEquals(Arrays.asList("den", "haag"), QueryExtractor.extractTerms("den AND haag"));
		assertEquals(Arrays.asList("den", "haag", "new", "york"), QueryExtractor.extractTerms("(den haag) AND (new york)"));
		assertEquals(Arrays.asList("den", "haag", "new", "york"), QueryExtractor.extractTerms("(den haag) OR (new york)"));
		assertEquals(Arrays.asList("den", "haag", "new", "york"), QueryExtractor.extractTerms("den haag OR new york"));
		assertEquals(Arrays.asList("den", "haag", "new", "york"), QueryExtractor.extractTerms("(den haag) (new york)"));
		assertEquals(Arrays.asList("den", "haag", "new", "york"), QueryExtractor.extractTerms("den haag new york"));
		/*
		assertEquals(Arrays.asList("den", "haag"), QueryExtractor.extractTerms("den AND haag"));
		assertEquals(Arrays.asList("den", "haag"), QueryExtractor.extractTerms("den NOT haag"));
		assertEquals(Arrays.asList("den haag"), QueryExtractor.extractTerms("\"den haag\""));
		assertEquals(Arrays.asList("den haag"), QueryExtractor.extractTerms("\"den, haag\""));
		assertEquals(Arrays.asList("den", "haag", "den haag"), 
				QueryExtractor.extractTerms("den OR haag OR \"den haag\""));
		assertEquals(Arrays.asList("den", "haag"), QueryExtractor.extractTerms("name:(den OR haag)"));
		assertEquals(Arrays.asList("den haag", "new york"),
				QueryExtractor.extractTerms("name:(\"den haag\" OR \"new york\")"));
		assertEquals(Arrays.asList("spinoza"), QueryExtractor.extractTerms("spinoza*"));
		assertEquals(Arrays.asList("den haag"), QueryExtractor.extractTerms("\"den haag*\""));
		assertEquals(Arrays.asList("spinoza"), QueryExtractor.extractTerms("spinoza~"));
		assertEquals(Arrays.asList("spinoza"), QueryExtractor.extractTerms("Spinoza~"));
		assertEquals(Arrays.asList("spinoza"), QueryExtractor.extractTerms("spinoza~0.3"));
		assertEquals(Arrays.asList("1989", "1990"), QueryExtractor.extractTerms("year:[1989 TO 1990]"));
		assertEquals(Arrays.asList("1989", "1990"), QueryExtractor.extractTerms("year:[1989 TO 1990}"));
		assertEquals(Arrays.asList("1989", "1990"), QueryExtractor.extractTerms("year:{1989 TO 1990]"));
		assertEquals(Arrays.asList("1989", "1990"), QueryExtractor.extractTerms("year:{1989 TO 1990}"));
		assertEquals(Arrays.asList("apple", "zoo"), QueryExtractor.extractTerms("name:[apple TO zoo]"));
		assertEquals(Arrays.asList("2013-03-15t19:58:36.43z", "2013-04-15t19:58:36.43z"),
			QueryExtractor.extractTerms(
				"timestamp_created:[\"2013-03-15T19:58:36.43Z\" TO \"2013-04-15T19:58:36.43Z\"]"));
		assertEquals(Arrays.asList("2013-03-15t19:58:36.43z", "2013-04-15t19:58:36.43z"),
			QueryExtractor.extractTerms(
				"timestamp_created:[2013-03-15T19:58:36.43Z TO 2013-04-15T19:58:36.43Z]"));
		assertEquals(Arrays.asList("spinoza", "spinoza"), QueryExtractor.extractTerms("Spinoza OR spinoza"));
		assertEquals(Arrays.asList("apples"), QueryExtractor.extractTerms("apples"));
		*/
	}


}
