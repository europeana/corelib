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

import java.util.Date;

import eu.europeana.corelib.definitions.solr.DocType;

/**
 * Interface for the APIBean. The APIBean contains the fields exposed by the SOLR engine
 * for public access.   
 * NOTE: Draft to be crosschecked and validated over time
 * 
 * @author Gerald de Jong <geralddejong@gmail.com>
 * @author Sjoerd Siebinga <sjoerd.siebinga@gmail.com>
 * @author Yorgos Mamakis <yorgos.mamakis@ kb.nl>
 */

public interface ApiBean extends BriefBean {

    String[] getTitle();

    String[] getEdmObject();

    String[] getCreator(); //creator copyfields dc_creator, dc_contributor

    String[] getYear(); //YEAR copyfield dc_date

    String[] getProvider(); //PROVIDER copyfield edm_provider

    String[] getDataProvider(); //DATA_PROVIDER copyfield edm_dataProvider

    String[] getLanguage(); //LANGUAGE copyfield dc_language

    DocType getType(); //TYPE copyfield edm_type
    
    // here the dcterms namespaces starts

    String[] getDcTermsIsPartOf();

    String[] getDcTermsSpatial();

    // Ranking and Enrichment terms

    int getEuropeanaCompleteness();

    String[] getAggregationEdmRights();

    String[] getEdmPlace();

    String[] getEdmPlaceLabel();

    Float[] getEdmPlaceLatitude();

    Float[] getEdmPlaceLongitude();

    String[] getEdmTimespan();

    String[] getEdmTimespanLabel();

    Date[] getEdmTimespanBegin();

    Date[] getEdmTimespanEnd();

    String[] getEdmConcept(); //skos:concept 

    String[] getEdmConceptLabel(); //skos:concept prefLabel

    String[] getEdmConceptBroaderTerm(); //skos:concept broader

    String[] getEdmAgent();

    String[] getEdmAgentLabel();

	String getFullDocUrl();

	String[] getDcTermsHasPart();

	String[] getEdmPlaceAltLabel();

	String[] getEdmConceptBroaderLabel();

	String[] getEdmTimespanBroaderTerm();

	String[] getEdmTimespanBroaderLabel();

	String[] getEdmPlaceBroaderTerm();
}
