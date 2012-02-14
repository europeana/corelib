/*
 * Copyright 2007-2012 The Europeana Foundation
 *
 *  Licenced under the EUPL, Version 1.1 (the "Licence") and subsequent versions as approved
 *  by the European Commission;
 *  You may not use this work except in compliance with the Licence.
 * 
 *  You may obtain a copy of the Licence at:
 *  http://joinup.ec.europa.eu/software/page/eupl
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under
 *  the Licence is distributed on an "AS IS" basis, without warranties or conditions of
 *  any kind, either express or implied.
 *  See the Licence for the specific language governing permissions and limitations under
 *  the Licence.
 */

package eu.europeana.corelib.solr.service;

import eu.europeana.corelib.definitions.solr.beans.FullBean;
import eu.europeana.corelib.definitions.solr.beans.IdBean;
import eu.europeana.corelib.solr.exceptions.SolrTypeException;
import eu.europeana.corelib.solr.model.Query;
import eu.europeana.corelib.solr.model.ResultSet;

/**
 * Search service that retrieves BriefBeans or APIBeans in the case of a query search or a FullBean in the case of a
 * user selection. Currently the implementation uses SOLR for Brief/APIBeans and MongoDB for FullBean retrieval.
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
public interface SearchService {

	/**
	 * Retrieve a record by id.
	 * 
	 * @param europeanaObjectId
	 *            - The unique europeana id
	 * @return A full europeana record
	 * @throws SolrTypeException
	 */
	FullBean findById(String europeanaObjectId) throws SolrTypeException;

	/**
	 * Perform a search in SOLR based on the given query and return the results in the format of the given class.
	 * 
	 * @param beanClazz
	 *            The required bean type, should be ApiBean or BriefBean
	 * @param query
	 *            Model class containing the search specification.
	 * @return The search results, including facets, breadcrumb and original query.
	 * @throws SolrTypeException
	 */
	<T extends IdBean> ResultSet<T> search(Class<T> beanClazz, Query query) throws SolrTypeException;

}
