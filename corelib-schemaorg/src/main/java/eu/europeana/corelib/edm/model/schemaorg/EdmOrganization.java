package eu.europeana.corelib.edm.model.schemaorg;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;

@JsonldType(SchemaOrgConstants.TYPE_ORGANIZATION)
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({"@type", "@id", 
	SchemaOrgConstants.PROPERTY_URL, SchemaOrgConstants.PROPERTY_IMAGE, 
	SchemaOrgConstants.PROPERTY_MAIN_ENTITY_OF_PAGE, SchemaOrgConstants.PROPERTY_NAME, 
	SchemaOrgConstants.PROPERTY_ALTERNATE_NAME, SchemaOrgConstants.PROPERTY_DESCRIPTION,
	SchemaOrgConstants.PROPERTY_LOGO, SchemaOrgConstants.PROPERTY_AREA_SERVED,
	SchemaOrgConstants.PROPERTY_TELEPHONE, SchemaOrgConstants.PROPERTY_ADDRESS,
	SchemaOrgConstants.PROPERTY_IDENTIFIER, SchemaOrgConstants.PROPERTY_SAME_AS })
public class EdmOrganization extends ContextualEntity {
    @Override
    public String getTypeName() {
        return SchemaOrgConstants.TYPE_ORGANIZATION;
    }
    
    @JsonProperty(SchemaOrgConstants.PROPERTY_IDENTIFIER)
    public List<BaseType> getIdentifier() {
        return getProperty(SchemaOrgConstants.PROPERTY_IDENTIFIER);
    }
    
    @JsonProperty(SchemaOrgConstants.PROPERTY_MAIN_ENTITY_OF_PAGE)
    public BaseType getMainEntityOfPage() {
    	List<BaseType> res = getProperty(SchemaOrgConstants.PROPERTY_MAIN_ENTITY_OF_PAGE);
    	if(res != null && !res.isEmpty())
    		return res.get(0);
    	return null;
    }

    @JsonProperty(SchemaOrgConstants.PROPERTY_LOGO)
    public BaseType getLogo() {
    	List<BaseType> res = getProperty(SchemaOrgConstants.PROPERTY_LOGO);
    	if(res != null && !res.isEmpty())
    		return res.get(0);
    	return null;
    }
    
    @JsonProperty(SchemaOrgConstants.PROPERTY_AREA_SERVED)
    public BaseType getAreaServed() {
    	List<BaseType> res = getProperty(SchemaOrgConstants.PROPERTY_AREA_SERVED);
    	if(res != null && !res.isEmpty())
    		return res.get(0);
    	return null;
    }

    @JsonProperty(SchemaOrgConstants.PROPERTY_TELEPHONE)
    public BaseType getTelephone() {
    	List<BaseType> res = getProperty(SchemaOrgConstants.PROPERTY_TELEPHONE);
    	if(res != null && !res.isEmpty())
    		return res.get(0);
    	return null;
    }
    
    @JsonProperty(SchemaOrgConstants.PROPERTY_ADDRESS)
    public BaseType getAddress() {
    	List<BaseType> res = getProperty(SchemaOrgConstants.PROPERTY_ADDRESS);
    	if(res != null && !res.isEmpty())
    		return res.get(0);
    	return null;
    }
    
    public void addIdentifier(Text identifier) {
        addProperty(SchemaOrgConstants.PROPERTY_IDENTIFIER, identifier);
    }

    public void addMainEntityOfPage(Text mainEntityOfPage) {
        addProperty(SchemaOrgConstants.PROPERTY_MAIN_ENTITY_OF_PAGE, mainEntityOfPage);
    }
    
    public void addLogo(Text logo) {
        addProperty(SchemaOrgConstants.PROPERTY_LOGO, logo);
    }

    public void addAreaServed(Text areaServed) {
        addProperty(SchemaOrgConstants.PROPERTY_AREA_SERVED, areaServed);
    }
    
    public void addTelephone(Text telephone) {
        addProperty(SchemaOrgConstants.PROPERTY_TELEPHONE, telephone);
    }

    public void addAddress(PostalAddress address) {
        addProperty(SchemaOrgConstants.PROPERTY_ADDRESS, address);
    }
}
