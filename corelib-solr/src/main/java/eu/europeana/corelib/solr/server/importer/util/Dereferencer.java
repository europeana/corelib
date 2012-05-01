package eu.europeana.corelib.solr.server.importer.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.Mongo;

import eu.europeana.corelib.solr.denormalization.impl.ControlledVocabularyImpl;
import eu.europeana.corelib.solr.denormalization.impl.Extractor;
import eu.europeana.corelib.solr.denormalization.impl.VocabularyMongoServer;

/**
 * Class for record normalization (retrieve data from a controlled vocabulary
 * rather than storing a URI) TODO:everything
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */

public class Dereferencer {

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
		List<List<String>> values = new ArrayList<List<String>>();
		List<String> originalValue = new ArrayList<String>();
		originalValue.add("original");
		originalValue.add(uri);
		values.add(originalValue);
		VocabularyMongoServer server = new VocabularyMongoServer(new Mongo("localhost", 27017),
				"vocabulary");
		if (isURI(uri)) {
			Extractor extractor = new Extractor(new ControlledVocabularyImpl(),
					server);
			if(extractor.getControlledVocabulary(uri)!=null){
				values.addAll(extractor.denormalize(uri,
						extractor.getControlledVocabulary(uri)));
			}
			
		}
		server.close();
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
}
