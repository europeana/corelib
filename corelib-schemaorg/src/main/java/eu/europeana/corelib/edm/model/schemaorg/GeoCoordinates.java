package eu.europeana.corelib.edm.model.schemaorg;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;

import java.util.List;

@JsonldType(SchemaOrgConstants.TYPE_GEO_COORDINATES)
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({"@type", SchemaOrgConstants.PROPERTY_LATITUDE, 
	SchemaOrgConstants.PROPERTY_LONGITUDE, SchemaOrgConstants.PROPERTY_ELEVATION })
public class GeoCoordinates extends Thing {
    @JsonIgnore
    @Override
    public String getTypeName() { return SchemaOrgConstants.TYPE_GEO_COORDINATES; }

    @JsonProperty(SchemaOrgConstants.PROPERTY_LATITUDE)
    public BaseType getLatitude() {
    	List<BaseType> res = getProperty(SchemaOrgConstants.PROPERTY_LATITUDE);
    	if(res != null && !res.isEmpty())
    		return res.get(0);
    	return null;
    }

    @JsonProperty(SchemaOrgConstants.PROPERTY_LONGITUDE)
    public BaseType getLongitude() {
    	List<BaseType> res = getProperty(SchemaOrgConstants.PROPERTY_LONGITUDE);
    	if(res != null && !res.isEmpty())
    		return res.get(0);
    	return null;
    }

    @JsonProperty(SchemaOrgConstants.PROPERTY_ELEVATION)
    public BaseType getElevation() {
    	List<BaseType> res = getProperty(SchemaOrgConstants.PROPERTY_ELEVATION);
    	if(res != null && !res.isEmpty())
    		return res.get(0);
    	return null;
    }

    public void addLatitude(Text latitude) {
        addProperty(SchemaOrgConstants.PROPERTY_LATITUDE, latitude);
    }

    public void addLongitude(Text longitude) {
        addProperty(SchemaOrgConstants.PROPERTY_LONGITUDE, longitude);
    }

    public void addElevation(Text elevation) {
        addProperty(SchemaOrgConstants.PROPERTY_ELEVATION, elevation);
    }
}
