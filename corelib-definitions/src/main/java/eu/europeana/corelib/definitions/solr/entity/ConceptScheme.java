package eu.europeana.corelib.definitions.solr.entity;

public interface ConceptScheme extends AbstractEdmEntity {
	String[] getDcTitle();
	
	void setDcTitle(String[] dcTitle);
	
	String[] getDcCreator();
	
	void setDcCreator(String[] dcCreator);
	
	String[] getHasTopConceptOf();
	
	void setHasTopConceptOf(String[] hasTopConceptOf);
	
	String[] getNote();
	
	void setNote(String[] note);
}
