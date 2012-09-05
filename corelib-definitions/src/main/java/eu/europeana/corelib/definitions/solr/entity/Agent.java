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

package eu.europeana.corelib.definitions.solr.entity;

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
	Map<String,String> getBegin();

	/**
	 * Retrieves the edm:end for an edm:Agent
	 * 
	 * @return Date representing the death of an agent
	 */
	Map<String,String> getEnd();

	/**
	 * Set the edm:begin field for an edm:Agent
	 * 
	 * @param begin
	 *            String representing a valid date
	 */
	void setBegin(Map<String,String> begin);

	/**
	 * Set the edm:end field for an edm:Agent
	 * 
	 * @param end
	 *            String representing a valid date
	 */
	void setEnd(Map<String,String> end);
	
	void setEdmWasPresentAt(String[] edmWasPresentAt);
	
	String[] getEdmWasPresentAt();
	
	void setEdmHasMet(Map<String,String> edmHasMet);
	
	Map<String,String> getEdmHasMet();
	
	void setEdmIsRelatedTo(Map<String,String> edmIsRelatedTo);
	
	Map<String,String> getEdmIsRelatedTo();
	
	
	void setOwlSameAs(String[] owlSameAs);
	
	String[] getOwlSameAs();
	
	void setFoafName(Map<String,String> foafName);
	
	Map<String,String> getFoafName();
	
	void setDcDate(Map<String,String> dcDate);
	
	Map<String,String> getDcDate();
	
	void setDcIdentifier(Map<String,String> dcIdentifier);
	
	Map<String,String> getDcIdentifier();
	
	void setRdaGr2DateOfBirth(Map<String,String> rdaGr2DateOfBirth);
	
	Map<String,String> getRdaGr2DateOfBirth();
	
	void setRdaGr2DateOfDeath(Map<String,String> rdaGr2DateOfDeath);
	
	Map<String,String> getRdaGr2DateOfDeath();
	
	void setRdaGr2DateOfEstablishment(Map<String,String> rdaGr2DateOfEstablishment);
	
	Map<String,String> getRdaGr2DateOfEstablishment();
	
	void setRdaGr2DateOfTermination(Map<String,String> rdaGr2DateOfTermination);
	
	Map<String,String> getRdaGr2DateOfTermination();
	
	void setRdaGr2Gender(Map<String,String> rdaGr2Gender);
	
	Map<String,String> getRdaGr2Gender();
	
	void setRdaGr2ProfessionOrOccupation(Map<String,String> rdaGr2ProfessionOrOccupation);
	
	Map<String,String> getRdaGr2ProfessionOrOccupation();
	
	void setRdaGr2BiographicalInformation(Map<String,String> rdaGr2BiographicalInformation);
	
	Map<String,String> getRdaGr2BiographicalInformation();
	
}
