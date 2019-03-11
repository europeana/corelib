package eu.europeana.corelib.edm.model.schemaorg;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldProperty;

@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({"@type", "@id", 
	SchemaOrgConstants.PROPERTY_URL, SchemaOrgConstants.PROPERTY_IMAGE, SchemaOrgConstants.PROPERTY_NAME, 
	SchemaOrgConstants.PROPERTY_ALTERNATE_NAME, SchemaOrgConstants.PROPERTY_DESCRIPTION, 
	SchemaOrgConstants.PROPERTY_IN_LANGUAGE, SchemaOrgConstants.PROPERTY_SAME_AS })
public abstract class ContextualEntity extends Thing {

	private String image;
    private String entityPageUrl;
    
    @JsonProperty(SchemaOrgConstants.PROPERTY_URL)
    public String getEntityPageUrl() {
        return entityPageUrl;
    }
    
    @JsonProperty(SchemaOrgConstants.PROPERTY_IMAGE)
    public String getImage() {
        return image;
    }
   
    public void setEntityPageUrl(String entityPageUrl) {
        this.entityPageUrl = entityPageUrl;
    }
    
    public void setImage(String image) {
        this.image = image;
    }
}
