package eu.europeana.corelib.edm.model.schemaorg;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;

@JsonldType(SchemaOrgConstants.TYPE_NEWSPAPER)
public class Newspaper extends CreativeWork {
    @JsonIgnore
    @Override
    public String getTypeName() { return SchemaOrgConstants.TYPE_NEWSPAPER; }
}
