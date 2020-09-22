package eu.europeana.corelib.record.impl;

import eu.europeana.corelib.definitions.edm.beans.FullBean;
import eu.europeana.corelib.edm.exceptions.BadDataException;
import eu.europeana.corelib.mongo.server.EdmMongoServer;
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

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
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

    @Resource(name = "corelib_record_mongoServer")
    protected EdmMongoServer mongoServer;

    @Resource(name = "metis_redirect_mongo")
    protected RecordRedirectDao redirectDao;
    @Value("${mongodb.redirect.dbname}")
    private String redirectDbName;

    @Value("#{europeanaProperties['portal.baseUrl']}")
    private String portalBaseUrl;
    @Value("#{europeanaProperties['apiGateway.baseUrl']}")
    private String apiGatewayBaseUrl;
    @Value("#{europeanaProperties['api2.baseUrl']}")
    private String api2BaseUrl;
    @Value("#{europeanaProperties['manifest.add.url']}")
    private Boolean manifestAddUrl;
    @Value("#{europeanaProperties['htmlsnippet.css.source']}")
    private String attributionCss;

    /**
     * Check if the redirectDao works fine. If not we disable it.
     * This is a temporary hack so we can use the Record API without a redirect database (for Metis Sandbox)
     * When we switch to Spring-Boot we can implement a more elegant solution with @ConditionalOnProperty for example
     */
    @PostConstruct
    private void checkConfiguration() {
        if (redirectDao == null || StringUtils.isEmpty(redirectDbName) || StringUtils.containsIgnoreCase(redirectDbName,"REMOVED")) {
            LOG.warn("No redirect database configured!");
            redirectDao = null;
        } else {
            // do a request to see if things are working (if database exists and we have access)
            try {
                redirectDao.getRecordRedirectsByOldId("/xx/yy");
                LOG.info("Connection to redirect database {} is okay.", redirectDbName);
            } catch (RuntimeException e) {
                if (e.getMessage().contains("not authorized")) {
                    // this is the expected behavior if we try to access a database that doesn't exist.
                    LOG.warn("Not authorized to access redirect database {}. It may not exist.", redirectDbName);
                    redirectDao = null;
                    LOG.warn("Redirect functionality is now disabled");
                } else {
                    LOG.error("Error accessing redirect database '{}'", redirectDbName, e);
                }
            }
        }
    }

    /**
     * @see RecordService#findById(String, String)
     */
    @Override
    public FullBean findById(String collectionId, String recordId) throws EuropeanaException {
        return findById(EuropeanaUriUtils.createEuropeanaId(collectionId, recordId));
    }

    /**
     * @see RecordService#findById(String)
     */
    @Override
    public FullBean findById(String europeanaObjectId) throws EuropeanaException {
        FullBean fullBean = fetchFullBean(europeanaObjectId, true);
        if (fullBean != null) {
            return enrichFullBean(fullBean);
        } else {
            return null;
        }
    }

    /**
     * @see RecordService#fetchFullBean(String, boolean)
     */
    @Override
    public FullBean fetchFullBean(String europeanaObjectId, boolean resolve) throws EuropeanaException {
        long   startTime = System.currentTimeMillis();
        FullBean fullBean = mongoServer.getFullBean(europeanaObjectId);
        if (LOG.isDebugEnabled()) {
            LOG.debug("RecordService fetch FullBean with europeanaObjectId took {} ms", (System.currentTimeMillis() - startTime));
        }

        if (Objects.isNull(fullBean) && resolve) {
            // object not found, check redirect database
            startTime = System.currentTimeMillis();
            String newId = resolveId(europeanaObjectId);
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
     * @see RecordService#enrichFullBean(FullBean)
     */
    public FullBean enrichFullBean(FullBean fullBean){
        // 1. add meta info for all webresources + generate attribution snippets
        WebMetaInfo.injectWebMetaInfoBatch(fullBean, mongoServer, attributionCss);

        // 2. add link to IIIF for newspaper and AV/EUScreen items
        IIIFLink.addReferencedBy(fullBean, manifestAddUrl, api2BaseUrl);

        // 3. make sure we add /item in various places
        UrlConverter.addSlashItem(fullBean);

        // 4. generate proper edmPreview thumbnail urls
        UrlConverter.setEdmPreview(fullBean, apiGatewayBaseUrl);

        // 5. generate proper edmLandingpage portal urls
        UrlConverter.setEdmLandingPage(fullBean, portalBaseUrl);

        return fullBean;
    }



    /**
     * @see RecordService#resolveId(String)
     */
    @Override
    public String resolveId(String europeanaId) throws BadDataException {
        if (redirectDao == null) {
            return null;
        }
        List<RecordRedirect> redirects = redirectDao.getRecordRedirectsByOldId(europeanaId);
        if (redirects.isEmpty()){
            LOG.debug("RecordService no redirection was found for EuropeanaID {}", europeanaId);
            return null;
        } else {
            return redirects.get(0).getNewId();
        }
    }

    /**
     * @see RecordService#resolveId(String, String)
     */
    @Override
    public String resolveId(String collectionId, String recordId) throws BadDataException {
        return resolveId(EuropeanaUriUtils.createEuropeanaId(collectionId, recordId));
    }

}
