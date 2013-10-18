package eu.europeana.corelib.definitions.solr.model;

import org.apache.commons.lang.StringUtils;

public class QueryFacet {

	private String id;
	private String ex;
	private String query;

	public QueryFacet(String query) {
		this.query = query;
	}

	public QueryFacet(String query, String id) {
		this(query);
		this.id = id;
	}

	public QueryFacet(String query, String id, String ex) {
		this(query, id);
		this.ex = ex;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEx() {
		return ex;
	}

	public void setEx(String ex) {
		this.ex = ex;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getQueryFacetString() {
		StringBuilder sb = new StringBuilder();
		boolean hasId = StringUtils.isNotBlank(id);
		boolean hasEx = StringUtils.isNotBlank(ex);
		if (hasEx || hasId) {
			sb.append("{!");
			if (hasId) {
				sb.append("id=").append(id);
			}
			if (hasEx && hasId) {
				sb.append(" ");
			}
			if (hasEx) {
				sb.append("ex=").append(ex);
			}
			sb.append("}");
		}
		sb.append(query);
		return sb.toString();
	}
}
