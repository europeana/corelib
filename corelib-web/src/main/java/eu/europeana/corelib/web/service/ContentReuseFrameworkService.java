package eu.europeana.corelib.web.service;

import eu.europeana.harvester.domain.SourceDocumentReferenceMetaInfo;

/**
 * @deprecated
 * CRF is not accessed directly by API and Metis
 */
@Deprecated
public interface ContentReuseFrameworkService {

	SourceDocumentReferenceMetaInfo getMetadata(String recordId);
}
