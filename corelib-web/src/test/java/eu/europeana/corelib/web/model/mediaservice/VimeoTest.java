package eu.europeana.corelib.web.model.mediaservice;

import static org.junit.Assert.*;

import org.junit.Test;

public class VimeoTest {

	@Test
	public void testConstructor() {
		Vimeo mediaService = new Vimeo("24453345");
		assertNotNull(mediaService);

		assertEquals("http://vimeo.com/24453345", mediaService.getUrl());
		assertEquals("http://player.vimeo.com/video/24453345?title=0&byline=0&portrait=0", mediaService.getEmbeddedUrl());
		assertEquals("<iframe src=\"http://player.vimeo.com/video/24453345?title=0&amp;byline=0&amp;portrait=0\" width=\"WIDTH\" height=\"HEIGHT\" frameborder=\"0\" webkitallowfullscreen mozallowfullscreen allowfullscreen></iframe> <p><a href=\"http://vimeo.com/24453345\">24453345</a> on <a href=\"http://vimeo.com\">Vimeo</a>.</p>", mediaService.getEmbeddedHtml());
	}

}
