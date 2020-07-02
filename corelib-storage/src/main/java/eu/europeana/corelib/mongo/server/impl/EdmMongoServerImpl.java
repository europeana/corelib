package eu.europeana.corelib.mongo.server.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import dev.morphia.mapping.MappingException;
import eu.europeana.corelib.definitions.edm.beans.FullBean;
import eu.europeana.corelib.edm.exceptions.MongoDBException;
import eu.europeana.corelib.edm.exceptions.MongoRuntimeException;
import eu.europeana.corelib.edm.model.metainfo.WebResourceMetaInfoImpl;
import eu.europeana.corelib.mongo.server.EdmMongoServer;
import eu.europeana.corelib.solr.bean.impl.FullBeanImpl;
import eu.europeana.corelib.solr.entity.*;
import eu.europeana.corelib.storage.impl.MongoProviderImpl;
import eu.europeana.corelib.web.exception.EuropeanaException;
import eu.europeana.corelib.web.exception.ProblemType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @see eu.europeana.corelib.mongo.server.EdmMongoServer
 * @author Yorgos.Mamakis@ kb.nl
 *
 */
public class EdmMongoServerImpl implements EdmMongoServer {

    private static final Logger LOG = LogManager.getLogger(EdmMongoServerImpl.class);

    private              MongoClient            mongoClient;
    private              String                 databaseName;
    private              Datastore              datastore;

    /**
     * Create a new Morphia datastore to do get/delete/save operations on the database
     * Any required login credentials as well as connection options (like timeouts) should be set in advance in the
     * provided mongoClient
     * Used by corelib.search embeddedMongoProvider (unit test usage) only
     *
     * @deprecated please use EdmMongoServerImpl(MongoClient, String, boolean)
     *
     * @param mongoClient
     * @param databaseName
     */
    @Deprecated
    public EdmMongoServerImpl(MongoClient mongoClient, String databaseName)  {
        this(mongoClient, databaseName, false);
    }

    /**
     * Create a new Morphia datastore to do get/delete/save operations on the database
     * Any required login credentials as well as connection options (like timeouts) should be set in advance in the
     * provided mongoClient
     * Used by corelib.search embeddedMongoProvider (unit test usage) only
     *
     * @param mongoClient
     * @param databaseName
     * @param createIndexes, if true then it will try to create the necessary indexes if needed
     */
    public EdmMongoServerImpl(MongoClient mongoClient, String databaseName, boolean createIndexes) {
        this.mongoClient = mongoClient;
        this.databaseName = databaseName;
        createDatastore(createIndexes);
    }

    /**
     * Create a new datastore to do get/delete/save operations on the database.
     * @deprecated              unused
     *
     * @param hosts comma-separated host names
     * @param ports comma-separated port numbers
     * @param databaseName
     * @param username
     * @param password
     * @deprecated please use EdmMongoServerImpl(MongoClient, String, boolean)    *
     */
    @Deprecated
    public EdmMongoServerImpl(String hosts, String ports, String databaseName, String username, String password) {
        this(hosts, ports, databaseName, username, password, false);
    }

    /**
     * Create a new datastore to do get/delete/save operations on the database.
     * (called only from #102 above, which is deprecated / unused)
     *
     * @param hosts comma-separated host names
     * @param ports comma-separated port numbers
     * @param databaseName
     * @param username
     * @param password
     * @param createIndexes, if true then it will try to create the necessary indexes if needed
     * @deprecated please use EdmMongoServerImpl(MongoClient, String, boolean)
     */
    @Deprecated
    public EdmMongoServerImpl(String hosts, String ports, String databaseName, String username, String password,
                              boolean createIndexes) {
        MongoClientOptions.Builder options = MongoClientOptions.builder();
        options.socketKeepAlive(true);
        options.connectionsPerHost(10);
        options.connectTimeout(5000);
        options.socketTimeout(6000);
        this.mongoClient = new MongoProviderImpl(hosts, ports, databaseName, username, password, options).getMongo();
        this.databaseName = databaseName;
        createDatastore(createIndexes);
    }

    private void createDatastore(boolean createIndexes) {
        Morphia morphia = new Morphia();

        morphia.map(FullBeanImpl.class);
        morphia.map(ProvidedCHOImpl.class);
        morphia.map(AgentImpl.class);
        morphia.map(AggregationImpl.class);
        morphia.map(ConceptImpl.class);
        morphia.map(ProxyImpl.class);
        morphia.map(PlaceImpl.class);
        morphia.map(TimespanImpl.class);
        morphia.map(WebResourceImpl.class);
        morphia.map(EuropeanaAggregationImpl.class);
        morphia.map(EventImpl.class);
        morphia.map(PhysicalThingImpl.class);
        morphia.map(ConceptSchemeImpl.class);
        morphia.map(BasicProxyImpl.class);
        morphia.map(WebResourceMetaInfoImpl.class);

        datastore = morphia.createDatastore(mongoClient, databaseName);
        if (createIndexes) {
            datastore.ensureIndexes();
        }
        LOG.info("Morphia EDMMongoServer datastore is created");
    }

    @Override
    public Datastore getDatastore() {
        return this.datastore;
    }

    @Override
    public FullBean getFullBean(String id) throws EuropeanaException {
        try {
            long start = 0;
            if (LOG.isDebugEnabled()) {
                start = System.currentTimeMillis();
            }
            FullBeanImpl result =  datastore.find(FullBeanImpl.class).field("about").equal(id).get();
            LOG.debug ("Mongo query find fullbean {} finished in {} ms", id, (System.currentTimeMillis() - start));
            return result;
        } catch (RuntimeException re) {
            if (re.getCause() != null &&
                    (re.getCause() instanceof MappingException || re.getCause() instanceof java.lang.ClassCastException)) {
                throw new MongoDBException(ProblemType.RECORD_RETRIEVAL_ERROR, re);
            } else {
                throw new MongoRuntimeException(ProblemType.MONGO_UNREACHABLE, re);
            }
        }
    }

    @Override
    public Map<String, WebResourceMetaInfoImpl> retrieveWebMetaInfos(List<String> hashCodes) {
        Map<String, WebResourceMetaInfoImpl> metaInfos = new HashMap<>();

        final BasicDBObject basicObject = new BasicDBObject("$in", hashCodes);   // e.g. {"$in":["1","2","3"]}
        long start = 0;
        if (LOG.isDebugEnabled()) {
            start = System.currentTimeMillis();
        }
        List<WebResourceMetaInfoImpl> metaInfoList = getDatastore().find(WebResourceMetaInfoImpl.class)
                .disableValidation()
                .field("_id").equal(basicObject).asList();
        LOG.debug("Mongo query find metainfo for {} webresources done in {} ms", hashCodes.size(), (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        metaInfoList.forEach(cursor -> {
            String id= cursor.getId();
            metaInfos.put(id, cursor);
        });
        LOG.debug("Mongo cursor done in {} ms", (System.currentTimeMillis() - start));
        return metaInfos;

    }
    @Override
    public String toString() {
        return "MongoDB: [Host: " + mongoClient.getAddress().getHost() + "]\n"
                + "[Port: " + mongoClient.getAddress().getPort() + "]\n"
                + "[DB: " + databaseName + "]\n";
    }

    @Override
    public <T> T searchByAbout(Class<T> clazz, String about) {
        return datastore.find(clazz).filter("about", about).get();
    }

    @Override
    public void close() {
        if (mongoClient != null) {
            LOG.info("Closing MongoClient for EDMMongoServer");
            mongoClient.close();
        }
    }
}
