package eu.europeana.corelib.edm.model.schemaorg;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;

@JsonldType(SchemaOrgConstants.TYPE_THING)
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({"@type", "@id", 
    SchemaOrgConstants.PROPERTY_NAME,SchemaOrgConstants.PROPERTY_ALTERNATE_NAME, SchemaOrgConstants.PROPERTY_DESCRIPTION,
    SchemaOrgConstants.PROPERTY_SAME_AS})
public class Timespan extends ContextualEntity {

    @Override
    public String getTypeName() { return SchemaOrgConstants.TYPE_THING;}

}
