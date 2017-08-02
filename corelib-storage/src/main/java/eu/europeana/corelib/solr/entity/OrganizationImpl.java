package eu.europeana.corelib.solr.entity;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import org.mongodb.morphia.annotations.Entity;

import eu.europeana.corelib.definitions.edm.entity.Organization;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@JsonSerialize(include = Inclusion.NON_EMPTY)
@JsonInclude(NON_EMPTY)
@Entity("Organization")
//TODO: NOT TO BE USED
public class OrganizationImpl extends AgentImpl implements Organization {

	private Map<String,List<String>> edmAcronym;
	private Map<String,String> edmOrganizationScope;
	private Map<String,String> edmOrganizationDomain;
	private Map<String,String> edmOrganizationSector;
	private Map<String,String> edmGeographicLevel;
	private String edmCountry;
	private Map<String,List<String>> edmEuropeanaRole;
	private String foafHomepage;	
	private Map<String,List<String>> dcIdentifier;
	private String foafLogo;
	@Override

	public Map<String, List<String>> getEdmAcronym() {
		return this.edmAcronym;
	}

	@Override
	public void setEdmAcronym(Map<String, List<String>> edmAcronym) {
		this.edmAcronym = edmAcronym;
	}

	@Override
	public Map<String,String> getEdmOrganizationScope() {
		return this.edmOrganizationScope;
	}

	@Override
	public void setEdmOrganizationScope(Map<String,String> edmOrganizationScope) {
		this.edmOrganizationScope = edmOrganizationScope;
	}

	@Override
	public Map<String,String> getEdmOrganizationDomain() {
		return this.edmOrganizationDomain;
	}

	@Override
	public void setEdmOrganizationDomain(Map<String,String> edmOrganizationDomain) {
		this.edmOrganizationDomain = edmOrganizationDomain;
	}

	@Override
	public Map<String,String> getEdmOrganizationSector() {
		return this.edmOrganizationSector;
	}

	@Override
	public void setEdmOrganizationSector(Map<String,String> edmOrganizationSector) {
		this.edmOrganizationSector= edmOrganizationSector;
	}

	@Override
	public Map<String,String> getEdmGeographicLevel() {
		return this.edmGeographicLevel;
	}

	@Override
	public void setEdmGeorgraphicLevel(Map<String,String> edmGeographicLevel) {
		this.edmGeographicLevel = edmGeographicLevel;
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
	public Map<String,List<String>> getEdmEuropeanaRole() {
		return this.edmEuropeanaRole;
	}

	@Override
	public void setEdmEuropeanaRole(Map<String,List<String>> edmEuropeanaRole) {
		this.edmEuropeanaRole = edmEuropeanaRole;
	}

	@Override
	public String getFoafHomepage() {
		return this.foafHomepage;
	}

	@Override
	public void setFoafHomepage(String foafHomePage) {
		this.foafHomepage = foafHomePage;
	}

	@Override
	public String getFoafLogo() {
		return this.foafLogo;
	}

	@Override
	public void setFoafLogo(String foafLogo) {
		this.foafLogo = foafLogo;
	}

	@Override
	public Map<String, List<String>> getDcIdentifier() {
		return this.dcIdentifier;
	}

	@Override
	public void setDcIdentifier(Map<String,List<String>> dcIdentifier) {
		
		this.dcIdentifier= dcIdentifier;
	}

}
