package eu.europeana.corelib.record.config;

import eu.europeana.corelib.record.config.initializers.EdmMongerServerInitializer;
import eu.europeana.corelib.record.config.initializers.RedirectDaoInitializer;
import eu.europeana.corelib.record.DataSourceWrapper;
import eu.europeana.corelib.record.config.initializers.MongoProviderInitializer;
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
 * It also enables the retrieval of Record DB/Redirect DB pairs by a unique identifier.
 */
@Configuration
public class RecordServerConfig {

    private static final Logger LOG = LogManager.getLogger(RecordServerConfig.class);
    private final Map<String, DataSourceWrapper> dataSourceById = new HashMap<>();

    private final DataSourceConfigLoader configLoader;
    private String defaultDataSourceId;

    private final List<MongoProviderInitializer> mongoConnections = new ArrayList<>();

    public RecordServerConfig(@Autowired DataSourceConfigLoader configLoader) {
        this.configLoader = configLoader;
    }

    @PostConstruct
    private void setupDataSources() {
        for (DataSourceConfigLoader.MongoConfigProperty instance : configLoader.getMongoInstances()) {
            MongoProviderInitializer connection = new MongoProviderInitializer(instance.getConnectionUrl(), configLoader.getMongoMaxConnectionIdleTime());
            // keep track of connections, so they can be closed on exit
            mongoConnections.add(connection);
            for (DataSourceConfigLoader.DataSourceConfigProperty dsConfig : instance.getSources()) {
                DataSourceWrapper dsWrapper = new DataSourceWrapper();
                // create connection to Record db if configured
                if (dsConfig.getRecordDbName().isPresent()) {
                    dsWrapper.setRecordServer(new EdmMongerServerInitializer(connection, dsConfig.getRecordDbName().get()));
                    LOG.info("Registered EdmMongoServer for data source: {}, record-dbName={}", dsConfig.getId(), dsConfig.getRecordDbName().get());
                } else {
                    LOG.info("No record db configured for data source: {}", dsConfig.getId());
                }

                // create connection to Redirect db if configured
                if (dsConfig.getRedirectDbName().isPresent()) {
                    RedirectDaoInitializer redirectDbInit = new RedirectDaoInitializer(connection, dsConfig.getRedirectDbName().get());
                    dsWrapper.setRedirectDb(redirectDbInit);
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
     * @param id configured data source ID
     * @return Optional containing matching data sources
     */
    public Optional<DataSourceWrapper> getDataSourceById(String id) {
        return Optional.ofNullable(dataSourceById.get(id));
    }

    /**
     * Gets the default data sources.
     * This will also be the first Record DB / Redirect DB pair in the app configuration
     *
     * @return Optional containing matching data sources.
     * @deprecated use #getDataSourceById(String)
     */
    public Optional<DataSourceWrapper> getDefaultDataSource() {
        return Optional.ofNullable(dataSourceById.get(defaultDataSourceId));
    }


    /**
     * Invoked by Spring container on application exit.
     */
    @PreDestroy
    private void closeConnections() {
        LOG.info("Closing database connections...");
        mongoConnections.forEach(MongoProviderInitializer::close);
    }
}

