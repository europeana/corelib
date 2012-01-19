package eu.europeana.corelib.solr.service;

import java.util.List;

import eu.europeana.corelib.solr.bean.FullBean;
import eu.europeana.corelib.solr.bean.IdBean;
import eu.europeana.corelib.solr.model.Query;

public interface SearchService {
	
	/**
	 * 
	 * @param europeanaObjectId
	 * @return
	 */
	FullBean findById(String europeanaObjectId);
	
	<T extends IdBean> List<T> search(Class<T> beanClazz, Query query);

}
