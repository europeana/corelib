package eu.europeana.corelib.neo4j.server.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import eu.europeana.corelib.neo4j.entity.CustomNode;

/**
 * JSON Deserializer for the CustomNode class
 * @author yorgos.mamakis@ europeana.eu
 *
 */
public class CustomNodeDeserializer extends JsonDeserializer<CustomNode> {

	
	@Override
	public CustomNode deserialize(JsonParser jp, DeserializationContext arg1)
			throws IOException {
		CustomNode node = new CustomNode();
		 JsonNode json = jp.getCodec().readTree(jp);
		 Iterator<String> iterator = json.fieldNames();
		 Map<String,Object> properties = new HashMap<>();
		 while(iterator.hasNext()){
			 String iteratorKey = iterator.next();
			 properties.put(iteratorKey, json.get(iteratorKey));
		 }
		 node.setProperties(properties);
		return node;
	}

	
	

}
