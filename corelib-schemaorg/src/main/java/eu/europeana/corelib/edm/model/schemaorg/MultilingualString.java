package eu.europeana.corelib.edm.model.schemaorg;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MultilingualString implements BaseType {
    @JsonProperty(SchemaOrgConstants.VALUE)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String value;

    @JsonProperty(SchemaOrgConstants.LANGUAGE)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String language;

    public void setValue(String value) { this.value = value; }

    public void setLanguage(String language) { this.language = language; }

    public String getValue() {
        return value;
    }

    public String getLanguage() {
        return language;
    }

    @JsonIgnore
    @Override
    public String getTypeName() {
        return "MultilingualString";
    }
}
