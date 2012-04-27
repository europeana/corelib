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

package eu.europeana.corelib.solr.model;

import java.util.List;

import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.SpellCheckResponse;

import eu.europeana.corelib.definitions.solr.beans.IdBean;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public class ResultSet<T extends IdBean> {
	
	private Query query;

	private List<T> results;
	
	private List<FacetField> facetFields;
	
	private SpellCheckResponse spellcheck;
	
	// statistics
	
	private long resultSize;
	
	private long searchTime;

	/**
	 * GETTERS & SETTTERS
	 */

	public List<T> getResults() {
		return results;
	}

	public ResultSet<T> setResults(List<T> results) {
		this.results = results;
		return this;
	}

	public Query getQuery() {
		return query;
	}

	public ResultSet<T> setQuery(Query query) {
		this.query = query;
		return this;
	}

	public List<FacetField> getFacetFields() {
		return facetFields;
	}

	public ResultSet<T> setFacetFields(List<FacetField> facetFields) {
		this.facetFields = facetFields;
		return this;
	}

	public SpellCheckResponse getSpellcheck() {
		return spellcheck;
	}

	public ResultSet<T> setSpellcheck(SpellCheckResponse spellcheck) {
		this.spellcheck = spellcheck;
		return this;
	}

	public long getResultSize() {
		return resultSize;
	}

	public ResultSet<T> setResultSize(long resultSize) {
		this.resultSize = resultSize;
		return this;
	}

	public long getSearchTime() {
		return searchTime;
	}

	public ResultSet<T> setSearchTime(long l) {
		this.searchTime = l;
		return this;
	}
	
}
