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
 * EDM Concept fields representation
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
public interface Concept extends ContextualClass {

	/**
	 * Retrieves the broader terms of a concept
	 * 
	 * @return A String array with all the broader terms of a concept
	 */
	String[] getBroader();

	/**
	 * Set the broader terms for a concept
	 * 
	 * @param broader
	 *            A string array with all the broader terms of a concept
	 */
	void setBroader(String[] broader);
	
	Map<String,String> getHiddenLabel();
	
	void setHiddenLabel(Map<String,String> hiddenLabel);
	
	String[] getNarrower();
	
	void setNarrower(String[] narrower);
	
	String[] getRelated();
	
	void setRelated(String[] related);
	
	String[] getBroadMatch();
	
	void setBroadMatch(String[] broadMatch);
	
	String[] getNarrowMatch();
	
	void setNarrowMatch(String[] narrowMatch);
	
	String[] getRelatedMatch();
	
	void setRelatedMatch(String[] relatedMatch);
	
	String[] getExactMatch();
	
	void setExactMatch(String[] exactMatch);
	
	String[] getCloseMatch();
	
	void setCloseMatch(String[] closeMatch);
	
	String[] getNotation();
	
	void setNotation(String[] notation);
	
	String[] getInScheme();
	
	void setInScheme(String[] inScheme);
}
