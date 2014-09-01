package eu.europeana.corelib.web.service.impl;

import javax.annotation.Resource;

import eu.europeana.corelib.web.service.ContentReuseFrameworkService;
import eu.europeana.harvester.domain.SourceDocumentReferenceMetaInfo;
import eu.europeana.harvester.client.HarvesterClientImpl;

public class ContentReuseFrameworkServiceImpl implements ContentReuseFrameworkService {

	@Resource
	HarvesterClientImpl harvesterClient;

	public ContentReuseFrameworkServiceImpl() {}

	@Override
	public SourceDocumentReferenceMetaInfo getMetadata(String url) {
		return harvesterClient.retrieveMetaInfoByUrl(url);
	}

}
