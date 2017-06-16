package eu.europeana.corelib.edm.utils.construct;

import eu.europeana.corelib.definitions.edm.entity.Service;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.edm.utils.SolrUtils;
import org.apache.solr.common.SolrInputDocument;

/**
 * Created by ymamakis on 1/12/16.
 */
public class ServiceSolrCreator {

    public void create(SolrInputDocument doc, Service service){
        SolrUtils.addFromString(doc, EdmLabel.SV_RDF_ABOUT,service.getAbout());
        SolrUtils.addFromStringArray(doc,EdmLabel.SV_DCTERMS_CONFORMS_TO,service.getDctermsConformsTo());
        SolrUtils.addFromStringArray(doc,EdmLabel.SV_DOAP_IMPLEMENTS,service.getDoapImplements());
    }
}
