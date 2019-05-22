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
