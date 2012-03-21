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

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.Mongo;

import eu.europeana.corelib.definitions.solr.beans.FullBean;
import eu.europeana.corelib.solr.bean.impl.FullBeanImpl;
import eu.europeana.corelib.solr.entity.AgentImpl;
import eu.europeana.corelib.solr.entity.AggregationImpl;
import eu.europeana.corelib.solr.entity.ConceptImpl;
import eu.europeana.corelib.solr.entity.PlaceImpl;
import eu.europeana.corelib.solr.entity.ProvidedCHOImpl;
import eu.europeana.corelib.solr.entity.ProxyImpl;
import eu.europeana.corelib.solr.entity.TimespanImpl;
import eu.europeana.corelib.solr.entity.WebResourceImpl;
import eu.europeana.corelib.solr.exceptions.MongoDBException;
import eu.europeana.corelib.solr.server.MongoDBServer;

/**
 * @see eu.europeana.corelib.solr.server.MongoDBServer
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
public class MongoDBServerImpl implements MongoDBServer {

	private Mongo mongoServer;
	private String databaseName;
	private Datastore datastore;

	public MongoDBServerImpl(Mongo mongoServer, String databaseName)
			throws MongoDBException {
		this.mongoServer = mongoServer;
		this.databaseName = databaseName;
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
		
		datastore = morphia.createDatastore(mongoServer, databaseName);
		datastore.ensureIndexes();
	}

	@Override
	public Datastore getDatastore() {
		return this.datastore;
	}

	@Override
	public FullBean getFullBean(String id) {
		return datastore.find(FullBeanImpl.class).field("about")
				.equal(id).get();
	}

	@Override
	public String toString() {
		return "MongoDB: [Host: " + mongoServer.getAddress().getHost() + "]\n"
				+ "[Port: " + mongoServer.getAddress().getPort() + "]\n"
				+ "[DB: " + databaseName + "]\n";
	}
	
	@Override
	public <T> T searchByAbout(Class<T> clazz, String about){
		return datastore.find(clazz).field("about").equal(about).get();
	}

	@Override
	public void close(){
		mongoServer.close();
	}
}
