package eu.europeana.uim.sugarcrmclient.internal.helpers;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;


/**
 * Property Reader Utility for UIM
 * @author Yorgos.Mamakis@ kb.nl
 *
 */
public final class PropertyReader {
	/**
	 * The UIM property fields
	 */
	static Map<String,String> fields;
	private static final String UIM_PROPERTIES = "uim.properties";
	static {
		try {
			fields = readFile(UIM_PROPERTIES);
		} catch (IOException e) {
			System.out.println("Values not loaded. Call loadPropertiesFromFile(filePath) to specify the filepath");
		}
	}
	
	/**
	 * Get UIM Property
	 * @param property An enumerated configuration property available for this plugin
	 * @return
	 */
	public static String getProperty(UimConfigurationProperty property){
		return fields.get(property.toString());
	}
	
	public void loadPropertiesFromFile(String path){
		try{
			fields = readFile(path);
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Read property file from path. The default behaviour is to search for uim.properties file in the karaf root folder
	 * @param path The path of the file
	 * @return A Map with all the fields/values that are available for this plugin
	 * @throws IOException
	 */
	private static Map<String,String> readFile(String path) throws IOException{
		List<String> lines = FileUtils.readLines(new File(path));
		Map<String,String> fields = new HashMap<String,String>();
		for(String str:lines){
			if(!StringUtils.startsWith(str, "#")&&str.length()>0){
				String[] vals = StringUtils.split(str,"=");
				fields.put(vals[0].trim(),vals.length>1?vals[1].trim():"");
			}
		}
		return fields;
	}
}
