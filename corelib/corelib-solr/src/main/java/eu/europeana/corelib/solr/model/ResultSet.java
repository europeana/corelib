package eu.europeana.corelib.solr.model;

import java.util.List;

import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.SpellCheckResponse;

import eu.europeana.corelib.solr.bean.IdBean;

public class ResultSet<T extends IdBean> {
	
	private Query query;

	private List<T> results;
	
	private List<FacetField> facetFields;
	
	private SpellCheckResponse spellcheck;
	
	// statistics
	
	private long resultSize;
	
	private int searchTime;

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

	public int getSearchTime() {
		return searchTime;
	}

	public ResultSet<T> setSearchTime(int searchTime) {
		this.searchTime = searchTime;
		return this;
	}
	
}
