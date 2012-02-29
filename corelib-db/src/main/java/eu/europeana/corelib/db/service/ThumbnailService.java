package eu.europeana.corelib.db.service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import eu.europeana.corelib.db.entity.nosql.ImageCache;
import eu.europeana.corelib.definitions.model.ThumbSize;

public interface ThumbnailService {
	
	ImageCache storeThumbnail(String objectId, BufferedImage image) throws IOException;
	
	ImageCache storeThumbnail(String objectId, URL url) throws IOException;
	
	byte[] retrieveThumbnail(String objectId, ThumbSize size);
	
	void deleteThumbnail(String objectId);

}
