/**
 * 
 */
package eu.europeana.corelib.utils.converters;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

/**
 * @author Georgios Markakis (gwarkx@hotmail.com)
 *
 */
public class String2MapConverterFactory implements ConverterFactory<String,Map<String,List<String>>>{

	@Override
	public <T extends Map<String, List<String>>> Converter<String, T> getConverter(
			Class<T> arg0) {
		return new String2MapConverter();
	}

	
	/**
     * @author Georgios Markakis (gwarkx@hotmail.com)
     *
     * @param <String>
     */
    private final class String2MapConverter<T> implements Converter<String,Map<String, List<String>>> {
		@Override
		public Map<String, List<String>> convert(String value) {
			
			Map<String, List<String>> map = new HashMap<String, List<String>>();
			ObjectMapper mapper = new ObjectMapper();
			
			try {
				 map = mapper.readValue( value,new TypeReference<HashMap<String, List<String>>>(){});
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return map;
		}
    }
}
