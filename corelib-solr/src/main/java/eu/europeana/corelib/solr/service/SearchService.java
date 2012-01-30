package eu.europeana.corelib.solr.service;

import eu.europeana.corelib.solr.bean.FullBean;
import eu.europeana.corelib.solr.bean.IdBean;
import eu.europeana.corelib.solr.exceptions.SolrTypeException;
import eu.europeana.corelib.solr.model.Query;
import eu.europeana.corelib.solr.model.ResultSet;

public interface SearchService {
	
	/**
	 * Retrieve a record by id.
	 * 
	 * @param europeanaObjectId - The unique europeana id
	 * @return A full europeana record
	 */
	FullBean findById(String europeanaObjectId);
	
	/**
	 * Perform a search in SOLR based on the given query and return the results in the format of the given class.
	 * 
	 * @param beanClazz The required bean type, should be ApiBean or BriefBean
	 * @param query Model class containing the search specification.
	 * @return The search results, including facets, breadcrumb and original query.
	 * @throws SolrTypeException 
	 */
	<T extends IdBean> ResultSet<T> search(Class<T> beanClazz, Query query) throws SolrTypeException;

}
