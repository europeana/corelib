package eu.europeana.corelib.solr.mongodb.impl;

import java.net.UnknownHostException;

import org.bson.types.ObjectId;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;

import eu.europeana.corelib.definitions.exception.ProblemType;
import eu.europeana.corelib.solr.bean.FullBean;
import eu.europeana.corelib.solr.exceptions.MongoDBException;
import eu.europeana.corelib.solr.mongodb.MongoDBServer;

/**
 * @see eu.europeana.corelib.solr.mongodb.MongoDBServer
 * @author yorgos.mamakis@kb.nl
 *
 */
public class MongoDBServerImpl implements MongoDBServer {

	private Mongo mongoServer;
	private DB mongoDB;
	private Datastore datastore; 
	private Morphia morphia;
	public MongoDBServerImpl(String host, int port, String databaseName) throws MongoDBException {
		try{
			mongoServer = new Mongo(host, port);
			setDB(databaseName);
			createDatastore();
		}
		catch(UnknownHostException e){
			throw new MongoDBException(ProblemType.UNKNOWN_MONGO_DB_HOST);
		}
	}
	
	
	private void setDB(String databaseName) {
		mongoDB = mongoServer.getDB(databaseName);
	}
	
	
	private void createDatastore(){
		morphia = new Morphia();
		morphia.map(FullBean.class);
		datastore = morphia.createDatastore(mongoServer, mongoDB.getName());
		
	}

	@Override
	public FullBean getFullBean(ObjectId id) {		
		return datastore.find(FullBean.class).field("europeana_id").equal(id).get();
	}
	
	@Override
	public String toString(){
		return "MongoDB: [Host: " + mongoServer.getAddress().getHost() + "]\n"+
				"[Port: " +mongoServer.getAddress().getPort() +"]\n" +
				"[DB: " + mongoDB.getName() +"]\n";
	}

}
