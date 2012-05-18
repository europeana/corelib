/*
 * Copyright 2007-2012 The Europeana Foundation
 *
 *  Licenced under the EUPL, Version 1.1 (the "Licence") and subsequent versions as approved
 *  by the European Commission;
 *  You may not use this work except in compliance with the Licence.
 * 
 *  You may obtain a copy of the Licence at:
 *  http://joinup.ec.europa.eu/software/page/eupl
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under
 *  the Licence is distributed on an "AS IS" basis, without warranties or conditions of
 *  any kind, either express or implied.
 *  See the Licence for the specific language governing permissions and limitations under
 *  the Licence.
 */

package eu.europeana.corelib.solr.server.importer.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.common.SolrInputDocument;

import eu.europeana.corelib.definitions.jibx.AltLabel;
import eu.europeana.corelib.definitions.jibx.Broader;
import eu.europeana.corelib.definitions.jibx.Concept;
import eu.europeana.corelib.definitions.jibx.Note;
import eu.europeana.corelib.definitions.jibx.PrefLabel;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.solr.MongoServer;
import eu.europeana.corelib.solr.entity.ConceptImpl;
import eu.europeana.corelib.solr.server.EdmMongoServer;
import eu.europeana.corelib.solr.utils.MongoUtils;

/**
 * Constructor for Concepts
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
public final class ConceptFieldInput {

	
	private ConceptFieldInput(){
		
	}
	/**
	 * Return a SolrInputDocument with the Concept fields filled in
	 * 
	 * @param concept
	 *            The JiBX Entity holding the Concept field values
	 * @param solrInputDocument
	 *            The SolrInputDocument to alter
	 * @return The SolrInputDocument with the filled in values for a Concept
	 */
	public static SolrInputDocument createConceptSolrFields(Concept concept,
			SolrInputDocument solrInputDocument) {
		solrInputDocument.addField(EdmLabel.SKOS_CONCEPT.toString(),
				concept.getAbout());
		if (concept.getAltLabelList() != null) {
			for (AltLabel altLabel : concept.getAltLabelList()) {
				if (altLabel.getLang() != null) {
					solrInputDocument.addField(
							EdmLabel.CC_SKOS_ALT_LABEL.toString() + "."
									+ altLabel.getLang().getLang(),
							altLabel.getString());
				} else {
					solrInputDocument.addField(
							EdmLabel.CC_SKOS_ALT_LABEL.toString(),
							altLabel.getString());
				}
			}
		}
		if (concept.getPrefLabelList() != null) {
			for (PrefLabel prefLabel : concept.getPrefLabelList()) {
				if (prefLabel.getLang() != null) {
					solrInputDocument.addField(
							EdmLabel.CC_SKOS_PREF_LABEL.toString() + "."
									+ prefLabel.getLang().getLang(),
							prefLabel.getString());
				} else {
					solrInputDocument.addField(
							EdmLabel.CC_SKOS_PREF_LABEL.toString(),
							prefLabel.getString());
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

	/**
	 * Creates or updates a MongoDB Concept Entity
	 * 
	 * @param concept
	 *            The JiBX Concept Entity that has the field values of the
	 *            Concept
	 * @param mongoServer
	 *            The MongoDBServer instance that is going to be used to save
	 *            the MongoDB Concept
	 * @return The MongoDB Concept Entity
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static ConceptImpl createConceptMongoFields(Concept concept,
			MongoServer mongoServer)  {

		ConceptImpl conceptMongo = (ConceptImpl) ((EdmMongoServer)mongoServer).searchByAbout(
				ConceptImpl.class, concept.getAbout());
		if (conceptMongo == null) {
			// If it does not exist
			conceptMongo = createNewConcept(concept);
			mongoServer.getDatastore().save(conceptMongo);
		} else {
			conceptMongo = updateConcept(conceptMongo, concept, mongoServer);
		}
		return conceptMongo;
	}

	private static ConceptImpl updateConcept(ConceptImpl conceptMongo,
			Concept concept, MongoServer mongoServer)  {

		if (conceptMongo.getNote() != null) {
			List<String> newNoteList = new ArrayList<String>();
			for (Note noteJibx : concept.getNoteList()) {
				if (MongoUtils.contains(conceptMongo.getNote(),
						noteJibx.getString())) {
					newNoteList.add(noteJibx.getString());
				}
			}
			for (String note : conceptMongo.getNote()) {
				newNoteList.add(note);
			}
			MongoUtils.update(ConceptImpl.class, conceptMongo.getAbout(),
					mongoServer, "note", newNoteList);
		}

		if (conceptMongo.getAltLabel() != null) {
			Map<String, String> newAltLabelMap = conceptMongo.getAltLabel();
			if (concept.getAltLabelList() != null) {
				for (AltLabel altLabel : concept.getAltLabelList()) {
					if (altLabel.getLang() != null) {
						if (!MongoUtils.contains(newAltLabelMap, altLabel
								.getLang().getLang(), altLabel.getString())) {
							newAltLabelMap.put(altLabel.getLang().getLang(),
									altLabel.getString());
						}
					} else {
						newAltLabelMap.put("def", altLabel.getString());
					}
				}
			}
			MongoUtils.update(ConceptImpl.class, conceptMongo.getAbout(),
					mongoServer, "end", newAltLabelMap);

		}

		if (conceptMongo.getPrefLabel() != null) {
			Map<String, String> newPrefLabelMap = conceptMongo.getPrefLabel();
			if (concept.getPrefLabelList() != null) {
				for (PrefLabel prefLabel : concept.getPrefLabelList()) {
					if (prefLabel.getLang() != null) {
						if (!MongoUtils.contains(newPrefLabelMap, prefLabel
								.getLang().getLang(), prefLabel.getString())) {
							newPrefLabelMap.put(prefLabel.getLang().getLang(),
									prefLabel.getString());
						}
					} else {
						newPrefLabelMap.put("def", prefLabel.getString());
					}
				}
				MongoUtils.update(ConceptImpl.class, conceptMongo.getAbout(),
						mongoServer, "prefLabel", newPrefLabelMap);
			
			}
		}

		if (conceptMongo.getBroader() != null) {
			List<String> broaderList = new ArrayList<String>();
			for (Broader broaderJibx : concept.getBroaderList()) {
				if (!MongoUtils.contains(conceptMongo.getBroader(),
						broaderJibx.getBroader())) {
					broaderList.add(broaderJibx.getBroader());
				}
			}
			for (String broader : conceptMongo.getBroader()) {
				broaderList.add(broader);
			}
			MongoUtils.update(ConceptImpl.class, conceptMongo.getAbout(),
					mongoServer, "broader", broaderList);
		}
		return (ConceptImpl) ((EdmMongoServer)mongoServer).searchByAbout(ConceptImpl.class,
				concept.getAbout());
	}

	private static ConceptImpl createNewConcept(Concept concept) {
		ConceptImpl conceptMongo = new ConceptImpl();
		conceptMongo.setAbout(concept.getAbout());

		if (concept.getNoteList() != null) {
			List<String> noteList = new ArrayList<String>();
			for (Note note : concept.getNoteList()) {
				noteList.add(note.getString());
			}
			conceptMongo.setNote(noteList.toArray(new String[noteList.size()]));
		}

		if (concept.getBroaderList() != null) {
			List<String> broaderList = new ArrayList<String>();
			for (Broader broader : concept.getBroaderList()) {
				broaderList.add(broader.getBroader());
			}
			conceptMongo.setBroader(broaderList.toArray(new String[broaderList
					.size()]));
		}

		if (concept.getPrefLabelList() != null) {
			Map<String, String> prefLabelMongo = new HashMap<String, String>();
			for (PrefLabel prefLabelJibx : concept.getPrefLabelList()) {
				if (prefLabelJibx.getLang() != null) {
					prefLabelMongo.put(prefLabelJibx.getLang().getLang(),
							prefLabelJibx.getString());
				} else {
					prefLabelMongo.put("def", prefLabelJibx.getString());
				}
			}
			conceptMongo.setPrefLabel(prefLabelMongo);
		}

		if (concept.getAltLabelList() != null) {
			Map<String, String> altLabelMongo = new HashMap<String, String>();
			for (AltLabel altLabelJibx : concept.getAltLabelList()) {
				if (altLabelJibx.getLang() != null) {
					altLabelMongo.put(altLabelJibx.getLang().getLang(),
							altLabelJibx.getString());
				} else {
					altLabelMongo.put("def", altLabelJibx.getString());
				}
			}
			conceptMongo.setAltLabel(altLabelMongo);
		}
		return conceptMongo;
	}
}
