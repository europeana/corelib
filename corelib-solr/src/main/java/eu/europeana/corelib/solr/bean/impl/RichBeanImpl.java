package eu.europeana.corelib.solr.bean.impl;

import org.apache.solr.client.solrj.beans.Field;

import eu.europeana.corelib.definitions.solr.beans.RichBean;

public class RichBeanImpl extends ApiBeanImpl implements RichBean {

	@Field("proxy_dc_description")
	protected String[] dcDescription;

	@Field("provider_aggregation_edm_isShownBy")
	protected String[] edmIsShownBy;

	@Field("europeana_aggregation_edm_landingPage")
	protected String[] edmLandingPage;

	@Override
	public String[] getDcDescription() {
		return (this.dcDescription != null ? this.dcDescription.clone() : null);
	}

	@Override
	public String[] getEdmIsShownBy() {
		return (this.edmIsShownBy != null ? this.edmIsShownBy.clone() : null);
	}

	public String[] getEdmLandingPage() {
		return (this.edmLandingPage != null ? this.edmLandingPage.clone() : null);
	}
}
