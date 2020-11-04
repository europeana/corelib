package eu.europeana.corelib.storage;

import eu.europeana.corelib.storage.impl.MongoProviderImpl;

/**
 * Lazily initializes a connection to a Mongo instance
 */
public class MongoProviderInitializer extends LazyInitializer<MongoProvider> {

    private final String connectionUrl;
    private final String maxConnectionIdleTime;

    private boolean isConnectionOpen;

    public MongoProviderInitializer(String connectionUrl, String maxConnectionIdleTime) {
        this.connectionUrl = connectionUrl;
        this.maxConnectionIdleTime = maxConnectionIdleTime;
    }


    @Override
    protected MongoProvider initialize() {
        MongoProviderImpl mongoProvider = new MongoProviderImpl(connectionUrl, maxConnectionIdleTime);
        isConnectionOpen = true;
        return mongoProvider;
    }

    public void close() {
        if (isConnectionOpen) {
            get().close();
        }
    }
}
