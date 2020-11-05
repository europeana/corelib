package eu.europeana.corelib.record.config.initializers;

import eu.europeana.corelib.storage.MongoProvider;
import eu.europeana.corelib.storage.impl.MongoProviderImpl;
import eu.europeana.corelib.utils.LazyInitializer;

/**
 * Lazily initializes a connection to a Mongo instance
 */
public class MongoProviderInitializer extends LazyInitializer<MongoProvider> {

    private final String connectionUrl;
    private final String maxConnectionIdleTime;

    private MongoProvider provider;

    public MongoProviderInitializer(String connectionUrl, String maxConnectionIdleTime) {
        this.connectionUrl = connectionUrl;
        this.maxConnectionIdleTime = maxConnectionIdleTime;
    }


    @Override
    protected MongoProvider initialize() {
        provider = new MongoProviderImpl(connectionUrl, maxConnectionIdleTime);
        return provider;
    }

    /**
     * Closes the underlying {@link MongoProvider} connection.
     * To be invoked on application exit.
     */
    public void close() {
        if (provider != null) {
            provider.close();
        }
    }
}
