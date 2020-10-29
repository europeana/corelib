package eu.europeana.corelib.record;

import eu.europeana.metis.mongo.EdmMongoServer;
import eu.europeana.metis.mongo.RecordRedirectDao;

import java.util.Optional;

/**
 * Wrapper class encapsulating Mongo Record and Redirect ID DAO
 * implementations
 */
public class DataSourceWrapper {

    private EdmMongoServer recordServer;
    private RecordRedirectDao redirectDb;

    public DataSourceWrapper() {
    }

    public DataSourceWrapper(EdmMongoServer recordServer, RecordRedirectDao redirectDb) {
        this.recordServer = recordServer;
        this.redirectDb = redirectDb;
    }

    public void setRecordServer(EdmMongoServer recordServer) {
        this.recordServer = recordServer;
    }

    public void setRedirectDb(RecordRedirectDao redirectDb) {
        this.redirectDb = redirectDb;
    }

    public Optional<EdmMongoServer> getRecordServer() {
        return Optional.ofNullable(recordServer);
    }

    public Optional<RecordRedirectDao> getRedirectDb() {
        return Optional.ofNullable(redirectDb);
    }

    public boolean isConfigured(){
        return recordServer != null || redirectDb != null;
    }

}
