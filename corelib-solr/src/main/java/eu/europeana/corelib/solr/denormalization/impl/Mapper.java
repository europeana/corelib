package eu.europeana.corelib.solr.denormalization.impl;

import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.solr.denormalization.ControlledVocabulary;
import eu.europeana.corelib.solr.server.MongoDBServer;
/**
 * Mapper class for Controlled Vocabularies
 * @author yorgos.mamakis@ kb.nl
 *
 */
public class Mapper {

	@Resource(name = "corelib_solr_mongoServer")
	private MongoDBServer mongoServer;
	
	private ControlledVocabulary controlledVocabulary;
	private Mapper(ControlledVocabulary controlledVocabulary){
		this.controlledVocabulary=controlledVocabulary;
	}
	
	/**
	 * Mapping function between a Europeana Field and an Element of the Controlled Vocabulary
	 * @param field The ControlledVocabulary Field
	 * @param europeanaField The EuropeanaField
	 */
	public void mapField(String field, EdmLabel europeanaField){
		controlledVocabulary.setMappedField(field, europeanaField);
		
	}
	
	/**
	 * Method saving the mapping of the controlled vocabulary
	 */
	public void saveMapping(){
		sanitize(controlledVocabulary);
		mongoServer.getDatastore().save(controlledVocabulary);
		
	}
	
	/**
	 * Sanitizing method that removes unmapped fields (the controlled vocabulary is initialized with full mappings)
	 * @param controlledVocabulary The controlled vocabulary to sanitize
	 */
	private void sanitize(ControlledVocabulary controlledVocabulary){
		Set<Entry<String,EdmLabel>> elements = controlledVocabulary.getElements().entrySet();
		while(elements.iterator().hasNext()){
			Entry<String,EdmLabel> entry = elements.iterator().next();
			if(entry.getValue()==null){
				elements.iterator().remove();
			}
		}
	}
}
