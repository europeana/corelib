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

import eu.europeana.corelib.definitions.solr.DocType;
import eu.europeana.corelib.definitions.solr.beans.BriefBean;

/**
 * @see eu.europeana.corelib.definitions.solr.beans.BriefBean
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
public class BriefBeanImpl extends IdBeanImpl implements BriefBean {

	private String fullDocUrl;

	@Field("PROVIDER")
	private String[] provider;

	@Field("edm_dataProvider")
	private String[] edmDataProvider;

	@Field("edm_object")
	private String[] edmObject;

	@Field("europeana_completeness")
	private int europeanaCompleteness;

	@Field("TYPE")
	private String[] docType;

	@Field("LANGUAGE")
	private String[] language;

	@Field("YEAR")
	private String[] year;

	@Field("RIGHTS")
	private String[] rights;

	@Field("title")
	private String[] title;

	@Field("creator")
	private String[] creator;

	@Field("edm_place")
	private String[] edmPlace;

	@Field("edm_place_pref_label")
	private List<Map<String, String>> edmPlacePrefLabel;

	@Field("edm_place_latLon")
	private Float[] edmPlaceLatitude;

	@Field("edm_place_latLon")
	private Float[] edmPlaceLongitude;

	@Field("edm_timespan")
	private String[] edmTimespan;

	@Field("edm_timespan_label")
	private List<Map<String, String>> edmTimespanLabel;

	@Field("edm_timespan_begin")
	private String[] edmTimespanBegin;

	@Field("edm_timespan_end")
	private String[] edmTimespanEnd;

	@Field("edm_agent")
	private String[] edmAgentTerm;

	@Field("edm_agent_label")
	private List<Map<String, String>> edmAgentLabel;

	@Field("dcterms_hasPart")
	private String[] dctermsHasPart;

	@Field("dcterms_spatial")
	private String[] dctermsSpatial;

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
	public String[] getEdmObject() { // was getThumbnails
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
	public List<Map<String, String>> getEdmPlaceLabel() {
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
	public List<Map<String, String>> getEdmTimespanLabel() {
		return edmTimespanLabel;
	}

	@Override
	public String[] getEdmTimespanBegin() {
		return edmTimespanBegin;
	}

	@Override
	public String[] getEdmTimespanEnd() {
		return edmTimespanEnd;
	}

	@Override
	public String[] getEdmAgent() {
		return edmAgentTerm;
	}

	@Override
	public List<Map<String, String>> getEdmAgentLabel() {
		return edmAgentLabel;
	}

	@Override
	public String[] getDcTermsHasPart() {
		return this.dctermsHasPart;
	}

	@Override
	public String[] getDcTermsSpatial() {
		return this.dctermsSpatial;
	}

}
