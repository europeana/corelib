package eu.europeana.corelib.definitions.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests
 */
public class RightsOptionTest {

    /**
     * We check if all rights options start with http:// and end with a slash. This is required for the getValueByUrl
     * test to work properly!
     */
    @Test
    public void checkRightsOption() {
        for (RightsOption ro : RightsOption.values()) {
            assertTrue(ro.getUrl().startsWith("http://"));
            assertTrue(ro.getUrl().endsWith("/"));
        }
    }

    @Test
    public void getValueByUrlTest() {
        // correct url
        assertEquals(RightsOption.CC_NOC, RightsOption.getValueByUrl("http://creativecommons.org/publicdomain/mark/1.0/"));
        assertEquals(RightsOption.CC_NOC, RightsOption.getValueByUrl("https://creativecommons.org/publicdomain/mark"));
        assertEquals(RightsOption.RS_NOC_OKLR, RightsOption.getValueByUrl("http://rightsstatements.org/vocab/NoC-OKLR/1.0/"));
        assertEquals(RightsOption.RS_NOC_OKLR, RightsOption.getValueByUrl("https://rightsstatements.org/vocab/NoC-OKLR/2.1/"));

        // empty url
        assertNull(RightsOption.getValueByUrl(null));
        assertNull(RightsOption.getValueByUrl(""));
        assertNull(RightsOption.getValueByUrl("   "));

        // unknown urls
        assertNull(RightsOption.getValueByUrl("http://rights-statements.org/vocab/NoC-OKLR/1.0/")); // incorrect base-url
        assertNull(RightsOption.getValueByUrl("http://rightsstatements.org/vocab/NoCOKLR/1.0/")); // missing hyphen
    }
}
