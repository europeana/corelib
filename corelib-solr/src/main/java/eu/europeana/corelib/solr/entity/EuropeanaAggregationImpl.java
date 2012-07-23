package eu.europeana.corelib.solr.entity;

import java.util.List;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;

import eu.europeana.corelib.definitions.solr.entity.EuropeanaAggregation;
import eu.europeana.corelib.definitions.solr.entity.WebResource;
import eu.europeana.corelib.utils.StringArrayUtils;

@Entity("EuropeanaAggregation")
public class EuropeanaAggregationImpl extends AbstractEdmEntityImpl implements EuropeanaAggregation {


	@Embedded
	private List<WebResourceImpl> webResources;

	private String aggregatedCHO;
	private String[] aggregates;
	private String dcCreator;
	private String edmLandingPage;
	private String edmIsShownBy;
	private String[] edmHasView;
	private String edmCountry;
	private String edmLanguage;
	private String edmRights;

	

	@Override
	public String getAggregatedCHO() {
		return this.aggregatedCHO;
	}

	@Override
	public void setAggregatedCHO(String aggregatedCHO) {
		this.aggregatedCHO = aggregatedCHO;
	}

	@Override
	public String[] getAggregates() {
		return (StringArrayUtils.isNotBlank(this.aggregates) ? this.aggregates
				.clone() : null);
	}

	@Override
	public void setAggregates(String[] aggregates) {
		this.aggregates = aggregates;
	}

	@Override
	public String getDcCreator() {
		return this.dcCreator;
	}

	@Override
	public void setDcCreator(String dcCreator) {
		this.dcCreator = dcCreator;
	}

	@Override
	public String getEdmLandingPage() {
		return this.edmLandingPage;
	}

	@Override
	public void setEdmLandingPage(String edmLandingPage) {
		this.edmLandingPage = edmLandingPage;
	}

	@Override
	public String getEdmIsShownBy() {
		return this.edmIsShownBy;
	}

	@Override
	public void setEdmIsShownBy(String edmIsShownBy) {
		this.edmIsShownBy = edmIsShownBy;
	}

	@Override
	public String[] getEdmHasView() {
		return this.edmHasView;
	}

	@Override
	public void setEdmHasView(String[] edmHasView) {
		this.edmHasView = edmHasView;
	}

	@Override
	public String getEdmCountry() {
		return this.edmCountry;
	}

	@Override
	public void setEdmCountry(String edmCountry) {
		this.edmCountry = edmCountry;
	}

	@Override
	public String getEdmLanguage() {
		return this.edmLanguage;
	}

	@Override
	public void setEdmLanguage(String edmLanguage) {
		this.edmLanguage = edmLanguage;
	}

	@Override
	public String getEdmRights() {
		return this.edmRights;
	}

	@Override
	public void setEdmRights(String edmRights) {
		this.edmRights = edmRights;
	}

	@Override
	public List<? extends WebResource> getWebResources() {
		return webResources;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setWebResources(List<? extends WebResource> webResources) {
		this.webResources = (List<WebResourceImpl>) webResources;
	}
}
