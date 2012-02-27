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

	public static SolrInputDocument createConceptSolrFields(Concept concept,
			SolrInputDocument solrInputDocument) {
		solrInputDocument.addField(EdmLabel.SKOS_CONCEPT.toString(),
				concept.getAbout());
		if (concept.getAltLabelList() != null) {
			for (AltLabel altLabel : concept.getAltLabelList()) {
				try{
				solrInputDocument.addField(
						EdmLabel.CC_SKOS_ALT_LABEL.toString() + "."
								+ altLabel.getLang().getLang(),
						altLabel.getString());
				}
				catch(NullPointerException e){
					solrInputDocument.addField(EdmLabel.CC_SKOS_ALT_LABEL.toString(),
					altLabel.getString());
				}
			}
		}
		if (concept.getPrefLabelList() != null) {
			for (PrefLabel prefLabel : concept.getPrefLabelList()) {
				try {
					solrInputDocument.addField(
							EdmLabel.CC_SKOS_PREF_LABEL.toString() + "."
									+ prefLabel.getLang().getLang(),
							prefLabel.getString());
				} catch (NullPointerException e) {
					solrInputDocument.addField(
							EdmLabel.CC_SKOS_PREF_LABEL.toString(), prefLabel.getString());
				}
			}
		}
		if (concept.getBroaderList() != null) {
			for (Broader broader : concept.getBroaderList()) {
				solrInputDocument.addField(EdmLabel.CC_SKOS_BROADER.toString(),
						broader.getBroader());
			}
		}
		if (concept.getNoteList() != null) {
			for (Note note : concept.getNoteList()) {
				solrInputDocument.addField(EdmLabel.CC_SKOS_NOTE.toString(),
						note.getString());
			}
		}
		return solrInputDocument;
	}

	public static ConceptImpl createConceptMongoFields(Concept concept,
			MongoDBServer mongoServer) {
		ConceptImpl conceptMongo = new ConceptImpl();
		try {
			conceptMongo = (ConceptImpl) mongoServer.searchByAbout(concept
					.getAbout());
			conceptMongo.getAbout();
		}
		// If it does not exist
		catch (NullPointerException npe) {
			conceptMongo = new ConceptImpl();
			conceptMongo.setAbout(concept.getAbout());

			if (concept.getNoteList() != null) {
				List<String> noteList = new ArrayList<String>();
				for (Note note : concept.getNoteList()) {
					noteList.add(note.getString());
				}
				conceptMongo.setNote(noteList.toArray(new String[noteList
						.size()]));
			}

			if (concept.getBroaderList() != null) {
				List<String> broaderList = new ArrayList<String>();
				for (Broader broader : concept.getBroaderList()) {
					broaderList.add(broader.getBroader());
				}
				conceptMongo.setBroader(broaderList
						.toArray(new String[broaderList.size()]));
			}

			if (concept.getPrefLabelList() != null) {
				Map<String, String> prefLabelMongo = new HashMap<String, String>();
				for (PrefLabel prefLabelJibx : concept.getPrefLabelList()) {
					try {
						prefLabelMongo.put(prefLabelJibx.getLang().getLang(),
								prefLabelJibx.getString());
					} catch (NullPointerException e) {
						prefLabelMongo.put("def", prefLabelJibx.getString());
					}
				}
				conceptMongo.setPrefLabel(prefLabelMongo);
			}

			if (concept.getAltLabelList() != null) {
				Map<String, String> altLabelMongo = new HashMap<String, String>();
				for (AltLabel altLabelJibx : concept.getAltLabelList()) {
					try {
						altLabelMongo.put(altLabelJibx.getLang().getLang(),
								altLabelJibx.getString());
					} catch (NullPointerException e) {
						altLabelMongo.put("def", altLabelJibx.getString());
					}
				}
				conceptMongo.setAltLabel(altLabelMongo);
			}
			mongoServer.getDatastore().save(conceptMongo);
		}
		return conceptMongo;
	}
}
