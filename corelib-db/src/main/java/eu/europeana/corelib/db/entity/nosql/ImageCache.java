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

import java.util.HashMap;
import java.util.Map;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;

import eu.europeana.corelib.db.entity.nosql.abstracts.NoSqlEntity;
import eu.europeana.corelib.db.service.ThumbnailService;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
@Entity("ImageCache")
public class ImageCache implements NoSqlEntity {

	@Id
	private String imageId;

	@Indexed(unique=false)
	private String objectId;

	@Indexed(unique=false)
	private String collectionId;

	@Indexed(unique=false)
	private String originalUrl;

	private Map<String, Image> images = new HashMap<String, Image>();

	/**
	 * CONSTRUCTORS
	 */

	public ImageCache() {
		// left empty on purpose, do NOT remove!!
	}

	public ImageCache(String objectId, String imageId, String collectionId, String url) {
		setImageId(new StringBuilder(objectId).append(ThumbnailService.COMBINE_CHAR).append(imageId).toString());
		setObjectId(objectId);
		setCollectionId(collectionId);
		setOriginalUrl(url);
	}

	/**
	 * GETTERS & SETTTERS
	 */
	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	/**
	 * String = LARGE, MEDIUM, TINY
	 * @return
	 */
	public Map<String, Image> getImages() {
		return images;
	}

	public String getOriginalUrl() {
		return originalUrl;
	}

	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
	}

	public String getCollectionId() {
		return collectionId;
	}

	public void setCollectionId(String collectionId) {
		this.collectionId = collectionId;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
}
