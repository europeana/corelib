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

package eu.europeana.corelib.solr.server.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

import eu.europeana.corelib.definitions.solr.beans.FullBean;
import eu.europeana.corelib.solr.bean.impl.FullBeanImpl;
import eu.europeana.corelib.solr.entity.AgentImpl;
import eu.europeana.corelib.solr.entity.AggregationImpl;
import eu.europeana.corelib.solr.entity.ConceptImpl;
import eu.europeana.corelib.solr.entity.ConceptSchemeImpl;
import eu.europeana.corelib.solr.entity.EuropeanaAggregationImpl;
import eu.europeana.corelib.solr.entity.EuropeanaProxyImpl;
import eu.europeana.corelib.solr.entity.EventImpl;
import eu.europeana.corelib.solr.entity.PhysicalThingImpl;
import eu.europeana.corelib.solr.entity.PlaceImpl;
import eu.europeana.corelib.solr.entity.ProvidedCHOImpl;
import eu.europeana.corelib.solr.entity.ProxyImpl;
import eu.europeana.corelib.solr.entity.TimespanImpl;
import eu.europeana.corelib.solr.entity.WebResourceImpl;
import eu.europeana.corelib.solr.exceptions.MongoDBException;
import eu.europeana.corelib.solr.server.EdmMongoServer;
import eu.europeana.corelib.tools.lookuptable.EuropeanaId;
import eu.europeana.corelib.tools.lookuptable.EuropeanaIdMongoServer;

/**
 * @see eu.europeana.corelib.solr.server.EdmMongoServer
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
public class EdmMongoServerImpl implements EdmMongoServer {

	private Mongo mongoServer;
	private String databaseName;
	private String username;
	private String password;
	private Datastore datastore;
	private static final String EUROPEANA_ID_DB = "EuropeanaId";

	public EdmMongoServerImpl(Mongo mongoServer, String databaseName,
			String username, String password) throws MongoDBException {
		this.mongoServer = mongoServer;

		this.databaseName = databaseName;
		this.username = username;
		this.password = password;
		createDatastore();
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
		morphia.map(EuropeanaProxyImpl.class);
		morphia.map(ConceptSchemeImpl.class);

		datastore = morphia.createDatastore(mongoServer, databaseName);
		if (StringUtils.isNotBlank(this.username)
				&& StringUtils.isNotBlank(this.password)) {
			datastore.getDB().authenticate(this.username,
					this.password.toCharArray());
		}
		datastore.ensureIndexes();

	}

	@Override
	public Datastore getDatastore() {
		return this.datastore;
	}

	@Override
	public FullBean getFullBean(String id) {
		// If the id requested exists
		if (datastore.find(FullBeanImpl.class).field("about").equal(id).get() != null) {
			return datastore.find(FullBeanImpl.class).field("about").equal(id)
					.get();
		}

		return null;
	}

	@Override
	public FullBean resolve(String id) {
		EuropeanaIdMongoServer europeanaIdMongoServer = new EuropeanaIdMongoServer(
				mongoServer, EUROPEANA_ID_DB);
		// If it does not check whether it has been set in the past,
		// if it exists retrieve the newID and update the lastAccess field
		if (europeanaIdMongoServer.newIdExists(id)) {
			List<EuropeanaId> newIDList = europeanaIdMongoServer
					.retrieveEuropeanaIdFromOld(id);
			EuropeanaId newId = newIDList.get(0);
			newId.setLastAccess(new Date().getTime());
			europeanaIdMongoServer.saveEuropeanaId(newId);
			return datastore.find(FullBeanImpl.class).field("about")
					.equal(newIDList.get(0).getNewId()).get();
		}
		return null;
	}

	@Override
	public String toString() {
		return "MongoDB: [Host: " + mongoServer.getAddress().getHost() + "]\n"
				+ "[Port: " + mongoServer.getAddress().getPort() + "]\n"
				+ "[DB: " + databaseName + "]\n";
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T searchByAbout(Class<T> clazz, String about) {

		if (!clazz.isInstance(FullBeanImpl.class)) {
			return datastore.find(clazz, "about", about).get();
		} else {
			DBObject ref = (DBObject) datastore.find(clazz, "about", about)
					.get();
			return (T) ref;
		}
	}

	@Override
	public void close() {
		mongoServer.close();
	}

}
