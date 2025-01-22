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

    private RecordDao recordDaoInitializer;
    private RecordRedirectDao redirectDaoInitializer;
    private RecordDao tombstoneDaoInitializer;

    public DataSourceWrapper() {
    }

    public DataSourceWrapper(RecordDao recordDaoInitializer, RecordRedirectDao redirectDaoInitializer,
                             RecordDao tombstoneDaoInitializer) {
        this.recordDaoInitializer = recordDaoInitializer;
        this.redirectDaoInitializer = redirectDaoInitializer;
        this.tombstoneDaoInitializer = tombstoneDaoInitializer;
    }

    public void setRecordDao(RecordDao recordDaoInitializer) {
        this.recordDaoInitializer = recordDaoInitializer;
    }

    public void setRedirectDb(RecordRedirectDao redirectDb) {
        this.redirectDaoInitializer = redirectDb;
    }

    public void setTombstoneDb(RecordDao tombstoneDb) {
        this.tombstoneDaoInitializer = tombstoneDb;
    }

    public Optional<RecordDao> getRecordDao() {
        return Optional.ofNullable(recordDaoInitializer);
    }

    public Optional<RecordRedirectDao> getRedirectDao() {
        return redirectDaoInitializer == null ? Optional.empty() : Optional.ofNullable(redirectDaoInitializer);
    }

    public Optional<RecordDao> getTombstoneDao() {
        return tombstoneDaoInitializer == null ? Optional.empty() : Optional.ofNullable(tombstoneDaoInitializer);
    }

    public boolean isConfigured() {
        return recordDaoInitializer != null || redirectDaoInitializer != null || tombstoneDaoInitializer != null;
    }

}
