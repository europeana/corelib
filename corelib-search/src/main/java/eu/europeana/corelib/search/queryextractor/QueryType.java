package eu.europeana.corelib.search.queryextractor;

public enum QueryType {

	TERM("TermQuery"),
	PHRASE("PhraseQuery"),
	BOOLEAN("BooleanQuery"),
	BOOLEAN_OR("BooleanQuery.OR"),
	BOOLEAN_AND("BooleanQuery.AND"),
	BOOLEAN_NOT("BooleanQuery.NOT"),
	PREFIX("PrefixQuery"),
	FUZZY("FuzzyQuery"),
	TERMRANGE("TermRangeQuery"),
	MATCHALLDOCS("MatchAllDocsQuery");

	private String type;

	QueryType(String type) {
		this.type = type;
	}

	@Override
	public String toString(){
		return type;
	}
}
