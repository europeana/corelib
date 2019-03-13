package eu.europeana.corelib.edm.model.schemaorg;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;

@JsonldType(SchemaOrgConstants.TYPE_ORGANIZATION)
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({"@type", "@id", 
	SchemaOrgConstants.PROPERTY_URL, SchemaOrgConstants.PROPERTY_IMAGE, SchemaOrgConstants.PROPERTY_NAME, 
	SchemaOrgConstants.PROPERTY_ALTERNATE_NAME, SchemaOrgConstants.PROPERTY_DESCRIPTION,
	SchemaOrgConstants.PROPERTY_FOUNDING_DATE, SchemaOrgConstants.PROPERTY_DISSOLUTION_DATE,
	SchemaOrgConstants.PROPERTY_SAME_AS })
public class Organization extends ContextualEntity {
    @Override
    public String getTypeName() {
        return SchemaOrgConstants.TYPE_ORGANIZATION;
    }
    
    @JsonProperty(SchemaOrgConstants.PROPERTY_FOUNDING_DATE)
    public List<BaseType> getFoundingDate() {
        return getProperty(SchemaOrgConstants.PROPERTY_FOUNDING_DATE);
    }

    @JsonProperty(SchemaOrgConstants.PROPERTY_DISSOLUTION_DATE)
    public List<BaseType> getDissolutionDate() {
        return getProperty(SchemaOrgConstants.PROPERTY_DISSOLUTION_DATE);
    }
    
    public void addFoundingDate(Text foundingDate) {
        addProperty(SchemaOrgConstants.PROPERTY_FOUNDING_DATE, foundingDate);
    }

    public void addDissolutionDate(Text dissolutionDate) {
        addProperty(SchemaOrgConstants.PROPERTY_DISSOLUTION_DATE, dissolutionDate);
    }
}
