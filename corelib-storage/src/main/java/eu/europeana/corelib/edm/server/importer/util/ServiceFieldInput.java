package eu.europeana.corelib.edm.server.importer.util;

import eu.europeana.corelib.definitions.jibx.Service;
import eu.europeana.corelib.edm.utils.SolrUtils;
import eu.europeana.corelib.solr.entity.ServiceImpl;

/**
 * Created by ymamakis on 1/12/16.
 */
public class ServiceFieldInput {

  public ServiceImpl createServiceMongoFields(Service service) {
    ServiceImpl serv = new ServiceImpl();
    serv.setAbout(service.getAbout());
    if (service.getConformsToList() != null) {
      serv.setDcTermsConformsTo(
          SolrUtils.resourceOrLiteralListToArray(service.getConformsToList()));
    }

    if (service.getImplementList() != null) {
      serv.setDoapImplements(SolrUtils.resourceListToArray(service.getImplementList()));
    }
    return serv;
  }
}
