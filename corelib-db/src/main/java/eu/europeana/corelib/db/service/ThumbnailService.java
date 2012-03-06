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
import java.io.IOException;
import java.net.URL;

import eu.europeana.corelib.db.entity.nosql.ImageCache;
import eu.europeana.corelib.db.service.abstracts.AbstractNoSqlService;
import eu.europeana.corelib.definitions.model.ThumbSize;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public interface ThumbnailService extends AbstractNoSqlService<ImageCache, String> {
	
	ImageCache storeThumbnail(String objectId, BufferedImage image) throws IOException;
	
	ImageCache storeThumbnail(String objectId, URL url) throws IOException;
	
	byte[] retrieveThumbnail(String objectId, ThumbSize size);
	
}
