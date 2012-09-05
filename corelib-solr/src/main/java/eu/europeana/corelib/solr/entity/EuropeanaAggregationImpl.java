package eu.europeana.corelib.solr.entity;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;

import eu.europeana.corelib.definitions.solr.entity.EuropeanaAggregation;
import eu.europeana.corelib.definitions.solr.entity.WebResource;
import eu.europeana.corelib.utils.StringArrayUtils;

@JsonSerialize(include = Inclusion.NON_EMPTY)
@Entity("EuropeanaAggregation")
public class EuropeanaAggregationImpl extends AbstractEdmEntityImpl implements EuropeanaAggregation {

	
	

	@Embedded
	private List<WebResourceImpl> webResources;

	private String aggregatedCHO;
	private String[] aggregates;
	private Map<String,String> dcCreator;
	private String edmLandingPage;
	private String edmIsShownBy;
	private String[] edmHasView;
	private Map<String,String> edmCountry;
	private Map<String,String> edmLanguage;
	private Map<String,String> edmRights;

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
		return (StringArrayUtils.isNotBlank(this.aggregates) ? this.aggregates.clone() : null);
	}

	@Override
	public void setAggregates(String[] aggregates) {
		this.aggregates = aggregates;
	}

	@Override
	public Map<String,String> getDcCreator() {
		return this.dcCreator;
	}

	@Override
	public void setDcCreator(Map<String,String> dcCreator) {
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
	public Map<String,String> getEdmCountry() {
		return this.edmCountry;
	}

	@Override
	public void setEdmCountry(Map<String,String> edmCountry) {
		this.edmCountry = edmCountry;
	}

	@Override
	public Map<String,String> getEdmLanguage() {
		return this.edmLanguage;
	}

	@Override
	public void setEdmLanguage(Map<String,String> edmLanguage) {
		this.edmLanguage = edmLanguage;
	}

	@Override
	public Map<String,String> getEdmRights() {
		return this.edmRights;
	}

	@Override
	public void setEdmRights(Map<String,String> edmRights) {
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
