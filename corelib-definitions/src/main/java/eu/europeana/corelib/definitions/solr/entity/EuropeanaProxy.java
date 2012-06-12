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
 * Europeana specific proxy
 * @author Yorgos.Mamakis@ kb.nl
 *
 */
public interface EuropeanaProxy extends BasicProxy {

	/**
	 * Setter for the user tags
	 * @param userTag
	 */
	void setUserTag(String[] userTag);
	
	/**
	 * Getter for the user tags
	 * @return The edm:userTag of the specific Europeana proxy.
	 */
	String[] getUserTag();
	
	/**
	 * Setter for the Europeana specific Proxy year
	 * @param year
	 */
	void setEdmYear(String[] year);
	
	/**
	 * Getter of the Europeana specific Proxy Year
	 * @return The edm:year for the specific Europeana Proxy
	 */
	String[] getEdmYear();
}
