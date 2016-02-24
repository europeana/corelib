package eu.europeana.corelib.solr.entity;

import com.google.code.morphia.annotations.Entity;
import eu.europeana.corelib.definitions.edm.entity.Service;

/**
 * Created by ymamakis on 1/12/16.
 */
@Entity("Service")
public class ServiceImpl extends AbstractEdmEntityImpl implements Service {

    private String dctermsConformsTo;

    @Override
    public String getDctermsConformsTo() {
        return dctermsConformsTo;
    }

    @Override
    public void setDcTermsConformsTo(String dcTermsConformsTo) {
        this.dctermsConformsTo = dcTermsConformsTo;
    }
}
