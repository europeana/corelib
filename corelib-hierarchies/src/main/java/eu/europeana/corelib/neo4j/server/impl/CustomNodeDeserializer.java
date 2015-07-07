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
package eu.europeana.corelib.neo4j.server.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

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
		 Iterator<String> iterator = json.getFieldNames();
		 Map<String,Object> properties = new HashMap<>();
		 while(iterator.hasNext()){
			 String iteratorKey = iterator.next();
			 properties.put(iteratorKey, json.get(iteratorKey));
		 }
		 node.setProperties(properties);
		return node;
	}

	
	

}
