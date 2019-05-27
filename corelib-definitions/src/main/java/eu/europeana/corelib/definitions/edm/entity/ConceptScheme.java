package eu.europeana.corelib.definitions.edm.entity;
/**
 * Interface for the ConceptScheme - Not Currently used
 * @author Yorgos.Mamakis@ kb.nl
 *
 */
public interface ConceptScheme extends AbstractEdmEntity {
	/**
	 * 
	 * @return the dc:title for the conceptscheme
	 */
	String[] getDcTitle();
	
	/**
	 * sets the dc:title for the conceptscheme
	 * @param dcTitle
	 */
	void setDcTitle(String[] dcTitle);
	
	/**
	 * 
	 * @return the dc:creator for the conceptscheme
	 */
	String[] getDcCreator();
	
	/**
	 * sets the dc:creator for the conceptscheme
	 * @param dcCreator
	 */
	void setDcCreator(String[] dcCreator);
	
	/**
	 * 
	 * @return the skos:hasTopConceptOf for the conceptscheme
	 */
	String[] getHasTopConceptOf();
	
	/**
	 * sets the skos:hasTopConceptOf for the conceptscheme
	 * @param hasTopConceptOf
	 */
	void setHasTopConceptOf(String[] hasTopConceptOf);
	
	/**
	 * 
	 * @return the skos:note for the conceptscheme
	 */
	String[] getNote();
	
	/**
	 * sets the skos:note for the conceptscheme
	 * @param note
	 */
	void setNote(String[] note);
}
