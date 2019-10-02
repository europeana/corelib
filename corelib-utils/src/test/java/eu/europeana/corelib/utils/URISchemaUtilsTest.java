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
    public void absoluteURITest() {
        assertTrue(EuropeanaUriUtils.isAbsoluteIRI("https://"));

        assertTrue(EuropeanaUriUtils.isAbsoluteIRI("http://"));

        assertTrue(EuropeanaUriUtils.isAbsoluteIRI("session://"));

        assertTrue( EuropeanaUriUtils.isAbsoluteIRI("bitcoin:"));

        assertFalse(EuropeanaUriUtils.isAbsoluteIRI("5fdh5672"));

        assertFalse( EuropeanaUriUtils.isAbsoluteIRI(""));

    }

}