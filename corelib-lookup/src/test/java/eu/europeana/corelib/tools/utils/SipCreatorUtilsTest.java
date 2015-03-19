package eu.europeana.corelib.tools.utils;

import static org.junit.Assert.assertEquals;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import eu.europeana.corelib.lookup.utils.PreSipCreatorUtils;
import eu.europeana.corelib.lookup.utils.SipCreatorUtils;

public class SipCreatorUtilsTest {

	@Test
	public void testSipCreatorUtils() {
		String workingDir = StringUtils.endsWith(
				System.getProperty("user.dir"), "corelib") ? System
				.getProperty("user.dir") + "/corelib-solr-tools" : System
				.getProperty("user.dir");
		SipCreatorUtils sipCreatorUtils = new SipCreatorUtils();
		String repository = workingDir+"/src/test/resources/";
		sipCreatorUtils.setRepository(repository);
		assertEquals("europeana_isShownBy[0]",sipCreatorUtils.getHashField("9200103", "9200103_Ag_EU_TEL_Gallica_a0142"));
		PreSipCreatorUtils preSipCreatorUtils = new PreSipCreatorUtils();
		preSipCreatorUtils.setRepository(repository);
		assertEquals("europeana_isShownAt", preSipCreatorUtils.getHashField("00735", "00735_A_DE_Landesarchiv_ese_5_0000013080"));
	}

}
