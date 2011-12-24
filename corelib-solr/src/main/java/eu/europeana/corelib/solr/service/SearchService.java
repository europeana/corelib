package eu.europeana.corelib.solr.service;

import eu.europeana.corelib.solr.bean.FullBean;

public interface SearchService {
	
	/**
	 * 
	 * @param europeanaObjectId
	 * @return
	 */
	FullBean findById(String europeanaObjectId);

}
