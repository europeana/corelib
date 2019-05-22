package eu.europeana.corelib.definitions.edm.entity;

import java.util.List;
import java.util.Map;

/**
 * EDM Agent fields representation
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
public interface Agent extends ContextualClass {

	/**
	 * Retrieves the edm:begin field of an edm:Agent
	 * 
	 * @return Date representing the birth of an agent
	 */
	Map<String, List<String>> getBegin();

	/**
	 * Retrieves the edm:end for an edm:Agent
	 * 
	 * @return Date representing the death of an agent
	 */
	Map<String,List<String>> getEnd();

	/**
	 * Set the edm:begin field for an edm:Agent
	 * 
	 * @param begin
	 *            String representing a valid date
	 */
	void setBegin(Map<String,List<String>> begin);

	/**
	 * Set the edm:end field for an edm:Agent
	 * 
	 * @param end
	 *            String representing a valid date
	 */
	void setEnd(Map<String,List<String>> end);
	
	/**
	 * Sets the edm:wasPresentAt field for an edm:Agent
	 * @param edmWasPresentAt
	 */
	void setEdmWasPresentAt(String[] edmWasPresentAt);
	
	/**
	 * 
	 * @return the edm:wasPresentAt for an edm:Agent
	 */
	String[] getEdmWasPresentAt();
	
	/**
	 * Sets the edm:hasMet for an edm:Agent
	 * @param edmHasMet
	 */
	void setEdmHasMet(Map<String,List<String>> edmHasMet);
	
	/**
	 * 
	 * @return the edm:hasMet for an edm:Agent
	 */
	Map<String,List<String>> getEdmHasMet();
	
	/**
	 * Sets the edm:isRelatedTo for an edm:Agent
	 * @param edmIsRelatedTo
	 */
	void setEdmIsRelatedTo(Map<String,List<String>> edmIsRelatedTo);
	
	/**
	 * 
	 * @return the edm:isRelatedTo for an edm:Agent
	 */
	Map<String,List<String>> getEdmIsRelatedTo();
	
	/**
	 * Sets the owl:sameAs for an edm:Agent
	 * @param owlSameAs
	 */
	void setOwlSameAs(String[] owlSameAs);
	
	/**
	 * 
	 * @return the owl:sameAs for an edm:Agent
	 */
	String[] getOwlSameAs();
	
	/**
	 * Sets the foaf:name for an edm:Agent
	 * @param foafName
	 */
	void setFoafName(Map<String,List<String>> foafName);
	
	/**
	 * 
	 * @return the foaf:name for an edm:Agent
	 */
	Map<String,List<String>> getFoafName();
	
	/**
	 * Sets the dc:date for an edm:Agent
	 * @param dcDate
	 */
	void setDcDate(Map<String,List<String>> dcDate);
	
	/**
	 * 
	 * @return the dc:date for an edm:Agent
	 */
	Map<String,List<String>> getDcDate();
	
	/**
	 * Sets the dc:identifier for an edm:Agent
	 * @param dcIdentifier
	 */
	void setDcIdentifier(Map<String,List<String>> dcIdentifier);
	
	/**
	 * 
	 * @return the dc:identifier for an edm:Agent
	 */
	Map<String,List<String>> getDcIdentifier();
	
	/**
	 * Sets the rdaGr2:dateOfBirth for an edm:Agent
	 * @param rdaGr2DateOfBirth
	 */
	void setRdaGr2DateOfBirth(Map<String,List<String>> rdaGr2DateOfBirth);
	
	/**
	 * 
	 * @return the rdaGr2:dateOfBirth for an edm:Agent
	 */
	Map<String,List<String>> getRdaGr2DateOfBirth();
	
	/**
	 * Sets the rdaGr2:dateOfDeath for an edm:Agent
	 * @param rdaGr2DateOfDeath
	 */
	void setRdaGr2PlaceOfDeath(Map<String,List<String>> rdaGr2PlaceOfDeath);
	
	/**
	 * 
	 * @return the rdaGr2:dateOfDeath for an edm:Agent
	 */
	Map<String,List<String>> getRdaGr2PlaceOfDeath();

	/**
	 * Sets the rdaGr2:dateOfBirth for an edm:Agent
	 * @param rdaGr2DateOfBirth
	 */
	void setRdaGr2PlaceOfBirth(Map<String,List<String>> rdaGr2PlaceOfBirth);
	
	/**
	 * 
	 * @return the rdaGr2:dateOfBirth for an edm:Agent
	 */
	Map<String,List<String>> getRdaGr2PlaceOfBirth();
	
	/**
	 * Sets the rdaGr2:dateOfDeath for an edm:Agent
	 * @param rdaGr2DateOfDeath
	 */
	void setRdaGr2DateOfDeath(Map<String,List<String>> rdaGr2DateOfDeath);
	
	/**
	 * 
	 * @return the rdaGr2:dateOfDeath for an edm:Agent
	 */
	Map<String,List<String>> getRdaGr2DateOfDeath();
	
	/**
	 * sets the rdaGr2:dateOfEstablishment for an edm:Agent
	 * @param rdaGr2DateOfEstablishment
	 */
	void setRdaGr2DateOfEstablishment(Map<String,List<String>> rdaGr2DateOfEstablishment);
	
	/**
	 * 
	 * @return the rdaGr2:datefEstablishment for an edm:Agent
	 */
	Map<String,List<String>> getRdaGr2DateOfEstablishment();
	
	/**
	 * Sets the rdaGr2:dateOfTermination for an edm:Agent
	 * @param rdaGr2DateOfTermination
	 */
	void setRdaGr2DateOfTermination(Map<String,List<String>> rdaGr2DateOfTermination);
	
	/**
	 * 
	 * @return the rdaGr2:dateOfTermination for an edm:Agent
	 */
	Map<String,List<String>> getRdaGr2DateOfTermination();
	
	/**
	 * Sets the rdaGr2:gender for an edm:Agent
	 * @param rdaGr2Gender
	 */
	void setRdaGr2Gender(Map<String,List<String>> rdaGr2Gender);
	
	/**
	 * 
	 * @return the rdaGr2:gender for an edm:Agent
	 */
	Map<String,List<String>> getRdaGr2Gender();
	
	/**
	 * Sets the rdaGr2:professionOrOccupation for an edm:Agent
	 * @param rdaGr2ProfessionOrOccupation
	 */
	void setRdaGr2ProfessionOrOccupation(Map<String,List<String>> rdaGr2ProfessionOrOccupation);
	
	/**
	 * 
	 * @return the rdaGr2:professionOrOccupation for an edm:Agent
	 */
	Map<String,List<String>> getRdaGr2ProfessionOrOccupation();
	
	/**
	 * Sets the rdaGr2:biographicalInformation for an edm:Agent
	 * @param rdaGr2BiographicalInformation
	 */
	void setRdaGr2BiographicalInformation(Map<String,List<String>> rdaGr2BiographicalInformation);
	
	/**
	 * 
	 * @return the rdaGr2:biographicalInformation for an edm:Agent
	 */
	Map<String,List<String>> getRdaGr2BiographicalInformation();
	
}
