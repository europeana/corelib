package eu.europeana.corelib.edm.model.schemaorg;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;

import java.util.List;

@JsonldType(SchemaOrgConstants.TYPE_PLACE)
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({"@type", "@id", 
	SchemaOrgConstants.PROPERTY_URL, SchemaOrgConstants.PROPERTY_IMAGE, SchemaOrgConstants.PROPERTY_NAME, 
	SchemaOrgConstants.PROPERTY_ALTERNATE_NAME, SchemaOrgConstants.PROPERTY_GEO,
	SchemaOrgConstants.PROPERTY_DESCRIPTION, SchemaOrgConstants.PROPERTY_CONTAINS_PLACE,
	SchemaOrgConstants.PROPERTY_CONTAINED_IN_PLACE,	SchemaOrgConstants.PROPERTY_SAME_AS })
public class Place extends ContextualEntity {
	
    @JsonIgnore
    @Override
    public String getTypeName() { return SchemaOrgConstants.TYPE_PLACE; }

    @JsonProperty(SchemaOrgConstants.PROPERTY_GEO)
    public BaseType getGeo() {
    	List<BaseType> res = getProperty(SchemaOrgConstants.PROPERTY_GEO);
    	if(res != null && !res.isEmpty())
    		return res.get(0);
    	return null;
    }

    @JsonProperty(SchemaOrgConstants.PROPERTY_CONTAINS_PLACE)
    public List<BaseType> getContainsPlace() {
        return getProperty(SchemaOrgConstants.PROPERTY_CONTAINS_PLACE);
    }

    @JsonProperty(SchemaOrgConstants.PROPERTY_CONTAINED_IN_PLACE)
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
