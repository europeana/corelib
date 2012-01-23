package eu.europeana.corelib.solr.model;

import java.util.List;

import eu.europeana.corelib.solr.bean.IdBean;

public class ResultSet<T extends IdBean> {
	
	private Query query;

	private List<T> results;

	/**
	 * GETTERS & SETTTERS
	 */

	public List<T> getResults() {
		return results;
	}

	public void setResults(List<T> results) {
		this.results = results;
	}

	public Query getQuery() {
		return query;
	}

	public void setQuery(Query query) {
		this.query = query;
	}
	
}
