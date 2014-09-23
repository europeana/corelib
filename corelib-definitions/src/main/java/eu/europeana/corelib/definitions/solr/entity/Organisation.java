package eu.europeana.corelib.definitions.solr.entity;

import java.util.List;
import java.util.Map;
//TODO: NOT TO BE USED
public interface Organisation extends Agent{
	
	Map<String,List<String>> getEdmAcronym();
	
	void setEdmAcronym(Map<String,List<String>> edmAcronym);
	
	Map<String,String> getEdmOrganisationScope();
	
	void setEdmOrganisationScope(Map<String,String> edmOrganisationScope);
	
	Map<String,String> getEdmOrganisationDomain();
	
	void setEdmOrganisationDomain(Map<String,String> edmOrganisationDomain);
	
	Map<String,String> getEdmOrganisationSector();
	
	void setEdmOrganisationSector(Map<String,String> edmOrganisationSector);
	
	Map<String,String> getEdmGeographicLevel();
	
	void setEdmGeorgraphicLevel(Map<String,String> edmGeographicLevel);
	
	String getEdmCountry();
	
	void setEdmCountry(String edmCountry);
	
	Map<String,List<String>> getEdmEuropeanaRole();
	
	void setEdmEuropeanaRole(Map<String,List<String>> edmEuropeanaRole);
	
	String getFoafHomepage();
	
	void setFoafHomepage(String foafHomePage);
	
	
}
