package eu.europeana.corelib.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Image util classes
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 *
 */
public class ImageUtilsTest {
	
	@Test
	public void scaleTest() throws IOException {
		assertNull("Should return null on null as input", ImageUtils.scale(null, 0, 0));
		
		BufferedImage bufImg = ImageIO.read( getClass().getResourceAsStream("/images/GREATWAR.jpg") );
		
		BufferedImage scaled = ImageUtils.scale(bufImg, 100, 100);
		
		assertNotNull("Should not return null", scaled);
		assertEquals(scaled.getHeight(), 59);
		assertEquals(scaled.getWidth(), 100);
	}
	
	@Test
	public void toByteArrayTest() throws IOException {
		assertNull("Should return null on null as input", ImageUtils.toByteArray(null));

		BufferedImage bufImg = ImageIO.read( getClass().getResourceAsStream("/images/GREATWAR.jpg") );
		byte[] array = ImageUtils.toByteArray(bufImg);
		
		assertNotNull("Should not return null", array);
		assertFalse("Should not return empty array", array.length == 0);
		assertTrue("Length of byte array invalid:" + array.length, array.length > 400000);
	}
	
	@Test
	public void toBufferedImageTest() throws IOException {
		assertNull("Should return null on null as input", ImageUtils.toBufferedImage(null));
		assertNull("Should return null on empty byte array as input", ImageUtils.toBufferedImage(new byte[]{}));
		
		BufferedImage bufImg1 = ImageIO.read( getClass().getResourceAsStream("/images/GREATWAR.jpg") );
		byte[] array1 = ImageUtils.toByteArray(bufImg1);
		
		BufferedImage bufImg2 = ImageUtils.toBufferedImage(array1);
		
		assertNotNull("Should not return null", bufImg2);
		assertEquals(bufImg1.getHeight(), bufImg2.getHeight());
		assertEquals(bufImg1.getWidth(), bufImg2.getWidth());
	}

}
