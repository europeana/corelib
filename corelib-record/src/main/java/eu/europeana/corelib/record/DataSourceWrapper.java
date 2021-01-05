package eu.europeana.corelib.record;

import eu.europeana.corelib.record.config.initializers.RecordDaoInitializer;
import eu.europeana.corelib.record.config.initializers.RedirectDaoInitializer;
import eu.europeana.metis.mongo.dao.RecordDao;
import eu.europeana.metis.mongo.dao.RecordRedirectDao;

import java.util.Optional;

/**
 * Wrapper class encapsulating Mongo Record and Redirect ID DAO
 * implementations
 */
public class DataSourceWrapper {

    private RecordDaoInitializer recordDaoInitializer;
    private RedirectDaoInitializer redirectDaoInitializer;

    public DataSourceWrapper() {
    }

    public DataSourceWrapper(RecordDaoInitializer recordDaoInitializer, RedirectDaoInitializer redirectDaoInitializer) {
        this.recordDaoInitializer = recordDaoInitializer;
        this.redirectDaoInitializer = redirectDaoInitializer;
    }

    public void setRecordDao(RecordDaoInitializer recordDaoInitializer) {
        this.recordDaoInitializer = recordDaoInitializer;
    }

    public void setRedirectDb(RedirectDaoInitializer redirectDb) {
        this.redirectDaoInitializer = redirectDb;
    }

    public Optional<RecordDao> getRecordDao() {
        return Optional.ofNullable(recordDaoInitializer.get());
    }

    public Optional<RecordRedirectDao> getRedirectDb() {
        return Optional.ofNullable(redirectDaoInitializer.get());
    }

    public boolean isConfigured() {
        return recordDaoInitializer != null || redirectDaoInitializer != null;
    }

}
