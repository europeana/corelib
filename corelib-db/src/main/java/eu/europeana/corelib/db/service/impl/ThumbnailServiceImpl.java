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
	public ImageCache storeThumbnail(String objectId, URL url) throws IOException {
		BufferedImage originalImage = ImageIO.read(url);
		return storeThumbnail(objectId, originalImage);
	}

	@Override
	public byte[] retrieveThumbnail(String objectId, ThumbSize size) {
		ImageCache cache = repository.findOne(objectId);
		return cache.getImages().get(size.toString()).getImage();
	}
	
	@Override
	public ImageCache storeThumbnail(String objectId, BufferedImage originalImage) throws IOException {
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
