package eu.europeana.corelib.web.service.impl;

import java.util.List;

import javax.annotation.Resource;

import eu.europeana.corelib.web.service.ContentReuseFrameworkService;
import eu.europeana.harvester.domain.ImageMetaInfo;
import eu.europeana.harvester.client.HarvesterClientImpl;

public class ContentReuseFrameworkServiceImpl implements ContentReuseFrameworkService {

	@Resource
	HarvesterClientImpl harvesterClient;

	@Override
	public List<ImageMetaInfo> getMetadata(String recordId) {
		//harvesterClient.createOrModifySourceDocumentReference(arg0);
		return null;
	}

}
