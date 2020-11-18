package eu.europeana.corelib.edm.model.schemaorg;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;

import java.util.List;

@JsonldType(SchemaOrgConstants.TYPE_PERSON)
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({"@type", "@id", 
	SchemaOrgConstants.PROPERTY_URL, SchemaOrgConstants.PROPERTY_IMAGE, SchemaOrgConstants.PROPERTY_NAME, 
	SchemaOrgConstants.PROPERTY_ALTERNATE_NAME, SchemaOrgConstants.PROPERTY_DESCRIPTION,
	SchemaOrgConstants.PROPERTY_BIRTH_DATE, SchemaOrgConstants.PROPERTY_DEATH_DATE,
	SchemaOrgConstants.PROPERTY_GENDER, SchemaOrgConstants.PROPERTY_JOB_TITLE, 
	SchemaOrgConstants.PROPERTY_BIRTH_PLACE, SchemaOrgConstants.PROPERTY_DEATH_PLACE,
	SchemaOrgConstants.PROPERTY_SAME_AS })
public class Timespan extends ContextualEntity {

    @JsonIgnore
    @Override
    public String getTypeName() { return SchemaOrgConstants.TYPE_PERSON; }

    @JsonProperty(SchemaOrgConstants.PROPERTY_BIRTH_DATE)
    public List<BaseType> getBirthDate() {
        return getProperty(SchemaOrgConstants.PROPERTY_BIRTH_DATE);
    }

    @JsonProperty(SchemaOrgConstants.PROPERTY_DEATH_DATE)
    public List<BaseType> getDeathDate() {
        return getProperty(SchemaOrgConstants.PROPERTY_DEATH_DATE);
    }

    @JsonProperty(SchemaOrgConstants.PROPERTY_GENDER)
    public List<BaseType> getGender() {
        return getProperty(SchemaOrgConstants.PROPERTY_GENDER);
    }

    @JsonProperty(SchemaOrgConstants.PROPERTY_JOB_TITLE)
    public List<BaseType> getJobTitle() {
        return getProperty(SchemaOrgConstants.PROPERTY_JOB_TITLE);
    }

    @JsonProperty(SchemaOrgConstants.PROPERTY_BIRTH_PLACE)
    public List<BaseType> getBirthPlace() {
        return getProperty(SchemaOrgConstants.PROPERTY_BIRTH_PLACE);
    }

    @JsonProperty(SchemaOrgConstants.PROPERTY_DEATH_PLACE)
    public List<BaseType> getDeathPlace() {
        return getProperty(SchemaOrgConstants.PROPERTY_DEATH_PLACE);
    }

    public void addBirthDate(Text birthDate) {
        addProperty(SchemaOrgConstants.PROPERTY_BIRTH_DATE, birthDate);
    }

    public void addDeathDate(Text deathDate) {
        addProperty(SchemaOrgConstants.PROPERTY_DEATH_DATE, deathDate);
    }

    public void addGender(Text gender) {
        addProperty(SchemaOrgConstants.PROPERTY_GENDER, gender);
    }

    public void addJobTitle(MultilingualString jobTitle) {
        addProperty(SchemaOrgConstants.PROPERTY_JOB_TITLE, jobTitle);
    }

    public void addBirthPlace(MultilingualString birthPlace) {
        addProperty(SchemaOrgConstants.PROPERTY_BIRTH_PLACE, birthPlace);
    }

    public void addBirthPlace(Reference<Place> birthPlace) {
        addProperty(SchemaOrgConstants.PROPERTY_BIRTH_PLACE, birthPlace);
    }

    public void addDeathPlace(MultilingualString deathPlace) {
        addProperty(SchemaOrgConstants.PROPERTY_DEATH_PLACE, deathPlace);
    }

    public void addDeathPlace(Reference<Place> deathPlace) {
        addProperty(SchemaOrgConstants.PROPERTY_DEATH_PLACE, deathPlace);
    }
}
