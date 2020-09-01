package eu.europeana.corelib.mongo.server.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoClientSettings.Builder;
import com.mongodb.client.MongoClient;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import dev.morphia.mapping.Mapper;
import dev.morphia.mapping.MappingException;
import dev.morphia.query.experimental.filters.Filters;
import eu.europeana.corelib.definitions.edm.beans.FullBean;
import eu.europeana.corelib.edm.exceptions.MongoDBException;
import eu.europeana.corelib.edm.exceptions.MongoRuntimeException;
import eu.europeana.corelib.edm.model.metainfo.WebResourceMetaInfoImpl;
import eu.europeana.corelib.mongo.server.EdmMongoServer;
import eu.europeana.corelib.solr.bean.impl.FullBeanImpl;
import eu.europeana.corelib.solr.entity.AgentImpl;
import eu.europeana.corelib.solr.entity.AggregationImpl;
import eu.europeana.corelib.solr.entity.BasicProxyImpl;
import eu.europeana.corelib.solr.entity.ConceptImpl;
import eu.europeana.corelib.solr.entity.ConceptSchemeImpl;
import eu.europeana.corelib.solr.entity.EuropeanaAggregationImpl;
import eu.europeana.corelib.solr.entity.EventImpl;
import eu.europeana.corelib.solr.entity.PhysicalThingImpl;
import eu.europeana.corelib.solr.entity.PlaceImpl;
import eu.europeana.corelib.solr.entity.ProvidedCHOImpl;
import eu.europeana.corelib.solr.entity.ProxyImpl;
import eu.europeana.corelib.solr.entity.TimespanImpl;
import eu.europeana.corelib.solr.entity.WebResourceImpl;
import eu.europeana.corelib.storage.impl.MongoProviderImpl;
import eu.europeana.corelib.web.exception.EuropeanaException;
import eu.europeana.corelib.web.exception.ProblemType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * @author Yorgos.Mamakis@ kb.nl
 * @see eu.europeana.corelib.mongo.server.EdmMongoServer
 */
public class EdmMongoServerImpl implements EdmMongoServer {

  private static final Logger LOG = LogManager.getLogger(EdmMongoServerImpl.class);

  private MongoClient mongoClient;
  private String databaseName;
  private Datastore datastore;

  /**
   * Create a new Morphia datastore to do get/delete/save operations on the database Any required
   * login credentials as well as connection options (like timeouts) should be set in advance in the
   * provided mongoClient Used by corelib.search embeddedMongoProvider (unit test usage) only
   *
   * @deprecated please use EdmMongoServerImpl(MongoClient, String, boolean)
   */
  @Deprecated
  public EdmMongoServerImpl(MongoClient mongoClient, String databaseName) {
    this(mongoClient, databaseName, false);
  }

  /**
   * Create a new Morphia datastore to do get/delete/save operations on the database Any required
   * login credentials as well as connection options (like timeouts) should be set in advance in the
   * provided mongoClient Used by corelib.search embeddedMongoProvider (unit test usage) only
   *
   * @param createIndexes, if true then it will try to create the necessary indexes if needed
   */
  public EdmMongoServerImpl(MongoClient mongoClient, String databaseName, boolean createIndexes) {
    this.mongoClient = mongoClient;
    this.databaseName = databaseName;
    createDatastore(createIndexes);
  }

  /**
   * Create a new datastore to do get/delete/save operations on the database.
   *
   * @param hosts comma-separated host names
   * @param ports comma-separated port numbers
   * @deprecated please use EdmMongoServerImpl(MongoClient, String, boolean)    *
   */
  @Deprecated
  public EdmMongoServerImpl(String hosts, String ports, String databaseName, String username,
      String password) {
    this(hosts, ports, databaseName, username, password, false);
  }

