package eu.europeana.corelib.solr.server.importer.util;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang.StringUtils;

/**
 * Class for record normalization
 * (retrieve data from a controlled vocabulary rather than storing a URI)
 * TODO:everything
 * @author Yorgos.Mamakis@ kb.nl
 *
 */

public class Normalizer {
	
	/**
	 * Lookup the URI provided in a control vocabulary and return its description 
	 * @param uri - The URI pointing to a specific contextual definition
	 * @return The full representation the URI points to
	 */
	
	public String normalize(String uri){
		if(isURI(uri)){
			
			return null;
		}
		else {
			return uri;
		}
	}
	
	private boolean isURI(String uri){
		
			try {
				new URL(uri);
				return true;
			} catch (MalformedURLException e) {
				return false;
			}
		
	}
}
