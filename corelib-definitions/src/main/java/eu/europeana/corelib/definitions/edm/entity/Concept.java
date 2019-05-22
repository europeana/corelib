package eu.europeana.corelib.definitions.edm.entity;

import java.util.List;
import java.util.Map;

/**
 * EDM Concept fields representation
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
public interface Concept extends ContextualClass {

	/**
	 * Retrieves the broader terms of a concept
	 * 
	 * @return A String array with all the broader terms of a concept
	 */
	String[] getBroader();

	/**
	 * Set the broader terms for a concept
	 * 
	 * @param broader
	 *            A string array with all the broader terms of a concept
	 */
	void setBroader(String[] broader);
	
	/**
	 * Gets the skos:hiddenLabel for a skos:concept
	 */
	@Override
	Map<String,List<String>> getHiddenLabel();
	
	/**
	 * Sets the skos:hiddenLabel for a skos:concept
	 */
	@Override
	void setHiddenLabel(Map<String,List<String>> hiddenLabel);
	
	/**
	 * 
	 * @return the skos:narrower for a skos:Concept
	 */
	String[] getNarrower();
	
	/**
	 * Sets the skos:narrower for a skos:Concept
	 * @param narrower
	 */
	void setNarrower(String[] narrower);
	
	/**
	 * 
	 * @return the skos:related for a skos:Concept
	 */
	String[] getRelated();
	
	/**
	 * Sets the skos:related for a skos:Concept
	 * @param related
	 */
	void setRelated(String[] related);
	
	/**
	 * 
	 * @return the skos:broadMatch for a skos:Concept
	 */
	String[] getBroadMatch();
	
	/**
	 * sets the skos:broadMatch for a skos:Concept
	 * @param broadMatch
	 */
	void setBroadMatch(String[] broadMatch);
	
	/**
	 * 
	 * @return the skos:narrowMatch for a skos:Concept
	 */
	String[] getNarrowMatch();
	
	/**
	 * sets the skos:narrowMatch for a skos:Concept
	 * @param narrowMatch
	 */
	void setNarrowMatch(String[] narrowMatch);
	
	/**
	 * 
	 * @return the skos:relatedMatch for a skos:Concept
	 */
	String[] getRelatedMatch();
	
	/**
	 * Sets the skos:relatedMatch for a skos:Concept
	 * @param relatedMatch
	 */
	void setRelatedMatch(String[] relatedMatch);
	
	/**
	 * 
	 * @return the skos:exactMatch for a skos:Concept
	 */
	String[] getExactMatch();
	
	/**
	 * Sets the skos:exactMatch for a skos:Concept
	 * @param exactMatch
	 */
	void setExactMatch(String[] exactMatch);
	
	/**
	 * 
	 * @return the skos:closeMatch for a skos:Concept
	 */
	String[] getCloseMatch();
	
	/**
	 * Sets the skos:closeMatch for a skos:Concept
	 * @param closeMatch
	 */
	void setCloseMatch(String[] closeMatch);
	
	/**
	 * 
	 * @return the skos:notation for a skos:Concept
	 */
	Map<String,List<String>> getNotation();
	
	/**
	 * Sets the skos:notation for a skos:Concept
	 * @param notation
	 */
	void setNotation(Map<String,List<String>> notation);
	
	/**
	 * 
	 * @return the skos:inScheme for a skos:Concept
	 */
	String[] getInScheme();
	
	/**
	 * Sets the skos:inScheme for a skos:Concept
	 * @param inScheme
	 */
	void setInScheme(String[] inScheme);
}
