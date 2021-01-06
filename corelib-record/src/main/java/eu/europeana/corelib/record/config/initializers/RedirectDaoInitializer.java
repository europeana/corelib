package eu.europeana.corelib.record.config.initializers;

import eu.europeana.corelib.utils.LazyInitializer;
import eu.europeana.metis.mongo.dao.RecordRedirectDao;

/**
 * This class manages an {@link RecordRedirectDao} object to which it lazily connects.
 */
public class RedirectDaoInitializer extends LazyInitializer<RecordRedirectDao> {

    private final MongoClientInitializer connection;
    private final String dbName;

    public RedirectDaoInitializer(MongoClientInitializer connection, String redirectDbName) {
        this.connection = connection;
        this.dbName = redirectDbName;
    }

    @Override
    protected RecordRedirectDao initialize() {
        return new RecordRedirectDao(connection.get(), dbName, false);
    }
}
