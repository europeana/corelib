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
package eu.europeana.corelib.neo4j.entity;

import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

import eu.europeana.corelib.neo4j.server.impl.CustomNodeDeserializer;
/**
 * Class that represents an embedded neo4j node in the Hierarchy class
 * @author Yorgos.Mamakis@ europeana.eu
 *
 */
@JsonDeserialize(using=CustomNodeDeserializer.class)
public class CustomNode{

	
	private Map<String,Object> properties;
	
	/**
	 * Check if the map representing Node properties has a specific key
	 * @param key The key to search on
	 * @return true if the key exists
	 */
	public boolean hasProperty(String key){
		return properties.containsKey(key);
	}
	
	/**
	 * Retrieve a specific key from the node properties
	 * @param key The key to retrieve
	 * @return The value for the specified key
	 */
	public Object getProperty(String key){
		return properties.get(key);
	}

	/**
	 * Get all keys
	 * @return The keyset of the node properties
	 */
	public Set<String> getPropertyKeys(){
		return properties.keySet();
	}
	
	public Map<String, Object> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}
	
	
	
}
