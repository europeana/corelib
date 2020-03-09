package eu.europeana.corelib.utils;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * URI Schema util classes
 * @author Srishti Singh
 *
 */
public class URISchemaUtilsTest {

    @Test
    public void isURITest() {
        assertTrue(EuropeanaUriUtils.isUri("https://"));

        assertTrue(EuropeanaUriUtils.isUri("http://"));

        assertTrue(EuropeanaUriUtils.isUri("session://"));

        assertTrue(EuropeanaUriUtils.isUri("#"));

        assertTrue( EuropeanaUriUtils.isUri("bitcoin:"));

        assertFalse(EuropeanaUriUtils.isUri("5fdh5672"));

        assertFalse( EuropeanaUriUtils.isUri(""));

    }

}