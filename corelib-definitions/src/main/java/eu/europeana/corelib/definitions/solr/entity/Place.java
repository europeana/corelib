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
 * EDM Place fields representation
 * @author Yorgos.Mamakis@ kb.nl
 *
 */
public interface Place extends ContextualClass{
	
	/**
	 * Retrieves the dcterms:isPartOf fields for a Place
	 * @return A String array representing the dcterms:isPartOf fields for a Place
	 */
	String[] getIsPartOf();
	
	/**
	 * Retrieves the latitude of a Place
	 * @return A float representing the latitude of a Place
	 */
	float getLatitude();
	
	/**
	 * Retrieves the longitude of a Place
	 * @return A float representing the longitude of a Place
	 */
	float getLongitude();

	void setIsPartOf(String[] isPartOf);

	void setLatitude(float latitude);

	void setLongitude(float longitude);
	
}
