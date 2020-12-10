package eu.europeana.corelib.record;

import eu.europeana.corelib.mongo.server.EdmMongoServer;
import eu.europeana.corelib.record.config.initializers.EdmMongerServerInitializer;
import eu.europeana.corelib.record.config.initializers.RedirectDaoInitializer;
import eu.europeana.corelib.record.config.initializers.SchemaMongerServerInitializer;
import eu.europeana.metis.mongo.RecordRedirectDao;

import java.util.Optional;

/**
 * Wrapper class encapsulating Mongo Record and Redirect ID DAO
 * implementations
 */
public class DataSourceWrapper {

    private EdmMongerServerInitializer recordServerInitializer;
    private SchemaMongerServerInitializer schemaServerInitializer;
    private RedirectDaoInitializer redirectDaoInitializer;

    public DataSourceWrapper() {
    }

    public DataSourceWrapper(EdmMongerServerInitializer edmMongerServerInitializer,SchemaMongerServerInitializer schemaServerInitializer, RedirectDaoInitializer redirectDaoInitializer) {
        this.recordServerInitializer = edmMongerServerInitializer;
        this.schemaServerInitializer = schemaServerInitializer;
        this.redirectDaoInitializer = redirectDaoInitializer;
    }

    public void setRecordServer(EdmMongerServerInitializer edmMongerServerInitializer) {
        this.recordServerInitializer = edmMongerServerInitializer;
    }

    public void setSchemaServer(SchemaMongerServerInitializer schemaServerInitializer) {
        this.schemaServerInitializer = schemaServerInitializer;
    }
    public void setRedirectDb(RedirectDaoInitializer redirectDb) {
        this.redirectDaoInitializer = redirectDb;
    }

    public Optional<EdmMongoServer> getRecordServer() {
        return Optional.ofNullable(recordServerInitializer.get());
    }

    public Optional<EdmMongoServer> getSchemaServer() {
        return Optional.ofNullable(schemaServerInitializer.get());
    }


    public Optional<RecordRedirectDao> getRedirectDb() {
        return Optional.ofNullable(redirectDaoInitializer.get());
    }

    public boolean isConfigured() {
        return recordServerInitializer != null || redirectDaoInitializer != null;
    }

}
