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
package eu.europeana.corelib.tools.lookuptable;

/**
 *
 * @author Georgios Markakis <gwarkx@hotmail.com>
 * @since 3 Oct 2012
 */
public class LookupResult {

	private String europeanaID;
	
	private LookupState state;
	
	/**
	 * @return the europeanaID
	 */
	public String getEuropeanaID() {
		return europeanaID;
	}

	/**
	 * @param europeanaID the europeanaID to set
	 */
	public void setEuropeanaID(String europeanaID) {
		this.europeanaID = europeanaID;
	}

	/**
	 * 
	 */
	public LookupResult() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the state
	 */
	public LookupState getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(LookupState state) {
		this.state = state;
	}

}
