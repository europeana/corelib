package eu.europeana.corelib.search.impl;

import org.junit.Ignore;
import org.junit.Test;

import eu.europeana.corelib.logging.Log;
import eu.europeana.corelib.logging.Logger;
import eu.europeana.corelib.mongo.server.EdmMongoServer;
import eu.europeana.corelib.search.TestSearchService;
import eu.europeana.corelib.search.impl.SearchServiceImpl;
import eu.europeana.corelib.tools.lookuptable.EuropeanaIdMongoServer;

@Ignore
public class TestSearchServiceImpl extends SearchServiceImpl implements
		TestSearchService {

	
	
	@Override
	public void setEdmMongoServer(EdmMongoServer mongoServer) {
		this.mongoServer = mongoServer;

	}



	@Override
	public void setEuropeanaIdMongoServer(EuropeanaIdMongoServer mongoServer) {
		this.idServer = mongoServer;
	}





	@Override
	public void setLogger(Logger log) {
		this.log = log;
		
	}
	
	@Override
	public boolean isHierarchy(String nodeId){
		return true;
	}
}
