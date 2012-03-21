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
	 * Get the Europeana field that has been mapped to the specific controlled
	 * vocabulary field
	 * 
	 * @param field
	 *            The controlled vocabulary field
	 * @return The mapped EdmLabel
	 */
	EdmLabel getEdmLabel(String field);

	/**
	 * Set the mapping between a Europeana Field and a Controlled Vocabulary
	 * Field
	 * 
	 * @param field
	 *            The original controlled vocabulary field
	 * @param fieldToMap
	 *            The Europeanafield
	 */
	void setMappedField(String fieldToMap, EdmLabel europeanaField);

	/**
	 * Get the original field from the mapped europeana field
	 * 
	 * @param europeanaField
	 *            The europeana field to search for
	 * @return The original field
	 */
	String getMappedField(EdmLabel europeanaField);

	/**
	 * Return if a field is mapped
	 * 
	 * @param field
	 *            The name of the field
	 * @return true if the field has been mapped, false else
	 */
	boolean isMapped(String field);

	/**
	 * Get the ControlledVocabulary name (Unique)
	 * 
	 * @return The controlled vocabulary name
	 */
	String getName();

	/**
	 * Create a controlled vocabulary from a schema
	 * @param location The location of the vocabulary (a local file or URL string)
	 * @return An empty map whose keys are the original fields
	 */
	Map<String, EdmLabel> readSchema(String remoteLocation, String localLocation);

	void setLocation(String location);

	String getLocation();
}
