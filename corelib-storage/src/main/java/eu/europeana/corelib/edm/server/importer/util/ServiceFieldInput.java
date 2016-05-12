package eu.europeana.corelib.edm.server.importer.util;

import eu.europeana.corelib.definitions.jibx.Service;
import eu.europeana.corelib.definitions.model.EdmLabel;
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
        if (service.getConformsTo().getResource() != null && StringUtils.isNotEmpty(service.getConformsTo().getResource().getResource())) {
            serv.setDcTermsConformsTo(service.getConformsTo().getResource().getResource());
        }
        return serv;
    }

    public SolrInputDocument createServiceSolrFields(Service service, SolrInputDocument solrInputDocument) {
        solrInputDocument.addField(EdmLabel.SV_RDF_ABOUT.toString(), service.getAbout());
        if (service.getConformsTo() != null && service.getConformsTo().getResource() != null
                && StringUtils.isNotEmpty(service.getConformsTo().getResource().getResource())) {
            solrInputDocument.addField(EdmLabel.SV_DCTERMS_CONFORMS_TO.toString(), service.getConformsTo().getResource().getResource());
        }
        return solrInputDocument;
    }
}
