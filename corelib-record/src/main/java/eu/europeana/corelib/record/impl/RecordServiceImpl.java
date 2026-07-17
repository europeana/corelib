package eu.europeana.corelib.record.impl;

import dev.morphia.mapping.MappingException;
import eu.europeana.corelib.definitions.edm.beans.FullBean;
import eu.europeana.corelib.edm.exceptions.MongoDBException;
import eu.europeana.corelib.edm.exceptions.MongoRuntimeException;
import eu.europeana.corelib.edm.utils.ProxyAggregationUtils;
import eu.europeana.corelib.record.BaseUrlWrapper;
import eu.europeana.corelib.record.RecordService;
import eu.europeana.corelib.record.api.IIIFLink;
import eu.europeana.corelib.record.api.UrlConverter;
import eu.europeana.corelib.record.api.WebMetaInfo;
import eu.europeana.corelib.utils.EuropeanaUriUtils;
import eu.europeana.corelib.web.exception.EuropeanaException;
import eu.europeana.corelib.web.exception.ProblemType;
import eu.europeana.metis.mongo.dao.RecordDao;
import eu.europeana.metis.mongo.dao.RecordRedirectDao;
import eu.europeana.metis.mongo.model.RecordRedirect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Optional;

/**
 * Retrieves CHO records from Mongo database.
 * If the provided recordId is not known, the redirect database can be checked to see if this recordId was known in the
 * past and has gotten a new id
 * @see FullBean
 * @see RecordService
 *
 * @author Patrick Ehlert
 * Created on 4-4-2020
 */
public class RecordServiceImpl implements RecordService {

    private static final Logger LOG = LogManager.getLogger(RecordServiceImpl.class);

    @Value("#{europeanaProperties['iiifManifest.baseUrl']}")
    private String manifestBaseUrl;
    @Value("#{europeanaProperties['manifest.add.apiUrl']}")
    private Boolean manifestAddApiUrl;
    @Value("#{europeanaProperties['htmlsnippet.css.source']}")
    private String attributionCss;

    /**
     * @see RecordService#findById(RecordDao recordDao, String, String, BaseUrlWrapper)
     */
    @Override
    public FullBean findById(RecordDao recordDao, String collectionId, String recordId, BaseUrlWrapper urls) throws EuropeanaException {
        return findById(recordDao, EuropeanaUriUtils.createEuropeanaId(collectionId, recordId), urls);
    }

    /**
     * @see RecordService#findById(RecordDao recordDao, String, BaseUrlWrapper)
     */
    @Override
    public FullBean findById(RecordDao recordDao, String europeanaObjectId, BaseUrlWrapper urls) throws EuropeanaException {
        FullBean fullBean = fetchFullBean(recordDao, europeanaObjectId);

        if (fullBean != null) {
            return enrichFullBean(recordDao, fullBean, urls);
        } else {
            return null;
        }
    }

    /**
     * Fetches the full record details as a FullBean object using the provided RecordDao
     * and Europeana object ID. If the record is not found, null is returned.
     *
     * IMPORTANT: this already fetches the WebMetaInfo. the method internally calls the
     *           {@link WebMetaInfo#injectWebMetaInfoBatch(FullBean, RecordDao, String)}
     *
     * @param recordDao The data access object used to fetch the record from the underlying data source.
     * @param europeanaObjectId The unique identifier of the record to be fetched.
     * @return The FullBean object representing the full record details, or null if no record is found.
     * @throws EuropeanaException If there is an error while fetching the record.
     */
    @Override
    public FullBean fetchFullBean(RecordDao recordDao, String europeanaObjectId) throws EuropeanaException {
        try {
            long startTime = System.currentTimeMillis();
            Optional<FullBean> fullBean = recordDao.getRecord(europeanaObjectId);
            if (LOG.isDebugEnabled()) {
                LOG.debug("Load FullBean {} from db {} took {} ms, result = {}",
                        europeanaObjectId, recordDao, (System.currentTimeMillis() - startTime), fullBean);
            }
            return fullBean.isPresent() ? fullBean.get() : null;
        } catch (RuntimeException re) { // the exception handling is removed from metis-mongo and moved in corelib
            throw processException(re);
        }
    }

