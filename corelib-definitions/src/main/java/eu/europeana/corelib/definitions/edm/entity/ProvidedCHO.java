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
 * Provided Cultural Heritage Object
 * @author Yorgos.Mamakis@ kb.nl
 */
public interface ProvidedCHO extends AbstractEdmEntity {

	/**
	 * Retrieve the owl:sameAs fields for a ProvidedCHO
	 * 
	 * @return String array representing the owl:sameAs fields of a ProvidedCHO
	 */
	String[] getOwlSameAs();

	/**
	 * Set the owl:sameAs fields for a ProvidedCHO
	 * 
	 * @param owlSameAs
	 *            String array representing the owl:sameAs fields of a
	 *            ProvidedCHO
	 */
	void setOwlSameAs(String[] owlSameAs);
}
