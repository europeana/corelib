package eu.europeana.corelib.record.config.initializers;

import com.mongodb.client.MongoClient;
import eu.europeana.metis.mongo.connection.MongoClientProvider;

/**
 * Initializes a connection to a Mongo instance
 */
public class MongoClientInitializer {

    private final String connectionUrl;

    private MongoClient mongoClient;

    public MongoClientInitializer(String connectionUrl) {
        this.connectionUrl = connectionUrl;
        this.mongoClient = MongoClientProvider.create(this.connectionUrl).createMongoClient();
    }

    public MongoClient get() {
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
