package eu.europeana.corelib.solr.server.importer.util;

import org.apache.solr.common.SolrInputDocument;

import eu.europeana.corelib.definitions.model.EdmLabel;
import eu.europeana.corelib.definitions.solr.entity.License;
import eu.europeana.corelib.solr.MongoServer;
import eu.europeana.corelib.solr.entity.LicenseImpl;
import eu.europeana.corelib.solr.server.EdmMongoServer;
import eu.europeana.corelib.solr.utils.updaters.LicenseUpdater;

public class LicenseFieldInput {

    public SolrInputDocument createLicenseSolrFields(
            eu.europeana.corelib.definitions.jibx.License license,
            SolrInputDocument solrInputDocument, boolean isAggregation) {
        if (isAggregation) {
            solrInputDocument.addField(EdmLabel.PROVIDER_AGGREGATION_CC_LICENSE.toString(),
                    license.getAbout());
            solrInputDocument.addField(EdmLabel.PROVIDER_AGGREGATION_CC_DEPRECATED_ON.toString(), license
                    .getDeprecatedOn().getDate());
            solrInputDocument.addField(EdmLabel.PROVIDER_AGGREGATION_ODRL_INHERITED_FROM.toString(),
                    license.getInheritFrom().getResource());
        } else {
            solrInputDocument.addField(EdmLabel.WR_CC_LICENSE.toString(),
                    license.getAbout());
            solrInputDocument.addField(EdmLabel.WR_CC_DEPRECATED_ON.toString(), license
                    .getDeprecatedOn().getDate());
            solrInputDocument.addField(EdmLabel.WR_ODRL_INHERITED_FROM.toString(),
                    license.getInheritFrom().getResource());
        }
        return solrInputDocument;

    }

    public LicenseImpl createLicenseMongoFields(
            eu.europeana.corelib.definitions.jibx.License jibxLicense) {
        LicenseImpl mongoLicense = new LicenseImpl();
        mongoLicense.setAbout(jibxLicense.getAbout());
        mongoLicense.setCcDeprecatedOn(jibxLicense.getDeprecatedOn()
                .getDate());
        mongoLicense.setOdrlInheritFrom(jibxLicense.getInheritFrom()
                .getResource());
        return mongoLicense;
    }

    public LicenseImpl createLicenseMongoFields(
            eu.europeana.corelib.definitions.jibx.License jibxLicense,
            MongoServer mongoServer) {
        LicenseImpl mongoLicense = ((EdmMongoServer) mongoServer).getDatastore()
                .find(LicenseImpl.class).filter("about", jibxLicense.getAbout())
                .get();

        if (mongoLicense == null) {
            mongoLicense = createLicenseMongoFields(jibxLicense);
            try {
                mongoServer.getDatastore().save(mongoLicense);
            } catch (Exception e) {
                mongoLicense = updateLicense(mongoLicense, jibxLicense,
                        mongoServer);
            }
        } else {
            mongoLicense = updateLicense(mongoLicense, jibxLicense, mongoServer);
        }
        return mongoLicense;
    }

    private LicenseImpl updateLicense(License mongoLicense,
            eu.europeana.corelib.definitions.jibx.License jibxLicense,
            MongoServer mongoServer) {

        new LicenseUpdater().update(mongoLicense, jibxLicense, mongoServer);
        return ((EdmMongoServer) mongoServer).getDatastore()
                .find(LicenseImpl.class).filter("about", jibxLicense.getAbout())
                .get();
    }
}
