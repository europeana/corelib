package eu.europeana.corelib.record;

import eu.europeana.corelib.definitions.edm.beans.FullBean;
import eu.europeana.corelib.web.exception.EuropeanaException;
import eu.europeana.metis.mongo.dao.RecordDao;
import eu.europeana.metis.mongo.dao.RecordRedirectDao;

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
     * @param recordDao the dao of the database to load the data from
     * @param collectionId id of the collection to which this record belongs
     * @param recordId id of the record in a collection
     * @param urls Base urls to use in enriching bean
     * @return FullBean including WebResources
     * @throws EuropeanaException when there is an error retrieving the data
     */
    FullBean findById(RecordDao recordDao, String collectionId, String recordId, BaseUrlWrapper urls) throws EuropeanaException;

    /**
     * Retrieves a record including all it's {@link eu.europeana.corelib.definitions.edm.entity.WebResource}s
     *
     * @param recordDao the dao of the database to load the data from
     * @param europeanaObjectId The unique europeana id
     * @param urls Base urls to use in enriching bean
     * @return FullBean including WebResources
     * @throws EuropeanaException when there is an error retrieving the data
     */
    FullBean findById(RecordDao recordDao, String europeanaObjectId, BaseUrlWrapper urls) throws EuropeanaException;

    /**
     * Retrieve a record object from the database (without {@link eu.europeana.corelib.definitions.edm.entity.WebResource}s
     * Use {@link #enrichFullBean(RecordDao, FullBean, BaseUrlWrapper)} to add the web resources meta info and attribution snippets
     *
     * @param recordDao the recordDao to load data from
     * @param europeanaObjectId the id of the record to retrieve
     * @return FullBean with basic record information
     * @throws EuropeanaException when there is an error retrieving the data
     */
    FullBean fetchFullBean(RecordDao recordDao, String europeanaObjectId) throws EuropeanaException;

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
     * @param recordDao the recordDao to load data from
     * @param fullBean the basic fullbean we should enrich for usage
     * @param baseUrls urls from the configuration to use while enriching the fullbean
     * @return enriched full bean
     * @throws EuropeanaException when there is an error retrieving the data
     */
    FullBean enrichFullBean(RecordDao recordDao, FullBean fullBean, BaseUrlWrapper baseUrls) throws EuropeanaException;

    /**
     * Retrieve a record object from the tombstone database (if available)
     * @param recordDao the recordDao to load data from
     * @param europeanaObjectId the id of the record to retrieve
     * @return tombstone fullbean if available, otherwise null
     * @throws EuropeanaException if there is an error loading the tombstone record
     */
    FullBean fetchTombstone(RecordDao recordDao, String europeanaObjectId) throws EuropeanaException;

    /**
     * Retrieve the latest id of a record that has a redirect
     *
     * @param redirectDao the redirectDao to load the data from
     * @param europeanaObjectId the old record id
     * @return a new record id, null if none was found
     */
    String resolveId(RecordRedirectDao redirectDao, String europeanaObjectId);

}
