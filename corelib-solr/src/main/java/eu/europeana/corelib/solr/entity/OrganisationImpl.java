package eu.europeana.corelib.solr.entity;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.google.code.morphia.annotations.Entity;

import eu.europeana.corelib.definitions.solr.entity.Organisation;
@JsonSerialize(include = Inclusion.NON_EMPTY)
@Entity("Organization")
//TODO: NOT TO BE USED
public class OrganisationImpl extends AgentImpl implements Organisation {

	private Map<String,List<String>> edmAcronym;
	private Map<String,String> edmOrganisationScope;
	private Map<String,String> edmOrganisationDomain;
	private Map<String,String> edmOrganisationSector;
	private Map<String,String> edmGeographicLevel;
	private String edmCountry;
	private Map<String,List<String>> edmEuropeanaRole;
	private String foafHomepage;	
	private Map<String,List<String>> dcIdentifier;
	@Override

	public Map<String, List<String>> getEdmAcronym() {
		return this.edmAcronym;
	}

	@Override
	public void setEdmAcronym(Map<String, List<String>> edmAcronym) {
		this.edmAcronym = edmAcronym;
	}

	@Override
	public Map<String,String> getEdmOrganisationScope() {
		return this.edmOrganisationScope;
	}

	@Override
	public void setEdmOrganisationScope(Map<String,String> edmOrganisationScope) {
		this.edmOrganisationScope = edmOrganisationScope;
	}

	@Override
	public Map<String,String> getEdmOrganisationDomain() {
		return this.edmOrganisationDomain;
	}

	@Override
	public void setEdmOrganisationDomain(Map<String,String> edmOrganisationDomain) {
		this.edmOrganisationDomain = edmOrganisationDomain;
	}

	@Override
	public Map<String,String> getEdmOrganisationSector() {
		return this.edmOrganisationSector;
	}

	@Override
	public void setEdmOrganisationSector(Map<String,String> edmOrganisationSector) {
		this.edmOrganisationSector= edmOrganisationSector;
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
	public Map<String, List<String>> getDcIdentifier() {
		return this.dcIdentifier;
	}

	@Override
	public void setDcIdentifier(Map<String,List<String>> dcIdentifier) {
		
		this.dcIdentifier= dcIdentifier;
	}

}
