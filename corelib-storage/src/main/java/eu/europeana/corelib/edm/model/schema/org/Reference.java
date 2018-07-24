package eu.europeana.corelib.edm.model.schema.org;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldId;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Reference<T extends Thing> implements BaseType {

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
    public String getType() {
        if (type != null) {
            return type.getSimpleName();
        }
        return null;
    }

    @Override
    public String getTypeName() {
        return getType();
    }
}
