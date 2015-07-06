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

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.beans.Field;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import eu.europeana.corelib.definitions.edm.beans.BriefBean;
import eu.europeana.corelib.definitions.solr.DocType;
import eu.europeana.corelib.solr.bean.impl.IdBeanImpl;
import eu.europeana.corelib.web.service.impl.EuropeanaUrlServiceImpl;

/**
 * @see eu.europeana.corelib.definitions.edm.beans.BriefBean
 *
 * TODO: check edmTimespanLabel, edmPlacePrefLabel
 *
 * @author Yorgos.Mamakis@ kb.nl
 */
@JsonSerialize(include = Inclusion.NON_EMPTY)
public class BriefBeanImpl extends IdBeanImpl implements BriefBean {

    @Field("timestamp")
    protected Date timestamp;

    @Field("PROVIDER")
    protected String[] provider;

    @Field("provider_aggregation_edm_dataProvider")
    protected String[] edmDataProvider;

    @Field("provider_aggregation_edm_object")
    protected String[] edmObject;

    @Field("COMPLETENESS")
    protected String europeanaCompleteness;

    @Field("TYPE")
    protected String[] docType;

    @Field("LANGUAGE")
    protected String[] language;

    @Field("YEAR")
    protected String[] year;

    @Field("RIGHTS")
    protected String[] rights;

    @Field("title")
    protected String[] title;

    @Field("proxy_dc_creator")
    protected String[] dcCreator;

    @Field("proxy_dc_contributor")
    protected String[] dcContributor;
    
    @Field("proxy_dc_language")
    protected String[] dcLanguage;

    @Field("edm_place")
    protected String[] edmPlace;

    @Field("pl_skos_prefLabel")
    protected List<Map<String, String>> edmPlacePrefLabel;

    @Field("pl_skos_prefLabel.*")
    protected Map<String, List<String>> edmPlaceLabelLangAware;

    @Field("pl_wgs84_pos_lat")
    protected List<String> edmPlaceLatitude;

    @Field("pl_wgs84_pos_long")
    protected List<String> edmPlaceLongitude;

    @Field("edm_timespan")
    protected String[] edmTimespan;

    @Field("ts_skos_prefLabel")
    protected List<Map<String, String>> edmTimespanLabel;

    @Field("ts_skos_prefLabel.*")
    protected Map<String, List<String>> edmTimespanLabelLangAware;

    @Field("ts_edm_begin")
    protected String[] edmTimespanBegin;

    @Field("ts_edm_end")
    protected String[] edmTimespanEnd;

    @Field("edm_agent")
    protected String[] edmAgentTerm;

    @Field("ag_skos_prefLabel")
    protected List<Map<String, String>> edmAgentLabel;

    @Field("ag_skos_prefLabel.*")
    protected Map<String, List<String>> edmAgentLabelLangAware;

    @Field("proxy_dcterms_hasPart")
    protected String[] dctermsHasPart;

    @Field("proxy_dcterms_spatial")
    protected String[] dctermsSpatial;

    @Field("europeana_aggregation_edm_preview")
    protected String[] edmPreview;

    @Field("proxy_dc_title")
    protected String[] proxyDcTitle;

    @Field("description")
    protected String[] description;

    @Field("score")
    protected Float score;

    @Field("provider_aggregation_edm_isShownAt")
    protected String[] edmIsShownAt;
    
    @Field("edm_previewNoDistribute")
    protected Boolean edmPreviewNotDistribute;
    
    @Field("proxy_dc_title.*")
    protected Map<String,List<String>> dcTitleLangAware;
    
    @Field("proxy_dc_creator.*")
    protected Map<String,List<String>> dcCreatorLangAware;
    
    @Field("proxy_dc_contriutor.*")
    protected Map<String,List<String>>dcContributorLangAware;
    
    @Field("proxy_dc_language.*")
    protected Map<String,List<String>>dcLanguageLangAware;
    
    @Override
    public String[] getEdmPreview() {
        List<String> previews = new ArrayList<String>();
        if (this.edmObject != null) {
            for (String str : edmObject) {
                if (StringUtils.isNotBlank(str)) {
                    previews.add(EuropeanaUrlServiceImpl.getBeanInstance().getThumbnailUrl(str, getType()).toString());
                }
            }
        }
        return previews.toArray(new String[previews.size()]);
    }

