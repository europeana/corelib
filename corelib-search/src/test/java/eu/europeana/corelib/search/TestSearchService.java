package eu.europeana.corelib.search;

import eu.europeana.corelib.logging.Logger;
import eu.europeana.corelib.mongo.server.EdmMongoServer;
import eu.europeana.corelib.search.SearchService;
import eu.europeana.corelib.tools.lookuptable.EuropeanaIdMongoServer;

public interface TestSearchService extends SearchService {

	public void setEdmMongoServer(EdmMongoServer mongoServer);
	
	public void setEuropeanaIdMongoServer(EuropeanaIdMongoServer mongoServer);
	
	public void setLogger(Logger log);
}
