package eu.europeana.corelib.record.config.initializers;

import eu.europeana.corelib.mongo.server.EdmMongoServer;
import eu.europeana.corelib.mongo.server.impl.EdmMongoServerImpl;
import eu.europeana.corelib.utils.LazyInitializer;

/**
 * This class manages an {@link EdmMongoServer} object to which it lazily connects.
 */
public class EdmMongerServerInitializer extends LazyInitializer<EdmMongoServer> {

    private final MongoProviderInitializer connection;
    private final String dbName;

    public EdmMongerServerInitializer(MongoProviderInitializer connection, String dbName) {
        this.connection = connection;
        this.dbName = dbName;
    }

    @Override
    protected EdmMongoServer initialize() {
        return new EdmMongoServerImpl(connection.get().getMongoClient(), dbName, false);
    }
}
