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
import eu.europeana.corelib.definitions.jibx.BroadMatch;
import eu.europeana.corelib.definitions.jibx.Broader;
import eu.europeana.corelib.definitions.jibx.CloseMatch;
import eu.europeana.corelib.definitions.jibx.Concept;
import eu.europeana.corelib.definitions.jibx.ExactMatch;
import eu.europeana.corelib.definitions.jibx.LiteralType;
import eu.europeana.corelib.definitions.jibx.NarrowMatch;
import eu.europeana.corelib.definitions.jibx.Narrower;
import eu.europeana.corelib.definitions.jibx.Notation;
import eu.europeana.corelib.definitions.jibx.Note;
import eu.europeana.corelib.definitions.jibx.PrefLabel;
import eu.europeana.corelib.definitions.jibx.RDF;
import eu.europeana.corelib.definitions.jibx.Related;
import eu.europeana.corelib.definitions.jibx.RelatedMatch;
import eu.europeana.corelib.definitions.jibx.ResourceType;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.solr.MongoServer;
import eu.europeana.corelib.solr.entity.ConceptImpl;
import eu.europeana.corelib.solr.server.EdmMongoServer;
import eu.europeana.corelib.solr.utils.MongoUtils;
import eu.europeana.corelib.solr.utils.SolrUtils;
import eu.europeana.corelib.utils.StringArrayUtils;

