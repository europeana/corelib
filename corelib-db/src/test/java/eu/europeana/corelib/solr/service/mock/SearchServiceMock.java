/*
 * Copyright 2007 EDL FOUNDATION
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they 
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "License");
 * you may not use this work except in compliance with the
 * License.
 * You may obtain a copy of the License at:
 *
 * http://ec.europa.eu/idabc/eupl
 *
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the License is
 * distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the License for the specific language governing
 * permissions and limitations under the License.
 */

package eu.europeana.corelib.solr.service.mock;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import eu.europeana.corelib.definitions.solr.DocType;
import eu.europeana.corelib.solr.bean.FullBean;
import eu.europeana.corelib.solr.bean.IdBean;
import eu.europeana.corelib.solr.model.Query;
import eu.europeana.corelib.solr.model.ResultSet;
import eu.europeana.corelib.solr.service.SearchService;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * 
 * @see eu.europeana.corelib.solr.service.SearchService
 */
public class SearchServiceMock implements SearchService {
	
	public static final String TITLE="Mock Title";
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

}
