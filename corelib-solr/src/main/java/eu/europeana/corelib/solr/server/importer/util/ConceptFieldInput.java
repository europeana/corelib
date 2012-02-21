package eu.europeana.corelib.solr.server.importer.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.common.SolrInputDocument;

import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.solr.entity.ConceptImpl;
import eu.europeana.corelib.solr.server.MongoDBServer;
import eu.europeana.corelib.definitions.jibx.AltLabel;
import eu.europeana.corelib.definitions.jibx.Broader;
import eu.europeana.corelib.definitions.jibx.Concept;
import eu.europeana.corelib.definitions.jibx.Note;
import eu.europeana.corelib.definitions.jibx.PrefLabel;

public class ConceptFieldInput {

	public static SolrInputDocument createConceptSolrFields(Concept concept, SolrInputDocument solrInputDocument){
		solrInputDocument.addField(EdmLabel.SKOS_CONCEPT.toString(), concept.getAbout());
		for(AltLabel altLabel: concept.getAltLabelList()){
			solrInputDocument.addField(EdmLabel.CC_SKOS_ALT_LABEL.toString()+"."+altLabel.getLang().getLang(), altLabel.getString());
		}
		for(PrefLabel prefLabel: concept.getPrefLabelList()){
			solrInputDocument.addField(EdmLabel.CC_SKOS_PREF_LABEL.toString()+"."+prefLabel.getLang().getLang(), prefLabel.getString());
		}
		for(Broader broader: concept.getBroaderList()){
			solrInputDocument.addField(EdmLabel.CC_SKOS_BROADER.toString(),broader.getBroader());
		}
		for(Note note:concept.getNoteList()){
			solrInputDocument.addField(EdmLabel.CC_SKOS_NOTE.toString(),note.getString());
		}
		return solrInputDocument;
	}

	public static List<? extends eu.europeana.corelib.definitions.solr.entity.Concept> createConceptMongoFields(	Concept concept, MongoDBServer mongoServer) {
		List <eu.europeana.corelib.definitions.solr.entity.Concept> conceptList = new ArrayList<eu.europeana.corelib.definitions.solr.entity.Concept>();
		//If Concept exists in Mongo
		try{
			conceptList.add((eu.europeana.corelib.definitions.solr.entity.Concept) mongoServer.searchByAbout(concept.getAbout()));
		}
		//If it does not exist
		catch(NullPointerException npe){
			eu.europeana.corelib.definitions.solr.entity.Concept conceptMongo = new ConceptImpl();
			conceptMongo.setAbout(concept.getAbout());
			
			List<String> noteList = new ArrayList<String>();
			for(Note note : concept.getNoteList()){
				noteList.add(note.getString());
			}
			conceptMongo.setNote(noteList.toArray(new String[noteList.size()]));
			
			List<String> broaderList = new ArrayList<String>();
			for(Broader broader : concept.getBroaderList()){
				broaderList.add(broader.getBroader());
			}
			conceptMongo.setBroader(broaderList.toArray(new String[broaderList.size()]));
			
			Map<String, String> prefLabelMongo = new HashMap<String, String>();
			for (PrefLabel prefLabelJibx : concept.getPrefLabelList()) {
				prefLabelMongo.put(prefLabelJibx.getLang().getLang(),prefLabelJibx.getString());
			}
			conceptMongo.setPrefLabel(prefLabelMongo);

			Map<String, String> altLabelMongo = new HashMap<String, String>();
			for (AltLabel altLabelJibx : concept.getAltLabelList()) {
				altLabelMongo.put(altLabelJibx.getLang().getLang(),
						altLabelJibx.getString());
			}
			conceptMongo.setAltLabel(altLabelMongo);
			mongoServer.getDatastore().save(conceptMongo);
		}
		return conceptList;
	}
}
