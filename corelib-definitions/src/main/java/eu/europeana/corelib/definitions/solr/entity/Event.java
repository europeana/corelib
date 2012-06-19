package eu.europeana.corelib.definitions.solr.entity;


public interface Event extends ContextualClass {
	
	void setEdmHappenedAt(String[] edmHappenedAt);
	
	String[] getEdmHappenedAt();
	
	void setEdmOccuredAt(String[] edmOccuredAt);
	
	String[] getEdmOccuredAt();
	
	String[] getSameAs();
	
	void setSameAs(String[] sameAs);
	
	
	String[] getDcIdentifier();
	
	void setDcIdentifier(String[] dcIdentifier);
	
	String[] getDctermsHasPart();
	
	void setDctermsHasPart(String[] dctermsHasPart);
	
	String[] getDctermsIsPartOf();
	
	void setDctermsIsPartOf(String[] dctermsIsPartOf);
	
	String[] getCrmP120FOccursBefore();
	
	void setCrmP120FOccursBefore(String[] crmP120FOccursBefore);
	
	String[] getEdmHasType();
	
	void setEdmHasType(String[] edmHasType);
	
	String[] getEdmIsRelatedTo();
	
	void setEdmIsRelatedTo(String[] edmIsRelatedTo);
}
