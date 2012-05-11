package eu.europeana.corelib.tools.utils;

import org.junit.Test;
import static org.junit.Assert.*;


public class EuropeanaUriUtilsTest {

	private final static String RECORD_WITH_HTTP = "http://dev/test)(';x";
	private final static String RECORD_WITHOUT_HTTP = "GX:OTP9";
	private final static String COLLECTION_WITHOUT_LETTER = "92001";
	private final static String COLLECTION_WITH_LETTER = "92001a";
	
	@Test
	public void testEuropeanaUriCreation(){
		String collectionIDtest = EuropeanaUriUtils.createEuropeanaId(COLLECTION_WITHOUT_LETTER, RECORD_WITHOUT_HTTP);
		assertEquals("/92001/GX_OTP9", collectionIDtest);
		collectionIDtest =  EuropeanaUriUtils.createEuropeanaId(COLLECTION_WITH_LETTER,RECORD_WITHOUT_HTTP);
		assertEquals("/92001/GX_OTP9", collectionIDtest);
		collectionIDtest =  EuropeanaUriUtils.createEuropeanaId(COLLECTION_WITH_LETTER,RECORD_WITH_HTTP);
		assertEquals("/92001/test____x", collectionIDtest);
		collectionIDtest =  EuropeanaUriUtils.createEuropeanaId(COLLECTION_WITHOUT_LETTER,RECORD_WITH_HTTP);
		assertEquals("/92001/test____x", collectionIDtest);
	}
	
}
