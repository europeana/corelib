package eu.europeana.corelib.edm.model.schemaorg;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o){
        if (o == this) return true;
        if (!(o instanceof MultilingualString)) {
            return false;
        }
        MultilingualString multilingualString = (MultilingualString) o;
        return Objects.equals(this.value, multilingualString.getValue()) &&
               Objects.equals(this.language, multilingualString.getLanguage());
    }

    @Override
    public int hashCode(){
        return new HashCodeBuilder(83, 211)
                .append(value)
                .append(language)
                .toHashCode();
    }
}
