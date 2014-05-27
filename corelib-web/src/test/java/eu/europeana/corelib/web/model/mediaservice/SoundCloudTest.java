package eu.europeana.corelib.web.model.mediaservice;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.europeana.corelib.web.service.SoundCloudApiService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/corelib-web-context.xml", "/corelib-web-test.xml" })
public class SoundCloudTest {

	@Resource
	private SoundCloudApiService soundCloudApiService;

	@Test
	public void testPath() {
		SoundCloud mediaService = new SoundCloud("dick-1952/geluid-diverse-elektrische", soundCloudApiService);
		assertNotNull(mediaService);

		assertEquals("http://soundcloud.com/dick-1952/geluid-diverse-elektrische", mediaService.getUrl());
		assertEquals("https://w.soundcloud.com/player/?url=https%3A//api.soundcloud.com/tracks/150424305&amp;auto_play=false&amp;hide_related=false&amp;visual=true", mediaService.getEmbeddedUrl());
		assertEquals("<iframe width=\"100%\" height=\"450\" scrolling=\"no\" frameborder=\"no\" src=\"https://w.soundcloud.com/player/?url=https%3A//api.soundcloud.com/tracks/150424305&amp;auto_play=false&amp;hide_related=false&amp;visual=true\"></iframe>", mediaService.getEmbeddedHtml());
	}

	@Test
	public void testTrackId() {
		SoundCloud mediaService = new SoundCloud("150424305", soundCloudApiService);
		assertNotNull(mediaService);

		assertEquals("http://soundcloud.com/dick-1952/geluid-diverse-elektrische", mediaService.getUrl());
		assertEquals("https://w.soundcloud.com/player/?url=https%3A//api.soundcloud.com/tracks/150424305&amp;auto_play=false&amp;hide_related=false&amp;visual=true", mediaService.getEmbeddedUrl());
		assertEquals("<iframe width=\"100%\" height=\"450\" scrolling=\"no\" frameborder=\"no\" src=\"https://w.soundcloud.com/player/?url=https%3A//api.soundcloud.com/tracks/150424305&amp;auto_play=false&amp;hide_related=false&amp;visual=true\"></iframe>", mediaService.getEmbeddedHtml());
	}
}
