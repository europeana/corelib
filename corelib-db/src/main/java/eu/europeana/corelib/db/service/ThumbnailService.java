package eu.europeana.corelib.db.service;

import java.net.URL;

import eu.europeana.corelib.definitions.model.ThumbSize;

public interface ThumbnailService {
	
	boolean storeThumbnail(String objectId, URL url);
	
	byte[] retrieveThumbnail(String objectId, ThumbSize size);

}
