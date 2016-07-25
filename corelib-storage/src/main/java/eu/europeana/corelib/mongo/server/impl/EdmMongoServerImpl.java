/*
 * Copyright 2007-2012 The Europeana Foundation
 *
 *  Licenced under the EUPL, Version 1.1 (the "Licence") and subsequent versions as approved
 *  by the European Commission;
 *  You may not use this work except in compliance with the Licence.
 * 
 *  You may obtain a copy of the Licence at:
 *  http://joinup.ec.europa.eu/software/page/eupl
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under
 *  the Licence is distributed on an "AS IS" basis, without warranties or conditions of
 *  any kind, either express or implied.
 *  See the Licence for the specific language governing permissions and limitations under
 *  the Licence.
 */

package eu.europeana.corelib.mongo.server.impl;

import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.mapping.MappingException;
import com.mongodb.Mongo;

import eu.europeana.corelib.definitions.edm.beans.FullBean;
import eu.europeana.corelib.definitions.exception.ProblemType;
import eu.europeana.corelib.edm.exceptions.MongoDBException;
import eu.europeana.corelib.edm.exceptions.MongoRuntimeException;
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
import eu.europeana.corelib.tools.lookuptable.EuropeanaId;
import eu.europeana.corelib.tools.lookuptable.EuropeanaIdMongoServer;

/**
 * @see eu.europeana.corelib.mongo.server.EdmMongoServer
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
public class EdmMongoServerImpl implements EdmMongoServer {

	private final Logger log = Logger.getLogger(getClass().getName());

	private Mongo mongoServer;
	private String databaseName;
	private String username;
	private String password;
	private Datastore datastore;
	EuropeanaIdMongoServer europeanaIdMongoServer;
	private final static String RESOLVE_PREFIX = "http://www.europeana.eu/resolve/record";
	private final static String PORTAL_PREFIX = "http://www.europeana.eu/portal/record";

	public EdmMongoServerImpl(Mongo mongoServer, String databaseName,
			String username, String password) throws MongoDBException {
		log.info("EDMMongoServer is instantiated");
		this.mongoServer = mongoServer;
		this.mongoServer.getMongoOptions().socketKeepAlive = true;
		this.mongoServer.getMongoOptions().autoConnectRetry = true;
		this.mongoServer.getMongoOptions().connectionsPerHost = 10;
		this.mongoServer.getMongoOptions().connectTimeout = 5000;
		this.mongoServer.getMongoOptions().socketTimeout = 6000;
		this.databaseName = databaseName;
		this.username = username;
		this.password = password;
		createDatastore();

	}

	@Override
	public void setEuropeanaIdMongoServer(
			EuropeanaIdMongoServer europeanaIdMongoServer) {
		this.europeanaIdMongoServer = europeanaIdMongoServer;
	}

	public EdmMongoServerImpl() {
	}

	private void createDatastore() {
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

		datastore = morphia.createDatastore(mongoServer, databaseName);

		if (StringUtils.isNotBlank(this.username)
				&& StringUtils.isNotBlank(this.password)) {
			datastore.getDB().authenticate(this.username,
					this.password.toCharArray());
		}
		datastore.ensureIndexes();
		log.info("EDMMongoServer datastore is created");
	}

	@Override
	public Datastore getDatastore() {
		return this.datastore;
	}

	@Override
	public FullBean getFullBean(String id) throws MongoDBException, MongoRuntimeException {
		try {
			return datastore.find(FullBeanImpl.class).field("about").equal(id)
					.get();
		} catch (RuntimeException re) {
			if (re.getCause() != null
				&& re.getCause() instanceof MappingException) {
				throw new MongoDBException(ProblemType.RECORD_RETRIEVAL_ERROR);
				
			} else {
				throw new MongoRuntimeException(ProblemType.MONGO_UNREACHABLE);
			}

		}
	}

	@Override
	public FullBean resolve(String id) {

		EuropeanaId newId = europeanaIdMongoServer
				.retrieveEuropeanaIdFromOld(id);
		if (newId != null) {
			europeanaIdMongoServer.updateTime(newId.getNewId(), id);
			return datastore.find(FullBeanImpl.class)
					.field("about").equal(newId.getNewId()).get();
		}

		newId = europeanaIdMongoServer
				.retrieveEuropeanaIdFromOld(RESOLVE_PREFIX + id);

		if (newId != null) {
			europeanaIdMongoServer.updateTime(newId.getNewId(), id);
			return datastore.find(FullBeanImpl.class).field("about")
					.equal(newId.getNewId()).get();
		}

		newId = europeanaIdMongoServer.retrieveEuropeanaIdFromOld(PORTAL_PREFIX
				+ id);

		if (newId != null) {
			europeanaIdMongoServer.updateTime(newId.getNewId(), id);
			return datastore.find(FullBeanImpl.class).field("about")
					.equal(newId.getNewId()).get();
		}

		log.info(String.format("Unresolvable Europeana ID: %s", id));
		return null;
	}

	@Override
	public String toString() {
		return "MongoDB: [Host: " + mongoServer.getAddress().getHost() + "]\n"
				+ "[Port: " + mongoServer.getAddress().getPort() + "]\n"
				+ "[DB: " + databaseName + "]\n";
	}

	@Override
	public <T> T searchByAbout(Class<T> clazz, String about) {

		return datastore.find(clazz).filter("about", about).get();
	}

	@Override
	public void close() {
		log.info("EDMMongoServer is closed");
		mongoServer.close();
	}
}
