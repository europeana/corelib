package eu.europeana.corelib.definitions.edm.entity;

import java.util.List;
import java.util.Map;

/**
 * EDM Timespan fields representation
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
public interface Timespan extends ContextualClass {

	/**
	 * Retrieve the edm:begin field of a Timespan
	 * 
	 * @return Stringrepresenting the edm:begin field of a timespan
	 */
	Map<String,List<String>> getBegin();

	/**
	 * Retrieve the edm:end field of a Timespan
	 * 
	 * @return String representing the edm:end field of a timespan
	 */
	Map<String,List<String>> getEnd();

	/**
	 * Retrieve the dcterms:isPartOf field of a Timespan
	 * 
	 * @return String array representing the dcterms:isPartOf fields of a
	 *         timespan
	 */
	Map<String,List<String>> getIsPartOf();

	/**
	 * Set the edm:begin field for a Timespan. It expects to find a date.
	 * 
	 * @param begin
	 *            the edm:begin field for a Timespan
	 */
	void setBegin(Map<String,List<String>> begin);

	/**
	 * Set the edm:end field for a Timespan. It expects to find a date.
	 * 
	 * @param end
	 *            the edm:end field for a Timespan
	 */
	void setEnd(Map<String,List<String>> end);

	/**
	 * Set the isPartOf fields for a Timespan.
	 * 
	 * @param isPartOf
	 *            A String array representing the isPartOf fields for a Timespan
	 */
	void setIsPartOf(Map<String,List<String>> isPartOf);
	
	/**
	 * sets the dcterms:hasPart for the edm:Timespan
	 * @param hasPart
	 */
	void setDctermsHasPart(Map<String,List<String>> hasPart);
	
	/**
	 * 
	 * @return the dcterms:hasPart for the edm:Timespan
	 */
	Map<String,List<String>> getDctermsHasPart();
	
	/**
	 * sets the owl:sameAs for the edm:Timespan
	 * @param owlSameAs
	 */
	void setOwlSameAs(String[] owlSameAs);
	
	/**
	 * 
	 * @return the owl:sameAs for the edm:Timespan
	 */
	String[] getOwlSameAs();
	

	
	
}
