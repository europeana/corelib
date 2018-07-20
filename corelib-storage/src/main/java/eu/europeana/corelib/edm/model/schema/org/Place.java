package eu.europeana.corelib.edm.model.schema.org;

import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldProperty;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;

import java.util.List;

@JsonldType(SchemaOrgConstants.TYPE_PLACE)
public class Place extends Thing {

    @JsonldProperty(SchemaOrgConstants.PROPERTY_GEO)
    public List<BaseType> getGeo() {
        return getProperty(SchemaOrgConstants.PROPERTY_GEO);
    }

    @JsonldProperty(SchemaOrgConstants.PROPERTY_CONTAINS_PLACE)
    public List<BaseType> getContainsPlace() {
        return getProperty(SchemaOrgConstants.PROPERTY_CONTAINS_PLACE);
    }

    @JsonldProperty(SchemaOrgConstants.PROPERTY_CONTAINED_IN_PLACE)
    public List<BaseType> getContainedInPlace() {
        return getProperty(SchemaOrgConstants.PROPERTY_CONTAINED_IN_PLACE);
    }

    public void addGeo(GeoCoordinates geo) {
        addProperty(SchemaOrgConstants.PROPERTY_GEO, geo);
    }

    public void addContainsPlace(Reference<Place> containsPlace) {
        addProperty(SchemaOrgConstants.PROPERTY_CONTAINS_PLACE, containsPlace);
    }

    public void addContainedInPlace(Reference<Place> containedInPlace) {
        addProperty(SchemaOrgConstants.PROPERTY_CONTAINED_IN_PLACE, containedInPlace);
    }
}
