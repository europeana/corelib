package eu.europeana.corelib.edm.model.schema.org;

import com.fasterxml.jackson.annotation.JsonInclude;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldProperty;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;

import java.util.List;

@JsonldType("CreativeWork")
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class CreativeWork extends Thing {

    @Override
    public String getTypeName() { return SchemaOrgConstants.TYPE_CREATIVE_WORK;}

    @JsonldProperty(SchemaOrgConstants.PROPERTY_PUBLISHER)
    public List<BaseType> getPublisher() {
        return getProperty(SchemaOrgConstants.PROPERTY_PUBLISHER);
    }

    @JsonldProperty(SchemaOrgConstants.PROPERTY_CONTRIBUTOR)
    public List<BaseType> getContributor() {
        return getProperty(SchemaOrgConstants.PROPERTY_CONTRIBUTOR);
    }

    @JsonldProperty(SchemaOrgConstants.PROPERTY_ABOUT)
    public List<BaseType> getAbout() {
        return getProperty(SchemaOrgConstants.PROPERTY_ABOUT);
    }

    @JsonldProperty(SchemaOrgConstants.PROPERTY_CREATOR)
    public List<BaseType> getCreator() {
        return getProperty(SchemaOrgConstants.PROPERTY_CREATOR);
    }

    @JsonldProperty(SchemaOrgConstants.PROPERTY_DATE_CREATED)
    public List<BaseType> getDateCreated() {
        return getProperty(SchemaOrgConstants.PROPERTY_DATE_CREATED);
    }

    @JsonldProperty(SchemaOrgConstants.PROPERTY_HAS_PART)
    public List<BaseType> getHasPart() {
        return getProperty(SchemaOrgConstants.PROPERTY_HAS_PART);
    }

    @JsonldProperty(SchemaOrgConstants.PROPERTY_EXAMPLE_OF_WORK)
    public List<BaseType> getExampleOfWork() {
        return getProperty(SchemaOrgConstants.PROPERTY_EXAMPLE_OF_WORK);
    }

    @JsonldProperty(SchemaOrgConstants.PROPERTY_IS_PART_OF)
    public List<BaseType> getIsPartOf() {
        return getProperty(SchemaOrgConstants.PROPERTY_IS_PART_OF);
    }

    @JsonldProperty(SchemaOrgConstants.PROPERTY_DATE_PUBLISHED)
    public List<BaseType> getDatePublished() {
        return getProperty(SchemaOrgConstants.PROPERTY_DATE_PUBLISHED);
    }

    @JsonldProperty(SchemaOrgConstants.PROPERTY_MENTIONS)
    public List<BaseType> getMentions() {
        return getProperty(SchemaOrgConstants.PROPERTY_MENTIONS);
    }

    @JsonldProperty(SchemaOrgConstants.PROPERTY_SPATIAL_COVERAGE)
    public List<BaseType> getSpatialCoverage() {
        return getProperty(SchemaOrgConstants.PROPERTY_SPATIAL_COVERAGE);
    }

    @JsonldProperty(SchemaOrgConstants.PROPERTY_TEMPORAL_COVERAGE)
    public List<BaseType> getTemporalCoverage() {
        return getProperty(SchemaOrgConstants.PROPERTY_TEMPORAL_COVERAGE);
    }

    @JsonldProperty(SchemaOrgConstants.PROPERTY_IS_BASED_ON)
    public List<BaseType> getIsBasedOn() {
        return getProperty(SchemaOrgConstants.PROPERTY_IS_BASED_ON);
    }

    @JsonldProperty(SchemaOrgConstants.PROPERTY_PROVIDER)
    public List<BaseType> getProvider() {
        return getProperty(SchemaOrgConstants.PROPERTY_PROVIDER);
    }

    @JsonldProperty(SchemaOrgConstants.PROPERTY_ASSOCIATED_MEDIA)
    public List<BaseType> getAssociatedMedia() {
        return getProperty(SchemaOrgConstants.PROPERTY_ASSOCIATED_MEDIA);
    }

    @JsonldProperty(SchemaOrgConstants.PROPERTY_THUMBNAIL_URL)
    public List<BaseType> getThumbnailUrl() {
        return getProperty(SchemaOrgConstants.PROPERTY_THUMBNAIL_URL);
    }

    @JsonldProperty(SchemaOrgConstants.PROPERTY_LICENSE)
    public List<BaseType> getLicense() {
        return getProperty(SchemaOrgConstants.PROPERTY_LICENSE);
    }

    public void addPublisher(MultilingualString publisher) {
        addProperty(SchemaOrgConstants.PROPERTY_PUBLISHER, publisher);
    }

    public void addPublisher(Reference publisher) {
        addProperty(SchemaOrgConstants.PROPERTY_PUBLISHER, publisher);
    }

    public void addContributor(MultilingualString contributor) {
        addProperty(SchemaOrgConstants.PROPERTY_CONTRIBUTOR, contributor);
    }

    public void addContributor(Reference contributor) {
        addProperty(SchemaOrgConstants.PROPERTY_CONTRIBUTOR, contributor);
    }

    public void addAbout(MultilingualString about) {
        addProperty(SchemaOrgConstants.PROPERTY_ABOUT, about);
    }

    public void addAbout(Reference about) {
        addProperty(SchemaOrgConstants.PROPERTY_ABOUT, about);
    }

    public void addCreator(MultilingualString creator) {
        addProperty(SchemaOrgConstants.PROPERTY_CREATOR, creator);
    }

    public void addCreator(Reference creator) {
        addProperty(SchemaOrgConstants.PROPERTY_CREATOR, creator);
    }

    public void addDateCreated(Text dateCreated) {
        addProperty(SchemaOrgConstants.PROPERTY_DATE_CREATED, dateCreated);
    }

    public void addHasPart(MultilingualString hasPart) {
        addProperty(SchemaOrgConstants.PROPERTY_HAS_PART, hasPart);
    }

    public void addHasPart(Reference hasPart) {
        addProperty(SchemaOrgConstants.PROPERTY_HAS_PART, hasPart);
    }

    public void addExampleOfWork(MultilingualString exampleOfWork) {
        addProperty(SchemaOrgConstants.PROPERTY_EXAMPLE_OF_WORK, exampleOfWork);
    }

    public void addExampleOfWork(Reference exampleOfWork) {
        addProperty(SchemaOrgConstants.PROPERTY_EXAMPLE_OF_WORK, exampleOfWork);
    }

    public void addIsPartOf(MultilingualString isPartOf) {
        addProperty(SchemaOrgConstants.PROPERTY_IS_PART_OF, isPartOf);
    }

    public void addIsPartOf(Reference isPartOf) {
        addProperty(SchemaOrgConstants.PROPERTY_IS_PART_OF, isPartOf);
    }

    public void addDatePublished(Text datePublished) {
        addProperty(SchemaOrgConstants.PROPERTY_DATE_PUBLISHED, datePublished);
    }

    public void addMentions(MultilingualString mentions) {
        addProperty(SchemaOrgConstants.PROPERTY_MENTIONS, mentions);
    }

    public void addMentions(Reference mentions) {
        addProperty(SchemaOrgConstants.PROPERTY_MENTIONS, mentions);
    }

    public void addSpatialCoverage(MultilingualString spatialCoverage) {
        addProperty(SchemaOrgConstants.PROPERTY_SPATIAL_COVERAGE, spatialCoverage);
    }

    public void addSpatialCoverage(Reference spatialCoverage) {
        addProperty(SchemaOrgConstants.PROPERTY_SPATIAL_COVERAGE, spatialCoverage);
    }

    public void addTemporalCoverage(Text temporalCoverage) {
        addProperty(SchemaOrgConstants.PROPERTY_TEMPORAL_COVERAGE, temporalCoverage);
    }

    public void addIsBasedOn(Reference<CreativeWork> isBasedOn) {
        addProperty(SchemaOrgConstants.PROPERTY_IS_BASED_ON, isBasedOn);
    }

    public void addProvider(Text provider) {
        addProperty(SchemaOrgConstants.PROPERTY_PROVIDER, provider);
    }

    public void addProvider(Reference<Organization> provider) {
        addProperty(SchemaOrgConstants.PROPERTY_PROVIDER, provider);
    }

    public void addAssociatedMedia(Reference<MediaObject> associatedMedia) {
        addProperty(SchemaOrgConstants.PROPERTY_ASSOCIATED_MEDIA, associatedMedia);
    }

    public void addThumbnailUrl(Text thumbnailUrl) {
        addProperty(SchemaOrgConstants.PROPERTY_THUMBNAIL_URL, thumbnailUrl);
    }

    public void addLicense(Text license) {
        addProperty(SchemaOrgConstants.PROPERTY_LICENSE, license);
    }
}
