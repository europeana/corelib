package eu.europeana.corelib.edm.model.schemaorg;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o){
        if (o == this) return true;
        if (!(o instanceof DateString)) {
            return false;
        }
        DateString dateString = (DateString) o;
        return Objects.equals(this.value, dateString.getValue());
    }

    @Override
    public int hashCode(){
        return new HashCodeBuilder(83, 211)
                .append(value)
                .toHashCode();
    }
}
