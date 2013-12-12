package eu.europeana.corelib.definitions.solr.model;

import org.apache.commons.lang.StringUtils;

/**
 * Helper class to build Solr query facets
 */
public class QueryFacet {

	/**
	 * Identifier of the query (the {!id=} part)
	 */
	private String identifier;

	/**
	 * What other queries to exclude from this query (the {!ex=} part)
	 */
	private String exclusion;

	/**
	 * The Solr query
	 */
	private String query;

	/**
	 * Create a Query facet with a Solr query
	 * @param query
	 *   The solr query to run
	 */
	public QueryFacet(String query) {
		this.query = query;
	}

	/**
	 * Create a Query facet with a Solr query and an identifier
	 * @param query
	 *   The solr query to run
	 * @param identifier
	 *   Identifier of the query
	 */
	public QueryFacet(String query, String identifier) {
		this(query);
		this.identifier = identifier;
	}

	/**
	 * Create a Query facet with a Solr query, an identifier and an exclusion
	 * @param query
	 *   The solr query to run
	 * @param identifier
	 *   Identifier of the query
	 * @param exclusion
	 *   What other queries to exclude from this query
	 */
	public QueryFacet(String query, String identifier, String exclusion) {
		this(query, identifier);
		this.exclusion = exclusion;
	}

	public String getId() {
		return identifier;
	}

	public void setId(String id) {
		this.identifier = id;
	}

	public String getExclusion() {
		return exclusion;
	}

	public void setExclusion(String ex) {
		this.exclusion = ex;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getQueryFacetString() {
		StringBuilder sb = new StringBuilder();
		boolean hasId = StringUtils.isNotBlank(identifier);
		boolean hasEx = StringUtils.isNotBlank(exclusion);
		if (hasEx || hasId) {
			sb.append("{!");
			if (hasId) {
				sb.append("id=").append(identifier);
			}
			if (hasEx && hasId) {
				sb.append(" ");
			}
			if (hasEx) {
				sb.append("ex=").append(exclusion);
			}
			sb.append("}");
		}
		sb.append(query);
		return sb.toString();
	}
}
