package eu.europeana.corelib.web.service;

import eu.europeana.harvester.domain.SourceDocumentReferenceMetaInfo;

public interface ContentReuseFrameworkService {

	SourceDocumentReferenceMetaInfo getMetadata(String recordId);
}
