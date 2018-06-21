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
import eu.europeana.corelib.definitions.jibx.Concept;
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
