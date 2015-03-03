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

package eu.europeana.corelib.edm.server.importer.util;

import java.util.List;
import java.util.Map;

import org.apache.solr.common.SolrInputDocument;

import eu.europeana.corelib.definitions.jibx.AltLabel;
import eu.europeana.corelib.definitions.jibx.BroadMatch;
import eu.europeana.corelib.definitions.jibx.Broader;
import eu.europeana.corelib.definitions.jibx.CloseMatch;
import eu.europeana.corelib.definitions.jibx.Concept;
import eu.europeana.corelib.definitions.jibx.ExactMatch;
import eu.europeana.corelib.definitions.jibx.NarrowMatch;
import eu.europeana.corelib.definitions.jibx.Narrower;
import eu.europeana.corelib.definitions.jibx.Notation;
import eu.europeana.corelib.definitions.jibx.PrefLabel;
import eu.europeana.corelib.definitions.jibx.Related;
import eu.europeana.corelib.definitions.jibx.RelatedMatch;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.edm.utils.MongoUtils;
import eu.europeana.corelib.edm.utils.SolrUtils;
import eu.europeana.corelib.solr.entity.ConceptImpl;
import eu.europeana.corelib.utils.StringArrayUtils;

/**
 * Constructor for Concepts
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
public final class ConceptFieldInput {

	public ConceptFieldInput() {

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
	public SolrInputDocument createConceptSolrFields(Concept concept,
			SolrInputDocument solrInputDocument) {
		solrInputDocument.addField(EdmLabel.SKOS_CONCEPT.toString(),
				concept.getAbout());
		if(concept.getChoiceList()!=null){
		for (Concept.Choice choice : concept.getChoiceList()) {
			if (choice.ifAltLabel()) {
				AltLabel altLabel = choice.getAltLabel();
				solrInputDocument = SolrUtils
						.addFieldFromLiteral(solrInputDocument, altLabel,
								EdmLabel.CC_SKOS_ALT_LABEL);
			}
			if (choice.ifPrefLabel()) {

				PrefLabel prefLabel = choice.getPrefLabel();
				solrInputDocument = SolrUtils.addFieldFromLiteral(
						solrInputDocument, prefLabel,
						EdmLabel.CC_SKOS_PREF_LABEL);
			}

			if (choice.ifBroader()) {
				Broader broader = choice.getBroader();
				solrInputDocument.addField(EdmLabel.CC_SKOS_BROADER.toString(),
						broader.getResource());
			}
			if (choice.ifNote()) {
				solrInputDocument = SolrUtils.addFieldFromLiteral(
						solrInputDocument, choice.getNote(),
						EdmLabel.CC_SKOS_NOTE);
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
				solrInputDocument = SolrUtils
						.addFieldFromLiteral(solrInputDocument, notation,
								EdmLabel.CC_SKOS_NOTATIONS);
			}
		}
		}
		return solrInputDocument;
	}

	



	

	public ConceptImpl createNewConcept(Concept concept) {
		ConceptImpl conceptMongo = new ConceptImpl();
		conceptMongo.setAbout(concept.getAbout());
		if(concept.getChoiceList()!=null){
		for (Concept.Choice choice : concept.getChoiceList()) {
			if (choice.ifNote()) {
				if (conceptMongo.getNote() == null) {
					conceptMongo.setNote(MongoUtils
							.createLiteralMapFromString(choice.getNote()));
				} else {
					Map<String, List<String>> tempMap = conceptMongo.getNote();
					tempMap.putAll(MongoUtils.createLiteralMapFromString(choice
							.getNote()));
					conceptMongo.setNote(tempMap);
				}
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
				if (conceptMongo.getNotation() == null) {
					conceptMongo.setNotation(MongoUtils
							.createLiteralMapFromString(choice.getNotation()));
				} else {
					Map<String, List<String>> tempMap = conceptMongo
							.getNotation();
					tempMap.putAll(MongoUtils.createLiteralMapFromString(choice
							.getNotation()));
					conceptMongo.setNotation(tempMap);
				}
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
				if (conceptMongo.getPrefLabel() == null) {
					conceptMongo.setPrefLabel(MongoUtils
							.createLiteralMapFromString(choice.getPrefLabel()));
				} else {
					Map<String, List<String>> tempMap = conceptMongo
							.getPrefLabel();
					tempMap.putAll(MongoUtils.createLiteralMapFromString(choice
							.getPrefLabel()));
					conceptMongo.setPrefLabel(tempMap);
				}
			}

			if (choice.ifAltLabel()) {
				if (conceptMongo.getAltLabel() == null) {
					conceptMongo.setAltLabel(MongoUtils
							.createLiteralMapFromString(choice.getAltLabel()));
				} else {
					Map<String, List<String>> tempMap = conceptMongo
							.getAltLabel();
					tempMap.putAll(MongoUtils.createLiteralMapFromString(choice
							.getAltLabel()));
					conceptMongo.setAltLabel(tempMap);
				}
			}
		}
		
		}
		return conceptMongo;
	}
}
