package eu.europeana.corelib.server.impl;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.gridfs.GridFS;
import eu.europeana.corelib.server.ThumbnailMongoServer;

import java.util.logging.Logger;

public class ThumbnailMongoServerImpl implements ThumbnailMongoServer {

    private final Logger log = Logger.getLogger(getClass().getName());

    private Mongo mongoServer;

    private String username;
    private String password;

    private String databaseName;
    private String namespace;

    private GridFS gridFS;

    public ThumbnailMongoServerImpl(Mongo mongoServer, String username, String password,
                                    String databaseName, String namespace) {
        this.mongoServer = mongoServer;
        this.mongoServer.getMongoOptions().socketKeepAlive = true;
        this.mongoServer.getMongoOptions().autoConnectRetry = true;
        this.mongoServer.getMongoOptions().connectionsPerHost = 10;
        this.mongoServer.getMongoOptions().connectTimeout = 5000;
        this.mongoServer.getMongoOptions().socketTimeout = 6000;
        this.username = username;
        this.password = password;
        this.databaseName = databaseName;
        this.namespace = namespace;

        createGridFS();
    }

    private void createGridFS() {
        DB db;

        if(!username.equals("")) {
            db = mongoServer.getDB("admin");
            final Boolean auth = db.authenticate(username, password.toCharArray());
            if (!auth) {
                System.out.println("Mongo auth error");
                System.exit(-1);
            }
        }

        db = mongoServer.getDB(databaseName);

        gridFS = new GridFS(db, namespace);
    }

    @Override
    public GridFS getGridFS() {
        return gridFS;
    }

    @Override
    public String toString() {
        System.out.println(databaseName);
        return "MongoDB: [Host: " + mongoServer.getAddress().getHost() + "]\n"
                + "[Port: " + mongoServer.getAddress().getPort() + "]\n"
                + "[DB: " + databaseName + "]\n";
    }

    @Override
    public void close() {
        log.info("ThumbnailMongoServer is closed");
        mongoServer.close();
    }
}
