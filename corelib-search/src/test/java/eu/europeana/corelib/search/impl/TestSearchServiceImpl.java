package eu.europeana.corelib.search.impl;

import eu.europeana.corelib.logging.Logger;
import eu.europeana.corelib.mongo.server.EdmMongoServer;
import eu.europeana.corelib.search.TestSearchService;
import eu.europeana.corelib.tools.lookuptable.EuropeanaIdMongoServer;
import org.apache.solr.client.solrj.SolrServer;
import org.junit.Ignore;

@Ignore
public class TestSearchServiceImpl extends SearchServiceImpl implements TestSearchService {

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
    public boolean isHierarchy(String nodeId) {
        return true;
    }
}
