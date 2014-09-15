package eu.europeana.corelib.solr.server.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import eu.europeana.corelib.neo4j.entity.CustomNode;

public class CustomNodeDeserializer extends JsonDeserializer<CustomNode> {

	@Override
	public CustomNode deserialize(JsonParser jp, DeserializationContext arg1)
			throws IOException, JsonProcessingException {
		CustomNode node = new CustomNode();
		 JsonNode json = jp.getCodec().readTree(jp);
		 Iterator<String> iterator = json.getFieldNames();
		 Map<String,Object> properties = new HashMap<String,Object>();
		 while(iterator.hasNext()){
			 String iteratorKey = iterator.next();
			 properties.put(iteratorKey, json.get(iteratorKey));
		 }
		 node.setProperties(properties);
		return node;
	}

	
	

}
