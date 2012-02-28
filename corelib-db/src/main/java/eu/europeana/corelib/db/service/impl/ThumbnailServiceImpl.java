package eu.europeana.corelib.db.service.impl;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import eu.europeana.corelib.db.entity.nosql.Image;
import eu.europeana.corelib.db.entity.nosql.ImageCache;
import eu.europeana.corelib.db.repository.ImageCacheRepository;
import eu.europeana.corelib.db.service.ThumbnailService;
import eu.europeana.corelib.definitions.model.ThumbSize;
import eu.europeana.corelib.utils.ImageUtils;

public class ThumbnailServiceImpl implements ThumbnailService {
	
	@Resource
	private ImageCacheRepository repository;

	@Override
	public boolean storeThumbnail(String objectId, URL url) {
		try {
			BufferedImage originalImage = ImageIO.read(url);
			ImageCache cache = new ImageCache(objectId, originalImage);
			
			BufferedImage tiny = ImageUtils.scale(originalImage, ThumbSize.TINY.getMaxWidth(),
					ThumbSize.TINY.getMaxHeight());
			cache.getImages().put("TINY", new Image(tiny));
			
			BufferedImage medium = ImageUtils.scale(originalImage, ThumbSize.MEDIUM.getMaxWidth(),
					ThumbSize.MEDIUM.getMaxHeight());
			cache.getImages().put("MEDIUM", new Image(medium));

			BufferedImage large = ImageUtils.scale(originalImage, ThumbSize.LARGE.getMaxWidth(),
					ThumbSize.LARGE.getMaxHeight());
			cache.getImages().put("LARGE", new Image(large));
			
			repository.save(cache);
			
			return true;
			
		} catch (IOException e) {

		}
		return false;
	}

	@Override
	public byte[] retrieveThumbnail(String objectId, ThumbSize size) {
		ImageCache cache = repository.findOne(objectId);
		return cache.getImages().get(size.toString()).getImage();
	}

}
