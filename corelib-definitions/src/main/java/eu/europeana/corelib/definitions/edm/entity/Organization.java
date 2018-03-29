package eu.europeana.corelib.definitions.edm.entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface Organization extends AbstractEdmEntity{
	
	Map<String, List<String>> getPrefLabel();
	
	void setPrefLabel(Map<String, List<String>> prefLabel);
	
	Map<String,List<String>> getEdmAcronym();
	
	void setEdmAcronym(Map<String,List<String>> edmAcronym);
	
	Map<String, String> getEdmOrganizationScope();
	
	void setEdmOrganizationScope(Map<String, String> edmOrganizationScope);
	
	Map<String, String> getEdmOrganizationDomain();
	
	void setEdmOrganizationDomain(Map<String, String> edmOrganizationDomain);
	
	Map<String, String> getEdmOrganizationSector();
	
	void setEdmOrganizationSector(Map<String, String> edmOrganizationSector);
	
	Map<String, String> getEdmGeographicLevel();
	
	void setEdmGeorgraphicLevel(Map<String, String> edmGeographicLevel);
	
	Map<String, String> getEdmCountry();
	
	void setEdmCountry(Map<String, String> edmCountry);
	
	void setFoafMbox(List<String> foafMbox);

	List<String> getFoafMbox();

	void setFoafPhone(List<String> foafPhone);

	List<String> getFoafPhone();

	void setDcDescription(Map<String, String> dcDescription);

	Map<String, String> getDcDescription();

	void setRdfType(String rdfType);

	String getRdfType();

	void setDcIdentifier(Map<String,List<String>> dcIdentifier);

	Map<String, List<String>> getDcIdentifier();

	void setFoafLogo(String foafLogo);

	String getFoafLogo();

	void setFoafHomepage(String foafHomePage);

	String getFoafHomepage();

	void setEdmEuropeanaRole(Map<String,List<String>> edmEuropeanaRole);

	Map<String, List<String>> getEdmEuropeanaRole();

	void setAddress(Address address);

	Address getAddress();
	
	//TODO: SG - move the setters/getters for OwlSameAs to the parent interface for all entity types
	void setOwlSameAs(String[] owlSameAs);
	
	String[] getOwlSameAs();
	
}