package eu.europeana.corelib.definitions.edm.entity;

import java.util.List;
import java.util.Map;
//TODO: NOT TO BE USED
public interface Organization extends Agent{
	
	Map<String,List<String>> getEdmAcronym();
	
	void setEdmAcronym(Map<String,List<String>> edmAcronym);
	
	Map<String,String> getEdmOrganizationScope();
	
	void setEdmOrganizationScope(Map<String,String> edmOrganizationScope);
	
	Map<String,String> getEdmOrganizationDomain();
	
	void setEdmOrganizationDomain(Map<String,String> edmOrganizationDomain);
	
	Map<String,String> getEdmOrganizationSector();
	
	void setEdmOrganizationSector(Map<String,String> edmOrganizationSector);
	
	Map<String,String> getEdmGeographicLevel();
	
	void setEdmGeorgraphicLevel(Map<String,String> edmGeographicLevel);
	
	String getEdmCountry();
	
	void setEdmCountry(String edmCountry);
	
	Map<String,List<String>> getEdmEuropeanaRole();
	
	void setEdmEuropeanaRole(Map<String,List<String>> edmEuropeanaRole);
	
	String getFoafHomepage();
	
	void setFoafHomepage(String foafHomePage);

	String getFoafLogo();

	void setFoafLogo(String foafLogo);
	
	
}
