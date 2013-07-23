package eu.europeana.corelib.dereference;

import eu.europeana.corelib.dereference.impl.ControlledVocabularyImpl;
import eu.europeana.corelib.solr.MongoServer;
/**
 * Vocabulary Mapping mongo server
 * @author Yorgos.Mamakis@ KB.nl
 *
 */
public interface VocabularyMongoServer extends MongoServer{

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
	public abstract ControlledVocabularyImpl getControlledVocabulary(
			String field, String filter);

	public abstract ControlledVocabularyImpl getControlledVocabularyByUri(
			String uri, String name);

}