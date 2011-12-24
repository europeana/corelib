package eu.europeana.corelib.solr.service.mock;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import eu.europeana.corelib.solr.bean.FullBean;
import eu.europeana.corelib.solr.service.SearchService;

public class SearchServiceMock implements SearchService {
	
	public static final String TITLE="Mock Title";
	public static final String AUTHOR="Mock Author";
	public static final String THUMBNAIL="MockThumbnail.jpg";

	@Override
	public FullBean findById(String europeanaObjectId) {
		FullBean mockBean = createMock(FullBean.class);
		expect(mockBean.getPostTitle()).andStubReturn(TITLE);
		expect(mockBean.getPostAuthor()).andStubReturn(AUTHOR);
		expect(mockBean.getId()).andStubReturn(europeanaObjectId);
		expect(mockBean.getThumbnail()).andStubReturn(THUMBNAIL);
		replay(mockBean);
		return mockBean;
	}

}
