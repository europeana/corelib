package eu.europeana.corelib.edm.model.schemaorg;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;

import java.util.List;

@JsonldType(SchemaOrgConstants.TYPE_TIMESPAN)
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({"@type", "@id", 
    SchemaOrgConstants.PROPERTY_NAME,SchemaOrgConstants.PROPERTY_ALTERNATE_NAME, SchemaOrgConstants.PROPERTY_DESCRIPTION,
	SchemaOrgConstants.PROPERTY_BEGIN_DATE, SchemaOrgConstants.PROPERTY_END_DATE,SchemaOrgConstants.PROPERTY_SAME_AS,
	SchemaOrgConstants.PROPERTY_IS_PART_OF, SchemaOrgConstants.PROPERTY_HAS_PART, SchemaOrgConstants.PROPERTY_NEXT_IN_SEQUENCE})
public class Timespan extends ContextualEntity {

    @JsonIgnore
    @Override
    public String getTypeName() { return SchemaOrgConstants.TYPE_TIMESPAN; }

    @JsonProperty(SchemaOrgConstants.PROPERTY_BEGIN_DATE)
    public List<BaseType> getBeginDate() {
        return getProperty(SchemaOrgConstants.PROPERTY_BEGIN_DATE);
    }

    @JsonProperty(SchemaOrgConstants.PROPERTY_END_DATE)
    public List<BaseType> getEndDate() {
        return getProperty(SchemaOrgConstants.PROPERTY_END_DATE);
    }

    @JsonProperty(SchemaOrgConstants.PROPERTY_IS_PART_OF)
    public List<BaseType> getIsPartOf() {
        return getProperty(SchemaOrgConstants.PROPERTY_IS_PART_OF);
    }

    @JsonProperty(SchemaOrgConstants.PROPERTY_HAS_PART)
    public List<BaseType> getHasPart() {
        return getProperty(SchemaOrgConstants.PROPERTY_HAS_PART);
    }

    @JsonProperty(SchemaOrgConstants.PROPERTY_NEXT_IN_SEQUENCE)
    public List<BaseType> getNextInSequence() {
        return getProperty(SchemaOrgConstants.PROPERTY_NEXT_IN_SEQUENCE);
    }

    public void addBeginDate(Text beginDate) {
        addProperty(SchemaOrgConstants.PROPERTY_BEGIN_DATE, beginDate);
    }

    public void addEndDate(Text endDate) {
        addProperty(SchemaOrgConstants.PROPERTY_END_DATE, endDate);
    }

    public void addIsPartOf(Text isPartOf) {
        addProperty(SchemaOrgConstants.PROPERTY_IS_PART_OF, isPartOf);
    }

    public void addHasPart(Text hasPart) {
        addProperty(SchemaOrgConstants.PROPERTY_HAS_PART, hasPart);
    }

    public void addNextInSequence(Text nextInSequence) {
        addProperty(SchemaOrgConstants.PROPERTY_NEXT_IN_SEQUENCE, nextInSequence);
    }

}
