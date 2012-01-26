/*
 * Copyright 2007 EDL FOUNDATION
 *
 * Licensed under the EUPL, Version 1.0 or? as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 * you may not use this work except in compliance with the
 * Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://ec.europa.eu/idabc/eupl
 *
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
 */

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
	 * @param europeanaObjectId
	 * @return FullBean or 
	 */
	FullBean findById(String europeanaObjectId);
	
	/**
	 * Perform a search in SOLR based on the given query and return the results in the format of the given class.
	 * 
	 * @param beanClazz The required bean type, should be ApiBean or BriefBean
	 * @param query Model class containing the search specification.
	 * @return The search results, including facets, breadcrumb and original query.
	 */
	<T extends IdBean> ResultSet<T> search(Class<T> beanClazz, Query query) throws SolrTypeException;

}
