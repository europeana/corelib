package eu.europeana.corelib.solr.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import dev.morphia.annotations.Entity;
import eu.europeana.corelib.definitions.edm.entity.Address;
import eu.europeana.corelib.definitions.edm.entity.Organization;
import java.util.List;
import java.util.Map;

@JsonInclude(Include.NON_EMPTY)
@Entity("Organization")
public class OrganizationImpl extends ContextualClassImpl implements Organization {

	private String rdfType;
	private Map<String,List<String>> dcIdentifier;
	private Map<String,String> dcDescription;
	private Map<String,List<String>> edmAcronym;
	
	private String foafLogo;
	private String foafHomepage;	
	private List<String> foafPhone;
	private List<String> foafMbox;
	
	private Map<String,List<String>> edmEuropeanaRole;
	private Map<String,String> edmOrganizationDomain;
	private Map<String,String> edmOrganizationSector;
	private Map<String,String> edmOrganizationScope;
	private Map<String,String> edmGeographicLevel;
	private Map<String,String> edmCountry;
	private String[] owlSameAs;
	private Address address;

	public Map<String, List<String>> getEdmAcronym() {
		return this.edmAcronym;
	}


	public void setEdmAcronym(Map<String, List<String>> edmAcronym) {
		this.edmAcronym = edmAcronym;
	}


	public Map<String,String> getEdmOrganizationScope() {
		return this.edmOrganizationScope;
	}


	public void setEdmOrganizationScope(Map<String,String> edmOrganizationScope) {
		this.edmOrganizationScope = edmOrganizationScope;
	}


	public Map<String, String> getEdmOrganizationDomain() {
		return this.edmOrganizationDomain;
	}


	public void setEdmOrganizationDomain(Map<String, String> edmOrganizationDomain) {
		this.edmOrganizationDomain = edmOrganizationDomain;
	}


	public Map<String, String> getEdmOrganizationSector() {
		return this.edmOrganizationSector;
	}


	public void setEdmOrganizationSector(Map<String, String> edmOrganizationSector) {
		this.edmOrganizationSector= edmOrganizationSector;
	}


	public Map<String, String> getEdmGeographicLevel() {
		return this.edmGeographicLevel;
	}


	public void setEdmGeorgraphicLevel(Map<String, String> edmGeographicLevel) {
		this.edmGeographicLevel = edmGeographicLevel;
	}


	public Map<String, String> getEdmCountry() {
		return this.edmCountry;
	}


	public void setEdmCountry(Map<String, String> edmCountry) {
		this.edmCountry = edmCountry;
	}


	public Map<String,List<String>> getEdmEuropeanaRole() {
		return this.edmEuropeanaRole;
	}


	public void setEdmEuropeanaRole(Map<String,List<String>> edmEuropeanaRole) {
		this.edmEuropeanaRole = edmEuropeanaRole;
	}


	public String getFoafHomepage() {
		return this.foafHomepage;
	}


	public void setFoafHomepage(String foafHomePage) {
		this.foafHomepage = foafHomePage;
	}


	public String getFoafLogo() {
		return this.foafLogo;
	}


	public void setFoafLogo(String foafLogo) {
		this.foafLogo = foafLogo;
	}


	public Map<String, List<String>> getDcIdentifier() {
		return this.dcIdentifier;
	}


	public void setDcIdentifier(Map<String,List<String>> dcIdentifier) {

		this.dcIdentifier= dcIdentifier;
	}


	public String getRdfType() {
		return rdfType;
	}


	public void setRdfType(String rdfType) {
		this.rdfType = rdfType;
	}


	public Map<String, String> getDcDescription() {
		return dcDescription;
	}


	public void setDcDescription(Map<String, String> dcDescription) {
		this.dcDescription = dcDescription;
	}


	public List<String> getFoafPhone() {
		return foafPhone;
	}


	public void setFoafPhone(List<String> foafPhone) {
		this.foafPhone = foafPhone;
	}


	public List<String> getFoafMbox() {
		return foafMbox;
	}


	public void setFoafMbox(List<String> foafMbox) {
		this.foafMbox = foafMbox;
	}

	public String[] getOwlSameAs() {
		return owlSameAs;
	}

	public void setOwlSameAs(String[] owlSameAs) {
		this.owlSameAs = owlSameAs;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
}
