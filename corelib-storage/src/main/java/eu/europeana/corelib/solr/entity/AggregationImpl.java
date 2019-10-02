package eu.europeana.corelib.solr.entity;


import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.Reference;
import org.mongodb.morphia.annotations.Transient;
import com.fasterxml.jackson.annotation.JsonInclude;
import eu.europeana.corelib.definitions.edm.beans.FullBean;
import eu.europeana.corelib.definitions.edm.entity.Aggregation;
import eu.europeana.corelib.definitions.edm.entity.WebResource;
import eu.europeana.corelib.utils.StringArrayUtils;

/**
 * @see eu.europeana.corelib.definitions.edm.entity.Aggregation
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
@JsonInclude(Include.NON_EMPTY)
//@NodeEntity(partial = true)
@Entity("Aggregation")
public class AggregationImpl extends AbstractEdmEntityImpl implements Aggregation {

//	@GraphProperty(propertyType = String.class)
	private Map<String,List<String>> edmDataProvider;
	private String edmIsShownBy;
	private String edmIsShownAt;
	private String edmObject;
	
//	@GraphProperty(propertyType = String.class)
	private Map<String,List<String>> edmProvider;
	
//	@GraphProperty(propertyType = String.class)
	private Map<String,List<String>> edmRights;
	
//	@GraphProperty(defaultValue="")
	private String edmUgc;
	
//	@GraphProperty(propertyType = String.class)
	private Map<String,List<String>> dcRights;
	
//	@GraphProperty(defaultValue="")
	private String[] hasView;
	
//	@GraphProperty(defaultValue="")
	private String aggregatedCHO;
	
//	@GraphProperty(defaultValue="")
	private String[] aggregates;
	
//	@GraphProperty(defaultValue="")
	private String[] edmUnstored;

//	@Transient
	@Reference
	@Indexed
	private List<WebResourceImpl> webResources;

//	@GraphProperty
	private Boolean edmPreviewNoDistribute;

	private Map<String,List<String>> edmIntermediateProvider;

	@Transient
	@JsonIgnore
	private FullBean parentBean;

	@Override
	public String getAggregatedCHO() {
		return this.aggregatedCHO;
	}

	@Override
	public void setAggregatedCHO(String aggregatedCHO) {
		this.aggregatedCHO = aggregatedCHO;
	}

	@Override
	public Boolean getEdmPreviewNoDistribute() {
		return this.edmPreviewNoDistribute;
	}

	@Override
	public void setEdmPreviewNoDistribute(Boolean edmPreviewNoDistribute) {
		this.edmPreviewNoDistribute = edmPreviewNoDistribute;
	}

	@Override
	public String getEdmUgc() {
		return this.edmUgc;
	}

	@Override
	public void setEdmUgc(String edmUgc) {
		this.edmUgc = edmUgc;
	}

	
	@Override
	public void setEdmDataProvider(Map<String,List<String>> edmDataProvider) {
		this.edmDataProvider = edmDataProvider;
	}

	@Override
	public void setEdmIsShownBy(String edmIsShownBy) {
		this.edmIsShownBy = edmIsShownBy;
	}

	@Override
	public void setEdmIsShownAt(String edmIsShownAt) {
		this.edmIsShownAt = edmIsShownAt;
	}

	@Override
	public void setEdmObject(String edmObject) {
		this.edmObject = edmObject;
	}

	@Override
	public void setEdmProvider(Map<String,List<String>> edmProvider) {
		this.edmProvider = edmProvider;
	}

	@Override
	public void setEdmRights(Map<String,List<String>> edmRights) {
		this.edmRights = edmRights;
	}

	@Override
	public void setDcRights(Map<String,List<String>> dcRights) {
		this.dcRights = dcRights;
	}

	@Override
	public Map<String,List<String>> getEdmDataProvider() {
		return this.edmDataProvider;
	}

	@Override
	public String getEdmIsShownBy() {
		return this.edmIsShownBy;
	}

	@Override
	public String getEdmIsShownAt() {
		return this.edmIsShownAt;
	}

	@Override
	public String getEdmObject() {
		return this.edmObject;
	}

	@Override
	public Map<String,List<String>> getEdmProvider() {
		return this.edmProvider;
	}

	@Override
	public Map<String,List<String>> getDcRights() {
		return this.dcRights;
	}

	@Override
	public Map<String,List<String>> getEdmRights() {
		return this.edmRights;
	}

	@Override
	public List<? extends WebResource> getWebResources() {
		return this.webResources;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setWebResources(List<? extends WebResource> webResources) {
		this.webResources = (List<WebResourceImpl>) webResources;
	}

	@Override
	public String[] getHasView() {
		return (StringArrayUtils.isNotBlank(this.hasView) ? this.hasView.clone() : null);
	}

	@Override
	public void setHasView(String[] hasView) {
		this.hasView = hasView !=null? hasView.clone():null;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (o.getClass() == this.getClass()) {
			return this.getAbout().equals(((AggregationImpl) o).getAbout());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.about != null ? this.about.hashCode() : this.id.hashCode();
	}

	@Override
	public String[] getAggregates() {
		return (StringArrayUtils.isNotBlank(this.aggregates) ? this.aggregates.clone() : null);
	}

	@Override
	public void setAggregates(String[] aggregates) {
		this.aggregates = aggregates!=null? aggregates.clone():null;
	}

	@Override
	public String[] getEdmUnstored() {
		return (StringArrayUtils.isNotBlank(this.edmUnstored) ? this.edmUnstored.clone() : null);
	}

	@Override
	public void setEdmUnstored(String[] edmUnstored) {
		this.edmUnstored = edmUnstored.clone();
	}

	@Override
	public Map<String, List<String>> getEdmIntermediateProvider() {
		return this.edmIntermediateProvider;
	}

	@Override
	public void setEdmIntermediateProvider(Map<String, List<String>> edmIntermediateProvider) {
		this.edmIntermediateProvider = edmIntermediateProvider;
	}

	/**
	 * Used to maintain a reference to the encapsulating Record which is referred to in the AttributionSnippet
	 * Not made available through the interface bean, used in the Attributionsnippet alone
	 * @return the encapsulating FullBean
	 */
	public FullBean getParentBean(){
		return this.parentBean;
	}
	/**
	 * parentBean is the encapsulating Record which is referred to by the AttributionSnippet
	 * Not made available through the interface bean, used in the Attributionsnippet alone
	 * @param parentBean
	 */
	public void setParentBean(FullBean parentBean){
		this.parentBean = parentBean;
		for (WebResourceImpl webRes : this.webResources){
			webRes.setParentAggregation(this);
		}
	}
}
