package eu.europeana.corelib.web.service;

import java.util.List;

import eu.europeana.harvester.domain.ImageMetaInfo;

public interface ContentReuseFrameworkService {

	List<ImageMetaInfo> getMetadata(String recordId);
}
