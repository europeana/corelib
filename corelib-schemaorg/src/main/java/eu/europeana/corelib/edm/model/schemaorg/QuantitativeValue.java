package eu.europeana.corelib.edm.model.schemaorg;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldProperty;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;

import java.util.List;

@JsonldType(SchemaOrgConstants.TYPE_QUANTITATIVE_VALUE)
public class QuantitativeValue extends Thing {

    @JsonldProperty(SchemaOrgConstants.PROPERTY_UNIT_CODE)
    public List<BaseType> getUnitCode() {
        return getProperty(SchemaOrgConstants.PROPERTY_UNIT_CODE);
    }

    @JsonldProperty(SchemaOrgConstants.PROPERTY_VALUE)
    public List<BaseType> getValue() {
        return getProperty(SchemaOrgConstants.PROPERTY_VALUE);
    }

    @JsonIgnore
    @Override
    public String getTypeName() {
        return SchemaOrgConstants.TYPE_QUANTITATIVE_VALUE;
    }

    public void addUnitCode(Text unitCode) {
        addProperty(SchemaOrgConstants.PROPERTY_UNIT_CODE, unitCode);
    }

    public void addValue(Text value) {
        addProperty(SchemaOrgConstants.PROPERTY_VALUE, value);
    }
}
