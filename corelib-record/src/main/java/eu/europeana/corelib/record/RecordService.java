package eu.europeana.corelib.record;

import eu.europeana.corelib.definitions.edm.beans.FullBean;
import eu.europeana.corelib.edm.exceptions.BadDataException;
import eu.europeana.metis.mongo.EdmMongoServer;
import eu.europeana.corelib.web.exception.EuropeanaException;
import eu.europeana.metis.mongo.RecordRedirectDao;

/**
 * Retrieves CHO records from Mongo database.
 * If the provided recordId is not known, the redirect database can be checked to see if this recordId was known in the
 * past and has gotten a new id
 * @see FullBean
 *
 * @author Patrick Ehlert
 * Created on 4-4-2020
 */
public interface RecordService {

    /**
     * Retrieves a record including all it's {@link eu.europeana.corelib.definitions.edm.entity.WebResource}s
     *
     *
     * @param datasource Record server + Redirect DAO implementation to use for query
     * @param collectionId id of the collection to which this record belongs
     * @param recordId id of the record in a collection
     * @param urls Base urls to use in enriching bean
     * @return FullBean including WebResources
     * @throws EuropeanaException when there is an error retrieving the data
     */
    FullBean findById(DataSourceWrapper datasource, String collectionId, String recordId, BaseUrlWrapper urls) throws EuropeanaException;

    /**
     * Retrieves a record including all it's {@link eu.europeana.corelib.definitions.edm.entity.WebResource}s
     *
     * @param europeanaObjectId The unique europeana id
     * @param urls Base urls to use in enriching bean
     * @return FullBean including WebResources
     * @throws EuropeanaException when there is an error retrieving the data
     */
    FullBean findById(DataSourceWrapper datasource, String europeanaObjectId, BaseUrlWrapper urls) throws EuropeanaException;

    /**
     * Retrieve a record object from the database (without {@link eu.europeana.corelib.definitions.edm.entity.WebResource}s
     * Use {@link #enrichFullBean(EdmMongoServer, FullBean, BaseUrlWrapper)} to add the web resources meta info an attribution snippets
     *
     * @param europeanaObjectId The unique europeana id
     * @param resolveId if true and the record was not found, then the resolve method is used to check if this record has
     *                  a newId, if false then no missing record id will be resolved
     * @return FullBean with basic record information
     * @throws EuropeanaException when there is an error retrieving the data
     */
    FullBean fetchFullBean(DataSourceWrapper datasource, String europeanaObjectId, boolean resolveId) throws EuropeanaException;

    /**
     * This prepares a Fullbean retrieved from Mongo for use by Record API and other services
     * It adds:
     * <ul>
     *     <li>MetaInfo for all web resources</li>
     *     <li>Attribution snippet for all web resources</li>
     *     <li>Link to IIIF Manifest for newspaper items (that do not have referencedBy)</li>
     * </ul> and also makes sure that
     * <ul>
     *     <li>edmPreview has a proper thumbnail url</li>
     *     <li>edmLandingpage has a proper portal url</li>
     *     <li>providedCHO, aggregatedCHO and proxyFor values start with '/item'</li>
     * </ul>
     *
     * @param fullBean the basic fullbean we should enrich for usage
     * @return enriched full bean
     * @throws EuropeanaException when there is an error retrieving the data
     */
    FullBean enrichFullBean(EdmMongoServer mongoServer, FullBean fullBean, BaseUrlWrapper baseUrls) throws EuropeanaException;

    /**
     * Checks if an europeanaObjectId is old and has a newId. Note that this new id may also be old so we check iteratively
     * and return the newest id
     *
     * @param redirectDao
     * @param europeanaObjectId the old record id
     * @return a new record id, null if none was found
     * @throws BadDataException if a circular id reference is found
     * @deprecated (since April 2020)
     */
    @Deprecated // will be deprecated when the new redirect database goes live
    String resolveId(RecordRedirectDao redirectDao, String europeanaObjectId) throws BadDataException;

    /**
     *  Uses the provided collectionId and recordId to create an EuropeanaId and checks if that id is old and has a newId.
     * Note that this new id may also be old so we check iteratively and return the newest id
     * @param collectionId the collection Id
     * @param recordId     the record Id
     * @return a new record id, null if none was found
     * @throws BadDataException if a circular id reference is found
     * @deprecated (since April 2020)
     */
    @Deprecated // will be deprecated when the new redirect database goes live
    String resolveId(RecordRedirectDao redirectDao, String collectionId, String recordId) throws BadDataException;

}
