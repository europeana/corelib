package eu.europeana.corelib.mongo.server.impl;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import eu.europeana.corelib.storage.impl.MongoProviderImpl;
import eu.europeana.corelib.web.exception.EuropeanaException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.mapping.MappingException;
import eu.europeana.corelib.definitions.edm.beans.FullBean;
import eu.europeana.corelib.web.exception.ProblemType;
import eu.europeana.corelib.edm.exceptions.MongoDBException;
import eu.europeana.corelib.edm.exceptions.MongoRuntimeException;
import eu.europeana.corelib.mongo.server.EdmMongoServer;
import eu.europeana.corelib.solr.bean.impl.FullBeanImpl;
import eu.europeana.corelib.solr.entity.*;
import eu.europeana.corelib.tools.lookuptable.EuropeanaId;
import eu.europeana.corelib.tools.lookuptable.EuropeanaIdMongoServer;


/**
 * @see eu.europeana.corelib.mongo.server.EdmMongoServer
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
public class EdmMongoServerImpl implements EdmMongoServer {

	private static final Logger LOG = LogManager.getLogger(EdmMongoServerImpl.class);

	private MongoClient mongoClient;
	private String databaseName;
	private Datastore datastore;
	private EuropeanaIdMongoServer europeanaIdMongoServer;
	/* A lot of old records are in the EuropeanaId database with "http://www.europeana.eu/resolve/record/1/2" as 'oldId' */
	private static final String RESOLVE_PREFIX = "http://www.europeana.eu/resolve/record";
	// TODO October 2018 It seems there are no records with this prefix in the EuropeanaId database so most likely this can be removed
	private static final String PORTAL_PREFIX = "http://www.europeana.eu/portal/record";

	/**
	 * Create a new Morphia datastore to do get/delete/save operations on the database
	 * Any required login credentials as well as connection options (like timeouts) should be set in advance in the
	 * provided mongoClient
	 * @param mongoClient
	 * @param databaseName
	 * @throws MongoDBException
	 */
	@Deprecated
	public EdmMongoServerImpl(MongoClient mongoClient, String databaseName) throws MongoDBException {
        this(mongoClient, databaseName, false);
	}

	/**
	 * Create a new Morphia datastore to do get/delete/save operations on the database
	 * Any required login credentials as well as connection options (like timeouts) should be set in advance in the
	 * provided mongoClient
	 * @param mongoClient
	 * @param databaseName
	 * @param createIndexes, if true then it will try to create the necessary indexes if needed
	 * @throws MongoDBException
	 */
	public EdmMongoServerImpl(MongoClient mongoClient, String databaseName, boolean createIndexes) throws MongoDBException {
		this.mongoClient = mongoClient;
		this.databaseName = databaseName;
		createDatastore(createIndexes);
	}

	/**
	 * Create a new datastore to do get/delete/save operations on the database.
	 * @param hosts comma-separated host names
	 * @param ports comma-separated port numbers
	 * @param databaseName
	 * @param username
	 * @param password
	 */
	@Deprecated
	public EdmMongoServerImpl(String hosts, String ports, String databaseName, String username, String password)
			throws MongoDBException {
        this(hosts, ports, databaseName, username, password, false);
	}

	/**
	 * Create a new datastore to do get/delete/save operations on the database.
	 * @param hosts comma-separated host names
	 * @param ports comma-separated port numbers
	 * @param databaseName
	 * @param username
	 * @param password
	 * @param createIndexes, if true then it will try to create the necessary indexes if needed
	 */
	public EdmMongoServerImpl(String hosts, String ports, String databaseName, String username, String password,
							  boolean createIndexes) throws MongoDBException {
		MongoClientOptions.Builder options = MongoClientOptions.builder();
		options.socketKeepAlive(true);
		options.connectionsPerHost(10);
		options.connectTimeout(5000);
		options.socketTimeout(6000);
		this.mongoClient = new MongoProviderImpl(hosts, ports, databaseName, username, password, options).getMongo();
		this.databaseName = databaseName;
		createDatastore(createIndexes);
	}

	@Override
	public void setEuropeanaIdMongoServer(
			EuropeanaIdMongoServer europeanaIdMongoServer) {
		this.europeanaIdMongoServer = europeanaIdMongoServer;
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
			return datastore.find(FullBeanImpl.class).field("about").equal(id).get();
		} catch (RuntimeException re) {
			if (re.getCause() != null &&
					(re.getCause() instanceof MappingException || re.getCause() instanceof java.lang.ClassCastException)) {
				throw new MongoDBException(re, ProblemType.RECORD_RETRIEVAL_ERROR);
			} else {
				throw new MongoRuntimeException(re, ProblemType.MONGO_UNREACHABLE);
			}
		}
	}

	@Override
	public FullBean resolve(String id) {

		EuropeanaId newId = europeanaIdMongoServer.retrieveEuropeanaIdFromOld(id);
		if (newId != null) {
			newId = europeanaIdMongoServer.retrieveEuropeanaIdFromOld(RESOLVE_PREFIX + id);
		}
		if (newId != null) {
			newId = europeanaIdMongoServer.retrieveEuropeanaIdFromOld(PORTAL_PREFIX	+ id);
		}

		if (newId != null) {
			//TODO For now update time is disabled because it's rather expensive operation and we need to think of a better approach
			//europeanaIdMongoServer.updateTime(newId.getNewId(), id);
			return datastore.find(FullBeanImpl.class).field("about").equal(newId.getNewId()).get();
		}

		LOG.info("Unresolvable Europeana ID: {}", id);
		return null;
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
