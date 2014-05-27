package eu.europeana.corelib.web.model.mediaservice;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.europeana.corelib.web.model.mediaservice.AudioBoo;
import eu.europeana.corelib.web.model.mediaservice.DailyMotion;
import eu.europeana.corelib.web.model.mediaservice.MediaServiceType;
import eu.europeana.corelib.web.model.mediaservice.SoundCloud;
import eu.europeana.corelib.web.model.mediaservice.Vimeo;
import eu.europeana.corelib.web.model.mediaservice.Youtube;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/corelib-web-context.xml", "/corelib-web-test.xml" })
public class MediaServiceTypeTest {

	@Test
	public void testFindInstance() {
		assertTrue(MediaServiceType.findInstance("urn:audioboo:793330") instanceof AudioBoo);
		assertTrue(MediaServiceType.findInstance("urn:dailymotion:x18cxlz") instanceof DailyMotion);
		assertTrue(MediaServiceType.findInstance("urn:soundcloud:dick-1952/geluid-diverse-elektrische") instanceof SoundCloud);
		assertTrue(MediaServiceType.findInstance("urn:soundcloud:150424305") instanceof SoundCloud);
		assertTrue(MediaServiceType.findInstance("urn:youtube:cwHVie7GpNw") instanceof Youtube);
		assertTrue(MediaServiceType.findInstance("urn:vimeo:24453345") instanceof Vimeo);
		assertNull(MediaServiceType.findInstance("urn:nonexistent:793330"));
	}

	@Test
	public void testAudioBoo() {
		AudioBoo mediaService0 = (AudioBoo) MediaServiceType.findInstance("urn:audioboo:793330");
		assertNotNull(mediaService0);
		AudioBoo mediaService = (AudioBoo) MediaServiceType.AUDIOBOO.getInstance("urn:audioboo:793330");
		assertNotNull(mediaService);
		assertEquals(mediaService0, mediaService);

		assertEquals("http://audioboo.fm/boos/793330", mediaService.getUrl());
		assertEquals("https://audioboo.fm/boos/793330/embed/v2?eid=AQAAAGBQf1PyGgwA", mediaService.getEmbeddedUrl());
		assertEquals("<div class=\"ab-player\" data-boourl=\"https://audioboo.fm/boos/793330/embed/v2?eid=AQAAAGBQf1PyGgwA\" data-boowidth=\"100%\" data-maxheight=\"150\" data-iframestyle=\"background-color:transparent; display:block; box-shadow:0 0 1px 1px rgba(0, 0, 0, 0.15); min-width:349px; max-width:700px;\" style=\"background-color:transparent;\"><a href=\"http://audioboo.fm/boos/\">793330 on Audioboo</a></div><script type=\"text/javascript\">(function() { var po = document.createElement(\"script\"); po.type = \"text/javascript\"; po.async = true; po.src = \"https://d15mj6e6qmt1na.cloudfront.net/cdn/embed.js\"; var s = document.getElementsByTagName(\"script\")[0]; s.parentNode.insertBefore(po, s); })();</script>", mediaService.getEmbeddedHtml());
	}

	@Test
	public void testDailyMotion() {
		DailyMotion mediaService0 = (DailyMotion) MediaServiceType.findInstance("urn:dailymotion:x18cxlz");
		assertNotNull(mediaService0);
		DailyMotion mediaService = (DailyMotion) MediaServiceType.DAILYMOTION.getInstance("urn:dailymotion:x18cxlz");
		assertNotNull(mediaService);
		assertEquals(mediaService0, mediaService);

		assertEquals("http://www.dailymotion.com/video/x18cxlz", mediaService.getUrl());
		assertEquals("http://www.dailymotion.com/embed/video/x18cxlz", mediaService.getEmbeddedUrl());
		assertEquals("<iframe frameborder=\"0\" width=\"480\" height=\"270\" src=\"http://www.dailymotion.com/embed/video/x18cxlz\" allowfullscreen></iframe><br /><a href=\"http://www.dailymotion.com/video/x18cxlz\" target=\"_blank\">x18cxlz</a>", mediaService.getEmbeddedHtml());
	}

