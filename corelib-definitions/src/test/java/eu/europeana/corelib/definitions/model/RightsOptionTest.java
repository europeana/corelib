package eu.europeana.corelib.definitions.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for RightsOption class
 */
public class RightsOptionTest {

    /**
     * We check if all rights options start with http://
     * This is required for the getValueByUrl() method to work properly!
     */
    @Test
    public void checkRightsOptionHttp() {
        for (RightsOption ro : RightsOption.values()) {
            assertTrue(ro.getUrl().startsWith("http://"));
        }
    }

    /**
     * We check if all ShortMatchPatterns are unique and no ShortMatchPattern contains another one.
     * This is required for the getValueByUrl() method to work properly!
     */
    @Test
    public void checkRightsOptionUniqueShortPattern() {
        for (RightsOption roToTest : RightsOption.values()) {
            for (RightsOption ro : RightsOption.values()) {
                assertFalse("RightsOption short match pattern conflict! - " + ro.shortMatchPattern +
                                " contains " + roToTest.shortMatchPattern,
                        ro.shortMatchPattern.contains(roToTest.shortMatchPattern) && ro != roToTest);
            }
        }
    }

    @Test
    public void getValueByUrlExactTest() {
        // empty url
        assertNull(RightsOption.getValueByUrl(null, true));
        assertNull(RightsOption.getValueByUrl("", true));
        assertNull(RightsOption.getValueByUrl("   ", true));

        // the 9 most-used urls
        assertEquals(RightsOption.RS_INC,  RightsOption.getValueByUrl("http://rightsstatements.org/vocab/InC/1.0/", true));
        assertEquals(RightsOption.CC_NOC,  RightsOption.getValueByUrl("http://creativecommons.org/publicdomain/mark/1.0/", true));
        assertEquals(RightsOption.CC_ZERO,  RightsOption.getValueByUrl("http://creativecommons.org/publicdomain/zero/1.0/", true));
        assertEquals(RightsOption.CC_BY, RightsOption.getValueByUrl("http://creativecommons.org/licenses/by/4.0/", true));
        assertEquals(RightsOption.RS_NOC_OKLR, RightsOption.getValueByUrl("http://rightsstatements.org/vocab/NoC-OKLR/1.0/", true));
        assertEquals(RightsOption.CC_BY_NC_SA, RightsOption.getValueByUrl("http://creativecommons.org/licenses/by-nc-sa/4.0/", true));
        assertEquals(RightsOption.CC_BY_NC_ND, RightsOption.getValueByUrl("http://creativecommons.org/licenses/by-nc-nd/4.0/", true));
        assertEquals(RightsOption.RS_NOC_NC, RightsOption.getValueByUrl("http://rightsstatements.org/vocab/NoC-NC/1.0/", true));
        assertEquals(RightsOption.CC_BY_SA, RightsOption.getValueByUrl("http://creativecommons.org/licenses/by-sa/3.0/", true));

        // some alternative ones (with common problems)
        assertEquals(RightsOption.RS_INC,
                RightsOption.getValueByUrl("http://rightsstatements.org/vocab/InC/1.0/ ", true)); // trailing space!
        assertEquals(RightsOption.CC_BY_SA,
                RightsOption.getValueByUrl("http://creativecommons.org/licenses/by-sa/3.0/nl/", true)); // with language
        assertEquals(RightsOption.CC_BY_NC_ND,
                RightsOption.getValueByUrl("https://creativecommons.org/licenses/by-nc-nd/4.0/deed.gl", true)); // other extension + https

        // also common in the database but we can't match it with exact matching....
        assertNull(RightsOption.getValueByUrl("http://creativecommons.org/licenses/publicdomain/mark/", true)); // extra licenses path
        assertNull(RightsOption.getValueByUrl("http://rightsstatements.org/page/CNE/1.0/", true)); // page instead of vocab path
    }

    @Test
    public void getValueByUrlShortMatchTest() {
        // empty url
        assertNull(RightsOption.getValueByUrl(null, false));
        assertNull(RightsOption.getValueByUrl("", false));
        assertNull(RightsOption.getValueByUrl("   ", false));

        // common alternative urls in the database
        assertEquals(RightsOption.CC_NOC, RightsOption.getValueByUrl("http://creativecommons.org/licenses/publicdomain/mark/", false)); // extra licenses path
        assertEquals(RightsOption.RS_CNE, RightsOption.getValueByUrl("http://rightsstatements.org/page/CNE/1.0/", false)); // page instead of vocab path

        // uncommon alternative urls (occurance less than 150)
        assertNull(RightsOption.getValueByUrl("An error occurred getting the license - uri.", false));
        assertEquals(RightsOption.CC_BY_ND, RightsOption.getValueByUrl("http://creativecommons.org/licenses/by-nd/2.5/,http://www.culture.si/en/Licenses#Chttp://creativecommons.org/licenses/by-nd/2.5/,", false));
        assertEquals(RightsOption.RS_INC_OW_EU, RightsOption.getValueByUrl("http://rightsstatements.org/page/InC-OW-EU/1.0/", false));
        assertNull(RightsOption.getValueByUrl("Attribution-NonCommercial-NoDerivatives 4.0 Internacional", false));
        assertNull(RightsOption.getValueByUrl("http://creativecommons.org/licenses/©%20DEXTRA%20Photo/3.0/", false));

        // obscure alternative urls in the database (occurance less than 5)
        assertNull(RightsOption.getValueByUrl("CC BY-SA 4.0", false));
        assertNull(RightsOption.getValueByUrl("http://creativecommons.org.licenses/by-nc-nd3.0/", false)); // missing / and extra .
        assertEquals(RightsOption.CC_BY_SA, RightsOption.getValueByUrl("http://creativecommons.org/licences/by-sa/3.0/", false)); // licences instead of licenses
        assertEquals(RightsOption.EU_ORPHAN, RightsOption.getValueByUrl("http://www.europeana.eu/rights/orphan-work-eu.html", false)); // extra .html extension
        assertEquals(RightsOption.CC_NOC, RightsOption.getValueByUrl("http://creativecomons.org/publicdomain/mark/1.0/", false)); // only 1 m in comons
        assertEquals(RightsOption.CC_NOC, RightsOption.getValueByUrl("ttp://creativecommons.org/publicdomain/mark/1.0/", false));
        assertEquals(RightsOption.CC_BY_NC_ND, RightsOption.getValueByUrl("Creative Commons/by-nc-nd/4.0/", false));
    }
}
