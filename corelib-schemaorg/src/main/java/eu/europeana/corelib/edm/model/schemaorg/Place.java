package eu.europeana.corelib.edm.model.schemaorg;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldProperty;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;

import java.util.List;

@JsonldType(SchemaOrgConstants.TYPE_PLACE)
public class Place extends ContextualEntity {
	
    @JsonIgnore
    @Override
    public String getTypeName() { return SchemaOrgConstants.TYPE_PLACE; }

    @JsonldProperty(SchemaOrgConstants.PROPERTY_GEO)
    public BaseType getGeo() {
    	List<BaseType> res = getProperty(SchemaOrgConstants.PROPERTY_GEO);
    	if(res != null && !res.isEmpty())
    		return res.get(0);
    	return null;
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
