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

package eu.europeana.corelib.db.service.impl;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.springframework.util.Assert;

import eu.europeana.corelib.db.entity.nosql.Image;
import eu.europeana.corelib.db.entity.nosql.ImageCache;
import eu.europeana.corelib.db.repository.ImageCacheRepository;
import eu.europeana.corelib.db.service.ThumbnailService;
import eu.europeana.corelib.definitions.model.ThumbSize;
import eu.europeana.corelib.utils.ImageUtils;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public class ThumbnailServiceImpl implements ThumbnailService {

	@Resource
	private ImageCacheRepository repository;

	@Override
	public ImageCache storeThumbnail(String objectId, URL url) throws IOException {
		Assert.notNull(objectId);
		Assert.notNull(url);
		BufferedImage originalImage = ImageIO.read(url);
		return storeThumbnail(objectId, originalImage);
	}

	@Override
	public byte[] retrieveThumbnail(String objectId, ThumbSize size) {
		Assert.notNull(objectId);
		Assert.notNull(size);
		ImageCache cache = repository.findOne(objectId);
		if (cache != null) {
			return cache.getImages().get(size.toString()).getImage();
		}
		return null;
	}

	@Override
	public ImageCache storeThumbnail(String objectId, BufferedImage originalImage) throws IOException {
		Assert.notNull(objectId);
		Assert.notNull(originalImage);
		
		ImageCache cache = new ImageCache(objectId, originalImage);

		BufferedImage tiny = ImageUtils.scale(originalImage, ThumbSize.TINY.getMaxWidth(),
				ThumbSize.TINY.getMaxHeight());
		cache.getImages().put(ThumbSize.TINY.toString(), new Image(tiny));

		BufferedImage medium = ImageUtils.scale(originalImage, ThumbSize.MEDIUM.getMaxWidth(),
				ThumbSize.MEDIUM.getMaxHeight());
		cache.getImages().put(ThumbSize.MEDIUM.toString(), new Image(medium));

		BufferedImage large = ImageUtils.scale(originalImage, ThumbSize.LARGE.getMaxWidth(),
				ThumbSize.LARGE.getMaxHeight());
		cache.getImages().put(ThumbSize.LARGE.toString(), new Image(large));

		return repository.save(cache);
	}

}
