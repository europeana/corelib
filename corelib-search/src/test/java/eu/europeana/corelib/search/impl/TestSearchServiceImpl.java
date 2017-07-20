package eu.europeana.corelib.search.impl;

import eu.europeana.corelib.definitions.edm.beans.IdBean;
import eu.europeana.corelib.definitions.solr.model.Query;
import eu.europeana.corelib.edm.exceptions.SolrTypeException;
import eu.europeana.corelib.search.model.ResultSet;
import org.junit.Ignore;

@Ignore
public class TestSearchServiceImpl extends SearchServiceImpl {

    @Override
    public <T extends IdBean> ResultSet<T> search(Class<T> beanInterface, Query query) throws SolrTypeException {
        ResultSet<T> resultSet = super.search(beanInterface, query);
        System.out.println("QUERY: " + query.toString() + "   RESULTSIZE: " + resultSet.getResultSize());
        return resultSet;
    }

    @Override
    public boolean isHierarchy(String nodeId, int hierarchyTimeout) {
        return true;
    }
}
