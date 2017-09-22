package eu.europeana.corelib.web.service;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/corelib-web-context.xml", "/corelib-web-test.xml" })
/**
 * @deprecated since 2017-09-22
 */
@Deprecated
public class MicrosoftTranslatorServiceTest {

	@Resource
	private MicrosoftTranslatorService microsoftTranslator;

//	@Test
	public void testTranslateLocation() {
		String translated = microsoftTranslator.translate("den haag", "en");
		assertNotNull(translated);
		if (!StringUtils.contains(translated,
				"TranslateApiException: AppId is over the quota")) {
			assertEquals("The Hague", translated);
		}
	}

//	@Test
	public void testTranslateSentence() {
		String translated = microsoftTranslator.translate(
			"Stevie Wonder is een Amerikaans zanger, componist en "
			+ "multi-instrumentalist.",
			"en");
		assertNotNull(translated);
		if (!StringUtils.contains(translated,
				"TranslateApiException: AppId is over the quota")) {
			assertEquals("Stevie Wonder is an American singer-songwriter, "
					+ "composer and multi-instrumentalist.", translated);
		}
	}
}
