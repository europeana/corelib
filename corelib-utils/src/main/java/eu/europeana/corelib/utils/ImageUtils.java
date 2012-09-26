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
 */
public class ImageUtils {

	private ImageUtils() {
		// do not allow instances of this class
	}

	public static BufferedImage scale(BufferedImage org, int width) throws IOException {
		if (org != null) {
			return Scalr.resize(org, width);
		}
		return null;
	}

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

	public static BufferedImage toBufferedImage(byte[] org) throws IOException {
		if ((org == null) || (org.length == 0)) {
			return null;
		}
		InputStream in = new ByteArrayInputStream(org);
		return ImageIO.read(in);
	}
}
