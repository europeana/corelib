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

package eu.europeana.corelib.definitions.solr.beans;

import java.util.List;
import java.util.Map;

/**
 * Interface for the APIBean. The APIBean contains the fields exposed by the SOLR engine for public access. NOTE: Draft
 * to be crosschecked and validated over time
 * 
 * @author Yorgos Mamakis <yorgos.mamakis@ kb.nl>
 */
public interface ApiBean extends BriefBean {

	String[] getDctermsIsPartOf();

	String[] getEdmConcept(); // skos:concept

	List<Map<String, String>> getEdmConceptLabel(); // skos:concept prefLabel

	String[] getEdmConceptBroaderTerm(); // skos:concept broader

	List<Map<String, String>> getEdmPlaceAltLabel();

	List<Map<String, String>> getEdmConceptBroaderLabel();

	String[] getEdmTimespanBroaderTerm();

	List<Map<String, String>> getEdmTimespanBroaderLabel();

	String[] getEdmPlaceBroaderTerm();

	boolean[] getUgc();

	void setUgc(boolean[] ugc);

	String[] getCountry();

	void setCountry(String[] country);

	String[] getEuropeanaCollectionName();

	void setEuropeanaCollectionName(String[] europeanaCollectionName);

	void setDctermsIsPartOf(String[] dctermsIsPartOf);
}
