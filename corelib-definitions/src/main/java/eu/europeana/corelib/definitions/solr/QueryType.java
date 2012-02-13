package eu.europeana.corelib.definitions.solr;

public enum QueryType {
	ADVANCED("advanced"),
	MORE_LIKE_THIS("moreLikeThis");
	
	String queryType;
	private QueryType(String queryType){
		this.queryType = queryType;
	}
	
	public String toString(){
		return this.queryType;
	}
}
