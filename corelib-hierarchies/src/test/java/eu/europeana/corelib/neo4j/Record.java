package eu.europeana.corelib.neo4j;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Record representation for loading in unit tests
 * @author Yorgos.Mamakis@ europeana.eu
 * @see src/test/resources/records.json
 */
@JsonSerialize
@JsonDeserialize
public class Record {

	@JsonProperty(value="edm_type")
	private String edmType;
	
	@JsonProperty(value="rdf_about")
	private String rdfAbout;
	
	@JsonProperty(value="hasChildren")
	private Boolean hasChildren;
	
	@JsonProperty(value="dc_description_xml_lang_en")
	private String dcDescription;
	
	@JsonProperty(value="dc_title_xml_lang_en")
	private String dcTitle;
	
	@JsonProperty(value="hasParent")
	private String hasParent;
	
	@JsonProperty(value="edm_isNextInSequence")
	private String edmIsNextInSequence;
	
	@JsonProperty(value="edm_isLastInSequence")
	private String edmIsLastInSequence;
	
	@JsonProperty(value="edm_isFirstInSequence")
	private String edmIsFirstInSequence;
	
	@JsonProperty(value="dcterms_isPartOf")
	private String dcterms_isPartOf;
	
	@JsonProperty(value="dcterms_hasPart")
	private String[] dcterms_hasPart;

	public String getEdmType() {
		return edmType;
	}

	public void setEdmType(String edmType) {
		this.edmType = edmType;
	}

	public String getRdfAbout() {
		return rdfAbout;
	}

	public void setRdfAbout(String rdfAbout) {
		this.rdfAbout = rdfAbout;
	}

	public Boolean getHasChildren() {
		return hasChildren;
	}

	public void setHasChildren(Boolean hasChildren) {
		this.hasChildren = hasChildren;
	}

	public String getDcDescription() {
		return dcDescription;
	}

	public void setDcDescription(String dcDescription) {
		this.dcDescription = dcDescription;
	}

	public String getDcTitle() {
		return dcTitle;
	}

	public void setDcTitle(String dcTitle) {
		this.dcTitle = dcTitle;
	}

	public String getHasParent() {
		return hasParent;
	}

	public void setHasParent(String hasParent) {
		this.hasParent = hasParent;
	}

	public String getEdmIsNextInSequence() {
		return edmIsNextInSequence;
	}

	public void setEdmIsNextInSequence(String edmIsNextInSequence) {
		this.edmIsNextInSequence = edmIsNextInSequence;
	}

	public String getEdmIsLastInSequence() {
		return edmIsLastInSequence;
	}

	public void setEdmIsLastInSequence(String edmIsLastInSequence) {
		this.edmIsLastInSequence = edmIsLastInSequence;
	}

	public String getEdmIsFirstInSequence() {
		return edmIsFirstInSequence;
	}

	public void setEdmIsFirstInSequence(String edmIsFirstInSequence) {
		this.edmIsFirstInSequence = edmIsFirstInSequence;
	}

	public String getDcterms_isPartOf() {
		return dcterms_isPartOf;
	}

	public void setDcterms_isPartOf(String dcterms_isPartOf) {
		this.dcterms_isPartOf = dcterms_isPartOf;
	}

	public String[] getDcterms_hasPart() {
		return dcterms_hasPart;
	}

	public void setDcterms_hasPart(String[] dcterms_hasPart) {
		this.dcterms_hasPart = dcterms_hasPart;
	}
	
	
	
}
