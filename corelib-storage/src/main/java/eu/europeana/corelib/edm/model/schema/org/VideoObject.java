package eu.europeana.corelib.edm.model.schema.org;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;

@JsonldType(SchemaOrgConstants.TYPE_VIDEO_OBJECT)
public class VideoObject extends MediaObject {
    @JsonIgnore
    @Override
    public String getTypeName() { return SchemaOrgConstants.TYPE_VIDEO_OBJECT; }
}
