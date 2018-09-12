package eu.europeana.corelib.edm.model.schemaorg;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldProperty;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;

import java.util.List;

@JsonldType(SchemaOrgConstants.TYPE_GEO_COORDINATES)
public class GeoCoordinates extends Thing {
    @JsonIgnore
    @Override
    public String getTypeName() { return SchemaOrgConstants.TYPE_GEO_COORDINATES; }

    @JsonldProperty(SchemaOrgConstants.PROPERTY_LATITUDE)
    public List<BaseType> getLatitude() {
        return getProperty(SchemaOrgConstants.PROPERTY_LATITUDE);
    }

    @JsonldProperty(SchemaOrgConstants.PROPERTY_LONGITUDE)
    public List<BaseType> getLongitude() {
        return getProperty(SchemaOrgConstants.PROPERTY_LONGITUDE);
    }

    @JsonldProperty(SchemaOrgConstants.PROPERTY_ELEVATION)
    public List<BaseType> getElevation() {
        return getProperty(SchemaOrgConstants.PROPERTY_ELEVATION);
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
