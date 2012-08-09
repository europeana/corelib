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
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import junit.framework.Assert;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.ImageWriteException;
import org.apache.sanselan.Sanselan;
import org.apache.sanselan.common.IImageMetadata;
import org.apache.sanselan.common.byteSources.ByteSourceFile;
import org.apache.sanselan.formats.jpeg.JpegImageMetadata;
import org.apache.sanselan.formats.jpeg.JpegPhotoshopMetadata;
import org.apache.sanselan.formats.jpeg.exifRewrite.ExifRewriter;
import org.apache.sanselan.formats.jpeg.xmp.JpegXmpRewriter;
import org.apache.sanselan.formats.tiff.TiffImageMetadata;
import org.apache.sanselan.formats.tiff.write.TiffOutputSet;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.europeana.corelib.db.dao.NosqlDao;
import eu.europeana.corelib.db.entity.nosql.Image;
import eu.europeana.corelib.db.entity.nosql.ImageCache;
import eu.europeana.corelib.db.exception.DatabaseException;
import eu.europeana.corelib.definitions.jibx.RDF;
import eu.europeana.corelib.definitions.model.ThumbSize;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/corelib-db-context.xml", "/corelib-db-test.xml" })
public class ThumbnailServiceTest {

	@Resource(name="corelib_db_imageDao")
	NosqlDao<ImageCache, String> imageDao;
	
	@Resource
	ThumbnailService thumbnailService;
	
	BufferedImage image;
	
	final String OBJ_ID = "objectTest";
	final String COL_ID = "collectionTest";
	
	@Before
	public void setup() throws IOException {
		imageDao.getCollection().drop();
		image = ImageIO.read( getClass().getResourceAsStream("/images/GREATWAR.jpg") );
	}
	
	@Test
	public void storeTest() throws IOException, DatabaseException, ImageReadException {

		
		Assert.assertTrue("Schema not empty...", imageDao.count() == 0);
        
		ImageCache cache = thumbnailService.storeThumbnail(OBJ_ID, COL_ID, image, "/images/GREATWAR.jpg");
		
		Assert.assertTrue("Test item does not exist in MongoDB", thumbnailService.exists(OBJ_ID));
		
		byte[] tiny = thumbnailService.retrieveThumbnail(OBJ_ID, ThumbSize.TINY);
		
		Assert.assertTrue(tiny.length == cache.getImages().get(ThumbSize.TINY.toString()).getImage().length );
		
		ImageCache imageCache = thumbnailService.findByID(OBJ_ID);
		
		Image tinyImage = imageCache.getImages().get(ThumbSize.TINY.toString());
		
		Assert.assertTrue(tinyImage.getHeight() > 0);
		Assert.assertEquals(tinyImage.getWidth(), ThumbSize.TINY.getMaxWidth());
		
		Image largeImage = imageCache.getImages().get(ThumbSize.LARGE.toString());
		InputStream in = new ByteArrayInputStream(largeImage.getImage());
		
	}
	
	/**
	 * @throws JiBXException
	 * @throws FileNotFoundException
	 * @throws DatabaseException
	 */
	@Test
	public void storewithXMPTest() throws JiBXException, FileNotFoundException, DatabaseException {
		
		IBindingFactory context = BindingDirectory.getFactory(RDF.class);
		
		IUnmarshallingContext uctx = context.createUnmarshallingContext();
		
		File test = new File("src/test/resources/edm/edmsample.xml");
		
		InputStream ins = new FileInputStream(test);
		
		RDF edm = (RDF) uctx.unmarshalDocument(ins, "UTF-8");
		
		ImageCache cache = thumbnailService.storeThumbnail(OBJ_ID, COL_ID, image, "/images/GREATWAR.jpg",edm);
		
		
		ImageCache imageCache = thumbnailService.findByID(OBJ_ID);
		Assert.assertNotNull(imageCache);
		String xmpInfo = thumbnailService.extractXMPInfo(OBJ_ID, COL_ID, ThumbSize.TINY);
		
		
		
		System.out.println(xmpInfo);
		
	}

}
