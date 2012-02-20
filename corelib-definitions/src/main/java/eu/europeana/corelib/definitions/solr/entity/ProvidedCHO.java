package eu.europeana.corelib.definitions.solr.entity;

public interface ProvidedCHO extends AbstractEdmEntity {

	
	/**
	 * Retrieve the owl:sameAs fields for a ProvidedCHO
	 * @return String array representing the owl:sameAs fields of a ProvidedCHO
	 */
	String[] getOwlSameAs();
	
	/**
	 * Retrieve the edm:isNextInSequence fields for a ProvidedCHO
	 * @return String representing the edm:isNextInSequence fields for a ProvidedCHO
	 */
	String getEdmIsNextInSequence();
	
	void setOwlSameAs(String[] owlSameAs);
	
	void setEdmIsNextInSequence(String edmIsNextInSequence);
	
	String getAbout();

	void setAbout(String about);

}
