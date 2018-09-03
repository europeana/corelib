package eu.europeana.corelib.edm.model.schema.org;

import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldProperty;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;

import java.util.List;

@JsonldType(SchemaOrgConstants.TYPE_VISUAL_ARTWORK)
public class VisualArtwork extends CreativeWork {

    @JsonldProperty(SchemaOrgConstants.PROPERTY_ARTFORM)
    public List<BaseType> getArtform() {
        return getProperty(SchemaOrgConstants.PROPERTY_ARTFORM);
    }

    @JsonldProperty(SchemaOrgConstants.PROPERTY_ART_MEDIUM)
    public List<BaseType> getArtMedium() {
        return getProperty(SchemaOrgConstants.PROPERTY_ART_MEDIUM);
    }

    public void addArtform(Text artform) {
        addProperty(SchemaOrgConstants.PROPERTY_ARTFORM, artform);
    }

    public void addArtMedium(Text artMedium) {
        addProperty(SchemaOrgConstants.PROPERTY_ART_MEDIUM, artMedium);
    }

    @Override
    public String getTypeName() {
        return SchemaOrgConstants.TYPE_VISUAL_ARTWORK;
    }
}
