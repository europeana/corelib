package eu.europeana.corelib.edm.model.schemaorg;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DateString implements BaseType {

    @JsonProperty(SchemaOrgConstants.VALUE)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) { this.value = value; }

    @Override
    public String getTypeName() { return "DateString"; }
}
