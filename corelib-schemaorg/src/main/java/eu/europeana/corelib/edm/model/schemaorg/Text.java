package eu.europeana.corelib.edm.model.schemaorg;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Objects;

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

    @JsonIgnore
    @Override
    public String getTypeName() {
        return "Text";
    }


    @Override
    public boolean equals(Object o){
        if (o == this) return true;
        if (!(o instanceof Text)) {
            return false;
        }
        Text text = (Text) o;
        return Objects.equals(this.value, text.toString());
    }

    @Override
    public int hashCode(){
        return new HashCodeBuilder(83, 211)
                .append(value)
                .toHashCode();
    }
}
