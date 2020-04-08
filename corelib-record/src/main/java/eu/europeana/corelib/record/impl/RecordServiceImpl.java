package eu.europeana.corelib.record.impl;

import eu.europeana.corelib.definitions.edm.beans.FullBean;
import eu.europeana.corelib.definitions.edm.entity.Aggregation;
import eu.europeana.corelib.edm.exceptions.BadDataException;
import eu.europeana.corelib.mongo.server.EdmMongoServer;
import eu.europeana.corelib.record.RecordService;
import eu.europeana.corelib.solr.bean.impl.FullBeanImpl;
import eu.europeana.corelib.solr.entity.WebResourceImpl;
import eu.europeana.corelib.tools.lookuptable.EuropeanaId;
import eu.europeana.corelib.tools.lookuptable.EuropeanaIdMongoServer;
import eu.europeana.corelib.utils.EuropeanaUriUtils;
import eu.europeana.corelib.web.exception.EuropeanaException;
import eu.europeana.corelib.web.exception.ProblemType;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import java.util.ArrayList;
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

    /* A lot of old records are in the EuropeanaId database with "http://www.europeana.eu/resolve/record/1/2" as 'oldId' */
    @Deprecated // can be removed when we have the new redirect functionality
    private static final String RESOLVE_PREFIX = "http://www.europeana.eu/resolve/record";
    // TODO October 2018 It seems there are no records with this prefix in the EuropeanaId database so most likely this can be removed
    @Deprecated // can be removed when we have the new redirect functionality
    private static final String PORTAL_PREFIX = "http://www.europeana.eu/record";

    @Resource(name = "corelib_record_mongoServer")
    protected EdmMongoServer mongoServer;
    @Resource(name = "corelib_record_mongoServer_id")
    protected EuropeanaIdMongoServer idServer;

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
    @Deprecated // will be deprecated when the new redirect database goes live
    public String resolveId(String europeanaObjectId) throws BadDataException {
        List<String> idsSeen = new ArrayList<>(); // used to detect circular references

        String lastId = resolveIdInternal(europeanaObjectId, idsSeen);
        String newId = lastId;
        while (newId != null) {
            newId = resolveIdInternal(newId, idsSeen);
            if (newId != null) {
                lastId = newId;
            }
        }
        return lastId;
    }

    /**
     * @see RecordService#resolveId(String, String)
     */
    @Override
    @Deprecated // will be deprecated when the new redirect database goes live
    public String resolveId(String collectionId, String recordId) throws BadDataException {
        return resolveId(EuropeanaUriUtils.createEuropeanaId(
                collectionId, recordId));
    }

    /**
     * Checks if there is a new id for the provided europeanaObjectId, using different variations of this id
     * Additionally this method checks if the new found id is an id that was resolved earlier in the chain (check for circular reference)
     * @param europeanaObjectId
     * @param europeanaObjectIdsSeen list with all europeanaObjectIds (without any prefixes) we've resolved so far.
     * @return the found newId of the record, or null if no newId was found,
     * @throws BadDataException if a circular id reference is found
     */
    @Deprecated // will be deprecated when the new redirect database goes live
    private String resolveIdInternal(String europeanaObjectId, List<String> europeanaObjectIdsSeen) throws BadDataException {
        europeanaObjectIdsSeen.add(europeanaObjectId);

        List<String> idsToCheck = new ArrayList<>();
        idsToCheck.add(europeanaObjectId);
        idsToCheck.add(RESOLVE_PREFIX + europeanaObjectId);
        idsToCheck.add(PORTAL_PREFIX + europeanaObjectId);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Trying to resolve ids {}", idsToCheck);
        }
        EuropeanaId newId = idServer.retrieveEuropeanaIdFromOld(idsToCheck);
        if (newId != null) {
            String result = newId.getNewId();

            // check for circular references
            if (result != null && europeanaObjectIdsSeen.contains(result)) {
                europeanaObjectIdsSeen.add(result);
                String message = "Cannot resolve record-id because a circular id reference was found! "+europeanaObjectIdsSeen;
                LOG.error(message);
                throw new BadDataException(ProblemType.INCONSISTENT_DATA, message +" The data team will be notified of the problem.");
            }
            return result;
        }
        return null;
    }
}
