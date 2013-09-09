package eu.europeana.corelib.web.utils;

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
	public void testAddPath() {
		String expected = "http://www.europeana.eu/portal/user";
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
		url.addPath("portal/");
		url.addPage("/index.html?");
		assertEquals("Adding path and page containing unneeded slashes failed", expected, url.toString());
	}

}