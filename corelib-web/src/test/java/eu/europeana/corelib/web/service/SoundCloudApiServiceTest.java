package eu.europeana.corelib.web.service;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.europeana.corelib.web.model.mediaservice.soundcloud.Track;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/corelib-web-context.xml", "/corelib-web-test.xml" })
public class SoundCloudApiServiceTest {

	@Resource
	private SoundCloudApiService service;

	@Test
	public void testResolvePath() {
		Track track = service.resolvePath("dick-1952/interieur-treinstel-matr-36");
		assertEquals("49487785", track.getId());
		assertEquals("http://soundcloud.com/dick-1952/interieur-treinstel-matr-36", track.getPermalink_url());
		assertEquals("49487785", track.getId());
		assertEquals("dick-1952/interieur-treinstel-matr-36", track.getPath());
	}

	@Test
	public void testTrackInfo() {
		Track track = service.getTrackInfo("49487785");
		assertEquals("49487785", track.getId());
		assertEquals("http://soundcloud.com/dick-1952/interieur-treinstel-matr-36", track.getPermalink_url());
		assertEquals("49487785", track.getId());
		assertEquals("dick-1952/interieur-treinstel-matr-36", track.getPath());
	}
}
