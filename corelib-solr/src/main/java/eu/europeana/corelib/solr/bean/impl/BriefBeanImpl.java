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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.beans.Field;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import eu.europeana.corelib.definitions.solr.DocType;
import eu.europeana.corelib.definitions.solr.beans.BriefBean;

/**
 * @see eu.europeana.corelib.definitions.solr.beans.BriefBean
 * 
 * TODO: check edmTimespanLabel, edmPlacePrefLabel
 * 
 * @author Yorgos.Mamakis@ kb.nl
 */
@JsonSerialize(include = Inclusion.NON_EMPTY)
public class BriefBeanImpl extends IdBeanImpl implements BriefBean {

	@Field("timestamp")
	private Date timestamp;

	@Field("PROVIDER")
	private String[] provider;

	@Field("provider_aggregation_edm_dataProvider")
	private String[] edmDataProvider;

	@Field("provider_aggregation_edm_object")
	private String[] edmObject;

	@Field("COMPLETENESS")
	private String europeanaCompleteness;

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

	@Field("proxy_dc_creator")
	private String[] dcCreator;
	
	@Field("proxy_dc_contributor")
	private String[] dcContributor;

	@Field("edm_place")
	private String[] edmPlace;

	@Field("pl_skos_prefLabel")
	private List<Map<String, String>> edmPlacePrefLabel;

	@Field("pl_wgs84_pos_lat")
	private Float edmPlaceLatitude;

	@Field("pl_wgs84_pos_long")
	private Float edmPlaceLongitude;

	@Field("edm_timespan")
	private String[] edmTimespan;

	@Field("ts_skos_prefLabel")
	private List<Map<String, String>> edmTimespanLabel;

	@Field("ts_edm_begin")
	private String[] edmTimespanBegin;

	@Field("ts_edm_end")
	private String[] edmTimespanEnd;

	@Field("edm_agent")
	private String[] edmAgentTerm;

	@Field("ag_skos_prefLabel")
	private List<Map<String, String>> edmAgentLabel;

	@Field("proxy_dcterms_hasPart")
	private String[] dctermsHasPart;

	@Field("proxy_dcterms_spatial")
	private String[] dctermsSpatial;

	@Field("europeana_aggregation_edm_preview")
	private String[] edmPreview;

	@Override
	public String[] getEdmPreview(){
		return this.edmPreview;
	}

	@Override
	public String[] getTitle() {
		return (this.title != null ? this.title.clone() : null);
	}

	@Override
	public String[] getEdmObject() { // was getThumbnails
		return (this.edmObject != null ? this.edmObject.clone() : null);
	}

	@Override
	public String[] getDcCreator() {
		return (this.dcCreator != null ? this.dcCreator.clone() : null);
	}
	
	@Override
	public String[] getDcContributor() {
		return (this.dcContributor != null ? this.dcContributor.clone() : null);
	}

	@Override
	public String[] getYear() {
		return (this.year != null ? this.year.clone() : null);
	}

	@Override
	public String[] getProvider() {
		return (this.provider != null ? this.provider.clone() : null);
	}

	@Override
	public String[] getLanguage() {
		return (this.language != null ? this.language.clone() : null);
	}

	@Override
	public DocType getType() {
		return (docType != null ? DocType.get(docType) : null);
	}

	@Override
	public String[] getRights() {
		return (this.rights != null ? this.rights.clone() : null);
	}

	@Override
	public String[] getDataProvider() {
		return (this.edmDataProvider != null ? this.edmDataProvider.clone() : null);
	}

	@Override
	public int getEuropeanaCompleteness() {
		return Integer.parseInt(europeanaCompleteness);
	}

	@Override
	public String[] getEdmPlace() {
		return (this.edmPlace != null ? this.edmPlace.clone() : null);
	}

	@Override
	public List<Map<String, String>> getEdmPlaceLabel() {
		if (edmPlacePrefLabel != null && 
				edmPlacePrefLabel.size() > 0) {
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			for (int i = 0, max = edmPlacePrefLabel.size(); i < max; i++) {
				Object label = edmPlacePrefLabel.get(i);
				if (label.getClass().getName() == "java.lang.String") {
					Map<String, String> map = new HashMap<String, String>();
					map.put("def", (String)label);
					list.add(map);
				} else {
					list.add((Map<String, String>)label);
				}
			}
			return list;
		}

		return edmPlacePrefLabel;
	}

	@Override
	public Float getEdmPlaceLatitude() {
		return (this.edmPlaceLatitude != null ? this.edmPlaceLatitude : null);
	}

	@Override
	public Float getEdmPlaceLongitude() {
		return (this.edmPlaceLongitude != null ? this.edmPlaceLongitude : null);
	}

	@Override
	public String[] getEdmTimespan() {
		return (this.edmTimespan != null ? this.edmTimespan.clone() : null);
	}

	@Override
	public List<Map<String, String>> getEdmTimespanLabel() {
		if (edmTimespanLabel != null 
				&& edmTimespanLabel.size() > 0) {
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			for (int i = 0, max = edmTimespanLabel.size(); i < max; i++) {
				Object label = edmTimespanLabel.get(i);
				if (label.getClass().getName() == "java.lang.String") {
					Map<String, String> map = new HashMap<String, String>();
					map.put("def", (String)label);
					list.add(map);
				} else {
					list.add((Map<String, String>)label);
				}
			}
			return list;
		}
		return this.edmTimespanLabel;
	}

	@Override
	public String[] getEdmTimespanBegin() {
		return (this.edmTimespanBegin != null ? this.edmTimespanBegin.clone() : null);
	}

	@Override
	public String[] getEdmTimespanEnd() {
		return (this.edmTimespan != null ? this.edmTimespanEnd.clone() : null);
	}

	@Override
	public String[] getEdmAgent() {
		return (this.edmAgentTerm != null ? this.edmAgentTerm.clone() : null);
	}

	@Override
	public List<Map<String, String>> getEdmAgentLabel() {
		return this.edmAgentLabel;
	}

	@Override
	public String[] getDctermsHasPart() {
		return (this.dctermsHasPart != null ? this.dctermsHasPart.clone() : null);
	}

	@Override
	public String[] getDctermsSpatial() {
		return (this.dctermsSpatial != null ? this.dctermsSpatial.clone() : null);
	}

	@Override
	public Date getTimestamp() {
		return timestamp;
	}
}
