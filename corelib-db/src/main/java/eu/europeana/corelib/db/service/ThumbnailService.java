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
import java.net.URL;

import eu.europeana.corelib.db.entity.nosql.ImageCache;
import eu.europeana.corelib.db.exception.DatabaseException;
import eu.europeana.corelib.db.service.abstracts.AbstractNoSqlService;
import eu.europeana.corelib.definitions.jibx.RDF;
import eu.europeana.corelib.definitions.model.ThumbSize;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * @author Georgios Markakis <gwarkx@hotmail.com>
 *
 * 6 Aug 2012
 */
public interface ThumbnailService extends AbstractNoSqlService<ImageCache, String> {

	String DEFAULT_IMAGEID = "0"; 
	String COMBINE_CHAR = "_";

	/**
	 * Create and store thumbnails of given images. This method also adds XMP metadata information
	 * to all generated thumbnails given the information found in the EDM xml document which is related 
	 * to theses images.
	 * 
	 * @param objectId permanent ID of image to store image.
	 * @param collectionId collection ID to which the object belongs.
	 * @param image A BufferedImage instance with the original image
	 * @param url The URL as a String of the original location of the image, can be null.
	 * @param edminfo the JIBX RDF representation of the original EDM "record"
	 * @return The stored entity.
	 * @throws DatabaseException 
	 */
	ImageCache storeThumbnail(String objectId, String collectionId, BufferedImage image, String url,RDF edmInfo) throws DatabaseException;

	/**
	 * Create and store thumbnails of given images.
	 * 
	 * @param objectId permanent ID of image to store image.
	 * @param collectionId collection ID to which the object belongs.
	 * @param image A BufferedImage instance with the original image
	 * @param url The URL as a String of the original location of the image, can be null.
	 * @return The stored entity.
	 * @throws DatabaseException 
	 */
	ImageCache storeThumbnail(String objectId, String collectionId, BufferedImage image, String url) throws DatabaseException;

	/**
	 * 
	 * @param objectId permanent ID of image to store image.
	 * @param imageId Alternative Image ID when there is more then one image for this object
	 * @param collectionId collection ID to which the object belongs.
	 * @param image A BufferedImage instance with the original image
	 * @param url The URL as a String of the original location of the image, can be null.
	 * @return The stored entity.
	 * @throws DatabaseException 
	 */
	ImageCache storeThumbnail(String objectId, String imageId, String collectionId, BufferedImage image, String url) throws DatabaseException;

	/**
	 * Downloads Image and Create and store thumbnails of given URL.
	 * 
	 * @param objectId permanent ID of image to store image.
	 * @param collectionId collection ID to which the object belongs.
	 * @param url URL object where to download the image to store.
	 * @return The stored entity.
	 * @throws DatabaseException
	 */
	ImageCache storeThumbnail(String objectId, String collectionId, URL url) throws DatabaseException;

	/**
	 * Downloads Image and Create and store thumbnails of given URL.
	 * 
	 * @param objectId permanent ID of image to store image.
	 * @param imageId Alternative Image ID when there is more then one image for this object
	 * @param collectionId collection ID to which the object belongs.
	 * @param url URL object where to download the image to store.
	 * @return The stored entity.
	 * @throws DatabaseException
	 */
	ImageCache storeThumbnail(String objectId, String imageId, String collectionId, URL url) throws DatabaseException;

	/**
	 * Retrieve a byte[] with the stored thumbnail, can be used for streaming the image.
	 * 
	 * @param objectId permanent ID of image to store image.
	 * @param size The ThumbSize of the requested image
	 * @return byte[] of thumbnail, or null of not found
	 */
	byte[] retrieveThumbnail(String objectId, ThumbSize size);

	/**
	 * Retrieve a byte[] with the stored thumbnail, can be used for streaming the image.
	 * 
	 * @param objectId permanent ID of image to store image.
	 * @param imageId Alternative Image ID when there is more then one image for this object
	 * @param size The ThumbSize of the requested image
	 * @return byte[] of thumbnail, or null of not found
	 */
	byte[] retrieveThumbnail(String objectId, String imageId, ThumbSize size);

	/**
	 * Find a ImageCache by it's original image url
	 * 
	 * @param url A String representation of the image url
	 * @return ImageCache entity if found, null if there are no matches
	 * @throws DatabaseException
	 */
	ImageCache findByOriginalUrl(String url) throws DatabaseException;

	/**
	 * Extended version of {@link #exists(String)} with a alternative imageId
	 * 
	 * @param objectId permanent ID of image to store image.
	 * @param imageId Alternative Image ID when there is more then one image for this object
	 * @return True if the image is stored in database
	 */
	abstract boolean exists(String objectId, String imageId);

	/**
	 * Extended version of {@link #findByID(String)} with a alternative imageId
	 * 
	 * @param objectId permanent ID of image to store image.
	 * @param imageId Alternative Image ID when there is more then one image for this object
	 * @return Returns a ImageCache object if found
	 */
	abstract ImageCache findByID(String objectId, String imageId);

	/**
	 * Extracts the XMP info contained in the Europeana thumbnail
	 * 
	 * @param objectId permanent ID of image to store image.
	 * @param imageId Alternative Image ID when there is more then one image for this object
	 * @param size The ThumbSize of the requested image
	 * @return the XMP info embedded in the thumbnail
	 */
	String extractXMPInfo(String objectId, String imageId,ThumbSize size) throws DatabaseException;
}
