package eu.europeana.corelib.dereference.impl;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.Mongo;

import eu.europeana.corelib.solr.MongoServer;

public class VocabularyMongoServer implements MongoServer {

	private Mongo mongoServer;
	private String databaseName;
	private Datastore datastore;

	@Override
	public Datastore getDatastore() {
		return this.datastore;
	}

	@Override
	public void close() {
		mongoServer.close();
	}

	public VocabularyMongoServer(Mongo mongoServer, String databaseName) {
		
		this.mongoServer = mongoServer;
		this.databaseName = databaseName;
		createDatastore();
	}

	private void createDatastore() {

		Morphia morphia = new Morphia();
		morphia.map(ControlledVocabularyImpl.class);
		datastore = morphia.createDatastore(mongoServer, databaseName);
		datastore.ensureIndexes();
	}

}
