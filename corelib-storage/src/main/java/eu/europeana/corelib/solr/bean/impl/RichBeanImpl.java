package eu.europeana.corelib.solr.bean.impl;

import org.apache.solr.client.solrj.beans.Field;

import eu.europeana.corelib.definitions.edm.beans.RichBean;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

public class RichBeanImpl extends ApiBeanImpl implements RichBean {

    @Field("proxy_dc_description")
    protected String[] dcDescription;

    @Field("provider_aggregation_edm_isShownBy")
    protected String[] edmIsShownBy;

    @Field("europeana_aggregation_edm_landingPage")
    protected String[] edmLandingPage;

    @Field("proxy_dc_description.*")
    protected Map<String,List<String>> dcDescriptionLangAware;
    
    @Field("proxy_dc_type.*")
    protected Map<String,List<String>> dcTypeLangAware;
    
    @Field("proxy_dc_subject.*")
    protected Map<String,List<String>> dcSubjectLangAware;
    
    @Override
    public String[] getDcDescription() {
        return (this.dcDescription != null ? this.dcDescription.clone() : null);
    }

    @Override
    public String[] getEdmIsShownBy() {
        return (this.edmIsShownBy != null ? this.edmIsShownBy.clone() : null);
    }

    public String[] getEdmLandingPage() {
        return (this.edmLandingPage != null ? this.edmLandingPage.clone() : null);
    }

    @Override
    public Map<String, List<String>> getDcDescriptionLangAware() {
       if (dcDescriptionLangAware != null) {
            Map<String, List<String>> retMap = new HashMap<>();

            for (String key : dcDescriptionLangAware.keySet()) {
                retMap.put(StringUtils.substringAfter(key, "."), dcDescriptionLangAware.get(key));
            }

            return retMap;
        }
        return null;
    }

    @Override
    public Map<String, List<String>> getDcTypeLangAware() {
        if (dcTypeLangAware != null) {
            Map<String, List<String>> retMap = new HashMap<>();

            for (String key : dcTypeLangAware.keySet()) {
                retMap.put(StringUtils.substringAfter(key, "."), dcTypeLangAware.get(key));
            }

            return retMap;
        }
        return null;
    }

    @Override
    public Map<String, List<String>> getDcSubjectLangAware() {
        if (dcSubjectLangAware != null) {
            Map<String, List<String>> retMap = new HashMap<>();

            for (String key : dcSubjectLangAware.keySet()) {
                retMap.put(StringUtils.substringAfter(key, "."), dcSubjectLangAware.get(key));
            }

            return retMap;
        }
        return null;
    }
}
