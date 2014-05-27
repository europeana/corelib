package eu.europeana.corelib.web.model.mediaservice;

import static org.junit.Assert.*;

import org.junit.Test;

public class AudioBooTest {

	@Test
	public void testConstructor() {
		AudioBoo mediaService = new AudioBoo("793330");
		assertNotNull(mediaService);

		assertEquals("http://audioboo.fm/boos/793330", mediaService.getUrl());
		assertEquals("https://audioboo.fm/boos/793330/embed/v2?eid=AQAAAGBQf1PyGgwA", mediaService.getEmbeddedUrl());
		assertEquals("<div class=\"ab-player\" data-boourl=\"https://audioboo.fm/boos/793330/embed/v2?eid=AQAAAGBQf1PyGgwA\" data-boowidth=\"100%\" data-maxheight=\"150\" data-iframestyle=\"background-color:transparent; display:block; box-shadow:0 0 1px 1px rgba(0, 0, 0, 0.15); min-width:349px; max-width:700px;\" style=\"background-color:transparent;\"><a href=\"http://audioboo.fm/boos/\">793330 on Audioboo</a></div><script type=\"text/javascript\">(function() { var po = document.createElement(\"script\"); po.type = \"text/javascript\"; po.async = true; po.src = \"https://d15mj6e6qmt1na.cloudfront.net/cdn/embed.js\"; var s = document.getElementsByTagName(\"script\")[0]; s.parentNode.insertBefore(po, s); })();</script>", mediaService.getEmbeddedHtml());
	}
}
