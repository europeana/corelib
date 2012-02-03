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

package eu.europeana.corelib.solr.bean.impl;

import java.util.Date;

import org.apache.solr.client.solrj.beans.Field;

import eu.europeana.corelib.definitions.solr.DocType;
import eu.europeana.corelib.definitions.solr.beans.ApiBean;

/**
 * @see eu.europeana.corelib.definitions.solr.beans.ApiBean
 * 
 * @author Yorgos.Mamakis@kb.nl
 *
 */
public class ApiBeanImpl extends BriefBeanImpl implements ApiBean {

	

    
    int index;
    String fullDocUrl;
    int score;
    String debugQuery;

    @Field("europeana_collectionName")
    String[] europeanaCollectionName;

    @Field("PROVIDER")
    String[] provider;

    @Field("edm_dataProvider")
    String[] edmDataProvider;

    @Field("edm_object")
    String[] edmObject;

    @Field("europeana_completeness")
    int europeanaCompleteness;

    @Field("edm_rights")
    String[] edmRights;

    @Field("COMPLETENESS")
    String[] completeness;

    @Field("COUNTRY")
    String[] country;

    @Field("TYPE")
    String[] docType;

    @Field("LANGUAGE")
    String[] language;

    @Field("YEAR")
    String[] year;

    @Field("RIGHTS")
    String[] rights;

    @Field("UGC")
    String[] ugc;

    @Field
    String[] title;

    @Field("solr_prefLabel.en")
    String[] prefLabelEn;

    @Field("solr_prefLabel.ru")
    String[] prefLabelRu;

    @Field("creator")
    String[] creator;
    
    String[] recordHashFirstSix;

    @Field("edm_place")
    String[] edmPlace;

    @Field("edm_place_pref_label")
    String[] edmPlacePrefLabel;

    @Field("edm_place_broader_term")
    String[] enrichmentPlaceBroaderTerm;

    @Field("edm_place_alt_label")
    String[] edmPlaceAltLabel;

    @Field("edm_place_latitude")
    Float[] edmPlaceLatitude;

    @Field("edm_place_longitude")
    Float[] edmPlaceLongitude;

    @Field("edm_timespan")
    String[] edmTimespan;

    @Field("edm_timespan_label")
    String[] edmTimespanLabel;

    @Field("edm_timespan_broader_term")
    String[] edmTimespanBroaderTerm;

    @Field("edm_timespan_broader_label")
    String[] edmTimespanBroaderLabel;

    @Field("edm_timespan_begin")
    Date[] edmTimespanBegin;

    @Field("edm_timespan_end")
    Date[] edmTimespanEnd;

    @Field("edm_concept")
    String[] edmConceptTerm;

    @Field("edm_concept_label")
    String[] edmConceptPrefLabel;

    @Field("edm_concept_broader")
    String[] edmConceptBroaderTerm;

    @Field("edm_concept_broader_label")
    String[] edmConceptBroaderLabel;

    @Field("edm_agent")
    String[] edmAgentTerm;

    @Field("edm_agent_label")
    String[] edmAgentLabel;

    @Field("dcterms_hasPart")
    String[] dctermsHasPart;
    
    @Field("dcterms_isPartOf")
    String[] dctermsIsPartOf;

    @Field("dcterms_spatial")
    String[] dctermsSpatial;


    @Override
    public String getFullDocUrl() {
        return fullDocUrl;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public String[] getTitle() {
        return this.title;
    }

    @Override
    public String[] getEdmObject() { //was getThumbnails
        return this.edmObject;
    }

    @Override
    public String[] getCreator() {
        return this.creator;
    }

    @Override
    public String[] getYear() {
        return this.year;
    }

    @Override
    public String[] getProvider() {
        return this.provider;
    }

    @Override
    public String[] getLanguage() {
        return this.language;
    }

    @Override
    public DocType getType() {
        return DocType.get(docType);
    }

    public String[] getRights() {
        return this.rights;
    }

    @Override
    public String[] getDataProvider() {
        return this.edmDataProvider;
    }

    @Override
    public int getEuropeanaCompleteness() {
        return europeanaCompleteness;
    }
    
    @Override
    public String[] getEdmPlace() {
        return this.edmPlace;
    }

    @Override
    public String[] getEdmPlaceBroaderTerm() {
        return enrichmentPlaceBroaderTerm;
    }

    @Override
    public String[] getEdmPlaceAltLabel() {
        return edmPlaceAltLabel;
    }

    @Override
    public String[] getEdmPlaceLabel() {
        return edmPlacePrefLabel;
    }

    @Override
    public Float[] getEdmPlaceLatitude() {
        return edmPlaceLatitude;
    }

    @Override
    public Float[] getEdmPlaceLongitude() {
        return edmPlaceLongitude;
    }

    @Override
    public String[] getEdmTimespan() {     
        return edmTimespan;
    }

    @Override
    public String[] getEdmTimespanLabel() {
        return edmTimespanLabel;
    }

    @Override
    public String[] getEdmTimespanBroaderTerm() {
        return edmTimespanBroaderTerm;
    }

    @Override
    public String[] getEdmTimespanBroaderLabel() {
        return edmTimespanBroaderLabel;
    }

    @Override
    public Date[] getEdmTimespanBegin() {
        return edmTimespanBegin;
    }

    @Override
    public Date[] getEdmTimespanEnd() {
        return edmTimespanEnd;
    }

   @Override
    public String[] getEdmConcept() {
        return edmConceptTerm;
    }

    @Override
    public String[] getEdmConceptLabel() {
        return edmConceptPrefLabel;
    }

    @Override
    public String[] getEdmConceptBroaderTerm() {
        return edmConceptBroaderTerm;
    }

    @Override
    public String[] getEdmConceptBroaderLabel() {
        return edmConceptBroaderLabel;
    }

    @Override
    public String[] getEdmAgent() {
        return edmAgentTerm;
    }

    @Override
    public String[] getEdmAgentLabel() {
        return edmAgentLabel;
    }
    
    @Override
    public String[] getAggregationEdmRights() {
        return this.edmRights;
    }
    
    @Override
    public String[] getDcTermsHasPart() {
        return this.dctermsHasPart;
    }

    @Override
    public String[] getDcTermsIsPartOf() {
        return this.dctermsIsPartOf;
    }

    @Override
    public String[] getDcTermsSpatial() {
        return this.dctermsSpatial;
    }

}
