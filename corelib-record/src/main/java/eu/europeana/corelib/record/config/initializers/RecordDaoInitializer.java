package eu.europeana.corelib.record.config.initializers;

import eu.europeana.corelib.utils.LazyInitializer;
import eu.europeana.metis.mongo.dao.RecordDao;

/**
 * This class manages an {@link RecordDao} object to which it lazily connects.
 */
public class RecordDaoInitializer extends LazyInitializer<RecordDao> {

    private final MongoClientInitializer connection;
    private final String dbName;

    public RecordDaoInitializer(MongoClientInitializer connection, String dbName) {
        this.connection = connection;
        this.dbName = dbName;
    }

    @Override
    protected RecordDao initialize() {
        return new RecordDao(connection.get(), dbName, false);
    }
}
