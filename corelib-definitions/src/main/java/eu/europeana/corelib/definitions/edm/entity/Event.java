package eu.europeana.corelib.definitions.edm.entity;

/**
 * Interface for the edm:Event - Not yet implemented
 * @author Yorgos.Mamakis@ kb.nl
 *
 */
public interface Event extends ContextualClass {
	/**
	 * edm:happenedAt
	 * @param edmHappenedAt
	 */
	void setEdmHappenedAt(String[] edmHappenedAt);
	
	/**
	 * 
	 * @return
	 */
	String[] getEdmHappenedAt();
	
	/**
	 * edm:occuredAt
	 * @param edmOccuredAt
	 */
	void setEdmOccuredAt(String[] edmOccuredAt);
	
	/**
	 * 
	 * @return
	 */
	String[] getEdmOccuredAt();
	
	/**
	 * owl:sameAs
	 * @return
	 */
	String[] getSameAs();
	
	/**
	 * 
	 * @param sameAs
	 */
	void setSameAs(String[] sameAs);
	
	/**
	 * dc:identifier
	 * @return
	 */
	String[] getDcIdentifier();
	
	/**
	 * 
	 * @param dcIdentifier
	 */
	void setDcIdentifier(String[] dcIdentifier);
	
	/**
	 * dcterms:hasPart
	 * @return
	 */
	String[] getDctermsHasPart();
	
	/**
	 * 
	 * @param dctermsHasPart
	 */
	void setDctermsHasPart(String[] dctermsHasPart);
	
	/**
	 * dcterms:isPartOf
	 * @return
	 */
	String[] getDctermsIsPartOf();
	
	/**
	 * 
	 * @param dctermsIsPartOf
	 */
	void setDctermsIsPartOf(String[] dctermsIsPartOf);
	
	/**
	 * crmp120f:occursBefore
	 * @return
	 */
	String[] getCrmP120FOccursBefore();
	
	/**
	 * 
	 * @param crmP120FOccursBefore
	 */
	void setCrmP120FOccursBefore(String[] crmP120FOccursBefore);
	
	/**
	 * edm:hasType
	 * @return
	 */
	String[] getEdmHasType();
	
	/**
	 * 
	 * @param edmHasType
	 */
	void setEdmHasType(String[] edmHasType);
	
	/**
	 * edm:isRelatedTo
	 * @return
	 */
	String[] getEdmIsRelatedTo();
	
	/**
	 * 
	 * @param edmIsRelatedTo
	 */
	void setEdmIsRelatedTo(String[] edmIsRelatedTo);
}