	@Test
	public void testSoundCloudPath() {
		SoundCloud mediaService0 = (SoundCloud) MediaServiceType.findInstance("urn:soundcloud:dick-1952/geluid-diverse-elektrische");
		assertNotNull(mediaService0);
		SoundCloud mediaService = (SoundCloud) MediaServiceType.SOUNDCLOUD.getInstance("urn:soundcloud:dick-1952/geluid-diverse-elektrische");
		assertNotNull(mediaService);
		assertEquals(mediaService0, mediaService);

		assertEquals("http://soundcloud.com/dick-1952/geluid-diverse-elektrische", mediaService.getUrl());
		assertEquals("https://w.soundcloud.com/player/?url=https%3A//api.soundcloud.com/tracks/150424305&amp;auto_play=false&amp;hide_related=false&amp;visual=true", mediaService.getEmbeddedUrl());
		assertEquals("<iframe width=\"100%\" height=\"450\" scrolling=\"no\" frameborder=\"no\" src=\"https://w.soundcloud.com/player/?url=https%3A//api.soundcloud.com/tracks/150424305&amp;auto_play=false&amp;hide_related=false&amp;visual=true\"></iframe>", mediaService.getEmbeddedHtml());
	}

	@Test
	public void testSoundCloudTrack() {
		SoundCloud mediaService0 = (SoundCloud) MediaServiceType.findInstance("urn:soundcloud:150424305");
		assertNotNull(mediaService0);
		SoundCloud mediaService = (SoundCloud) MediaServiceType.SOUNDCLOUD.getInstance("urn:soundcloud:150424305");
		assertNotNull(mediaService);

		assertEquals(mediaService0, mediaService);
		assertEquals("http://soundcloud.com/dick-1952/geluid-diverse-elektrische", mediaService.getUrl());
		assertEquals("https://w.soundcloud.com/player/?url=https%3A//api.soundcloud.com/tracks/150424305&amp;auto_play=false&amp;hide_related=false&amp;visual=true", mediaService.getEmbeddedUrl());
		assertEquals("<iframe width=\"100%\" height=\"450\" scrolling=\"no\" frameborder=\"no\" src=\"https://w.soundcloud.com/player/?url=https%3A//api.soundcloud.com/tracks/150424305&amp;auto_play=false&amp;hide_related=false&amp;visual=true\"></iframe>", mediaService.getEmbeddedHtml());
	}

	@Test
	public void testVimeo() {
		Vimeo mediaService0 = (Vimeo) MediaServiceType.findInstance("urn:vimeo:24453345");
		assertNotNull(mediaService0);
		Vimeo mediaService = (Vimeo) MediaServiceType.VIMEO.getInstance("urn:vimeo:24453345");
		assertNotNull(mediaService);
		assertEquals(mediaService0, mediaService);

		assertEquals("http://vimeo.com/24453345", mediaService.getUrl());
		assertEquals("http://player.vimeo.com/video/24453345?title=0&byline=0&portrait=0", mediaService.getEmbeddedUrl());
		assertEquals("<iframe src=\"http://player.vimeo.com/video/24453345?title=0&amp;byline=0&amp;portrait=0\" width=\"WIDTH\" height=\"HEIGHT\" frameborder=\"0\" webkitallowfullscreen mozallowfullscreen allowfullscreen></iframe> <p><a href=\"http://vimeo.com/24453345\">24453345</a> on <a href=\"http://vimeo.com\">Vimeo</a>.</p>", mediaService.getEmbeddedHtml());
	}

	@Test
	public void testYoutube() {
		Youtube mediaService0 = (Youtube) MediaServiceType.findInstance("urn:youtube:cwHVie7GpNw");
		assertNotNull(mediaService0);
		Youtube mediaService = (Youtube) MediaServiceType.YOUTUBE.getInstance("urn:youtube:cwHVie7GpNw");
		assertNotNull(mediaService);
		assertEquals(mediaService0, mediaService);

		assertEquals("http://youtube.com/watch?v=cwHVie7GpNw", mediaService.getUrl());
		assertEquals("http://youtube.com/embed/cwHVie7GpNw", mediaService.getEmbeddedUrl());
		assertEquals("<iframe width=\"420\" height=\"315\" src=\"http://www.youtube.com/embed/cwHVie7GpNw\" frameborder=\"0\" allowfullscreen></iframe>", mediaService.getEmbeddedHtml());
	}

}
