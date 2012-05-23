package eu.europeana.corelib.tools.utils;

import static org.junit.Assert.*;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

public class SipCreatorUtilsTest {

	@Test
	public void testSipCreatorUtils() {
		String workingDir = StringUtils.endsWith(
				System.getProperty("user.dir"), "corelib") ? System
				.getProperty("user.dir") + "/corelib-solr-tools" : System
				.getProperty("user.dir");
		SipCreatorUtils sipCreatorUtils = new SipCreatorUtils();
		sipCreatorUtils.setRepository(workingDir+"/src/test/resources/");
		assertEquals("europeana_isShownBy[0]",sipCreatorUtils.getHashField("9200103", "9200103_Ag_EU_TEL_Gallica_a0142"));
		
	}

}
