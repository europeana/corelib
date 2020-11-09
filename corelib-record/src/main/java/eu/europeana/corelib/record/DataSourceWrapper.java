package eu.europeana.corelib.record;

import eu.europeana.metis.mongo.dao.RecordDao;
import eu.europeana.metis.mongo.dao.RecordRedirectDao;
import java.util.Optional;

/**
 * Wrapper class encapsulating Mongo Record and Redirect ID DAO
 * implementations
 */
public class DataSourceWrapper {

    private RecordDao recordDao;
    private RecordRedirectDao redirectDb;

    public DataSourceWrapper() {
    }

    public DataSourceWrapper(RecordDao recordDao, RecordRedirectDao redirectDb) {
        this.recordDao = recordDao;
        this.redirectDb = redirectDb;
    }

    public void setRecordDao(RecordDao recordDao) {
        this.recordDao = recordDao;
    }

    public void setRedirectDb(RecordRedirectDao redirectDb) {
        this.redirectDb = redirectDb;
    }

    public Optional<RecordDao> getRecordDao() {
        return Optional.ofNullable(recordDao);
    }

    public Optional<RecordRedirectDao> getRedirectDb() {
        return Optional.ofNullable(redirectDb);
    }

    public boolean isConfigured(){
        return recordDao != null || redirectDb != null;
    }

}
