package eu.europeana.corelib.solr.utils.converters;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.util.ReflectionUtils.FieldCallback;

import eu.europeana.corelib.definitions.solr.entity.AbstractEdmEntity;

public class EDMEntity2MapConverterFactory implements ConverterFactory<AbstractEdmEntity,Map<String,String>> {

	@Override
	public <T extends Map<String,String>> Converter<AbstractEdmEntity, T> getConverter(
			Class<T> arg0) {
		// TODO Auto-generated method stub
		return new AbstractEdmEntityMapConverter();
	}

	
	/**
     * @author Georgios Markakis (gwarkx@hotmail.com)
     *
     * @param <String>
     */
    private final class AbstractEdmEntityMapConverter<T> implements Converter<AbstractEdmEntity,Map<String,String>> {
		@Override
		public Map<String,String> convert(AbstractEdmEntity value) {
			
		   final Map<String, String> map = new HashMap<String, String>();

		   Class<?> clazz = value.getClass();

		   FieldCallback fc = new FieldCallback(){

			@Override
			public void doWith(Field field) throws IllegalArgumentException,
					IllegalAccessException {
				
				org.springframework.util.ReflectionUtils.makeAccessible(field);
				String name =field.getName();
				String value = "";
				
				
				Object obj = new Object();
				field.get(obj);
				
				Class<?> type = field.getType();
				
				if(type.equals(HashMap.class)){
					
				}
				if(type.equals(String.class)){
					value = (String)obj;
				}

				map.put(name,value);
				
			}
			   
		   };

		   org.springframework.util.ReflectionUtils.doWithFields(clazz, fc);   //findField(clazz, "value");
		    
			
			return map;
		}
    }
}
