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

package eu.europeana.corelib.search.query;

/**
 * Enumeration holding the MoreLikeThis fields
 * 
 * @author Yorgos.Mamakis@ kb.nl
 */
@Deprecated
public enum MoreLikeThis {

	// CREATOR("creator"),
	// DESCRIPTION("description"),
	// SUBJECT("subject"),
	// CONTRIBUTOR("contributor"),
	TITLE("title"),
	WHO("who"),
	WHAT("what"),
	// WHERE("where"),
	// WHEN("when")
	;

	private String mlt;

	MoreLikeThis(String mlt) {
		this.mlt = mlt;
	}

	/**
	 * Return the MoreLikeThis field
	 */
	public String toString() {
		return this.mlt;
	}
}
