package eu.europeana.corelib.record.impl;

import eu.europeana.corelib.definitions.edm.beans.FullBean;
import eu.europeana.corelib.edm.exceptions.BadDataException;
import eu.europeana.corelib.mongo.server.EdmMongoServer;
import eu.europeana.corelib.record.BaseUrlWrapper;
import eu.europeana.corelib.record.DataSourceWrapper;
import eu.europeana.corelib.record.RecordService;
import eu.europeana.corelib.record.api.IIIFLink;
import eu.europeana.corelib.record.api.UrlConverter;
import eu.europeana.corelib.record.api.WebMetaInfo;
import eu.europeana.corelib.utils.EuropeanaUriUtils;
import eu.europeana.corelib.web.exception.EuropeanaException;
import eu.europeana.metis.mongo.RecordRedirect;
import eu.europeana.metis.mongo.RecordRedirectDao;
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

    @Value("#{europeanaProperties['manifest.add.url']}")
    private Boolean manifestAddUrl;
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

        if (fullBean != null && datasource.getRecordServer().isPresent()) {
            return enrichFullBean(datasource.getRecordServer().get(), fullBean, urls);
        } else {
            return null;
        }
    }

    /**
     * @see RecordService#fetchFullBean(DataSourceWrapper, String, boolean)
     */
    @Override
    public FullBean fetchFullBean(DataSourceWrapper datasource, String europeanaObjectId, boolean resolve) throws EuropeanaException {
        long   startTime = System.currentTimeMillis();
        if (datasource.getRecordServer().isEmpty()) {
            LOG.warn("Could not fetch FullBean with europeanaObjectId {}. No record server configured", europeanaObjectId);
            return null;
        }
        EdmMongoServer mongoServer = datasource.getRecordServer().get();
        FullBean fullBean = mongoServer.getFullBean(europeanaObjectId);
        if (LOG.isDebugEnabled()) {
            LOG.debug("RecordService fetch FullBean with europeanaObjectId took {} ms", (System.currentTimeMillis() - startTime));
        }

        if (Objects.isNull(fullBean) && resolve) {
            // object not found, check redirect database
            startTime = System.currentTimeMillis();
            String newId = datasource.getRedirectDb().isPresent() ? resolveId(datasource.getRedirectDb().get(), europeanaObjectId) : null;
            if (LOG.isDebugEnabled()) {
                LOG.debug("RecordService resolve newId took {} ms", (System.currentTimeMillis() - startTime));
            }
            if (StringUtils.isNotBlank(newId)){
                startTime = System.currentTimeMillis();
                fullBean = mongoServer.getFullBean(newId);
                if (fullBean == null) {
                    LOG.warn("{} was redirected to {} but there is no such record!", europeanaObjectId, newId);
                }
                if (LOG.isDebugEnabled()) {
                    LOG.debug("RecordService fetch FullBean with new id took {} ms", (System.currentTimeMillis() - startTime));
                }
            }
        }
        return fullBean;
    }

    /**
     * @see RecordService#enrichFullBean(EdmMongoServer, FullBean, BaseUrlWrapper)
     */
    public FullBean enrichFullBean(EdmMongoServer mongoServer, FullBean fullBean, BaseUrlWrapper urls){
        // 1. add meta info for all webresources + generate attribution snippets
        WebMetaInfo.injectWebMetaInfoBatch(fullBean, mongoServer, attributionCss);

        // 2. add link to IIIF for newspaper and AV/EUScreen items
        IIIFLink.addReferencedBy(fullBean, manifestAddUrl, urls.getApi2BaseUrl());

        // 3. make sure we add /item in various places
        UrlConverter.addSlashItem(fullBean);

        // 4. generate proper edmPreview thumbnail urls
        UrlConverter.setEdmPreview(fullBean, urls.getApiGatewayBaseUrl());

        // 5. generate proper edmLandingpage portal urls
        UrlConverter.setEdmLandingPage(fullBean, urls.getPortalBaseUrl());

        return fullBean;
    }



    /**
     * @see RecordService#resolveId(RecordRedirectDao, String)
     */
    @Override
    public String resolveId(RecordRedirectDao redirectDao, String europeanaId) {
        List<RecordRedirect> redirects = redirectDao.getRecordRedirectsByOldId(europeanaId);
        if (redirects.isEmpty()){
            LOG.debug("RecordService no redirection was found for EuropeanaID {}", europeanaId);
            return null;
        } else {
            return redirects.get(0).getNewId();
        }
    }

    /**
     * @see RecordService#resolveId(RecordRedirectDao redirectDao, String, String)
     */
    @Override
    public String resolveId(RecordRedirectDao redirectDao, String collectionId, String recordId) throws BadDataException {
        return resolveId(redirectDao, EuropeanaUriUtils.createEuropeanaId(collectionId, recordId));
    }

}
