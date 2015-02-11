package eu.europeana.corelib.solr;

import com.mongodb.gridfs.GridFS;

public interface GridFSMongoServer {

    String toString();

    GridFS getGridFS();

    void close();
}
