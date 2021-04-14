package eu.europeana.corelib.edm.model.schemaorg;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldId;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o){
        if (o == this) return true;
        if (!(o instanceof Reference)) {
            return false;
        }
        Reference reference = (Reference) o;
        return Objects.equals(this.id, reference.getId()) &&
               Objects.equals(this.getType(), reference.getType());
    }

    @Override
    public int hashCode(){
        return new HashCodeBuilder(83, 211)
                .append(getType())
                .append(id)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "Reference{" +
                "type=" + type +
                ", id='" + id + '\'' +
                '}';
    }
}
