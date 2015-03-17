/*
 * Copyright 2007-2012 The Europeana Foundation
 *
 *  Licenced under the EUPL, Version 1.1 (the "Licence") and subsequent versions as approved
 *  by the European Commission;
 *  You may not use this work except in compliance with the Licence.
 * 
 *  You may obtain a copy of the Licence at:
 *  http://joinup.ec.europa.eu/software/page/eupl
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under
 *  the Licence is distributed on an "AS IS" basis, without warranties or conditions of
 *  any kind, either express or implied.
 *  See the Licence for the specific language governing permissions and limitations under
 *  the Licence.
 */

package eu.europeana.corelib.definitions.edm.beans;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Interface for the APIBean. The APIBean contains the fields exposed by the
 * SOLR engine for public access. NOTE: Draft to be crosschecked and validated
 * over time
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
