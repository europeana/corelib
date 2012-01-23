package eu.europeana.corelib.solr.service;

import eu.europeana.corelib.solr.bean.FullBean;
import eu.europeana.corelib.solr.bean.IdBean;
import eu.europeana.corelib.solr.model.Query;
import eu.europeana.corelib.solr.model.ResultSet;

public interface SearchService {
	
	/**
	 * 
	 * @param europeanaObjectId
	 * @return
	 */
	FullBean findById(String europeanaObjectId);
	
	<T extends IdBean> ResultSet<T> search(Class<T> beanClazz, Query query);

}
