/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.europeana.corelib.solr.utils.construct;

import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;
import eu.europeana.corelib.definitions.solr.entity.WebResource;
import eu.europeana.corelib.solr.MongoServer;
import eu.europeana.corelib.solr.entity.WebResourceImpl;
import eu.europeana.corelib.solr.server.EdmMongoServer;
import eu.europeana.corelib.solr.utils.MongoUtils;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Yorgos.Mamakis@ europeana.eu
 */
public class WebResourceCreator {

    public WebResource saveWebResource(WebResource wr, MongoServer mongo) {

        WebResourceImpl wrNew = ((EdmMongoServer) mongo).searchByAbout(WebResourceImpl.class, wr.getAbout());
        if (wrNew != null) {
            return updateWebResource(wrNew, wr, mongo);
        }

        mongo.getDatastore().save(wr);
        return wr;
    }

    private WebResource updateWebResource(WebResource wrNew, WebResource wr, MongoServer mongoServer) {
        Query<WebResourceImpl> updateQuery = mongoServer.getDatastore()
                .createQuery(WebResourceImpl.class).field("about")
                .equal(wr.getAbout());
        UpdateOperations<WebResourceImpl> ops = mongoServer.getDatastore()
                .createUpdateOperations(WebResourceImpl.class);
        boolean update = false;
        if (wr.getDcDescription() != null) {
            if (wrNew.getDcDescription() == null || !MongoUtils.mapEquals(wr.getDcDescription(), wrNew.
                    getDcDescription())) {
                update = true;
                wrNew.setDcDescription(wr.getDcDescription());
                ops.set("dcDescription", wr.getDcDescription());
            }
        } else {
            if (wrNew.getDcDescription() != null) {
                update = true;
                ops.unset("dcDescription");
                wrNew.setDcDescription(null);
            }
        }

        if (wr.getDcFormat() != null) {
            if (wrNew.getDcFormat() == null || !MongoUtils.mapEquals(wr.getDcFormat(), wrNew.getDcFormat())) {
                update = true;
                wrNew.setDcFormat(wr.getDcFormat());
                ops.set("dcFormat", wr.getDcFormat());
            }
        } else {
            if (wrNew.getDcFormat() != null) {
                update = true;
                ops.unset("dcFormat");
                wrNew.setDcFormat(null);
            }
        }
        if (wr.getDcSource() != null) {
            if (wrNew.getDcSource() == null || !MongoUtils.mapEquals(wr.getDcSource(), wrNew.getDcSource())) {
                update = true;
                wrNew.setDcSource(wr.getDcSource());
                ops.set("dcSource", wr.getDcSource());
            }
        } else {
            if (wrNew.getDcSource() != null) {
                update = true;
                ops.unset("dcSource");
                wrNew.setDcSource(null);
            }
        }
        if (wr.getDctermsConformsTo() != null) {
            if (wrNew.getDctermsConformsTo() == null || !MongoUtils.mapEquals(wr.getDctermsConformsTo(), wrNew.
                    getDctermsConformsTo())) {
                update = true;
                wrNew.setDctermsConformsTo(wr.getDctermsConformsTo());
                ops.set("dctermsConformsTo", wr.getDctermsConformsTo());
            }
        } else {
            if (wrNew.getDctermsConformsTo() != null) {
                update = true;
                ops.unset("dctermsConformsTo");
                wrNew.setDctermsConformsTo(null);
            }
        }

        if (wr.getDctermsCreated() != null) {
            if (wrNew.getDctermsCreated() == null || !MongoUtils.mapEquals(wr.getDctermsCreated(), wrNew.
                    getDctermsCreated())) {
                update = true;
                wrNew.setDctermsCreated(wr.getDctermsCreated());
                ops.set("dctermsCreated", wr.getDctermsCreated());
            }
        } else {
            if (wrNew.getDctermsCreated() != null) {
                update = true;
                ops.unset("dctermsCreated");
                wrNew.setDctermsCreated(null);
            }
        }

        if (wr.getDctermsExtent() != null) {
            if (wrNew.getDctermsExtent() == null || !MongoUtils.mapEquals(wr.getDctermsExtent(), wrNew.
                    getDctermsExtent())) {
                update = true;
                wrNew.setDctermsExtent(wr.getDctermsExtent());
                ops.set("dctermsExtent", wr.getDctermsExtent());
            }
        } else {
            if (wrNew.getDctermsExtent() != null) {
                update = true;
                ops.unset("dctermsExtent");
                wrNew.setDctermsExtent(null);
            }
        }
        if (wr.getDctermsHasPart() != null) {
            if (wrNew.getDctermsHasPart() == null || !MongoUtils.mapEquals(wr.getDctermsHasPart(), wrNew.
                    getDctermsHasPart())) {
                update = true;
                wrNew.setDctermsHasPart(wr.getDctermsHasPart());
                ops.set("dctermsHasPart", wr.getDctermsHasPart());
            }
        } else {
            if (wrNew.getDctermsHasPart() != null) {
                update = true;
                ops.unset("dctermsHasPart");
                wrNew.setDctermsHasPart(null);
            }
        }
        
        if (wr.getDctermsIsFormatOf() != null) {
            if (wrNew.getDctermsIsFormatOf() == null || !MongoUtils.mapEquals(wr.getDctermsIsFormatOf(), wrNew.
                    getDctermsIsFormatOf())) {
                update = true;
                wrNew.setDctermsIsFormatOf(wr.getDctermsIsFormatOf());
                ops.set("dctermsIsFormatOf", wr.getDctermsIsFormatOf());
            }
        } else {
            if (wrNew.getDctermsIsFormatOf() != null) {
                update = true;
                ops.unset("dctermsIsFormatOf");
                wrNew.setDctermsIsFormatOf(null);
            }
        }
        
        
         if (wr.getDctermsIssued() != null) {
            if (wrNew.getDctermsIssued() == null || !MongoUtils.mapEquals(wr.getDctermsIssued(), wrNew.
                    getDctermsIssued())) {
                update = true;
                wrNew.setDctermsIssued(wr.getDctermsIssued());
                ops.set("dctermsIssued", wr.getDctermsIssued());
            }
        } else {
            if (wrNew.getDctermsIssued() != null) {
                update = true;
                ops.unset("dctermsIssued");
                wrNew.setDctermsIssued(null);
            }
        }
         
          if (wr.getIsNextInSequence() != null) {
            if (wrNew.getIsNextInSequence()== null || !StringUtils.equals(wr.getIsNextInSequence(), wrNew.
                    getIsNextInSequence())) {
                update = true;
                wrNew.setIsNextInSequence(wr.getIsNextInSequence());
                ops.set("isNextInSequence", wr.getIsNextInSequence());
            }
        } else {
            if (wrNew.getIsNextInSequence() != null) {
                update = true;
                ops.unset("isNextInSequence");
                wrNew.setIsNextInSequence(null);
            }
        }
          
          if (wr.getWebResourceDcRights() != null) {
            if (wrNew.getWebResourceDcRights() == null || !MongoUtils.mapEquals(wr.getWebResourceDcRights(), wrNew.
                    getWebResourceDcRights())) {
                update = true;
                wrNew.setWebResourceDcRights(wr.getWebResourceDcRights());
                ops.set("webResourceDcRights", wr.getWebResourceDcRights());
            }
        } else {
            if (wrNew.getWebResourceDcRights() != null) {
                update = true;
                ops.unset("webResourceDcRights");
                wrNew.setWebResourceDcRights(null);
            }
        }
          
          if (wr.getWebResourceEdmRights() != null) {
            if (wrNew.getWebResourceEdmRights() == null || !MongoUtils.mapEquals(wr.getWebResourceEdmRights(), wrNew.
                    getWebResourceEdmRights())) {
                update = true;
                wrNew.setWebResourceEdmRights(wr.getWebResourceEdmRights());
                ops.set("webResourceEdmRights", wr.getWebResourceEdmRights());
            }
        } else {
            if (wrNew.getWebResourceEdmRights() != null) {
                update = true;
                ops.unset("webResourceEdmRights");
                wrNew.setWebResourceEdmRights(null);
            }
        }
          
          if(update){
              mongoServer.getDatastore().update(updateQuery, ops);
          }
        return wrNew;
    }

}
