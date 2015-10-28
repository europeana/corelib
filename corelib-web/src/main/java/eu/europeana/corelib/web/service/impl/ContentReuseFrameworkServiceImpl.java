package eu.europeana.corelib.web.service.impl;

import javax.annotation.Resource;

import eu.europeana.corelib.web.service.ContentReuseFrameworkService;
import eu.europeana.harvester.client.HarvesterClient;
import eu.europeana.harvester.domain.SourceDocumentReferenceMetaInfo;

public class ContentReuseFrameworkServiceImpl implements ContentReuseFrameworkService {

	@Resource
	private HarvesterClient harvesterClient;

	public ContentReuseFrameworkServiceImpl() {}

	@Override
	public SourceDocumentReferenceMetaInfo getMetadata(String url) {
		return harvesterClient.retrieveMetaInfoByUrl(url);
	}

}
