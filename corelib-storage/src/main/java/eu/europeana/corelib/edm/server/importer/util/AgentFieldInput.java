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

import eu.europeana.corelib.definitions.jibx.ProfessionOrOccupation;
import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.solr.common.SolrInputDocument;

import eu.europeana.corelib.definitions.jibx.AgentType;
import eu.europeana.corelib.definitions.jibx.AltLabel;
import eu.europeana.corelib.definitions.jibx.Date;
import eu.europeana.corelib.definitions.jibx.HasMet;
import eu.europeana.corelib.definitions.jibx.Identifier;
import eu.europeana.corelib.definitions.jibx.IsRelatedTo;
import eu.europeana.corelib.definitions.jibx.Note;
import eu.europeana.corelib.definitions.jibx.PrefLabel;
import eu.europeana.corelib.definitions.jibx.SameAs;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.edm.utils.MongoUtils;
import eu.europeana.corelib.edm.utils.SolrUtils;
import eu.europeana.corelib.solr.entity.AgentImpl;

/**
 * Constructor of Agent Fields.
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */

public final class AgentFieldInput {

	public AgentFieldInput() {

	}

	/**
	 * Fill in a SolrInputDocument with Agent specific fields
	 * 
	 * @param agentType
	 *            A JiBX entity that represents an Agent
	 * @param solrInputDocument
	 *            The SolrInputDocument to alter
	 * @return The SolrInputDocument with Agent specific values
	 */
	public SolrInputDocument createAgentSolrFields(AgentType agentType,
			SolrInputDocument solrInputDocument) {
		solrInputDocument.addField(EdmLabel.EDM_AGENT.toString(),
				agentType.getAbout());
		if (agentType.getAltLabelList() != null) {
			for (AltLabel altLabel : agentType.getAltLabelList()) {
				solrInputDocument = SolrUtils
						.addFieldFromLiteral(solrInputDocument, altLabel,
								EdmLabel.AG_SKOS_ALT_LABEL);
			}
		}
		if (agentType.getPrefLabelList() != null) {
			for (PrefLabel prefLabel : agentType.getPrefLabelList()) {
				solrInputDocument = SolrUtils.addFieldFromLiteral(
						solrInputDocument, prefLabel,
						EdmLabel.AG_SKOS_PREF_LABEL);
			}
		}

		if (agentType.getNoteList() != null) {
			for (Note note : agentType.getNoteList()) {
				solrInputDocument = SolrUtils.addFieldFromLiteral(
						solrInputDocument, note, EdmLabel.AG_SKOS_NOTE);
			}
		}

		if (agentType.getBegin() != null) {
			solrInputDocument = SolrUtils.addFieldFromLiteral(
					solrInputDocument, agentType.getBegin(),
					EdmLabel.AG_EDM_BEGIN);
		}

		if (agentType.getEnd() != null) {
			solrInputDocument = SolrUtils.addFieldFromLiteral(
					solrInputDocument, agentType.getEnd(), EdmLabel.AG_EDM_END);
		}

		if (agentType.getDateList() != null) {
			for (Date date : agentType.getDateList()) {
				solrInputDocument = SolrUtils.addFieldFromResourceOrLiteral(
						solrInputDocument, date, EdmLabel.AG_DC_DATE);
			}
		}

		if (agentType.getIdentifierList() != null) {
			for (Identifier identifier : agentType.getIdentifierList()) {
				if (identifier.getString() != null) {
					solrInputDocument = SolrUtils.addFieldFromLiteral(
							solrInputDocument, identifier,
							EdmLabel.AG_DC_IDENTIFIER);
				}
			}
		}

		if (agentType.getHasMetList() != null) {
			for (HasMet hasMet : agentType.getHasMetList()) {
				solrInputDocument = SolrUtils.addFieldFromResource(
						solrInputDocument, hasMet, EdmLabel.AG_EDM_HASMET);
			}
		}
		if(agentType.getSameAList()!=null){
			for(SameAs sameAs:agentType.getSameAList()){
				solrInputDocument = SolrUtils.addFieldFromResource(solrInputDocument, sameAs, EdmLabel.AG_OWL_SAMEAS);
			}
		}
		if (agentType.getIsRelatedToList() != null) {
			for (IsRelatedTo isRelatedTo : agentType.getIsRelatedToList()) {
				solrInputDocument = SolrUtils.addFieldFromResourceOrLiteral(
						solrInputDocument, isRelatedTo,
						EdmLabel.AG_EDM_ISRELATEDTO);
			}
		}

		if (agentType.getBiographicalInformation() != null) {
			solrInputDocument = SolrUtils.addFieldFromLiteral(
					solrInputDocument, agentType.getBiographicalInformation(),
					EdmLabel.AG_RDAGR2_BIOGRAPHICALINFORMATION);
		}

		if (agentType.getDateOfBirth() != null) {
			solrInputDocument = SolrUtils.addFieldFromLiteral(
					solrInputDocument, agentType.getDateOfBirth(),
					EdmLabel.AG_RDAGR2_DATEOFBIRTH);
		}

		if (agentType.getDateOfDeath() != null) {
			solrInputDocument = SolrUtils.addFieldFromLiteral(
					solrInputDocument, agentType.getDateOfDeath(),
					EdmLabel.AG_RDAGR2_DATEOFDEATH);
		}

		if (agentType.getPlaceOfBirth() != null) {
			solrInputDocument = SolrUtils.addFieldFromResourceOrLiteral(
					solrInputDocument, agentType.getPlaceOfBirth(),
					EdmLabel.AG_RDAGR2_PLACEOFBIRTH);
		}

		if (agentType.getPlaceOfDeath() != null) {
			solrInputDocument = SolrUtils.addFieldFromResourceOrLiteral(
					solrInputDocument, agentType.getPlaceOfDeath(),
					EdmLabel.AG_RDAGR2_PLACEOFDEATH);
		}
		
		if (agentType.getDateOfEstablishment() != null) {
			solrInputDocument = SolrUtils.addFieldFromLiteral(
					solrInputDocument, agentType.getDateOfEstablishment(),
					EdmLabel.AG_RDAGR2_DATEOFESTABLISHMENT);
		}

		if (agentType.getDateOfTermination() != null) {
			solrInputDocument = SolrUtils.addFieldFromLiteral(
					solrInputDocument, agentType.getDateOfTermination(),
					EdmLabel.AG_RDAGR2_DATEOFTERMINATION);
		}

		if (agentType.getGender() != null) {
			solrInputDocument = SolrUtils.addFieldFromLiteral(
					solrInputDocument, agentType.getGender(),
					EdmLabel.AG_RDAGR2_GENDER);
		}

		if (agentType.getProfessionOrOccupationList() != null) {
			for (ProfessionOrOccupation professionOrOccupation : agentType.getProfessionOrOccupationList()) {
				solrInputDocument = SolrUtils.addFieldFromResourceOrLiteral(
						solrInputDocument, professionOrOccupation,
						EdmLabel.AG_RDAGR2_PROFESSIONOROCCUPATION);
			}
		}

		return solrInputDocument;
	}

	
	

