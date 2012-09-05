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

import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.solr.common.SolrInputDocument;

import com.google.code.morphia.mapping.MappingException;

import eu.europeana.corelib.definitions.jibx.AgentType;
import eu.europeana.corelib.definitions.jibx.AltLabel;
import eu.europeana.corelib.definitions.jibx.Date;
import eu.europeana.corelib.definitions.jibx.HasMet;
import eu.europeana.corelib.definitions.jibx.Identifier;
import eu.europeana.corelib.definitions.jibx.IsRelatedTo;
import eu.europeana.corelib.definitions.jibx.Note;
import eu.europeana.corelib.definitions.jibx.PrefLabel;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.solr.MongoServer;
import eu.europeana.corelib.solr.entity.AgentImpl;
import eu.europeana.corelib.solr.server.EdmMongoServer;
import eu.europeana.corelib.solr.utils.MongoUtils;
import eu.europeana.corelib.solr.utils.SolrUtils;

/**
 * Constructor of Agent Fields.
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */

public final class AgentFieldInput {

	private AgentFieldInput() {

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
	public static SolrInputDocument createAgentSolrFields(AgentType agentType,
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
				solrInputDocument = SolrUtils.addFieldFromLiteral(
						solrInputDocument, hasMet, EdmLabel.AG_EDM_HASMET);
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

		if (agentType.getProfessionOrOccupation() != null) {
			solrInputDocument = SolrUtils.addFieldFromResourceOrLiteral(
					solrInputDocument, agentType.getProfessionOrOccupation(),
					EdmLabel.AG_RDAGR2_PROFESSIONOROCCUPATION);
		}

		return solrInputDocument;
	}

	/**
	 * Create or Update a Mongo Entity of type Agent from the JiBX AgentType
	 * object
	 * 
	 * Mapping from the JibXBinding Fields to the MongoDB Entity Fields The
	 * fields mapped are the rdf:about (String -> String) skos:note(List<Note>
	 * -> String[]) skos:prefLabel(List<PrefLabel> ->
	 * HashMap<String,String>(lang,description)) skos:altLabel(List<AltLabel> ->
	 * HashMap<String,String>(lang,description)) edm:begin (String -> Date)
	 * edm:end (String -> Date)
	 * 
	 * @param agentType
	 *            - JiBX representation of an Agent EDM entity
	 * @param mongoServer
	 *            - The mongoServer to save the entity
	 * @return The created Agent
	 * @throws IOException
	 * @throws MalformedURLException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws MappingException
	 */
	public static AgentImpl createAgentMongoEntity(AgentType agentType,
			MongoServer mongoServer) throws MalformedURLException, IOException {

		AgentImpl agent = ((EdmMongoServer) mongoServer).searchByAbout(
				AgentImpl.class, agentType.getAbout());

		// if it does not exist

		if (agent == null) {
			agent = createNewAgent(agentType);
			mongoServer.getDatastore().save(agent);
		} else {
			agent = updateMongoAgent(agent, agentType, mongoServer);
		}
		return agent;
	}

	/**
	 * Update an already stored Agent Mongo Entity
	 * 
	 * @param agent
	 *            The agent to update
	 * @param agentType
	 *            The JiBX Agent Entity
	 * @param mongoServer
	 *            The MongoDB Server to save the Agent to
	 * @return The new Agent MongoDB Entity
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	private static AgentImpl updateMongoAgent(AgentImpl agent,
			AgentType agentType, MongoServer mongoServer) {
		
		if (agent.getBegin() != null) {
			MongoUtils
					.update(AgentImpl.class, agent.getAbout(), mongoServer,
							"begin", MongoUtils
									.createLiteralMapFromString(agentType
											.getBegin()));
		}

		if (agentType.getDateList() != null) {
			MongoUtils.update(AgentImpl.class, agent.getAbout(), mongoServer,
					"dcDate", MongoUtils
							.createResourceOrLiteralMapFromList(agentType
									.getDateList()));
		}

		if (agentType.getIdentifierList() != null) {
			MongoUtils.update(AgentImpl.class, agent.getAbout(), mongoServer,
					"dcIdentifier", MongoUtils
							.createLiteralMapFromList(agentType
									.getIdentifierList()));
		}

		if (agentType.getBiographicalInformation() != null) {
			MongoUtils.update(AgentImpl.class, agent.getAbout(), mongoServer,
					"biographicalInformation", MongoUtils
							.createLiteralMapFromString(agentType
									.getBiographicalInformation()));
		}

		if (agentType.getDateOfBirth() != null) {
			MongoUtils.update(AgentImpl.class, agent.getAbout(), mongoServer,
					"dateOfBirth", MongoUtils
							.createLiteralMapFromString(agentType
									.getDateOfBirth()));
		}

		if (agentType.getDateOfDeath() != null) {
			MongoUtils.update(AgentImpl.class, agent.getAbout(), mongoServer,
					"dateOfDeath", MongoUtils
							.createLiteralMapFromString(agentType
									.getDateOfDeath()));
		}

		if (agentType.getDateOfEstablishment() != null) {
			MongoUtils.update(AgentImpl.class, agent.getAbout(), mongoServer,
					"dateOfEstablishment", MongoUtils
							.createLiteralMapFromString(agentType
									.getDateOfEstablishment()));
		}

		if (agentType.getDateOfTermination() != null) {
			MongoUtils.update(AgentImpl.class, agent.getAbout(), mongoServer,
					"dateOfTermination", MongoUtils
							.createLiteralMapFromString(agentType
									.getDateOfTermination()));
		}

		if (agentType.getGender() != null) {
			MongoUtils.update(AgentImpl.class, agent.getAbout(), mongoServer,
					"gender", MongoUtils.createLiteralMapFromString(agentType
							.getGender()));
		}

		if (agentType.getHasMetList() != null) {
			MongoUtils.update(AgentImpl.class, agent.getAbout(), mongoServer,
					"hasMet", MongoUtils.createLiteralMapFromList(agentType
							.getHasMetList()));
		}

		if (agentType.getIsRelatedToList() != null) {
			MongoUtils.update(AgentImpl.class, agent.getAbout(), mongoServer,
					"isRelatedTo", MongoUtils
							.createResourceOrLiteralMapFromList(agentType
									.getIsRelatedToList()));
		}

		if (agentType.getNameList() != null) {
			MongoUtils.update(AgentImpl.class, agent.getAbout(), mongoServer,
					"name", MongoUtils.createLiteralMapFromList(agentType
							.getNameList()));
		}

		if (agentType.getSameAList() != null) {
			MongoUtils.update(AgentImpl.class, agent.getAbout(), mongoServer,
					"sameAs",
					SolrUtils.resourceListToArray(agentType.getSameAList()));
		}

		if (agentType.getProfessionOrOccupation() != null) {
			if (agentType.getHasMetList() != null) {
				MongoUtils.update(AgentImpl.class, agent.getAbout(),
						mongoServer, "professionOrOccupation", MongoUtils
								.createResourceOrLiteralMapFromString(agentType
										.getProfessionOrOccupation()));
			}
		}

		if (agent.getEnd() != null) {
			MongoUtils.update(AgentImpl.class, agent.getAbout(), mongoServer,
					"end",
					MongoUtils.createLiteralMapFromString(agentType.getEnd()));

		}

		if (agent.getNote() != null) {

			MongoUtils.update(AgentImpl.class, agent.getAbout(), mongoServer,
					"note", MongoUtils.createLiteralMapFromList(agentType
							.getNoteList()));
		}

		if (agent.getAltLabel() != null) {
			MongoUtils.update(AgentImpl.class, agent.getAbout(), mongoServer,
					"altLabel", MongoUtils.createLiteralMapFromList(agentType
							.getAltLabelList()));
		}

		if (agent.getPrefLabel() != null) {
			MongoUtils.update(AgentImpl.class, agent.getAbout(), mongoServer,
					"prefLabel", MongoUtils.createLiteralMapFromList(agentType
							.getPrefLabelList()));

		}

		return ((EdmMongoServer) mongoServer).searchByAbout(AgentImpl.class,
				agentType.getAbout());
	}

	/**
	 * Create new Agent MongoDB Entity from JiBX Agent Entity
	 * 
	 * @param agentType
	 * @return
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	private static AgentImpl createNewAgent(AgentType agentType)
			throws MalformedURLException, IOException {
		AgentImpl agent = new AgentImpl();
		// agent.setId(new ObjectId());
		agent.setAbout(agentType.getAbout());

		agent.setDcDate(MongoUtils.createResourceOrLiteralMapFromList(agentType
				.getDateList()));
		agent.setDcIdentifier(MongoUtils.createLiteralMapFromList(agentType
				.getIdentifierList()));
		agent.setEdmHasMet(MongoUtils.createLiteralMapFromList(agentType
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
		agent.setRdaGr2DateOfEstablishment(MongoUtils
				.createLiteralMapFromString(agentType.getDateOfEstablishment()));
		agent.setRdaGr2DateOfTermination(MongoUtils
				.createLiteralMapFromString(agentType.getDateOfTermination()));
		agent.setRdaGr2Gender(MongoUtils.createLiteralMapFromString(agentType
				.getGender()));
		agent.setRdaGr2ProfessionOrOccupation(MongoUtils
				.createResourceOrLiteralMapFromString(agentType
						.getProfessionOrOccupation()));
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
