package eu.europeana.corelib.definitions.edm.entity;

import java.util.List;
import java.util.Map;

/**
 * Interface of the svcs:Service
 *
 * Created by ymamakis on 1/12/16.
 */
public interface Service extends AbstractEdmEntity{

    String[] getDctermsConformsTo();
    void setDcTermsConformsTo(String[] dcTermsConformsTo);

    String[] getDoapImplements();
    void setDoapImplements(String[] doapImplements);

    Map<String, List<String>> getRdfsLabel();
    void setRdfsLabel(Map<String,List<String>> rdfsLabel);
}
