package eu.europeana.corelib.edm.model.schemaorg;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;

@JsonldType(SchemaOrgConstants.TYPE_AUDIO_OBJECT)
public class AudioObject extends MediaObject {
    @JsonIgnore
    @Override
    public String getTypeName() { return SchemaOrgConstants.TYPE_AUDIO_OBJECT; }
}
