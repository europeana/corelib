package eu.europeana.corelib.edm.server.importer.util;

import eu.europeana.corelib.solr.entity.LicenseImpl;

public class LicenseFieldInput {

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
}
