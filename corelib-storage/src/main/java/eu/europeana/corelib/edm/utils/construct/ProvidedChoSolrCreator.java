/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.europeana.corelib.edm.utils.construct;

import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.edm.utils.SolrUtils;
import eu.europeana.corelib.solr.entity.ProvidedCHOImpl;

import org.apache.solr.common.SolrInputDocument;
import org.springframework.util.StringUtils;

/**
 *
 * @author gmamakis
 */
public class ProvidedChoSolrCreator {
    public void create(SolrInputDocument doc, ProvidedCHOImpl pCho){
        SolrUtils.addFromString(doc, EdmLabel.EUROPEANA_ID, StringUtils.replace(pCho.getAbout(), "/item/", "/"));
        SolrUtils.addFromStringArray(doc, EdmLabel.PROXY_OWL_SAMEAS, pCho.getOwlSameAs());
    }
}
