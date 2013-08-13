package eu.europeana.corelib.solr.service;

import eu.europeana.corelib.solr.server.EdmMongoServer;
import eu.europeana.corelib.tools.lookuptable.EuropeanaIdMongoServer;

public interface TestSearchService extends SearchService {

	public void setEdmMongoServer(EdmMongoServer mongoServer);
	
	public void setEuropeanaIdMongoServer(EuropeanaIdMongoServer mongoServer);
}
