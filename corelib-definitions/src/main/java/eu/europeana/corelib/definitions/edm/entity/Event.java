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
package eu.europeana.corelib.definitions.edm.entity;

/**
 * Interface for the edm:Event - Not yet implemented
 * @author Yorgos.Mamakis@ kb.nl
 *
 */
public interface Event extends ContextualClass {
	/**
	 * edm:happenedAt
	 * @param edmHappenedAt
	 */
	void setEdmHappenedAt(String[] edmHappenedAt);
	
	/**
	 * 
	 * @return
	 */
	String[] getEdmHappenedAt();
	
	/**
	 * edm:occuredAt
	 * @param edmOccuredAt
	 */
	void setEdmOccuredAt(String[] edmOccuredAt);
	
	/**
	 * 
	 * @return
	 */
	String[] getEdmOccuredAt();
	
	/**
	 * owl:sameAs
	 * @return
	 */
	String[] getSameAs();
	
	/**
	 * 
	 * @param sameAs
	 */
	void setSameAs(String[] sameAs);
	
	/**
	 * dc:identifier
	 * @return
	 */
	String[] getDcIdentifier();
	
	/**
	 * 
	 * @param dcIdentifier
	 */
	void setDcIdentifier(String[] dcIdentifier);
	
	/**
	 * dcterms:hasPart
	 * @return
	 */
	String[] getDctermsHasPart();
	
	/**
	 * 
	 * @param dctermsHasPart
	 */
	void setDctermsHasPart(String[] dctermsHasPart);
	
	/**
	 * dcterms:isPartOf
	 * @return
	 */
	String[] getDctermsIsPartOf();
	
	/**
	 * 
	 * @param dctermsIsPartOf
	 */
	void setDctermsIsPartOf(String[] dctermsIsPartOf);
	
	/**
	 * crmp120f:occursBefore
	 * @return
	 */
	String[] getCrmP120FOccursBefore();
	
	/**
	 * 
	 * @param crmP120FOccursBefore
	 */
	void setCrmP120FOccursBefore(String[] crmP120FOccursBefore);
	
	/**
	 * edm:hasType
	 * @return
	 */
	String[] getEdmHasType();
	
	/**
	 * 
	 * @param edmHasType
	 */
	void setEdmHasType(String[] edmHasType);
	
	/**
	 * edm:isRelatedTo
	 * @return
	 */
	String[] getEdmIsRelatedTo();
	
	/**
	 * 
	 * @param edmIsRelatedTo
	 */
	void setEdmIsRelatedTo(String[] edmIsRelatedTo);
}
