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

import java.io.IOException;
import java.net.MalformedURLException;
import eu.europeana.corelib.definitions.jibx.AgentType;
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
