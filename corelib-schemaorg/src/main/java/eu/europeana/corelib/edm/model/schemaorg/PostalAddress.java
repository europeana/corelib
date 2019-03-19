package eu.europeana.corelib.edm.model.schemaorg;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;

import java.util.List;

/**
 * This class represents the data structure for schema.org serialization of VCARD Address
 */
@JsonldType(SchemaOrgConstants.TYPE_POSTAL_ADDRESS)
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({"@id", "@type", SchemaOrgConstants.PROPERTY_STREET_ADDRESS, 
	SchemaOrgConstants.PROPERTY_POSTAL_CODE, SchemaOrgConstants.PROPERTY_POST_OFFICE_BOX_NUMBER,
	SchemaOrgConstants.PROPERTY_ADDRESS_LOCALITY, SchemaOrgConstants.PROPERTY_ADDRESS_REGION,
	SchemaOrgConstants.PROPERTY_ADDRESS_COUNTRY})
public class PostalAddress extends Thing {
    @JsonIgnore
    @Override
    public String getTypeName() { return SchemaOrgConstants.TYPE_POSTAL_ADDRESS; }

    @JsonProperty(SchemaOrgConstants.PROPERTY_STREET_ADDRESS)
    public BaseType getStreetAddress() {
    	List<BaseType> res = getProperty(SchemaOrgConstants.PROPERTY_STREET_ADDRESS);
    	if(res != null && !res.isEmpty())
    		return res.get(0);
    	return null;
    }

    @JsonProperty(SchemaOrgConstants.PROPERTY_POSTAL_CODE)
    public BaseType getPostalCode() {
    	List<BaseType> res = getProperty(SchemaOrgConstants.PROPERTY_POSTAL_CODE);
    	if(res != null && !res.isEmpty())
    		return res.get(0);
    	return null;
    }

    @JsonProperty(SchemaOrgConstants.PROPERTY_POST_OFFICE_BOX_NUMBER)
    public BaseType getPostOfficeBoxNumber() {
    	List<BaseType> res = getProperty(SchemaOrgConstants.PROPERTY_POST_OFFICE_BOX_NUMBER);
    	if(res != null && !res.isEmpty())
    		return res.get(0);
    	return null;
    }
    
    @JsonProperty(SchemaOrgConstants.PROPERTY_ADDRESS_LOCALITY)
    public BaseType getAddressLocality() {
    	List<BaseType> res = getProperty(SchemaOrgConstants.PROPERTY_ADDRESS_LOCALITY);
    	if(res != null && !res.isEmpty())
    		return res.get(0);
    	return null;
    }

    @JsonProperty(SchemaOrgConstants.PROPERTY_ADDRESS_REGION)
    public BaseType getAddressRegion() {
    	List<BaseType> res = getProperty(SchemaOrgConstants.PROPERTY_ADDRESS_REGION);
    	if(res != null && !res.isEmpty())
    		return res.get(0);
    	return null;
    }

    @JsonProperty(SchemaOrgConstants.PROPERTY_ADDRESS_COUNTRY)
    public BaseType getAddressCountry() {
    	List<BaseType> res = getProperty(SchemaOrgConstants.PROPERTY_ADDRESS_COUNTRY);
    	if(res != null && !res.isEmpty())
    		return res.get(0);
    	return null;
    }

    public void addStreetAddress(Text streetAddress) {
        addProperty(SchemaOrgConstants.PROPERTY_STREET_ADDRESS, streetAddress);
    }

    public void addPostalCode(Text postalCode) {
        addProperty(SchemaOrgConstants.PROPERTY_POSTAL_CODE, postalCode);
    }
    
    public void addPostOfficeBoxNumber(Text postOfficeBoxNumber) {
        addProperty(SchemaOrgConstants.PROPERTY_POST_OFFICE_BOX_NUMBER, postOfficeBoxNumber);
    }
    
    public void addAddressLocality(Text addressLocality) {
        addProperty(SchemaOrgConstants.PROPERTY_ADDRESS_LOCALITY, addressLocality);
    }
    
    public void addAddressRegion(Text addressRegion) {
        addProperty(SchemaOrgConstants.PROPERTY_ADDRESS_REGION, addressRegion);
    }
    
    public void addAddressCountry(Text addressCountry) {
        addProperty(SchemaOrgConstants.PROPERTY_ADDRESS_COUNTRY, addressCountry);
    }
}
