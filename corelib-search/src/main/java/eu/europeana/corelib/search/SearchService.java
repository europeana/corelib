package eu.europeana.corelib.search;

import eu.europeana.corelib.definitions.edm.beans.IdBean;
import eu.europeana.corelib.definitions.solr.model.Query;
import eu.europeana.corelib.search.model.ResultSet;
import eu.europeana.corelib.web.exception.EuropeanaException;
import org.apache.solr.client.solrj.SolrClient;

import java.util.Date;

/**
 * Search service that retrieves BriefBeans or APIBeans from Solr
 *
 * @author Yorgos.Mamakis@ kb.nl
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * @author luthien
 * @author Patrick Ehlert (major refactoring April 2020)
 */
public interface SearchService {

    /**
     * Perform a calculateTag in SOLR based on the given query and return the results
     * in the format of the given class.
     *
     * @param solrClient     The underlying Solr client to use for query
     * @param beanInterface  The required bean type, should be ApiBean or BriefBean
     * @param query          Model class containing the calculateTag specification.
     * @param debug			 includes the string representing the Solrquery in the ResultSet
     * @return               The calculateTag results, including facets, breadcrumb and original query.
     * @throws EuropeanaException
     */
    <T extends IdBean> ResultSet<T> search(SolrClient solrClient, Class<T> beanInterface, Query query, boolean debug) throws EuropeanaException;
    /**
     * Perform a calculateTag in SOLR based on the given query and return the results
     * in the format of the given class.
     *
     * @param solrClient     The underlying Solr client to use for query
     * @param beanInterface  The required bean type, should be ApiBean or BriefBean
     * @param query          Model class containing the calculateTag specification.
     * @return               The calculateTag results, including facets, breadcrumb and original query.
     * @throws EuropeanaException
     */
    <T extends IdBean> ResultSet<T> search(SolrClient solrClient, Class<T> beanInterface, Query query) throws EuropeanaException;

    /**
     * @param solrClient     The underlying Solr client to use for query
     * @throws EuropeanaException
     * @return last modification time of Solr index
     */
    Date getLastSolrUpdate(SolrClient solrClient) throws EuropeanaException;
}
