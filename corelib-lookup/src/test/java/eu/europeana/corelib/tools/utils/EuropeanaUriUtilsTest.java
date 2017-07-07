package eu.europeana.corelib.tools.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import eu.europeana.corelib.utils.EuropeanaUriUtils;


public class EuropeanaUriUtilsTest {

	private final static String RECORD_WITH_HTTP = "http://dev/test)(';x";
	private final static String RECORD_WITHOUT_HTTP = "GX:OTP9";
	private final static String COLLECTION_WITHOUT_LETTER = "92001";
	private final static String COLLECTION_WITH_LETTER = "92001a";
	
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

		System.out.println(EuropeanaUriUtils.createSanitizedEuropeanaId("202020","http://www.receptite.com/рецепта/селска-салата"));
	}
	
}
