package eu.europeana.corelib.record;

import eu.europeana.corelib.mongo.server.EdmMongoServer;
import eu.europeana.corelib.record.config.initializers.EdmMongerServerInitializer;
import eu.europeana.corelib.record.config.initializers.RedirectDaoInitializer;
import eu.europeana.metis.mongo.RecordRedirectDao;

import java.util.Optional;

/**
 * Wrapper class encapsulating Mongo Record and Redirect ID DAO
 * implementations
 */
public class DataSourceWrapper {

    private EdmMongerServerInitializer recordServerInitializer;
    private RedirectDaoInitializer redirectDaoInitializer;

    public DataSourceWrapper() {
    }

    public DataSourceWrapper(EdmMongerServerInitializer edmMongerServerInitializer, RedirectDaoInitializer redirectDaoInitializer) {
        this.recordServerInitializer = edmMongerServerInitializer;
        this.redirectDaoInitializer = redirectDaoInitializer;
    }

    public void setRecordServer(EdmMongerServerInitializer edmMongerServerInitializer) {
        this.recordServerInitializer = edmMongerServerInitializer;
    }

    public void setRedirectDb(RedirectDaoInitializer redirectDb) {
        this.redirectDaoInitializer = redirectDb;
    }

    public Optional<EdmMongoServer> getRecordServer() {
        return Optional.ofNullable(recordServerInitializer.get());
    }

    public Optional<RecordRedirectDao> getRedirectDb() {
        return Optional.ofNullable(redirectDaoInitializer.get());
    }

    public boolean isConfigured() {
        return recordServerInitializer != null || redirectDaoInitializer != null;
    }

}
