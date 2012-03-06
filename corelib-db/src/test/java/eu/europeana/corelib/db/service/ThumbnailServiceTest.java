/*
 * Copyright 2007-2012 The Europeana Foundation
 *
 *  Licenced under the EUPL, Version 1.1 (the "Licence") and subsequent versions as approved 
 *  by the European Commission;
 *  You may not use this work except in compliance with the Licence.
 *  
 *  You may obtain a copy of the Licence at:
 *  http://joinup.ec.europa.eu/software/page/eupl
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under 
 *  the Licence is distributed on an "AS IS" basis, without warranties or conditions of 
 *  any kind, either express or implied.
 *  See the Licence for the specific language governing permissions and limitations under 
 *  the Licence.
 */

package eu.europeana.corelib.db.service;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.europeana.corelib.db.entity.nosql.ImageCache;
import eu.europeana.corelib.db.repository.ImageCacheRepository;
import eu.europeana.corelib.definitions.model.ThumbSize;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/corelib-db-context.xml", "/corelib-db-test.xml" })
public class ThumbnailServiceTest {

	@Resource
	ImageCacheRepository repository;
	
	@Resource
	ThumbnailService thumbnailService;
	
	@Before
	public void setup() {
		repository.deleteAll();
	}
	
	@Test
	public void storeTest() throws IOException {
		Assert.assertTrue("Schema not empty...", repository.count() == 0);
		
		BufferedImage image = ImageIO.read( getClass().getResourceAsStream("/images/GREATWAR.jpg") );
		ImageCache cache = thumbnailService.storeThumbnail("test", image);
		
		Assert.assertTrue("Test item does not exists in MongoDB", repository.exists("test"));
		
		byte[] tiny = thumbnailService.retrieveThumbnail("test", ThumbSize.TINY);
		
		Assert.assertTrue(tiny.length == cache.getImages().get(ThumbSize.TINY.toString()).getImage().length );
		
//		InputStream in = new ByteArrayInputStream(thumbnailService.retrieveThumbnail("test", ThumbSize.LARGE));
//		BufferedImage bImageFromConvert = ImageIO.read(in);
//
//		ImageIO.write(bImageFromConvert, "jpg", new File("new-darksouls.jpg"));		
		
	}

}
