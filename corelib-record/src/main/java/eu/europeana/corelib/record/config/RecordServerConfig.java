package eu.europeana.corelib.record.config;

import eu.europeana.corelib.record.DataSourceWrapper;
import eu.europeana.corelib.record.config.initializers.MongoClientInitializer;
import eu.europeana.corelib.record.config.initializers.RecordDaoInitializer;
import eu.europeana.corelib.record.config.initializers.RedirectDaoInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;

/**
 * This class configures database connections specified as application properties. It also enables
 * the retrieval of Record DB/Redirect DB pairs by a unique identifier.
 */
@Configuration
public class RecordServerConfig {

    private static final Logger LOG = LogManager.getLogger(RecordServerConfig.class);
    private final Map<String, DataSourceWrapper> dataSourceById = new HashMap<>();

    private final DataSourceConfigLoader configLoader;

    private final List<MongoClientInitializer> mongoConnections = new ArrayList<>();

    public RecordServerConfig(@Autowired DataSourceConfigLoader configLoader) {
        this.configLoader = configLoader;
    }

    /**
     * Note that we only read configurations and setup DataSources. No actual connections are created until the first
     * request to a DataSource
     */
    @PostConstruct
    private void setupDataSources() {
        LOG.info("Loading mongo database configurations...");
        for (DataSourceConfigLoader.MongoConfigProperty instance : configLoader.getMongoInstances()) {
            MongoClientInitializer connection = new MongoClientInitializer(instance.getConnectionUrl());
            // keep track of connections, so they can be closed on exit
            mongoConnections.add(connection);

            for (DataSourceConfigLoader.DataSourceConfigProperty dsConfig : instance.getSources()) {
                setupDatabases(dsConfig, connection);
            }
        }
    }

    private void setupDatabases(DataSourceConfigLoader.DataSourceConfigProperty dsConfig, MongoClientInitializer connection) {
        DataSourceWrapper dsWrapper = new DataSourceWrapper();

        // create connection to Record db if configured
        if (dsConfig.getRecordDbName().isPresent()) {
            dsWrapper.setRecordDao(new RecordDaoInitializer(connection, dsConfig.getRecordDbName().get()));
            LOG.info("Registered RecordDao for data source: {}, record-dbName={}",
                    dsConfig.getId(), dsConfig.getRecordDbName().get());
        } else {
            LOG.info("No record db configured for data source: {}", dsConfig.getId());
        }

        // create connection to Redirect db if configured
        if (dsConfig.getRedirectDbName().isPresent()) {
            dsWrapper.setRedirectDb(new RedirectDaoInitializer(connection, dsConfig.getRedirectDbName().get()));
            LOG.info("Registered RecordRedirectDao for data source: {}, redirect-dbName={}",
                    dsConfig.getId(), dsConfig.getRedirectDbName().get());
        } else {
            LOG.info("No redirect db configured for data source: {}", dsConfig.getId());
        }

        // create connection to tombstone db if configured
        if (dsConfig.getTombstoneDbName().isPresent()) {
            dsWrapper.setTombstoneDb(new RecordDaoInitializer(connection, dsConfig.getTombstoneDbName().get()));
            LOG.info("Registered RecordDao for data source: {}, tombstone-dbName={}",
                    dsConfig.getId(), dsConfig.getTombstoneDbName().get());
        } else {
            LOG.info("No redirect db configured for data source: {}", dsConfig.getId());
        }

        if (dsWrapper.isConfigured()) {
            dataSourceById.put(dsConfig.getId(), dsWrapper);
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
     * Invoked by Spring container on application exit.
     */
    @PreDestroy
    private void closeConnections() {
        LOG.info("Closing mongo connections...");
        mongoConnections.forEach(MongoClientInitializer::close);
    }
}

