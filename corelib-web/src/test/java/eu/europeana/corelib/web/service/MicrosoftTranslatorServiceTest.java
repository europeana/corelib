package eu.europeana.corelib.web.service;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/corelib-web-context.xml", "/corelib-web-test.xml" })
public class MicrosoftTranslatorServiceTest {

	@Resource
	private MicrosoftTranslatorService microsoftTranslator;

//	@Test
	public void testTranslateLocation() {
		String translated = microsoftTranslator.translate("den haag", "en");
		System.out.println(translated);
		assertNotNull(translated);
		assertEquals("The Hague", translated);
	}

//	@Test
	public void testTranslateSentence() {
		String translated = microsoftTranslator.translate("Stevie Wonder is een Amerikaans zanger, componist en multi-instrumentalist.", "en");
		System.out.println(translated);
		assertNotNull(translated);
		assertEquals("Stevie Wonder is an American singer-songwriter, composer and multi-instrumentalist.", translated);
	}
}
