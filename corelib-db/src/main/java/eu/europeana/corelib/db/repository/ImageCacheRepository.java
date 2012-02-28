package eu.europeana.corelib.db.repository;

import org.springframework.data.repository.CrudRepository;

import eu.europeana.corelib.db.entity.nosql.ImageCache;

public interface ImageCacheRepository extends CrudRepository<ImageCache, String> {

}
