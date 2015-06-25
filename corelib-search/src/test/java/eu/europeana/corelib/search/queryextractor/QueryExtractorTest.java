package eu.europeana.corelib.search.queryextractor;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class QueryExtractorTest {

    @Test
    public void testExtractTerms() {
        assertEquals(new ArrayList<String>(), new QueryExtractor("*:*").extractTerms());
        assertEquals(Collections.singletonList("spinoza"), new QueryExtractor("spinoza").extractTerms());
        assertEquals(Arrays.asList("den", "haag"), new QueryExtractor("den haag").extractTerms());
        assertEquals(Collections.singletonList("den haag"), new QueryExtractor("den haag").extractTerms(true));
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
        assertEquals(Collections.singletonList("den haag new york"),
                new QueryExtractor("den haag new york").extractTerms(true));
        assertEquals(Arrays.asList("den", "haag"), new QueryExtractor("den AND haag").extractTerms());
        assertEquals(Arrays.asList("den", "haag"),
                new QueryExtractor("den AND haag").extractTerms(true));
        assertEquals(Arrays.asList("den", "haag"), new QueryExtractor("den NOT haag").extractTerms());
        assertEquals(Arrays.asList("den", "haag"), new QueryExtractor("den NOT haag").extractTerms(true));
        assertEquals(Collections.singletonList("den haag"), new QueryExtractor("\"den haag\"").extractTerms());
        assertEquals(Collections.singletonList("den haag"), new QueryExtractor("\"den haag\"").extractTerms(true));
        assertEquals(Collections.singletonList("den, haag"), new QueryExtractor("\"den, haag\"").extractTerms());
        assertEquals(Collections.singletonList("den, haag"), new QueryExtractor("\"den, haag\"").extractTerms(true));
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
        assertEquals(Collections.singletonList("spinoza"), new QueryExtractor("spinoza*").extractTerms());
        assertEquals(Collections.singletonList("spinoza"), new QueryExtractor("spinoza*").extractTerms(true));
        assertEquals(Collections.singletonList("den haag"), new QueryExtractor("\"den haag*\"").extractTerms());
        assertEquals(Collections.singletonList("den haag"), new QueryExtractor("\"den haag*\"").extractTerms(true));
        assertEquals(Collections.singletonList("spinoza"), new QueryExtractor("spinoza~").extractTerms());
        assertEquals(Collections.singletonList("spinoza"), new QueryExtractor("spinoza~").extractTerms(true));
        assertEquals(Collections.singletonList("Spinoza"), new QueryExtractor("Spinoza~").extractTerms());
        assertEquals(Collections.singletonList("Spinoza"), new QueryExtractor("Spinoza~").extractTerms(true));
        assertEquals(Collections.singletonList("spinoza"), new QueryExtractor("spinoza~0.3").extractTerms());
        assertEquals(Collections.singletonList("Spinoza"), new QueryExtractor("Spinoza~0.3").extractTerms(true));
        assertEquals(Arrays.asList("Spinoza", "spinoza"), new QueryExtractor("Spinoza OR spinoza").extractTerms());
        assertEquals(Arrays.asList("Spinoza", "spinoza"), new QueryExtractor("Spinoza OR spinoza").extractTerms(true));
        assertEquals(Collections.singletonList("apples"), new QueryExtractor("apples").extractTerms());
        assertEquals(Collections.singletonList("apples"), new QueryExtractor("apples").extractTerms(true));
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
        assertEquals(Collections.singletonList("den, haag"), new QueryExtractor("\"den, haag\"").extractTerms());
        assertEquals(Collections.singletonList("den haag"), new QueryExtractor("\"den haag\"").extractTerms());
        assertEquals(Collections.singletonList("newspaper"), new QueryExtractor("what:\"newspaper\"").extractTerms());
    }

    @Test
    public void testAsterix() {
        assertEquals(Collections.singletonList("test"), new QueryExtractor("*:* test").extractTerms());
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
        assertEquals("(la joconde) OR \"la gioconda\" OR \"mona lisa\"",
                rewriteQuery("la joconde", Arrays.asList("la gioconda", "mona lisa")));

        QueryExtractor queryExtractor = new QueryExtractor("(mona lisa) OR (den haag)");
        List<QueryModification> queryModifications = new ArrayList<>();
        queryModifications.add(new QueryModification(1, 10,
                "((mona lisa) OR \"La Joconde\" OR \"Gioconda\" OR \"Mona Lisa\")"));
        queryModifications.add(new QueryModification(16, 24,
                "((den haag) OR \"The Hague\" OR \"Den Haag\" OR \"Haag\" OR "
                        + "\"La Haye\" OR \"L'Aia\" OR \"Haga\")"));
        String modifiedQuery = queryExtractor.rewrite(queryModifications);
        assertEquals("(((mona lisa) OR \"La Joconde\" OR \"Gioconda\" OR \"Mona Lisa\")) OR (((den haag) OR \"The Hague\" "
                + "OR \"Den Haag\" OR \"Haag\" OR \"La Haye\" OR \"L'Aia\" OR \"Haga\"))", modifiedQuery);
    }

    @Test
    public void testPhraseAlternatives() {
        assertEquals("what:(\"newspaper\" OR \"Zeitung\" OR \"Krant\" OR \"Newspaper\")",
                rewriteQuery("what:\"newspaper\"", Arrays.asList("Zeitung", "Krant", "Newspaper")));
    }

    private String rewriteQuery(String query, List<String> alternatives) {
        String modifiedQuery;
        QueryExtractor queryExtractor = new QueryExtractor(query);
        List<QueryToken> tokens = queryExtractor.extractInfo(true);
        List<QueryModification> queryModifications = new ArrayList<>();
        for (QueryToken token : tokens) {
            QueryModification queryModification = token.createModification(query, alternatives);
            if (queryModification != null) {
                queryModifications.add(queryModification);
            }
        }
        modifiedQuery = queryExtractor.rewrite(queryModifications);
        return modifiedQuery;
    }
}

