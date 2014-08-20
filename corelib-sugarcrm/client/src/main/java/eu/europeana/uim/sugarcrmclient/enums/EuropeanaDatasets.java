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
 * Enumerations holding the names of the system modules in sugarCRM currently being used
 * 
 * @author Georgios Markakis
 */
public enum EuropeanaDatasets {

	ORGANIZATIONS("Accounts"),
	DATASETS("Opportunities");

	private final String sysId;

	/**
	 * @param sysId
	 */
	EuropeanaDatasets(String sysId){
		this.sysId = sysId;
	}

	/**
	 * @return
	 */
	public String getSysId() {
		return sysId;
	}
}
