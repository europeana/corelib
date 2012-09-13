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

import java.util.List;
import java.util.Map;

import eu.europeana.corelib.definitions.solr.DocType;

/**
 * Provider Proxy fields representation
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
public interface Proxy extends BasicProxy {

	/**
	 * Retrieve the edm:type fields for a Proxy
	 * 
	 * @return DocType representing the edm:type fields for a Proxy
	 */
	DocType getEdmType();

	/**
	 * Set the edmType field for a Proxy
	 * 
	 * @param edmType
	 * 			String array containing the edmType of a Proxy
	 */
	void setEdmType(DocType edmType);

	boolean isEuropeanaProxy();

	void setEuropeanaProxy(boolean europeanaProxy);

	Map<String,List<String>> getYear();

	void setYear(Map<String,List<String>> year);

	
	Map<String,List<String>>getUserTags();

	void setUserTags(Map<String,List<String>> userTags);
}
