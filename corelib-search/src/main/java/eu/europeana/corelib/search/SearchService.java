package eu.europeana.corelib.search;

import eu.europeana.corelib.definitions.edm.beans.FullBean;
import eu.europeana.corelib.definitions.edm.beans.IdBean;
import eu.europeana.corelib.definitions.solr.model.Query;
import eu.europeana.corelib.edm.exceptions.BadDataException;
import eu.europeana.corelib.search.model.ResultSet;
import eu.europeana.corelib.web.exception.EuropeanaException;
import org.apache.solr.client.solrj.response.FacetField;

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
 * @author Willem-Jan Boogerd
 * @author luthien
 */
public interface SearchService {

    /**
     * Retrieve a record by europeanaObjectId (no further processing)
     *
     * @param europeanaObjectId The unique europeana id
     * @return A full Europeana record
     * @throws EuropeanaException;
     */
    FullBean fetchFullBean(String europeanaObjectId) throws EuropeanaException;

    /**
     * (optionally) adding similar items to a FullBean, etcetera
     *
     * @param fullBean The FullBean to be processed (injectWebMetaInfoBatch etc.)
     * @return processed full Europeana record
     */
    FullBean processFullBean(FullBean fullBean);

    /**
     * Checks if a redirected newId is available for a EuropeanaId that wasn't found in the regular collection.
     * This method now calls the Metis RecordRedirectDao, eliminating the need to iterate the operation to retrieve
     * the most recent redirection
     *
     * @param europeanaObjectId the old record id
     * @return a new record id, null if none was found
     */
    String resolve(String europeanaObjectId);

    /**
     * Perform a calculateTag in SOLR based on the given query and return the results
     * in the format of the given class.
     *
     * @param beanInterface The required bean type, should be ApiBean or BriefBean
     * @param query         Model class containing the calculateTag specification.
     * @param debug         includes the string representing the Solrquery in the ResultSet
     * @return The calculateTag results, including facets, breadcrumb and original query.
     * @throws EuropeanaException;
     */

    <T extends IdBean> ResultSet<T> search(Class<T> beanInterface, Query query, boolean debug) throws
                                                                                               EuropeanaException;

    /**
     * Perform a calculateTag in SOLR based on the given query and return the results
     * in the format of the given class.
     *
     * @param beanInterface The required bean type, should be ApiBean or BriefBean
     * @param query         Model class containing the calculateTag specification.
     * @return The calculateTag results, including facets, breadcrumb and original query.
     * @throws EuropeanaException;
     */
    <T extends IdBean> ResultSet<T> search(Class<T> beanInterface, Query query) throws EuropeanaException;

    /**
     * Returns a list of "see also" suggestions. The suggestions are organized
     * by fields (who, what, where, when, and title). Each suggestion contains a
     * field value and the number of documents it matches.
     *
     * @param queries Map of field names, and corresponding field values.
     * @return list of see also suggestions
     */
    Map<String, Integer> seeAlso(List<String> queries);

    /**
     * @return last modification time of Solr index
     * @throws EuropeanaException;
     */
    Date getLastSolrUpdate() throws EuropeanaException;

    Map<String, Integer> queryFacetSearch(String query, String[] qf, List<String> queries);


    // ----------------------------------------------------------------- //
    // --> CODE BELOW THIS LINE IS DEPRECATED OR NOT USED ANY LONGER <-- //
    // ----------------------------------------------------------------- //


    /**
     * Uses the provided collectionId and recordId to create an EuropeanaId and checks if that id is old and has a newId.
     * Note that this new id may also be old so we check iteratively and return the newest id
     *
     * @param collectionId the collection Id
     * @param recordId     the record Id
     * @return a new record id, null if none was found
     * @deprecated by EA-1907
     */
    @Deprecated
    String resolve(String collectionId, String recordId) throws BadDataException;

    /**
     * Create collection list for a given query and facet field
     *
     * @param facetFieldName The SolrFacetType field to create the collection for
     * @param queryString    The Query to use for creating the collection
     * @param refinements    Optional refinements
     * @return A List of FacetField.Count objects containing the collection
     * @throws EuropeanaException;
     * @deprecated method is never called
     */
    @Deprecated
    List<FacetField.Count> createCollections(String facetFieldName, String queryString, String... refinements) throws
                                                                                                               EuropeanaException;

    /**
     * (optionally) adding similar items to a FullBean, etcetera
     *
     * @param fullBean          The FullBean to be processed (injectWebMetaInfoBatch etc.)
     * @param europeanaObjectId The unique europeana id
     * @param similarItems      whether to retrieve similar items
     * @return processed full europeana record
     * @deprecated sept 2019, similarItems are not supported anymore
     */
    @Deprecated
    FullBean processFullBean(FullBean fullBean, String europeanaObjectId, boolean similarItems);

    /**
     * Retrieves a record by collectionId and recordId and calling processFullBean() afterwards
     *
     * @param collectionId id of the collection to which this record belongs
     * @param recordId
     * @param similarItems whether to retrieve similar items
     * @return A full europeana record
     * @throws EuropeanaException
     * @deprecated sept 2019, similarItems are not supported anymore
     */
    @Deprecated
    FullBean findById(String collectionId, String recordId, boolean similarItems) throws EuropeanaException;

    /**
     * Retrieves a record by collectionId and recordId and calling processFullBean() afterwards
     *
     * @param collectionId id of the collection to which this record belongs
     * @param recordId
     * @return A full europeana record
     * @throws EuropeanaException
     * @deprecated sept 2019, similarItems are not supported anymore
     */
    @Deprecated
    FullBean findById(String collectionId, String recordId) throws EuropeanaException;

    /**
     * Retrieve a record by id and calling processFullBean() afterwards
     *
     * @param europeanaObjectId The unique europeana id
     * @param similarItems      Whether to retrieve similar items
     * @return A full europeana record
     * @throws EuropeanaException
     * @deprecated sept 2019, similarItems are not supported anymore
     */
    @Deprecated
    FullBean findById(String europeanaObjectId, boolean similarItems) throws EuropeanaException;

    /**
     * Retrieve a record by id and calling processFullBean() afterwards
     *
     * @param europeanaObjectId The unique europeana id
     * @return A full europeana record
     * @throws EuropeanaException
     * @deprecated sept 2019, similarItems are not supported anymore
     */
    @Deprecated
    FullBean findById(String europeanaObjectId) throws EuropeanaException;

}
