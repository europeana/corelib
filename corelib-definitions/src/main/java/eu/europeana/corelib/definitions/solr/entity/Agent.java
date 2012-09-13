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
	
	void setEdmWasPresentAt(String[] edmWasPresentAt);
	
	String[] getEdmWasPresentAt();
	
	void setEdmHasMet(Map<String,List<String>> edmHasMet);
	
	Map<String,List<String>> getEdmHasMet();
	
	void setEdmIsRelatedTo(Map<String,List<String>> edmIsRelatedTo);
	
	Map<String,List<String>> getEdmIsRelatedTo();
	
	
	void setOwlSameAs(String[] owlSameAs);
	
	String[] getOwlSameAs();
	
	void setFoafName(Map<String,List<String>> foafName);
	
	Map<String,List<String>> getFoafName();
	
	void setDcDate(Map<String,List<String>> dcDate);
	
	Map<String,List<String>> getDcDate();
	
	void setDcIdentifier(Map<String,List<String>> dcIdentifier);
	
	Map<String,List<String>> getDcIdentifier();
	
	void setRdaGr2DateOfBirth(Map<String,List<String>> rdaGr2DateOfBirth);
	
	Map<String,List<String>> getRdaGr2DateOfBirth();
	
	void setRdaGr2DateOfDeath(Map<String,List<String>> rdaGr2DateOfDeath);
	
	Map<String,List<String>> getRdaGr2DateOfDeath();
	
	void setRdaGr2DateOfEstablishment(Map<String,List<String>> rdaGr2DateOfEstablishment);
	
	Map<String,List<String>> getRdaGr2DateOfEstablishment();
	
	void setRdaGr2DateOfTermination(Map<String,List<String>> rdaGr2DateOfTermination);
	
	Map<String,List<String>> getRdaGr2DateOfTermination();
	
	void setRdaGr2Gender(Map<String,List<String>> rdaGr2Gender);
	
	Map<String,List<String>> getRdaGr2Gender();
	
	void setRdaGr2ProfessionOrOccupation(Map<String,List<String>> rdaGr2ProfessionOrOccupation);
	
	Map<String,List<String>> getRdaGr2ProfessionOrOccupation();
	
	void setRdaGr2BiographicalInformation(Map<String,List<String>> rdaGr2BiographicalInformation);
	
	Map<String,List<String>> getRdaGr2BiographicalInformation();
	
}
