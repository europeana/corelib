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

import eu.europeana.corelib.definitions.solr.DocType;

/**
 * Interface for the BriefBean. The BriefBean contains the fields exposed by the
 * SOLR engine for presenting each record in the result and search page.
 * 
 * 
 * @author Yorgos Mamakis <yorgos.mamakis@kb.nl>
 */
public interface BriefBean extends IdBean {

	String[] getTitle();

	String[] getEdmObject();

	String[] getYear(); // YEAR copyfield dc_date

	String[] getProvider(); // PROVIDER copyfield edm_provider

	String[] getDataProvider(); // DATA_PROVIDER copyfield edm_dataProvider

	String[] getLanguage(); // LANGUAGE copyfield dc_language

	DocType getType(); // TYPE copyfield edm_type

	// here the dcterms namespaces starts

	String[] getDcTermsSpatial();

	// Ranking and Enrichment terms

	int getEuropeanaCompleteness();

	String[] getEdmPlace();

	List<Map<String, String>> getEdmPlaceLabel();

	Float[] getEdmPlaceLatitude();

	Float[] getEdmPlaceLongitude();

	String[] getEdmTimespan();

	List<Map<String, String>> getEdmTimespanLabel();

	String[] getEdmTimespanBegin();

	String[] getEdmTimespanEnd();

	String[] getEdmAgent();

	List<Map<String, String>> getEdmAgentLabel();

	String getFullDocUrl();

	String[] getDcTermsHasPart();

	String[] getDcCreator();

	String[] getDcContributor();
}
