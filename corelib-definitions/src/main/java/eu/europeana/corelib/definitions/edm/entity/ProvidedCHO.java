package eu.europeana.corelib.definitions.edm.entity;

/**
 * Provided Cultural Heritage Object
 * @author Yorgos.Mamakis@ kb.nl
 */
public interface ProvidedCHO extends AbstractEdmEntity {

	/**
	 * Retrieve the owl:sameAs fields for a ProvidedCHO
	 * 
	 * @return String array representing the owl:sameAs fields of a ProvidedCHO
	 */
	String[] getOwlSameAs();

	/**
	 * Set the owl:sameAs fields for a ProvidedCHO
	 * 
	 * @param owlSameAs
	 *            String array representing the owl:sameAs fields of a
	 *            ProvidedCHO
	 */
	void setOwlSameAs(String[] owlSameAs);
}
