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

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.beans.Field;

import eu.europeana.corelib.definitions.solr.beans.ApiBean;

/**
 * @see eu.europeana.corelib.definitions.solr.beans.ApiBean
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
public class ApiBeanImpl extends BriefBeanImpl implements ApiBean {

	@Field("edm_concept")
	private String[] edmConceptTerm;

	@Field("edm_concept_label")
	private List<Map<String, String>> edmConceptPrefLabel;

	@Field("edm_concept_broader")
	private String[] edmConceptBroaderTerm;

	@Field("edm_concept_broader_label")
	private List<Map<String, String>> edmConceptBroaderLabel;

	@Field("edm_timespan_broader_term")
	private String[] edmTimespanBroaderTerm;

	@Field("edm_timespan_broader_label")
	private List<Map<String, String>> edmTimespanBroaderLabel;

	@Field("pl_prefLabel.en")
	private String[] prefLabelEn;

	@Field("pl_prefLabel.ru")
	private String[] prefLabelRu;

	@Field("europeana_recordHashFirst")
	private String[] recordHashFirstSix;

	@Field("UGC")
	private String[] ugc;

	@Field("edm_rights")
	private String[] edmRights;

	@Field("COMPLETENESS")
	private String[] completeness;

	@Field("COUNTRY")
	private String[] country;

	private int score;
	private String debugQuery;

	@Field("europeana_collectionName")
	private String[] europeanaCollectionName;

	private int index;

	@Field("edm_place_broader_term")
	private String[] enrichmentPlaceBroaderTerm;

	@Field("edm_place_alt_label")
	private List<Map<String, String>> edmPlaceAltLabel;

	@Field("dcterms_isPartOf")
	private String[] dctermsIsPartOf;

	@Override
	public String[] getEdmPlaceBroaderTerm() {
		return (enrichmentPlaceBroaderTerm != null ? enrichmentPlaceBroaderTerm.clone() : null);
	}

	@Override
	public List<Map<String, String>> getEdmPlaceAltLabel() {
		return edmPlaceAltLabel;
	}

	@Override
	public String[] getEdmTimespanBroaderTerm() {
		return (edmTimespanBroaderTerm != null ? edmTimespanBroaderTerm.clone() : null);
	}

	@Override
	public List<Map<String, String>> getEdmTimespanBroaderLabel() {
		return edmTimespanBroaderLabel;
	}

	@Override
	public String[] getEdmConcept() {
		return (edmConceptTerm != null ? edmConceptTerm.clone() : null);
	}

	@Override
	public List<Map<String, String>> getEdmConceptLabel() {
		return edmConceptPrefLabel;
	}

	@Override
	public String[] getEdmConceptBroaderTerm() {
		return (edmConceptBroaderTerm != null ? edmConceptBroaderTerm.clone() : null);
	}

	@Override
	public List<Map<String, String>> getEdmConceptBroaderLabel() {
		return edmConceptBroaderLabel;
	}

	@Override
	public String[] getAggregationEdmRights() {
		return (edmRights != null ? this.edmRights.clone() : null);
	}

	@Override
	public String[] getDctermsIsPartOf() {
		return (dctermsIsPartOf != null ? this.dctermsIsPartOf.clone() : null);
	}

	@Override
	public String[] getUgc() {
		return ugc != null ? this.ugc.clone() : null;
	}

	@Override
	public void setUgc(String[] ugc) {
		this.ugc = ugc.clone();
	}

	@Override
	public String[] getEdmRights() {
		return edmRights != null ? this.edmRights.clone() : null;
	}

	@Override
	public void setEdmRights(String[] edmRights) {
		this.edmRights = edmRights.clone();
	}

	@Override
	public String[] getCountry() {
		return country != null ? this.country.clone() : null;
	}

	@Override
	public void setCountry(String[] country) {
		this.country = country.clone();
	}

	@Override
	public String[] getEuropeanaCollectionName() {
		return europeanaCollectionName != null ? this.europeanaCollectionName.clone() : null;
	}

	@Override
	public void setEuropeanaCollectionName(String[] europeanaCollectionName) {
		this.europeanaCollectionName = europeanaCollectionName.clone();
	}

	@Override
	public void setDctermsIsPartOf(String[] dctermsIsPartOf) {
		this.dctermsIsPartOf = dctermsIsPartOf.clone();
	}
}
