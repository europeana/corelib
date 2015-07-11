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

package eu.europeana.corelib.definitions.solr;

/**
 * @author Yorgos.Mamakis@ kb.nl
 */
public enum QueryType {

	ADVANCED("advanced"), 
	MORE_LIKE_THIS("moreLikeThis"),
	SIMPLE("search"),
	BM25F("bm25f");

	private String queryType;

	QueryType(String queryType) {
		this.queryType = queryType;
	}

	@Override
	public String toString() {
		return this.queryType;
	}
}
