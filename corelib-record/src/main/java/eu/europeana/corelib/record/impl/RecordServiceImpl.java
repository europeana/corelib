package eu.europeana.corelib.record.impl;

import eu.europeana.corelib.definitions.edm.beans.FullBean;
import eu.europeana.corelib.edm.utils.ProxyAggregationUtils;
import eu.europeana.corelib.record.BaseUrlWrapper;
import eu.europeana.corelib.record.DataSourceWrapper;
import eu.europeana.corelib.record.RecordService;
import eu.europeana.corelib.record.api.IIIFLink;
import eu.europeana.corelib.record.api.UrlConverter;
import eu.europeana.corelib.record.api.WebMetaInfo;
import eu.europeana.corelib.utils.EuropeanaUriUtils;
import eu.europeana.corelib.web.exception.EuropeanaException;
import eu.europeana.metis.mongo.dao.RecordDao;
import eu.europeana.metis.mongo.dao.RecordRedirectDao;
import eu.europeana.metis.mongo.model.RecordRedirect;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Objects;

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
     * @see RecordService#findById(DataSourceWrapper, String, String, BaseUrlWrapper)
     */
    @Override
    public FullBean findById(DataSourceWrapper datasource, String collectionId, String recordId, BaseUrlWrapper urls) throws EuropeanaException {
        return findById(datasource, EuropeanaUriUtils.createEuropeanaId(collectionId, recordId), urls);
    }

    /**
     * @see RecordService#findById(DataSourceWrapper, String, BaseUrlWrapper)
     */
    @Override
    public FullBean findById(DataSourceWrapper datasource, String europeanaObjectId, BaseUrlWrapper urls) throws EuropeanaException {
        FullBean fullBean = fetchFullBean(datasource, europeanaObjectId, true);

        if (fullBean != null && datasource.getRecordDao().isPresent()) {
            return enrichFullBean(datasource.getRecordDao().get(), fullBean, urls);
        } else {
            return null;
        }
    }

    /**
     * @see RecordService#fetchFullBean(DataSourceWrapper, String, boolean)
     */
    @Override
    public FullBean fetchFullBean(DataSourceWrapper datasource, String europeanaObjectId, boolean resolve) throws EuropeanaException {
        long startTime = System.currentTimeMillis();

        if (datasource.getRecordDao().isEmpty()) {
            LOG.warn("Could not load FullBean {}. No record server configured", europeanaObjectId);
            return null;
        }
        RecordDao recordDao = datasource.getRecordDao().get();

        // We try to load the record from the 'main' record database
        FullBean fullBean = recordDao.getFullBean(europeanaObjectId);
        if (LOG.isDebugEnabled()) {
            LOG.debug("RecordService load FullBean {} took {} ms, result = {}",
                    europeanaObjectId, (System.currentTimeMillis() - startTime), fullBean);
        }

        // If not available, we check if there's a redirect and if we can load that record
        if (Objects.isNull(fullBean) && resolve) {
            startTime = System.currentTimeMillis();
            fullBean = fetchFromRedirectDb(datasource, europeanaObjectId, recordDao);
            if (LOG.isDebugEnabled()) {
                LOG.debug("RecordService load redirected FullBean {} took {} ms, result = {}",
                        europeanaObjectId, (System.currentTimeMillis() - startTime), fullBean);
            }
        }

        return fullBean;
    }

    /**
     * @see RecordService#enrichFullBean(RecordDao, FullBean, BaseUrlWrapper)
     */
    public FullBean enrichFullBean(RecordDao recordDao, FullBean fullBean, BaseUrlWrapper urls){
        long startTime = System.currentTimeMillis();

        // 1. order the proxy and aggregation
        fullBean.setProxies(ProxyAggregationUtils.orderProxy(fullBean));
        fullBean.setAggregations(ProxyAggregationUtils.orderAggregation(fullBean));

        // 2. add meta info for all webresources + generate attribution snippets
        WebMetaInfo.injectWebMetaInfoBatch(fullBean, recordDao, attributionCss);

        // 3. add link to IIIF for newspaper and AV/EUScreen items. Also adds the manifest resources for the IIIF links.
        IIIFLink.addReferencedByAndManifestResources(fullBean, manifestAddApiUrl, urls.getApi2BaseUrl(), manifestBaseUrl);

        // 4. make sure we add /item in various places
        UrlConverter.addSlashItem(fullBean);

        // 5. generate proper edmPreview thumbnail urls
        UrlConverter.setEdmPreview(fullBean, urls.getApiGatewayBaseUrl());

        // 6. generate proper edmLandingpage portal urls
        UrlConverter.setEdmLandingPage(fullBean, urls.getPortalBaseUrl());

        if (LOG.isDebugEnabled()) {
            LOG.debug("RecordService enrich record took {} ms", (System.currentTimeMillis() - startTime));
        }
        return fullBean;
    }

    /**
     * @see RecordService#fetchTombstone(DataSourceWrapper, String)
     */
    @Override
    public FullBean fetchTombstone(DataSourceWrapper dataSource, String europeanaObjectId) throws EuropeanaException {
        long startTime = System.currentTimeMillis();
        FullBean result = null;
        if (dataSource.getTombstoneDao().isPresent()) {
            result = dataSource.getTombstoneDao().get().getFullBean(europeanaObjectId);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("RecordService fetch tombstone {} took {} ms, result = {}", europeanaObjectId, (System.currentTimeMillis() - startTime), result);
        }
        return result;
    }

    private FullBean fetchFromRedirectDb(DataSourceWrapper datasource, String europeanaObjectId, RecordDao recordDao) throws EuropeanaException {
        long startTime = System.currentTimeMillis();
        FullBean result = null;
        String newId = datasource.getRedirectDao().isPresent() ? resolveId(datasource.getRedirectDao().get(), europeanaObjectId) : null;
        if (LOG.isDebugEnabled()) {
            LOG.debug("RecordService resolve newId for record {} took {} ms, result = {}", europeanaObjectId, (System.currentTimeMillis() - startTime), newId);
        }

        if (StringUtils.isNotBlank(newId)){
            startTime = System.currentTimeMillis();
            result = recordDao.getFullBean(newId);
            if (result == null) {
                LOG.debug("{} was redirected to {} but there is no such record!", europeanaObjectId, newId);
            }
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("RecordService fetch FullBean with new id {} took {} ms, result = {}", newId, (System.currentTimeMillis() - startTime), result);
        }
        return result;
    }

    private String resolveId(RecordRedirectDao redirectDao, String europeanaId) {
        List<RecordRedirect> redirects = redirectDao.getRecordRedirectsByOldId(europeanaId);
        if (redirects.isEmpty()){
            LOG.debug("RecordService no redirection was found for EuropeanaID {}", europeanaId);
            return null;
        } else {
            return redirects.get(0).getNewId();
        }
    }

}
