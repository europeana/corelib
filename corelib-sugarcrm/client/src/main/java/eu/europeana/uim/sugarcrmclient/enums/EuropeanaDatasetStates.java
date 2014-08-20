/*
 * Copyright 2007 EDL FOUNDATION
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 * you may not use this work except in compliance with the
 * Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://ec.europa.eu/idabc/eupl
 *
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
 */
package eu.europeana.uim.sugarcrmclient.enums;

/**
 * This enumeration holds the State Descriptions for EuropeanaDatasets in Sugar CRM
 * @author Georgios Markakis
 */
public enum EuropeanaDatasetStates {

	OAI_PMH_TESTING("Prospecting", "OAI-PHM testing"),
	OAI_PMH_SENT_TO_ORG("Qualification", "OAI-PHM test sent to Organisation"),
	READY_FOR_HARVESTING("Needs%Analysis", "Ready for Harvesting"),
	MAPPING_AND_NORMALIZATION("Value%Proposition", "Mapping and Normalization"),
	READY_FOR_REPLICATION("Id.%Decision%Makers", "Ready for Replication"),
	ONGOING_SCHEDULED_UPDATES("Updates", "Ongoing scheduled updates"),
	INGESTION_COMPLETE("Closed%Won", "Ingestion complete"),
	DISABLED_AND_REPLACED("Replaced", "Disabled and Replaced"),
	HARVESTING_PENDING("Harvesting%Pending", "Harvesting Pending"),
	;

	private final String sysId;
	private final String description;

	EuropeanaDatasetStates(String sysId, String description) {
		this.sysId = sysId;
		this.description = description;
	}

	public String getSysId() {
		return sysId;
	}

	public String getDescription() {
		return description;
	}
}
