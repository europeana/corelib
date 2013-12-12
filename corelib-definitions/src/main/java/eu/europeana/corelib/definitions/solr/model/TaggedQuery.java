package eu.europeana.corelib.definitions.solr.model;

public class TaggedQuery {

	private String tag;
	private String query;

	public TaggedQuery(String tag, String query) {
		super();
		this.tag = tag;
		this.query = query;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{!tag=").append(tag).append("}");
		sb.append(query);
		return sb.toString();
	}

}
