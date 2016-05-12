package eu.europeana.corelib.web.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
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

	private List<TrackData> pairs;

	@Before
	public void setup() {
		pairs = new ArrayList<TrackData>();
		pairs.add(new TrackData("dick-1952/geluid-diverse-elektrische", "150424305"));
		pairs.add(new TrackData("dick-1952/geluid-van-een-vertrekkende", "137927336"));
//		pairs.add(new TrackData("classicsandjazz/01-hungarian-dance-no-5", "114360389"));
        pairs.add(new TrackData("matas/hobnotropic", "49931"));
	}

	@Test
	public void testResolvePath() {
		for (TrackData pair : pairs) {
			Track track = service.resolvePath(pair.getPath());
			assertEquals(pair.getPath(), track.getPath());
			assertEquals(pair.getTrackId(), track.getId());
			assertEquals("http://soundcloud.com/" + pair.getPath(), track.getPermalink_url());
		}
	}

	@Test
	public void testTrackInfo() {
		for (TrackData pair : pairs) {
			Track track = service.getTrackInfo(pair.getTrackId());
			assertEquals(pair.getPath(), track.getPath());
			assertEquals(pair.getTrackId(), track.getId());
			assertEquals("http://soundcloud.com/" + pair.getPath(), track.getPermalink_url());
		}
	}

	private class TrackData {
		private String path;
		private String trackId;

		public TrackData(String path, String trackId) {
			super();
			this.path = path;
			this.trackId = trackId;
		}

		public String getPath() {
			return path;
		}

		public String getTrackId() {
			return trackId;
		}
	}
}
