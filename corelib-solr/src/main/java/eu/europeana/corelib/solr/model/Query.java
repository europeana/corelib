package eu.europeana.corelib.solr.model;

public class Query {
	
	private String query;
	
	private String[] refinements;
	
	private int start;
	
	private int pageSize;

	/**
	 * GETTERS & SETTTERS
	 */

	public String getQuery() {
		return query;
	}

	public Query setQuery(String query) {
		this.query = query;
		return this;
	}

	public String[] getRefinements() {
		return refinements;
	}

	public Query setRefinements(String[] refinements) {
		this.refinements = refinements;
		return this;
	}

	public int getStart() {
		return start;
	}

	public Query setStart(int start) {
		this.start = start;
		return this;
	}

	public int getPageSize() {
		return pageSize;
	}

	public Query setPageSize(int pageSize) {
		this.pageSize = pageSize;
		return this;
	}

}
