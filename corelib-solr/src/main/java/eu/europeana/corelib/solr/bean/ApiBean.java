/*
 * Copyright 2007 EDL FOUNDATION
 *
 * Licensed under the EUPL, Version 1.0 or? as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 * you may not use this work except in compliance with the
 * Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://ec.europa.eu/idabc/eupl
 *
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
 */

package eu.europeana.corelib.solr.bean;

import java.util.Date;

import eu.europeana.corelib.definitions.solr.DocType;

/**
 * Interface for the APIBean. The APIBean contains the fields exposed by the SOLR engine
 * for public access.   
 * NOTE: Draft to be crosschecked and validated over time
 * 
 * @author Gerald de Jong <geralddejong@gmail.com>
 * @author Sjoerd Siebinga <sjoerd.siebinga@gmail.com>
 * @author Yorgos Mamakis <yorgos.mamakis@kb.nl>
 */

public interface ApiBean extends BriefBean {

    String getTitle();

    String[] getEdmObject();

    String getCreator(); //creator copyfields dc_creator, dc_contributor

    String getYear(); //YEAR copyfield dc_date

    String getProvider(); //PROVIDER copyfield edm_provider

    String getDataProvider(); //DATA_PROVIDER copyfield edm_dataProvider

    String getLanguage(); //LANGUAGE copyfield dc_language

    DocType getType(); //TYPE copyfield edm_type
    
    // here the dcterms namespaces starts

    String[] getDcTermsIsPartOf();

    String[] getDcTermsSpatial();

    // Ranking and Enrichment terms

    int getEuropeanaCompleteness();

    String[] getAggregationEdmRights();

    String[] getEdmPlaceTerm();

    String[] getEdmPlaceLabel();

    String[] getEdmPlaceBroaderTerm(); //dcterms:isPartOf of the Place 

    String[] getEdmPlaceBroaderLabel(); //dcterms:isPartOf of the Place

    float getEdmPlaceLatitude();

    float getEdmPlaceLongitude();

    String[] getEdmTimespanTerm();

    String[] getEdmTimespanLabel();

    String[] getEdmTimespanBroaderTerm(); //ts_dcterms_isPartOf

    String[] getEdmPeriodBroaderLabel();

    Date getEdmPeriodBegin();

    Date getEdmPeriodEnd();

    String[] getEdmConceptTerm(); //skos:concept 

    String[] getEdmConceptLabel(); //skos:concept prefLabel

    String[] getEdmConceptBroaderTerm(); //skos:concept broader

    String[] getEdmConceptBroaderLabel(); //skos:concept prefLabel (broader)

    String[] getEdmAgentTerm();

    String[] getEdmAgentLabel();
}
