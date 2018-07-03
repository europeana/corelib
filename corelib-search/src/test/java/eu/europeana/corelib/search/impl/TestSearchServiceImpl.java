package eu.europeana.corelib.search.impl;

import eu.europeana.corelib.definitions.edm.beans.IdBean;
import eu.europeana.corelib.definitions.solr.model.Query;
import eu.europeana.corelib.search.model.ResultSet;
import eu.europeana.corelib.web.exception.EuropeanaException;
import org.junit.Ignore;

@Ignore
public class TestSearchServiceImpl extends SearchServiceImpl {

    @Override
    public <T extends IdBean> ResultSet<T> search(Class<T> beanInterface, Query query) throws EuropeanaException {
        ResultSet<T> resultSet = super.search(beanInterface, query);
        System.out.println("QUERY: " + query.toString() + "   RESULTSIZE: " + resultSet.getResultSize());
        return resultSet;
    }
}
