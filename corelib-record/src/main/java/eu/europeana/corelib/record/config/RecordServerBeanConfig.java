package eu.europeana.corelib.record.config;

import eu.europeana.corelib.mongo.server.impl.EdmMongoServerImpl;
import eu.europeana.corelib.record.DataSourceWrapper;
import eu.europeana.corelib.storage.impl.MongoProviderImpl;
import eu.europeana.metis.mongo.RecordRedirectDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;

/**
 * This class configures database connections specified as application properties.
 * It also enables the retrieval of a Record DB/Redirect DB pairs by a unique identifier.
 */
@Configuration
public class RecordServerBeanConfig {

    private static final Logger LOG = LogManager.getLogger(RecordServerBeanConfig.class);
    private final Map<String, DataSourceWrapper> dataSourceById = new HashMap<>();

    private final DataSourceConfigLoader configLoader;
    private String defaultDataSourceId;

    private final List<MongoProviderImpl> mongoConnections = new ArrayList<>();

    public RecordServerBeanConfig(@Autowired DataSourceConfigLoader configLoader) {
        this.configLoader = configLoader;
    }

    @PostConstruct
    private void setupDataSources() {
        //TODO: validate connections here.
        for (DataSourceConfigLoader.MongoConfigProperty instance : configLoader.getMongoInstances()) {
            MongoProviderImpl mongoProvider = new MongoProviderImpl(instance.getConnectionUrl(), configLoader.getMongoMaxConnectionIdleTime());
            // keep track of connections, so they can be closed on exit
            mongoConnections.add(mongoProvider);
            for (DataSourceConfigLoader.DataSourceConfigProperty dsConfig : instance.getSources()) {
                DataSourceWrapper dsWrapper = new DataSourceWrapper();
                // create connection to Record db if configured
                if (dsConfig.getRecordDbName().isPresent()) {
                    dsWrapper.setRecordServer(new EdmMongoServerImpl(mongoProvider.getMongoClient(), dsConfig.getRecordDbName().get(), false));
                    LOG.info("Registered EdmMongoServer for data source: {}, record-dbName={}", dsConfig.getId(), dsConfig.getRecordDbName().get());
                } else {
                    LOG.info("No record db configured for data source: {}", dsConfig.getId());
                }

                // create connection to Redirect db if configured
                if (dsConfig.getRedirectDbName().isPresent()) {
                    RecordRedirectDao redirectDb = new RecordRedirectDao(mongoProvider.getMongoClient(), dsConfig.getRedirectDbName().get(), false);
                    if(validateRedirectDbConnection(redirectDb, dsConfig.getRedirectDbName().get())) {
                        dsWrapper.setRedirectDb(redirectDb);
                        LOG.info("Registered RecordRedirectDao  for data source: {}, redirect-dbName={}", dsConfig.getId(), dsConfig.getRedirectDbName().get());
                    }
                } else {
                    LOG.info("No redirect db configured for data source: {}", dsConfig.getId());
                }

                if (dsWrapper.isConfigured()) {
                    dataSourceById.put(dsConfig.getId(), dsWrapper);

                    // included specifically for UserServiceImpl (which is already deprecated)
                    if (StringUtils.isEmpty(defaultDataSourceId)) {
                        defaultDataSourceId = dsConfig.getId();
                    }
                }
            }
        }
    }

    /**
     * Gets data sources with the given id
     *
     * @param id Data source ID
     * @return Optional containing matching data sources
     */
    public Optional<DataSourceWrapper> getDataSourceById(String id) {
        return Optional.ofNullable(dataSourceById.get(id));
    }

    /**
     * Gets the default data sources.
     * This will also be the first Record DB / Redirect DB pair in the app configuration
     * @return Optional containing matching data sources.
     */
    public Optional<DataSourceWrapper> getDefaultDataSource() {
        return Optional.ofNullable(dataSourceById.get(defaultDataSourceId));
    }

    /**
     * Check if the redirectDao works fine. If not we disable it.
     * This is a temporary hack so we can use the Record API without a redirect database (for Metis Sandbox)
     * When we switch to Spring-Boot we can implement a more elegant solution with @ConditionalOnProperty for example
     * @param redirectDb
     * @param redirectDbName
     */
    private boolean validateRedirectDbConnection(RecordRedirectDao redirectDb, String redirectDbName) {
        try {
            redirectDb.getRecordRedirectsByOldId("/xx/yy");
            LOG.info("Connection to redirect database {} is okay.", redirectDbName);
            return true;
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not authorized")) {
                // this is the expected behavior if we try to access a database that doesn't exist.
                LOG.warn("Not authorized to access redirect database {}. It may not exist.", redirectDbName);
                LOG.warn("Redirect functionality is now disabled");
            } else {
                LOG.error("Error accessing redirect database '{}'", redirectDbName, e);
            }
        }
        return false;
    }

    /**
     * Invoked by Spring container on application exit.
     * Closes database connections.
     */
    @PreDestroy
    private void closeConnections() {
        LOG.info("Closing database connections...");
        mongoConnections.forEach(MongoProviderImpl::close);
    }
}

