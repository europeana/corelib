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
     * Retrieve a record by europeanaId (no further processing)
     *
     * @param europeanaId The unique europeana id
     * @return A full Europeana record
     * @throws EuropeanaException;
     */
    FullBean fetchFullBean(String europeanaId) throws EuropeanaException;

    /**
     * Adds meta-info and the Attribution snippet to all webresources of the FullBean
     *
     * @param fullBean The FullBean to be processed (injectWebMetaInfoBatch etc.)
     * @return processed full Europeana record
     */
    FullBean processFullBean(FullBean fullBean);

    /**
     * Retrieves a record by collectionId and recordId and calling processFullBean() afterwards
     *
     * @param collectionId id of the collection to which this record belongs
     * @param recordId
     * @return A full europeana record
     * @throws EuropeanaException
     */
    FullBean findById(String collectionId, String recordId) throws EuropeanaException;

    /**
     * Retrieve a record by id and calling processFullBean() afterwards
     *
     * @param europeanaId The unique europeana id
     * @return A full europeana record
     * @throws EuropeanaException
     */
    FullBean findById(String europeanaId) throws EuropeanaException;

    /**
     * Checks if a redirected newId is available for a EuropeanaId that wasn't found in the regular collection
     * and returns that if found.
     *
     * @param europeanaId the old record id
     * @return a new record id, null if none was found
     */
    String resolve(String europeanaId);

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

}
