package eu.europeana.corelib.web.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import eu.europeana.harvester.domain.ImageMetaInfo;

// FIXME: These tests are currently ignored as they rely on an external db.
// They need to be refactored using an embedded MongoDB
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/corelib-web-context.xml", "/corelib-web-test.xml"})
@Ignore
@Deprecated
public class ContentReuseFrameworkServiceTest {
//TODO: Unit tests should never depend on an actual server. Refactor
	@Resource(name = "corelib_web_contentReuseFrameworkService")
	private ContentReuseFrameworkService crfService;

	@Ignore
	public void testGetMetadata() {

		List<Image> images = new ArrayList<Image>();
		images.add(new Image("http://images.memorix.nl/gam/thumb/200x200/d9874575-20a1-8279-8754-ec88fa494d25.jpg", 
			"85d0a688c8561ebab5ef1166de799881", 200, 130, "image/jpeg", "JPEG", "sRGB"));
		images.add(new Image("http://sounds.bl.uk/waveforms/water/022a-wa10016xxxxx-0037a0.png",
			"0fa62d483d482f4de2f902816b464dcf", 495, 222, "image/png", "PNG", "sRGB"));
		images.add(new Image("http://projects.packed.be/tm-archive/media/collectiveaccess_transmediale13/images/3/44136_ca_object_representations_media_321_thumbnail.jpg",
			"554190fcd63c5cfd5d671c678804475b", 120, 90, "image/jpeg", "JPEG", "sRGB"));
		images.add(new Image("http://projects.packed.be/tm-archive/media/collectiveaccess_transmediale13/images/1/2/6/93653_ca_object_representations_media_12661_thumbnail.jpg",
			"dd76b8f5c8fb53580cfcf739c1f784eb", 79, 120, "image/jpeg", "JPEG", "sRGB"));

		for (Image image : images) {
			assertEquals(image.getHash(), createHash(image.getUrl()));
			assertNotNull("metaInfo should not be null", crfService.getMetadata(image.getUrl()));
			ImageMetaInfo storedMeta = crfService.getMetadata(image.getUrl()).getImageMetaInfo();
			assertNotNull("Image metaInfo should not be null", storedMeta);
			assertEquals(image.getUrl(), image.getWidth(), storedMeta.getWidth());
			assertEquals(image.getHeight(), storedMeta.getHeight());
			assertEquals(image.getMimeType(), storedMeta.getMimeType());
			assertEquals(image.getFileFormat(), storedMeta.getFileFormat());
			assertEquals(image.getColorSpace(), storedMeta.getColorSpace());
		}
	}

	private String createHash(String url) {
		final HashFunction hf = Hashing.md5();
		final HashCode hc = hf.newHasher().putString(url, Charsets.UTF_8).hash();
		final String id = hc.toString();
		return id;
	}

    // TODO: fix this test. ImageMetaInfo has new constructor
	private class Image extends ImageMetaInfo {
		private static final long serialVersionUID = 831205053322762451L;

		private String url;
		private String hash;

		public Image(String url, String hash, Integer width, Integer height,
				String mimeType, String fileFormat, String colorSpace) {
			// CHANGED: super(width, height, mimeType, fileFormat, colorSpace);
			this.url = url;
			this.hash = hash;
		}

		public String getUrl() {
			return url;
		}

		public String getHash() {
			return hash;
		}
	}
}
