package eu.europeana.corelib.record.impl;

import eu.europeana.corelib.definitions.edm.beans.FullBean;
import eu.europeana.corelib.definitions.edm.entity.Aggregation;
import eu.europeana.corelib.edm.exceptions.BadDataException;
import eu.europeana.corelib.mongo.server.EdmMongoServer;
import eu.europeana.corelib.record.RecordService;
import eu.europeana.corelib.solr.bean.impl.FullBeanImpl;
import eu.europeana.corelib.solr.entity.WebResourceImpl;
import eu.europeana.corelib.utils.EuropeanaUriUtils;
import eu.europeana.corelib.web.exception.EuropeanaException;
import eu.europeana.metis.mongo.RecordRedirect;
import eu.europeana.metis.mongo.RecordRedirectDao;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

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

    @Value("#{europeanaProperties['api2.baseUrl']}")
    private String api2BaseUrl;
    @Value("#{europeanaProperties['manifest.add.url']}")
    private Boolean manifestAddUrl;
    @Value("#{europeanaProperties['htmlsnippet.css.source']}")
    private String htmlCssSource;

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
            return addWebResourceMetaInfo(fullBean);
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
     * @see RecordService#addWebResourceMetaInfo(FullBean)
     */
    public FullBean addWebResourceMetaInfo(FullBean fullBean){
        // add meta info for all webresources
        WebMetaInfo.injectWebMetaInfoBatch(fullBean, mongoServer, manifestAddUrl, api2BaseUrl);

        // generate attribution snippets for all webresources
        if ((fullBean.getAggregations() != null && !fullBean.getAggregations().isEmpty())) {
            ((FullBeanImpl) fullBean).setAsParent();
            for (Aggregation agg : fullBean.getAggregations()) {
                if (agg.getWebResources() != null && !agg.getWebResources().isEmpty()) {
                    for (WebResourceImpl wRes : (List<WebResourceImpl>) agg.getWebResources()) {
                        wRes.initAttributionSnippet(htmlCssSource);
                    }
                }
            }
        }
        return fullBean;
    }



    /**
     * @see RecordService#resolveId(String)
     */
    @Override
    public String resolveId(String europeanaId) throws BadDataException {
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
