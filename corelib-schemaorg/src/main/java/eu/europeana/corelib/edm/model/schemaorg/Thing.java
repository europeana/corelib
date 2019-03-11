package eu.europeana.corelib.edm.model.schemaorg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldId;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;

@JsonldType(SchemaOrgConstants.TYPE_THING)
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({"@type", "@id", SchemaOrgConstants.PROPERTY_NAME, 
	SchemaOrgConstants.PROPERTY_ALTERNATE_NAME, SchemaOrgConstants.PROPERTY_DESCRIPTION, 
	SchemaOrgConstants.PROPERTY_IN_LANGUAGE, SchemaOrgConstants.PROPERTY_SAME_AS })
public class Thing implements BaseType {
    private String id;
    
    /**
     * Properties
     */
    @JsonIgnore
    private Map<String, List<BaseType>> properties = new HashMap<>();

    public void setId(String id) {
        this.id = id;
    }

    @JsonldId
    public String getId() {
        return id;
    }

    @JsonProperty(SchemaOrgConstants.PROPERTY_NAME)
    public List<BaseType> getName() {
        return getProperty(SchemaOrgConstants.PROPERTY_NAME);
    }

    @JsonProperty(SchemaOrgConstants.PROPERTY_DESCRIPTION)
    public List<BaseType> getDescription() {
        return getProperty(SchemaOrgConstants.PROPERTY_DESCRIPTION);
    }

    @JsonProperty(SchemaOrgConstants.PROPERTY_IN_LANGUAGE)
    public List<BaseType> getInLanguage() {
        return getProperty(SchemaOrgConstants.PROPERTY_IN_LANGUAGE);
    }

    @JsonProperty(SchemaOrgConstants.PROPERTY_ALTERNATE_NAME)
    public List<BaseType> getAlternateName() {
        return getProperty(SchemaOrgConstants.PROPERTY_ALTERNATE_NAME);
    }

    @JsonProperty(SchemaOrgConstants.PROPERTY_SAME_AS)
    public List<BaseType> getSameAs() {
        return getProperty(SchemaOrgConstants.PROPERTY_SAME_AS);
    }
    
    @JsonProperty(SchemaOrgConstants.PROPERTY_URL)
    public List<BaseType> getUrl() {
        return getProperty(SchemaOrgConstants.PROPERTY_URL);
    }

    
    public void addName(MultilingualString name) {
        addProperty(SchemaOrgConstants.PROPERTY_NAME, name);
    }

    public void addDescription(MultilingualString description) {
        addProperty(SchemaOrgConstants.PROPERTY_DESCRIPTION, description);
    }

    public void addInLanguage(MultilingualString inLanguage) {
        addProperty(SchemaOrgConstants.PROPERTY_IN_LANGUAGE, inLanguage);
    }

    public void addAlternateName(MultilingualString alternateName) {
        addProperty(SchemaOrgConstants.PROPERTY_ALTERNATE_NAME, alternateName);
    }

    public void addSameAs(Text sameAs) {
        addProperty(SchemaOrgConstants.PROPERTY_SAME_AS, sameAs);
    }

    public void addUrl(Text url) {
        addProperty(SchemaOrgConstants.PROPERTY_URL, url);
    }
    
    @JsonIgnore
    @Override
    public String getTypeName() {
        return SchemaOrgConstants.TYPE_THING;
    }

    public void addProperty(String propertyName, BaseType propertyValue) {
        if (propertyValue != null) {
            List<BaseType> currentValue = getCurrentPropertyValue(propertyName);
            if (!currentValue.contains(propertyValue)) {
                currentValue.add(propertyValue);
            }
        }
    }

    List<BaseType> getProperty(String propertyName) {
        return properties.get(propertyName);
    }

    private List<BaseType> getCurrentPropertyValue(String propertyName) {
        return properties.computeIfAbsent(propertyName, k -> new ArrayList<>());
    }
}
