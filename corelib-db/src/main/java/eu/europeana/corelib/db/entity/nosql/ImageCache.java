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
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
@Document(collection="ImageCache")
public class ImageCache {
	
	@Id
	private String objectId;
	
	private int height;
	
	private int width;
	
	private Map<String, Image> images = new HashMap<String, Image>();
	
	/**
	 * CONSTRUCTORS
	 */
	
	public ImageCache() {
		// left empty on purpose, do NOT remove!!
	}

	public ImageCache(String objectId, BufferedImage original) {
		this.objectId = objectId;
		this.height = original.getHeight();
		this.width = original.getWidth();
	}
	
	/**
	 * GETTERS & SETTTERS
	 */

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

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

	public Map<String, Image> getImages() {
		return images;
	}

}