	/**
	 * Create new Agent MongoDB Entity from JiBX Agent Entity
	 * 
	 * @param agentType
	 * @return
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	public AgentImpl createNewAgent(AgentType agentType)
			throws IOException {
		AgentImpl agent = new AgentImpl();
		agent.setAbout(agentType.getAbout());

		agent.setDcDate(MongoUtils.createResourceOrLiteralMapFromList(agentType
				.getDateList()));
		agent.setDcIdentifier(MongoUtils.createLiteralMapFromList(agentType
				.getIdentifierList()));
		agent.setEdmHasMet(MongoUtils.createResourceMapFromList(agentType
				.getHasMetList()));
		agent.setEdmIsRelatedTo(MongoUtils
				.createResourceOrLiteralMapFromList(agentType
						.getIsRelatedToList()));
		agent.setFoafName(MongoUtils.createLiteralMapFromList(agentType
				.getNameList()));
		agent.setRdaGr2BiographicalInformation(MongoUtils
				.createLiteralMapFromString(agentType
						.getBiographicalInformation()));
		agent.setRdaGr2DateOfBirth(MongoUtils
				.createLiteralMapFromString(agentType.getDateOfBirth()));
		agent.setRdaGr2DateOfDeath(MongoUtils
				.createLiteralMapFromString(agentType.getDateOfDeath()));
		agent.setRdaGr2PlaceOfBirth(MongoUtils
				.createResourceOrLiteralMapFromString(agentType.getPlaceOfBirth()));
		agent.setRdaGr2PlaceOfDeath(MongoUtils
				.createResourceOrLiteralMapFromString(agentType.getPlaceOfDeath()));
		agent.setRdaGr2DateOfEstablishment(MongoUtils
				.createLiteralMapFromString(agentType.getDateOfEstablishment()));
		agent.setRdaGr2DateOfTermination(MongoUtils
				.createLiteralMapFromString(agentType.getDateOfTermination()));
		agent.setRdaGr2Gender(MongoUtils.createLiteralMapFromString(agentType
				.getGender()));
		agent.setRdaGr2ProfessionOrOccupation(MongoUtils
				.createResourceOrLiteralMapFromList(
						agentType.getProfessionOrOccupationList()));
		agent.setNote(MongoUtils.createLiteralMapFromList(agentType
				.getNoteList()));
		agent.setPrefLabel(MongoUtils.createLiteralMapFromList(agentType
				.getPrefLabelList()));
		agent.setAltLabel(MongoUtils.createLiteralMapFromList(agentType
				.getAltLabelList()));
		agent.setBegin(MongoUtils.createLiteralMapFromString(agentType
				.getBegin()));
		agent.setEnd(MongoUtils.createLiteralMapFromString(agentType.getEnd()));
		agent.setOwlSameAs(SolrUtils.resourceListToArray(agentType
				.getSameAList()));
		return agent;
	}
}
