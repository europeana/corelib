package eu.europeana.corelib.definitions.solr;
/**
 * Check if needed
 * @author Yorgos.Mamakis@ kb.nl
 *
 */
public enum Facets {
	UGC("UGC"), 
	LANGUAGE("LANGUAGE"), 
	TYPE("TYPE"), 
	YEAR("YEAR"),
	PROVIDER("PROVIDER"),
	COUNTRY("COUNTRY"),
	RIGHTS("RIGHTS");
	
	private String facet;
	private Facets(String facets){
		this.facet = facets;
	}
	
	public String toString(){
		return facet;
	}
}
