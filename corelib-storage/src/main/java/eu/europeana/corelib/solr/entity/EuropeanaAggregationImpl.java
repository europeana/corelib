package eu.europeana.corelib.solr.entity;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

import eu.europeana.corelib.definitions.edm.entity.EuropeanaAggregation;
import eu.europeana.corelib.definitions.edm.entity.WebResource;
import eu.europeana.corelib.utils.StringArrayUtils;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@JsonSerialize(include = Inclusion.NON_EMPTY)
@JsonInclude(NON_EMPTY)
@Entity("EuropeanaAggregation")
public class EuropeanaAggregationImpl extends AbstractEdmEntityImpl implements EuropeanaAggregation {

	@Reference
	private List<WebResource> webResources;

	private String aggregatedCHO;
	private String[] aggregates;
	private Map<String,List<String>> dcCreator;
	private String edmLandingPage;
	private String edmIsShownBy;
	private String[] edmHasView;
	private Map<String,List<String>> edmCountry;
	private Map<String,List<String>> edmLanguage;
	private Map<String,List<String>> edmRights;
	private String edmPreview ="";

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
		this.aggregates = aggregates!=null?aggregates.clone():null;
	}

	@Override
	public Map<String,List<String>> getDcCreator() {
		return this.dcCreator;
	}

	@Override
	public void setDcCreator(Map<String,List<String>> dcCreator) {
		this.dcCreator = dcCreator;
	}

	@Override
	public String getEdmLandingPage() {

		String tempUrl = StringUtils.substringAfter(this.aggregatedCHO, "/item/");
		if(tempUrl==null){
			tempUrl = StringUtils.substringAfter(this.about, "/aggregation/europeana/");
		}
            String finalUrl = "http://europeana.eu/portal/record/"+tempUrl+".html";
		return finalUrl;
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
		this.edmHasView = edmHasView!=null?edmHasView.clone():null;
	}

	@Override
	public Map<String,List<String>> getEdmCountry() {
		return this.edmCountry;
	}

	@Override
	public void setEdmCountry(Map<String,List<String>> edmCountry) {
		this.edmCountry = edmCountry;
	}

	@Override
	public Map<String,List<String>> getEdmLanguage() {
		return this.edmLanguage;
	}

	@Override
	public void setEdmLanguage(Map<String,List<String>> edmLanguage) {
		this.edmLanguage = edmLanguage;
	}

	@Override
	public Map<String,List<String>> getEdmRights() {
		return this.edmRights;
	}

	@Override
	public void setEdmRights(Map<String,List<String>> edmRights) {
		this.edmRights = edmRights;
	}

	@Override
	public List<? extends WebResource> getWebResources() {
		return webResources;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setWebResources(List<? extends WebResource> webResources) {
		this.webResources = (List<WebResource>) webResources;
	}
	
	@Override
	public String getEdmPreview(){
		
		return this.edmPreview;
	}
	
	@Override
	public void setEdmPreview(String edmPreview){
		this.edmPreview = edmPreview;
	}
}
