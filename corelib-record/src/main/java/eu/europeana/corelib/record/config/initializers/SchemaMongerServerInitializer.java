package eu.europeana.corelib.record.config.initializers;

import eu.europeana.corelib.mongo.server.EdmMongoServer;
import eu.europeana.corelib.mongo.server.impl.EdmMongoServerImpl;
import eu.europeana.corelib.utils.LazyInitializer;

/**
 * This class manages an {@link EdmMongoServer} object to which it lazily connects.
 */
public class SchemaMongerServerInitializer extends LazyInitializer<EdmMongoServer> {

    private final MongoProviderInitializer connection;
    private final String schemaDbName;

    public SchemaMongerServerInitializer(MongoProviderInitializer connection, String schemaDbName) {
        this.connection = connection;
        this.schemaDbName = schemaDbName;
    }

    @Override
    protected EdmMongoServer initialize() {
        return new EdmMongoServerImpl(connection.get().getMongoClient(), schemaDbName, false);
    }
}
