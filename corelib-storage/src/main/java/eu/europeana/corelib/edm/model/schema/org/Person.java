package eu.europeana.corelib.edm.model.schema.org;

import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldProperty;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;

import java.util.List;

@JsonldType(SchemaOrgConstants.TYPE_PERSON)
public class Person extends Thing {

    @JsonldProperty(SchemaOrgConstants.PROPERTY_BIRTH_DATE)
    public List<BaseType> getBirthDate() {
        return getProperty(SchemaOrgConstants.PROPERTY_BIRTH_DATE);
    }

    @JsonldProperty(SchemaOrgConstants.PROPERTY_DEATH_DATE)
    public List<BaseType> getDeathDate() {
        return getProperty(SchemaOrgConstants.PROPERTY_DEATH_DATE);
    }

    @JsonldProperty(SchemaOrgConstants.PROPERTY_GENDER)
    public List<BaseType> getGender() {
        return getProperty(SchemaOrgConstants.PROPERTY_GENDER);
    }

    @JsonldProperty(SchemaOrgConstants.PROPERTY_JOB_TITLE)
    public List<BaseType> getJobTitle() {
        return getProperty(SchemaOrgConstants.PROPERTY_JOB_TITLE);
    }

    @JsonldProperty(SchemaOrgConstants.PROPERTY_BIRTH_PLACE)
    public List<BaseType> getBirthPlace() {
        return getProperty(SchemaOrgConstants.PROPERTY_BIRTH_PLACE);
    }

    @JsonldProperty(SchemaOrgConstants.PROPERTY_DEATH_PLACE)
    public List<BaseType> getDeathPlace() {
        return getProperty(SchemaOrgConstants.PROPERTY_DEATH_PLACE);
    }
    private List<Reference<Place>> deathPlace;

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
