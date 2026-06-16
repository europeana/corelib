package eu.europeana.corelib.solr.entity;

import dev.morphia.annotations.Entity;
import eu.europeana.corelib.definitions.edm.entity.Service;
import java.util.List;
import java.util.Map;

/**
 * Created by ymamakis on 1/12/16.
 */
@Entity(value = "Service", useDiscriminator = false)
public class ServiceImpl extends AbstractEdmEntityImpl implements Service {

    private String[] dctermsConformsTo;
    private String[] doapImplements;
    private Map<String, List<String>> rdfsLabel;

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

    @Override
    public Map<String,List<String>> getRdfsLabel() {
        return rdfsLabel;
    }

    @Override
    public void setRdfsLabel(Map<String,List<String>> rdfsLabel) {
        this.rdfsLabel = rdfsLabel;
    }
}