  /**
   * Create a new datastore to do get/delete/save operations on the database. (called only from #102
   * above, which is deprecated / unused)
   *
   * @param hosts comma-separated host names
   * @param ports comma-separated port numbers
   * @param createIndexes, if true then it will try to create the necessary indexes if needed
   * @deprecated please use EdmMongoServerImpl(MongoClient, String, boolean)
   */
  @Deprecated
  public EdmMongoServerImpl(String hosts, String ports, String databaseName, String username,
      String password, boolean createIndexes) {
    //Keep alive is removed and by default enabled
    final Builder mongoClientSettingsBuilder = MongoClientSettings.builder()
        .applyToSocketSettings(builder ->
            builder.connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(6, TimeUnit.SECONDS))
        .applyToConnectionPoolSettings(builder -> builder.maxSize(10));

    this.mongoClient = new MongoProviderImpl(hosts, ports, databaseName, username, password,
        mongoClientSettingsBuilder).getMongoClient();
    this.databaseName = databaseName;
    createDatastore(createIndexes);
  }

  private void createDatastore(boolean createIndexes) {
    this.datastore = Morphia.createDatastore(this.mongoClient, databaseName);
    final Mapper mapper = datastore.getMapper();

    mapper.map(FullBeanImpl.class);
    mapper.map(ProvidedCHOImpl.class);
    mapper.map(AgentImpl.class);
    mapper.map(AggregationImpl.class);
    mapper.map(ConceptImpl.class);
    mapper.map(ProxyImpl.class);
    mapper.map(PlaceImpl.class);
    mapper.map(TimespanImpl.class);
    mapper.map(WebResourceImpl.class);
    mapper.map(EuropeanaAggregationImpl.class);
    mapper.map(EventImpl.class);
    mapper.map(PhysicalThingImpl.class);
    mapper.map(ConceptSchemeImpl.class);
    mapper.map(BasicProxyImpl.class);
    mapper.map(WebResourceMetaInfoImpl.class);

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
      FullBeanImpl result = datastore.find(FullBeanImpl.class).filter(Filters.eq("about", id)).first();
      LOG.debug("Mongo query find fullbean {} finished in {} ms", id,
          (System.currentTimeMillis() - start));
      return result;
    } catch (RuntimeException re) {
      if (re.getCause() != null &&
          (re.getCause() instanceof MappingException || re
              .getCause() instanceof java.lang.ClassCastException)) {
        throw new MongoDBException(ProblemType.RECORD_RETRIEVAL_ERROR, re);
      } else {
        throw new MongoRuntimeException(ProblemType.MONGO_UNREACHABLE, re);
      }
    }
  }

  @Override
  public Map<String, WebResourceMetaInfoImpl> retrieveWebMetaInfos(List<String> hashCodes) {
    Map<String, WebResourceMetaInfoImpl> metaInfos = new HashMap<>();

    final BasicDBObject basicObject = new BasicDBObject("$in",
        hashCodes);   // e.g. {"$in":["1","2","3"]}
    long start = 0;
    if (LOG.isDebugEnabled()) {
      start = System.currentTimeMillis();
    }
    List<WebResourceMetaInfoImpl> metaInfoList = getDatastore().find(WebResourceMetaInfoImpl.class)
        .disableValidation()
        .filter(Filters.eq("_id", basicObject)).iterator().toList();
    LOG.debug("Mongo query find metainfo for {} webresources done in {} ms", hashCodes.size(),
        (System.currentTimeMillis() - start));

    start = System.currentTimeMillis();
    metaInfoList.forEach(cursor -> {
      String id = cursor.getId();
      metaInfos.put(id, cursor);
    });
    LOG.debug("Mongo cursor done in {} ms", (System.currentTimeMillis() - start));
    return metaInfos;

  }

  @Override
  public String toString() {
    return "MongoDB: [Hosts: " + mongoClient.getClusterDescription().getClusterSettings().getHosts() + "]\n"
        + "[DB: " + databaseName + "]\n";
  }

  @Override
  public <T> T searchByAbout(Class<T> clazz, String about) {
    return datastore.find(clazz).filter(Filters.eq("about", about)).first();
  }

  @Override
  public void close() {
    if (mongoClient != null) {
      LOG.info("Closing MongoClient for EDMMongoServer");
      mongoClient.close();
    }
  }
}
