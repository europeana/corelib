package eu.europeana.corelib.definitions.solr.entity;

public interface ProvidedCHO extends AbstractEdmEntity {

	/**
	 * Retrieve the owl:sameAs fields for a ProvidedCHO
	 * 
	 * @return String array representing the owl:sameAs fields of a ProvidedCHO
	 */
	String[] getOwlSameAs();

	/**
	 * Retrieve the edm:isNextInSequence fields for a ProvidedCHO
	 * 
	 * @return String representing the edm:isNextInSequence fields for a
	 *         ProvidedCHO
	 */
	String getEdmIsNextInSequence();

	/**
	 * Set the owl:sameAs fields for a ProvidedCHO
	 * 
	 * @param owlSameAs
	 *            String array representing the owl:sameAs fields of a
	 *            ProvidedCHO
	 */
	void setOwlSameAs(String[] owlSameAs);

	/**
	 * Set the edm:isNextInSequence fields for a ProvidedCHO
	 * 
	 * @param edmIsNextInSequence
	 *            String representing the edm:isNextInSequence fields for a
	 *            ProvidedCHO
	 */
	void setEdmIsNextInSequence(String edmIsNextInSequence);

}
