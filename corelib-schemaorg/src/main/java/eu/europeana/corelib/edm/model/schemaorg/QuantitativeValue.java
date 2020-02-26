package eu.europeana.corelib.edm.model.schemaorg;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldProperty;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;

import java.util.List;

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

}
