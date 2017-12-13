package eu.europeana.corelib.web.utils;

import eu.europeana.corelib.web.exception.InvalidUrlException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UrlBuilderTest {

	@Test
	public void testUrlCleaning() {
		String dirty = "http://www.europeana.eu//portal/index.html?";
		String clean = "http://www.europeana.eu/portal/index.html";
		UrlBuilder url = new UrlBuilder(dirty);
		assertEquals("Cleaning of double slashes or unwanted chars at the end failed", clean, url.toString());
		
		dirty = "www.europeana.eu//portal/";
		clean = "http://www.europeana.eu/portal";
		url = new UrlBuilder(dirty);
		assertEquals("Cleaning of ending slashes and missing protocol", clean, url.toString());

		dirty = "https://www.europeana.eu//portal/index.html?var1=value1&&var2=value2&";
		clean = "https://www.europeana.eu/portal/index.html?var1=value1&var2=value2";
		url = new UrlBuilder(dirty);
		assertEquals("Cleaning of ending or double &", clean, url.toString());
	}

	@Test
	public void testRelativeUrlSupport() {
		String dirty = "/portal//index.html?";
		String clean = "/portal/index.html";
		UrlBuilder url = new UrlBuilder(dirty);
		assertEquals("Cleaning of double slashes or unwanted chars at the end failed", clean, url.toString());
	}

	@Test
	public void testProtocolSupport() {
		String dirty = "ftp://www.europeana.eu//portal/index.html?var1=value1&&var2=value2&";
		String clean = "ftp://www.europeana.eu/portal/index.html?var1=value1&var2=value2";
		UrlBuilder url = new UrlBuilder(dirty);
		assertEquals("Cleaning of ending or double &", clean, url.toString());

		String expected = "www.europeana.eu/portal/user/";
		url = new UrlBuilder("http://www.europeana.eu/");
		url.addPath("portal", "user").disableProtocol();
		assertEquals("Disabling protocol", expected, url.toString());
	}

	@Test
	public void testAddPath() {
		String expected = "http://www.europeana.eu/portal/user/";
		UrlBuilder url = new UrlBuilder("http://www.europeana.eu");
		url.addPath("portal", "user");
		assertEquals("Adding two paths failed", expected, url.toString());
		
		url = new UrlBuilder("http://www.europeana.eu/");
		url.addPath("portal/", "/user");
		assertEquals("Adding two paths containing unneeded slashes failed", expected, url.toString());
	}

	@Test
	public void testAddPage() {
		String expected = "http://www.europeana.eu/portal/index.html";
		UrlBuilder url = new UrlBuilder("http://www.europeana.eu");
		url.addPath("portal");
		url.addPage("index.html");
		assertEquals("Adding path and page failed", expected, url.toString());
		
		url = new UrlBuilder("http://www.europeana.eu/");
		url.addPath("portal/").addPage("/index.html?");
		assertEquals("Adding path and page containing unneeded slashes failed", expected, url.toString());
		
		expected = "http://www.europeana.eu/portal/record/09303/0B7E902E597DA16379C9AEC6EDC8C1E19CEBE9DC.html?start=1&query=money&startPage=1&rows=24";
		url = new UrlBuilder("http://www.europeana.eu/");
		url.addPath("portal/", "record").addPath("09303");
		url.addPage("0B7E902E597DA16379C9AEC6EDC8C1E19CEBE9DC.html");
		url.addParam("start", "1", true).addParam("query", "money", true);
		url.addParam("startPage", "1", true).addParam("rows", "24", true);
		assertEquals("Adding path and page containing unneeded slashes failed", expected, url.toString());
	}
	
	@Test
	public void testSetDomain() {
		String expected = "https://github.com/eLedge/UrlBuilder";
		UrlBuilder url = new UrlBuilder("https://eledge.net/eLedge/UrlBuilder");
		url.setDomain("http://github.com/");
		assertEquals("Domain replacement failed", expected, url.toString());
	}
	
	@Test
	public void testCanonicalUrl() throws InvalidUrlException {
		String expected = "http://eledge.net:8080/page/example.html";
		String expected2 = "http://eledge.net:8080/page/example.html?test=1#anchor";
		UrlBuilder url =  new UrlBuilder("http://eledge.net:8080//page/example.html?test=1#anchor");
		assertEquals("Canonical URL cleaning failed", expected, url.toCanonicalUrl());
		assertEquals("URL cleaning failed", expected2, url.toString());
	}
	
	
	@Test(expected=InvalidUrlException.class)
	public void testInvalidCanonicalUrl() throws InvalidUrlException {
		new UrlBuilder("/page/example.html?test=1#anchor").toCanonicalUrl();
	}

	/**
	 * Testing addParamsFromURL() method (fix for ticket #1335)
	 */
	@Test
	public void testAddParamsFromURL() {
		UrlBuilder url = new UrlBuilder("http://www.europeana.eu");
		url.addParamsFromURL("query=*%3A*&qf=DATA_PROVIDER:%22Musik%20%26%20Teatermuseet%22&qf=paris");
		assertEquals("http://www.europeana.eu?query=*%3A*&qf=DATA_PROVIDER%3A%22Musik+%26+Teatermuseet%22&qf=paris",
				url.toString());
	}
}