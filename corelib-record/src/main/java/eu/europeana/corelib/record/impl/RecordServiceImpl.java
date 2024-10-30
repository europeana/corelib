package eu.europeana.corelib.record.impl;

import eu.europeana.corelib.definitions.edm.beans.FullBean;
import eu.europeana.corelib.edm.utils.ProxyAggregationUtils;
import eu.europeana.corelib.record.BaseUrlWrapper;
import eu.europeana.corelib.record.RecordService;
import eu.europeana.corelib.record.api.IIIFLink;
import eu.europeana.corelib.record.api.UrlConverter;
import eu.europeana.corelib.record.api.WebMetaInfo;
import eu.europeana.corelib.utils.EuropeanaUriUtils;
import eu.europeana.corelib.web.exception.EuropeanaException;
import eu.europeana.metis.mongo.dao.RecordDao;
import eu.europeana.metis.mongo.dao.RecordRedirectDao;
import eu.europeana.metis.mongo.model.RecordRedirect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

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
     * @see RecordService#fetchFullBean(RecordDao, String)
     */
    @Override
    public FullBean fetchFullBean(RecordDao recordDao, String europeanaObjectId) throws EuropeanaException {
        long startTime = System.currentTimeMillis();
        FullBean fullBean = recordDao.getFullBean(europeanaObjectId);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Load FullBean {} from db {} took {} ms, result = {}",
                    europeanaObjectId, recordDao, (System.currentTimeMillis() - startTime), fullBean);
        }
        return fullBean;
    }

    /**
     * @see RecordService#enrichFullBean(RecordDao, FullBean, BaseUrlWrapper)
     */
    public FullBean enrichFullBean(RecordDao recordDao, FullBean fullBean, BaseUrlWrapper urls){

        // 1. order the proxy and aggregation
        fullBean.setProxies(ProxyAggregationUtils.orderProxy(fullBean));
        fullBean.setAggregations(ProxyAggregationUtils.orderAggregation(fullBean));

        // 2. add meta info for all webresources + generate attribution snippets
        long startTime = System.currentTimeMillis();
        WebMetaInfo.injectWebMetaInfoBatch(fullBean, recordDao, attributionCss);
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
        long startTime = System.currentTimeMillis();
        FullBean result = recordDao.getFullBean(europeanaObjectId);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Load tombstone {} from db {} took {} ms, result = {}",
                    europeanaObjectId, recordDao, (System.currentTimeMillis() - startTime), result);
        }
        return result;
    }

    public String resolveId(RecordRedirectDao redirectDao, String europeanaId) {
        List<RecordRedirect> redirects = redirectDao.getRecordRedirectsByOldId(europeanaId);
        if (redirects.isEmpty()){
            return null;
        } else {
            return redirects.get(0).getNewId();
        }
    }

}
