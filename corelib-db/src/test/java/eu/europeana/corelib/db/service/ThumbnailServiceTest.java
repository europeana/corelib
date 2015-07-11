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

import eu.europeana.corelib.db.dao.NosqlDao;
import eu.europeana.corelib.db.entity.nosql.Image;
import eu.europeana.corelib.db.entity.nosql.ImageCache;
import eu.europeana.corelib.db.exception.DatabaseException;
import eu.europeana.corelib.definitions.jibx.RDF;
import eu.europeana.corelib.definitions.model.ThumbSize;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * @author Georgios Markakis <gwarkx@hotmail.com>
 * @since 4 May 2012
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/corelib-db-context.xml", "/corelib-db-test.xml"})
public class ThumbnailServiceTest {

    private static Logger LOGGER = LoggerFactory.getLogger(ThumbnailServiceTest.class);

    @Resource(name = "corelib_db_imageDao")
    NosqlDao<ImageCache, String> imageDao;

    @Resource
    ThumbnailService thumbnailService;

    BufferedImage image;

    final String OBJ_ID = "objectTest";
    final String COL_ID = "collectionTest";

    /**
     * Initialise the testing session
     *
     * @throws IOException
     */
    @Before
    public void setup() throws IOException {
        imageDao.getCollection().drop();
        image = ImageIO.read(getClass().getResourceAsStream("/images/GREATWAR.jpg"));
    }

    /**
     * Image storage Test (no XMP information)
     *
     * @throws IOException
     * @throws DatabaseException
     */
    @Test
    public void storeTest() throws IOException, DatabaseException {

        assertTrue("Schema not empty...", imageDao.count() == 0);

        ImageCache cache = thumbnailService.storeThumbnail(OBJ_ID, COL_ID, image, "/images/GREATWAR.jpg");

        assertTrue("Test item does not exist in MongoDB", thumbnailService.exists(OBJ_ID));

        byte[] tiny = thumbnailService.retrieveThumbnail(OBJ_ID, ThumbSize.TINY);

        assertTrue(tiny.length == cache.getImages().get(ThumbSize.TINY.toString()).getImage().length);

        ImageCache imageCache = thumbnailService.findByID(OBJ_ID);

        Image tinyImage = imageCache.getImages().get(ThumbSize.TINY.toString());

        Image mediumImage = imageCache.getImages().get(ThumbSize.MEDIUM.toString());

        Image largeImage = imageCache.getImages().get(ThumbSize.LARGE.toString());

        //Test and ensure that the proportions of the produced images do not violate the thumbnail
        //specifications

        assertTrue(tinyImage.getHeight() <= ThumbSize.TINY.getMaxHeight());
        assertTrue(tinyImage.getWidth() <= ThumbSize.TINY.getMaxWidth());

        assertTrue(mediumImage.getHeight() <= ThumbSize.MEDIUM.getMaxHeight());
        assertTrue(mediumImage.getWidth() <= ThumbSize.MEDIUM.getMaxWidth());

        assertTrue(largeImage.getHeight() <= ThumbSize.LARGE.getMaxHeight());
        assertTrue(largeImage.getWidth() <= ThumbSize.LARGE.getMaxWidth());
    }

    /**
     * Image Storage Test (XMP Information added)
     *
     * @throws JiBXException
     * @throws FileNotFoundException
     * @throws DatabaseException
     */
    @Test
    public void storewithXMPTest() throws JiBXException, FileNotFoundException,
            DatabaseException {

        // Create an EDM object representation from a sample EDM XML file
        IBindingFactory context = BindingDirectory.getFactory(RDF.class);

        IUnmarshallingContext uctx = context.createUnmarshallingContext();

        File test = new File("../corelib-search/src/test/resources/test_files/edm_new.xml");

        InputStream ins = new FileInputStream(test);

        RDF edm = (RDF) uctx.unmarshalDocument(ins, "UTF-8");

        //Store the image by passing the EDM object as a parameter. All
        //relevant information contained in the EDM object will be embedded
        //in the file in the form of XMP (RDF) data
        thumbnailService.storeThumbnail(OBJ_ID, COL_ID, image, "/images/GREATWAR.jpg", edm);

        ImageCache imageCache = thumbnailService.findByID(OBJ_ID);
        assertNotNull("Image was not stored successfully in MongoDB", imageCache);

        //Check info for TINY thumbnail
        String xmpInfoTiny = thumbnailService.extractXMPInfo(OBJ_ID, COL_ID, ThumbSize.TINY);
        assertNotNull("Metadata for TINY image not stored successfully", xmpInfoTiny);
        LOGGER.info("== TINY thumbnail XMP Metadata: ==");
        LOGGER.info(xmpInfoTiny);

        //Check info for MEDIUM thumbnail
        String xmpInfoMed = thumbnailService.extractXMPInfo(OBJ_ID, COL_ID, ThumbSize.MEDIUM);
        assertNotNull("Metadata for MEDIUM image not stored successfully", xmpInfoMed);
        LOGGER.info("== MEDIUM thumbnail XMP Metadata: ==");
        LOGGER.info(xmpInfoMed);

        //Check info for LARGE thumbnail
        String xmpInfoLarge = thumbnailService.extractXMPInfo(OBJ_ID, COL_ID, ThumbSize.LARGE);
        assertNotNull("Metadata for MEDIUM image not stored successfully", xmpInfoLarge);
        LOGGER.info("== LARGE thumbnail XMP Metadata: ==");
        LOGGER.info(xmpInfoLarge);
    }

}
