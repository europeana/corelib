package eu.europeana.corelib.solr.entity;

import com.google.code.morphia.annotations.Embedded;

import eu.europeana.corelib.definitions.solr.entity.EuropeanaProxy;
import eu.europeana.corelib.utils.StringArrayUtils;

@Embedded
public class EuropeanaProxyImpl extends ProxyImpl implements EuropeanaProxy {

	private String[] userTag;
	private String[] edmYear;
	@Override
	public void setUserTag(String[] userTag) {
		this.userTag = userTag;
	}

	@Override
	public String[] getUserTag() {
		return (StringArrayUtils.isNotBlank(userTag)?this.userTag.clone():null);
	}

	@Override
	public void setEdmYear(String[] year) {
		this.edmYear = year;
	}

	@Override
	public String[] getEdmYear() {
		return (StringArrayUtils.isNotBlank(edmYear)?this.edmYear.clone():null);
	}

}
