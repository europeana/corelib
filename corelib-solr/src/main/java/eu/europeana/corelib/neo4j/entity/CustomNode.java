package eu.europeana.corelib.neo4j.entity;

import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

import eu.europeana.corelib.solr.server.impl.CustomNodeDeserializer;

@JsonDeserialize(using=CustomNodeDeserializer.class)
public class CustomNode{

	Map<String,Object> properties;
	
	public boolean hasProperty(String key){
		return properties.containsKey(key);
	}
	
	public Object getProperty(String key){
		return properties.get(key);
	}

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
