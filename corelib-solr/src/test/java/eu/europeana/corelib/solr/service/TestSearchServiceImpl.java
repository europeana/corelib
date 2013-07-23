package eu.europeana.corelib.solr.service;

import eu.europeana.corelib.solr.server.EdmMongoServer;
import eu.europeana.corelib.solr.service.impl.SearchServiceImpl;
import eu.europeana.corelib.tools.lookuptable.EuropeanaIdMongoServer;

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

}
