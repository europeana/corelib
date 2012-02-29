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
	String getBegin();

	/**
	 * Retrieves the edm:end for an edm:Agent
	 * 
	 * @return Date representing the death of an agent
	 */
	String getEnd();

	/**
	 * Set the edm:begin field for an edm:Agent
	 * 
	 * @param begin
	 *            String representing a valid date
	 */
	void setBegin(String begin);

	/**
	 * Set the edm:end field for an edm:Agent
	 * 
	 * @param end
	 *            String representing a valid date
	 */
	void setEnd(String end);

}
