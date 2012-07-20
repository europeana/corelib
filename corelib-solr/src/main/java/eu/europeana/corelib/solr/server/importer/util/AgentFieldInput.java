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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.common.SolrInputDocument;
import org.bson.types.ObjectId;

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
				if (altLabel.getLang() != null) {
					solrInputDocument.addField(
							EdmLabel.AG_SKOS_ALT_LABEL.toString() + "."
									+ altLabel.getLang().getLang(),
							altLabel.getString());
				} else {
					solrInputDocument.addField(
							EdmLabel.AG_SKOS_ALT_LABEL.toString(),
							altLabel.getString());
				}
			}
		}
		if (agentType.getPrefLabelList() != null) {
			for (PrefLabel prefLabel : agentType.getPrefLabelList()) {
				if (prefLabel.getLang() != null) {
					solrInputDocument.addField(
							EdmLabel.AG_SKOS_PREF_LABEL.toString() + "."
									+ prefLabel.getLang().getLang(),
							prefLabel.getString());
				} else {
					solrInputDocument.addField(
							EdmLabel.AG_SKOS_PREF_LABEL.toString(),
							prefLabel.getString());
				}
			}
		}

		if (agentType.getNoteList() != null) {
			for (Note note : agentType.getNoteList()) {
				solrInputDocument.addField(EdmLabel.AG_SKOS_NOTE.toString(),
						note.getString());
			}
		}
		if (agentType.getBegin() != null) {

			solrInputDocument.addField(EdmLabel.AG_EDM_BEGIN.toString(),
					agentType.getBegin().getString());

		}
		if (agentType.getEnd() != null) {

			solrInputDocument.addField(EdmLabel.AG_EDM_END.toString(),
					agentType.getEnd().getString());

		}
		if (agentType.getDateList() != null) {
			for (Date date : agentType.getDateList()) {
				if (date.getString() != null) {
					solrInputDocument.addField(EdmLabel.AG_DC_DATE.toString(),
							date.getString());
				}
				if (date.getResource() != null) {
					solrInputDocument.addField(EdmLabel.AG_DC_DATE.toString(),
							date.getResource());
				}
			}
		}

		if (agentType.getIdentifierList() != null) {
			for (Identifier identifier : agentType.getIdentifierList()) {
				if (identifier.getString() != null) {
					solrInputDocument.addField(
							EdmLabel.AG_DC_IDENTIFIER.toString(),
							identifier.getString());
				}
			}
		}

		if (agentType.getHasMetList() != null) {
			for (HasMet hasMet : agentType.getHasMetList()) {
				if (hasMet.getString() != null) {
					solrInputDocument.addField(
							EdmLabel.AG_EDM_HASMET.toString(),
							hasMet.getString());
				}
			}
		}

		if (agentType.getIsRelatedToList() != null) {
			for (IsRelatedTo isRelatedTo : agentType.getIsRelatedToList()) {
				if (isRelatedTo.getResource() != null) {
					solrInputDocument.addField(
							EdmLabel.AG_EDM_ISRELATEDTO.toString(),
							isRelatedTo.getResource());
				}
				if (isRelatedTo.getString() != null) {
					solrInputDocument.addField(
							EdmLabel.AG_EDM_ISRELATEDTO.toString(),
							isRelatedTo.getString());
				}
			}
		}

		if (agentType.getBiographicalInformation() != null) {
			solrInputDocument.addField(
					EdmLabel.AG_RDAGR2_BIOGRAPHICALINFORMATION.toString(),
					agentType.getBiographicalInformation().getString());
		}
		if (agentType.getDateOfBirth() != null) {
			solrInputDocument.addField(EdmLabel.AG_RDAGR2_DATEOFBIRTH
					.toString(), agentType.getDateOfBirth().getString());
		}
		if (agentType.getDateOfDeath() != null) {
			solrInputDocument.addField(EdmLabel.AG_RDAGR2_DATEOFDEATH
					.toString(), agentType.getDateOfDeath().getString());
		}
		if (agentType.getDateOfEstablishment() != null) {
			solrInputDocument.addField(EdmLabel.AG_RDAGR2_DATEOFESTABLISHMENT
					.toString(), agentType.getDateOfEstablishment().getString());
		}
		if (agentType.getDateOfTermination() != null) {
			solrInputDocument.addField(EdmLabel.AG_RDAGR2_DATEOFTERMINATION
					.toString(), agentType.getDateOfTermination().getString());
		}
		if (agentType.getGender() != null) {
			solrInputDocument.addField(EdmLabel.AG_RDAGR2_GENDER
					.toString(), agentType.getGender().getString());
		}
		if(agentType.getProfessionOrOccupation()!=null){
			solrInputDocument.addField(EdmLabel.AG_RDAGR2_PROFESSIONOROCCUPATION
					.toString(), agentType.getProfessionOrOccupation().getString());
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
			MongoServer mongoServer)
			throws MalformedURLException, IOException {

		AgentImpl agent = (AgentImpl) ((EdmMongoServer) mongoServer)
				.searchByAbout(AgentImpl.class, agentType.getAbout());

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
		if (agent.getBegin() != null
				&& !StringUtils.equals(agentType.getBegin().getString(),
						agent.getBegin())) {
			MongoUtils.update(AgentImpl.class, agent.getAbout(), mongoServer,
					"begin", agentType.getBegin().getString());
		}

		
		
		if (agent.getEnd() != null
				&& !StringUtils.equals(agentType.getEnd().getString(),
						agent.getEnd())) {
			MongoUtils.update(AgentImpl.class, agent.getAbout(), mongoServer,
					"end", agentType.getEnd().getString());

		}

		if (agent.getNote() != null&& agentType.getNoteList()!=null) {
			List<String> newNoteList = new ArrayList<String>();
			for (Note noteJibx : agentType.getNoteList()) {
				if (!MongoUtils.contains(agent.getNote(), noteJibx.getString())) {
					newNoteList.add(noteJibx.getString());
				}
			}
			for (String note : agent.getNote()) {
				newNoteList.add(note);
			}

			MongoUtils.update(AgentImpl.class, agent.getAbout(), mongoServer,
					"note", newNoteList);
		}

		if (agent.getAltLabel() != null) {
			Map<String, String> newAltLabelMap = agent.getAltLabel();
			if (agentType.getAltLabelList() != null) {
				for (AltLabel altLabel : agentType.getAltLabelList()) {
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
			MongoUtils.update(AgentImpl.class, agent.getAbout(), mongoServer,
					"altLabel", newAltLabelMap);
		}

		if (agent.getPrefLabel() != null) {
			Map<String, String> newPrefLabelMap = agent.getPrefLabel();
			if (agentType.getPrefLabelList() != null) {
				for (PrefLabel prefLabel : agentType.getPrefLabelList()) {
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
				MongoUtils.update(AgentImpl.class, agent.getAbout(),
						mongoServer, "prefLabel", newPrefLabelMap);
			}
		}
		
		return (AgentImpl) ((EdmMongoServer) mongoServer).searchByAbout(
				AgentImpl.class, agentType.getAbout());
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
		agent.setId(new ObjectId());
		agent.setAbout(agentType.getAbout());

		agent.setDcDate(SolrUtils.resourceOrLiteralListToArray(agentType
				.getDateList()));
		agent.setDcIdentifier(SolrUtils.literalListToArray(agentType
				.getIdentifierList()));
		agent.setEdmHasMet(SolrUtils.literalListToArray(agentType
				.getHasMetList()));
		agent.setEdmIsRelatedTo(SolrUtils
				.resourceOrLiteralListToArray(agentType.getIsRelatedToList()));
		agent.setFoafName(SolrUtils.literalListToArray(agentType.getNameList()));
		agent.setRdaGr2BiographicalInformation(SolrUtils
				.getLiteralString(agentType.getBiographicalInformation()));
		agent.setRdaGr2DateOfBirth(SolrUtils.getLiteralString(agentType
				.getDateOfBirth()));
		agent.setRdaGr2DateOfDeath(SolrUtils.getLiteralString(agentType
				.getDateOfDeath()));
		agent.setRdaGr2DateOfEstablishment(SolrUtils.getLiteralString(agentType
				.getDateOfEstablishment()));
		agent.setRdaGr2DateOfTermination(SolrUtils.getLiteralString(agentType
				.getDateOfTermination()));
		agent.setRdaGr2Gender(SolrUtils.getLiteralString(agentType.getGender()));
		agent.setRdaGr2ProfessionOrOccupation(SolrUtils
				.getResourceOrLiteralString(agentType
						.getProfessionOrOccupation()));
		agent.setNote(SolrUtils.literalListToArray(agentType.getNoteList()));

		if (agentType.getPrefLabelList() != null) {
			Map<String, String> prefLabelMongo = new HashMap<String, String>();
			for (PrefLabel prefLabelJibx : agentType.getPrefLabelList()) {
				if (prefLabelJibx.getLang() != null) {
					prefLabelMongo.put(prefLabelJibx.getLang().getLang(),
							prefLabelJibx.getString());
				} else {
					prefLabelMongo.put("def", prefLabelJibx.getString());
				}
			}
			agent.setPrefLabel(prefLabelMongo);
		}

		if (agentType.getAltLabelList() != null) {
			Map<String, String> altLabelMongo = new HashMap<String, String>();
			for (AltLabel altLabelJibx : agentType.getAltLabelList()) {
				if (altLabelJibx.getLang() != null) {
					altLabelMongo.put(altLabelJibx.getLang().getLang(),
							altLabelJibx.getString());
				} else {
					altLabelMongo.put("def", altLabelJibx.getString());
				}
			}
			agent.setAltLabel(altLabelMongo);
		}

		agent.setBegin(SolrUtils.getLiteralString(agentType.getBegin()));
		agent.setEnd(SolrUtils.getLiteralString(agentType.getEnd()));
		agent.setOwlSameAs(SolrUtils.resourceListToArray(agentType.getSameAList()));
		return agent;
	}
}
