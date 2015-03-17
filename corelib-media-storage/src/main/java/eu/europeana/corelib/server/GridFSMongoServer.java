package eu.europeana.corelib.server;

import com.mongodb.gridfs.GridFS;

public interface GridFSMongoServer {

    String toString();

    GridFS getGridFS();

    void close();
}