    /**
     * Enhances a {@link FullBean} object with additional metadata and URL configurations.
     * This method performs several operations such as ordering proxies and aggregations,
     * injecting metadata, generating IIIF links, and setting proper thumbnail and portal URLs.
     *
     * @param recordDao The data access object used to retrieve metadata for the FullBean.
     * @param fullBean The FullBean object to be enriched with additional metadata and configurations.
     * @param urls A wrapper containing base URLs used for generating APIs and portal links.
     * @return The enriched FullBean object after applying all necessary modifications.
     */
    public FullBean enrichFullBean(RecordDao recordDao, FullBean fullBean, BaseUrlWrapper urls){

        // 1. order the proxy and aggregation
        fullBean.setProxies(ProxyAggregationUtils.orderProxy(fullBean));
        fullBean.setAggregations(ProxyAggregationUtils.orderAggregation(fullBean));

        // 2. add meta info for all webresources + generate attribution snippets
        long startTime = System.currentTimeMillis();
       // WebMetaInfo.injectWebMetaInfoBatch(fullBean, recordDao, attributionCss);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Loading {} webresources from db {} took {} ms",
                    fullBean.getEuropeanaAggregation().getWebResources().size(), recordDao, (System.currentTimeMillis() - startTime));
        }

        // 3. add link to IIIF for newspaper and AV/EUScreen items. Also adds the manifest resources for the IIIF links.
        IIIFLink.addReferencedByAndManifestResources(fullBean, manifestAddApiUrl, urls.getApi2BaseUrl(), manifestBaseUrl);

        // 4. make sure we add /item in various places
        UrlConverter.addSlashItem(fullBean);

        // 5. generate proper edmPreview thumbnail urls
        UrlConverter.setEdmPreview(fullBean, urls.getApiGatewayBaseUrl());

        // 6. generate proper edmLandingpage portal urls
        UrlConverter.setEdmLandingPage(fullBean, urls.getPortalBaseUrl());

        return fullBean;
    }

    /**
     * @see RecordService#fetchTombstone(RecordDao recordDao, String)
     */
    @Override
    public FullBean fetchTombstone(RecordDao recordDao, String europeanaObjectId) throws EuropeanaException {
        try {
            long startTime = System.currentTimeMillis();
            // using getFullBean as we don't need to fetch web meta infos
            FullBean result = recordDao.getFullBean(europeanaObjectId);
            if (LOG.isDebugEnabled()) {
                LOG.debug("Load tombstone {} from db {} took {} ms, result = {}",
                        europeanaObjectId, recordDao, (System.currentTimeMillis() - startTime), result);
            }
            return result;
        } catch (RuntimeException e) {
            throw processException(e);
        }
    }

    public String resolveId(RecordRedirectDao redirectDao, String europeanaId) {
        List<RecordRedirect> redirects = redirectDao.getRecordRedirectsByOldId(europeanaId);
        if (redirects.isEmpty()){
            return null;
        } else {
            return redirects.get(0).getNewId();
        }
    }


    /**
     * Processes a {@link RuntimeException} and maps it to a specific {@link EuropeanaException}.
     * Determines the exception type based on the cause and provides an appropriate error context.
     *
     * @param re the runtime exception to process
     * @return a {@link EuropeanaException} instance representing the processed exception
     */
    protected EuropeanaException processException(RuntimeException re) {
        if (re.getCause() != null && (re.getCause() instanceof MappingException
                || re.getCause() instanceof ClassCastException)) {
            return new MongoDBException(ProblemType.RECORD_RETRIEVAL_ERROR, re);
        } else {
            return new MongoRuntimeException(ProblemType.MONGO_UNREACHABLE, re);
        }
    }
}
