package eu.europeana.corelib.web.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.europeana.corelib.utils.model.LanguageVersion;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/corelib-web-context.xml", "/corelib-web-test.xml" })
public class WikipediaApiServiceTest {

	@Resource
	private WikipediaApiService wikipediaApiService;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetLanguageLinks() {
		Map<String, String> languageVersions = wikipediaApiService.getVersionsInLanguage("den haag", "en");
		assertNotNull(languageVersions);
		//assertEquals(108, languageVersions.size());
	}
	/*
	
	@Test
	public void testGetLanguageVersions() {
		List<LanguageVersion> translations;
		List<String> languages = new ArrayList<String>();
		languages.add("en");
		languages.add("nl");
		languages.add("bg");

		translations = wikipediaApiService.getLanguageLinks("den haag", languages);
		assertNotNull(translations);
		assertEquals(3, translations.size());

		languages.remove("bg");
		languages.add("af");
		translations = wikipediaApiService.getLanguageLinks("den haag", languages);
		assertNotNull(translations);
		assertEquals(3, translations.size());
	}
	*/
}
