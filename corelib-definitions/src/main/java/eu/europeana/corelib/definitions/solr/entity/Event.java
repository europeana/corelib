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

/**
 * Interface for the edm:Event - Not yet implemented
 * @author Yorgos.Mamakis@ kb.nl
 *
 */
public interface Event extends ContextualClass {
	
	void setEdmHappenedAt(String[] edmHappenedAt);
	
	String[] getEdmHappenedAt();
	
	void setEdmOccuredAt(String[] edmOccuredAt);
	
	String[] getEdmOccuredAt();
	
	String[] getSameAs();
	
	void setSameAs(String[] sameAs);
	
	
	String[] getDcIdentifier();
	
	void setDcIdentifier(String[] dcIdentifier);
	
	String[] getDctermsHasPart();
	
	void setDctermsHasPart(String[] dctermsHasPart);
	
	String[] getDctermsIsPartOf();
	
	void setDctermsIsPartOf(String[] dctermsIsPartOf);
	
	String[] getCrmP120FOccursBefore();
	
	void setCrmP120FOccursBefore(String[] crmP120FOccursBefore);
	
	String[] getEdmHasType();
	
	void setEdmHasType(String[] edmHasType);
	
	String[] getEdmIsRelatedTo();
	
	void setEdmIsRelatedTo(String[] edmIsRelatedTo);
}
