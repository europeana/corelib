package eu.europeana.corelib.edm.server.importer.util;

import eu.europeana.corelib.definitions.jibx.Service;
import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.edm.utils.SolrUtils;
import eu.europeana.corelib.solr.entity.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.common.SolrInputDocument;

/**
 * Created by ymamakis on 1/12/16.
 */
public class ServiceFieldInput {

    public ServiceImpl createServiceMongoFields(Service service) {
        ServiceImpl serv = new ServiceImpl();
        serv.setAbout(service.getAbout());
        if (service.getConformsToList() != null) {

            serv.setDcTermsConformsTo(SolrUtils.resourceOrLiteralListToArray(service.getConformsToList()));
        }
        if (service.getImplements().getResource() != null && StringUtils.isNotEmpty(service.getImplements().getResource())) {
            serv.setDoapImplements(service.getImplements().getResource());
        }
        return serv;
    }

    public SolrInputDocument createServiceSolrFields(Service service, SolrInputDocument solrInputDocument) {
        solrInputDocument.addField(EdmLabel.SV_RDF_ABOUT.toString(), service.getAbout());
        if (service.getConformsToList() != null) {

            solrInputDocument.addField(EdmLabel.SV_DCTERMS_CONFORMS_TO.toString(), SolrUtils.resourceOrLiteralListToArray(service.getConformsToList()));
        }
        if (service.getImplements() != null
                && StringUtils.isNotEmpty(service.getImplements().getResource())) {
            solrInputDocument.addField(EdmLabel.SV_DOAP_IMPLEMENTS.toString(), service.getImplements().getResource());
        }
        return solrInputDocument;
    }
}
