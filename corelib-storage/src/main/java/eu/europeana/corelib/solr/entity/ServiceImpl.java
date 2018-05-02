package eu.europeana.corelib.solr.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import eu.europeana.corelib.definitions.edm.entity.Service;

/**
 * Created by ymamakis on 1/12/16.
 */
@Entity("Service")
public class ServiceImpl extends AbstractEdmEntityImpl implements Service {

    private String[] dctermsConformsTo;
    private String[] doapImplements;

    @JsonIgnore
    @Override
    public ObjectId getId() {
        return this.id;
    }

    @Override
    public String[] getDctermsConformsTo() {
        return dctermsConformsTo;
    }

    @Override
    public void setDcTermsConformsTo(String[] dcTermsConformsTo) {
        this.dctermsConformsTo = dcTermsConformsTo;
    }

    @Override
    public String[] getDoapImplements() {
        return doapImplements;
    }

    @Override
    public void setDoapImplements(String[] doapImplements) {
        this.doapImplements = doapImplements;
    }
}
