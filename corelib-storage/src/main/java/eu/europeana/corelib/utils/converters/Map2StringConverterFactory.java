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
package eu.europeana.corelib.utils.converters;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;


/**
 * @author Georgios Markakis (gwarkx@hotmail.com)
 *
 * Mar 11, 2014
 */
public class Map2StringConverterFactory implements ConverterFactory<Map<String,List<String>>, String>{



	/* (non-Javadoc)
	 * @see org.springframework.core.convert.converter.ConverterFactory#getConverter(java.lang.Class)
	 */
	@Override
	public <T extends String> Converter<Map<String, List<String>>, T> getConverter(
			Class<T> arg0) {

		return new Map2StringConverter();
	}
	
	
    /**
     * @author Georgios Markakis (gwarkx@hotmail.com)
     *
     * @param <String>
     */
    private final class Map2StringConverter<T> implements Converter<Map<String, List<String>>, String> {
		@Override
		public String convert(Map<String, List<String>> value) {
			
			ObjectMapper mapper = new ObjectMapper();
			
			String json = null;
			try {
				json = mapper.writeValueAsString(value);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return json;
		}
    }




	

}
