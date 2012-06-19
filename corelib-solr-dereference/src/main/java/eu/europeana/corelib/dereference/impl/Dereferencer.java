package eu.europeana.corelib.dereference.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;

import eu.europeana.corelib.tools.AppContext;

/**
 * Class for record normalization (retrieve data from a controlled vocabulary
 * rather than storing a URI)
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */

public class Dereferencer {

	private static VocabularyMongoServer server;

	
	/**
	 * Lookup the URI provided in a control vocabulary and return its
	 * description
	 * 
	 * @param uri
	 *            - The URI pointing to a specific contextual definition
	 * @return The full representation the URI points to
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	public static List<List<String>> normalize(String uri)
			throws MalformedURLException, IOException {
		if (server == null) {
			ApplicationContext applicationContext = AppContext
					.getApplicationContext();
			server = (VocabularyMongoServer) applicationContext
					.getBean("corelib_solr_vocabularyMongoServer");
		}
		List<List<String>> values = new ArrayList<List<String>>();
		List<String> originalValue = new ArrayList<String>();
		originalValue.add("original");
		originalValue.add(uri);
		values.add(originalValue);
		if (isURI(uri)) {
			Extractor extractor = new Extractor(new ControlledVocabularyImpl(),
					server);
			if (extractor.getControlledVocabulary("URI", uri) != null) {
				values.addAll(extractor.denormalize(uri,
						extractor.getControlledVocabulary("URI", uri)));
			}

		}
		return values;
	}

	/**
	 * Check if the supplied string is a URI
	 * 
	 * @param uri
	 *            - The string to be checked
	 * @return <code>true</code> if it is URI, <code>false</code> else
	 */

	private static boolean isURI(String uri) {

		try {
			new URL(uri);
			return true;
		} catch (MalformedURLException e) {
			return false;
		}

	}
	
	public static void setServer(VocabularyMongoServer server){
		Dereferencer.server = server;
	}
}
