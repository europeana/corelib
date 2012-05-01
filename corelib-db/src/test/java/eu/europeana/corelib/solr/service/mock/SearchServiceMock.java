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

import java.util.List;

import eu.europeana.corelib.definitions.solr.DocType;
import eu.europeana.corelib.definitions.solr.beans.FullBean;
import eu.europeana.corelib.definitions.solr.beans.IdBean;
import eu.europeana.corelib.definitions.solr.model.Query;
import eu.europeana.corelib.solr.model.ResultSet;
import eu.europeana.corelib.solr.model.Term;
import eu.europeana.corelib.solr.service.SearchService;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * 
 * @see eu.europeana.corelib.solr.service.SearchService
 */
public class SearchServiceMock implements SearchService {
	
	public static final String[] TITLE=new String[]{"Mock Title"};
	public static final String[] AUTHOR=new String[]{"Mock Author"};
	public static final String[] THUMBNAIL=new String[]{"MockThumbnail.jpg"};

	@Override
	public FullBean findById(String europeanaObjectId) {
		FullBean mockBean = createMock(FullBean.class);
		expect(mockBean.getTitle()).andStubReturn(TITLE);
		expect(mockBean.getDcPublisher()).andStubReturn(AUTHOR);
		expect(mockBean.getId()).andStubReturn(europeanaObjectId);
		expect(mockBean.getEdmObject()).andStubReturn(THUMBNAIL);
		expect(mockBean.getType()).andStubReturn(DocType.TEXT);
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

}
