package eu.europeana.corelib.edm.model.schemaorg;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;

@JsonldType(SchemaOrgConstants.TYPE_WEB_PAGE)
public class WebPage extends CreativeWork {
    @JsonIgnore
    @Override
    public String getTypeName() { return SchemaOrgConstants.TYPE_WEB_PAGE; }
}
