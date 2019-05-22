package eu.europeana.corelib.definitions.edm.beans;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * The ApiBean contains the fields exposed by the SOLR engine by default (when using the 'standard' profile).
 *
 * 
 * @author Yorgos Mamakis <yorgos.mamakis@ kb.nl>
 */
public interface ApiBean extends BriefBean {

	/**
	 * 
	 * @return dcterms:isPartOf string array
	 */
	String[] getDctermsIsPartOf();

	/**
	 * 
	 * @return skos:concept string array
	 */
	String[] getEdmConcept(); // skos:concept

	/**
	 * 
	 * @return skos:prefLabel multilingual list for a skos:concept
	 */
	List<Map<String, String>> getEdmConceptLabel(); // skos:concept prefLabel

	/**
	 * 
	 * @return skos:broader for a skos:concept
	 */
	String[] getEdmConceptBroaderTerm(); // skos:concept broader

	/**
	 * 
	 * @return skos:altLabel multilingual list for an edm:place
	 */
	List<Map<String, String>> getEdmPlaceAltLabel();

	/**
	 * 
	 * @return skos:broaderLabel for a skos:concept
	 */
	List<Map<String, String>> getEdmConceptBroaderLabel();

	/**
	 * 
	 * @return skos:broader for an edm:timespan
	 */
	String[] getEdmTimespanBroaderTerm();

	/**
	 * 
	 * @return skos:broaderLabel for an edm:timespan
	 */
	List<Map<String, String>> getEdmTimespanBroaderLabel();

	/**
	 * 
	 * @return skos:broaderTerm for an edm:place
	 */
	String[] getEdmPlaceBroaderTerm();

	/**
	 * 
	 * @return edm:ugc
	 */
	boolean[] getUgc();

	/**
	 * Sets the edm:ugc
	 * 
	 * @param ugc
	 */
	void setUgc(boolean[] ugc);

	/**
	 * 
	 * @return Country
	 */
	String[] getCountry();

	/**
	 * Set the Country field
	 * 
	 * @param country
	 */
	void setCountry(String[] country);

	/**
	 * 
	 * @return europeana_collectionName field
	 */
	String[] getEuropeanaCollectionName();

	/**
	 * sets the europeana_collectionName field
	 */
	void setEuropeanaCollectionName(String[] europeanaCollectionName);

	/**
	 * sets the dcterms:isPartOf
	 * 
	 * @param dctermsIsPartOf
	 */
	void setDctermsIsPartOf(String[] dctermsIsPartOf);

	/**
	 * The date the record was created
	 * 
	 * @return
	 */
	Date getTimestampCreated();

	/**
	 * The date the record was updated
	 * 
	 * @return
	 */
	Date getTimestampUpdate();

	/**
	 * Language aware version of the skos:Concept-skos:prefLabel field
	 * 
	 * @return
	 */
	Map<String, List<String>> getEdmConceptPrefLabelLangAware();

	/**
	 * Language aware version of the skos:Concept-skos:broader field
	 * 
	 * @return
	 */
	Map<String, List<String>> getEdmConceptBroaderLabelLangAware();

	/**
	 * Language aware version of the edm:Place-skos:altLabel field
	 * 
	 * @return
	 */
	Map<String, List<String>> getEdmPlaceAltLabelLangAware();

}