    @Override
    public String[] getTitle() {
        if (this.title != null) {
            return this.title.clone();
        } else if (this.proxyDcTitle != null) {
            return this.proxyDcTitle.clone();
        } else if (this.description != null) {
            return this.description.clone();
        } else {
            return null;
        }
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
    public String[] getDcLanguage() {
        return (this.dcLanguage != null ? this.dcLanguage.clone() : null);
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
        return (docType != null ? DocType.safeValueOf(docType) : null);
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
        return (!StringUtils.isBlank(europeanaCompleteness) ? Integer.parseInt(europeanaCompleteness) : 0);
    }

    @Override
    public String[] getEdmPlace() {
        return (this.edmPlace != null ? this.edmPlace.clone() : null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, String>> getEdmPlaceLabel() {
        if (edmPlacePrefLabel != null && edmPlacePrefLabel.size() > 0) {
            List<Map<String, String>> list = new ArrayList<Map<String, String>>();
            for (int i = 0, max = edmPlacePrefLabel.size(); i < max; i++) {
                Object label = edmPlacePrefLabel.get(i);
                if (label.getClass().getName() == "java.lang.String") {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("def", (String) label);
                    list.add(map);
                } else {
                    list.add((Map<String, String>) label);
                }
            }
            return list;
        }

        return edmPlacePrefLabel;
    }

    @Override
    public List<String> getEdmPlaceLatitude() {
        return (this.edmPlaceLatitude != null ? this.edmPlaceLatitude : null);
    }

    @Override
    public List<String> getEdmPlaceLongitude() {
        return (this.edmPlaceLongitude != null ? this.edmPlaceLongitude : null);
    }

    @Override
    public String[] getEdmTimespan() {
        return (this.edmTimespan != null ? this.edmTimespan.clone() : null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, String>> getEdmTimespanLabel() {
        if (edmTimespanLabel != null
                && edmTimespanLabel.size() > 0) {
            List<Map<String, String>> list = new ArrayList<Map<String, String>>();
            for (int i = 0, max = edmTimespanLabel.size(); i < max; i++) {
                Object label = edmTimespanLabel.get(i);
                if (label.getClass().getName() == "java.lang.String") {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("def", (String) label);
                    list.add(map);
                } else {
                    list.add((Map<String, String>) label);
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
        return (this.edmTimespanEnd != null ? this.edmTimespanEnd.clone() : null);
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

    @Override
    public float getScore() {
        return ((Float) this.score != null ? this.score : 0.0f);
    }

    @Override
    public String[] getEdmIsShownAt() {
        return (this.edmIsShownAt != null ? this.edmIsShownAt.clone() : null);
    }

    @Override
    public Map<String, List<String>> getEdmPlaceLabelLangAware() {
        if (edmPlaceLabelLangAware != null) {
            Map<String, List<String>> retMap = new HashMap<>();

            for (String key : edmPlaceLabelLangAware.keySet()) {
                retMap.put(StringUtils.substringAfter(key, "."), edmPlaceLabelLangAware.get(key));
            }

            return retMap;
        }
        return null;
    }

    @Override
    public Map<String, List<String>> getEdmTimespanLabelLangAware() {
        if (edmTimespanLabelLangAware != null) {
            Map<String, List<String>> retMap = new HashMap<>();

            for (String key : edmTimespanLabelLangAware.keySet()) {
                retMap.put(StringUtils.substringAfter(key, "."), edmTimespanLabelLangAware.get(key));
            }

            return retMap;
        }
        return null;
    }

    @Override
    public Map<String, List<String>> getEdmAgentLabelLangAware() {
        if (edmAgentLabelLangAware != null) {
            Map<String, List<String>> retMap = new HashMap<>();

            for (String key : edmAgentLabelLangAware.keySet()) {
                retMap.put(StringUtils.substringAfter(key, "."), edmAgentLabelLangAware.get(key));
            }

            return retMap;
        }
        return null;
    }

	@Override
	public Boolean getPreviewNoDistribute() {
		return edmPreviewNotDistribute!=null?edmPreviewNotDistribute:false;
	}

    @Override
    public Map<String, List<String>> getDcTitleLangAware() {
         if (dcTitleLangAware != null) {
            Map<String, List<String>> retMap = new HashMap<>();

            for (String key : dcTitleLangAware.keySet()) {
                retMap.put(StringUtils.substringAfter(key, "."), dcTitleLangAware.get(key));
            }

            return retMap;
        }
        return null;
    }

    @Override
    public Map<String, List<String>> getDcCreatorLangAware() {
         if (dcCreatorLangAware != null) {
            Map<String, List<String>> retMap = new HashMap<>();

            for (String key : dcCreatorLangAware.keySet()) {
                retMap.put(StringUtils.substringAfter(key, "."), dcCreatorLangAware.get(key));
            }

            return retMap;
        }
        return null;
    }

    @Override
    public Map<String, List<String>> getDcContributorLangAware() {
       if (dcContributorLangAware != null) {
            Map<String, List<String>> retMap = new HashMap<>();

            for (String key : dcContributorLangAware.keySet()) {
                retMap.put(StringUtils.substringAfter(key, "."), dcContributorLangAware.get(key));
            }

            return retMap;
        }
        return null;
    }
    @Override
    public Map<String, List<String>> getDcLanguageLangAware() {
       if (dcLanguageLangAware != null) {
            Map<String, List<String>> retMap = new HashMap<>();

            for (String key : dcLanguageLangAware.keySet()) {
                retMap.put(StringUtils.substringAfter(key, "."), dcLanguageLangAware.get(key));
            }

            return retMap;
        }
        return null;
    }

}
