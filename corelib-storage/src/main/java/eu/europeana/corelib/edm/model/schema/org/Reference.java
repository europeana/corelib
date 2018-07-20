package eu.europeana.corelib.edm.model.schema.org;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldId;

public class Reference<T extends Thing> implements BaseType {

    @JsonIgnore
    private Class<T> type;

    public Reference() {
        this.type = null;
    }

    public Reference(Class<T> type) {
        this.type = type;
    }

    @JsonldId
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("@type")
    @Override
    public String getTypeName() {
        if (type != null) {
            type.getClass().getSimpleName();
        }
        return null;
    }
}
