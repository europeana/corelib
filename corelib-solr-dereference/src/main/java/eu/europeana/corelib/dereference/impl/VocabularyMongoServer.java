package eu.europeana.corelib.dereference.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;

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

	public VocabularyMongoServer(){
		
	}
	private void createDatastore() {

		Morphia morphia = new Morphia();
		morphia.map(ControlledVocabularyImpl.class)
				.map(EntityImpl.class);
		
		datastore = morphia.createDatastore(mongoServer, databaseName);
		
		datastore.ensureIndexes();
	}
	
	public ControlledVocabularyImpl getControlledVocabulary(String field, String filter){
		String[] splitName = filter.split("/");
		String vocabularyName = splitName[0]+"/"+splitName[1]+"/"+splitName[2]+"/";
		List<ControlledVocabularyImpl>vocabularies = getDatastore()
				.find(ControlledVocabularyImpl.class)
				.filter(field, vocabularyName).asList();
		for (ControlledVocabularyImpl vocabulary: vocabularies){
			boolean ruleController = true;
			for(String rule :vocabulary.getRules()){
				ruleController = ruleController && (filter.contains(rule)||StringUtils.equals(rule, "*"));
			}
			if(ruleController){
				return vocabulary;
			}
		}
		return null;
	}

}
