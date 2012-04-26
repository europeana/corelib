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

package eu.europeana.corelib.solr.denormalization;

import java.util.Map;

import eu.europeana.corelib.definitions.model.EdmLabel;

/**
 * Generic ControlledVocabulary interfaces. It exposes the necessary methods to
 * create a controlled vocabulary from a File or URL, as well as generic methods
 * to map and retrieve mapped fields.
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
public interface ControlledVocabulary {

	/**
	 * Retrieve the original elements of the controlled vocabulary
	 * 
	 * @return A list of the elements of the controlled vocabulary
	 */
	Map<String, EdmLabel> getElements();


	/**
	 * Get the ControlledVocabulary name (Unique)
	 * 
	 * @return The controlled vocabulary name
	 */
	String getName();


	/**
	 * Set the Controlled Vocabulary location
	 * @param location - The location to set
	 */
	void setLocation(String location);

	/**
	 * Get vocabulary location
	 * @return The Controlled vocabulary
	 */
	String getLocation();

	/**
	 * Set the vocabulary name
	 * @param name - the name of the vocabulary to set
	 */
	void setName(String name);

	/**
	 * Set the URI of the vocabulary TODO: check if needed
	 * @param URI The URI to set
	 */
	void setURI(String URI);

	/**
	 * Retrieve the URI of the controlled vocabulary TODO: Check if needed
	 * @return The URI of the controlled vocabulary
	 */
	String getURI();

	/**
	 * Retrieve the suffix of the controlled vocabulary
	 * @return The suffix of the controlled vocabulary
	 */
	String getSuffix();

	/**
	 * Set the suffix of the controlled vocabulary
	 * @param suffix The suffix of the controlled vocabulary
	 */
	void setSuffix(String suffix);

	void setElements(Map<String, EdmLabel> elements);
}
