package eu.europeana.corelib.solr.bean.impl;

import eu.europeana.corelib.edm.utils.EdmUtils;
import org.apache.solr.client.solrj.beans.Field;

import eu.europeana.corelib.definitions.edm.beans.RichBean;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * @see eu.europeana.corelib.definitions.edm.beans.RichBean
 */
@JsonPropertyOrder(alphabetic=true)
public class RichBeanImpl extends ApiBeanImpl implements RichBean {

    @Field("europeana_aggregation_edm_landingPage")
    protected String[] edmLandingPage;
    
    @Field("proxy_dc_type.*")
    protected Map<String,List<String>> dcTypeLangAware;
    
    @Field("proxy_dc_subject.*")
    protected Map<String,List<String>> dcSubjectLangAware;

    // temporary added for debugging purposes (see EA-1395)
    @Field("fulltext")
    private List<Map<String, String>> fulltext;

    // temporary added for debugging purposes (see EA-1395)
    @Field("fulltext.*")
    private Map<String, List<String>> fulltextLangAware;

    @Override
    public String[] getEdmLandingPage() {
        return (this.edmLandingPage != null ? this.edmLandingPage.clone() : null);
    }

    @Override
    public Map<String, List<String>> getDcTypeLangAware() {
        return EdmUtils.cloneMap(dcTypeLangAware);
    }

    @Override
    public Map<String, List<String>> getDcSubjectLangAware() {
        return EdmUtils.cloneMap(dcSubjectLangAware);
    }

    // temporary added for debugging purposes (see EA-1395)
    @Override
    public List<Map<String, String>> getFulltext() {
        return EdmUtils.cloneList(fulltext);
    }

    // temporary added for debugging purposes (see EA-1395)
    @Override
    public Map<String, List<String>> getFulltextLangAware() {
        return EdmUtils.cloneMap(fulltextLangAware);
    }
}
