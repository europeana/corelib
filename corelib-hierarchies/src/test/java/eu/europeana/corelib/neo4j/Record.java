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
package eu.europeana.corelib.neo4j;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

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
