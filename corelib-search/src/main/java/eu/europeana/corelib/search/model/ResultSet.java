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

package eu.europeana.corelib.search.model;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.SpellCheckResponse;

import eu.europeana.corelib.definitions.solr.model.Query;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public class ResultSet<T> {

	/**
	 * The request query object
	 */
	private Query query;

	/**
	 * The request query object
	 */
	private String solrQueryString;

	/**
	 * The list of result objects
	 */
	private List<T> results;

	/**
	 * The list of facets
	 */
	private List<FacetField> facetFields;

	/**
	 * The spellcheck component response
	 */
	private SpellCheckResponse spellcheck;

	/**
	 * The query facets response
	 */
	private Map<String, Integer> queryFacets;

	// statistics

	/**
	 * The total number of results
	 */
	private long resultSize;

	/**
	 * The time in millisecond how long the search has been taken
	 */
	private long searchTime;

	/**
	 * The current pagination cursor
	 */
	private String currentCursorMark;

	/**
	 * The next pagination cursor
	 */
	private String nextCursorMark;

	/**
	 * To store the concatenated actual sort fields
	 */
	private String sortField;



	/**
	 * GETTERS & SETTERS
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

	/**
	 * Gets the total number of results
	 * @return
	 */
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

	public Map<String, Integer> getQueryFacets() {
		return queryFacets;
	}

	public ResultSet<T> setQueryFacets(Map<String, Integer> queryFacets) {
		this.queryFacets = queryFacets;
		return this;
	}
	public String getCurrentCursorMark() {
		return currentCursorMark;
	}

	public void setCurrentCursorMark(String currentCursorMark) {
		this.currentCursorMark = currentCursorMark;
	}

	public String getNextCursorMark() {
		return nextCursorMark;
	}

	public void setNextCursorMark(String nextCursorMark) {
		this.nextCursorMark = nextCursorMark;
	}

	public String getSortField() { return sortField; }

	public void setSortField(String sortField) { this.sortField = sortField; }

	@Override
	public String toString() {
		return "ResultSet [query=" + query + ", results=" + results
				+ ", facetFields=" + facetFields + ", spellcheck=" + spellcheck
				+ ", resultSize=" + resultSize + ", searchTime=" + searchTime
				+ "]";
	}

	public String getSolrQueryString() {
		return solrQueryString;
	}

	public void setSolrQueryString(String solrQueryString) {
		this.solrQueryString = solrQueryString;
	}

}
