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
        assertTrue(EuropeanaUriUtils.isUri("https://"));
        assertTrue(EuropeanaUriUtils.isUri("http://"));
        assertTrue(EuropeanaUriUtils.isUri("session://"));
        assertTrue(EuropeanaUriUtils.isUri("#"));
        assertTrue( EuropeanaUriUtils.isUri("bitcoin:"));
        assertFalse(EuropeanaUriUtils.isUri("5fdh5672"));
        assertFalse(EuropeanaUriUtils.isUri(""));
        assertFalse(EuropeanaUriUtils.isUri(null));
        assertFalse(EuropeanaUriUtils.isUri("/direct/2385451"));

        //check for more relative URi for using isUriExt
        assertTrue(EuropeanaUriUtils.isUriExt("/direct/2385451"));
        assertTrue(EuropeanaUriUtils.isUriExt("../direct/2385451"));
        assertTrue(EuropeanaUriUtils.isUriExt("./direct/2385451"));
        assertTrue(EuropeanaUriUtils.isUriExt("/direct/2385451"));
        assertFalse(EuropeanaUriUtils.isUriExt("2385451"));
        assertFalse(EuropeanaUriUtils.isUriExt(null));

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