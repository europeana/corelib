package eu.europeana.corelib.solr.bean.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import eu.europeana.corelib.edm.utils.EdmUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.beans.Field;

import eu.europeana.corelib.definitions.edm.beans.ApiBean;
import eu.europeana.corelib.solr.bean.impl.BriefBeanImpl;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

/**
 * @see eu.europeana.corelib.definitions.edm.beans.ApiBean
 *
 * @author Yorgos.Mamakis@ kb.nl
 */
@JsonPropertyOrder(alphabetic=true)
public class ApiBeanImpl extends BriefBeanImpl implements ApiBean {

    @Field("skos_concept")
    private String[] edmConceptTerm;

    @Field("cc_skos_prefLabel")
    private List<Map<String, String>> edmConceptPrefLabel;

    @Field("cc_skos_prefLabel.*")
    private Map<String, List<String>> edmConceptPrefLabelLangAware;

    @Field("cc_skos_broader")
    private String[] edmConceptBroaderTerm;

    private List<Map<String, String>> edmConceptBroaderLabel;

    @Field("cc_skos_broader.*")
    private Map<String, List<String>> edmConceptBroaderLabelLangAware;

    @Field("ts_dcterms_isPartOf")
    private String[] edmTimespanBroaderTerm;

    @Field("ts_dcterms_isPartOf")
    private List<Map<String,String>> edmTimespanBroaderLabel;

    @Field("europeana_recordHashFirstSix")
    private String[] recordHashFirstSix;

    @Field("UGC")
    private String[] ugc;

    @Field("COMPLETENESS")
    private String completeness;

    @Field("COUNTRY")
    private String[] country;

    private int score;

    @Field("europeana_collectionName")
    private String[] europeanaCollectionName;

    private int index;

    @Field("pl_dcterms_isPartOf")
    private String[] edmPlaceBroaderTerm;

    @Field("pl_skos_altLabel")
    private List<Map<String,String>> edmPlaceAltLabel;

    @Field("pl_skos_altLabel.*")
    private Map<String, List<String>> edmPlaceAltLabelLangAware;

    @Field("pl_dcterms_isPartOf")
    private String[] dctermsIsPartOf;

    @Field("timestamp_created")
    private Date timestampCreated;

    @Field("timestamp_update") //This is obviously a typo, but do not change it as it will render previous ingested records unusable
    private Date timestampUpdate;


    @Override
    public String[] getEdmPlaceBroaderTerm() {
        return (edmPlaceBroaderTerm != null ? edmPlaceBroaderTerm.clone() : null);
    }

    @Override
    public List<Map<String,String>> getEdmPlaceAltLabel() {
        return EdmUtils.cloneList(edmPlaceAltLabel);
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
        return EdmUtils.cloneList(edmConceptPrefLabel);
    }

    @Override
    public String[] getEdmConceptBroaderTerm() {
        return (edmConceptBroaderTerm != null ? edmConceptBroaderTerm.clone() : null);
    }

    @Override
    public List<Map<String, String>> getEdmConceptBroaderLabel() {
        return null;
    }

    @Override
    public String[] getDctermsIsPartOf() {
        return (dctermsIsPartOf != null ? this.dctermsIsPartOf.clone() : null);
    }

    @Override
    public boolean[] getUgc() {
        if (ugc == null) {
            return null;
        }
        String[] _ugc = this.ugc.clone();
        boolean[] retVal = new boolean[_ugc.length];
        for (int i = 0, len = _ugc.length; i < len; i++) {
            retVal[i] = Boolean.valueOf(_ugc[i]);
        }
        return retVal;
    }

    @Override
    public void setUgc(boolean[] ugc) {
        if (ugc == null) {
            this.ugc = null;
        } else {
            String[] retVal = new String[ugc.length];
            for (int i = 0, len = ugc.length; i < len; i++) {
                retVal[i] = Boolean.toString(ugc[i]);
            }
            this.ugc = retVal;
        }
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

    public int getCompleteness() {
        return Integer.parseInt(completeness);
    }

    public void setCompleteness(String completeness) {
        this.completeness = completeness;
    }

    @Override
    public Date getTimestampCreated() {
        return (Date) (timestampCreated != null ? this.timestampCreated.clone() : null);
    }

    @Override
    public Date getTimestampUpdate() {
        return (Date) (timestampUpdate != null ? this.timestampUpdate.clone() : null);
    }

    @Override
    public Map<String, List<String>> getEdmConceptPrefLabelLangAware() {
        if (edmConceptPrefLabelLangAware != null) {
            Map<String, List<String>> retMap = new HashMap<>();
            
            for (String key : edmConceptPrefLabelLangAware.keySet()) {
            	Set<String> cleaned = new HashSet<String>();
            	for(String dup : edmConceptPrefLabelLangAware.get(key)){
            		cleaned.add(dup);
            	}
            	
                retMap.put(StringUtils.substringAfter(key, "."), new ArrayList<String>(cleaned));
            }

            return retMap;
        }
        return null;
    }

    @Override
    public Map<String, List<String>> getEdmConceptBroaderLabelLangAware() {
        return EdmUtils.cloneMap(edmConceptBroaderLabelLangAware);
    }

    @Override
    public Map<String, List<String>> getEdmPlaceAltLabelLangAware() {
        return EdmUtils.cloneMap(edmPlaceAltLabelLangAware);
    }

}
