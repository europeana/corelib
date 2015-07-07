package eu.europeana.corelib.search.queryextractor;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
