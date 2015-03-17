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

package eu.europeana.corelib.db.entity.nosql;

import java.awt.image.BufferedImage;
import java.io.IOException;

import eu.europeana.corelib.logging.Logger;
import eu.europeana.corelib.utils.ImageUtils;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public class Image {

	private int height;

	private int width;

	private byte[] image;

	/**
	 * CONSTRUCTORS
	 */

	public Image() {
		// left empty on purpose, do NOT remove!!
	}

	/**
	 * Alternative constructor that instantiates the image
	 * object with a byte array instead. This is mainly
	 * used to maintain XMP information since BufferedImage
	 * removes this kind of info.
	 * 
	 * @param imagebytes the image binary
	 */
	public Image(byte[] imagebytes){
		setImage(imagebytes);
		try {
			BufferedImage tmp = ImageUtils.toBufferedImage(imagebytes);
			setHeight(tmp.getHeight());
			setWidth(tmp.getWidth());
		} catch (IOException e) {
			Logger.getLogger(this.getClass()).error(e.getMessage());
		}
	}

	/**
	 * 
	 * 
	 * @param original
	 * @throws IOException
	 */
	public Image(BufferedImage original) throws IOException {
		setHeight(original.getHeight());
		setWidth(original.getWidth());
		setImage(ImageUtils.toByteArray(original));
	}

	/**
	 * GETTERS & SETTTERS
	 */

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public byte[] getImage() {
		return image.clone();
	}

	public void setImage(byte[] image) {
		this.image = image.clone();
	}
}
