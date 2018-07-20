package eu.europeana.corelib.edm.model.schema.org;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Text implements BaseType {
    @JsonIgnore
    private String value;

    public Text() {
        this.value = null;
    }

    public Text(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public String getTypeName() {
        return null;
    }
}
