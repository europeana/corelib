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

package eu.europeana.corelib.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * @author Georgios Markakis <gwarkx@hotmail.com>
 * @since 30 May 2012
 */
public class ImageUtils {

	private ImageUtils() {
		// do not allow instances of this class
	}

	/**
	 * Scales an Image proportionally. It chooses the largest dimension (width/heigth) as a basis
	 * of proportional resizing.
	 *   
	 * @param org
	 * @param width
	 * @param height
	 * @return
	 * @throws IOException
	 */
	public static BufferedImage scale(BufferedImage org, int width, int height) throws IOException {
		if (org != null) {
				BufferedImage resized = Scalr.resize(org, Scalr.Method.QUALITY, Scalr.Mode.AUTOMATIC,
						width, height, Scalr.OP_ANTIALIAS);
				
				if(resized.getHeight() > height || resized.getWidth() > width ){
					return Scalr.crop(resized, width, height);
				}
				else{
					return resized;
				}

		}
		return null;
	}

	/**
	 * Converts a BufferedImage to a byte array.
	 * 
	 * @param org a java.awt.image.BufferedImage object
	 * @return a byte array with the contents of the image
	 * @throws IOException
	 */
	public static byte[] toByteArray(BufferedImage org) throws IOException {
		if (org != null) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(org, "jpg", baos);
			baos.flush();
			byte[] imageInByte = baos.toByteArray();
			baos.close();
			return imageInByte;
		}
		return null;
	}

	/**
	 * Converts a byte array to a BufferedImage. 
	 * 
	 * @param org a byte array with the contents of the image
	 * @return a java.awt.image.BufferedImage object
	 * @throws IOException
	 */
	public static BufferedImage toBufferedImage(byte[] org) throws IOException {
		if ((org == null) || (org.length == 0)) {
			return null;
		}
		InputStream in = new ByteArrayInputStream(org);
		return ImageIO.read(in);
	}
}
