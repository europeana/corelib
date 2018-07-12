package eu.europeana.corelib.edm.model.schema.org;

import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldId;

public class MultilingualStringWithReference extends MultilingualString {

    @JsonldId
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
