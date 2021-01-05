package eu.europeana.corelib.record.config.initializers;

import com.mongodb.client.MongoClient;
import eu.europeana.corelib.utils.LazyInitializer;
import eu.europeana.metis.mongo.connection.MongoClientProvider;

/**
 * Lazily initializes a connection to a Mongo instance
 */
public class MongoClientInitializer extends LazyInitializer<MongoClient> {

    private final String connectionUrl;

    private MongoClient mongoClient;

    public MongoClientInitializer(String connectionUrl) {
        this.connectionUrl = connectionUrl;
    }


    @Override
    protected MongoClient initialize() {
        mongoClient = MongoClientProvider.create(connectionUrl).createMongoClient();
        return this.mongoClient;
    }

    /**
     * Closes the underlying connection.
     * To be invoked on application exit.
     */
    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}
