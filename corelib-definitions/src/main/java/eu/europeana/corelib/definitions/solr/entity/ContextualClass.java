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
 * Interface representing contextual classes in EDM (Agent, Place, Timespan, Concept)
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
public interface ContextualClass extends AbstractEdmEntity {

	/**
	 * Retrieves the Preferable Label for a Contextual Class (language,value) format
	 * 
	 * @return A multidimensional String array for the Preferable Labels of a contextual class (one per language)
	 */
	Map<String, String> getPrefLabel();

	/**
	 * Retrieves the Alternative Label for a Contextual Class (language,value) format
	 * 
	 * @return A multidimensional String array for the Alternative Labels of a contextual class (one per language)
	 */
	Map<String, String> getAltLabel();

	/**
	 * Retrieves the skos:note fields of a Contextual Class
	 * 
	 * @return A string array with notes for the Contextual Class
	 */
	String[] getNote();

	void setAltLabel(Map<String, String> altLabel);

	void setNote(String[] note);

	void setPrefLabel(Map<String, String> prefLabel);

	String getAbout();

	void setAbout(String about);


}