/**
 * Constructor for Concepts
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
public final class ConceptFieldInput {

	private ConceptFieldInput() {

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
		for (Concept.Choice choice : concept.getChoiceList()) {
			if (choice.ifAltLabel()) {
				AltLabel altLabel = choice.getAltLabel();
				if (altLabel.getLang() != null) {
					solrInputDocument.addField(
							EdmLabel.CC_SKOS_ALT_LABEL.toString() +(altLabel.getLang()!=null? "."
									+ altLabel.getLang().getLang():""),
							altLabel.getString());
				} else {
					solrInputDocument.addField(
							EdmLabel.CC_SKOS_ALT_LABEL.toString(),
							altLabel.getString());
				}
			}
			if (choice.ifPrefLabel()) {

				PrefLabel prefLabel = choice.getPrefLabel();
				if (prefLabel.getLang() != null) {
					solrInputDocument.addField(
							EdmLabel.CC_SKOS_PREF_LABEL.toString() + (prefLabel.getLang()!=null?"."
									+ prefLabel.getLang().getLang() :""),
							prefLabel.getString());
				} else {
					solrInputDocument.addField(
							EdmLabel.CC_SKOS_PREF_LABEL.toString(),
							prefLabel.getString());
				}
			}

			if (choice.ifBroader()) {
				Broader broader = choice.getBroader();
				solrInputDocument.addField(EdmLabel.CC_SKOS_BROADER.toString(),
						broader.getResource());
			}
			if (choice.ifNote()) {
				Note note = choice.getNote();
				solrInputDocument.addField(EdmLabel.CC_SKOS_NOTE.toString(),
						note.getString());
			}
			if (choice.ifBroadMatch()) {
				BroadMatch broadMatch = choice.getBroadMatch();
				solrInputDocument.addField(
						EdmLabel.CC_SKOS_BROADMATCH.toString(),
						broadMatch.getResource());
			}
			if (choice.ifCloseMatch()) {
				CloseMatch closeMatch = choice.getCloseMatch();
				solrInputDocument.addField(
						EdmLabel.CC_SKOS_CLOSEMATCH.toString(),
						closeMatch.getResource());
			}
			if (choice.ifExactMatch()) {
				ExactMatch exactMatch = choice.getExactMatch();
				solrInputDocument.addField(
						EdmLabel.CC_SKOS_EXACTMATCH.toString(),
						exactMatch.getResource());
			}
			if (choice.ifNarrower()) {
				Narrower narrower = choice.getNarrower();
				solrInputDocument.addField(
						EdmLabel.CC_SKOS_NARROWER.toString(),
						narrower.getResource());
			}
			if (choice.ifNarrowMatch()) {
				NarrowMatch narrowMatch = choice.getNarrowMatch();
				solrInputDocument.addField(
						EdmLabel.CC_SKOS_NARROWMATCH.toString(),
						narrowMatch.getResource());
			}
			if (choice.ifRelatedMatch()) {
				RelatedMatch relatedMatch = choice.getRelatedMatch();
				solrInputDocument.addField(
						EdmLabel.CC_SKOS_RELATEDMATCH.toString(),
						relatedMatch.getResource());
			}
			if (choice.ifRelated()) {
				Related related = choice.getRelated();

				solrInputDocument.addField(EdmLabel.CC_SKOS_RELATED.toString(),
						related.getResource());
			}
			if (choice.ifNotation()) {
				Notation notation = choice.getNotation();
				solrInputDocument.addField(
						EdmLabel.CC_SKOS_NOTATIONS.toString(),
						notation.getString());
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
			MongoServer mongoServer, RDF rdf) {

		ConceptImpl conceptMongo = (ConceptImpl) ((EdmMongoServer) mongoServer)
				.searchByAbout(ConceptImpl.class, concept.getAbout());
		if (conceptMongo == null) {
			// If it does not exist
			conceptMongo = createNewConcept(concept);
			mongoServer.getDatastore().save(conceptMongo);
		} else {
			conceptMongo = updateConcept(conceptMongo, concept, mongoServer);
		}
		return conceptMongo;
	}

	private static List<String> createObjectList(String[] originalValues,
			Object obj) {

		List<String> newList = new ArrayList<String>();
		String str = "";
		if (obj instanceof ResourceType) {
			str = ((ResourceType) obj).getResource();
		} else {
			str = ((LiteralType) obj).getString();
		}

		if (!MongoUtils.contains(originalValues, str)) {
			newList.add(str);
		}

		for (String arrStr : originalValues) {
			newList.add(arrStr);
		}
		return newList;
	}

	private static ConceptImpl updateConcept(ConceptImpl conceptMongo,
			Concept concept, MongoServer mongoServer) {
		for (Concept.Choice choice : concept.getChoiceList()) {
			if (choice.ifNote()) {
				if (conceptMongo.getNote() != null) {

					List<String> newNoteList = createObjectList(
							conceptMongo.getNote(), choice.getNote());
					MongoUtils.update(ConceptImpl.class,
							conceptMongo.getAbout(), mongoServer, "note",
							newNoteList);
				}
			}
			if (choice.ifAltLabel()) {
				if (conceptMongo.getAltLabel() != null) {
					Map<String, String> newAltLabelMap = conceptMongo
							.getAltLabel();

					AltLabel altLabel = choice.getAltLabel();
					if (altLabel.getLang() != null) {
						if (!MongoUtils.contains(newAltLabelMap, altLabel
								.getLang().getLang(), altLabel.getString())) {
							newAltLabelMap.put(altLabel.getLang().getLang(),
									altLabel.getString());
						}
					} else {
						newAltLabelMap.put("def", altLabel.getString());
					}

					MongoUtils.update(ConceptImpl.class,
							conceptMongo.getAbout(), mongoServer, "end",
							newAltLabelMap);
				}
			}

			if (choice.ifPrefLabel()) {
				if (conceptMongo.getPrefLabel() != null) {
					Map<String, String> newPrefLabelMap = conceptMongo
							.getPrefLabel();

					PrefLabel prefLabel = choice.getPrefLabel();
					if (prefLabel.getLang() != null) {
						if (!MongoUtils.contains(newPrefLabelMap, prefLabel
								.getLang().getLang(), prefLabel.getString())) {
							newPrefLabelMap.put(prefLabel.getLang().getLang(),
									prefLabel.getString());
						}
					} else {
						newPrefLabelMap.put("def", prefLabel.getString());
					}

					MongoUtils.update(ConceptImpl.class,
							conceptMongo.getAbout(), mongoServer, "prefLabel",
							newPrefLabelMap);

				}

			}
			if (choice.ifBroader()) {
				if (conceptMongo.getBroader() != null) {
					List<String> broaderList = createObjectList(
							conceptMongo.getBroader(), choice.getBroader());
					MongoUtils.update(ConceptImpl.class,
							conceptMongo.getAbout(), mongoServer, "broader",
							broaderList);
				}
			}
			if (choice.ifBroadMatch()) {
				if (conceptMongo.getBroadMatch() != null) {
					List<String> broadMatchList = createObjectList(conceptMongo.getBroadMatch(), choice.getBroadMatch());
					MongoUtils.update(ConceptImpl.class,
							conceptMongo.getAbout(), mongoServer, "broadMatch",
							broadMatchList);
				}
			}
			if (choice.ifCloseMatch()) {
				if (conceptMongo.getCloseMatch() != null) {
					List<String> closeMatchList = createObjectList(conceptMongo.getCloseMatch(),choice.getCloseMatch());
					MongoUtils.update(ConceptImpl.class,
							conceptMongo.getAbout(), mongoServer, "closeMatch",
							closeMatchList);
				}
			}
			if (choice.ifExactMatch()) {
				if (conceptMongo.getExactMatch() != null) {
					List<String> exactMatchList =createObjectList(conceptMongo.getExactMatch(),choice.getExactMatch());
					MongoUtils.update(ConceptImpl.class,
							conceptMongo.getAbout(), mongoServer, "exactMatch",
							exactMatchList);
				}
			}

			if (choice.ifNarrowMatch()) {
				if (conceptMongo.getNarrowMatch() != null) {
					List<String> narrowMatchList = createObjectList(conceptMongo.getNarrowMatch(),choice.getNarrowMatch());
					MongoUtils.update(ConceptImpl.class,
							conceptMongo.getAbout(), mongoServer, "narrowMatch",
							narrowMatchList);
				}
			}

			if (choice.ifInScheme()) {
				if (conceptMongo.getInScheme() != null) {
					List<String> inSchemeList = createObjectList(conceptMongo.getInScheme(),choice.getInScheme());
					MongoUtils.update(ConceptImpl.class,
							conceptMongo.getAbout(), mongoServer, "inScheme",
							inSchemeList);
				}
			}
			
			if (choice.ifNarrower()) {
				if (conceptMongo.getNarrower() != null) {
					List<String> narrowerList= createObjectList(conceptMongo.getNarrower(),choice.getNarrower());
					MongoUtils.update(ConceptImpl.class,
							conceptMongo.getAbout(), mongoServer, "narrower",
							narrowerList);
				}
			}
			if (choice.ifInScheme()) {
				if (conceptMongo.getInScheme() != null) {
					List<String> inSchemeList = createObjectList(conceptMongo.getInScheme(),choice.getInScheme());
					MongoUtils.update(ConceptImpl.class,
							conceptMongo.getAbout(), mongoServer, "inScheme",
							inSchemeList);
				}
			}
			if (choice.ifNotation()) {
				if (conceptMongo.getNotation() != null) {
					List<String> notationList = createObjectList(conceptMongo.getNotation(),choice.getNotation());
					MongoUtils.update(ConceptImpl.class,
							conceptMongo.getAbout(), mongoServer, "notation",
							notationList);
				}
			}
			if (choice.ifRelated()) {
				if (conceptMongo.getRelated() != null) {
					List<String> relatedList = createObjectList(conceptMongo.getRelated(),choice.getRelated());
					MongoUtils.update(ConceptImpl.class,
							conceptMongo.getAbout(), mongoServer, "related",
							relatedList);
				}
			}
			if (choice.ifRelatedMatch()) {
				if (conceptMongo.getRelated() != null) {
					List<String> relatedMatchList = createObjectList(conceptMongo.getRelatedMatch(),choice.getRelatedMatch());
					MongoUtils.update(ConceptImpl.class,
							conceptMongo.getAbout(), mongoServer, "relatedMatch",
							relatedMatchList);
				}
			}
		}

		return (ConceptImpl) ((EdmMongoServer) mongoServer).searchByAbout(
				ConceptImpl.class, concept.getAbout());
	}

	private static ConceptImpl createNewConcept(Concept concept) {
		ConceptImpl conceptMongo = new ConceptImpl();
	//	conceptMongo.setId(new ObjectId());
		conceptMongo.setAbout(concept.getAbout());
		for (Concept.Choice choice : concept.getChoiceList()) {
			if (choice.ifNote()) {
				conceptMongo.setNote(StringArrayUtils.addToArray(
						conceptMongo.getNote(),
						SolrUtils.getLiteralString(choice.getNote())));
			}
			if (choice.ifBroader()) {
				conceptMongo.setBroader(StringArrayUtils.addToArray(
						conceptMongo.getBroader(),
						SolrUtils.getResourceString(choice.getBroader())));
			}
			if (choice.ifBroadMatch()) {
				conceptMongo.setBroadMatch(StringArrayUtils.addToArray(
						conceptMongo.getBroadMatch(),
						SolrUtils.getResourceString(choice.getBroadMatch())));
			}
			if (choice.ifCloseMatch()) {
				conceptMongo.setCloseMatch(StringArrayUtils.addToArray(
						conceptMongo.getCloseMatch(),
						SolrUtils.getResourceString(choice.getCloseMatch())));
			}
			if (choice.ifExactMatch()) {
				conceptMongo.setExactMatch(StringArrayUtils.addToArray(
						conceptMongo.getExactMatch(),
						SolrUtils.getResourceString(choice.getExactMatch())));
			}
			if (choice.ifNarrower()) {
				conceptMongo.setNarrower(StringArrayUtils.addToArray(
						conceptMongo.getNarrower(),
						SolrUtils.getResourceString(choice.getNarrower())));
			}
			if (choice.ifNarrowMatch()) {
				conceptMongo.setNarrowMatch(StringArrayUtils.addToArray(
						conceptMongo.getNarrowMatch(),
						SolrUtils.getResourceString(choice.getNarrowMatch())));
			}
			if (choice.ifNotation()) {
				conceptMongo.setNotation(StringArrayUtils.addToArray(
						conceptMongo.getNotation(),
						SolrUtils.getLiteralString(choice.getNotation())));
			}
			if (choice.ifRelated()) {
				conceptMongo.setRelated(StringArrayUtils.addToArray(
						conceptMongo.getRelated(),
						SolrUtils.getResourceString(choice.getRelated())));
			}
			if (choice.ifRelatedMatch()) {
				conceptMongo.setCloseMatch(StringArrayUtils.addToArray(
						conceptMongo.getRelatedMatch(),
						SolrUtils.getResourceString(choice.getRelatedMatch())));
			}

			if (choice.ifPrefLabel()) {
				Map<String, String> prefLabelMongo = new HashMap<String, String>();
				PrefLabel prefLabelJibx = choice.getPrefLabel();
				if (prefLabelJibx.getLang() != null) {
					prefLabelMongo.put(prefLabelJibx.getLang().getLang(),
							prefLabelJibx.getString());
				} else {
					prefLabelMongo.put( prefLabelJibx.getString(), prefLabelJibx.getString());
				}

				conceptMongo.setPrefLabel(prefLabelMongo);
			}

			if (choice.ifAltLabel()) {
				Map<String, String> altLabelMongo = new HashMap<String, String>();
				AltLabel altLabelJibx = choice.getAltLabel();
				if (altLabelJibx.getLang() != null) {
					altLabelMongo.put(altLabelJibx.getLang().getLang(),
							altLabelJibx.getString());
				} else {
					altLabelMongo.put(altLabelJibx.getString(), altLabelJibx.getString());
				}

				conceptMongo.setAltLabel(altLabelMongo);
			}
		}
		return conceptMongo;
	}
}
