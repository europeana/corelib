package eu.europeana.corelib.dereference;

import eu.europeana.corelib.storage.MongoServer;
import eu.europeana.corelib.dereference.impl.ControlledVocabularyImpl;

/**
 * Vocabulary Mapping mongo server
 * 
 * @author Yorgos.Mamakis@ KB.nl
 *
 */
public interface VocabularyMongoServer extends MongoServer {

	/**
	 * Retrieve the mappings for a specific resource. Rules are also taken into
	 * account
	 * 
	 * @param field
	 *            - the field of the controlled vocabulary to search for (the
	 *            uri of the vocabulary is currently only supported)
	 * @param filter
	 *            - the value to match the field
	 * @return
	 */
	ControlledVocabularyImpl getControlledVocabulary(String field, String filter);

	/**
	 * Retrieve the mappings for a specific resource by URI
	 * 
	 * @param uri
	 *            The uri to search on
	 * @param name
	 *            The name to compare with if the URI exists more than once
	 * @return A ControlledVocabulary
	 */
	ControlledVocabularyImpl getControlledVocabularyByUri(String uri,
			String name);

	/**
	 * Retrieve a ControlledVocabulary by name
	 * 
	 * @param name
	 *            The name to search on
	 * @return A ControlledVocabulary
	 */
	ControlledVocabularyImpl getControlledVocabularyByName(String name);

}