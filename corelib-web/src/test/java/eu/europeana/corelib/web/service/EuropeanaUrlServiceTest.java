package eu.europeana.corelib.web.service;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/corelib-web-context.xml", "/corelib-web-test.xml" })
public class EuropeanaUrlServiceTest extends AbstractJUnit4SpringContextTests {

	@Resource
	private EuropeanaUrlService europeanaUrlService;

	@Test
	public void getApi2RedirectTest() {
		String expected = "http://localhost:8080/api/123/redirect?shownAt=http%3A%2F%2Fwww.kb.nl&provider=example&id=http%3A%2F%2Fwww.europeana.eu%2Fresolve%2Frecord%2Fkb%2Frecord&profile=full";
		assertEquals("getPortalHome full url failed", expected, europeanaUrlService.getApi2Redirect("123", "http://www.kb.nl", "example", "/kb/record", "full").toString());
	}

	@Test
	public void getPortalHomeTest() {
		String expected = "http://localhost:8081";
		assertEquals("getPortalHome full url failed", expected, europeanaUrlService.getPortalHome(false).toString());

		
	}

	@Test
	public void getPortalResolveTest() {
		String expected = "http://www.europeana.eu/resolve/record/euro/testrecord";
		assertEquals("getPortalResolve url failed", expected, europeanaUrlService.getPortalResolve("/euro/testrecord"));
	}

	//@Test Temporarily disabling
	public void getPortalSearchTest() throws UnsupportedEncodingException {
		final String query = "leerdam";
		final String rows = "24";
		String expected = "http://localhost:8081/search.html?query=leerdam&rows=24";
		assertEquals("getPortalHome full url failed", expected, europeanaUrlService.getPortalSearch(false, query, rows)
				.toString());

		expected = "/search.html?query=leerdam&rows=24";
		assertEquals("getPortalHome relative url failed", expected,
				europeanaUrlService.getPortalSearch(true, query, rows).toString());
	}

	@Test
	public void getPortalRecordTest() {
		final String collectionId = "euro";
		final String recordId = "testrecord";
		String expected = "http://localhost:8081/record/euro/testrecord.html";
		assertEquals("getPortalHome full url failed", expected,
				europeanaUrlService.getPortalRecord(false, collectionId, recordId).toString());

		expected = "/record/euro/testrecord.html";
		assertEquals("getPortalHome relative url failed", expected,
				europeanaUrlService.getPortalRecord(true, collectionId, recordId).toString());
		
		assertEquals("getPortalHome relative url failed", expected,
				europeanaUrlService.getPortalRecord(true, "/euro/testrecord").toString());

		expected = "http://www.europeana.eu/record/euro/testrecord.html";
		assertEquals("getCanonicalPortalRecord failed", expected,
				europeanaUrlService.getCanonicalPortalRecord("/euro/testrecord").toString());
	}

	@Test
	public void extractEuropeanaIdTest() {
		final String expected = "/euro/testrecord";

		assertEquals("extractEuropeanaIdTest full url failed", expected,
				europeanaUrlService.extractEuropeanaId("http://localhost:8081/record/euro/testrecord.html"));

		assertEquals("extractEuropeanaIdTest full resolve url failed", expected,
				europeanaUrlService.extractEuropeanaId("http://localhost:8081/resolve/record/euro/testrecord"));

		assertEquals("extractEuropeanaIdTest url failed", expected,
				europeanaUrlService.extractEuropeanaId("/record/euro/testrecord.html"));

		assertEquals("extractEuropeanaIdTest resolve url failed", expected,
				europeanaUrlService.extractEuropeanaId("/resolve/record/euro/testrecord"));
	}

}
