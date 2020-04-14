package eu.europeana.corelib.edm.model.schemaorg;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldProperty;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;

import java.util.List;

@JsonldType(SchemaOrgConstants.TYPE_MEDIA_OBJECT)
public class MediaObject extends CreativeWork {

    @JsonIgnore
    @Override
    public String getTypeName() { return SchemaOrgConstants.TYPE_MEDIA_OBJECT; }

    @JsonldProperty(SchemaOrgConstants.PROPERTY_UPLOAD_DATE)
    public List<BaseType> getUploadDate() {
        return getProperty(SchemaOrgConstants.PROPERTY_UPLOAD_DATE);
    }

    @JsonldProperty(SchemaOrgConstants.PROPERTY_CONTENT_URL)
    public List<BaseType> getContentUrl() {
        return getProperty(SchemaOrgConstants.PROPERTY_CONTENT_URL);
    }

    @JsonldProperty(SchemaOrgConstants.PROPERTY_ENCODES_CREATIVE_WORK)
    public List<BaseType> getEncodesCreativeWork() {
        return getProperty(SchemaOrgConstants.PROPERTY_ENCODES_CREATIVE_WORK);
    }

    @JsonldProperty(SchemaOrgConstants.PROPERTY_ENCODING_FORMAT)
    public List<BaseType> getEncodingFormat() {
        return getProperty(SchemaOrgConstants.PROPERTY_ENCODING_FORMAT);
    }

    @JsonldProperty(SchemaOrgConstants.PROPERTY_CONTENT_SIZE)
    public List<BaseType> getContentSize() {
        return getProperty(SchemaOrgConstants.PROPERTY_CONTENT_SIZE);
    }

    @JsonldProperty(SchemaOrgConstants.PROPERTY_HEIGHT)
    public List<BaseType> getHeight() {
        return getProperty(SchemaOrgConstants.PROPERTY_HEIGHT);
    }

    @JsonldProperty(SchemaOrgConstants.PROPERTY_WIDTH)
    public List<BaseType> getWidth() {
        return getProperty(SchemaOrgConstants.PROPERTY_WIDTH);
    }

    @JsonldProperty(SchemaOrgConstants.PROPERTY_DURATION)
    public List<BaseType> getDuration() {
        return getProperty(SchemaOrgConstants.PROPERTY_DURATION);
    }

    @JsonldProperty(SchemaOrgConstants.PROPERTY_BITRATE)
    public List<BaseType> getBitrate() {
        return getProperty(SchemaOrgConstants.PROPERTY_BITRATE);
    }

    public void addUploadDate(Text uploadDate) {
        addProperty(SchemaOrgConstants.PROPERTY_UPLOAD_DATE, uploadDate);
    }

    public void addContentUrl(Text contentUrl) {
        addProperty(SchemaOrgConstants.PROPERTY_CONTENT_URL, contentUrl);
    }

    public void addEncodesCreativeWork(Reference encodesCreativeWork) {
        addProperty(SchemaOrgConstants.PROPERTY_ENCODES_CREATIVE_WORK, encodesCreativeWork);
    }

    public void addEncodingFormat(Text encodingFormat) {
        addProperty(SchemaOrgConstants.PROPERTY_ENCODING_FORMAT, encodingFormat);
    }

    public void addContentSize(Text contentSize) {
        addProperty(SchemaOrgConstants.PROPERTY_CONTENT_SIZE, contentSize);
    }

    public void addHeight(QuantitativeValue height) {
        addProperty(SchemaOrgConstants.PROPERTY_HEIGHT, height);
    }

    public void addWidth(QuantitativeValue width) {
        addProperty(SchemaOrgConstants.PROPERTY_WIDTH, width);
    }

    public void addDuration(Text duration) {
        addProperty(SchemaOrgConstants.PROPERTY_DURATION, duration);
    }

    public void addBitrate(Text bitrate) {
        addProperty(SchemaOrgConstants.PROPERTY_BITRATE, bitrate);
    }
}
