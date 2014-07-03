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
package eu.europeana.uim.sugarcrmclient.enums;

/**
 * @author Georgios Markakis (gwarkx@hotmail.com)
 *
 * Nov 13, 2013
 */
public enum EuropeanaOrgRole {

	Data_Aggregator("D", "Data Aggregator"),
	Content_Provider("P", "Content Provider"),
	Technology_Partner("T", "Technology Partner"),
	Financial_Partner("F", "Financial Partner / Sponsor"),
	Policy_Maker("PM", "Policy Maker"),
	Think_Tank("C", "Think Tank/ Knowledge Organisation / Creative Industry"),
	Other("U", "Other")
	;

	private final String sysId;
	private final String description;

	EuropeanaOrgRole(String sysId, String description) {
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
