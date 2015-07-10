package eu.europeana.corelib.storage;

import com.mongodb.Mongo;

public interface MongoProvider {
    Mongo getMongo();
}
