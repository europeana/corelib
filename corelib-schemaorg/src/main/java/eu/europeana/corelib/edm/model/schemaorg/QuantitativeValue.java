package eu.europeana.corelib.edm.model.schemaorg;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldProperty;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;
import java.util.Objects;

@JsonldType(SchemaOrgConstants.TYPE_QUANTITATIVE_VALUE)
public class QuantitativeValue implements BaseType {

    @JsonProperty(SchemaOrgConstants.PROPERTY_UNIT_CODE)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String unitCode;

    @JsonProperty(SchemaOrgConstants.PROPERTY_VALUE)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String value;

    public String getUnitCode() { return unitCode; }

    public void setUnitCode(String unitCode) { this.unitCode = unitCode; }

    public String getValue() { return value; }

    public void setValue(String value) { this.value = value; }

    @JsonIgnore
    @Override
    public String getTypeName() {
        return SchemaOrgConstants.TYPE_QUANTITATIVE_VALUE;
    }

    @Override
    public boolean equals(Object o){
        if (o == this) return true;
        if (!(o instanceof QuantitativeValue)) {
            return false;
        }
        QuantitativeValue quantitativeValue = (QuantitativeValue) o;
        return Objects.equals(this.value, quantitativeValue.getValue()) &&
               Objects.equals(this.unitCode, quantitativeValue.getUnitCode());
    }

    @Override
    public int hashCode(){
        return new HashCodeBuilder(83, 211)
                .append(value)
                .append(unitCode)
                .toHashCode();
    }

}
