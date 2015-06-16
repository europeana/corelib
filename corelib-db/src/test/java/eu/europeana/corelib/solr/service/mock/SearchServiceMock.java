/*
 * Copyright 2007-2012 The Europeana Foundation
 *
 *  Licenced under the EUPL, Version 1.1 (the "Licence") and subsequent versions as approved
 *  by the European Commission;
 *  You may not use this work except in compliance with the Licence.
 *
 *  You may obtain a copy of the Licence at:
 *  http://joinup.ec.europa.eu/software/page/eupl
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under
 *  the Licence is distributed on an "AS IS" basis, without warranties or conditions of
 *  any kind, either express or implied.
 *  See the Licence for the specific language governing permissions and limitations under
 *  the Licence.
 */

package eu.europeana.corelib.solr.service.mock;

import eu.europeana.corelib.definitions.edm.beans.BriefBean;
import eu.europeana.corelib.definitions.edm.beans.FullBean;
import eu.europeana.corelib.definitions.edm.beans.IdBean;
import eu.europeana.corelib.definitions.edm.entity.Aggregation;
import eu.europeana.corelib.definitions.edm.entity.Proxy;
import eu.europeana.corelib.definitions.solr.DocType;
import eu.europeana.corelib.definitions.solr.model.Query;
import eu.europeana.corelib.definitions.solr.model.Term;
import eu.europeana.corelib.edm.exceptions.MongoDBException;
import eu.europeana.corelib.edm.exceptions.SolrTypeException;
import eu.europeana.corelib.neo4j.entity.Neo4jBean;
import eu.europeana.corelib.neo4j.entity.Neo4jStructBean;
import eu.europeana.corelib.search.SearchService;
import eu.europeana.corelib.search.model.ResultSet;
import eu.europeana.corelib.solr.bean.impl.FullBeanImpl;
import eu.europeana.corelib.solr.entity.AggregationImpl;
import org.apache.commons.lang.NotImplementedException;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FacetField.Count;

import java.io.IOException;
import java.util.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * @see eu.europeana.corelib.search.SearchService
 */
public class SearchServiceMock implements SearchService {

    public static final String[] TITLE = new String[]{"Mock Title"};
    public static final String[] AUTHOR = new String[]{"Mock Author"};
    public static final String[] THUMBNAIL = new String[]{"MockThumbnail.jpg"};

    @SuppressWarnings("unchecked")
    @Override
    public FullBean findById(String europeanaObjectId, boolean similarItems) {
        FullBean mockBean = mock(FullBean.class);
        Proxy proxy = mock(Proxy.class);
        Aggregation aggregation = mock(Aggregation.class);

        Map<String, List<String>> dcPublisher = new HashMap<>();
        dcPublisher.put("def", Collections.singletonList(AUTHOR[0]));

        when(mockBean.getTitle()).thenReturn(TITLE);
        when(mockBean.getId()).thenReturn(europeanaObjectId);
        when(mockBean.getType()).thenReturn(DocType.TEXT);
        when(mockBean.getAbout()).thenReturn(europeanaObjectId);
        when((List<Aggregation>) mockBean.getAggregations()).thenReturn(Collections.singletonList(aggregation));
        when((List<Proxy>) mockBean.getProxies()).thenReturn(Collections.singletonList(proxy));

        when(proxy.getDcPublisher()).thenReturn(dcPublisher);
        when(aggregation.getEdmObject()).thenReturn(THUMBNAIL[0]);
        return mockBean;
    }

    @Override
    public FullBean findById(String collectionId, String recordId, boolean similarItems) throws MongoDBException {

        // not needed in this mock...
        return null;
    }

    @Override
    public <T extends IdBean> ResultSet<T> search(Class<T> beanClazz, Query query) {
        // not needed in this mock...
        return null;
    }

    @Override
    public List<Term> suggestions(String query, int pageSize) {
        return null;
    }

    @Override
    public List<Count> createCollections(String facetFieldName, String queryString, String... refinements)
            throws SolrTypeException {
        return null;
    }

    @Override
    public List<BriefBean> findMoreLikeThis(String europeanaObjectId)
            throws SolrServerException {
        return null;
    }

    @Override
    public List<Term> suggestions(String query, int pageSize, String field)
            throws SolrTypeException {
        return null;
    }

    @Override
    public Map<String, Integer> seeAlso(List<String> params) {
        return null;
    }

    @Override
    public List<BriefBean> findMoreLikeThis(String europeanaObjectId, int count)
            throws SolrServerException {
        return null;
    }

    @Override
    public <T extends IdBean> ResultSet<T> sitemap(Class<T> beanInterface,
                                                   Query query) throws SolrTypeException {
        return null;
    }

    @Override
    public Date getLastSolrUpdate() throws SolrServerException, IOException {
        return null;
    }

    @Override
    public FullBean resolve(String europeanaObjectId, boolean similarItems)
            throws SolrTypeException {
        return null;
    }

    @Override
    public FullBean resolve(String collectionId, String recordId,
                            boolean similarItems) throws SolrTypeException {
        return null;
    }

    @Override
    public String resolveId(String europeanaObjectId) {
        return null;
    }

    @Override
    public String resolveId(String collectionId, String recordId) {
        return null;
    }

    @Override
    public Map<String, Integer> queryFacetSearch(String query, String[] qf,
                                                 List<String> queries) {
        return null;
    }

    @Override
    public Neo4jBean getHierarchicalBean(String nodeId) {
        return null;
    }

    @Override
    public List<Neo4jBean> getChildren(String nodeId, int offset, int limit) {
        return null;
    }

    @Override
    public List<Neo4jBean> getChildren(String nodeId, int offset) {
        return null;
    }

    @Override
    public List<Neo4jBean> getChildren(String nodeId) {
        return null;
    }

    @Override
    public List<Neo4jBean> getPreceedingSiblings(String nodeId, int limit) {
        return null;
    }

    @Override
    public List<Neo4jBean> getPreceedingSiblings(String nodeId) {
        return null;
    }

    @Override
    public List<Neo4jBean> getFollowingSiblings(String nodeId, int limit) {
        return null;
    }

    @Override
    public List<Neo4jBean> getFollowingSiblings(String nodeId) {
        return null;
    }

    @Override
    public long getChildrenCount(String nodeId) {
        return 0;
    }

    @Override
    public Neo4jStructBean getInitialStruct(String nodeId) {
        return new Neo4jStructBean();
    }

    @Override
    public boolean isHierarchy(String nodeId) {
        return false;
    }

    @Override
    public Integer search(Integer mediaType, String mimeType, String imageSize, Boolean imageColor, Boolean imageGrayScale, String imageAspectRatio, String imageColorPalette, Boolean soundHQ, String soundDuration, Boolean videoHQ, String videoDuration) {
        throw new NotImplementedException();
    }
}
