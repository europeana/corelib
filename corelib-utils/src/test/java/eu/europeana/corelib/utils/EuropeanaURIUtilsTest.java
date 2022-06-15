package eu.europeana.corelib.utils;

import org.apache.logging.log4j.LogManager;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * URI Schema util classes
 * @author Srishti Singh
 *
 */
public class EuropeanaURIUtilsTest {

    private final static String RECORD_WITH_HTTP = "http://dev/test)(';x";
    private final static String RECORD_WITHOUT_HTTP = "GX:OTP9";
    private final static String COLLECTION_WITHOUT_LETTER = "92001";
    private final static String COLLECTION_WITH_LETTER = "92001a";

    @Test
    public void isURITest() {
        assertFalse(EuropeanaUriUtils.isUri("https://"));
        assertFalse(EuropeanaUriUtils.isUri("http://"));
        assertFalse(EuropeanaUriUtils.isUri("session://"));
        
        assertTrue(EuropeanaUriUtils.isUri("https://a.b"));
        assertTrue(EuropeanaUriUtils.isUri("http://wobbly"));
        assertTrue(EuropeanaUriUtils.isUri("session://do/something.args"));
        
        assertFalse(EuropeanaUriUtils.isUri("#"));
        assertFalse(EuropeanaUriUtils.isUri("bitcoin:")); // beats me why we would need this ?
        assertFalse(EuropeanaUriUtils.isUri("5fdh5672"));
        assertFalse(EuropeanaUriUtils.isUri(""));
        assertFalse(EuropeanaUriUtils.isUri(null));
        
        assertFalse(EuropeanaUriUtils.isAbsoluteUri("/direct/2385451"));
        assertTrue(EuropeanaUriUtils.isRelativeUri("/direct/2385451"));
        assertTrue(EuropeanaUriUtils.isUri("/direct/2385451"));

        // check for absolute, relative or generic URI's
        assertTrue(EuropeanaUriUtils.isUri("/direct/2385451"));
        assertTrue(EuropeanaUriUtils.isRelativeUri("/direct/2385451"));
        assertFalse(EuropeanaUriUtils.isAbsoluteUri("/direct/2385451"));
        
        assertTrue(EuropeanaUriUtils.isUri("../direct/2385451"));
        assertTrue(EuropeanaUriUtils.isRelativeUri("../direct/2385451"));
        assertFalse(EuropeanaUriUtils.isAbsoluteUri("../direct/2385451"));
        
        assertTrue(EuropeanaUriUtils.isUri("./direct/2385451"));
        assertTrue(EuropeanaUriUtils.isRelativeUri("./direct/2385451"));
        assertFalse(EuropeanaUriUtils.isAbsoluteUri("./direct/2385451"));
    
        assertTrue(EuropeanaUriUtils.isUri("#direct/2385451"));
        assertTrue(EuropeanaUriUtils.isRelativeUri("#direct/2385451"));
        assertFalse(EuropeanaUriUtils.isAbsoluteUri("#direct/2385451"));
        
        assertFalse(EuropeanaUriUtils.isUri("# Bûter, brea en griene tsiis"));
        
        assertFalse(EuropeanaUriUtils.isUri("2385451"));
        assertFalse(EuropeanaUriUtils.isUri(null));

    }

    @Test
    public void testEuropeanaUriCreation(){
        String collectionIDtest = EuropeanaUriUtils.createSanitizedEuropeanaId(COLLECTION_WITHOUT_LETTER, RECORD_WITHOUT_HTTP);
        assertEquals("/92001/GX_OTP9", collectionIDtest);
        collectionIDtest =  EuropeanaUriUtils.createSanitizedEuropeanaId(COLLECTION_WITH_LETTER,RECORD_WITHOUT_HTTP);
        assertEquals("/92001/GX_OTP9", collectionIDtest);
        collectionIDtest =  EuropeanaUriUtils.createSanitizedEuropeanaId(COLLECTION_WITH_LETTER,RECORD_WITH_HTTP);
        assertEquals("/92001/test____x", collectionIDtest);
        collectionIDtest =  EuropeanaUriUtils.createSanitizedEuropeanaId(COLLECTION_WITHOUT_LETTER,RECORD_WITH_HTTP);
        assertEquals("/92001/test____x", collectionIDtest);

        LogManager.getLogger(EuropeanaURIUtilsTest.class).info(EuropeanaUriUtils.
                createSanitizedEuropeanaId("202020","http://www.receptite.com/рецепта/селска-салата"));
    }

}