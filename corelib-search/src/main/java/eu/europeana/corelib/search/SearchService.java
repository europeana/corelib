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

package eu.europeana.corelib.search;

import eu.europeana.corelib.definitions.edm.beans.BriefBean;
import eu.europeana.corelib.definitions.edm.beans.FullBean;
import eu.europeana.corelib.definitions.edm.beans.IdBean;
import eu.europeana.corelib.definitions.solr.model.Query;
import eu.europeana.corelib.definitions.solr.model.Term;
import eu.europeana.corelib.edm.exceptions.BadDataException;
import eu.europeana.corelib.edm.exceptions.MongoDBException;
import eu.europeana.corelib.edm.exceptions.MongoRuntimeException;
import eu.europeana.corelib.edm.exceptions.SolrTypeException;
import eu.europeana.corelib.search.model.ResultSet;
import eu.europeana.corelib.web.exception.EuropeanaException;
import org.apache.solr.client.solrj.response.FacetField;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Search service that retrieves BriefBeans or APIBeans in the case of a query
 * calculateTag or a FullBean in the case of a user selection. Currently the
 * implementation uses SOLR for Brief/APIBeans and MongoDB for FullBean
 * retrieval.
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public interface SearchService {

	/**
	 * Retrieve a record by europeanaObjectId (no further processing)
	 *
	 * @param europeanaObjectId The unique europeana id
	 * @return                  A full europeana record
	 * @throws EuropeanaException
	 */
	FullBean fetchFullBean(String europeanaObjectId) throws EuropeanaException;

	/**
	 * (optionally) adding similar items to a FullBean, etcetera
	 *
	 * @param fullBean 			The FullBean to be processed (injectWebMetaInfoBatch etc.)
	 * @param europeanaObjectId The unique europeana id
	 * @param similarItems  	whether to retrieve similar items
	 * @return                  processed full europeana record
	 * @throws EuropeanaException
	 */
	FullBean processFullBean(FullBean fullBean, String europeanaObjectId, boolean similarItems);

	/**
	 * Retrieves a record by collectionId and recordId and calling processFullBean() afterwards
	 *
	 * @param collectionId id of the collection to which this record belongs
	 * @param recordId
	 * @param similarItems  whether to retrieve similar items
	 * @return                  A full europeana record
	 * @throws EuropeanaException
	 */
	FullBean findById(String collectionId, String recordId, boolean similarItems) throws EuropeanaException;

	/**
	 * Retrieve a record by id and calling processFullBean() afterwards
	 *
	 * @param europeanaObjectId The unique europeana id
	 * @param similarItems      Whether to retrieve similar items
	 * @return                  A full europeana record
	 * @throws EuropeanaException
	 */
	FullBean findById(String europeanaObjectId, boolean similarItems) throws EuropeanaException;

	/**
	 * Retrieve a record by id. If the record cannot be found, it will retry
	 * 
     * @param europeanaObjectId The unique europeana id
     * @param similarItems      Whether to retrieve similar items
	 * @return                  A full europeana record
	 * @throws                  SolrTypeException
	 */
	FullBean resolve(String europeanaObjectId, boolean similarItems)
			throws SolrTypeException;

	/**
	 * Retrieve a record by splitted collectionId and recordId
	 * 
     * @param collectionId id of the collection to which this record belongs
	 * @param recordId
     * @param similarItems      Whether to retrieve similar items
	 * @return                  A full europeana record
	 * @throws                  SolrTypeException
	 */
	FullBean resolve(String collectionId, String recordId, boolean similarItems)
			throws SolrTypeException;

	/**
	 * Checks if an europeanaObjectId is old and has a newId. Note that this new id may also be old so we check iteratively
	 * and return the newest id
	 * @param europeanaObjectId the old record id
	 * @return a new record id, null if none was found
	 * @throws BadDataException if a circular id reference is found
	 */
	String resolveId(String europeanaObjectId) throws BadDataException;

	/**
	 *  Uses the provided collectionId and recordId to create an EuropeanaId and checks if that id is old and has a newId.
	 * Note that this new id may also be old so we check iteratively and return the newest id
     * @param collectionId the collection Id
     * @param recordId     the record Id
     * @return a new record id, null if none was found
     * @throws BadDataException if a circular id reference is found
     */
	String resolveId(String collectionId, String recordId) throws BadDataException;

	/**
	 * Perform a calculateTag in SOLR based on the given query and return the results
	 * in the format of the given class.
	 *
	 * @param beanInterface  The required bean type, should be ApiBean or BriefBean
	 * @param query          Model class containing the calculateTag specification.
	 * @param debug			 includes the string representing the Solrquery in the ResultSet
	 * @return               The calculateTag results, including facets, breadcrumb and original query.
	 * @throws EuropeanaException
	 */
	<T extends IdBean> ResultSet<T> search(Class<T> beanInterface, Query query, boolean debug) throws EuropeanaException;
	/**
	 * Perform a calculateTag in SOLR based on the given query and return the results
	 * in the format of the given class.
	 * 
	 * @param beanInterface  The required bean type, should be ApiBean or BriefBean
	 * @param query          Model class containing the calculateTag specification.
	 * @return               The calculateTag results, including facets, breadcrumb and original query.
	 * @throws EuropeanaException;
	 */
	<T extends IdBean> ResultSet<T> search(Class<T> beanInterface, Query query) throws EuropeanaException;

	/**
	 * Create collection list for a given query and facet field
	 * 
     * @param facetFieldName The SolrFacetType field to create the collection for
     * @param queryString    The Query to use for creating the collection
     * @param refinements    Optional refinements
	 * @return               A List of FacetField.Count objects containing the collection
	 * @throws               EuropeanaException;
	 * 
	 */
	List<FacetField.Count> createCollections(String facetFieldName,
			String queryString, String... refinements) throws EuropeanaException;

	/**
	 * returns a list of calculateTag suggestions and frequencies
	 * 
	 * @param query    The calculateTag term to find suggestions for
	 * @param pageSize Amount of requested suggestions
	 * @return         List of calculateTag suggestions
	 * @throws         EuropeanaException;
	 * @deprecated as of September 2018 (not working properly and very little usage)
	 */
	@Deprecated
	List<Term> suggestions(String query, int pageSize) throws EuropeanaException;

	/**
	 * returns a list of calculateTag suggestions and frequencies
	 * 
	 * @param query     The calculateTag term to find suggestions for
	 * @param pageSize  Amount of requested suggestions
	 * @return          List of calculateTag suggestions
	 * @throws          EuropeanaException;
	 */
	List<Term> suggestions(String query, int pageSize, String field) throws EuropeanaException;

	/**
	 * Retrieves the moreLikeThis List of BriefBeans
	 * 
	 * @param  europeanaObjectId
	 * @return moreLikeThis List of BriefBeans
	 * @throws EuropeanaException;
	 * @deprectated January 2018 - FMLT isn't used anymore
	 */
	@Deprecated
	List<BriefBean> findMoreLikeThis(String europeanaObjectId) throws EuropeanaException;;

	/**
	 * Returns a list of "see also" suggestions. The suggestions are organized
	 * by fields (who, what, where, when, and title). Each suggestion contains a
	 * field value and the number of documents it matches.
	 * 
     * @param  queries Map of field names, and corresponding field values.
	 * @return list of see also suggestions
	 */
	Map<String, Integer> seeAlso(List<String> queries);

	/**
	 * Returns a specified number of moreLikeThis objects
	 * 
	 * @param  europeanaObjectId
	 * @param  count
	 * @return a specified number of moreLikeThis objects
	 * @throws EuropeanaException;
	 * @deprectated January 2018 - FMLT isn't used anymore
	 */

	@Deprecated
	List<BriefBean> findMoreLikeThis(String europeanaObjectId, int count) throws EuropeanaException;

	/**
	 * @throws EuropeanaException;
	 * @throws IOException
	 * @return last modification time of Solr index
	 */
	Date getLastSolrUpdate() throws EuropeanaException;

	Map<String, Integer> queryFacetSearch(String query, String[] qf, List<String> queries);
}
