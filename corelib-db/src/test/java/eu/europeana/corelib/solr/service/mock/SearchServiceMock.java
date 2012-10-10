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

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServerException;

import eu.europeana.corelib.definitions.solr.DocType;
import eu.europeana.corelib.definitions.solr.beans.BriefBean;
import eu.europeana.corelib.definitions.solr.beans.FullBean;
import eu.europeana.corelib.definitions.solr.beans.IdBean;
import eu.europeana.corelib.definitions.solr.entity.Aggregation;
import eu.europeana.corelib.definitions.solr.entity.Proxy;
import eu.europeana.corelib.definitions.solr.model.Query;
import eu.europeana.corelib.definitions.solr.model.Term;
import eu.europeana.corelib.solr.bean.impl.FullBeanImpl;
import eu.europeana.corelib.solr.entity.AggregationImpl;
import eu.europeana.corelib.solr.exceptions.SolrTypeException;
import eu.europeana.corelib.solr.model.ResultSet;
import eu.europeana.corelib.solr.service.SearchService;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * 
 * @see eu.europeana.corelib.solr.service.SearchService
 */
public class SearchServiceMock implements SearchService {

	public static final String[] TITLE = new String[]{"Mock Title"};
	public static final String[] AUTHOR = new String[]{"Mock Author"};
	public static final String[] THUMBNAIL = new String[]{"MockThumbnail.jpg"};
	public static final List<? extends Aggregation> aggregations2 = new ArrayList<AggregationImpl>();

	@Override
	public FullBean findById(String collectionId, String recordId) throws SolrTypeException {
		// not needed in this mock...
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public FullBean findById(String europeanaObjectId) {
		FullBean mockBean = createMock(FullBean.class);
		Proxy proxy =createMock(Proxy.class);
		Map<String,List<String>> dcPublisher = new HashMap<String,List<String>>();
		List<String> vals = new ArrayList<String>();
		vals.add(AUTHOR[0]);
		dcPublisher.put("def",vals);
		proxy.setDcPublisher(dcPublisher);
		Aggregation aggregation =createMock(AggregationImpl.class);
		FullBean bean2 = new FullBeanImpl();
		List<Aggregation> aggregations = new ArrayList<Aggregation>();
		aggregation.setEdmObject(THUMBNAIL[0]);
		aggregations.add(aggregation);
		List<Proxy> proxies = new ArrayList<Proxy>();
		proxies.add(proxy);
		bean2.setProxies(proxies);
		mockBean.setAggregations(aggregations);
		expect(mockBean.getTitle()).andStubReturn(TITLE);
		
		expect(bean2.getProxies().get(0).getDcPublisher()).andStubReturn(dcPublisher);
		expect(mockBean.getId()).andStubReturn(europeanaObjectId);
		expect((List<Aggregation>)mockBean.getAggregations()).andStubReturn(aggregations);
		expect(aggregation.getEdmObject()).andStubReturn(THUMBNAIL[0]);
		expect((List<Proxy>)mockBean.getProxies()).andStubReturn(proxies);
		expect(mockBean.getType()).andStubReturn(DocType.TEXT);
		expect(mockBean.getAbout()).andStubReturn(europeanaObjectId);
		replay(aggregation);
		replay(proxy);
		replay(mockBean);
		return mockBean;
	}

	@Override
	public <T extends IdBean> ResultSet<T> search(Class<T> beanClazz, Query query) {
		// not needed in this mock...
		return null;
	}

	@Override
	public List<Term> suggestions(String query, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FullBean resolve(String collectionId, String recordId)
			throws SolrTypeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FullBean resolve(String europeanaObjectId) throws SolrTypeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BriefBean> findMoreLikeThis(String europeanaObjectId)
			throws SolrServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Term> suggestions(String query, int pageSize, String field)
			throws SolrTypeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Map<String, Integer>> seeAlso(Map<String, List<String>> fields) {
		// TODO Auto-generated method stub
		return null;
	}
}
