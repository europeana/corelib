package eu.europeana.corelib.edm.model.schema.org;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldProperty;

public class MultilingualString {
    @JsonProperty("@value")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String value;

    @JsonProperty("@language")
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
}
